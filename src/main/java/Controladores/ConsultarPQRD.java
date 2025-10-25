package Controladores;

import java.util.TimerTask;
import java.nio.file.Path;
import java.util.Timer;
import java.io.InputStream;
import org.primefaces.model.DefaultStreamedContent;
import java.io.FileInputStream;
import java.net.URLConnection;
import java.io.FileOutputStream;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.attribute.FileAttribute;
import java.text.DateFormat;
import java.io.IOException;
import org.primefaces.context.RequestContext;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.Calendar;
import java.sql.SQLException;
//import java.util.Iterator;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.ArrayList;
import DataBaseConection.DataBaseConection;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;
import Utilidades.Util;
import Objetos.Trazabilidad;
import java.util.List;
import org.primefaces.model.StreamedContent;
import Objetos.ConsultaPQRD;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean
@RequestScoped
public class ConsultarPQRD {
	private static String path_background_image;
	private static String urlOrigen;
	private static String fondoHeader;
	private static String fondoFooter;
	private static String colorFondoBotones;
	private static String colorLetraBotones;
	private static String fuenteTiulos;
	private static String fuenteEtiquetas;
	private static String fuenteContenido;
	private String nroRadicado;
	private String nroDocumento;
	private String nroVerificacion;
	private static ConsultaPQRD consultaPQRD;
	private StreamedContent file;
	private static boolean fileExist;
	private boolean nroRadicadoExist;
	private boolean mostrarFechaPosibleRespuesta;
	private List<Trazabilidad> trazList;
	private String textoAlternativoEncabezado;
	private String textoAlternativoPiedepagina;

	public String getNroVerificacion() {
		return this.nroVerificacion;
	}

	public void setNroVerificacion(final String nroVerificacion) {
		this.nroVerificacion = nroVerificacion;
	}

	public ConsultarPQRD() {
		this.nroRadicadoExist = false;
		this.mostrarFechaPosibleRespuesta = false;
		try {
			ConsultarPQRD.path_background_image = Util.getProperties("imagenFondo");
			ConsultarPQRD.fondoHeader = Util.getProperties("imagenFondoHeader");
			ConsultarPQRD.fondoFooter = Util.getProperties("imagenFondoFooter");
			ConsultarPQRD.colorFondoBotones = Util.getProperties("colorFondoBotones");
			ConsultarPQRD.colorLetraBotones = Util.getProperties("colorLetraBotones");
			ConsultarPQRD.fuenteTiulos = Util.getProperties("fuenteTiulos");
			ConsultarPQRD.fuenteEtiquetas = Util.getProperties("fuenteEtiquetas");
			ConsultarPQRD.fuenteContenido = Util.getProperties("fuenteContenido");
			ConsultarPQRD.urlOrigen = Util.getProperties("linkOrigen");
			this.textoAlternativoEncabezado = Util.getProperties("textoAlternativoEncabezado");
			this.textoAlternativoPiedepagina = Util.getProperties("textoAlternativoPiedepagina");
		} catch (Exception ex) {
			Logger.getLogger(ConsultarPQRD.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void posibleRespuesta() throws SQLException {
		
		final DataBaseConection dataBaseConection = new DataBaseConection();
		String queryRespuesta = "SELECT trazabilidad FROM comunicacionprqd as cpqrd INNER JOIN tablapqrestado as estados ON (estados.fldidtablapqrestado = cpqrd.estado) WHERE cpqrd.nroradicacion LIKE '?'";
		// Se deja en comentarios esta otra condicional para obtener la trazabilidad aun
		// cuando sea con el id del ultimo radicado
		// queryRespuesta += " AND cpqrd.numeroverificacion LIKE '?';";
		queryRespuesta = queryRespuesta.replaceFirst("\\?", this.nroRadicado);
		// queryRespuesta = queryRespuesta.replaceFirst("\\?", this.nroVerificacion);
		
		dataBaseConection.consultarDB(queryRespuesta);
		final ResultSet resultConsultaFecha = dataBaseConection.getResult();
		List<String> trazabList = new ArrayList<String>();
		while (resultConsultaFecha.next()) {
			final String trazText = resultConsultaFecha.getString("trazabilidad");
			ConsultarPQRD.consultaPQRD.setFechaPosibleRespuesta(resultConsultaFecha.getString("trazabilidad"));
			final String[] str = trazText.split("\n");
			trazabList = Arrays.asList(str);
		}
		final List<Trazabilidad> trazList2 = new ArrayList<Trazabilidad>();
		for (final String s : trazabList) {
			trazList2.add(new Trazabilidad(s));
		}
		this.trazList = trazList2;
		dataBaseConection.logoutDB();
	}

	public List<Trazabilidad> getTrazabilidad() {
		return this.trazList;
	}

	public String consultarPQRD() {
		if (this.nroRadicado != null && !this.nroRadicado.isEmpty() && this.nroVerificacion != null
				&& !this.nroVerificacion.isEmpty()) {
			try {
				this.nroRadicado = this.nroRadicado.toUpperCase();
				final DataBaseConection dataBaseConection1 = new DataBaseConection();
				String query2 = "SELECT nroradicacion, email, numeroverificacion, fechaultimaconsulta, cantidadconsultaserradase FROM comunicacionprqd WHERE tipopantalla='ANONIMO' AND nroradicacion LIKE '?';";
				query2 = query2.replaceFirst("\\?", this.nroRadicado);
				dataBaseConection1.consultarDB(query2);
				final ResultSet resultConsulta1 = dataBaseConection1.getResult();
				boolean validador = false;
				boolean numeroVerificacionErrado = false;
				boolean permitirConsulta = true;
				boolean numRadicadoExists = false;
				Date fechaUltCons = null;
				int numConsErr = 0;
				final Calendar calendar = Calendar.getInstance();
				final Date fechaHoy = new Date(calendar.getTime().getTime());
				while (resultConsulta1.next()) {
					final String numRad = resultConsulta1.getString("nroradicacion");
					final String emailConsulta = resultConsulta1.getString("email");
					final String numVerifA = resultConsulta1.getString("numeroverificacion");
					fechaUltCons = resultConsulta1.getDate("fechaultimaconsulta");
					numConsErr = resultConsulta1.getInt("cantidadconsultaserradase");
					
					if (fechaUltCons == null) {
						fechaUltCons = new Date(calendar.getTime().getTime());
					}
					String query3 = "SELECT numeroverificacion, fldidcomunicacionprqd FROM comunicacionprqd WHERE email LIKE '?' ORDER BY fldidcomunicacionprqd DESC LIMIT 1;";
					query3 = query3.replaceFirst("\\?", emailConsulta);
					dataBaseConection1.consultarDB(query3);
					final ResultSet resultConsulta2 = dataBaseConection1.getResult();
					while (resultConsulta2.next()) {
						final String numVerifB = resultConsulta2.getString("numeroverificacion");
						if (numRad != null && !numRad.isEmpty()) {
							numRadicadoExists = true;
							final String pattern = "yyyy-MM-dd";
							final DateFormat df = new SimpleDateFormat(pattern);
							final String fechaUlt = df.format(fechaUltCons);
							final String fechaHo = df.format(fechaHoy);
							
							if (!fechaUlt.equals(fechaHo)) {
								
								fechaUltCons = fechaHoy;
								numConsErr = 0;
							}
							permitirConsulta = (numConsErr < 3);
							if (permitirConsulta && numVerifA != null && !numVerifA.isEmpty() && numVerifB != null
									&& !numVerifB.isEmpty()) {
								if (numVerifA.equals(this.nroVerificacion) || numVerifB.equals(this.nroVerificacion)) {
									fechaUltCons = fechaHoy;
									
									numConsErr = 0;
									validador = true;
								} else {
									validador = false;
									numeroVerificacionErrado = true;
								}
							} else {
								validador = false;
							}
						} else {
							validador = false;
							numRadicadoExists = false;
						}
					}
				}
				
				dataBaseConection1.logoutDB();
				if (permitirConsulta) {
					
					if (validador) {
						
						final DataBaseConection dataBaseConection2 = new DataBaseConection();
						String query4 = "SELECT cpqrd.nroradicadorespuesta, cpqrd.archivorespuesta, cpqrd.nombrearchivoarchivorespuesta, cpqrd.fecharespuesta, cpqrd.fechaprimeraconsultarespu, estados.estado as estado FROM comunicacionprqd as cpqrd INNER JOIN tablapqrestado as estados ON (estados.fldidtablapqrestado = cpqrd.estado) WHERE cpqrd.nroradicacion LIKE '?';";
						query4 = query4.replaceFirst("\\?", this.nroRadicado);
						dataBaseConection2.consultarDB(query4);
						final ResultSet resultConsulta3 = dataBaseConection2.getResult();
						
						while (resultConsulta3.next()) {
							(ConsultarPQRD.consultaPQRD = new ConsultaPQRD())
									.setNro_radicacion_respuesta(resultConsulta3.getString("nroradicadorespuesta"));
							ConsultarPQRD.consultaPQRD
									.setDatos_archivo_respuesta(resultConsulta3.getBinaryStream("archivorespuesta"));
							ConsultarPQRD.consultaPQRD.setNombre_archivo_respuesta(
									resultConsulta3.getString("nombrearchivoarchivorespuesta"));
							ConsultarPQRD.consultaPQRD
									.setFecha_respuesta((java.util.Date) resultConsulta3.getDate("fecharespuesta"));
							ConsultarPQRD.consultaPQRD.setEstado(resultConsulta3.getString("estado"));
							ConsultarPQRD.consultaPQRD.setFechaprimeraconsultarespu(
									(java.util.Date) resultConsulta3.getDate("fechaprimeraconsultarespu"));
							ConsultarPQRD.fileExist = (ConsultarPQRD.consultaPQRD.getDatos_archivo_respuesta() != null
									&& ConsultarPQRD.consultaPQRD.getDatos_archivo_respuesta().available() != 0
									&& !ConsultarPQRD.consultaPQRD.getEstado().contains("pendiente")
									&& !ConsultarPQRD.consultaPQRD.getNombre_archivo_respuesta().isEmpty());
							if (ConsultarPQRD.consultaPQRD.getNro_radicacion_respuesta() != null
									&& !ConsultarPQRD.consultaPQRD.getNro_radicacion_respuesta().isEmpty()) {
								this.nroRadicadoExist = true;
							}
							
							final String estado = ConsultarPQRD.consultaPQRD.getEstado();
							switch (estado) {
							case "Pendiente":
							case "PENDIENTE": {
								this.nroRadicadoExist = false;
								ConsultarPQRD.fileExist = false;
								this.posibleRespuesta();
								if (ConsultarPQRD.consultaPQRD.getFechaPosibleRespuesta() == null
										|| ConsultarPQRD.consultaPQRD.getFechaPosibleRespuesta().isEmpty()) {
									this.mostrarFechaPosibleRespuesta = false;
									break;
								}
								this.mostrarFechaPosibleRespuesta = true;
								break;
							}
							case "RESPONDIDO":
							case "Respondido": {
								this.posibleRespuesta();
								if (ConsultarPQRD.consultaPQRD.getFechaPosibleRespuesta() == null
										|| ConsultarPQRD.consultaPQRD.getFechaPosibleRespuesta().isEmpty()) {
									this.mostrarFechaPosibleRespuesta = false;
								} else {
									this.mostrarFechaPosibleRespuesta = true;
								}
								if (ConsultarPQRD.consultaPQRD.getFechaprimeraconsultarespu() == null) {
									dataBaseConection2.actualizarDB(
											"UPDATE comunicacionprqd SET usuarioconsultorespuesta = ?, fechaprimeraconsultarespu = ?, horaprimeraconsultarespue = ? WHERE nroradicacion LIKE ?;",
											this.nroRadicado);
									break;
								}
								break;
							}
							}
							
							RequestContext.getCurrentInstance().execute("PF('result').show();");
						}

						dataBaseConection2.logoutDB();
					} else {
						RequestContext.getCurrentInstance().execute("mensajeErrorRadicado('" + this.nroRadicado + "'," + numeroVerificacionErrado + ")");
						fechaUltCons = fechaHoy;
						++numConsErr;

					}
				} else {
					RequestContext.getCurrentInstance().execute("PF('bloqueoconsulta').show();");
				}

				if (numRadicadoExists) {

					final DataBaseConection baseConection = new DataBaseConection();
					final String pattern2 = "yyyy-MM-dd";
					final DateFormat df2 = new SimpleDateFormat(pattern2);
					final String fecha = df2.format(fechaUltCons);

					baseConection.actualizarConsultasPQRD("UPDATE comunicacionprqd SET fechaultimaconsulta = '" + fecha
							+ "', cantidadconsultaserradase = " + numConsErr + " WHERE nroradicacion LIKE '"
							+ this.nroRadicado + "';");

					baseConection.logoutDB();
				}
				
			} catch (Exception ex) {
				Logger.getLogger(ConsultarPQRD.class.getName()).log(Level.SEVERE, null, ex);
				RequestContext.getCurrentInstance().execute("mensajeError()");
			}
		} else {
			System.err.println("Campos faltantes");
		}
		return "";
	}

	public StreamedContent getFile() {
		try {
			String mimeType = null;
			// final InputStream in =
			// ConsultarPQRD.consultaPQRD.getDatos_archivo_respuesta();
			final Path path = Files.createTempFile("sample", ".txt", (FileAttribute<?>[]) new FileAttribute[0]);
			final File fileTemp = path.toFile();
			final String dirSalida = fileTemp.getAbsolutePath();
			try (final InputStream inputStream = ConsultarPQRD.consultaPQRD.getDatos_archivo_respuesta()) {
				final File fil = new File(dirSalida + ConsultarPQRD.consultaPQRD.getNombre_archivo_respuesta());
				try (final FileOutputStream outputStream = new FileOutputStream(fil)) {
					final int longs = ConsultarPQRD.consultaPQRD.getDatos_archivo_respuesta().available();
					final byte[] bytes = new byte[longs];
					int read;
					while ((read = inputStream.read(bytes)) != -1) {
						outputStream.write(bytes, 0, read);
					}
					inputStream.close();
				}
			}
			final File file1 = new File(dirSalida + ConsultarPQRD.consultaPQRD.getNombre_archivo_respuesta());
			mimeType = URLConnection.guessContentTypeFromName(file1.getName());
			final String extensionArchivo = ConsultarPQRD.consultaPQRD.getNombre_archivo_respuesta();
			final String[] tokens = extensionArchivo.split("\\.(?=[^\\.]+$)");
			ConsultarPQRD.consultaPQRD.setNombre_archivo_respuesta(
					ConsultarPQRD.consultaPQRD.getNro_radicacion_respuesta() + "." + tokens[1]);
			this.file = (StreamedContent) new DefaultStreamedContent((InputStream) new FileInputStream(file1), mimeType,
					ConsultarPQRD.consultaPQRD.getNombre_archivo_respuesta());
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
		return ConsultarPQRD.path_background_image;
	}

	public ConsultaPQRD getConsultaPQRD() {
		return ConsultarPQRD.consultaPQRD;
	}

	public String getUrlOrigen() {
		return ConsultarPQRD.urlOrigen;
	}

	public boolean isFileExist() {
		return ConsultarPQRD.fileExist;
	}

	public boolean isNroRadicadoExist() {
		return this.nroRadicadoExist;
	}

	public String getFondoHeader() {
		return ConsultarPQRD.fondoHeader;
	}

	public String getFondoFooter() {
		return ConsultarPQRD.fondoFooter;
	}

	public String getColorFondoBotones() {
		return ConsultarPQRD.colorFondoBotones;
	}

	public String getColorLetraBotones() {
		return ConsultarPQRD.colorLetraBotones;
	}

	public String getFuenteTiulos() {
		return ConsultarPQRD.fuenteTiulos;
	}

	public String getFuenteEtiquetas() {
		return ConsultarPQRD.fuenteEtiquetas;
	}

	public String getFuenteContenido() {
		return ConsultarPQRD.fuenteContenido;
	}

	public boolean isMostrarFechaPosibleRespuesta() {
		return this.mostrarFechaPosibleRespuesta;
	}

	public String getTextoAlternativoEncabezado() {
		return textoAlternativoEncabezado;
	}

	public String getTextoAlternativoPiedepagina() {
		return textoAlternativoPiedepagina;
	}

	static {
		ConsultarPQRD.fileExist = false;
	}
}