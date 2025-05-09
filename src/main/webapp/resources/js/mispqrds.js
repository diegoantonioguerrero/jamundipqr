/**
 * 
 */

var paginaActual = 0;
var tamanoPagina = 10;
var totalPaginas = 1;

function mostrarPagina(nuevaPagina) {
	if(PF('dialogLoader')){
		PF('dialogLoader').show();	
	}
	
	//console.log("mostrarPagina");
    var items = document.querySelectorAll(".registro-item");
    var total = items.length;

	//console.log("items", total);

    totalPaginas = Math.ceil(total / tamanoPagina);
	//console.log("totalPaginas", totalPaginas);
    if (nuevaPagina < 0) nuevaPagina = 0;
    if (nuevaPagina >= totalPaginas) nuevaPagina = totalPaginas - 1;

    paginaActual = nuevaPagina;

    // Oculta todos los registros
    items.forEach(item => item.style.display = "none");

    // Muestra solo los registros de esta página
    var inicio = paginaActual * tamanoPagina;
    var fin = Math.min(inicio + tamanoPagina, total);
    for (var i = inicio; i < fin; i++) {
        items[i].style.display = "block";
    }

    actualizarControles();
	if(PF('dialogLoader')){
		PF('dialogLoader').hide();
	}
	// Scroll arriba
    window.scrollTo({ top: 0, behavior: 'smooth' });
}

function cambiarPagina(cambio) {
    mostrarPagina(paginaActual + cambio);
	return false;
}

function irAPagina(valor) {
    mostrarPagina(parseInt(valor));
}

function actualizarControles() {
    // Llenar combo de páginas solo la primera vez
    var combo = document.getElementById("selectPagina");
    if (combo.options.length !== totalPaginas) {
        combo.innerHTML = "";
        for (let i = 0; i < totalPaginas; i++) {
            var opt = document.createElement("option");
            opt.value = i;
            opt.text = (i + 1);
            combo.appendChild(opt);
        }
    }

    // Actualiza combo y texto de total
    combo.value = paginaActual;
    document.getElementById("totalPaginasLabel").innerText = totalPaginas;
}

function descargaArchivo(tipo, id){
	if(PF('dialogLoader')){
		PF('dialogLoader').hide();	
	}
	
	var query;
	if(tipo === 'usuario'){
		query = '[title="' + id +'"]';
	}
	else{
		query = '[title="respuesta_' + id +'"]';
	}
	
	//console.log(query);
	
	var btn = document.querySelector(query);
	btn.click();
}

function mensajeArchivo(msg){
	if(PF('dialogLoader')){
		PF('dialogLoader').hide();	
	}
	alert(msg);
}

function iniciarTimerSesion(tiempo, url){

    /*var enviarConsulta = document.getElementById("formPrincipal:EnviarConsulta");
    var regresar = document.getElementById("formPrincipal:Regresar");

    enviarConsulta.disabled = true;
    regresar.disabled = true;
    */
    var label = document.getElementById("formPrincipal:timeLabel");
	if(label){
		label.textContent = "Tienes " + tiempo + " minutos para finalizar tu sesión de consulta";
		label.style.display = 'inline';	
	}
    
    var inicio = new Date();
    var intervalo = setInterval(() => {
    	
    	var fin = new Date();
    	var diffMs = fin - inicio;
    	var diffMin = Math.floor(diffMs / 60000);
    	var diffSec = Math.floor((diffMs % 60000) / 1000);
		var restaMin = tiempo - 1 - diffMin;
		var restaSec = 59 - diffSec;  
		if (diffMin < tiempo){
			var msg = "Tienes " + restaMin + ":" + restaSec.toString().padStart(2, '0') + " minutos para finalizar tu sesión de consulta";		
        	//console.log(msg);
			if(label){
            	label.textContent = msg;
			}
        
        } else {
            clearInterval(intervalo);
            //label.textContent = "Redirigiendo...";
            alert("El tiempo de sesión de consulta ha finalizado, muchas gracias");
            window.location=url;
        }
    	
    }, 1000);

 
    
    }