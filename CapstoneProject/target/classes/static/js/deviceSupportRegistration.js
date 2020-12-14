// Josh Adeyemo
// Note: The code in this file and deviceSupportRegistration.html isn't being used in the final product.
  
// get all the proceed buttons, back buttons and sub pages on the page
// Note: I use sub page in comments to avoid confusion with the actual HTML page
// (sub page - a HTML element with a 'data-page' attribute and a class of 'page')
const proceedBtns = document.querySelectorAll('.proceed-btn');
const backBtns = document.querySelectorAll('.back-btn');
const pages = document.querySelectorAll('.page');

// get the select elements that will hold a list of the developers and models of the phones
const developerSelect = document.querySelector('#developer-select');
const modelSelect = document.querySelector('#model-select');

// stores all the phones that will be shown on the page
const phones = [];

// stores the current sub page
let currentPage = 0;
// the number of sub pages
let numberOfPages = pages.length;

// go through each of the proceed buttons and add a click event listener to move to the next sub page 
proceedBtns.forEach((btn, ind) => {
    btn.addEventListener('click', (e) => {

		// get the currently shown select
        const select = $('.show select');
        
        
        if (ind === 0){
            if(select){
            		// load the next select (the 'model' select) based on the value that was chosen 
            		// for select on the first page (the 'developer' select) 
                    loadSelect('#model-select', {dev: select.val()});
                    //console.log(select.val());
            }
            
        }else if (ind === proceedBtns.length - 2){
        	// load the summary and hidden element after the second last proceed button is clicked
            loadHidden();
            loadSummary();
        }
        currentPage++;
        showPage(currentPage);
            
    });
});

// go through each of the back buttons and add a click event listener to move to the previous sub page
backBtns.forEach((btn, ind) => {
    btn.addEventListener('click', (e) => {

        
        currentPage--;
        showPage(currentPage);
        
    });
});






// shows the specified sub page with pageNumber while hiding the other pages from view
// and creates a smooth transition effect when transitioning from pages
function showPage(pageNumber){
    if (pageNumber < 0 || pageNumber >= numberOfPages){
        return;
    }
                
    pages.forEach(page => {
        if (page.dataset.page == pageNumber){
            if (!page.classList.contains('show')){
                page.classList.add('show');
                setTimeout(() => {
                    page.classList.add('showVisually');
                }, 20);
            }
        }else{
            if (page.classList.contains('showVisually')){
                page.classList.remove('showVisually');
                // page.addEventListener('transitionend', (e) => {
                    page.classList.remove('show');
                // }, {
                //     capture: false,
                //     once: true,
                //     passive: false
                // })
            }
        }
    });
}


$(document).ready(function() {
	// Ajax call to retrieve the phone data from a csv file
    $.ajax({
        type: "GET",
        url: "AndroidPhones.csv",
        dataType: "text",
        success: function(data) {processData(data);},

     });

	// show the current sub page when the HTML document is ready, (here currentPage is 0, which is first page)
     showPage(currentPage);
});

// A class to hold and nicely format the phone data that is retrieved from the phone data file 
class Phone {
    constructor(model, developer, release, version, ref){
        this.model = model;
        this.developer = developer;
        this.release = release;
        this.version = version;
        this.ref = ref;
    }
}

// A function that handles parsing of the data retrieved from the phone data file 
function processData(allText) {
    //console.log(allText);
    const phoneContent = document.querySelector('#phone-content');
    allText = phoneContent.innerHTML.trim() || allText;
    console.log(allText);
    var allTextLines = allText.split(/\r\n|\n/);
    var headers = [];
    var lines = [];

    for (var i=0; i<allTextLines.length; i++) {
        
        if (allTextLines[i].startsWith("Model")){
            headers = allTextLines[i].split(',');
            //console.log(allTextLines[i]);
        }else{
            var data = allTextLines[i].split(',');
            if (data.length == headers.length) {

                var tarr = [];
                for (var j=0; j<headers.length; j++) {
                    tarr.push(data[j]);
                }
                lines.push(tarr);
                phones.push(new Phone(data[0], data[1], data[2], data[3], data[4]));
            }
        }
    }
    
    loadSelect("#developer-select");
}

// Loads the proper options into the select form elements on the page
function loadSelect(selectQuery, params = {}) {
    if (selectQuery === "#developer-select"){
        $(selectQuery).empty();
        const developers = [...new Set(phones.map(phone => phone.developer))];
        //console.log(developers);
        developers.forEach(dev => {
            const o = new Option(dev, dev);
            $(o).html(dev);
            $(selectQuery).append(o);

        });
    }else if (selectQuery === "#model-select"){
        $(selectQuery).empty();
        const filteredPhones = phones.filter(phone => params.dev.includes(phone.developer));
        filteredPhones.forEach((phone, i) => {
            const o = new Option(phone.model, phone.model);
            $(o).html(phone.model);
            $(selectQuery).append(o);
            o.addEventListener("click", () => {
                $(".model-info").html(`Released: ${phone.release}, Version: ${phone.version}`);
            });
            if (i === 0){
                $(".model-info").html(`Released: ${phone.release}, Version: ${phone.version}`);
                o.selected = true;
            }
        });
    }
}

// Loads the correct information about the selected phone into the hidden fields 
// so that they can be sent as parameters to the controller when the form is submitted 
function loadHidden(){
    const releaseHidden = document.querySelector('#release-hidden');
    const osHidden = document.querySelector('#os-hidden');
    const currentModel = $('#model-select').val();

    const phoneIndex = phones.findIndex(p => currentModel.includes(p.model));

    if (phoneIndex >= 0){
        const phone = phones[phoneIndex];
        releaseHidden.value = phone.release;
        osHidden.value = phone.version;
    }
}

// Loads the summary of all the form data on the entire page onto the summary sub page
function loadSummary() {
    const formData = new FormData(document.querySelector('form'))
    const summaryDiv = document.querySelector('#summary');
    summaryDiv.innerHTML = '';
    for (var pair of formData.entries()) {
        summaryDiv.innerHTML += `${capitalize(pair[0])} : ${pair[1]}<br>`;
    }
}

// Capitalizes a string
function capitalize(string){
    return string[0].toUpperCase() + string.substring(1, string.length);
}
