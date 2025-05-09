var emailConsultaOk = false;
var emailConsultaLabel = document.getElementById("formPrincipal:emailConsultaLabel");
var emailConsultaInput = document.getElementById("formPrincipal:emailConsulta");
var emailConsultaValue = "";

var numVerificacionLabel = document.getElementById("formPrincipal:numVerificacionLabel");
var numVerificacionInput = document.getElementById("formPrincipal:numVerificacion");
var numVerificacionValue = "";
var numVerificacionOk = false;

var msgEscribaNroVerificacion = 'Debe escribir el número de verificación de 6 dígitos que fue enviado a su email';
    
var pasoActual = document.getElementById("pasoActual");
//console.log("pasoActual", pasoActual.value );

function dialogWidth(){
    var htmlTag = document.getElementById('correspondenciaNoExiste');
    /*
    console.log("dialogWidth", htmlTag );
    
    if (htmlTag){
    	htmlTag.style.width = Math.floor(window.innerWidth*0.8)+"px";
    }
    
    var dialogTitle = document.getElementById('formPrincipal:dialogoEnvio_title');
    var centerLong = Math.floor(window.innerWidth*0.8)/2-15;
    var pad = centerLong.toString()
    dialogTitle.style.padding = "0 0 0 " +pad+ "px";
    */
}

function accesoNumVerificacion(){
    permitir = 1;
    localStorage.setItem("AccesoURL",permitir);
    localStorage.setItem("nroRadicadoConsultar",numRadicadoValue);
    localStorage.setItem("nroVerificacionConsultar",numVerificacionValue);
    var consultar = window.location.href.split("/faces")[0] + "/faces/numverificacion.xhtml";
    location.href=consultar;
}
/*
function inicializarConsultar(){
    numRadicadoInput.value = localStorage.getItem("nroRadicadoConsultar").toString();
    numRadicadoValue = numRadicadoInput.value;
    numVerificacionInput.value = localStorage.getItem("nroVerificacionConsultar").toString();
    numVerificacionValue = numVerificacionInput.value;
    localStorage.setItem("nroRadicadoConsultar","");
    localStorage.setItem("nroVerificacionConsultar","");
}*/


function getEmailConsulta() { //Funciona OnKeyUp
    emailConsultaValue = emailConsultaInput.value;
    emailConsultaInput.style = 'border: 1px solid #a8a8a8; width: 100%!important;';
    emailConsultaLabel.style = 'color: #4f4f4f;';
}

function getNroVerificacion() { //Funciona OnKeyUp
	numVerificacionInput = document.getElementById("formPrincipal:numVerificacion");
	numVerificacionLabel = document.getElementById("formPrincipal:numVerificacionLabel");
    numVerificacionValue = numVerificacionInput.value;
    numVerificacionInput.style = 'border: 1px solid #a8a8a8; width: 100%!important;';
    numVerificacionLabel.style = 'color: #4f4f4f;';
}



function removerEspacios(obj) { //Funciona OnKeyUp
	var data = obj.value;
	data = data.replace(new RegExp(' ', 'g'), ',');
	obj.value = data;
}

function validarRegexEmail(email) {
    var regex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    return regex.test(email);
}

function validateData(){
pasoActual = document.getElementById("pasoActual");

//console.log("validateData ", pasoActual.value);
	if(pasoActual.value === "1"){
		return validadorEmail();  
	}
	else{
		return validadorNumVerificacion();
	}
}


function validadorNumVerificacion() {
	//console.log("validadorNumVerificacion new ");
	numVerificacionLabel = document.getElementById("formPrincipal:numVerificacionLabel");
	numVerificacionInput = document.getElementById("formPrincipal:numVerificacion");
    numVerificacionValue = numVerificacionInput.value;
     
    if (numVerificacionValue === "") {
        emailConsultaOk = false;
    } else {
    		emailConsultaOk = true;
    }
    
    if (emailConsultaOk) {
    	PF('dialogLoader').show();
        return true;
        
    } else {
        numVerificacionInput.style = 'border: 1px solid #cd0a0a;';
        numVerificacionLabel.style = 'color: #cd0a0a;';
		mensajeErrorNroVerificacion(false);
    }
	return false;
}

function validadorEmail() {
	//console.log("validadorEmail new ");
    var msg = 'Por favor complete los campos faltantes.';

	emailConsultaLabel = document.getElementById("formPrincipal:emailConsultaLabel");
	emailConsultaInput = document.getElementById("formPrincipal:emailConsulta");
	emailConsultaValue = emailConsultaInput.value;

    if (emailConsultaValue === "") {
        emailConsultaOk = false;
    } else {
    	if (!validarRegexEmail(emailConsultaValue)){
    		msg = "El email está mal escrito";
    		emailConsultaOk = false;
    	}else{
    		emailConsultaOk = true;
    	}
    }
    
    
    if (emailConsultaOk) {
    	PF('dialogLoader').show();
        return true;
        
    } else {
        emailConsultaInput.style = 'border: 1px solid #cd0a0a;';
        emailConsultaLabel.style = 'color: #cd0a0a;';
        alert(msg);
    }
	return false;
}

function getNumVerificacion() { //Funciona OnKeyUp
    numVerificacionValue = numVerificacionInput.value;
    numVerificacionInput.style = 'border: 1px solid #a8a8a8; width: 100%!important;';
    numVerificacionLabel.style = 'color: #4f4f4f;';
}




function recargarConsultarPqrd(){
    permitir = 1;
    localStorage.setItem("AccesoURL",permitir);
    var consultar = window.location.href.split("/faces")[0] + "/faces/consultar.xhtml";
    location.href=consultar;
    localStorage.setItem("nroVerificacionConsultar","");
}

function mensajeError() {
    alert("Ha ocurrido un error inesperado");
}

function mensajeErrorCorrespondenciaNoExiste(correo) {
    PF('correspondenciaNoExiste').show();
    var elementos = document.querySelectorAll('#labelCorrespondencia');
    elementos.forEach(function(elem, index) {
    	elem.innerHTML = correo;
	});
}

function mensajeErrorMantenimiento(urlOrigen){
	alert("Sistema en mantenimiento, por favor haga su consulta más tarde");
	window.location=urlOrigen;
}

function mensajeCorreo(){
	alert("Hemos enviado un email con su número verificación");
}

function mensajeErrorDbg(errorDbg) {
    alert("Ha ocurrido un error inesperado: " + errorDbg);
}

function mensajeErrorNroVerificacion(conDosMinutos, url){
//console.log("conDosMinutos", conDosMinutos, url);
	if (conDosMinutos){
		mensajeDosMinutos(url);
	} else{
		alert(msgEscribaNroVerificacion);
	}
    
}

function mensajeDosMinutos(url){


var tiempo = 30; // tiempo en segundos

var enviarConsulta = document.getElementById("formPrincipal:EnviarConsulta");
var regresar = document.getElementById("formPrincipal:Regresar");

enviarConsulta.disabled = true;
regresar.disabled = true;

var label = document.getElementById("formPrincipal:timeLabel");
label.textContent = "Por favor espere " + tiempo + " segundos";
label.style.display = 'inline';

var intervalo = setInterval(() => {
    if (tiempo > 0) {
        tiempo--;
        label.textContent = "Por favor espere " + tiempo + " segundos";
    } else {
        clearInterval(intervalo);
        label.textContent = "Redirigiendo...";
        alert(msgEscribaNroVerificacion);
        window.location=url;
    }
}, 1000);


}

function hideLoader(){
	if (PF('dialogLoader')) {
		PF('dialogLoader').hide();
	}
}