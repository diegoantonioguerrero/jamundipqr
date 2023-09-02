package Controladores;

import Objetos.ComunicacionPQRD;
import Utilidades.Util;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Table;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CreatePDF {
   public void crearPDF(ComunicacionPQRD comunicacionPQRD) throws IOException, Exception {
      String extension = ".pdf";
      //File archivo = new File("temporalFile");
      //String path = archivo.getAbsolutePath();
      //path = path.replace("temporalFile", "webapps\\");
      String path = Util.getPath();
      path = path.replace("bin\\temporalFile", "webapps\\");
      String nombreAplicacion = Util.getProperties("nombreAplicacion");
      if(!path.toLowerCase().endsWith(nombreAplicacion)) {
    	  path = path + "\\" + nombreAplicacion;  
      }
      
      String pathFont = path;
      path = path + "\\resources\\radicados\\";
      String nombre_carpeta = comunicacionPQRD.getNro_radicacion();
      File dir = new File(path + nombre_carpeta);
      dir.mkdirs();
      path = path + nombre_carpeta + "\\";
      // archivo.delete();
      String dest = path + comunicacionPQRD.getNro_radicacion() + " - Remitente" + extension;
      PdfWriter writer = new PdfWriter(dest);
      PdfDocument pdf = new PdfDocument(writer);
      Document document = new Document(pdf, PageSize.LETTER);
      Throwable var12 = null;

      int margin;
      try {
         document.setMargins(30.0F, 20.0F, 20.0F, 30.0F);
         float[] pointColumnWidths = new float[]{120.0F, 440.0F};
         int normalSize = 10;
         margin = normalSize + 6;
         String pathVerdana = pathFont + "\\resources\\font\\verdana.ttf";
         String pathBold = pathFont + "\\resources\\font\\verdanaBold.ttf";
         System.err.println(pathVerdana);
         System.err.println(pathBold);
         PdfFont normal = PdfFontFactory.createFont(pathVerdana, "Identity-H", true);
         PdfFont bold = PdfFontFactory.createFont(pathBold, "Identity-H", true);
         Table table1 = new Table(pointColumnWidths);
         String txt1 = "N\u00famero Radicado: ";
         String radicado = comunicacionPQRD.getNro_radicacion();
         table1.addCell((Cell)((Cell)((Cell)(new Cell()).add(txt1).setBorder(Border.NO_BORDER)).setFont(bold)).setFontSize((float)normalSize));
         table1.addCell((Cell)((Cell)((Cell)(new Cell()).add(radicado).setBorder(Border.NO_BORDER)).setFont(normal)).setFontSize((float)normalSize));
         String txt2 = "Fecha de Creaci\u00f3n: ";
         String fecha = (new SimpleDateFormat("yyyy/MM/dd")).format(new Date());
         fecha = fecha + " " + comunicacionPQRD.getHoraRealizacion();
         table1.addCell((Cell)((Cell)((Cell)(new Cell()).add(txt2).setBorder(Border.NO_BORDER)).setFont(bold)).setFontSize((float)normalSize));
         table1.addCell((Cell)((Cell)((Cell)(new Cell()).add(fecha).setBorder(Border.NO_BORDER)).setFont(normal)).setFontSize((float)normalSize));
         table1.setMarginBottom((float)margin);
         document.add(table1);
         Table table2 = new Table(pointColumnWidths);
         String txt3 = "Tipo: ";
         String txtTipoIdentificacion = comunicacionPQRD.getTipo_identificacion();
         table2.addCell((Cell)((Cell)((Cell)(new Cell()).add(txt3).setBorder(Border.NO_BORDER)).setFont(bold)).setFontSize((float)normalSize));
         table2.addCell((Cell)((Cell)((Cell)(new Cell()).add(txtTipoIdentificacion).setBorder(Border.NO_BORDER)).setFont(normal)).setFontSize((float)normalSize));
         table2.setMarginBottom((float)margin);
         document.add(table2);
         Table table3 = new Table(pointColumnWidths);
         String txt4 = "Nombre: ";
         String txtNombre1;
         if (comunicacionPQRD.getPrimer_nombre() == null) {
            txtNombre1 = "";
         } else {
            txtNombre1 = comunicacionPQRD.getPrimer_nombre();
         }

         String txtNombre2;
         if (comunicacionPQRD.getSegundo_nombre() == null) {
            txtNombre2 = "";
         } else {
            txtNombre2 = comunicacionPQRD.getSegundo_nombre();
         }

         String txtApellido1;
         if (comunicacionPQRD.getPrimer_apellido() == null) {
            txtApellido1 = "";
         } else {
            txtApellido1 = comunicacionPQRD.getPrimer_apellido();
         }

         String txtApellido2;
         if (comunicacionPQRD.getSegundo_apellido() == null) {
            txtApellido2 = "";
         } else {
            txtApellido2 = comunicacionPQRD.getSegundo_apellido();
         }

         table3.addCell((Cell)((Cell)((Cell)(new Cell()).add(txt4).setBorder(Border.NO_BORDER)).setFont(bold)).setFontSize((float)normalSize));
         table3.addCell((Cell)((Cell)((Cell)(new Cell()).add(txtNombre1 + " " + txtNombre2 + " " + txtApellido1 + " " + txtApellido2).setBorder(Border.NO_BORDER)).setFont(normal)).setFontSize((float)normalSize));
         String txt5 = "Raz\u00f3n Social: ";
         String txtRazonSocial;
         if (comunicacionPQRD.getRazon_social() == null) {
            txtRazonSocial = "";
         } else {
            txtRazonSocial = comunicacionPQRD.getRazon_social();
         }

         table3.addCell((Cell)((Cell)((Cell)(new Cell()).add(txt5).setBorder(Border.NO_BORDER)).setFont(bold)).setFontSize((float)normalSize));
         table3.addCell((Cell)((Cell)((Cell)(new Cell()).add(txtRazonSocial).setBorder(Border.NO_BORDER)).setFont(normal)).setFontSize((float)normalSize));
         String txt6 = "Identficaci\u00f3n: ";
         String txtTipoDocumento;
         if (comunicacionPQRD.getNombreTipoDocumento() == null) {
            txtTipoDocumento = "";
         } else {
            txtTipoDocumento = comunicacionPQRD.getNombreTipoDocumento();
         }

         String txtNumDocumento;
         if (comunicacionPQRD.getNro_identificacion() == null) {
            txtNumDocumento = "";
         } else {
            txtNumDocumento = comunicacionPQRD.getNro_identificacion();
         }

         table3.addCell((Cell)((Cell)((Cell)(new Cell()).add(txt6).setBorder(Border.NO_BORDER)).setFont(bold)).setFontSize((float)normalSize));
         table3.addCell((Cell)((Cell)((Cell)(new Cell()).add(txtTipoDocumento + " " + txtNumDocumento).setBorder(Border.NO_BORDER)).setFont(normal)).setFontSize((float)normalSize));
         table3.setMarginBottom((float)margin);
         document.add(table3);
         Table table4 = new Table(pointColumnWidths);
         String txt7 = "Celular: ";
         String txtCelular;
         if (comunicacionPQRD.getCelular() == null) {
            txtCelular = "";
         } else {
            txtCelular = comunicacionPQRD.getCelular();
         }

         table4.addCell((Cell)((Cell)((Cell)(new Cell()).add(txt7).setBorder(Border.NO_BORDER)).setFont(bold)).setFontSize((float)normalSize));
         table4.addCell((Cell)((Cell)((Cell)(new Cell()).add(txtCelular).setBorder(Border.NO_BORDER)).setFont(normal)).setFontSize((float)normalSize));
         String txt8 = "Direcci\u00f3n: ";
         String txtDir;
         if (comunicacionPQRD.getDireccion() == null) {
            txtDir = "";
         } else {
            txtDir = comunicacionPQRD.getDireccion();
         }

         table4.addCell((Cell)((Cell)((Cell)(new Cell()).add(txt8).setBorder(Border.NO_BORDER)).setFont(bold)).setFontSize((float)normalSize));
         table4.addCell((Cell)((Cell)((Cell)(new Cell()).add(txtDir).setBorder(Border.NO_BORDER)).setFont(normal)).setFontSize((float)normalSize));
         String txt9 = "Ciudad: ";
         String txtCiudad;
         if (comunicacionPQRD.getNombre_ciudad() == null) {
            txtCiudad = "";
         } else {
            txtCiudad = comunicacionPQRD.getNombre_ciudad();
         }

         table4.addCell((Cell)((Cell)((Cell)(new Cell()).add(txt9).setBorder(Border.NO_BORDER)).setFont(bold)).setFontSize((float)normalSize));
         table4.addCell((Cell)((Cell)((Cell)(new Cell()).add(txtCiudad).setBorder(Border.NO_BORDER)).setFont(normal)).setFontSize((float)normalSize));
         table4.setMarginBottom((float)margin);
         document.add(table4);
         Table table5 = new Table(pointColumnWidths);
         String txt10 = "Email: ";
         String txtCorreo;
         if (comunicacionPQRD.getEmail() != null && !comunicacionPQRD.getEmail().isEmpty()) {
            txtCorreo = comunicacionPQRD.getEmail() + "\nEl usuario acept\u00f3 notificaciones por medios electr\u00f3nicos.";
         } else {
            txtCorreo = "";
         }

         table5.addCell((Cell)((Cell)((Cell)(new Cell()).add(txt10).setBorder(Border.NO_BORDER)).setFont(bold)).setFontSize((float)normalSize));
         table5.addCell((Cell)((Cell)((Cell)(new Cell()).add(txtCorreo).setBorder(Border.NO_BORDER)).setFont(normal)).setFontSize((float)normalSize));
         table5.setMarginBottom((float)margin);
         document.add(table5);
         Table table6 = new Table(pointColumnWidths);
         String txt11 = "Tipo PQRD:  ";
         String txtTipo = comunicacionPQRD.getNombreTipoPQRD();
         table6.addCell((Cell)((Cell)((Cell)(new Cell()).add(txt11).setBorder(Border.NO_BORDER)).setFont(bold)).setFontSize((float)normalSize));
         table6.addCell((Cell)((Cell)((Cell)(new Cell()).add(txtTipo).setBorder(Border.NO_BORDER)).setFont(normal)).setFontSize((float)normalSize));
         String txt12 = "Tipo Respuesta: ";
         String txtRespuesta = comunicacionPQRD.getNombreTipoRespuesta() == null ? "" : comunicacionPQRD.getNombreTipoRespuesta();
         table6.addCell((Cell)((Cell)((Cell)(new Cell()).add(txt12).setBorder(Border.NO_BORDER)).setFont(bold)).setFontSize((float)normalSize));
         table6.addCell((Cell)((Cell)((Cell)(new Cell()).add(txtRespuesta).setBorder(Border.NO_BORDER)).setFont(normal)).setFontSize((float)normalSize));
         String txt13 = "Dependencia: ";
         String txtDependencia = comunicacionPQRD.getNombreDependencia();
         table6.addCell((Cell)((Cell)((Cell)(new Cell()).add(txt13).setBorder(Border.NO_BORDER)).setFont(bold)).setFontSize((float)normalSize));
         table6.addCell((Cell)((Cell)((Cell)(new Cell()).add(txtDependencia).setBorder(Border.NO_BORDER)).setFont(normal)).setFontSize((float)normalSize));
         String txt14 = "Archivo Adjunto:";
         String txtArchivoAdjunto;
         if (comunicacionPQRD.getFileAdjunto() == null) {
            txtArchivoAdjunto = "NO";
         } else {
            txtArchivoAdjunto = "SI";
         }

         table6.addCell((Cell)((Cell)((Cell)(new Cell()).add(txt14).setBorder(Border.NO_BORDER)).setFont(bold)).setFontSize((float)normalSize));
         table6.addCell((Cell)((Cell)((Cell)(new Cell()).add(txtArchivoAdjunto).setBorder(Border.NO_BORDER)).setFont(normal)).setFontSize((float)normalSize));
         String txt15 = "Asunto:  ";
         String txtAsunto;
         if (comunicacionPQRD.getAsunto() == null) {
            txtAsunto = "";
         } else {
            txtAsunto = comunicacionPQRD.getAsunto();
         }

         table6.addCell((Cell)((Cell)((Cell)(new Cell()).add(txt15).setBorder(Border.NO_BORDER)).setFont(bold)).setFontSize((float)normalSize));
         table6.addCell((Cell)((Cell)((Cell)(new Cell()).add(txtAsunto).setBorder(Border.NO_BORDER)).setFont(normal)).setFontSize((float)normalSize));
         String txt16 = "Detalle:  ";
         String txtDetalle;
         if (comunicacionPQRD.getDetalle() == null) {
            txtDetalle = "";
         } else {
            txtDetalle = comunicacionPQRD.getDetalle();
         }

         table6.addCell((Cell)((Cell)((Cell)(new Cell()).add(txt16).setBorder(Border.NO_BORDER)).setFont(bold)).setFontSize((float)normalSize));
         table6.addCell((Cell)((Cell)((Cell)(new Cell()).add(txtDetalle).setBorder(Border.NO_BORDER)).setFont(normal)).setFontSize((float)normalSize));
         document.add(table6);
      } catch (Throwable var88) {
         var12 = var88;
         throw var88;
      } finally {
         if (document != null) {
            if (var12 != null) {
               try {
                  document.close();
               } catch (Throwable var83) {
                  var12.addSuppressed(var83);
               }
            } else {
               document.close();
            }
         }

      }

      if (comunicacionPQRD.getFileAdjunto() != null) {
         try {
            InputStream inputStream1 = new FileInputStream(comunicacionPQRD.getFileAdjunto());
            File file1 = new File(path + comunicacionPQRD.getNombre_archivo_adjunto());
            FileOutputStream outputStream = new FileOutputStream(file1);
            Throwable var93 = null;

            try {
               byte[] bytes = new byte[(int)comunicacionPQRD.getFileAdjunto().length()];

               while((margin = inputStream1.read(bytes)) != -1) {
                  outputStream.write(bytes, 0, margin);
               }

               inputStream1.close();
            } catch (Throwable var85) {
               var93 = var85;
               throw var85;
            } finally {
               if (outputStream != null) {
                  if (var93 != null) {
                     try {
                        outputStream.close();
                     } catch (Throwable var84) {
                        var93.addSuppressed(var84);
                     }
                  } else {
                     outputStream.close();
                  }
               }

            }
         } catch (IOException var87) {
         }
      }

   }
}
