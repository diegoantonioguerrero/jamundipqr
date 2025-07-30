var isOpera;
var isFirefox;
var isSafari;
var isIE;
var isEdge;
var isChrome;
var isEdgeChromium;
var isBlink;

function detectBrowser(){

	try
	{
	 
		// Opera 8.0+
		isOpera = (!!window.opr && !!opr.addons) || !!window.opera || navigator.userAgent.indexOf(' OPR/') >= 0;
		
		// Firefox 1.0+
		isFirefox = typeof InstallTrigger !== 'undefined';
		
		// Safari 3.0+ "[object HTMLElementConstructor]" 
		isSafari = /constructor/i.test(window.HTMLElement) || (function (p) {
		    return p.toString() === "[object SafariRemoteNotification]";
		})(!window['safari'] || (typeof safari !== 'undefined' && safari.pushNotification));
		
		// Internet Explorer 6-11
		isIE = /*@cc_on!@*/false || !!document.documentMode;
		
		// Edge 20+
		isEdge = !isIE && !!window.StyleMedia;
		
		// Chrome 1 - 79
		isChrome = !!window.chrome && (!!window.chrome.webstore || !!window.chrome.runtime);
		
		// Edge (based on chromium) detection
		isEdgeChromium = isChrome && (navigator.userAgent.indexOf("Edg") != -1);
		
		// Blink engine detection
		isBlink = (isChrome || isOpera) && !!window.CSS;
		
		/*
		var output = 'Detecting browsers by ducktyping:<hr>';
		 output += 'isFirefox: ' + isFirefox + '<br>';
		 output += 'isChrome: ' + isChrome + '<br>';
		 output += 'isSafari: ' + isSafari + '<br>';
		 output += 'isOpera: ' + isOpera + '<br>';
		 output += 'isIE: ' + isIE + '<br>';
		 output += 'isEdge: ' + isEdge + '<br>';
		 output += 'isEdgeChromium: ' + isEdgeChromium + '<br>';
		 output += 'isBlink: ' + isBlink + '<br>';
		 console.log(output);
		 */
	
	}
	catch(err){
		alert(err);
	}

}

detectBrowser();
ie();

function ie() {
	try
	{
	    if (isIE) {
	        document.getElementById("formIndex").style.display = "none";
	        document.getElementById("formIE").style.display = "block";
	    } else{
			if(document.getElementById("formIndex")){
				document.getElementById("formIndex").style.display = "block";
			}
			if(document.getElementById("formIE")){
				document.getElementById("formIE").style.display = "none";
			}
	    }
    
    }
	catch(err){
		alert(err);
	}
}