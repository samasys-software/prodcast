$(document).ready(function()
{

    var customerList;
    var content="";
	var customerMap = {};
	var customerDisplay = [];
	var customerId="";
	var fname="";
    

$.ajax({
	    type: 'GET' ,
		url : '../prodcast/global/customers?startswith=Ka',
		dataType : 'json',
		success : function( response ){
			if( response.error == 'true' ) {
				$('#selectError').html('Please refresh the page and try again');
				
			}
			else{
				
				customerList = response.customerList;
			for( var counter=0;counter<customerList.length;counter++){
				//customerDisplay.push(customerList[counter].customerName);
				customerMap[customerList[counter].id]=counter;
				customerDisplay.push({
					label: customerList[counter].customerName,
					value : customerList[counter].id 
				});
				
			}
			}
		}
	});
	
		
 $("#companyname").autocomplete({
	source: customerDisplay,
	focus: function(event, ui) {
							// prevent autocomplete from updating the textbox
							event.preventDefault();
							// manually update the textbox
							$(this).val(ui.item.label);
						},
						select: function(event, ui) {
							
							// prevent autocomplete from updating the textbox
							event.preventDefault();
							// manually update the textbox and hidden field
							$(this).val(ui.item.label);
							
							customerId=ui.item.value;
                           //alert(customerId);
						   $.ajax({
		type: 'GET' ,
		url : '../prodcast/global/customers?startswith='+customerId,
		dataType : 'json',
		success : function( response ){
			if( response.error == 'true' ) {
				$('#selectError').html('Please refresh the page and try again');
				
			}
			else{
				
				customer = response.customerList;
				for(var l=0;l<customer.length;l++)
				{
			    id=response.customer[l].id;
				alert(id);
				}
			}
		}
	});
						}
 


});







});