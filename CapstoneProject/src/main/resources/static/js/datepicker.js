// Josh Adeyemo
// Modified datepicker from https://www.w3schools.com/code/tryit.asp?filename=FED216E6BIIJ
var currPage = 0; //month
var year ;
var day;

// formats the time to look like 09 or 10
function format(time){
    return (time < 10) ? '0' + time : '' + time;
}
// formats the provided hour and minutes to look like HH:MM or H:MM
function formatTime(hours, minutes){
    return hours + ':' + format(minutes);
}

// Parses the time from the format HH:MM and returns a date object with the hour and minutes set to time
function parseTime(t) {
    var d = new Date();
    var time = t.match( /(\d+)(?::(\d\d))?\s*(p?)/ );
    d.setHours( parseInt( time[1]) + (time[3] ? 12 : 0) );
    d.setMinutes( parseInt( time[2]) || 0 );
    return d;
 }

// Returns a list the times not available for a specific date (in any format i.e. YYYY/MM/DD or MM/DD/YYYY)
// Iterates through all the elements with the class of appointment and looks 
// for an element within with the class of date, and filters the list for dates that 
// are the same as the provided date. Using this new list of appointments, it transforms the list 
// into times by looking for an element within with the class of time
 function getTimesNotAvailableFor(date){
     var date = Date.parse(date);
     console.log(date);
        var appointments = document.querySelectorAll('.appointment');
        var listOfAppointments = [];
        appointments.forEach(app => {
            let appdate = app.querySelector('.date').innerHTML;
            appdate = Date.parse(appdate);
            console.log(appdate);
            if (date.toString() == appdate.toString()){
                console.log("added")
                listOfAppointments.push(app);
            }
        });
        var listOfTimes = listOfAppointments.map(app => app.querySelector('.time').innerHTML);
        return listOfTimes;

 }


 // Loads the time picker given a list of the times (in string format) not available and will disable options for those times
 // Also creates options for the times based on a start time and end time provided
 // Currently only works for hours and will be updated to work for minutes later
function loadTimePicker(listOfTimesNotAvailable){
    console.log(listOfTimesNotAvailable);

    // get the timepicker input
    var timepicker = document.querySelector('#ftime');
    // get the start time and end time, if they are null or empty, set to default values
    var st = document.querySelector('#start-time').textContent.trim() || '9:00';
    var et = document.querySelector('#end-time').textContent.trim() || '17:00';


    // parse the starttime and endtime
    var starttime = parseTime(st).getHours();
    var endtime = parseTime(et).getHours();


    timepicker.innerHTML = '';

    // create the options for the timepicker
     var timepickerOption;
     for (let i = starttime; i < endtime; i += 1){
        timepickerOption = document.createElement('option');
        let t = formatTime(i, 0);
        if (listOfTimesNotAvailable.includes(t)){
            timepickerOption.disabled = true;
        }
        timepickerOption.value = t;
        timepickerOption.text = t;
        timepicker.appendChild(timepickerOption);
     }

     // make the timepicker visible
     document.querySelector('#timepicker-wrapper').style.display = 'block';
}


// Event handler function for when a date is clicked 
// Will populate the field of a input with id 'fdate' with the value of the date button
// Will also load the time picker 
function getVal(e)
{
     //lert(document.getElementById(e.id).value);
     day = document.getElementById(e.id).value;
     var date = year +"/"+  (currPage + 1)  + "/" + day ;
     document.getElementById("fdate").value = date;
     loadTimePicker(getTimesNotAvailableFor(date));

     
}


$(document).ready(function(){
    // As soon as focus is recieved  on the input with id of 'fdate' 
    // call getDays for the month of January
    // also show the date picker 
    $("#fdate").focus(function()
        {

			
			currPage = new Date().getMonth();
            getDays(currPage);
            $("#datepicker").css("display"," block");
            
        }
        );


    // Next month buttons event handler 
    // increments the current month variable and calls getDay with the month
    $("#next-month").click(function(){
        if(currPage < 11)
        {
            currPage =  currPage+1;
            getDays(currPage);
        }

    });

    // Previous month button event handler
    // decrements the current month variable and calls getDay with the month
    $("#prev-month").click(function(){

            if(currPage > 0)
            {
                currPage =  currPage-1;
                getDays(currPage);
            }
    });


    // Next year button event handler
    // increments the value of the year and calls getDays for the current month (of that year)
    $("#next-y").click(function(){

        $("#year").text(  parseInt($("#year").text())  + 1   )  ;
        getDays(currPage);


    });

    // Previous year button event handler
    // decrements the value of the year and calls getDays for the current month (of that year)
    $("#prev-y").click(function(){

    $("#year").text(  parseInt($("#year").text())  - 1   )  ;
            getDays(currPage);

    });			

    

    // Loads the day values for a specific month as buttons with the correct offset
    function getDays(month)
    {

        $("#dt-able-body").empty();
        var mos=['January','February','March','April','May','June','July','August','September','October','Novemeber','Decemeber']
        var day=['Sun', 'Mon', 'Tue', 'Wed' , 'Thu', 'Fri', 'Sat']

        // get the days of the weeks that are blocked out using the element with the id of 'days-closed' 
        // and transforms the list into a new list of integer values representing those days
        // i.e Sun, Sat will be converted to [0, 6]
        var daysBlockedOut = document.querySelector('#days-closed')
                                .textContent
                                .split(',')
                                .map(dayOfWeek => day.findIndex(d => d === dayOfWeek.trim()));

        


        year = parseInt($("#year").text());

        $("#month-title").html(mos[month]);


        $("#dt-able-body").append('<tr>');
        
        for(i = 0; i < 7; i++)
        {

            $('#dt-able-body').append("<td id='dt-head'>"  + day[i] + "</td>");
        }

        $("#dt-able-body").append('</tr>');
        
        

        var firstDay = new Date(year,month, 1);
        var lastDay = new Date(year, month+1, 0);
        


        var offset = firstDay.getDay();

        var dayCount = 1;
        for (i = 0; i < 5; i++)
        {
            $('#dt-able-body').append("<tr id=row-"+ i +">");
            for(rw = 0; rw < 7; rw++ )
            {
                if(offset == 0)
                {

                    $('#' + "row-"+ i).append("<td  id='"  + "cell-" + dayCount+ "'>"

                     +   "<input type='button' id ='day_val" +dayCount +"'"+   " onclick='getVal(this)' value= '" +  dayCount + "' " + (daysBlockedOut.includes(rw)?'disabled': '')  + ">" + 
                         
                     '</td>' );
                    

                    if(dayCount >= lastDay.getDate())
                    {
                        break;
                    }
                    dayCount++;
                }else
                {
                    $('#' + "row-"+ i).append('<td>' +'</td>' );
                     offset--;

                }




            }
            $('#dt-able-body').append('</tr>');

        }
    }


});

