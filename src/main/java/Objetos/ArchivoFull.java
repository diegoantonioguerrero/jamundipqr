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

        for (int i=0; i< this.archivos.size(); i++) {
        	Archivo entrada = this.archivos.get(i);
            String nombreArchivo = entrada.getNombre();
            byte[] datosArchivo = entrada.getBytesData();

            ZipEntry zipEntry = new ZipEntry(nombreArchivo);
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









