package Controladores;

import Objetos.Archivo;
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
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CreatePDFMejorado {

   public void crearPDF(ComunicacionPQRD comunicacionPQRD) throws IOException, Exception {
      String extension = ".pdf";
      String basePath = Util.getPath();
      String nombreAplicacion = Util.getProperties("nombreAplicacion");

      Path pathFont = Paths.get(basePath, nombreAplicacion);
      Path pathRadicados = Paths.get(basePath, nombreAplicacion, "resources", "radicados");

      String nombre_carpeta = comunicacionPQRD.getNro_radicacion();
      Path dir = pathRadicados.resolve(nombre_carpeta);
      Files.createDirectories(dir);

      String dest = dir.resolve(comunicacionPQRD.getNro_radicacion() + " - Remitente" + extension).toString();

      try (PdfWriter writer = new PdfWriter(dest);
           PdfDocument pdf = new PdfDocument(writer);
           Document document = new Document(pdf, PageSize.LETTER)) {

         document.setMargins(30.0F, 20.0F, 20.0F, 30.0F);
         float[] pointColumnWidths = new float[]{120.0F, 440.0F};
         int normalSize = 10;
         int margin = normalSize + 6;

         String pathVerdana = pathFont.resolve("resources").resolve("font").resolve("verdana.ttf").toString();
         String pathBold = pathFont.resolve("resources").resolve("font").resolve("verdanaBold.ttf").toString();

         PdfFont normal = PdfFontFactory.createFont(pathVerdana, "Identity-H", true);
         PdfFont bold = PdfFontFactory.createFont(pathBold, "Identity-H", true);

         // Tabla 1: Radicado y Fecha
         Table table1 = new Table(pointColumnWidths);
         String fecha = (new SimpleDateFormat("yyyy/MM/dd")).format(new Date());
         fecha = fecha + " " + comunicacionPQRD.getHoraRealizacion();
         table1.addCell(createCell("N\u00famero Radicado: ", bold, normalSize));
         table1.addCell(createCell(comunicacionPQRD.getNro_radicacion(), normal, normalSize));
         table1.addCell(createCell("Fecha de Creaci\u00f3n: ", bold, normalSize));
         table1.addCell(createCell(fecha, normal, normalSize));
         table1.setMarginBottom((float)margin);
         document.add(table1);

         // Tabla 2: Tipo
         Table table2 = new Table(pointColumnWidths);
         table2.addCell(createCell("Tipo: ", bold, normalSize));
         table2.addCell(createCell(comunicacionPQRD.getTipo_identificacion(), normal, normalSize));
         table2.setMarginBottom((float)margin);
         document.add(table2);

         // Tabla 3: Datos Personales
         Table table3 = new Table(pointColumnWidths);
         String nombreCompleto = String.join(" ",
                 comunicacionPQRD.getPrimer_nombre() != null ? comunicacionPQRD.getPrimer_nombre() : "",
                 comunicacionPQRD.getSegundo_nombre() != null ? comunicacionPQRD.getSegundo_nombre() : "",
                 comunicacionPQRD.getPrimer_apellido() != null ? comunicacionPQRD.getPrimer_apellido() : "",
                 comunicacionPQRD.getSegundo_apellido() != null ? comunicacionPQRD.getSegundo_apellido() : ""
         ).replaceAll("\\s+", " ").trim();

         String identificacionCompleta = String.join(" ",
                 comunicacionPQRD.getNombreTipoDocumento() != null ? comunicacionPQRD.getNombreTipoDocumento() : "",
                 comunicacionPQRD.getNro_identificacion() != null ? comunicacionPQRD.getNro_identificacion() : ""
         ).trim();

         table3.addCell(createCell("Nombre: ", bold, normalSize));
         table3.addCell(createCell(nombreCompleto, normal, normalSize));
         table3.addCell(createCell("Raz\u00f3n Social: ", bold, normalSize));
         table3.addCell(createCell(comunicacionPQRD.getRazon_social() != null ? comunicacionPQRD.getRazon_social() : "", normal, normalSize));
         table3.addCell(createCell("Identificaci\u00f3n: ", bold, normalSize));
         table3.addCell(createCell(identificacionCompleta, normal, normalSize));
         table3.setMarginBottom((float)margin);
         document.add(table3);

         // Tabla 4: Contacto
         Table table4 = new Table(pointColumnWidths);
         table4.addCell(createCell("Celular: ", bold, normalSize));
         table4.addCell(createCell(comunicacionPQRD.getCelular() != null ? comunicacionPQRD.getCelular() : "", normal, normalSize));
         table4.addCell(createCell("Direcci\u00f3n: ", bold, normalSize));
         table4.addCell(createCell(comunicacionPQRD.getDireccion() != null ? comunicacionPQRD.getDireccion() : "", normal, normalSize));
         table4.addCell(createCell("Ciudad: ", bold, normalSize));
         table4.addCell(createCell(comunicacionPQRD.getNombre_ciudad() != null ? comunicacionPQRD.getNombre_ciudad() : "", normal, normalSize));
         table4.setMarginBottom((float)margin);
         document.add(table4);

         // Tabla 5: Email
         Table table5 = new Table(pointColumnWidths);
         String txtCorreo = (comunicacionPQRD.getEmail() != null && !comunicacionPQRD.getEmail().isEmpty())
                 ? comunicacionPQRD.getEmail() + "\nEl usuario acept\u00f3 notificaciones por medios electr\u00f3nicos."
                 : "";
         table5.addCell(createCell("Email: ", bold, normalSize));
         table5.addCell(createCell(txtCorreo, normal, normalSize));
         table5.setMarginBottom((float)margin);
         document.add(table5);

         // Tabla 6: Detalles PQRD
         Table table6 = new Table(pointColumnWidths);
         table6.addCell(createCell("Tipo PQRD:  ", bold, normalSize));
         table6.addCell(createCell(comunicacionPQRD.getNombreTipoPQRD(), normal, normalSize));
         table6.addCell(createCell("Tipo Respuesta: ", bold, normalSize));
         table6.addCell(createCell(comunicacionPQRD.getNombreTipoRespuesta() != null ? comunicacionPQRD.getNombreTipoRespuesta() : "", normal, normalSize));
         table6.addCell(createCell("Dependencia: ", bold, normalSize));
         table6.addCell(createCell(comunicacionPQRD.getNombreDependencia(), normal, normalSize));
         table6.addCell(createCell("Archivo Adjunto:", bold, normalSize));
         table6.addCell(createCell(comunicacionPQRD.getFileAdjunto() == null ? "NO" : "SI", normal, normalSize));
         table6.addCell(createCell("Asunto:  ", bold, normalSize));
         table6.addCell(createCell(comunicacionPQRD.getAsunto() != null ? comunicacionPQRD.getAsunto() : "", normal, normalSize));
         table6.addCell(createCell("Detalle:  ", bold, normalSize));
         table6.addCell(createCell(comunicacionPQRD.getDetalle() != null ? comunicacionPQRD.getDetalle() : "", normal, normalSize));
         document.add(table6);
      }

      if (comunicacionPQRD.getFileAdjunto() != null) {
         try {
            InputStream inputStream1 = new FileInputStream(comunicacionPQRD.getFileAdjunto());
            String fullName = dir.resolve(comunicacionPQRD.getNombre_archivo_adjunto()).toString();
            File file1 = new File(fullName);
            try (FileOutputStream outputStream = new FileOutputStream(file1)) {
               byte[] bytes = new byte[(int)comunicacionPQRD.getFileAdjunto().length()];
               int read;
               while((read = inputStream1.read(bytes)) != -1) {
                  outputStream.write(bytes, 0, read);
               }
               inputStream1.close();
            }
            
            // descomprimir y borrar zip temporal
            
            String[] tokensExtension = fullName.split("\\.(?=[^\\.]+$)");
            if (tokensExtension.length > 0 && tokensExtension[1].toLowerCase().equals("zip")) {
            	
            	if  (file1.exists()) {
            		File destino = dir.toFile();
                    CreateZip.descomprimirZip(file1, destino);
                    if( !file1.delete()) {
                 	   throw new Exception("Error deleting temporal file");
                    }	
            	}	
            }
         } catch (IOException e) {
        	 throw e;
         }
      }
   }

   private Cell createCell(String text, PdfFont font, int fontSize) {
      return new Cell()
              .add(text)
              .setBorder(Border.NO_BORDER)
              .setFont(font)
              .setFontSize((float) fontSize);
   }
}
