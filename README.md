# TIN4403 - Lập trình ứng dụng cho các thiết bị di động
WiFi - CNTT-MMT/13572468
Phần mềm
1. Android Studio - Hedgehog
 - https://developer.android.com/
 - okhttp https://square.github.io/okhttp/
2. Emulators (BlueStacks, LDPlayer, NoxPlayer, ...)
 - https://www.bluestacks.com/
3. Visual Studio Code
 - https://code.visualstudio.com/
4. MEAN Stack - https://meanjs.org/
 - MongoDB, ExpressJS, AngularJS, and Node.js
 - Search Google: mean stack la gi
 - Cài đặt Nodejs v20.11.1 (https://nodejs.org/), MongoDB
   
5. Postman - https://www.postman.com/
6. Thư viện okhttp (chi tiết trong file Notes.txt)
API - 
 - GET https://dev.husc.edu.vn/tin4403/api
 - POST https://dev.husc.edu.vn/tin4403/api/login
   + BODY TYPE x-www-form-urlencode: username/password
   + Response
   {
    r:1,
    m:'token-value'
   }
 - POST https://dev.husc.edu.vn/tin4403/api/userinfo
   + HEADER : token lấy từ bước đăng nhập
 - POST https://dev.husc.edu.vn/tin4403/api/register
   + BODY TYPE x-www-form-urlencode: username[/password/fullname/email]
- POST https://dev.husc.edu.vn/tin4403/api/userupdate
   + HEADER : token lấy từ bước đăng nhập
   + BODY TYPE x-www-form-urlencode: [password/fullname/email]
   
