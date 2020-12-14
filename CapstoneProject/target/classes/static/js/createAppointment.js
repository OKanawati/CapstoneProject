// Josh Adeyemo
import { PageTransition } from './pageTransition.js';

// get all the pages, proceed buttons, and back buttons
let proceedBtns = document.querySelectorAll('.proceed-btn');
let backBtns = document.querySelectorAll('.back-btn');
let pages = document.querySelectorAll('.page');

// Create an instance of the PageTransition class
let pageTransitioner = new PageTransition(pages, proceedBtns, backBtns, 0);  


// When all the DOM content has finished loading
document.addEventListener('DOMContentLoaded', () => {
	// show the current page
	pageTransitioner.showPage(pageTransitioner.currentPage);
});

// ----- Section of code for createAppointment.html -----

// Sets text summary info from a single HTML form element
// to a another. The 'from' element has to have the id of 'identifier' 
// and the 'to' element has to have the class of 'identifier'.
// If the 'from' HTML form element is a required field and a value is not specified,
// 'required' will be set as the text, otherwise 'not provided' will be set
function loadSummaryItem(identifier){
	let from = document.querySelector(`#${identifier}`);
	let to = document.querySelector(`.${identifier}`);
	to.innerHTML = from.value.trim() || ((from.required)? 'required': 'not provided');
}

function loadAppointmentSummary(){
	loadSummaryItem('firstName');
	loadSummaryItem('lastName');
	loadSummaryItem('email');
	loadSummaryItem('fphone');
	loadSummaryItem('fdate');
	loadSummaryItem('ftime');
	loadSummaryItem('model-select');
	loadSummaryItem('fdescription');
	
}

// Add a click event to the 'go-to-summary' button to load appointment summary info
pageTransitioner.addNextButtonClickEvent('go-to-summary', () => {
	loadAppointmentSummary();
});