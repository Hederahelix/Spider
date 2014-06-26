function disableCopy(){return false}
//document.body.onselectstart=disableCopy;
//document.body.oncopy=disableCopy;
//document.body.oncut=disableCopy;
function paperDecode(paperString){var ret='';paperString=unescape(paperString);for(var i=paperString.length;i>0;i--){ret+=paperString.substr(i-1,1)}return ret}
function jsPaperDecode(paperString){document.write(paperDecode(paperString))}