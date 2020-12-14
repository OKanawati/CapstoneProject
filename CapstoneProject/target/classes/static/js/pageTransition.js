// Josh Adeyemo

// PageTransition class handles the transitions from page to page
// as well as the event handling for all the next and back buttons 
// on every page
// (page - a HTML element with a 'data-page' attribute and a class of 'page')
// (next buttons - buttons going to next page, back buttons - buttons going to previous page)
export class PageTransition{
    constructor(listPages, listNextButtons, listBackButtons, currentPage = 0){
    	// As we initialize the PageTransition class, it will accept a collection of 
    	// the HTML page elements 
    	// as well as the next and back buttons
    	// The currentPage stores the value of the current page that
    	// is visible (again this value is specified by the 'data-page' attribute)
        this.listPages = listPages;
        this.listNextButtons = listNextButtons;
        this.listBackButtons = listBackButtons;
        this.currentPage = currentPage;

		// Go through the list of next buttons and add a click event
		// so that it increments currentPage and shows the page
		// showPage() method defined below 
        this.listNextButtons.forEach((btn, ind) => {
            btn.addEventListener('click', (e) => {
                this.currentPage++;
                this.showPage(this.currentPage);    
            });
        });

        // Go through the list of back buttons and add a click event
		// so that it decrements currentPage and shows the page 
        this.listBackButtons.forEach((btn, ind) => {
            btn.addEventListener('click', (e) => {
                this.currentPage--;
                this.showPage(this.currentPage);   
            });
        });
    }

	// This method handles showing the provided page number as well 
	// as hiding the other pages from view 
    showPage(pageNumber){
    	// If the page number provided is invalid, exit
        if (this.currentPage < 0 || this.currentPage >= this.listPages.length){
            return;
        }
        
        // Go through the list of pages specified in the constructor
        // and check if the 'data-page' attribute is equal to the provided page number
        // If it is, show the page (set the display property to 'block') and after 20 milliseconds
        // show it visually (set the opacity to '1').
        // If is is not the page number, hide it visually (remove opacity '1' property) then 
        // hide it from the page, (remove display 'block' property) 
        // Note: all pages should have their display property set to 'none' and opacity to '0' 
        // by default for this to work
        // Note: if there was no setTimeout, the page would show instantaneously rather than gradually
        // fading in 
        this.listPages.forEach(page => {
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
	            	page.classList.remove('show');
                }
            }
        });
    }

	// Adds a click event that calls the 'listener' function to any next button
	// that has the class specified by 'buttonClass'
    addNextButtonClickEvent(buttonClass, listener){
        this.listNextButtons.forEach((btn, ind) => {
            if (btn.classList.contains(buttonClass)){
                btn.addEventListener('click', listener);
            }
        });
    }

	// Adds a click event that calls the 'listener' function to any back button
	// that has the class specified by 'buttonClass'
    addBackButtonClickEvent(buttonClass, listener){
        this.listBackButtons.forEach((btn, ind) => {
            if (btn.classList.contains(buttonClass)){
                btn.addEventListener('click', listener);
            }
        });
    }

}


