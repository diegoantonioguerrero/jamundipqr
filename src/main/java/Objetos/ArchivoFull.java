package Objetos;

import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.util.*;
import java.util.zip.*;

public class ArchivoFull{
	
	private List<Archivo> archivos;
	
	private byte[] bytesData;
	private String nombre;
	private String numeroradicacioninterno;
	private String respondidopor;
	
	public ArchivoFull() {
		this.archivos = new ArrayList<Archivo>();
	}
	
	public byte[] getBytesData() {
		return bytesData;
	}

	public void setBytesData(byte[] bytesData) {
		this.bytesData = bytesData;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNumeroradicacioninterno() {
		return numeroradicacioninterno;
	}
	
	

	public void setNumeroradicacioninterno(String numeroradicacioninterno) {
		this.numeroradicacioninterno = numeroradicacioninterno;
	}

	public List<Archivo> getArchivos() {
		return archivos;
	}

	public void setArchivos(List<Archivo> archivos) {
		this.archivos = archivos;
	}
	
		
    public byte[] comprimirArchivos() throws IOException {
    	
  
        ByteArrayOutputStream zipEnMemoria = new ByteArrayOutputStream();
        ZipOutputStream zipOut = new ZipOutputStream(zipEnMemoria);


        // Mapa para rastrear cuántas veces ha aparecido un nombre base
        Map<String, Integer> nombreArchivoCount = new HashMap<>();

        for (Archivo entrada : this.archivos) {
            String nombreOriginal = entrada.getNombre();
            byte[] datosArchivo = entrada.getBytesData();

            // Separar nombre y extensión
            String nombreBase = nombreOriginal;
            String extension = "";
            int lastDot = nombreOriginal.lastIndexOf('.');
            if (lastDot != -1) {
                nombreBase = nombreOriginal.substring(0, lastDot);
                extension = nombreOriginal.substring(lastDot);
            }

            // Generar nombre único
            String nombreFinal = nombreOriginal;
            if (nombreArchivoCount.containsKey(nombreOriginal)) {
                int count = nombreArchivoCount.get(nombreOriginal) + 1;
                nombreFinal = nombreBase + "-" + count + extension;
                nombreArchivoCount.put(nombreOriginal, count);
            } else {
                nombreArchivoCount.put(nombreOriginal, 1);
            }

            // Agregar entrada al ZIP
            ZipEntry zipEntry = new ZipEntry(nombreFinal);
            zipOut.putNextEntry(zipEntry);
            zipOut.write(datosArchivo);
            zipOut.closeEntry();
        }

        zipOut.close();
        return zipEnMemoria.toByteArray();
    }

	public String getRespondidopor() {
		return respondidopor;
	}

	public void setRespondidopor(String respondidopor) {
		this.respondidopor = respondidopor;
	}

}









