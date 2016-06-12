 $(document).ready(function() {
	 
	var customerList;
    var content="";
	var customerMap = {};
	var customerDisplay = [];
	var billno;
	var billamount;
	var outstandingamount;
	var outstanding;
	$('#outstandingdiv').hide();

// auto complete service begins
	
	$("#orderentry").on("pageinit",function() {
	//$('#selectcustomer').click(function(event){
				
		$.ajax({
		type: 'GET' ,
		url : '../prodcast/global/customers?startswith=',
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
				//alert(customerList[counter].customerName);
			}
			}
		}
	});
	
		
 });
 
  //var arr=["Murugan tea stall","Anjappar","Buhari"];
var outstandingBills ;

 $("#selectcustomer").autocomplete({
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
							
								
									
								$.ajax({
										type: 'GET' ,
										url : '../prodcast/global/customer?id='+ui.item.value,
										dataType : 'json',
										success : function( response ){
											
										outstandingBills = response.customerList[0].outstandingBill;
										if( outstandingBills.length > 0 ){
											$('#outstandingdiv').slideToggle();
											//alert( outstandingBills[0].billNumber +" " +outstandingBills[0].billAmount);
$('#outstanding').append('<tr><td align="center">'+outstandingBills[0].billNumber+'</td><td align="center">'+outstandingBills[0].billDate+'</td><td align="center">'+outstandingBills[0].billAmount+'</td><td align="center">'+outstandingBills[0].outstandingBalance+'</td></tr>');
										}
										
										else{
											$('#outstandingdiv').slideToggle();
										   window.open("orderdetails_v7.html","_self");
										}
										
										
									}
								});
								 
								 
							
							
	
					}
});
	
	
	$('#LoginButton').on("click", function() {
		
		$.ajax({
			type: 'GET' ,
			url : '../prodcast/global/login?userid='+$('#LoginUserId').val().toLowerCase()+'&password='+$('#LoginPassword').val(),
			dataType : 'json',
			success : function( response ){
				if( response.employee == null ) {
					$('#loginError').html('Invalid username or password. Please try again');
					
				}
				else{
					var name = response.employee.firstname + ' ' +response.employee.lastname;
					$('#SalesManName').html( 'Welcome, Mr.'+name );
					window.location.href='#saleshome';
				}
			}
		});
	});
	
	});