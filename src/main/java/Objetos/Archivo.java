package Objetos;

public class Archivo{
	
	private byte[] bytesData;
	private String nombre;
	private String numeroradicacioninterno;
	private String respondidopor;

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

	public String getRespondidopor() {
		return respondidopor;
	}

	public void setRespondidopor(String respondidopor) {
		this.respondidopor = respondidopor;
	}
}