package Utilidades;

import DataBaseConection.DataBaseConection;
import Objetos.ComunicacionPQRD;

import java.io.*;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Enumeration;
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
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import Controladores.CreateZip;

public class Util {
	private static final String ORIGINAL = "\301\341\311\351\315\355\323\363\332\372\321\361\334\374";
	private static final String REPLACEMENT = "AaEeIiOoUuNnUu";

	public static String getProperties(String llave) throws Exception {
		String extencionProperties = "/Configuracion/proyectproperties.properties";
		Properties props = new Properties();
		InputStream fis = DataBaseConection.class.getResourceAsStream(extencionProperties);
		props.load(new InputStreamReader(fis, StandardCharsets.UTF_8));
		fis.close();
		Enumeration ruta2 = props.keys();

		String key;
		do {
			if (!ruta2.hasMoreElements()) {
				return null;
			}

			key = (String) ruta2.nextElement();
		} while (!key.equals(llave));

		return props.getProperty(key);
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
		//File archivo = new File("temporalFile");
		//String mainPath = archivo.getAbsolutePath();
		String mainPath = Util.getPath();
		mainPath = mainPath.replace("temporalFile", "webapps\\");
		mainPath = mainPath.replace("bin\\temporalFile", "webapps\\");
		//mainPath = mainPath + getProperties("nombreAplicacion") + "\\resources\\img\\";
	    String nombreAplicacion = Util.getProperties("nombreAplicacion");
	    if(!mainPath.toLowerCase().endsWith(nombreAplicacion)) {
	    	mainPath = mainPath + "\\" + nombreAplicacion;  
	    }
	    mainPath = mainPath + "\\resources\\img\\";
		String OutputPath = mainPath + getProperties("imagenPieCorreo");
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
		String mainPath = Util.getPath();
		  mainPath = mainPath.replace("bin\\temporalFile", "webapps\\");
		  //String nombreAplicacion = Util.getProperties("nombreAplicacion");
		  LocalDateTime date = LocalDateTime.now();
		  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		  String text = date.format(formatter);
		  mainPath = mainPath + "\\resources\\logEmail\\";
		  File file = new File(mainPath);
		  if(!file.exists()) {
			  file.mkdir();
		  }
		  mainPath += text + "_logEmail.txt";
		  //Writer writer = null;
		  mainPath = mainPath.replace("\\", "/");
		  file = new File(mainPath);
		  try {
			  formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			  text = date.format(formatter);
			  Path filePath = Paths.get(file.getAbsolutePath());
			  Files.writeString(filePath, text + " " + error + "\r\n", StandardOpenOption.CREATE, StandardOpenOption.APPEND);
					 /* writer = new BufferedWriter(new OutputStreamWriter(
		            new FileOutputStream(mainPath), "utf-8"));
		  writer.write(text + " " + error + "\r\n");*/
		  } catch (IOException ex) {
		      // Report
		  System.out.println("An error occurred." + ex.getMessage());
		      ex.printStackTrace();
		  } finally {
		     /*try {
		    	 if(writer != null) {
		    		 writer.close();
		    	 }
		     } catch (Exception ex) {
		     	//ignore
		     }*/
		  }
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
				+ nombreEntidad);
		MimeMultipart multiParte = new MimeMultipart();
		multiParte.addBodyPart(texto);
		message.setContent(multiParte);
		//File archivo = new File("temporalFile");
		//String mainPath = archivo.getAbsolutePath();
		String mainPath = Util.getPath();
		mainPath = mainPath.replace("temporalFile", "webapps\\");
		mainPath = mainPath.replace("bin\\temporalFile", "webapps\\");
		//mainPath = mainPath + getProperties("nombreAplicacion") + "\\resources\\img\\";
	    String nombreAplicacion = Util.getProperties("nombreAplicacion");
	    if(!mainPath.toLowerCase().endsWith(nombreAplicacion)) {
	    	mainPath = mainPath + "\\" + nombreAplicacion;  
	    }
	    mainPath = mainPath + "\\resources\\img\\";
		String OutputPath = mainPath + getProperties("imagenPieCorreo");
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
		
		return true;
		/*
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
		
		return false;*/

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
			File archivo = new File("temporalFile");
			String mainPath = archivo.getAbsolutePath();
			mainPath = mainPath.replace("temporalFile", "webapps\\");
			mainPath = mainPath.replace("bin\\temporalFile", "webapps\\");
			mainPath = mainPath + getProperties("nombreAplicacion") + "\\resources\\img\\";
			String OutputPath = mainPath + getProperties("imagenPieCorreo");
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
	    // System.out.println(fullPath);
	    // System.out.println(pathArr[0]);
	    fullPath = pathArr[0];

	    return fullPath;

	}

}
