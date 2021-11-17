var memberID = ducumen.getelementById("memberID").value;
var memberPW=document.getElementById("memberPW").value;
var memberPW_re=document.getElementById("memberPW_re").value;
var memberMessage = document.getElementById("message").value;
var memberEmail = document.getElementById("email").value;
var pwBox=document.getElementsByClassName("pw");
var pwCheck=document.getElementById("wrongPw");

var checkId = /^[a-zA-Z0-9]{4,12}$/;
var checkPW = /[a-z0-9]{2,}@[a0z0-9-]{2,}\.[a-z0-9]{2,}/i;


function checkPW() {
	if(memberPW!=memberPW_re){
		pwCheck.style.display="block";
		pwBox.style.border.color=darkred;
		p.innerHTML="dsaf";
	}
	else{
		pwCheck.style.display="none";
	}
}

pwBox.onchange=checkPW();