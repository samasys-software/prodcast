$(document).ready(function()
{
	
	var i;
	var area=["Please Select Area","Washington DC","New Jersy", "California"];
	
	for(i=0;i<area.length;i++)
		{
	 	$('<option>'+area[i]+'</option>').appendTo('#selectarea');
		}
	
	$(function() {
        var customernames = [
           "Jason",
           "Arnold",
           "Catherine",
           "Domnic",
           "Eisel",
           "Farkhan",
           "George",
        ];
        $( "#selectcustomer" ).autocomplete({
           source: customernames
        });
     });
	
	
});







