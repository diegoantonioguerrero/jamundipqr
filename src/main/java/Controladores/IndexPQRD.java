package Controladores;

import Utilidades.Util;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean
@RequestScoped
public class IndexPQRD {
   private static String path_background_image;
   private static String urlOrigen;
   private static String fondoHeader;
   private static String fondoFooter;
   private static String colorFondoBotones;
   private static String colorLetraBotones;
   private static String fuenteTiulos;
   private static String fuenteEtiquetas;
   private static String fuenteContenido;

   public IndexPQRD() {
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
      } catch (Exception var2) {
         Logger.getLogger(IndexPQRD.class.getName()).log(Level.SEVERE, (String)null, var2);
      }
      /*
      try {
          Util.sendMailTest();
       } catch (Exception var19) {
          System.out.println(var19);
          Logger.getLogger(FormularPQRD.class.getName()).log(Level.SEVERE, (String)null, var19);
       }
       */

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
}
