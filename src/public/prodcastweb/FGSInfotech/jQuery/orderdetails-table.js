 $(document).ready(function() {
	 
	// $('#quantityvalue').attr('disabled','disabled');
	// $('#subtotal').attr('disabled','disabled');
	   
	 
	/* var newOptions = {"12908": "LipSip 200 ml",
  "12909": "LipSip 300 ml",
  "12910": "LipSip 300 ml"
};
*/
// auto complete service begins
 
  
    
        
		 
		 
	   $(function() {
            var producttypes = [
           "LipSip 200 ml",
           "LipSip 300 ml",
           "LipSip 500 ml",
            ];
            $( ".productvalue" ).autocomplete({
               source: producttypes
            });
         });





// auto complete service ends
       


		var last=0;
		var lastid="";
		var gid=new Array();
   gid.push("1");
   gid.push("2");
   var i=gid[gid.length-1];
	var content="";
     $(".qty").one("click",function() {
		 i++;
		 gid.push(i);
  content='<div'; 
  content+='id="'+i+'"><h4><fieldset><div class="ui-block-b" style="width:55%"><input class="productvalue" id="productvalue'+i+'"';
  content+='type="text"></div><div class="ui-block-b" style="width:15%"><input class="qty" id="quantityvalue'+i+'" type="text"/>';
  content+='</div><div class="ui-block-c" style="width:30%"><input id="subtotalvalue'+i+'" type="text"/></div></fieldset></h4>';
  content+='<fieldset class="ui-grid-c" data-theme="c" id="fd'+i+'"><div class="ui-block-a" style="width:25%">Discount</div><div class="ui-block-b"'; 
  content+='style="width:15%"><input type="text" id="discount'+i+'"/></div><div class="ui-block-c" style="width:20%">Offer</div><div class="ui-block-d"';
  content+='style="width:15%"><input type="text" id="offer'+i+'"/></div></fieldset></div></div>';
 
		$('#set').append(content).trigger('create');
	  
	
      });    
	  
	  /*
	  if($(this).is('set:last-child'))
      {
        alert('yes');
      }
     else{
	      alert("no");
    } */
	var lastvalue=0;
	var currentID=0;
	var id=0;
	
	// lastid=$('#set').children().last().attr('id');
	// alert(($(this).attr('id')));
	
	
	/*$('.qty').click(function(evt){
    var clicked = evt.target;
    currentID = clicked.id || "No ID!";
	id=currentID.replace ( /[^\d.]/g, '' ); 
    alert(id);
    });  
	 */
	 
	 $(document).on('click','#set .qty', function() {
		$('.qty').click(function(evt){
    var clicked = evt.target;
    currentID = clicked.id || "No ID!";
	id=currentID.replace ( /[^\d.]/g, '' ); 
    //alert(id);
	lastvalue=gid[gid.length-1];
    
	if(id == lastvalue)
	 {
		  i++;
		  gid.push(i);
  content='<div id="'+i+'"><h4><fieldset><div class="ui-block-b" style="width:55%"><input class="productvalue" id="productvalue'+i+'"';
  content+='type="text"></div><div class="ui-block-b" style="width:15%"><input class="qty" id="quantityvalue'+i+'" type="text"/>';
  content+='</div><div class="ui-block-c" style="width:30%"><input id="subtotalvalue'+i+'" type="text"/></div></fieldset></h4>';
  content+='<fieldset class="ui-grid-c subfd" data-theme="c" id="fd'+i+'"><div class="ui-block-a" style="width:25%">Discount</div><div class="ui-block-b"'; 
  content+='style="width:15%"><input type="text" id="discount'+i+'"/></div><div class="ui-block-c" style="width:20%">Offer</div><div class="ui-block-d"';
  content+='style="width:15%"><input type="text" id="offer'+i+'"/></div></fieldset></div>';
 
		 
	 $('#set').append(content).trigger('create');
	}
	 
	 else
	 {
		//alert("no"); 
	 }
    });  
	
	
		
	 
	

 });

 $('.productvalue').click(function()
 {
	 $('.subfd').slideToggle();
      
 });
 $(document).on('click','#set .productvalue', function() {
 
	  $('.subfd').slideToggle();
      
 });
	  
	
	
	
	
	
	
        
  $('.remove').on("click", function() {
	  for(var k=0;k<gid.length;k++)
	  {
	if($('#set #'+gid[k]).hasClass('ui-collapsible-collapsed'))
	{ 
	//alert("Please select the row to delete ");
	}
	else
	{
		
	  $('#set #'+gid[k]).remove();
	    gid.splice(k);
	}
	  } 
  });

});
