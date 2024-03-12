var express = require('express');
var bodyParser = require('body-parser');

var app = express();
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));

console.log("HELLO NODEJS")
//sử dụng để kiểm tra API có đang hoạt động
app.get("/", function (req, res) {
  console.log(arrUser);
  res.status(200).send("Welcome to RESTFUL API - NODEJS - TIN4403 - NNTHI");
});
app.get("/test", function (req, res) {
  res.status(200).send(JSON.stringify(arrUser));
  //res.status(200).send("ROUTE TEST ....");
});

var arrUser = [];
var oUser = {};
oUser.username = "nnthi";
oUser.password = "123456";
oUser.fullname = "Nguyen Nhat Thi";
oUser.email = "nnthi@gmail.com";

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
  res.status(200).send("API USERINFO - POST");
});

//dịch vụ WebService chạy tại cổng số n
var server = app.listen(4080, function () {
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
function login(user,pass,res){
  //if (user == "nnthi" && pass == "123456" )
  if (isExist(user,pass))
    res.status(200).send("API LOGIN - THANH CONG");
  else
    res.status(503).send("API LOGIN - LOI TAI KHOAN");
}
function register(user,pass,name,email,res){
  //if (user == "nnthi" && pass == "123456" )
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