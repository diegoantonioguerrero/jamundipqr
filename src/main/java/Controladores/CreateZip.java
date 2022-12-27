package Controladores;

import DataBaseConection.DataBaseConection;
import Objetos.ComunicacionPQRD;
import Utilidades.Util;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class CreateZip {
   private List<String> fileList = new ArrayList<String>();

   public String crearZip(ComunicacionPQRD comunicacionPQRD) throws IOException, Exception {
      String ext = ".zip";
      File archivo = new File("temporalFile");
      String mainPath = archivo.getAbsolutePath();
      mainPath = mainPath.replace("temporalFile", "webapps\\");
      mainPath = mainPath.replace("bin\\temporalFile", "webapps\\");
      mainPath = mainPath + Util.getProperties("nombreAplicacion") + "\\resources\\radicados\\";
      String OutputPath = mainPath + comunicacionPQRD.getNro_radicacion() + ext;
      String FolderPath = mainPath + comunicacionPQRD.getNro_radicacion();
      CreateZip appZip = new CreateZip();
      appZip.generateFileList(new File(FolderPath), FolderPath);
      appZip.zipIt(OutputPath, FolderPath);
      File fileDelete = new File(FolderPath);
      String[] entries = fileDelete.list();
      String[] var10 = entries;
      int var11 = entries.length;

      for(int var12 = 0; var12 < var11; ++var12) {
         String s = var10[var12];
         File currentFile = new File(fileDelete.getPath(), s);
         currentFile.delete();
      }

      fileDelete.delete();
      File currentF = new File(mainPath, comunicacionPQRD.getNro_radicacion() + ext);
      comunicacionPQRD.setFileZip(currentF);
      comunicacionPQRD.setNombrearchivoarchivoadjuntorar(comunicacionPQRD.getNro_radicacion() + ext);
      DataBaseConection dataBaseConection = new DataBaseConection();
      dataBaseConection.insertarZip(comunicacionPQRD);
      dataBaseConection.logoutDB();
      return OutputPath;
   }

   public void zipIt(String zipFile, String FolderPath) {
      byte[] buffer = new byte[1024];
      String source = (new File(FolderPath)).getName();
      FileOutputStream fos = null;
      ZipOutputStream zos = null;

      try {
         fos = new FileOutputStream(zipFile);
         zos = new ZipOutputStream(fos);
         FileInputStream in = null;
         Iterator<String> var8 = this.fileList.iterator();

         while(var8.hasNext()) {
            String file = (String)var8.next();
            ZipEntry ze = new ZipEntry(source + File.separator + file);
            zos.putNextEntry(ze);

            try {
               in = new FileInputStream(FolderPath + File.separator + file);

               int len;
               while((len = in.read(buffer)) > 0) {
                  zos.write(buffer, 0, len);
               }
            } finally {
               in.close();
            }
         }

         zos.closeEntry();
      } catch (IOException var27) {
         var27.printStackTrace();
      } finally {
         try {
            zos.close();
         } catch (IOException var25) {
            var25.printStackTrace();
         }

      }

   }

   public void generateFileList(File node, String FolderPath) {
      if (node.isFile()) {
         this.fileList.add(this.generateZipEntry(node.toString(), FolderPath));
      }

      if (node.isDirectory()) {
         String[] subNote = node.list();
         String[] var4 = subNote;
         int var5 = subNote.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            String filename = var4[var6];
            this.generateFileList(new File(node, filename), FolderPath);
         }
      }

   }

   private String generateZipEntry(String file, String FolderPath) {
      return file.substring(FolderPath.length() + 1, file.length());
   }
}
