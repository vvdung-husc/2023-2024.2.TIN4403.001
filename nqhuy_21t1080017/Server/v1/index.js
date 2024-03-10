var express = require("express");
var bodyParser = require("body-parser");
var unidecode = require("unidecode");

var app = express();
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));

console.log("HELLO NODEJS");

// sử dụng để kiểm tra API có đang hoạt động
app.get("/", function (req, res) {
  console.log(arrUser);
  res.status(200).send("Welcome to RESTFUL API - NODEJS - TIN4403 - NQHUY");
});

app.get("/test", function (req, res) {
  res.status(200).send(JSON.stringify(arrUser));
  // res.status(200).send("ROUTE TEST ....");
});

var arrUser = [];
var oUser = {};
oUser.username = "nqhuy";
oUser.phonenumber = "0832766413"; // Change this to your desired default phonenumber
oUser.password = "nqhuy";

arrUser.push(oUser);

// hàm đăng nhập - nhận thông tin tài khoản từ Android App
app.post("/login", function (req, res) {
  var phonenumber = req.body.phonenumber;
  var pass = req.body.password;
  console.log("ACCOUNT:", phonenumber, "/", pass);
  login(phonenumber, pass, res);
});

// hàm đăng ký tài khoản
app.post("/register", function (req, res) {
  var username = unidecode(req.body.username);
  var phonenumber = req.body.phonenumber;
  var pass = req.body.password;
  var oUser = {
    username: username,
    phonenumber: phonenumber,
    password: pass,
  };
  console.log(oUser);
  register(username, phonenumber, pass, res);
});

// hàm nhận thông tin tài khoản sau khi đã đăng nhập thành công
app.post("/userinfo", function (req, res) {
  res.status(200).send("API USERINFO - POST");
});

// dịch vụ WebService chạy tại cổng số n
var server = app.listen(4080, function () {
  console.log("API Running on port.", server.address().port);
});

function getUserByPhonenumber(phonenumber) {
  var n = arrUser.length;
  for (var i = 0; i < n; ++i) {
    if (arrUser[i].phonenumber == phonenumber) {
      return arrUser[i];
    }
  }
  return null;
}

function isExist(phonenumber, pass) {
  var oUser = getUserByPhonenumber(phonenumber);
  if (oUser && oUser.password == pass) return true;
  return false;
}

function login(phonenumber, pass, res) {
  console.log("Attempting login for:",phonenumber, "/", pass);
  if (isExist(phonenumber, pass))
    res.status(200).send("API LOGIN - THANH CONG");
  else res.status(503).send("API LOGIN - LOI TAI KHOAN");
}

function register(username, phonenumber, pass, res) {
  var u = getUserByPhonenumber(phonenumber);
  if (!u) {
    u = {};
    u.username = unidecode(username);
    u.phonenumber = phonenumber;
    u.password = pass; // mật khẩu mặt định nếu pass rỗng
    arrUser.push(u);
    res
      .status(200)
      .send("API REGISTER - THANH CONG [" + username + "]");
  } else {
    if (getUserByPhonenumber(phonenumber))
      res
        .status(503)
        .send("API REGISTER - TAI KHOAN [" + phonenumber + "] DA TON TAI");
    else if (getUserByPhonenumber(username))
      res
        .status(503)
        .send("API REGISTER - TAI KHOAN [" + username + "] DA TON TAI");
  }
}
