package Controladores;

import DataBaseConection.DataBaseConection;
import Objetos.ComunicacionPQRD;
import Objetos.Correspondencia;
import Utilidades.Util;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import org.primefaces.context.RequestContext;

@ManagedBean
@RequestScoped
public class NumVerificacion {
	private static String path_background_image;
	private static String urlOrigen;
	private static String fondoHeader;
	private static String fondoFooter;
	private static String colorFondoBotones;
	private static String colorLetraBotones;
	private static String fuenteTiulos;
	private static String fuenteEtiquetas;
	private static String fuenteContenido;
	private String emailIngresado;
	private static String textoAlternativoEncabezado;
	private static String textoAlternativoPiedepagina;

	public NumVerificacion() {
		try {
			path_background_image = Util.getProperties("imagenFondo");
			fondoHeader = Util.getProperties("imagenFondoHeader");
			fondoFooter = Util.getProperties("imagenFondoFooter");
			colorFondoBotones = Util.getProperties("colorFondoBotones");
			colorLetraBotones = Util.getProperties("colorLetraBotones");
			fuenteTiulos = Util.getProperties("fuenteTiulos");
			fuenteEtiquetas = Util.getProperties("fuenteEtiquetas");
			fuenteContenido = Util.getProperties("fuenteContenido");
			urlOrigen = Util.getProperties("linkOrigen");
			textoAlternativoEncabezado = Util.getProperties("textoAlternativoEncabezado");
			textoAlternativoPiedepagina = Util.getProperties("textoAlternativoPiedepagina");
		} catch (Exception var2) {
			Logger.getLogger(ConsultarPQRD.class.getName()).log(Level.SEVERE, (String) null, var2);
		}

	}

	public String validarVerificacion(boolean cambiador) throws Exception {
		try {
			DataBaseConection baseConection = new DataBaseConection();
			baseConection.consultarDB(
					"SELECT fldidcomunicacionprqd, nombre1, nombre2, apellido1, apellido2, razonsocial FROM comunicacionprqd WHERE email LIKE '"
							+ this.emailIngresado + "' ORDER BY fldidcomunicacionprqd DESC LIMIT 1;");
			ResultSet resultConsulta = baseConection.getResult();
			ComunicacionPQRD comunicacionPQRD = new ComunicacionPQRD();

			int id;
			for (id = 0; resultConsulta.next(); id = resultConsulta.getInt("fldidcomunicacionprqd")) {
				comunicacionPQRD.setPrimer_nombre(resultConsulta.getString("nombre1"));
				comunicacionPQRD.setSegundo_nombre(resultConsulta.getString("nombre2"));
				comunicacionPQRD.setPrimer_apellido(resultConsulta.getString("apellido1"));
				comunicacionPQRD.setSegundo_apellido(resultConsulta.getString("apellido2"));
				comunicacionPQRD.setRazon_social(resultConsulta.getString("razonsocial"));
				comunicacionPQRD.setEmail(this.emailIngresado);
			}

			if (id != 0) {
				String newNumVerificacion = String.valueOf((int) Math.floor(Math.random() * 900000.0D + 100000.0D));
				comunicacionPQRD.setNumero_verificacion(newNumVerificacion);
				if (Util.sendSecondEmail(comunicacionPQRD)) {
					baseConection.actualizarConsultasPQRD("UPDATE comunicacionprqd SET numeroverificacion = '"
							+ newNumVerificacion + "' WHERE fldidcomunicacionprqd = " + id);
				} else {
					if(!cambiador) {
						throw new Exception("Error al enviar el segundoEmail");
					}
					System.err.println("Error al enviar el segundoEmail");
				}
			} else {
				if(!cambiador) {
					throw new Exception("No se encontro la persona segundoEmail");
				}
				System.err.println("No se encontro la persona segundoEmail");
			}

			baseConection.logoutDB();
			if (cambiador) {
				RequestContext.getCurrentInstance().execute("cambiador()");	
			}
			
		} catch (SQLException var6) {
			Logger.getLogger(NumVerificacion.class.getName()).log(Level.SEVERE, (String) null, var6);
			if(!cambiador) {
				throw var6;
			}
		} catch (Exception var7) {
			Logger.getLogger(NumVerificacion.class.getName()).log(Level.SEVERE, (String) null, var7);
			if(!cambiador) {
				throw var7;
			}
		}

		return "";
	}
	
	public String enviarCorreo(Correspondencia correspondencia) throws Exception {
		try {

			ComunicacionPQRD comunicacionPQRD = new ComunicacionPQRD();

			comunicacionPQRD.setPrimer_nombre(correspondencia.getNombre());
			//comunicacionPQRD.setSegundo_nombre(resultConsulta.getString("nombre2"));
			//comunicacionPQRD.setPrimer_apellido(resultConsulta.getString("apellido1"));
			//comunicacionPQRD.setSegundo_apellido(resultConsulta.getString("apellido2"));
			//comunicacionPQRD.setRazon_social(resultConsulta.getString("razonsocial"));
			comunicacionPQRD.setEmail(correspondencia.getEmail());

			String newNumVerificacion = Integer.toString(correspondencia.getNumVerificacion());
			comunicacionPQRD.setNumero_verificacion(newNumVerificacion);
			if (!Util.sendSecondEmail(comunicacionPQRD)) {
				throw new Exception("Error al enviar el email");
				/*baseConection.actualizarConsultasPQRD("UPDATE comunicacionprqd SET numeroverificacion = '"

						+ newNumVerificacion + "' WHERE fldidcomunicacionprqd = " + id);*/

			}
		} catch (Exception var7) {
			Logger.getLogger(NumVerificacion.class.getName()).log(Level.SEVERE, (String) null, var7);
			throw var7;
		}

		return "";
	}


	public String getPath_background_image() {
		return path_background_image;
	}

	public String getUrlOrigen() {
		return urlOrigen;
	}

	public String getFondoHeader() {
		return fondoHeader;
	}

	public String getFondoFooter() {
		return fondoFooter;
	}

	public String getColorFondoBotones() {
		return colorFondoBotones;
	}

	public String getColorLetraBotones() {
		return colorLetraBotones;
	}

	public String getFuenteTiulos() {
		return fuenteTiulos;
	}

	public String getFuenteEtiquetas() {
		return fuenteEtiquetas;
	}

	public String getFuenteContenido() {
		return fuenteContenido;
	}

	public String getEmailIngresado() {
		return this.emailIngresado;
	}

	public void setEmailIngresado(String emailIngresado) {
		this.emailIngresado = emailIngresado;
	}

	public String getTextoAlternativoEncabezado() {
		return textoAlternativoEncabezado;
	}

	public String getTextoAlternativoPiedepagina() {
		return textoAlternativoPiedepagina;
	}
}
