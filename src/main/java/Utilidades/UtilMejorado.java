package Utilidades;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import DataBaseConection.DataBaseConection;
import Objetos.ComunicacionPQRD;

public class UtilMejorado {
	private static final String ORIGINAL = "\301\341\311\351\315\355\323\363\332\372\321\361\334\374";
	private static final String REPLACEMENT = "AaEeIiOoUuNnUu";

	public static String getProperties(String llave) throws Exception {
		String extensionProperties = "/Configuracion/proyectproperties.properties";
		Properties props = new Properties();
		// Usar try-with-resources para asegurar que el stream se cierre automáticamente
		try (InputStream fis = DataBaseConection.class.getResourceAsStream(extensionProperties)) {
			props.load(new InputStreamReader(fis, StandardCharsets.UTF_8));
		}
		return props.getProperty(llave);
	}

	public static void errorMessage(String title, String message) {
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage((String) null, new FacesMessage(FacesMessage.SEVERITY_ERROR, title, message));
	}

	public static boolean sendMail(ComunicacionPQRD comunicacionPQRD) throws Exception {
		Properties props = new Properties();
		props.put("mail.smtp.connectiontimeout", 10000);
		props.put("mail.smtp.timeout", 10000);
		props.put("mail.smtp.auth", "true");
		String sslEmailHabilitado = getProperties("sslEmailHabilitado");
		props.put("mail.smtp.starttls.enable", sslEmailHabilitado);
		props.put("mail.smtp.host", getProperties("hostEmail"));
		props.put("mail.smtp.port", getProperties("puertoEmail"));
		//Se agrega este nuevo protocolo
		props.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
		// props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

		Session session = Session.getInstance(props, new Authenticator() {

			protected PasswordAuthentication getPasswordAuthentication() {
				try {
					String emailRemitente = Util.getProperties("emailRemitente");
					String contrasenaEmailRemitente = Util.getProperties("contrasenaEmailRemitente");
					return new PasswordAuthentication(emailRemitente,contrasenaEmailRemitente);
				} catch (Exception ex) {
					Logger.getLogger(Utilidades.Util.class.getName()).log(Level.SEVERE, null, ex);
				}
				return null;
			}

		});


		String nombrePersona;
		if (comunicacionPQRD.getPrimer_nombre() != null && !comunicacionPQRD.getPrimer_nombre().isEmpty()) {
			nombrePersona = comunicacionPQRD.getPrimer_nombre();
		} else {
			nombrePersona = "";
		}

		String razonSocial;
		if (comunicacionPQRD.getRazon_social() != null && !comunicacionPQRD.getRazon_social().isEmpty()) {
			razonSocial = comunicacionPQRD.getRazon_social();
		} else {
			razonSocial = "";
		}

		String nombreDependencia;
		if (comunicacionPQRD.getNombreDependencia() != null && !comunicacionPQRD.getNombreDependencia().isEmpty()) {
			nombreDependencia = comunicacionPQRD.getNombreDependencia();
		} else {
			nombreDependencia = "";
		}

		String nombreTipoPQRD;
		if (comunicacionPQRD.getNombreTipoPQRD() != null && !comunicacionPQRD.getNombreTipoPQRD().isEmpty()) {
			nombreTipoPQRD = comunicacionPQRD.getNombreTipoPQRD();
		} else {
			nombreTipoPQRD = "";
		}

		String asunto;
		if (comunicacionPQRD.getAsunto() != null && !comunicacionPQRD.getAsunto().isEmpty()) {
			asunto = comunicacionPQRD.getAsunto();
		} else {
			asunto = "";
		}

		String nroRadicacion;
		if (comunicacionPQRD.getNro_radicacion() != null && !comunicacionPQRD.getNro_radicacion().isEmpty()) {
			nroRadicacion = comunicacionPQRD.getNro_radicacion();
		} else {
			nroRadicacion = "";
		}

		String nroVerificacion;
		if (comunicacionPQRD.getNumero_verificacion() != null
				&& !comunicacionPQRD.getNumero_verificacion().isEmpty()) {
			nroVerificacion = comunicacionPQRD.getNumero_verificacion();
		} else {
			nroVerificacion = "";
		}

		String nombreEntidad;
		if (getProperties("nombreEntidad") != null && !getProperties("nombreEntidad").isEmpty()) {
			nombreEntidad = getProperties("nombreEntidad");
			nombreEntidad = quitarTildes(nombreEntidad);
		} else {
			nombreEntidad = "";
		}

		// System.out.println(nombreEntidad);
		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(getProperties("emailRemitente"), nombreEntidad));
		message.setRecipients(RecipientType.TO, InternetAddress.parse(comunicacionPQRD.getEmail()));
		message.setSubject(
				"Solicitud " + comunicacionPQRD.getNro_radicacion() + " a " + getProperties("nombreEntidad"));

		String basePath = Util.getPath();
		String nombreAplicacion = Util.getProperties("nombreAplicacion");
		Path imagePath = Paths.get(basePath, nombreAplicacion, "resources", "img", getProperties("imagenPieCorreo"));
		String OutputPath = imagePath.toString();

		MimeMultipart multipart = new MimeMultipart("related");
		BodyPart messageBodyPart = new MimeBodyPart();
		String htmlText = "<p> Hola<br/>" + nombrePersona + "<br/>" + razonSocial
				+ "<br/><br/>En nuestra web oficial de " + getProperties("nombreEntidad")
				+ " hemos recibido de su parte una solicitud tipo " + nombreTipoPQRD.toUpperCase()
				+ " con destino a la dependencia " + nombreDependencia + " cuyo asunto es: " + asunto
				+ "<br/><br/>El n&uacute;mero de radicaci&oacute;n asignado a su solicitud es " + nroRadicacion
				+ "; Tenga en cuenta esta informaci&oacute;n para hacer seguimiento a su tr&aacute;mite."
				+ "<br/><br/>Este email fue enviado autom&aacute;ticamente.<br/>No responda a este correo.<br/><br/>Cordialmente<br/>"
				+ getProperties("nombreEntidad") + "<br/><br/></p><img src=\"cid:image\">";
		messageBodyPart.setContent(htmlText, "text/html");
		multipart.addBodyPart(messageBodyPart);
		messageBodyPart = new MimeBodyPart();
		DataSource fds = new FileDataSource(OutputPath);
		messageBodyPart.setDataHandler(new DataHandler(fds));
		messageBodyPart.setHeader("Content-ID", "<image>");
		messageBodyPart.setFileName(getProperties("imagenPieCorreo"));
		multipart.addBodyPart(messageBodyPart);
		message.setContent(multipart);
		
		//Se reintenta enviar el correo 3 veces
		for(int i=0; i<3; i++) {
			try {			
				Transport.send(message);
				return true;
			} catch (MessagingException var19) {
				if(i==2)
					logErrorInEmail(var19.getMessage());
			}
		}
		
		return false;
	}
	
	private static void logErrorInEmail(String error) {
		try {
			String basePath = Util.getPath();
			String nombreAplicacion = Util.getProperties("nombreAplicacion");
			// Construir la ruta de forma segura para cualquier SO
			Path logDir = Paths.get(basePath, nombreAplicacion, "resources", "logEmail");

			// Asegurarse de que el directorio exista
			if (Files.notExists(logDir)) {
				Files.createDirectories(logDir);
			}

			LocalDateTime now = LocalDateTime.now();
			String dateForFilename = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			String timestampForLog = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

			String logFileName = dateForFilename + "_logEmail.txt";
			Path logFilePath = logDir.resolve(logFileName);

			String contentToAppend = timestampForLog + " " + error + System.lineSeparator();

			// Escribir en el archivo usando NIO (más moderno y maneja el cierre automáticamente)
			Files.write(logFilePath, contentToAppend.getBytes(StandardCharsets.UTF_8),
					StandardOpenOption.CREATE, StandardOpenOption.APPEND);

		}catch(Exception ex)
		{
			System.out.println("Error logging." + ex.getMessage());
		    ex.printStackTrace();
		}
	}

	public static boolean sendSecondEmail(ComunicacionPQRD comunicacionPQRD) throws Exception {
		Properties props = new Properties();
		props.put("mail.smtp.connectiontimeout", 10000);
		props.put("mail.smtp.timeout", 10000);
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", getProperties("sslEmailHabilitado"));
		props.put("mail.smtp.host", getProperties("hostEmail"));
		props.put("mail.smtp.port", getProperties("puertoEmail"));
		//Se agrega este nuevo protocolo
		props.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
		Session session = Session.getInstance(props, new Authenticator() {

			protected PasswordAuthentication getPasswordAuthentication() {
				try {
					return new PasswordAuthentication(Util.getProperties("emailRemitente"),
							Util.getProperties("contrasenaEmailRemitente"));
				} catch (Exception ex) {
					Logger.getLogger(Utilidades.Util.class.getName()).log(Level.SEVERE, null, ex);
				}
				return null;
			}

		});


		String nombrePersona;
		if (comunicacionPQRD.getPrimer_nombre() != null && !comunicacionPQRD.getPrimer_nombre().isEmpty()) {
			nombrePersona = comunicacionPQRD.getPrimer_nombre();
		} else {
			nombrePersona = "";
		}

		if (comunicacionPQRD.getSegundo_nombre() != null && !comunicacionPQRD.getSegundo_nombre().isEmpty()) {
			nombrePersona = nombrePersona + " " + comunicacionPQRD.getSegundo_nombre();
		}

		if (comunicacionPQRD.getPrimer_apellido() != null && !comunicacionPQRD.getPrimer_apellido().isEmpty()) {
			nombrePersona = nombrePersona + " " + comunicacionPQRD.getPrimer_apellido();
		}

		if (comunicacionPQRD.getSegundo_apellido() != null && !comunicacionPQRD.getSegundo_apellido().isEmpty()) {
			nombrePersona = nombrePersona + " " + comunicacionPQRD.getSegundo_apellido();
		}

		String razonSocial;
		if (comunicacionPQRD.getRazon_social() != null && !comunicacionPQRD.getRazon_social().isEmpty()) {
			razonSocial = comunicacionPQRD.getRazon_social();
		} else {
			razonSocial = "";
		}

		String nombreEntidad;
		if (getProperties("nombreEntidad") != null && !getProperties("nombreEntidad").isEmpty()) {
			nombreEntidad = getProperties("nombreEntidad");
			nombreEntidad = quitarTildes(nombreEntidad);
		} else {
			nombreEntidad = "";
		}

		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(getProperties("emailRemitente"), nombreEntidad));
		message.setRecipients(RecipientType.TO, InternetAddress.parse(comunicacionPQRD.getEmail()));
		message.setSubject("N\u00famero verificaci\u00f3n " + comunicacionPQRD.getNumero_verificacion());
		BodyPart texto = new MimeBodyPart();
		texto.setText("Hola\n" + nombrePersona + "\n" + razonSocial + "\n\nSu n&uacute;mero de verificaci&oacute;n es "
				+ comunicacionPQRD.getNumero_verificacion()
				+ "\n\nEste email fue enviado autom&aacute;ticamente.\nNo responda a este correo.\n\nCordialmente\n"
				+ getProperties("nombreEntidad"));
		MimeMultipart multiParte = new MimeMultipart();
		multiParte.addBodyPart(texto);
		String basePath = Util.getPath();
		String nombreAplicacion = Util.getProperties("nombreAplicacion");
		Path imagePath = Paths.get(basePath, nombreAplicacion, "resources", "img", getProperties("imagenPieCorreo"));
		String OutputPath = imagePath.toString();
		MimeMultipart multipart = new MimeMultipart("related");
		BodyPart messageBodyPart = new MimeBodyPart();
		String htmlText = "<p>Hola<br/>" + nombrePersona + "<br/>" + razonSocial
				+ "<br/><br/>Su n&uacute;mero de verificaci&oacute;n es " + comunicacionPQRD.getNumero_verificacion()
				+ "<br/><br/>Este email fue enviado autom&aacute;ticamente.<br/>No responda a este correo.<br/><br/>Cordialmente<br/>"
				+ nombreEntidad + "<br/><br/></p><img src=\"cid:image\">";
		messageBodyPart.setContent(htmlText, "text/html");
		multipart.addBodyPart(messageBodyPart);
		messageBodyPart = new MimeBodyPart();
		DataSource fds = new FileDataSource(OutputPath);
		messageBodyPart.setDataHandler(new DataHandler(fds));
		messageBodyPart.setHeader("Content-ID", "<image>");
		messageBodyPart.setFileName(getProperties("imagenPieCorreo"));
		multipart.addBodyPart(messageBodyPart);
		message.setContent(multipart);
		
		//return true;
		
		//Se reintenta enviar el correo 3 veces
		for(int i=0; i<3; i++) {
			try {			
				Transport.send(message);
				return true;
			} catch (MessagingException var19) {
				if(i==2)
					logErrorInEmail(var19.getMessage());
			}
		}
		
		return false;

	}

	public static String quitarTildes(String str) {
		if (str == null) {
            return null;
        }
        final char[] array = str.toCharArray();
        for (int index = 0; index < array.length; ++index) {
            final int pos = "\u00c1\u00e1\u00c9\u00e9\u00cd\u00ed\u00d3\u00f3\u00da\u00fa\u00d1\u00f1\u00dc\u00fc".indexOf(array[index]);
            if (pos > -1) {
                array[index] = "AaEeIiOoUuNnUu".charAt(pos);
            }
        }
        return new String(array);
	}

	public static boolean sendMailTest() throws Exception {
		Properties props = new Properties();
			
		props.put("mail.smtp.connectiontimeout", 10000);
		props.put("mail.smtp.timeout", 10000);
		props.put("mail.smtp.auth", "true");
		String sslEmailHabilitado = getProperties("sslEmailHabilitado");
		props.put("mail.smtp.starttls.enable", sslEmailHabilitado);
		//props.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
		props.put("mail.smtp.host", getProperties("hostEmail"));
		props.put("mail.smtp.port", getProperties("puertoEmail"));
		//props.put("mail.smtp.ssl.trust", "*");

		Session session = Session.getInstance(props, new Authenticator() {

			protected PasswordAuthentication getPasswordAuthentication() {
				try {
					String emailRemitente = Util.getProperties("emailRemitente");
					String contrasenaEmailRemitente = Util.getProperties("contrasenaEmailRemitente");
					return new PasswordAuthentication(emailRemitente,contrasenaEmailRemitente);
				} catch (Exception ex) {
					Logger.getLogger(Utilidades.Util.class.getName()).log(Level.SEVERE, null, ex);
				}
				return null;
			}

		});

		try {
			String nombrePersona = "";
			String razonSocial = "";
			String nombreDependencia = "";
			String nombreTipoPQRD = "";
			String asunto = "";
			String nroRadicacion = "";
			String nroVerificacion = "";
			String nombreEntidad;
			if (getProperties("nombreEntidad") != null && !getProperties("nombreEntidad").isEmpty()) {
				nombreEntidad = getProperties("nombreEntidad");
				nombreEntidad = quitarTildes(nombreEntidad);
			} else {
				nombreEntidad = "";
			}
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(getProperties("emailRemitente"), "Test"));
			message.setRecipients(RecipientType.TO, InternetAddress.parse("diegoantonioguerrero@gmail.com"));
			message.setSubject("Solicitud " + "123" + " a " + getProperties("nombreEntidad"));

			String basePath = Util.getPath();
			String nombreAplicacion = Util.getProperties("nombreAplicacion");
			Path imagePath = Paths.get(basePath, nombreAplicacion, "resources", "img", getProperties("imagenPieCorreo"));
			String OutputPath = imagePath.toString();

			MimeMultipart multipart = new MimeMultipart("related");
			BodyPart messageBodyPart = new MimeBodyPart();
			String htmlText = "<p> Hola<br/>" + nombrePersona + "<br/>" + razonSocial
					+ "<br/><br/>En nuestra web oficial de " + getProperties("nombreEntidad")
					+ " hemos recibido de su parte una solicitud tipo " + nombreTipoPQRD.toUpperCase()
					+ " con destino a la dependencia " + nombreDependencia + " cuyo asunto es: " + asunto
					+ "<br/><br/>El n&uacute;mero de radicaci&oacute;n asignado a su solicitud es " + nroRadicacion
					+ " y el n&uacute;mero de verificaci&oacute;n asignado es " + nroVerificacion
					+ "; Tenga en cuenta estos datos para hacer seguimiento a su tr&aacute;mite.<br/><br/>Este email fue enviado autom&aacute;ticamente.<br/>No responda a este correo.<br/><br/>Cordialmente<br/>"
					+ getProperties("nombreEntidad") + "<br/><br/></p><img src=\"cid:image\">";
			messageBodyPart.setContent(htmlText, "text/html");
			multipart.addBodyPart(messageBodyPart);
			messageBodyPart = new MimeBodyPart();
			DataSource fds = new FileDataSource(OutputPath);
			messageBodyPart.setDataHandler(new DataHandler(fds));
			messageBodyPart.setHeader("Content-ID", "<image>");
			messageBodyPart.setFileName(getProperties("imagenPieCorreo"));
			multipart.addBodyPart(messageBodyPart);
			message.setContent(multipart);
			Transport.send(message);
			return true;
		} catch (MessagingException var19) {
			System.err.println(var19);
			return false;
		}
	}
	
	public static String getPath() throws UnsupportedEncodingException {
	    String path = Util.class.getClassLoader().getResource("").getPath();
	    String fullPath = URLDecoder.decode(path, "UTF-8");
	    String pathArr[] = fullPath.split("/WEB-INF/classes/");
	    if (pathArr.length > 0) {
	        fullPath = pathArr[0];
	        // Devuelve la ruta base de la aplicación web, ej: /path/to/tomcat/webapps/jamundipqr
	        return new File(fullPath).getParent();
	    }
	    return null; // O manejar el error de otra forma

	}

}
