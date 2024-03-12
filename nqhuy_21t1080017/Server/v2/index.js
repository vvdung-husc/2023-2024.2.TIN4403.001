var express = require("express");
var bodyParser = require("body-parser");
var Buffer = require("buffer/").Buffer;
var UTILS = require("./utils");

var app = express();
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));

console.log("HELLO NODEJS :");
//sử dụng để kiểm tra API có đang hoạt động
app.get("/", function (req, res) {
  console.log(arrUser);
  UTILS.apiResult(1, "RESTFUL API - NODEJS - TIN4403", res);
});

app.get("/test", function (req, res) {
  UTILS.apiResult(1, arrUser, res);
});


var arrUser = [];
var oUser = {};
oUser.username = "nqhuy";
oUser.phonenumber = "0832766413"; // Change this to your desired default phonenumber
oUser.password = "nqhuy123";

arrUser.push(oUser);
//hàm đăng nhập - nhận thông tin tài khoản từ Android App
app.post("/login", function (req, res) {
  var phonenumber = req.body.phonenumber;
  var pass = req.body.password;
  console.log("ACCOUNT:", phonenumber, "/", pass);
  login(phonenumber, pass, res);
});

//hàm đăng ký tài khoản
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

//hàm nhận thông tin tài khoản sau khi đã đăng nhập thành công
app.post("/userinfo", function (req, res) {
  var token = req.headers.token;
  userinfo(token, res);
});

//hàm nhận thông tin tài khoản sau khi đã đăng nhập thành công
app.post("/userupdate", function (req, res) {
  var token = req.headers.token;
  var username = req.body.username;
  var phonenumber = req.body.phonenumber;
  var pass = req.body.password;
  console.log("PASS", pass);
  console.log("NAME", username);
  console.log("PHONENUMBER", phonenumber);

  var info = {
    password: pass ? pass.toString() : null,
    username: username ? username.toString() : null,
    phonenumber: phonenumber ? phonenumber.toString() : null,
  };
  console.log(info);
  userUpdate(token, info, res);
});

//dịch vụ WebService chạy tại cổng số n
var server = app.listen(5080, function () {
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

function decodeToken(token) {
  var oResult = {};
  if (token == undefined || !token) {
    oResult["error"] = -1;
    oResult["message"] = "Yêu cầu đăng nhập trước khi sử dụng";
    return oResult;
  }

  //Chuyển token từ Base64 => Object - có dạng như ở phần login thành công
  var user_;
  try {
    var plain = Buffer.from(token, "base64").toString("utf8");
    console.log(plain);
    user_ = JSON.parse(plain);
    console.log(user_); //Không hiển thị nếu Parse lỗi
  } catch (e) {
    //console.log(e);
    oResult["error"] = -101;
    oResult["message"] = "Token -> JSON không hợp lệ";
    return oResult;
  }

  if (user_.p == undefined || !user_.p || user_.t == undefined || !user_.t) {
    oResult["error"] = -2;
    oResult["message"] = "JSON không hợp lệ";
    return oResult;
  }

  //kiểm tra thời gian đã logined, tính theo seconds
  var curSeconds = Date.now() / 1000;
  if (curSeconds - user_.t > 60 * 5) {
    //5phut
    oResult["error"] = -3;
    oResult["message"] = "Hết thời gian, yêu cầu đăng nhập lại để lấy token";
    return oResult;
  }

  oResult["error"] = 1;
  oResult["message"] = user_;

  return oResult;
}

function login(phonenumber, pass, res) {
  if (phonenumber == undefined || !phonenumber || phonenumber.length < 10) {
    UTILS.apiResult(-1, "Số điện thoại không hợp lệ", res);
    return;
  }
  if (pass == undefined || !pass || pass.length < 6) {
    UTILS.apiResult(-2, "Mật khẩu không hợp lệ", res);
    return;
  }

  if (!isExist(phonenumber, pass)) {
    UTILS.apiResult(-3, "Thông tin tài khoản không chính xác", res);
    return;
  }

  //Chuyển object thành chuổi Base64 - sử dụng cho các hàm sau khi đã login thành công
  var user_ = {};
  user_["p"] = phonenumber; //tên tài khoản đã đăng nhập
  user_["t"] = Date.now() / 1000; //thời gian đăng nhập (epoch second) - có thể dùng để yêu cầu đăng nhập lại nếu vượt quá thời gian xxx
  var token = Buffer.from(JSON.stringify(user_), "utf8").toString("base64");
  console.log(token);
  UTILS.apiResult(1, token, res);
}

function register(username, phonenumber, pass, res) {
  var u = getUserByPhonenumber(phonenumber);
  if (!u) {
    u = {};
    u.username = username;
    u.phonenumber = phonenumber;
    u.password = pass; 
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

function userinfo(token, res) {
  console.log("userinfo TOKEN: [", token, "]");

  var oResult = decodeToken(token);
  if (oResult.error != 1) {
    UTILS.apiResult(oResult.error, oResult.message, res);
    return;
  }

  var oToken = oResult.message;
  var u = getUserByPhonenumber(oToken.u);
  if (!u) {
    UTILS.apiResult(-4, "Không tìm thấy tài khoản - Token không hợp lệ", res);
    return;
  }

  UTILS.apiResult(1, u, res);
}

function userUpdate(token, info, res) {
  console.log("userUpdate TOKEN: [", token, "]");

  var oResult = decodeToken(token);
  if (oResult.error != 1) {
    UTILS.apiResult(oResult.error, oResult.message, res);
    return;
  }

  var oToken = oResult.message;
  var n = arrUser.length;
  for (var i = 0; i < n; ++i) {
    if (arrUser[i].username == oToken.u) {
      var count = 0;
      if (
        !(
          info.password == undefined ||
          !info.password ||
          info.password.length < 6
        )
      ) {
        count++;
        arrUser[i].password = info.password;
      }
      
      if (count == 0)
        UTILS.apiResult(-5, "[" + oToken.u + "] Không có gì để cập nhật", res);
      else UTILS.apiResult(1, "[" + oToken.u + "] Cập nhật thành công", res);
      return;
    }
  }
  UTILS.apiResult(-4, "Không tìm thấy tài khoản - Token không hợp lệ", res);
}
