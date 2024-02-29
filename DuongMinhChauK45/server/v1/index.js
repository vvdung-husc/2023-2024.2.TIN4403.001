var express = require('express');
var bodyParser = require('body-parser');

var app = express();
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));

console.log("HELLO NODEJS")
//sử dụng để kiểm tra API có đang hoạt động
app.get("/", function (req, res) {
  res.status(200).send("Welcome to RESTFUL API - NODEJS - TIN4403 - DUONG MINH CHAU");
});

//dịch vụ WebService chạy tại cổng số n
var server = app.listen(4080, function () {
  console.log("API Running on port.", server.address().port);
}); 