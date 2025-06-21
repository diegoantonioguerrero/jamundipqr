var code;
var a;
var b;
var c;
var d;
var e;
    
function generateCode(){
	a = Math.ceil(Math.random() * 9)+ '';
	b = Math.ceil(Math.random() * 9)+ '';
	c = Math.ceil(Math.random() * 9)+ '';
	d = Math.ceil(Math.random() * 9)+ '';
	e = Math.ceil(Math.random() * 9)+ '';
	
	return (a + b + c + d + e);
}



try{
	code = generateCode();
	if(document.getElementById("txtCaptcha")){
		document.getElementById("txtCaptcha").value = code;
	}
	
	if(document.getElementById("CaptchaDiv")){
		document.getElementById("CaptchaDiv").innerHTML = a+'  '+b+'  '+c+'  '+d+'  '+e;
	}
	
	if(document.getElementById("captchaOk")){
		document.getElementById("captchaOk").style.display = "none";
	}
}
catch(err){
	alert(err);
} 

//Consultar = 1; Nueva PQRD = 2
var pagina = 0;
//Permitir = 1; Devolver = 0;
var permitir;

function pagConsultar(){
    pagina = 1;
    document.getElementById("CaptchaIn").value = "";
}
function pagFormular(){
    pagina = 2;
    document.getElementById("CaptchaIn").value = "";
}

function comprobarCaptcha(ingresado){
//ingresado = document.getElementById("txtCaptcha").value;
//document.getElementById("CaptchaIn").value = ingresado;  

    if (ingresado === ''){
        alert('Favor ingrese un codigo');
    }
    else if(ingresado === code){
        permitir = 1;
        localStorage.setItem("AccesoURL",permitir);
        if (pagina === 2){
            window.location.href="faces/formularpqrd.xhtml";
        } else if (pagina === 1) {
            window.location.href="faces/opcionesconsulta.xhtml";
            localStorage.setItem("nroRadicadoConsultar","");
            localStorage.setItem("nroVerificacionConsultar","");
        }
    } else {
        alert('Código Incorrecto');
    }
}


