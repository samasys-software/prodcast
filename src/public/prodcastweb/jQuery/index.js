 $(document).ready(function() {
	 
	// customer selection in order entry screen begins
	
	
	 
	var customerList;
    var content="";
	var customerMap = {};
	var customerDisplay = [];
	var billno;
	var billamount;
	var outstandingamount;
	var outstanding;
	$('#outstandingdiv').hide();
	$('#review').hide();
	$('#savedialog').hide();
	$('#confirmdialog').hide();
	var saveorder=new Object();
	//var employeeId="43";
	var customerId="";
	var employeeId="";
    var so = {};
    var billsShown = false;	
	var entries=new Array();
	var originalBills = "";
	var originalOrderDetails = "";
    var selectedBill = "";	
	var billDetailsOriginal = "";
    var homelinkId = "";
	//
	
	$(".homepagelinks").on("click", function(){
    homelinkId=this.id;
	});
	
// auto complete service begins
	
	$("#orderdetailspage").on("pageinit", function(){
		originalOrderDetails  = $('#set').html();
	});
	
	$("#orderentry").on("pageinit",function() {
				
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
				
			}
			}
		}
	});
	
		
 });
 
  
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
							
							customerId=ui.item.value;
									
								$.ajax({
										type: 'GET' ,
										url : '../prodcast/global/customer?id='+ui.item.value,
										dataType : 'json',
										success : function( response ){
											
										outstandingBills = response.customer.outstandingBill;
										if( outstandingBills.length > 0 ){
											if(!billsShown){
												originalBills = $('#outstandingdiv').html();
												$('#outstandingdiv').slideToggle();
												billsShown = true;
											}
											else{
												$('#outstandingdiv').html( originalBills );
											}
											
											for( counter=0;counter<outstandingBills.length ;counter++){							
														$('#outstanding').append('<tr><td align="center"><input  id="billSelect'+counter+'" name="billNumber" value="'+outstandingBills[counter].billNumber+'" type="radio"/></td><td align="center"><a class="billNumber" id="'+outstandingBills[counter].billNumber+'" data-role="button" data-mini="true" data-inline="true">'+outstandingBills[counter].billNumber+'<a/></td><td align="center">'+outstandingBills[counter].billDate+'</td><td align="center">'+outstandingBills[counter].billAmount+'</td><td align="center">'+outstandingBills[counter].outstandingBalance+'</td></tr>');
											}
										}
										
										else{
											
									
											$('#outstandingdiv').html(originalBills );
											if(homelinkId!="collectionsId")
											{
											$.mobile.navigate('#orderdetailspage');	
											}
											
									}
										
										
									}
								});
								 
				
						}
 });






	
	// end
	


	
	$('#LoginButton').on("click", function() {
		
		$.ajax({
			type: 'GET' ,
			url : '../prodcast/global/login?userid='+$('#LoginUserId').val().toLowerCase()+'&password='+$('#LoginPassword').val(),
			dataType : 'json',
			success : function( response ){
				if( response.error == null ) {
					$('#loginError').html('Invalid username or password. Please try again');
					
				}
				else{
					var name = response.employee.firstname + ' ' +response.employee.lastname;
					employeeId = response.employee.employeeId;					
					$('#SalesManName').html( 'Welcome, Mr.'+name );
					window.location.href='#saleshome';
				}
			}
		});
	});
	
	$('#paymentSubmit').on("click", function() {
		
		var billId = "";
		
		for( counter=0;counter<outstandingBills.length ;counter++){	
			if( $('#billSelect'+counter).is(':checked')){ 
				billId = outstandingBills[counter].billNumber;
				break;
			}
		}
		
		var formData= {
			"employeeId":employeeId,
			"billId" : billId,
			"amount" : $('#payment').val(),
			"customerId" : customerId
		};
		
		
		$.ajax({
			type: 'POST' ,
			url : '../prodcast/global/collection',
			dataType : 'json',
			data : formData,
			encode : true ,
			success : function( response ){
				if ( response.error ){
					alert( response.errorMessage );
				}
				else{
						outstandingBills = response.customer.outstandingBill;
					if( outstandingBills.length > 0 ){
						if(!billsShown){
							originalBills = $('#outstandingdiv').html();
							$('#outstandingdiv').slideToggle();
							billsShown = true;
						}
						else{
							$('#outstandingdiv').html( originalBills );
						}
						
						
						
						for( counter=0;counter<outstandingBills.length ;counter++){							
									$('#outstanding').append('<tr><td align="center"><input id="billSelect'+counter+'" value="'+outstandingBills[counter].billNumber+'" type="radio"/></td><td align="center"><a class="billNumber" id="'+outstandingBills[counter].billNumber+'" data-role="button" data-mini="true" data-inline="true">'+outstandingBills[counter].billNumber+'<a/></td><td align="center">'+outstandingBills[counter].billDate+'</td><td align="center">'+outstandingBills[counter].billAmount+'</td><td align="center">'+outstandingBills[counter].outstandingBalance+'</td></tr>');
						}
					}
					
					else{
						$('#outstandingdiv').html(originalBills );
						$.mobile.navigate( '#orderdetailspage');
					}
				}
			}
		});
	});
	
	
	
	// order details functionality begins 
	
	var productList;
    var content="";
	var productMap = {};
	var productDisplay = [];
	var last=0;
	var lastid="";
	var gid=new Array();
	gid.push("1");
	
	$('.review').on("click",function()
	{
		$('#review').show();
	});

	function accordionEffect(id)
	{
		
		var totalVal = 0;
		 for(var k=0;k<gid.length;k++)
		 {
			 var ind = gid[k];
			 if( ind == id ){
				 $('#secondRow'+ind).show();
			 }
			 else{
				$('#secondRow'+ind).hide();
			 }
		 }
		 
	}		
	
	function calculateTotal( id)
	{
		
		if( $('#hproductvalue'+id).val()!="" && !isNaN( $('#hproductvalue'+id).val() )){
			var unitPrice = productList[productMap[$('#hproductvalue'+id).val()]].unitPrice;
			var qtytext = $('#quantityvalue'+id);
			var quantity = qtytext.val();
			var subtotal = Number(unitPrice)*Number( quantity );
			$('#subtotal'+id).text( subtotal );
		}
		var totalVal = 0;
		 for(var k=0;k<gid.length;k++)
		 {
			 var ind = gid[k];
			 if( !isNaN ( $('#subtotal'+ind).text())) totalVal += Number($('#subtotal'+ind).text());
		 }
		 
		 $('#totalvalue').text( totalVal );
		 
	}	

	
	$.ajax({
		type: 'GET' ,
		url : '../prodcast/global/products?startswith=',
		dataType : 'json',
		success : function( response ){
			
			if( response.error == 'true' ) {
				
				//$('#selectError').html('Please refresh the page and try again');
				alert("Please refresh the page and try again");
			}
			else{
				
			productList = response.productList;
			
			for( var counter=0;counter<productList.length;counter++){
				productMap[productList[counter].id]=counter;
				productDisplay.push({
					label: productList[counter].productName,
					value : productList[counter].id
				});
			}
			
			}
			
		}
	});
	
	$( "#productvalue1" ).autocomplete({
		            source: productDisplay,
					/*focus: function(event, ui) {
							// prevent autocomplete from updating the textbox
							event.preventDefault();
							// manually update the textbox
							$(this).val(ui.item.label);
							$("#hproductvalue1").val(ui.item.value);
							$("#quantityvalue1").val(0);
							var unitPrice = productList[productMap[ui.item.value]].unitPrice;
							$('#unitprice1').val( unitPrice);
							$('#subtotal1').val( 0 );
							calculateTotal(1);

							
					},*/
					select: function(event, ui) {
							// prevent autocomplete from updating the textbox
							event.preventDefault();
							// manually update the textbox and hidden field
							$(this).val(ui.item.label);
							$("#hproductvalue1").val(ui.item.value);
							$("#quantityvalue1").val(0);
							var unitPrice = productList[productMap[ui.item.value]].unitPrice;
							$('#unitprice1').text( unitPrice);
							$('#subtotal1').text( 0 );
							calculateTotal(1);
					}
	});
		
	
		var i=gid[gid.length-1];
		var content="";
			 $(".qty").one("click",function() {
			 i++;
			 gid.push(i);
	  
			  content='<div '; 
			  content+='id="'+i+'" style="border: 1px solid #3aff14; padding: 5px"><div id="firstRow'+i+'"><fieldset><div class="ui-block-b" style="width:55%"><input class="productvalue" id="productvalue'+i+'"';
			  content+='type="text"><input type="hidden" id="hproductvalue'+i+'"/></div><div class="ui-block-b" style="width:15%"><input class="qty" id="quantityvalue'+i+'" min="1" type="number" value="0"/>';
			  content+='</div><div class="ui-block-c" style="width:30%;margin-top:1%"><label style="align:center;padding-left:25%" id="subtotal'+i+'">0.00</label></div></fieldset></div>';
			  content+='<div id="secondRow'+ i + '" style="display:none" ><fieldset class="ui-grid-c" data-theme="c"><div class="ui-block-a" style="width:20%;height:100%">';
			  content+='<label for="unitprice">Unit Price:</label>&nbsp;<label  id="unitprice'+i+'" >0.00</label></div>';
			  content+='<div class="ui-block-c" style="width:80%">&nbsp;</div>';
			  content+='</div></fieldset></div></div>';
			 
			  $('#set').append(content).trigger('create');
			  
				$( "#productvalue"+i ).autocomplete({
								source: productDisplay,
								/*focus: function(event, ui) {
										// prevent autocomplete from updating the textbox
										event.preventDefault();
										// manually update the textbox
										$(this).val(ui.item.label);
										$("#hproductvalue"+i).val(ui.item.value);
										$("#quantityvalue"+i).val(0);
										var unitPrice = productList[productMap[ui.item.value]].unitPrice;
										$('#unitprice'+i).val( unitPrice);
										$('#subtotal'+i).val( 0 );
										calculateTotal(i);

										
								},*/
								select: function(event, ui) {
										// prevent autocomplete from updating the textbox
										
										var clicked = event.target;
										currentID = clicked.id || "No ID!";
										id=currentID.replace ( /[^\d.]/g, '' ); 

										event.preventDefault();
										// manually update the textbox
										$(this).val(ui.item.label);
										$("#hproductvalue"+id).val(ui.item.value);
										$("#quantityvalue"+id).val(0);
										var unitPrice = productList[productMap[ui.item.value]].unitPrice;
										$('#unitprice'+id).text( unitPrice);
										$('#subtotal'+id).text( 0 );
										calculateTotal(i);
								}
				});
		
	});    

	$(document).delegate('.qty' , 'keyup', function(evt ) {
		
			var clicked = evt.target;
			currentID = clicked.id || "No ID!";
			id=currentID.replace ( /[^\d.]/g, '' ); 
			calculateTotal(id);
	});
	
	$(document).delegate('.qty', 'click', function(evt){
			$('.qty').click(function(evt)
			{
				
			var clicked = evt.target;
			currentID = clicked.id || "No ID!";
			id=currentID.replace ( /[^\d.]/g, '' ); 
			//alert(id);
			lastvalue=gid[gid.length-1];
			
			if(id == lastvalue)
			 {
				 i++;
				 gid.push(i);
			  content='<div '; 
			  content+='id="'+i+'" style="border: 1px solid #3aff14; padding: 5px"><div id="firstRow'+i+'"><fieldset><div class="ui-block-b" style="width:55%"><input class="productvalue" id="productvalue'+i+'"';
			  content+='type="text"><input type="hidden" id="hproductvalue'+i+'"/></div><div class="ui-block-b" style="width:15%"><input class="qty" id="quantityvalue'+i+'" min="1" type="number" value="0"/>';
			  content+='</div><div class="ui-block-c" style="width:30%;margin-top:1%"><label style="align:center;padding-left:25%" id="subtotal'+i+'">0.00</label></div></fieldset></div>';
			  content+='<div id="secondRow'+ i + '" style="display:none" ><fieldset class="ui-grid-c" data-theme="c"><div class="ui-block-a" style="width:20%;height:100%">';
			  content+='<label for="unitprice">Unit Price:</label>&nbsp;<label  id="unitprice'+i+'" >0.00</label></div>';
			  content+='<div class="ui-block-c" style="width:80%">&nbsp;</div>';
			  content+='</div></fieldset></div></div>';
		 
				$('#set').append(content).trigger('create');
				 
				
				$( "#productvalue"+i ).autocomplete({
								source: productDisplay,
								/*focus: function(event, ui) {
										// prevent autocomplete from updating the textbox
										event.preventDefault();
										// manually update the textbox
										$(this).val(ui.item.label);
										$("#hproductvalue"+i).val(ui.item.value);
										$("#quantityvalue"+i).val(0);
										var unitPrice = productList[productMap[ui.item.value]].unitPrice;
										$('#unitprice'+i).val( unitPrice);
										$('#subtotal'+i).val( 0 );
										calculateTotal(i);

										
								},*/
								select: function(event, ui) {
										// prevent autocomplete from updating the textbox
										
										var clicked = event.target;
										currentID = clicked.id || "No ID!";
										id=currentID.replace ( /[^\d.]/g, '' ); 

										event.preventDefault();
										// manually update the textbox
										$(this).val(ui.item.label);
										$("#hproductvalue"+id).val(ui.item.value);
										$("#quantityvalue"+id).val(0);
										var unitPrice = productList[productMap[ui.item.value]].unitPrice;
										$('#unitprice'+id).text( unitPrice);
										$('#subtotal'+id).text( 0 );
										calculateTotal(i);
								}
				});
					
			 }
			 
			 accordionEffect(id);

	});
	  
	  
	
	});

	//$(document).delegate('.saveorder' , 'click', function(event ) {	
	$('.saveorder').on("click",function(event) {  
        
		
		$("#savedialog").dialog({
    modal: true,
    draggable: false,
    resizable: false,
    position: ['center'],
    show: 'blind',
    hide: 'blind',
	width: 200,
    dialogClass: 'ui-dialog-osx',
    });
	});	
	
	$('.sopaymentbtn').on("click",function(event){
		so.customerId=customerId;
		so.employeeId=employeeId;
		so.entries = entries;
		so.paymentAmount = $('#sopayment').val();
		
	   for(var i=0;i<gid.length;i++)
		{
			var proId = $('#hproductvalue'+gid[i]).val();
			if( proId == '' ){
				break;
			}
			var qtyVal = $('#quantityvalue'+gid[i]).val();
			if( qtyVal == '0'){
				break;
			}
			//alert(qtyVal);
			var entry = {};
			entry.productId = proId;
			entry.quantity = qtyVal;
			so.entries.push( entry );
		}	
		
	
	$.ajax({
		type: 'POST' ,
		url : '../prodcast/global/saveOrder',
		dataType : 'json',
		contentType: "application/json; charset=utf-8",
		data : JSON.stringify(so),
		
		success : function( response ){
			
			if( response.error ) {
					alert( response.errorMessage );
			}
			else{
				$('#savedialog').dialog("close");
				$('#confirmdialog').dialog({
    modal: true,
    draggable: false,
    resizable: false,
    position: ['center'],
    show: 'blind',
    hide: 'blind',
	width: 200,
	height:250,
    dialogClass: 'ui-dialog-osx',
    });
				
	 $('.confirmpaymentbtn').click(function()
	  {
		  $('#confirmdialog').dialog("close");
	 });
				
				$('#set').html( originalOrderDetails );
				outstandingBills = response.customer.outstandingBill;
				if( outstandingBills.length > 0 ){
					if(!billsShown){
						originalBills = $('#outstandingdiv').html();
						$('#outstandingdiv').slideToggle();
						billsShown = true;
					}
					else{
						$('#outstandingdiv').html( originalBills );
					}
					
					for( counter=0;counter<outstandingBills.length ;counter++){							
								$('#outstanding').append('<tr><td align="center"><input name="billNumber" id="billSelect" value="'+outstandingBills[counter].billNumber+'" type="radio"/></td><td align="center">'+outstandingBills[counter].billNumber+'</td><td align="center">'+outstandingBills[counter].billDate+'</td><td align="center">'+outstandingBills[counter].billAmount+'</td><td align="center">'+outstandingBills[counter].outstandingBalance+'</td></tr>');
					}
				}
				else{
					
			
					$('#outstandingdiv').html(originalBills );
					
				}
				$.mobile.navigate( '#orderentry');
			}

		}
			
		});
	});
	
	
     

	  
	//Remove Function
	  
	 
	    
	  
	  	$('.remove').on("click", function() {
		var completed = false;
		for(var k=0;k<gid.length;k++)
		{
			var sty = $('#secondRow'+gid[k]).css('display');
			if( sty != 'none')
			{ 
			  var idtoRemove= gid[k];
			  $('#set #'+idtoRemove).remove();
				gid.splice(k,1);
			   completed = true;
			   
			break;		   
			}
		}
		if( completed ) { 
			calculateTotal(0);
			accordionEffect(0);
		}
		if( !completed ){
				alert("Please select an entry to remove");
			}
				
		});
	
	
	// review panel functionality
	
	$('.reviewbtn').click(function()
	{     
		 $("#review").css("display", "block");
		 for(var i=0;i<gid.length;i++)
		{
			var productvalue = $('#productvalue'+gid[i]).val();
			
			var qtyvalue = $('#quantityvalue'+gid[i]).val();
			
			var uprice=$('#unitprice'+gid[i]).text();
			
			var stotal=$('#subtotal'+gid[i]).text();
			
			var total=$('#totalvalue').text();
			
			if(stotal != "0.00")
			{
			$('#reviewtable').append('<tbody id="tablebody"><tr><td align="center">'+productvalue+'</td><td align="right">'+qtyvalue+'</td><td align="right">'+uprice+'</td><td align="right">'+stotal+'</td></tr></tbody>');
           // $('#totalvalue1').text(total);
			}
		}
         $('#reviewtable').append('<tbody id="tablebody"><tr><td><hr style="border-color:#3aff14"></td><td><hr style="border-color:#3aff14"></td><td><hr style="border-color:#3aff14"></td><td><hr style="border-color:#3aff14"></td></tr></tbody>');     
		
		 $('#reviewtable').append('<tbody id="tablebody"><tr><td align="center"></td><td align="center"></td><td align="right"><b>Total Value</b></td><td align="right"><b>'+total+'</b></td></tr></tbody>');     
		  		
	});
	
	$('.continueorder').click(function()
	{
		$('#reviewtable >#tablebody').empty();
		$("#review").css("display", "none");
		
	});
	
	$(document).delegate('.billNumber' , 'click', function(event ) {
		selectedBill =  event.target.id;
		$('#billDetailsPage >#collectionTable').empty();
		$.mobile.navigate( '#billDetailsPage');
	});

	$(document).on('pageinit' , '#report' , function(){
		
			$( '#report #startdate' ).datepicker();
			$( '#report #enddate' ).datepicker();
			
	}	);
	$(document).on("pageshow","#billDetailsPage", function() {
				
		//$('#billDetailsPage').html( billDetailsOriginal);
		$('#collectionTable > tbody').empty();
		$('#productsTable > tbody').empty();
		
		$.ajax({
		type: 'GET' ,
		url : '../prodcast/global/billdetails?billId='+selectedBill,
		dataType : 'json',
		success : function( response ){
			if( response.error == 'true' ) {
				$('#selectError').html('Please refresh the page and try again');
				
			}
			else{
				 var order = response.order;
				 $('#billDetailsPage #billNumber').text( selectedBill );
				 $('#billDetailsPage #billDate').text( order.billDate );
				 $('#billDetailsPage #customerName').text( order.customerName );
				 $('#billDetailsPage #employeeName').text( order.employeeName );
				 $('#billDetailsPage #totalAmount').text( order.totalAmount );
				 $('#billDetailsPage #createDateTime').text( order.createTime );
				 $('#billDetailsPage #outstandingBalance').text( order.outstandingBalance );
				
				 //Adding products.
				 var orderEntries = order.orderEntries;
				 
				 for(counter=0;counter<orderEntries.length;counter++){
					 var entry = orderEntries[counter];
					 var trstr = '<tr><td>'+(counter+1)+'</td><td>'+entry.productName+'</td><td>'+entry.quantity+'</td><td>'+entry.unitPrice+'</td><td>'+entry.amount+'</td</tr>';
					 $('#billDetailsPage #productsTable').append( trstr );
				 }
				 
				 orderEntries = order.collectionEntries;
				 
				 for(counter=0;counter<orderEntries.length;counter++){
					 var entry = orderEntries[counter];
					 var trstr = '<tr><td>'+(counter+1)+'</td><td>'+entry.paymentDate+'</td><td>'+entry.employeeName+'</td><td>'+entry.amountPaid+'</td></tr>';
					 $('#billDetailsPage #collectionTable').append( trstr );
				 }
			}
		}
	});
	
		
 });
	

	//$(document).delegate('.reportaction' , 'click', function(event ) {
	$("input[name=reportdate]").bind('change', function(){	
		$('#collectionTable > tbody').empty();
		$('#salesTable > tbody').empty();
		var reportType = "today";
		for(counter=1;counter<=6;counter++){
			var obj = $('#reportdate'+counter);
			if( obj.is(':checked')) reportType = obj.val();
		}
		
		$.ajax({
		type: 'GET' ,
		url : '../prodcast/global/salesReport?employeeId='+employeeId+'&reportType='+reportType,
		dataType : 'json',
		success : function( response ){
			if( response.error == 'true' ) {
				$('#selectError').html('Please refresh the page and try again');
				
			}
			else{
				
				$('#lblTotalSales').text(response.totalSales);
				$('#lblTotalCollection').text(response.totalCollection);
				$('#lblTotalCash').text(response.collectionGroup["CASH"]);
				$('#lblBalance').text(response.balance);
				$('#reportdaterun').text( response.reportDates );		
				 //Adding products.
				 var orderEntries = response.orders;
				 
				 for(counter=0;counter<orderEntries.length;counter++){
					 var entry = orderEntries[counter];
					 var trstr = '<tr><td>'+(counter+1)+'</td><td>'+entry.customerName+'</td><td>'+entry.billDate+'</td><td>'+entry.totalAmount+'</td><td>'+entry.outstandingBalance+'</td</tr>';
					 $('#reportoutput #salesTable').append( trstr );
				 }
				 
				 orderEntries = response.collections;
				 
				 for(counter=0;counter<orderEntries.length;counter++){
					 var entry = orderEntries[counter];
					 var trstr = '<tr><td>'+(counter+1)+'</td><td>'+entry.customerName+'</td><td>'+entry.paymentDate+'</td><td>'+entry.amountPaid+'</td><td>'+entry.paymentType+'</td></tr>';
					 $('#reportoutput #collectionTable').append( trstr );
				 }
			}
		}
	});

		
	});
	
	});