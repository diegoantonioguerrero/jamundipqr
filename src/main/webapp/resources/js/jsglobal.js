function deshabilitaRetroceso() {
	try{
	
	    window.location.hash = "no-back-button";
	    window.location.hash = "Again-No-back-button"; //chrome
	    window.onhashchange = function () {
	        
			try{
	        
	        	window.location.hash = "no-back-button";
	        
	        }
			catch(err){
				alert(err);
			}
	        
	    };
	    window.history.forward(1);
	    window.onunload = function () {
	        null;
	    }
	
	}
	catch(err){
		alert(err);
	}
}

function mostrarIngresado() {
	var accesoURL = localStorage.getItem("AccesoURL");
    //console.log("gestionAcceso", accesoURL);
    var gestionAcceso = (accesoURL !== undefined && accesoURL !== null) ? accesoURL.toString() : 0;
    
    if (gestionAcceso === "1") {
        permitir = 0;
        localStorage.setItem("AccesoURL", permitir);
        document.getElementById("formPrincipal").style.display = "block";
    } else {
    
        document.getElementById("formPrincipal").style.display = "none";
        var origen = window.location.href.split("/faces")[0];
        //alert("chao " + origen);
        location.href = origen;
    }
}

function allowAccess(){
	//alert("allowAccess ");
	var permitir = 1;
	localStorage.setItem("AccesoURL",permitir);
} 

function accesoEnvioPqrd() {
    permitir = 1;
    localStorage.setItem("AccesoURL",permitir);
}

var sizeLimit = document.getElementById("limitearhivosize")
? document.getElementById("limitearhivosize").textContent : "";

if(document.getElementById("formPrincipal:labellimitearchivosize")){
	document.getElementById("formPrincipal:labellimitearchivosize").innerHTML = "Tamaño máximo permitido: " + (sizeLimit / 1000000) + " MB";
}


var infoUser = document.getElementById("formPrincipal:infouser");
var infoPqrd = document.getElementById("formPrincipal:infoPqrd");
var infopersona = document.getElementById("formPrincipal:infopersona");
var pantallaBandera = false;

//Import InfoUser
var tipoPersona = document.getElementById("formPrincipal:tipoPersona");
var tipoPersonaLabel = document.getElementById("formPrincipal:tipoPersona_label");
var razonSocial = document.getElementById("formPrincipal:razonSocial");
var razonSocialValue = "";
var labelRazonSocial = document.getElementById("formPrincipal:labelrazonsocial");
var tipoID = document.getElementById("formPrincipal:tipoDocumento");
var tipoIDLabel = document.getElementById("formPrincipal:tipoDocumento_label");
var numID = document.getElementById("formPrincipal:numDocumento");
var numIDvalue = "";
var labelnumID = document.getElementById("formPrincipal:labelnumid");
var nombre1 = document.getElementById("formPrincipal:primerNombre");
var nombre2 = document.getElementById("formPrincipal:segundoNombre");
var nombre1Value = "";
var labelNombre1 = document.getElementById("formPrincipal:labelnombreuno");
var apellido1 = document.getElementById("formPrincipal:primerApellido");
var apellido2 = document.getElementById("formPrincipal:segundoApellido");
var apellido1Value = "";
var labelApellido1 = document.getElementById("formPrincipal:labelapellidouno");
var celular = document.getElementById("formPrincipal:numeroCelular");
var celularValue = "";
var labelCelular = document.getElementById("formPrincipal:labelcelular");
var email = document.getElementById("formPrincipal:correoElectronico");
var emailValue = "";
var labelEmail = document.getElementById("formPrincipal:labelemail");
var confirmEmail = document.getElementById("formPrincipal:confirmEmail");
var confirmEmailValue = "";
var confirmEmailLabel = document.getElementById("formPrincipal:confirmEmailLabel");
var confirmEmailOk = false;
var ciudad = document.getElementById("formPrincipal:ciudad");
var ciudadLabel = document.getElementById("formPrincipal:ciudad_label");
var direccion = document.getElementById("formPrincipal:direccion");
var direccionValue = "";
var labelDireccion = document.getElementById("formPrincipal:labeldireccion");
var infoUserOk = false;
var activities;

//Import InfoPQRD
var tipoDependencia = document.getElementById("formPrincipal:dependencia");
var tipoDependenciaLabel = document.getElementById("formPrincipal:dependencia_label");
var tipoRespuesta = document.getElementById("formPrincipal:tipoRespuesta");
var tipoRespuestaLabel = document.getElementById("formPrincipal:tipoRespuesta_label");
var tipoClasificacion = document.getElementById("formPrincipal:tipoClasificacion");
var tipoClasificacionLabel = document.getElementById("formPrincipal:tipoClasificacion_label");
var asuntoPqrd = document.getElementById("formPrincipal:asuntoPqrd");
var asuntoPqrdValue = "";
var labelAsunto = document.getElementById("formPrincipal:labelasunto");
var detallePqrd = document.getElementById("formPrincipal:textoPqrd");
var detallePqrdValue = "";
var labelDetalle = document.getElementById("formPrincipal:labeldetalle");
var labelCheckBoxNotificaciones = document.getElementById("labelCheckboxNotificaciones");
var NotificacionesAceptadas = false;
var tipoClasificacionOk = true;

function checkbox() {
    NotificacionesAceptadas = PF('CheckBoxWidget').input.is(':checked');
    if (NotificacionesAceptadas) {
        labelCheckBoxNotificaciones.style = 'color: #4f4f4f;';
    } else {
        labelCheckBoxNotificaciones.style = 'color: #cd0a0a;';
    }
}

function validator() {
	
	try{
		
		var fnChangeView = function(){
		
			try{
				PF('dialogLoader').cfg.onShow = null;
			
			    var infoUser = document.getElementById("formPrincipal:infouser");
			    activities = PF('tipoIdentificacion').getSelectedValue();
			    if (activities === "POR IDENTIFICACION") {
			    	document.getElementsByClassName("camposObligatorios")[0].style.display = 'inline-block';
			        infoUser.style.display = 'block';
			        infoPqrd.style.display = 'block';
			        document.getElementById("tiporespuestadiv").style.display = 'block';
			        document.getElementById("tipoclasificaciondiv").style.display = 'none';
			        document.getElementById("CheckboxDiv").style.display = 'block';
			        NotificacionesAceptadas = false;
			        pantallaBandera = true;
			        infoUserOk = false;
			        tipoClasificacionOk = true;
			    } else if (activities === "ANONIMO") {
			    	document.getElementsByClassName("camposObligatorios")[0].style.display = 'inline-block';
			        NotificacionesAceptadas = true;
			        document.getElementById("CheckboxDiv").style.display = 'none';       
			        infoUser.style.display = 'none';
			        infoPqrd.style.display = 'block';
			        numID.value = "";
			        nombre1.value = "";
			        nombre2.value = "";
			        apellido1.value = "";
			        apellido2.value = "";
			        celular.value = "";
			        email.value = "";
			        confirmEmail.value = "";
			        razonSocial.value = "";
			        direccion.value = "";
			        document.getElementById("formPrincipal:tipoPersona_input").selectedIndex = 0;
			        document.getElementById("formPrincipal:tipoPersona_label").innerHTML = "Seleccione una opción";
			        document.getElementById("formPrincipal:ciudad_input").selectedIndex = 0;
			        document.getElementById("formPrincipal:ciudad_label").innerHTML = "Seleccione una opción";
			        document.getElementById("formPrincipal:tipoDocumento_input").selectedIndex = 0;
			        document.getElementById("formPrincipal:tipoDocumento_label").innerHTML = "Seleccione una opción";
			        document.getElementById("formPrincipal:tipoRespuesta_input").selectedIndex = 0;
			        document.getElementById("formPrincipal:tipoRespuesta_label").innerHTML = "Seleccione una opción";
			        document.getElementById("tiporespuestadiv").style.display = 'none';
			        document.getElementById("tipoclasificaciondiv").style.display = 'none';
			        pantallaBandera = false;
			        infoUserOk = true;
			        confirmEmail = true;
			
			    } else {
			        infoUser.style.display = 'none';
			        infoPqrd.style.display = 'none';
			        document.getElementsByClassName("camposObligatorios")[0].style.display = 'none';
			        pantallaBandera = false;
			    }
			    
			    document.getElementById("formPrincipal:tipoPQR_input").selectedIndex = 0;
			    document.getElementById("formPrincipal:tipoPQR_label").innerHTML = "Seleccione una opción";
			    PF('tipoSolicitud').selectValue('');
				// limpia todo el panel de adjuntos
				document.getElementById("formPrincipal:adjuntos").innerHTML = "";
			}
			catch(err){
				PF('dialogLoader').cfg.onShow = null;
				hideLoaderDialog();
				alert(err);
			}
		}
		
		if (PF('dialogLoader')) {
			PF('dialogLoader').cfg.onShow = function() {
	  			fnChangeView();
			};
			
			PF('dialogLoader').show();
		}
		else{
			fnChangeView();
		}
	}
	catch(err){
		PF('dialogLoader').cfg.onShow = null;
		hideLoaderDialog();
		alert(err);
	}

}

function cargarTiposPQR(tipoIdentificacion) {
    // Obtener los tipos correspondientes del mapa
    var tiposDisponibles = tiposMap[tipoIdentificacion];
    
    if (tiposDisponibles && tiposDisponibles.length > 0) {
        
        // Accede al widget de PrimeFaces
var selectWidget = PF('tipoSolicitud');

// Limpia el dropdown
selectWidget.selectItems = [];
selectWidget.options.length = 0; // limpia la lista de opciones

// Asegúrate de que el menú esté limpio
selectWidget.input.val('');
selectWidget.label.text(''); // Limpia la etiqueta mostrada

// Agrega nuevos ítems
selectWidget.options.push({ label: 'Seleccione una opción', value: '' });

 
        
     
        
        
        
        // Agregar las nuevas opciones
        for (var i = 0; i < tiposDisponibles.length; i++) {
            var tipo = tiposDisponibles[i];
            /*
            var option = document.createElement('option');
            option.value = tipo.id;
            option.textContent = tipo.nombre;
            tipoPQRSelect.appendChild(option);
            */
            selectWidget.options.push({ label: tipo.nombre, value: tipo.id });
            
        }
        
        // Refresca el componente visual
selectWidget.panel.contents().empty(); // borra el contenido renderizado
// Renderiza de nuevo el panel visualmente
selectWidget.panel.html(''); // limpia el DOM de opciones
selectWidget.buildItems(selectWidget.selectItems); // reconstruye el HTML

    }
}

function validator2() {
    var labelPersona = document.getElementById("formPrincipal:labelpersona");
    var activities = PF('personaNatural').getSelectedValue();
    if (activities === "NATURAL") {
        infopersona.style.display = 'none';
        personaBandera = false;
        tipoPersona.style = 'border: 1px solid #a8a8a8; width: 90%;';
        tipoPersonaLabel.style = 'color: #313131;';
        labelPersona.style = 'color: #313131;';
        razonSocial.value = "";
    } else if (activities === "JURIDICA") {
        infopersona.style.display = 'block';
        personaBandera = true;
        tipoPersona.style = 'border: 1px solid #a8a8a8; width: 90%;';
        tipoPersonaLabel.style = 'color: #313131;';
        labelPersona.style = 'color: #313131;';
    } else {
        infopersona.style.display = 'none';
        personaBandera = false;
        tipoPersona.style = 'border: 1px solid #cd0a0a; width: 90%;';
        tipoPersonaLabel.style = 'color: #cd0a0a;';
        labelPersona.style = 'color: #cd0a0a;';
    }
}

function validadorUser() {
    var labelPersona = document.getElementById("formPrincipal:labelpersona");
    var labelTipoID = document.getElementById("formPrincipal:labeltipoid");
    var labelCiudad = document.getElementById("formPrincipal:labelciudad");
    var labelRespuesta = document.getElementById("formPrincipal:labelrespuesta");

    var tipoPersonaValue = PF('personaNatural').getSelectedValue();
    var tipoIDValue = PF('tipoDocumento').getSelectedValue();
    var ciudadValue = PF('ciudadWidget').getSelectedValue();
    var tipoRespuestaValue = PF('tipoRespuesta').getSelectedValue();

    //Tipo Persona y Razón Social
    if (tipoPersonaValue === "NATURAL") {
        tipoPersona.style = 'border: 1px solid #a8a8a8; width: 90%;';
        tipoPersonaLabel.style = 'color: #313131;';
        labelPersona.style = 'color: #313131;';
        tipoPersonaOk = true;
    } else if (tipoPersonaValue === "JURIDICA") {
        tipoPersona.style = 'border: 1px solid #a8a8a8; width: 90%;';
        tipoPersonaLabel.style = 'color: #313131;';
        labelPersona.style = 'color: #313131;';
        //RazonSocial
        if (razonSocialValue === "") {
            razonSocial.style = 'border: 1px solid #cd0a0a;';
            labelRazonSocial.style = 'color: #cd0a0a;';
            var tipoPersonaOk = false;
        } else if (!razonSocialValue.replace(/\s/g, '').length) {
            razonSocial.style = 'border: 1px solid #cd0a0a;';
            labelRazonSocial.style = 'color: #cd0a0a;';
            tipoPersonaOk = false;
        } else {
            tipoPersonaOk = true;
        }
    } else {
        tipoPersona.style = 'border: 1px solid #cd0a0a; width: 90%;';
        tipoPersonaLabel.style = 'color: #cd0a0a;';
        labelPersona.style = 'color: #cd0a0a;';
        tipoPersonaOk = false;
    }

    //Tipo ID
    if (tipoIDValue === "") {
        tipoID.style = 'border: 1px solid #cd0a0a; width: 90%;';
        tipoIDLabel.style = 'color: #cd0a0a;';
        labelTipoID.style = 'color: #cd0a0a;';
        var tipoIDOk = false;
    } else {
        tipoIDOk = true;
    }

    //NumeroID
    if (numIDvalue === "") {
        numID.style = 'border: 1px solid #cd0a0a;';
        labelnumID.style = 'color: #cd0a0a;';
        var numIDOk = false;
    } else {
        numIDOk = true;
    }

    //Primer Nombre
    if (nombre1Value === "") {
        nombre1.style = 'border: 1px solid #cd0a0a;  width: 90%;';
        labelNombre1.style = 'color: #cd0a0a;';
        var nombre1Ok = false;
    } else if (!nombre1Value.replace(/\s/g, '').length) {
        nombre1.style = 'border: 1px solid #cd0a0a;  width: 90%;';
        labelNombre1.style = 'color: #cd0a0a;';
        nombre1Ok = false;
    } else {
        nombre1Ok = true;
    }

    //Primer Apellido
    if (apellido1Value === "") {
        apellido1.style = 'border: 1px solid #cd0a0a;  width: 90%;';
        labelApellido1.style = 'color: #cd0a0a;';
        var apellido1Ok = false;
    } else if (!apellido1Value.replace(/\s/g, '').length) {
        apellido1.style = 'border: 1px solid #cd0a0a;  width: 90%;';
        labelApellido1.style = 'color: #cd0a0a;';
        apellido1Ok = false;
    } else {
        apellido1Ok = true;
    }
    //Celular
    if (celularValue === "") {
        celular.style = 'border: 1px solid #cd0a0a; width: 90%;';
        labelCelular.style = 'color: #cd0a0a;';
        var celularOk = false;
    } else {
        celularOk = true;
    }

    //Email
    if (!validateEmail(emailValue)) {
        email.style = 'border: 1px solid #cd0a0a; width: 90%;';
        labelEmail.style = 'color: #cd0a0a;';
        var emailOk = false;
        if (emailValue !== confirmEmailValue) {
            confirmEmail.style = 'border: 1px solid #cd0a0a; width: 90%;';
            confirmEmailLabel.style = 'color: #cd0a0a;';
            var emailOk = false;
            confirmEmailOk = false;
        }
    } else {
        if (emailValue !== confirmEmailValue) {
            confirmEmail.style = 'border: 1px solid #cd0a0a; width: 90%;';
            confirmEmailLabel.style = 'color: #cd0a0a;';
            var emailOk = false;
            confirmEmailOk = false;
        } else {
            emailOk = true;
            confirmEmailOk = true;
        }
    }

    //Tipo ID
    if (ciudadValue === "") {
        ciudad.style = 'border: 1px solid #cd0a0a; width: 90%;';
        ciudadLabel.style = 'color: #cd0a0a;';
        labelCiudad.style = 'color: #cd0a0a;';
        var ciudadOk = false;
    } else {
        ciudadOk = true;
    }

    //Dirección
    if (direccionValue === "") {
        direccion.style = 'border: 1px solid #cd0a0a; width: 90%;';
        labelDireccion.style = 'color: #cd0a0a;';
        var direccionOk = false;
    } else if (!direccionValue.replace(/\s/g, '').length) {
        direccion.style = 'border: 1px solid #cd0a0a; width: 90%;';
        labelDireccion.style = 'color: #cd0a0a;';
        direccionOk = false;
    } else {
        direccionOk = true;
    }

    //Tipo Respuesta
    if (tipoRespuestaValue === "") {
        tipoRespuesta.style = 'border: 1px solid #cd0a0a; width: 90%;';
        tipoRespuestaLabel.style = 'color: #cd0a0a;';
        labelRespuesta.style = 'color: #cd0a0a;';
        var tipoRespuestaOk = false;
    } else {
        tipoRespuestaOk = true;
    }

    if (NotificacionesAceptadas === false) {
        labelCheckBoxNotificaciones.style = 'color: #cd0a0a;';
    }

    if (tipoPersonaOk && tipoIDOk && numIDOk && nombre1Ok && apellido1Ok && celularOk && emailOk && ciudadOk && direccionOk && tipoRespuestaOk && NotificacionesAceptadas) {
        infoUserOk = true;
    } else {
        infoUserOk = false;
    }
}

function validateEmail(email) {
    const re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(email);
}

function getRazonSocial() { //Funciona OnKeyUp
    razonSocialValue = razonSocial.value;
    razonSocial.style = 'border: 1px solid #a8a8a8; width: 90%;';
    labelRazonSocial.style = 'color: #4f4f4f;';
}

function getIDInput() { //Funciona OnKeyUp
    numIDvalue = numID.value;
    numID.style = 'border: 1px solid #a8a8a8; width: 90%;';
    labelnumID.style = 'color: #4f4f4f;';
}

function getPrimerNombre() { //Funciona OnKeyUp
    nombre1Value = nombre1.value;
    nombre1.style = 'border: 1px solid #a8a8a8; width: 90%;';
    labelNombre1.style = 'color: #4f4f4f;';
}

function getPrimerApellido() { //Funciona OnKeyUp
    apellido1Value = apellido1.value;
    apellido1.style = 'border: 1px solid #a8a8a8; width: 90%;';
    labelApellido1.style = 'color: #4f4f4f;';
}

function getCelular() { //Funciona OnKeyUp
    celularValue = celular.value;
    celular.style = 'border: 1px solid #a8a8a8; width: 90%;';
    labelCelular.style = 'color: #4f4f4f;';
}

function getEmail() { //Funciona OnKeyUp
    emailValue = email.value;
    email.style = 'border: 1px solid #a8a8a8; width: 90%;';
    labelEmail.style = 'color: #4f4f4f;';
}

function getConfirmEmail() { //Funciona OnKeyUp
    confirmEmailValue = confirmEmail.value;
    confirmEmail.style = 'border: 1px solid #a8a8a8; width: 90%;';
    confirmEmailLabel.style = 'color: #4f4f4f;';
}

function getDireccion() { //Funciona OnKeyUp
    direccionValue = direccion.value;
    direccion.style = 'border: 1px solid #a8a8a8; width: 90%;';
    labelDireccion.style = 'color: #4f4f4f;';
}

function validatorTipoIdentificacion() {
    var tipoIDValue = PF('tipoDocumento').getSelectedValue();
    var labelTipoID = document.getElementById("formPrincipal:labeltipoid");
    if (tipoIDValue === "") {
        tipoID.style = 'border: 1px solid #cd0a0a; width: 90%';
        tipoIDLabel.style = 'color: #cd0a0a;';
        labelTipoID.style = 'color: #cd0a0a;';
    } else {
        tipoID.style = 'border: 1px solid #a8a8a8; width: 90%; color: %';
        tipoIDLabel.style = 'color: #313131;';
        labelTipoID.style = 'color: #313131;';
    }
}

function validatorCiudad() {
    var ciudadValue = PF('ciudadWidget').getSelectedValue();
    var labelCiudad = document.getElementById("formPrincipal:labelciudad");
    if (ciudadValue === "") {
        ciudad.style = 'border: 1px solid #cd0a0a; width: 90%';
        ciudadLabel.style = 'color: #cd0a0a;';
        labelCiudad.style = 'color: #cd0a0a;';
    } else {
        ciudad.style = 'border: 1px solid #a8a8a8; width: 90%; color: %';
        ciudadLabel.style = 'color: #313131;';
        labelCiudad.style = 'color: #313131;';
    }
}

function getAsuntoInput() { //Funciona OnKeyUp
    asuntoPqrdValue = asuntoPqrd.value;
    asuntoPqrd.style = 'border: 1px solid #a8a8a8;';
    labelAsunto.style = 'color: #4f4f4f;';
}

function getDetalleInput() { //Funciona OnKeyUp
    detallePqrdValue = detallePqrd.value;
    detallePqrd.style = 'border: 1px solid #a8a8a8;';
    labelDetalle.style = 'color: #4f4f4f;';
}

function validatorDependencia() {
    var tipoDependenciaValue = PF('tipoDependencia').getSelectedValue();
    var labelDependencia = document.getElementById("formPrincipal:labeldependencia");
    if (tipoDependenciaValue === "") {
        tipoDependencia.style = 'border: 1px solid #cd0a0a; width: 90%';
        tipoDependenciaLabel.style = 'color: #cd0a0a;';
        labelDependencia.style = 'color: #cd0a0a;';
    } else {
        tipoDependencia.style = 'border: 1px solid #a8a8a8; width: 90%; color: %';
        tipoDependenciaLabel.style = 'color: #313131;';
        labelDependencia.style = 'color: #313131;';
    }
}

function validatorTipoPQR() {
    var tipoPQRValue = PF('tipoSolicitud').getSelectedValue();
    var tipoPQRHolder = document.getElementById("tipoPQRHolder");
    if (tipoPQRValue === "") {
    	tipoPQRHolder.classList.add('invalid');
    } else {
    	tipoPQRHolder.classList.remove('invalid');
    }
    
    recargarAdjuntos();
    return true;
}

function validatorRespuesta() {
    var tipoRespuestaValue = PF('tipoRespuesta').getSelectedValue();
    var labelRespuesta = document.getElementById("formPrincipal:labelrespuesta");
    if (tipoRespuestaValue === "") {
        tipoRespuesta.style = 'border: 1px solid #cd0a0a; width: 90%';
        tipoRespuestaLabel.style = 'color: #cd0a0a;';
        labelRespuesta.style = 'color: #cd0a0a;';
    } else {
        tipoRespuesta.style = 'border: 1px solid #a8a8a8; width: 90%;';
        tipoRespuestaLabel.style = 'color: #313131;';
        labelRespuesta.style = 'color: #313131;';
    }
}

function validatorClasificacion() {
    var tipoClasificacionValue = PF('tipoClasificacion').getSelectedValue();
    var labelClasificacion = document.getElementById("formPrincipal:labelclasificacion");
    if (tipoClasificacionValue === "") {
        tipoClasificacion.style = 'border: 1px solid #cd0a0a; width: 90%';
        tipoClasificacionLabel.style = 'color: #cd0a0a;';
        labelClasificacion.style = 'color: #cd0a0a;';
    } else {
        tipoClasificacion.style = 'border: 1px solid #a8a8a8; width: 90%; color: %';
        tipoClasificacionLabel.style = 'color: #313131;';
        labelClasificacion.style = 'color: #313131;';
    }
}

function validatorCampos() {
    var labelDependencia = document.getElementById("formPrincipal:labeldependencia");
    var labelTipoPQR = document.getElementById("formPrincipal:labeltipopqr");
    //var labelClasificacion = document.getElementById("formPrincipal:labelclasificacion");

    var tipoDependenciaValue = PF('tipoDependencia').getSelectedValue();
    var tipoPQRValue = PF('tipoSolicitud').getSelectedValue();
    //var tipoClasificacionValue = PF('tipoClasificacion').getSelectedValue();

    if (pantallaBandera) {
        validadorUser();
    }

    //Tipo Depdendencia
    if (tipoDependenciaValue === "") {
        tipoDependencia.style = 'border: 1px solid #cd0a0a; width: 90%;';
        tipoDependenciaLabel.style = 'color: #cd0a0a;';
        labelDependencia.style = 'color: #cd0a0a;';
        var tipoDependenciaOk = false;
    } else {
        tipoDependenciaOk = true;
    }

	var tipoPQRHolder = document.getElementById("tipoPQRHolder");
    
    //Tipo PQR
    if (tipoPQRValue === "") {
        tipoPQRHolder.classList.add('invalid');
        var tipoPQROk = false;
    } else {
    	tipoPQRHolder.classList.remove('invalid');
        tipoPQROk = true;
    }
    

    //Tipo Clasificación
    /*if (tipoClasificacionValue === "" && activities === "ANONIMO") {
        tipoClasificacion.style = 'border: 1px solid #cd0a0a; width: 90%;';
        tipoClasificacionLabel.style = 'color: #cd0a0a;';
        labelClasificacion.style = 'color: #cd0a0a;';
        tipoClasificacionOk = false;
    } else {
        tipoClasificacionOk = true;
    }*/

    //Asunto
    if (asuntoPqrdValue === "") {
        asuntoPqrd.style = 'border: 1px solid #cd0a0a;';
        labelAsunto.style = 'color: #cd0a0a;';
        var asuntoOk = false;
    } else if (!asuntoPqrdValue.replace(/\s/g, '').length) {
        asuntoPqrd.style = 'border: 1px solid #cd0a0a;';
        labelAsunto.style = 'color: #cd0a0a;';
        asuntoOk = false;
    } else {
        asuntoOk = true;
    }

    //Detalle
    if (detallePqrdValue === "") {
        detallePqrd.style = 'border: 1px solid #cd0a0a;';
        labelDetalle.style = 'color: #cd0a0a;';
        var detalleOk = false;
    } else if (!detallePqrdValue.replace(/\s/g, '').length) {
        detallePqrd.style = 'border: 1px solid #cd0a0a;';
        labelDetalle.style = 'color: #cd0a0a;';
        detalleOk = false;
    } else {
        detalleOk = true;
    }

	var validarTamanioTotalOk = validarTamanioTotal();
	
	if (!validarTamanioTotalOk){
		return; 
	}

    if (tipoClasificacionOk && tipoPQROk && tipoDependenciaOk && asuntoOk && detalleOk && infoUserOk) {
        PF('confirmacionEnvio').show();
    } else {

        if (confirmEmailOk) {
            alert('Favor completar los campos faltantes.');
        } else {
        	if (activities === "ANONIMO"){
        		alert('Favor completar los campos faltantes.');
        	}
        	else{
        		alert('El email está mal escrito.');
        	}
            
        }
    }
}


var inputElement = document.getElementById("formPrincipal:inputdelfile_input");
var fileList;
var nameFile;
if(inputElement){
	inputElement.addEventListener("change", handleFiles, false);
}
function handleFiles() {
    fileList = this.files;
    var file = fileList[0];
    nameFile = file.name;
    var ext = getFileExtension1(nameFile);
    
    if (ext === undefined){
    	alert("El archivo adjunto debe poseer una extensión con los formatos admitidos.");
    	return false;
    }
    var extension = ext.toString().toLowerCase();

    var sizeLimit = document.getElementById("limitearhivosize").textContent;
    
    var sizeLimitMb = sizeLimit / 1000000;

    if (extension === "pdf" || extension === "txt" || extension === "docx" || extension === "jpg" || extension === "png" || extension === "jpeg" || extension === "gif" || extension === "doc" || extension === "xlsx" || extension === "xls" || extension === "csv" || extension === "zip" || extension === "rar") {
    	
    	if (file.size == 0){
	    		alert("El archivo adjunto está vacio, se detectó un tamaño de 0 bytes");
	    		return false;
		}
		
        if (file.size < sizeLimit) {
            document.getElementById("formPrincipal:labelfilename").innerHTML = "El archivo " + nameFile + " fue cargado";
            document.getElementById("divupload").style.display = 'none';
            document.getElementById("formPrincipal:buttondelete").style.display = 'block';
        } else {
            deleteFile();
            alert("El archivo adjunto no puede exceder el tamaño de " + sizeLimitMb + " MB");
        }
    } else {
        deleteFile();
        alert("El archivo adjunto debe ser .txt, .docx, .doc, .xls, .xlsx, .jpeg, .pdf, .gif, .png, .jpg, .zip, .rar");
    }
}

function deleteFile() {
    document.getElementById("formPrincipal:buttondelete").style.display = 'none';
    document.getElementById("divupload").style.display = 'block';
    document.getElementById("formPrincipal:labelfilename").innerHTML = "";
    fileList = null;
    nameFile = null;
    inputElement.value = null;
}


function reconfigureAdjuntosHandler() {

/*====borrrar cuando sea definitivo==*/
inputElement = document.getElementById("formPrincipal:inputdelfile_input");
if(inputElement){
	inputElement.addEventListener("change", handleFiles, false);
}
/*====end borrrar cuando sea definitivo==*/

	var elementos = document.querySelectorAll('.uploadHandler input[type="file"]');
	
	elementos.forEach(function(el, index) {
	    el.addEventListener("change", function(event) {
	        return handleFileMultiple(this, index);
	    }, false);
	});

	hideLoaderDialog();
}

var extensionesValidas = [
  "pdf", "txt", "docx", "jpg", "png", "jpeg",
  "gif", "doc", "xlsx", "xls", "csv", "zip", "rar"
];


function handleFileMultiple(el, index) {
	try{
	    fileList = el.files;
	    var file = fileList[0];
	    nameFile = file.name;
	
		var ext = getFileExtension1(nameFile);
	    
	    if (ext === undefined){
	    	alert("El archivo adjunto debe poseer una extensión con los formatos admitidos " + extensionesValidas.join(", .").replace(/^/, "."));
	    	return false;
	    }
	    
	    var extension = ext.toString().toLowerCase();
    
	    var sizeLimit = document.getElementById("limitearhivosize").textContent;
	    var sizeLimitMb = sizeLimit / 1000000;
	
	    if (extensionesValidas.includes(extension)) {
	    	if (file.size == 0){
	    		alert("El archivo adjunto está vacio, se detectó un tamaño de 0 bytes");
	    		return false;
	    	}
	    	
	        if (file.size < sizeLimit) {
				var fileNameLabel = document.getElementById("formPrincipal:filenameLabel_" + index).innerHTML;
	            document.getElementById("formPrincipal:labelfilename_" + index).innerHTML = "El archivo " + fileNameLabel + " fue cargado";
	            document.getElementById("divupload_" + index).style.display = 'none';
	            document.getElementById("formPrincipal:buttondelete_" + index).style.display = 'block';
	            return true;
	        } else {
	            deleteFile();
	            alert("El archivo adjunto no puede exceder el tamaño de " + sizeLimitMb + " MB");
	            return false;
	        }
	    } else {
	        deleteFileMultiple(index);
	        alert("El archivo adjunto debe ser de tipo: " + extensionesValidas.join(", .").replace(/^/, "."));
	        return false;
	    }
	}
	catch(err){
		alert(err);
		return false;
	}	    
}

function validarTamanioTotal(){

	var elementos = document.querySelectorAll('.uploadHandler input[type="file"]');
	var sizeLimitTotal = document.getElementById("limitearhivosize").textContent;
	var sizeLimitMb = sizeLimitTotal / 1000000;
	var sumaArchivos = 0;
	var requiredFiles = "";
	var listaFiles = [];  // lista vacía
	var nombreRepetido = "";

	elementos.forEach(function(el, index) {
	    var fileList = el.files;
	    var required = document.getElementById("formPrincipal:filenameRequired_" + index).value + "";
    
	    if (fileList.length > 0){
		    var file = fileList[0];
		    
		    if (listaFiles.includes(file.name.toLowerCase())) {
	  			nombreRepetido = file.name;
			}else{
				listaFiles.push(file.name.toLowerCase());
			}
		    
		    sumaArchivos += file.size;
	    }
	    else if (required === "1"){
	    	var fileName = document.getElementById("formPrincipal:filenameLabel_" + index).innerHTML;
	    	requiredFiles = "Es obligatorio Adjuntar " + fileName;
	    }
	});
	
	
	if (requiredFiles !== "") {
		alert(requiredFiles);
		return false;		
	}
	
	if (nombreRepetido !== "") {
		alert("No se permite adjuntar dos archivos con el mismo nombre: " + nombreRepetido);
		return false;		
	}
		
	var invalidSize = sumaArchivos > sizeLimit;
	
	
	if (invalidSize) {
		alert("El/los archivo(s) adjunto(s) no puede(n) exceder el tamaño de " + sizeLimitMb + " MB en total");
	}
	
	
	return !invalidSize;

}

function deleteFileMultiple(index) {
    document.getElementById("formPrincipal:buttondelete_" + index).style.display = 'none';
    document.getElementById("divupload_" + index).style.display = 'block';
    document.getElementById("formPrincipal:labelfilename_" + index).innerHTML = "";
    //fileList = null;
    //nameFile = null;
    var inputFile = document.getElementById("formPrincipal:inputdelfile_" + index + "_input")
    inputFile.value = null;
}

function getFileExtension1(filename) {
    return (/[.]/.exec(filename)) ? /[^.]+$/.exec(filename)[0] : undefined;
}

if(nombre1){
	addPlaceHolderCustom(nombre1)
}
if(nombre2){
	addPlaceHolderCustom(nombre2)
}
if(apellido1){
	addPlaceHolderCustom(apellido1)
}
if(apellido2){
	addPlaceHolderCustom(apellido2)
}
if(numID){
	addPlaceHolderCustom(numID)
}
if(celular){
	addPlaceHolderCustom(celular)
}
function addPlaceHolderCustom(object){
		
	object.addEventListener('focus', (event) => {
		if (!object.dataset.place && object.placeholder ){
			object.dataset.place = event.target.placeholder;
		}
		event.target.placeholder = '';
		//console.log(object, event.target.placeholder);
	});

	object.addEventListener('blur', (event) => {
		if (object.dataset.place){
			event.target.placeholder = object.dataset.place;
		}
		//console.log(object, event.target.placeholder);

	});
}

function displayError(arguments){

console.error('Error al ejecutar remoteCommand:', arguments);
hideLoaderDialog(); 
alert('Ocurrió un error al ejecutar la acción. Intente nuevamente.');

}


function showLoaderDialog(){
	if (PF('dialogLoader')) {
		PF('dialogLoader').show();
	}
}

function hideLoaderDialog(){
	
	if (PF('dialogLoader')) {
		PF('dialogLoader').hide();
	}
}