
module.exports = new CUtils();

function CUtils() {
}

CUtils.prototype.apiResult = function(code, msg, res){
  var oResult = {};

  if (code == undefined || !code) oResult["r"] = 0;
  else oResult["r"] = parseInt(code);
  if (msg == undefined || !msg ) oResult["m"] = "WebService Restful API";
  else oResult["m"] = msg;

  var status = oResult["r"] > 0 ? 200 : 503;
  res.setHeader('Content-Type', 'application/json');
  res.status(status).send(JSON.stringify(oResult));
}