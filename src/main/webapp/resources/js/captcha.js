var a = Math.ceil(Math.random() * 9)+ '';
var b = Math.ceil(Math.random() * 9)+ '';
var c = Math.ceil(Math.random() * 9)+ '';
var d = Math.ceil(Math.random() * 9)+ '';
var e = Math.ceil(Math.random() * 9)+ '';

var code = a + b + c + d + e;
document.getElementById("txtCaptcha").value = code;
document.getElementById("CaptchaDiv").innerHTML = a+'  '+b+'  '+c+'  '+d+'  '+e;
if(document.getElementById("captchaOk")){
	document.getElementById("captchaOk").style.display = "none";
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
    var ingresado;
    if (ingresado === ''){
        alert('Favor ingrese un codigo');
    }
    else if(ingresado === code){
        permitir = 1;
        localStorage.setItem("AccesoURL",permitir);
        if (pagina === 2){
            window.location.href="faces/formularpqrd.xhtml";
        } else if (pagina === 1) {
            window.location.href="faces/consultar.xhtml";
            localStorage.setItem("nroRadicadoConsultar","");
            localStorage.setItem("nroVerificacionConsultar","");
        }
    } else {
        alert('Código Incorrecto');
    }
}


