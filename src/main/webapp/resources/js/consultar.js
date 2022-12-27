function dialogWidth(){
    var htmlTag = document.getElementById('dialogoEnvio');
    htmlTag.style.width = Math.floor(window.innerWidth*0.8)+"px";
    var dialogTitle = document.getElementById('formPrincipal:dialogoEnvio_title');
    var centerLong = Math.floor(window.innerWidth*0.8)/2-15;
    var pad = centerLong.toString()
    dialogTitle.style.padding = "0 0 0 " +pad+ "px";
}

function accesoNumVerificacion(){
    permitir = 1;
    localStorage.setItem("AccesoURL",permitir);
    localStorage.setItem("nroRadicadoConsultar",numRadicadoValue);
    localStorage.setItem("nroVerificacionConsultar",numVerificacionValue);
    var consultar = window.location.href.split("/faces")[0] + "/faces/numverificacion.xhtml";
    location.href=consultar;
}

function inicializarConsultar(){
    numRadicadoInput.value = localStorage.getItem("nroRadicadoConsultar").toString();
    numRadicadoValue = numRadicadoInput.value;
    numVerificacionInput.value = localStorage.getItem("nroVerificacionConsultar").toString();
    numVerificacionValue = numVerificacionInput.value;
    localStorage.setItem("nroRadicadoConsultar","");
    localStorage.setItem("nroVerificacionConsultar","");
}

var numRadicadoInput = document.getElementById("formPrincipal:radicadoConsulta");
var numRadicadoLabel = document.getElementById("formPrincipal:radicadoConsultaLabel");
var numRadicadoOk = false;
var numVerificacionInput = document.getElementById("formPrincipal:numVerificacion");
var numVerificacionLabel = document.getElementById("formPrincipal:numVerificacionLabel");
var numVerificacionOk = false;

function getNumRadicado() { //Funciona OnKeyUp
    numRadicadoValue = numRadicadoInput.value;
    numRadicadoInput.style = 'border: 1px solid #a8a8a8; width: 100%!important;';
    numRadicadoLabel.style = 'color: #4f4f4f;';
}
function removerEspacios(obj) { //Funciona OnKeyUp
	var data = obj.value;
	data = data.replace(new RegExp(' ', 'g'), ',');
	obj.value = data;
}



function getNumVerificacion() { //Funciona OnKeyUp
    numVerificacionValue = numVerificacionInput.value;
    numVerificacionInput.style = 'border: 1px solid #a8a8a8; width: 100%!important;';
    numVerificacionLabel.style = 'color: #4f4f4f;';
}

function validadorConsulta() {
    if (numRadicadoValue === "") {
        numRadicadoInput.style = 'border: 1px solid #cd0a0a;';
        numRadicadoLabel.style = 'color: #cd0a0a;';
        numRadicadoOk = false;
    } else {
        numRadicadoOk = true;
    }
    
    if (numVerificacionValue === "") {
        numVerificacionInput.style = 'border: 1px solid #cd0a0a;';
        numVerificacionLabel.style = 'color: #cd0a0a;';
        numVerificacionOk = false;
    } else {
        numVerificacionOk = true;
    }
    
    if (numVerificacionOk && numRadicadoOk) {
        var element = document.getElementById("formPrincipal:EnviarConsulta");
        element.click();
        
    } else {
        alert('Por favor complete los campos faltantes.');
    }
}

function recargarConsultarPqrd(){
    permitir = 1;
    localStorage.setItem("AccesoURL",permitir);
    var consultar = window.location.href.split("/faces")[0] + "/faces/consultar.xhtml";
    location.href=consultar;
    localStorage.setItem("nroVerificacionConsultar","");
}

function mensajeError() {
    alert("Número de radicación o verificación errados");
}