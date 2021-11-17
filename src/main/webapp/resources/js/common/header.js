const menuButton=document.getElementById("menuLogo");
const menu=document.getElementById("mainMenu");
//height1=document.getElementById("mainHeader").style.height;
//height2=document.getElementById("mainMenu").sytle.height;

function getMenu(){
	menu.classList.toggle('active');
	//height1=height1+height2;
}
menuButton.addEventListener('click', getMenu);



//window.onload=getLoginMenu;

