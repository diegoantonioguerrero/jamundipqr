package Objetos;

public class Correspondencia{
	private int fldidCorrespondencia;
	private String nombre;
	private String email;
	private int numVerificacion; 
	private String fecha;
	private String radicado;
	private String elAsunto;
	private String estado;
	private String trazabilidad;
	private String tablaenviorecibo;
	

	public int getFldidCorrespondencia() {
		return fldidCorrespondencia;
	}

	public void setFldidCorrespondencia(int fldidCorrespondencia) {
		this.fldidCorrespondencia = fldidCorrespondencia;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getNumVerificacion() {
		return numVerificacion;
	}

	public void setNumVerificacion(int numVerificacion) {
		this.numVerificacion = numVerificacion;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getRadicado() {
		return radicado;
	}

	public void setRadicado(String radicado) {
		this.radicado = radicado;
	}

	public String getElAsunto() {
		return elAsunto;
	}

	public void setElAsunto(String elAsunto) {
		this.elAsunto = elAsunto;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getTrazabilidad() {
		return trazabilidad;
	}

	public void setTrazabilidad(String trazabilidad) {
		this.trazabilidad = trazabilidad;
	}

	public String getTablaenviorecibo() {
		return tablaenviorecibo;
	}

	public void setTablaenviorecibo(String tablaenviorecibo) {
		this.tablaenviorecibo = tablaenviorecibo;
	}
}