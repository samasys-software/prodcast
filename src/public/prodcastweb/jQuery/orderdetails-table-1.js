 $(document).ready(function() {
	 
	var productList;
    var content="";
	var productMap = {};
	var productDisplay = [];
	var last=0;
	var lastid="";
	var gid=new Array();
	gid.push("1");

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
	  
	  
	//Remove Function
	  
	  
	  });	  
	  
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

	  
	  });
	  
      

	  
	
	
	
	
	
	
        
  
