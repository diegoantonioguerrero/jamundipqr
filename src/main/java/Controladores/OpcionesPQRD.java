package Controladores;

import java.util.TimerTask;
import java.nio.file.Path;
import java.util.Timer;
import java.io.InputStream;
import java.io.Serializable;

import org.primefaces.model.DefaultStreamedContent;
import java.io.FileInputStream;
import java.net.URLConnection;
import java.io.FileOutputStream;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.attribute.FileAttribute;
import java.rmi.UnexpectedException;
import java.io.IOException;
import org.primefaces.context.RequestContext;
import java.sql.ResultSet;
import DataBaseConection.DataBaseConection;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import Utilidades.Util;
import Objetos.Trazabilidad;
import java.util.List;
import java.util.Random;
import org.primefaces.model.StreamedContent;
import Objetos.ConsultaPQRD;
import Objetos.Correspondencia;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.bean.ManagedBean;

@ManagedBean(name = "opcionesPQRD")
@ViewScoped
public class OpcionesPQRD implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7724838274235912152L;
	private static String path_background_image;
	private static String urlOrigen;
	private static String textTermsConditionsMisPQRD;	
	
	private String emailConsulta;
	private int pasoActual = 1;
	private int intentos = 0;
	
	private String nroRadicado;
	private String nroDocumento;
	private String nroVerificacion;
	private static ConsultaPQRD consultaPQRD;
	private StreamedContent file;
	private boolean nroRadicadoExist;
	private boolean mostrarFechaPosibleRespuesta;

	DataBaseConection dataBaseConection1;
	
	public String getNroVerificacion() {
		return this.nroVerificacion;
	}

	public void setNroVerificacion(final String nroVerificacion) {
		this.nroVerificacion = nroVerificacion;
	}
	
	
	public String getEmailConsulta() {
		return this.emailConsulta;
	}

	public void setEmailConsulta(final String emailConsulta) {
		this.emailConsulta = emailConsulta;
	}

	

	public OpcionesPQRD() {
		this.nroRadicadoExist = false;
		this.mostrarFechaPosibleRespuesta = false;
		try {
			OpcionesPQRD.path_background_image = Util.getProperties("imagenFondo");
			OpcionesPQRD.textTermsConditionsMisPQRD = Util.getProperties("textTermsConditionsMisPQRD");

		} catch (Exception ex) {
			Logger.getLogger(OpcionesPQRD.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	
	private DataBaseConection getConnection() {
		if(dataBaseConection1 == null) {
			dataBaseConection1 = new DataBaseConection();	
		}
		
		if(dataBaseConection1.isClosed()) {
			dataBaseConection1.loginDB();
		} 
		return dataBaseConection1;
	}
	
	public boolean existeDirectorio() throws Exception {
		
		long records = 0;
			
			try {

				final DataBaseConection dataBaseConection1 = getConnection(); 
				String query2 = "SELECT COUNT(1) AS records FROM DIRECTORIOEMAILPRQD WHERE lower(email) LIKE lower('?');";
				query2 = query2.replaceFirst("\\?", this.emailConsulta);
				dataBaseConection1.consultarDB(query2);
				final ResultSet resultConsulta1 = dataBaseConection1.getResult();
				
				while (resultConsulta1.next()) {
					records = resultConsulta1.getLong("records");
				}
				
			} catch (Exception ex) {
				Logger.getLogger(ConsultarPQRD.class.getName()).log(Level.SEVERE, null, ex);
				throw ex;
			}
			
			return records > 0;
	
	}
	
	
public boolean validaDirectorio() throws Exception {
		
		long records = 0;
			
			try {

				final DataBaseConection dataBaseConection1 = getConnection(); 
				String query2 = "SELECT COUNT(1) AS records\r\n"
						+ "FROM directorioemailprqd \r\n"
						+ "WHERE lower('?') = ANY (string_to_array(TRIM(REPLACE( lower(EMAIL), ' ', '')), ';'))\r\n"
						+ "AND NUMEROVERIFICACION = '?';";
				query2 = query2.replaceFirst("\\?", this.emailConsulta);
				query2 = query2.replaceFirst("\\?", this.nroVerificacion);
				dataBaseConection1.consultarDB(query2);
				final ResultSet resultConsulta1 = dataBaseConection1.getResult();
				
				while (resultConsulta1.next()) {
					records = resultConsulta1.getLong("records");
				}
				
			} catch (Exception ex) {
				Logger.getLogger(ConsultarPQRD.class.getName()).log(Level.SEVERE, null, ex);
				throw ex;
			}
			
			return records > 0;
	
	}


public int getDirectorio() throws Exception {
	
	int numeroverificacion = 0;
		
		try {

			final DataBaseConection dataBaseConection1 = getConnection(); 
			String query2 = "SELECT numeroverificacion\r\n"
					+ "FROM directorioemailprqd \r\n"
					+ "WHERE lower('?') = ANY (string_to_array(TRIM(REPLACE( lower(EMAIL), ' ', '')), ';'));";
			query2 = query2.replaceFirst("\\?", this.emailConsulta);
			dataBaseConection1.consultarDB(query2);
			final ResultSet resultConsulta1 = dataBaseConection1.getResult();
			
			while (resultConsulta1.next()) {
				numeroverificacion = resultConsulta1.getInt("numeroverificacion");
			}
			
		} catch (Exception ex) {
			Logger.getLogger(ConsultarPQRD.class.getName()).log(Level.SEVERE, null, ex);
			throw ex;
		}
		
		return numeroverificacion;

}


	public boolean existeCorrespondencia() throws Exception {
		
		long records = 0;
			
			try {

				final DataBaseConection dataBaseConection1 = getConnection(); 
				String query2 = "SELECT COUNT(1) AS records\r\n"
						+ "FROM CORRESPONDENCIA \r\n"
						+ "INNER JOIN COMUNICACIONPRQD \r\n"
						+ "  ON CORRESPONDENCIA.numeroradicacioninterno = COMUNICACIONPRQD.nroradicacion \r\n"
						+ "INNER JOIN directorioemailprqd \r\n"
						+ "  ON directorioemailprqd.EMAIL = ANY (string_to_array(TRIM(REPLACE(CORRESPONDENCIA.EMAIL, ' ', '')), ';'))\r\n"
						+ "WHERE lower(COMUNICACIONPRQD.email) LIKE lower('?');";

				query2 = query2.replaceFirst("\\?", this.emailConsulta);
				dataBaseConection1.consultarDB(query2);
				final ResultSet resultConsulta1 = dataBaseConection1.getResult();
				
				while (resultConsulta1.next()) {
					records = resultConsulta1.getLong("records");
				}
				
			} catch (Exception ex) {
				Logger.getLogger(ConsultarPQRD.class.getName()).log(Level.SEVERE, null, ex);
				throw ex;
			}
			
			return records > 0;
	
	}
	
	public Correspondencia existeCorrespondenciaBasic() throws Exception {
		
		Correspondencia correspondencia = null;
			
			try {

				final DataBaseConection dataBaseConection1 = getConnection(); 
				String query2 = "SELECT fldidCorrespondencia, email,\r\n"
						+ "       CASE enviorecibo \r\n"
						+ "           WHEN 1 THEN solonombredestino\r\n"
						+ "           WHEN 2 THEN solonombreorigen\r\n"
						+ "           ELSE '' \r\n"
						+ "       END AS nombre\r\n"
						+ "FROM CORRESPONDENCIA \r\n"
						+ "WHERE \r\n"
						+ "lower('?') = ANY (string_to_array(TRIM(REPLACE(REPLACE(lower(CORRESPONDENCIA.email) , ' ', ''), ',', ';')), ';'))\r\n"
						+ "ORDER BY fldidCorrespondencia DESC\r\n"
						+ "LIMIT 1;";

				query2 = query2.replaceFirst("\\?", this.emailConsulta);
				dataBaseConection1.consultarDB(query2);
				final ResultSet resultConsulta1 = dataBaseConection1.getResult();
				
				while (resultConsulta1.next()) {
					correspondencia = new Correspondencia();
					correspondencia.setFldidCorrespondencia(resultConsulta1.getInt("fldidCorrespondencia"));
					correspondencia.setEmail(this.emailConsulta);
					correspondencia.setNombre(resultConsulta1.getString("nombre"));
				}
				
			} catch (Exception ex) {
				Logger.getLogger(ConsultarPQRD.class.getName()).log(Level.SEVERE, null, ex);
				throw ex;
			}
			
			return correspondencia;
	
	}
	
	public void deleteOldRecords() {
		
		try {
			//this.nroRadicado = this.nroRadicado.toUpperCase();
			final DataBaseConection dataBaseConection1 = getConnection();
			String query2 = "DELETE FROM DIRECTORIOEMAILPRQD WHERE fecha <> CURRENT_DATE;";
			boolean affectRows = dataBaseConection1.actualizarConsultasPQRD(query2);
			//final Calendar calendar = Calendar.getInstance();
			//final Date fechaHoy = new Date(calendar.getTime().getTime());
			
		} catch (Exception ex) {
			Logger.getLogger(ConsultarPQRD.class.getName()).log(Level.SEVERE, null, ex);
			throw ex;
		}
	}
	
	public int createDirectory() throws Exception {
		
		try {
			int numVerificacion = generateCode();
			final DataBaseConection dataBaseConection1 = getConnection();
			String query2 = "INSERT INTO directorioemailprqd (fldiddirectorioemailprqd, email, numeroverificacion, fecha)\r\n"
					+ "values ((select COALESCE(max(fldiddirectorioemailprqd), 0) + 1 AS id from directorioemailprqd) ,lower('?'),"
					+ "'" + numVerificacion + "', CURRENT_DATE);\r\n";
			query2 = query2.replaceFirst("\\?", this.emailConsulta);
			boolean affectRows = dataBaseConection1.actualizarConsultasPQRD(query2);
			if (!affectRows) {
				throw new UnexpectedException("No record saved");
			}
			
			return numVerificacion; 
			
		} catch (Exception ex) {
			Logger.getLogger(ConsultarPQRD.class.getName()).log(Level.SEVERE, null, ex);
			throw ex;
		}
	}
	
	public int generateCode() {
		
		Random rand = new Random();
	    int code = rand.nextInt(900000) + 100000; // Genera un número entre 100000 y 999999
	    return code;
	}

	public String validarEmail() {
		if (this.emailConsulta != null) {
			
			try {
				if (this.pasoActual == 1) {
					return this.pasoUno();
				}
				else{
					return this.pasoDos();
				}
				
			} catch (Exception ex) {
				Logger.getLogger(ConsultarPQRD.class.getName()).log(Level.SEVERE, null, ex);
				String msg = ex.getMessage();
				msg = msg.replace("\r\n", "\\r\\n"); 
				RequestContext.getCurrentInstance().execute("mensajeErrorDbg('" + msg + "')");
			}
			finally {
				if(dataBaseConection1 != null) {
					dataBaseConection1.logoutDB();	
				}
			}
		} else {
			System.err.println("Campos faltantes");
		}
		return null;
	}
	
	public String pasoUno() throws Exception {
		deleteOldRecords();
		boolean existsDirectory = this.existeDirectorio();
		if (existsDirectory) {
			this.setPasoActual(2);
			return null; //permanecer en la pagina
		}
		
		Correspondencia correspondencia = existeCorrespondenciaBasic(); 
		if(correspondencia == null) {
			//RequestContext.getCurrentInstance().execute("PF('bloqueoconsulta').show();");
			String msg = "El email " + this.emailConsulta + " no registra correspondencia en nuestro sistema";
			RequestContext.getCurrentInstance().execute("mensajeErrorCorrespondenciaNoExiste('" + msg + "')");
		}
		else {
			int numVerificacion = createDirectory();
			correspondencia.setNumVerificacion(numVerificacion);
			enviarCorreo(correspondencia);
			this.setPasoActual(2);
			RequestContext.getCurrentInstance().execute("mensajeCorreo()");
		}
		
		return null; 
	}
	
	private void enviarCorreo(Correspondencia correspondencia) throws Exception {
		NumVerificacion numVerificacionBean = (NumVerificacion) FacesContext.getCurrentInstance()
			    .getELContext()
			    .getELResolver()
			    .getValue(FacesContext.getCurrentInstance().getELContext(), null, "numVerificacion");

		try {
			numVerificacionBean.enviarCorreo(correspondencia);	
		}
		catch(Exception ex) {
			throw ex;
		}
	
	}

	public String pasoDos() throws Exception {
		boolean checkDirectorio = this.validaDirectorio();
		if (!checkDirectorio) {
			this.intentos++;
			boolean conDosMinutos = (this.intentos >= 3);
			RequestContext.getCurrentInstance().execute("mensajeErrorNroVerificacion(" + conDosMinutos + ", '" + getUrlOrigen()  + "')");
			return null; //permanecer en la pagina
		}
		
		Correspondencia correspondencia = existeCorrespondenciaBasic(); 
		if(correspondencia == null) {
			RequestContext.getCurrentInstance().execute("mensajeErrorMantenimiento('" + getUrlOrigen()  + "')");
			return null; //permanecer en la pagina, pero el se redirecciona
		}
		
		return "/faces/mispqrds.xhtml";//?email="+ this.emailConsulta+ "&nroVerificacion="+ this.nroVerificacion; 
	}

	
	public StreamedContent getFile() {
		try {
			String mimeType = null;
			// final InputStream in =
			// ConsultarPQRD.consultaPQRD.getDatos_archivo_respuesta();
			final Path path = Files.createTempFile("sample", ".txt", (FileAttribute<?>[]) new FileAttribute[0]);
			final File fileTemp = path.toFile();
			final String dirSalida = fileTemp.getAbsolutePath();
			try (final InputStream inputStream = OpcionesPQRD.consultaPQRD.getDatos_archivo_respuesta()) {
				final File fil = new File(dirSalida + OpcionesPQRD.consultaPQRD.getNombre_archivo_respuesta());
				try (final FileOutputStream outputStream = new FileOutputStream(fil)) {
					final int longs = OpcionesPQRD.consultaPQRD.getDatos_archivo_respuesta().available();
					final byte[] bytes = new byte[longs];
					int read;
					while ((read = inputStream.read(bytes)) != -1) {
						outputStream.write(bytes, 0, read);
					}
					inputStream.close();
				}
			}
			final File file1 = new File(dirSalida + OpcionesPQRD.consultaPQRD.getNombre_archivo_respuesta());
			mimeType = URLConnection.guessContentTypeFromName(file1.getName());
			final String extensionArchivo = OpcionesPQRD.consultaPQRD.getNombre_archivo_respuesta();
			final String[] tokens = extensionArchivo.split("\\.(?=[^\\.]+$)");
			OpcionesPQRD.consultaPQRD.setNombre_archivo_respuesta(
					OpcionesPQRD.consultaPQRD.getNro_radicacion_respuesta() + "." + tokens[1]);
			this.file = (StreamedContent) new DefaultStreamedContent((InputStream) new FileInputStream(file1), mimeType,
					OpcionesPQRD.consultaPQRD.getNombre_archivo_respuesta());
			final Timer temporizador = new Timer();
			TimerTask task = new TimerTask() {

				public void run() {
					if (file1 != null) {
						file1.delete();
						while (file1.exists())
							try {
								FileUtils.forceDelete(file1);
							} catch (IOException ex) {
								Logger.getLogger(Controladores.FormularPQRD.class.getName()).log(Level.SEVERE, null,
										ex);
							}
					}
					if (fileTemp != null) {
						fileTemp.delete();
						while (fileTemp.exists())
							try {
								FileUtils.forceDelete(fileTemp);
							} catch (IOException ex) {
								Logger.getLogger(Controladores.FormularPQRD.class.getName()).log(Level.SEVERE, null,
										ex);
							}
					}
				}
			};

			temporizador.scheduleAtFixedRate(task, 2000L, 60000L);
		} catch (IOException ex) {
			Logger.getLogger(ConsultarPQRD.class.getName()).log(Level.SEVERE, null, ex);
		}
		return this.file;
	}
	
	public String reenviarNumeroVerificacion() {
		
		if (this.emailConsulta == null) {
			RequestContext.getCurrentInstance().execute("mensajeErrorDbg('Vaya atrás y digite nuevamente el correo')");
			return null;
		}
		
		try {

			Correspondencia correspondencia = existeCorrespondenciaBasic();
			int numVerificacion = getDirectorio();
			correspondencia.setNumVerificacion(numVerificacion);
			enviarCorreo(correspondencia);
			RequestContext.getCurrentInstance().execute("mensajeCorreo()");
			
		} catch (Exception ex) {
			Logger.getLogger(ConsultarPQRD.class.getName()).log(Level.SEVERE, null, ex);
			String msg = ex.getMessage();
			msg = msg.replace("\r\n", "\\r\\n"); 
			RequestContext.getCurrentInstance().execute("mensajeErrorDbg('" + msg + "')");
		}
		finally {
			if(dataBaseConection1 != null) {
				dataBaseConection1.logoutDB();	
			}
		}
		
		return null; 
		
	}

	public void setNroRadicado(final String nroRadicado) {
		this.nroRadicado = nroRadicado;
	}

	public String getNroRadicado() {
		return this.nroRadicado;
	}

	public String getNroDocumento() {
		return this.nroDocumento;
	}

	public void setNroDocumento(final String nroDocumento) {
		this.nroDocumento = nroDocumento;
	}

	public String getPath_background_image() {
		return OpcionesPQRD.path_background_image;
	}

	public ConsultaPQRD getConsultaPQRD() {
		return OpcionesPQRD.consultaPQRD;
	}

	public String getUrlOrigen() {
		return OpcionesPQRD.urlOrigen;
	}

	

	public boolean isNroRadicadoExist() {
		return this.nroRadicadoExist;
	}
/*
  public boolean isFileExist() {
		return OpcionesPQRD.fileExist;
	}
	public String getFondoHeader() {
		return OpcionesPQRD.fondoHeader;
	}

	public String getFondoFooter() {
		return OpcionesPQRD.fondoFooter;
	}

	public String getColorFondoBotones() {
		return OpcionesPQRD.colorFondoBotones;
	}

	public String getColorLetraBotones() {
		return OpcionesPQRD.colorLetraBotones;
	}

	public String getFuenteTiulos() {
		return OpcionesPQRD.fuenteTiulos;
	}

	public String getFuenteEtiquetas() {
		return OpcionesPQRD.fuenteEtiquetas;
	}

	public String getFuenteContenido() {
		return OpcionesPQRD.fuenteContenido;
	}
		public String getTextoAlternativoEncabezado() {
		return textoAlternativoEncabezado;
	}

	public String getTextoAlternativoPiedepagina() {
		return textoAlternativoPiedepagina;
	}

*/
	public String getTextTermsConditionsMisPQRD() {
		return OpcionesPQRD.textTermsConditionsMisPQRD;
	}

	public boolean isMostrarFechaPosibleRespuesta() {
		return this.mostrarFechaPosibleRespuesta;
	}

	public int getPasoActual() {
		return pasoActual;
	}

	public void setPasoActual(int pasoActual) {
		this.pasoActual = pasoActual;
	}

	public int getIntentos() {
		return intentos;
	}

	public void setIntentos(int intentos) {
		this.intentos = intentos;
	}
}