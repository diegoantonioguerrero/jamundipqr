package Controladores;

import Utilidades.Util;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean
@RequestScoped
public class EnvioPQRD {
   private static String path_background_image;
   private static String urlOrigen;
   private static String fondoHeader;
   private static String fondoFooter;
   private static String colorFondoBotones;
   private static String colorLetraBotones;
   private static String fuenteTiulos;
   private static String fuenteEtiquetas;
   private static String fuenteContenido;
   private static String nroRadicacion;
   private static boolean mostrarMensaje;
   private static String fechaHoraRadicadoFormat; 
   private static String numeroVerificacionStatic; 
   private static String verificacionMsgStatic; 

   private boolean mostrar;
   private String nroRadicado;
   private String fechaHoraRadicado;
   private String numeroVerificacion;
   private String verificacionMsg;

   public EnvioPQRD() {
      try {
         path_background_image = Util.getProperties("imagenFondo");
         fondoHeader = Util.getProperties("imagenFondoHeader");
         fondoFooter = Util.getProperties("imagenFondoFooter");
         colorFondoBotones = Util.getProperties("colorFondoBotones");
         colorLetraBotones = Util.getProperties("colorLetraBotones");
         fuenteTiulos = Util.getProperties("fuenteTiulos");
         fuenteEtiquetas = Util.getProperties("fuenteEtiquetas");
         fuenteContenido = Util.getProperties("fuenteContenido");
         urlOrigen = Util.getProperties("linkOrigen");
         this.nroRadicado = nroRadicacion;
         this.mostrar = mostrarMensaje;
   	  	 this.fechaHoraRadicado = fechaHoraRadicadoFormat;
   	  	 this.setNumeroVerificacion(numeroVerificacionStatic);
   	  	 this.setVerificacionMsg(verificacionMsgStatic);

      } catch (Exception var2) {
         Logger.getLogger(ConsultarPQRD.class.getName()).log(Level.SEVERE, (String)null, var2);
      }

   }

   public EnvioPQRD(String nroRadicacion, boolean mostrar, String fechaHoraRadicadoFormat, String numeroVerificacion) {
      EnvioPQRD.nroRadicacion = nroRadicacion;
      EnvioPQRD.fechaHoraRadicadoFormat = fechaHoraRadicadoFormat;
      EnvioPQRD.mostrarMensaje = mostrar;
      EnvioPQRD.numeroVerificacionStatic = numeroVerificacion;
      EnvioPQRD.verificacionMsgStatic = "IMPORTANTE: Guarde muy bien este número de radicado " 
      + nroRadicacion 
      + " y su número de verificación " 
      + numeroVerificacion 
      + " pues será la única manera en que usted podrá consultar su respuesta.";
   }

   public boolean isMostrar() {
      return this.mostrar;
   }

   public String getNroRadicado() {
      return this.nroRadicado;
   }

   public String getPath_background_image() {
      return path_background_image;
   }

   public String getFondoHeader() {
      return fondoHeader;
   }

   public String getFondoFooter() {
      return fondoFooter;
   }

   public String getColorFondoBotones() {
      return colorFondoBotones;
   }

   public String getColorLetraBotones() {
      return colorLetraBotones;
   }

   public String getFuenteTiulos() {
      return fuenteTiulos;
   }

   public String getFuenteEtiquetas() {
      return fuenteEtiquetas;
   }

   public String getFuenteContenido() {
      return fuenteContenido;
   }

   public String getUrlOrigen() {
      return urlOrigen;
   }

public String getFechaHoraRadicado() {
	return fechaHoraRadicado;
}

public void setFechaHoraRadicado(String fechaHoraRadicado) {
	this.fechaHoraRadicado = fechaHoraRadicado;
}

public String getNumeroVerificacion() {
	return numeroVerificacion;
}

public void setNumeroVerificacion(String numeroVerificacion) {
	this.numeroVerificacion = numeroVerificacion;
}

public String getVerificacionMsg() {
	return verificacionMsg;
}

public void setVerificacionMsg(String verificacionMsg) {
	this.verificacionMsg = verificacionMsg;
}
}
