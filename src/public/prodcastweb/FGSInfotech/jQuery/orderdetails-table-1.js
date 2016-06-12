 $(document).ready(function() {
	 
	// $('#quantityvalue').attr('disabled','disabled');
	// $('#subtotal').attr('disabled','disabled');
	   
	 
	/* var newOptions = {"12908": "LipSip 200 ml",
  "12909": "LipSip 300 ml",
  "12910": "LipSip 300 ml"
};
*/
// auto complete service begins
 
   var producttypes = [
           "LipSip 200 ml",
           "LipSip 300 ml",
           "LipSip 500 ml",
            ];
    
        
		 
	   $(function() {
           
            $( "#productvalue1" ).autocomplete({
               source: producttypes
            });
         });





// auto complete service ends
       


		var last=0;
		var lastid="";
		var gid=new Array();
   gid.push("1");
 
   var i=gid[gid.length-1];
	var content="";
	     $(".qty").one("click",function() {
		 i++;
		 gid.push(i);
  
  content='<div '; 
  content+='id="'+i+'" style="border: 1px solid #5b544e; padding: 5px"><div id="firstRow'+i+'"><fieldset><div class="ui-block-b" style="width:55%"><input class="productvalue" id="productvalue'+i+'"';
  content+='type="text"></div><div class="ui-block-b" style="width:15%"><input class="qty" id="quantityvalue'+i+'" type="text"/>';
  content+='</div><div class="ui-block-c" style="width:30%"><input id="subtotalvalue'+i+'" type="text"/></div></fieldset></div>';
  content+='<div id="secondRow'+ i + '" ><fieldset class="ui-grid-c" data-theme="c"><div class="ui-block-a" style="width:25%">Discount</div><div class="ui-block-b"'; 
  content+='style="width:15%"><input type="text" id="discount'+i+'"/></div><div class="ui-block-c" style="width:20%">Offer</div><div class="ui-block-d"';
  content+='style="width:15%"><input type="text" id="offer'+i+'"/></div></fieldset></div></div>';
 
  $('#set').append(content).trigger('create');
	  $( "#productvalue"+i ).autocomplete({
               source: producttypes
            });
	
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
  content+='id="'+i+'" style="border: 1px solid #5b544e; padding: 5px"><div id="firstRow'+i+'"><fieldset><div class="ui-block-b" style="width:55%"><input class="productvalue" id="productvalue'+i+'"';
  content+='type="text"></div><div class="ui-block-b" style="width:15%"><input class="qty" id="quantityvalue'+i+'" type="text"/>';
  content+='</div><div class="ui-block-c" style="width:30%"><input id="subtotalvalue'+i+'" type="text"/></div></fieldset></div>';
  content+='<div id="secondRow'+ i + '" ><fieldset class="ui-grid-c" data-theme="c"><div class="ui-block-a" style="width:25%">Discount</div><div class="ui-block-b"'; 
  content+='style="width:15%"><input type="text" id="discount'+i+'"/></div><div class="ui-block-c" style="width:20%">Offer</div><div class="ui-block-d"';
  content+='style="width:15%"><input type="text" id="offer'+i+'"/></div></fieldset></div></div>';
 
		$('#set').append(content).trigger('create');
		
		    $( "#productvalue"+i ).autocomplete({
               source: producttypes
            });
        
		
	 }
	 
	 {
		
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
	});
	 
      });    
	  
	//Remove Function
	$('.remove').on("click", function() {
		var completed = false;
	  for(var k=0;k<gid.length;k++)
	{
		if($('#secondRow'+gid[k]).css('display') == 'none')
		{ 
		
		alert("Deleting");
		  
		  $('#set #'+gid[k]).remove();
			gid.splice(k,1);
		   completed = true;
			break;		   
		}
	
		
		
	}
	if( !completed ){
			alert("Please select an entry to remove");
		}
			
  });
  
  //

});

 
	  
      

	  
	
	
	
	
	
	
        
  
