try
{
	window.onload = function(){
		try
		{
		    var inputText = document.getElementById("CaptchaIn");
	//	    if(inputText !== undefined && inputText!= null){
		   
			    inputText.onpaste = function(e){
			        e.preventDefault();
			        alert('Esta acción está prohibida');
			    };    
			    
	//	    }
		}
		catch(err){
			alert(err);
		}
 
	};
}
catch(err){
	alert(err);
}


