const optionButton=document.getElementById("optionBtn");
const option=document.getElementById("filter_container");
function getMenu(){
	option.classList.toggle('active');
	//optionButton.innerHTML="옵션<img src='../sources/arrow-up.png'>"
}

optionButton.addEventListener('click', getMenu);


