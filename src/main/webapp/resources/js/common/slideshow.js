/**
 * 
 */
var slideIndex=0;

window.onload=function(){
	showSlides(slideIndex);
	
	var sec=3000;
	setInterval(function(){
		slideIndex++;
		showslides(slideIndex);
	}, sec)
}


function moveSlides(n){
	slideIndex = slide
}