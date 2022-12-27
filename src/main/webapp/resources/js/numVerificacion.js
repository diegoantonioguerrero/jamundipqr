function deshabilitaRetroceso() {
    window.location.hash = "no-back-button";
    window.location.hash = "Again-No-back-button"; //chrome
    window.onhashchange = function () {
        window.location.hash = "no-back-button";
    };
    window.history.forward(1);
    window.onunload = function () {
        null;
    }
}

function mostrarIngresado(){
    var gestionAcceso = localStorage.getItem("AccesoURL").toString();
    if (gestionAcceso === "1"){
        permitir = 0;
        localStorage.setItem("AccesoURL",permitir);
        document.getElementById("formPrincipal").style.display = "block";
    } else {
       document.getElementById("formPrincipal").style.display = "none";
       var origen = window.location.href.split("/faces")[0];
       location.href=origen;       
   }    
}

function accesoConsultarPqrd(){
    permitir = 1;
    localStorage.setItem("AccesoURL",permitir);
    var consultar = window.location.href.split("/faces")[0] + "/faces/consultar.xhtml";
    location.href=consultar;
}

function RegresarConsultarPqrd(){
    permitir = 1;
    localStorage.setItem("AccesoURL",permitir);
    var consultar = window.location.href.split("/faces")[0] + "/faces/consultar.xhtml";
    location.href=consultar;
    localStorage.setItem("nroVerificacionConsultar","");
}

var labelCheckBoxNotificaciones = document.getElementById("labelCheckboxNotificaciones");
var NotificacionesAceptadas = false;
var emailInput = document.getElementById("formPrincipal:emailInput");
var emailValue = "";
var emailInputLabel = document.getElementById("formPrincipal:emailInputLabel");

function checkbox(){
    NotificacionesAceptadas = PF('CheckBoxWidget').input.is(':checked');
    if(NotificacionesAceptadas){
        labelCheckBoxNotificaciones.style = 'color: #4f4f4f;';
    } else {
        labelCheckBoxNotificaciones.style = 'color: #cd0a0a;';        
    }
}

function getEmailInput() { //Funciona OnKeyUp
    emailValue = emailInput.value;
    emailInput.style = 'border: 1px solid #a8a8a8; width: 90%;';
    emailInputLabel.style = 'color: #4f4f4f;';
}

function validateEmail(email) {
    const re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(email);
}

function validatorCampos2() {
    //Email
    if (emailValue === "") {
        emailInput.style = 'border: 1px solid #cd0a0a; width: 90%;';
        emailInputLabel.style = 'color: #cd0a0a;';
        var emailOk = false;
    } else {
        emailOk = true;
    }
    
    if (!NotificacionesAceptadas){
        labelCheckBoxNotificaciones.style = 'color: #cd0a0a;';
    }
    
    
    if (NotificacionesAceptadas && emailOk) {
         if (!validateEmail(emailValue)) {
            emailInput.style = 'border: 1px solid #cd0a0a; width: 90%;';
            emailInputLabel.style = 'color: #cd0a0a;';
            var emailOk = false;
            alert('Favor ingrese un email válido')
        } else{
            document.getElementById("formPrincipal:labelEmailEnviado").innerHTML = "Se envió un email a " + emailValue + " con el número de verificación actualizado.";
            var element = document.getElementById("formPrincipal:enviarNroVerificacion");
            element.click();
        }
    } else {
        alert('Por favor complete los campos faltantes.');
    }        
}

function finalizacion() {
    PF('confirmarEnvio').hide;
    document.getElementById("formPrincipal:labelEmailEnviado").innerHTML = "Se envió un email a " + emailValue + " con el número de verificación actualizado.";
}

function cambiador() {
    document.getElementById("formPrincipal:labelEmailEnviado").innerHTML = "Se envió un email a " + emailValue + " con el número de verificación actualizado.";
    PF('confirmacionEmail').show();
}

