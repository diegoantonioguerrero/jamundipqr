window.onload = function(){
    var inputText = document.getElementById("CaptchaIn");
    inputText.onpaste = function(e){
        e.preventDefault();
        alert('Esta acción está prohibida');
    };    
};



