var express = require('express');
var bodyParser = require('body-parser');
var Buffer = require('buffer/').Buffer;
var UTILS = require('./utils');

var app = express();
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));

console.log("HELLO NODEJS :")
//sử dụng để kiểm tra API có đang hoạt động
app.get("/", function (req, res) {
  console.log(arrUser);
  UTILS.apiResult(1,"RESTFUL API - NODEJS - TIN4403",res);
});

app.get("/test", function (req, res) {
  UTILS.apiResult(1,arrUser,res);
});

var arrUser = [];
var oUser = {};
oUser.username = "admin";
oUser.password = "123456";
oUser.fullname = "Administrator";
oUser.email = "admin@tin4403.com";

arrUser.push(oUser);
//hàm đăng nhập - nhận thông tin tài khoản từ Android App
app.post("/login", function (req, res) {
  var user = req.body.username;
  var pass = req.body.password;	
  console.log("ACCOUNT:",user, "/",pass );
  login(user,pass,res);
});

//hàm đăng ký tài khoản
app.post("/register", function (req, res) {
  var user = req.body.username;
  var pass = req.body.password;	
  var name = req.body.fullname;
  var email = req.body.email;
  var oUser = {
    username: user,
    password: pass,
    fullname: name,
    email: email
  }
  console.log(oUser);
  register(user,pass,name,email,res);
  //res.status(200).send("API REGISTER - POST");
});

//hàm nhận thông tin tài khoản sau khi đã đăng nhập thành công
app.post("/userinfo", function (req, res) {
  var token = req.headers.token;  
  userinfo(token,res);
});

//hàm nhận thông tin tài khoản sau khi đã đăng nhập thành côngcls
app.post("/userupdate", function (req, res) {
  var token = req.headers.token; 
  var pass = req.body.password;	
  var name = req.body.fullname;
  var email = req.body.email;
  console.log('PASS',pass);
  console.log('NAME',name);
  console.log('EMAIL',email,"=>",email ? 1:0);

  var info = {
    password: pass ? pass.toString() : null,
    fullname: name ? name.toString() : null,
    email: email ? email.toString() : null
  }
  console.log(info);
  userUpdate(token,info,res);
});

//dịch vụ WebService chạy tại cổng số n
var server = app.listen(5080, function () {
  console.log("API Running on port.", server.address().port);
}); 

function getUser(user){
  var n = arrUser.length;
  for (var i = 0; i < n; ++i){
    if (arrUser[i].username == user) return arrUser[i];
  }
  return null;
}
function isExist(user,pass){
  var oUser = getUser(user);
  if (oUser && oUser.password == pass) return true;
  return false;
}

function decodeToken(token){
  var oResult = {};
  if (token == undefined || !token) {
    oResult['error'] = -1;
    oResult['message'] = "Yêu cầu đăng nhập trước khi sử dụng";
    return oResult;
  }

  //Chuyển token từ Base64 => Object - có dạng như ở phần login thành công
  var user_;
  try {
    var plain = Buffer.from(token, 'base64').toString('utf8');
    console.log(plain);
    user_ = JSON.parse(plain);
    console.log(user_);//Không hiển thị nếu Parse lỗi
  }
  catch (e){
    //console.log(e);
    oResult['error'] = -101;
    oResult['message'] = "Token -> JSON không hợp lệ";
    return oResult;    
  }  

  if (user_.u == undefined || !user_.u ||
    user_.t == undefined || !user_.t) {
    oResult['error'] = -2;
    oResult['message'] = "JSON không hợp lệ";
    return oResult; 
  }

  //kiểm tra thời gian đã logined, tính theo seconds
  var curSeconds = Date.now()/1000;
  if (curSeconds - user_.t > (60 * 5)){ //5phut
    oResult['error'] = -3;
    oResult['message'] = "Hết thời gian, yêu cầu đăng nhập lại để lấy token";
    return oResult; 
  }

  oResult['error'] = 1;
  oResult['message'] = user_;

  return oResult; 
}

function login(user,pass,res){
  if (user == undefined || !user || user.length < 3){
    UTILS.apiResult(-1,"Tài khoản không hợp lệ",res);
    return;
  }
  if (pass == undefined || !pass || pass.length < 6){
    UTILS.apiResult(-2,"Mật khẩu không hợp lệ",res);
    return;
  }    

  if (!isExist(user,pass)){
    UTILS.apiResult(-3,"Thông tin tài khoản không chính xác",res);
    return;
  }
  
  //Chuyển object thành chuổi Base64 - sử dụng cho các hàm sau khi đã login thành công
  var user_ = {};
  user_["u"] = user;       //tên tài khoản đã đăng nhập
  user_["t"] = Date.now()/1000; //thời gian đăng nhập (epoch second) - có thể dùng để yêu cầu đăng nhập lại nếu vượt quá thời gian xxx
  var token = Buffer.from(JSON.stringify(user_), 'utf8').toString('base64');
  console.log(token);
  UTILS.apiResult(1,token,res);
}
function register(user,pass,name,email,res){
  //if (user == "vvdung" && pass == "123456" )
  var u = getUser(user);
  if (!u){
    u = {};
    u.username = user;
    u.password = pass ? pass : "654321"; //mật khẩu mặt định nếu pass rỗng
    u.fullname = name ? name : "";//mặt định rỗng
    u.email = email ? email : "";//mặt định rỗng
    arrUser.push(u);
    res.status(200).send("API REGISTER - THANH CONG [" + user + "]");
  }
  else {
    res.status(503).send("API REGISTER - TAI KHOAN [" + user + "] DA TON TAI");
  }    
}

function userinfo(token,res){
  console.log("userinfo TOKEN: [",token,"]");

  var oResult = decodeToken(token);
  if (oResult.error != 1) {
    UTILS.apiResult(oResult.error,oResult.message,res);
    return;
  }

  var oToken = oResult.message;
  var u = getUser(oToken.u);
  if (!u){ 
    UTILS.apiResult(-4,"Không tìm thấy tài khoản - Token không hợp lệ",res);
    return;
  }  

  UTILS.apiResult(1,u,res);
}

function userUpdate(token,info,res){
  console.log("userUpdate TOKEN: [",token,"]");

  var oResult = decodeToken(token);
  if (oResult.error != 1) {
    UTILS.apiResult(oResult.error,oResult.message,res);
    return;
  }

  var oToken = oResult.message;
  var n = arrUser.length;
  for (var i = 0; i < n; ++i){
    if (arrUser[i].username == oToken.u){
      var count = 0;
      if (!(info.password == undefined || !info.password || info.password.length < 6)){
        count++;
        arrUser[i].password = info.password;
      }
      if (!(info.fullname == undefined || !info.fullname)){
        count++;
        arrUser[i].fullname = info.fullname;
      }      
      if (!(info.email == undefined || !info.email)){
        count++;
        arrUser[i].email = info.email;
      }   
      if (count == 0) UTILS.apiResult(-5,"[" + oToken.u + "] Không có gì để cập nhật",res);
      else UTILS.apiResult(1,"[" + oToken.u + "] Cập nhật thành công",res);
      return;
    } 
  }
  UTILS.apiResult(-4,"Không tìm thấy tài khoản - Token không hợp lệ",res);
}