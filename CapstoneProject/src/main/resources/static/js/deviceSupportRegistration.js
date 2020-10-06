const proceedBtns = document.querySelectorAll('.proceed-btn');
const backBtns = document.querySelectorAll('.back-btn');
const pages = document.querySelectorAll('.page');

const developerSelect = document.querySelector('#developer-select');
const modelSelect = document.querySelector('#model-select');


const phones = [];

let currentPage = 0;
let numberOfPages = pages.length;

proceedBtns.forEach((btn, ind) => {
    btn.addEventListener('click', (e) => {

        const select = $('.show select');
        
        
        if (ind === 0){
            if(select){
                    loadSelect('#model-select', {dev: select.val()});
                    //console.log(select.val());
            }
            
        }else if (ind === proceedBtns.length - 2){
            loadHidden();
            loadSummary();
        }
        currentPage++;
        showPage(currentPage);
            
    });
});

backBtns.forEach((btn, ind) => {
    btn.addEventListener('click', (e) => {

        
        currentPage--;
        showPage(currentPage);
        
    });
});







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
    $.ajax({
        type: "GET",
        url: "AndroidPhones.csv",
        dataType: "text",
        success: function(data) {processData(data);},

     });

     showPage(currentPage);
});

class Phone {
    constructor(model, developer, release, version, ref){
        this.model = model;
        this.developer = developer;
        this.release = release;
        this.version = version;
        this.ref = ref;
    }
}

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

function loadSummary() {
    const formData = new FormData(document.querySelector('form'))
    const summaryDiv = document.querySelector('#summary');
    summaryDiv.innerHTML = '';
    for (var pair of formData.entries()) {
        summaryDiv.innerHTML += `${capitalize(pair[0])} : ${pair[1]}<br>`;
    }
}

function capitalize(string){
    return string[0].toUpperCase() + string.substring(1, string.length);
}
