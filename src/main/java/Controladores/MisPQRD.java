//Decompiled by Procyon v0.5.36
// 

package Controladores;

import java.util.TimerTask;
import java.nio.file.Path;
import java.util.Timer;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

import org.primefaces.model.DefaultStreamedContent;
import java.io.FileInputStream;
import java.net.URLConnection;
import java.io.FileOutputStream;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.attribute.FileAttribute;
import java.rmi.UnexpectedException;
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
import org.apache.tomcat.util.http.fileupload.FileUtils;
import Utilidades.Util;
import Objetos.Trazabilidad;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.primefaces.model.StreamedContent;

import Objetos.Archivo;
import Objetos.ArchivoFull;
import Objetos.ConsultaPQRD;
import Objetos.Correspondencia;

import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;


@ManagedBean(name = "misPQRD")
@ViewScoped
public class MisPQRD implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8447310530685750423L;
	private String emailConsulta;
	private String nroVerificacion;
	private List<Correspondencia> registros;
   	private Long idArchivo;
	DataBaseConection dataBaseConection1;
	private int tiempoSesionConsulta = 5;
	
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
	
	public List<Correspondencia> getRegistros() {
		return registros;
	}

	public void setRegistros(List<Correspondencia> registros) {
		this.registros = registros;
	}

	public MisPQRD() {
		String tiemposesionconsulta = null;
		try {
			tiemposesionconsulta = Util.getProperties("tiemposesionconsulta");
			if (tiemposesionconsulta != null) {
				setTiempoSesionConsulta(Integer.valueOf(tiemposesionconsulta));	
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.setRegistros(new ArrayList<Correspondencia>());
	}
	
	public int getTiempoSesionConsulta() {
		return tiempoSesionConsulta;
	}

	public void setTiempoSesionConsulta(int tiempoSesionConsulta) {
		this.tiempoSesionConsulta = tiempoSesionConsulta;
	}
	
	@PostConstruct
    public void init() {
		Map<String, String> query = FacesContext.getCurrentInstance()
        .getExternalContext()
        .getRequestParameterMap();
        
		emailConsulta = query.get("formPrincipal:emailConsultar");
		nroVerificacion = query.get("formPrincipal:numVerificacion");
		
		try {
			obtenerRegistros();	
		}
		catch(Exception ex) {
			
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
	
   	public void obtenerRegistros() throws Exception {
   			
			try {

				final DataBaseConection dataBaseConection1 = getConnection(); 
				String query2 = "SELECT fldidcorrespondencia,fecha, \r\n"
						+ "	CONCAT(numeroradicacioninterno, ' ', tiposdedocumentos.nombre) as radicado, \r\n"
						+ "	elasunto,\r\n"
						+ "	case tablarequiererespuestacor\r\n"
						+ "		when 'SI' THEN 'PENDIENTE'\r\n"
						+ "		when 'NO' THEN 'NO REQUIERE RESPUESTA'\r\n"
						+ "		when 'RESPONDIDO' THEN 'RESPONDIDO' end as estado,\r\n"
						+ "	trazabilidad,\r\n"
						+ "	email, \r\n"
						+ "	tablaenviorecibo.tablaenviorecibo,\r\n"
						+ "	tablaexternointerno, \r\n"
						+ "	primergrabado, \r\n"
						+ "	tipomarcacorrespondencia.marca,\r\n"
						+ "	tablasinoadjunto.tablasino AS tieneadjuntos,\r\n"
						+ "	tablasino.tablasino AS requiererespuesta,\r\n"
						+ " LLAVEORDENAMIENTO\r\n"
						+ "FROM CORRESPONDENCIA \r\n"
						+ "INNER JOIN tablaenviorecibo\r\n"
						+ "ON CORRESPONDENCIA.enviorecibo = tablaenviorecibo.fldidtablaenviorecibo\r\n"
						+ "INNER JOIN tablaexternointerno \r\n"
						+ "ON CORRESPONDENCIA.tipoexternointerno = tablaexternointerno.fldidtablaexternointerno\r\n"
						+ "INNER JOIN tipomarcacorrespondencia\r\n"
						+ "ON CORRESPONDENCIA.marca = tipomarcacorrespondencia.fldidtipomarcacorrespondencia\r\n"
						+ "INNER JOIN tiposdedocumentos \r\n"
						+ "ON CORRESPONDENCIA.tipodocumento = tiposdedocumentos.fldidtiposdedocumentos\r\n"
						+ "INNER JOIN tablarequiererespuestacor\r\n"
						+ "ON CORRESPONDENCIA.requiererespuesta = tablarequiererespuestacor.fldidtablarequiererespuestacor\r\n"
						+ "INNER JOIN tablasino\r\n"
						+ "ON CORRESPONDENCIA.esrespuesta = tablasino.fldidtablasino\r\n"
						+ "INNER JOIN tablasino AS tablasinoadjunto\r\n"
						+ "ON CORRESPONDENCIA.tieneadjuntos = tablasinoadjunto.fldidtablasino\r\n"						
						+ "WHERE \r\n"
						+ "lower('?') = ANY (string_to_array(TRIM(REPLACE(REPLACE(lower(CORRESPONDENCIA.email) , ' ', ''), ',', ';')), ';'))\r\n"
						+ "AND tablaenviorecibo.tablaenviorecibo = 'ENTRA'\r\n"
						+ "AND tablaexternointerno = 'EXTERNO'\r\n"
						+ "AND primergrabado = 'SI'\r\n"
						+ "AND tipomarcacorrespondencia.marca IN ('ELABORADO - LEIDO','ELABORADO -  NO LEIDO')\r\n"
						+ "\r\n"
						+ "UNION\r\n"
						+ "SELECT \r\n"
						+ "	fldidcorrespondencia,\r\n"
						+ "	fecha,\r\n"
						+ "	CONCAT(numeroradicacioninterno, ' ', tiposdedocumentos.nombre) as radicado,\r\n"
						+ "	elasunto,\r\n"
						+ "	case tablarequiererespuestacor\r\n"
						+ "		when 'SI' THEN 'PENDIENTE'\r\n"
						+ "		when 'NO' THEN 'NO REQUIERE RESPUESTA'\r\n"
						+ "		when 'RESPONDIDO' THEN 'RESPONDIDO' end as estado,\r\n"
						+ "	trazabilidad,\r\n"
						+ "	email,\r\n"
						+ "	tablaenviorecibo.tablaenviorecibo,\r\n"
						+ "	tablaexternointerno,\r\n"
						+ "	primergrabado,\r\n"
						+ "	tipomarcacorrespondencia.marca,\r\n"
						+ "	tablasinoadjunto.tablasino AS tieneadjuntos,\r\n"
						+ "	tablasino.tablasino AS requiererespuesta,\r\n"
						+ " LLAVEORDENAMIENTO\r\n"
						+ "FROM CORRESPONDENCIA \r\n"
						+ "INNER JOIN tablaenviorecibo\r\n"
						+ "ON CORRESPONDENCIA.enviorecibo = tablaenviorecibo.fldidtablaenviorecibo\r\n"
						+ "INNER JOIN tablaexternointerno \r\n"
						+ "ON CORRESPONDENCIA.tipoexternointerno = tablaexternointerno.fldidtablaexternointerno\r\n"
						+ "LEFT JOIN tipomarcacorrespondencia\r\n"
						+ "ON CORRESPONDENCIA.marca = tipomarcacorrespondencia.fldidtipomarcacorrespondencia\r\n"
						+ "INNER JOIN tiposdedocumentos \r\n"
						+ "ON CORRESPONDENCIA.tipodocumento = tiposdedocumentos.fldidtiposdedocumentos\r\n"
						+ "INNER JOIN tablarequiererespuestacor\r\n"
						+ "ON CORRESPONDENCIA.requiererespuesta = tablarequiererespuestacor.fldidtablarequiererespuestacor\r\n"
						+ "INNER JOIN tablasino\r\n"
						+ "ON CORRESPONDENCIA.esrespuesta = tablasino.fldidtablasino\r\n"
						+ "INNER JOIN tablasino AS tablasinoadjunto\r\n"
						+ "ON CORRESPONDENCIA.tieneadjuntos = tablasinoadjunto.fldidtablasino\r\n"
						+ "WHERE \r\n"
						+ "lower('?') = ANY (string_to_array(TRIM(REPLACE(REPLACE(lower(CORRESPONDENCIA.email) , ' ', ''), ',', ';')), ';'))\r\n"
						+ "AND tablaenviorecibo.tablaenviorecibo = 'SALE'\r\n"
						+ "AND tablaexternointerno = 'EXTERNO'\r\n"
						+ "AND primergrabado = 'SI'\r\n"
						+ "AND tablasino.tablasino = 'NO'\r\n"
						+ "ORDER BY LLAVEORDENAMIENTO;";
				
				 
				query2 = query2.replaceFirst("\\?", this.emailConsulta);
				query2 = query2.replaceFirst("\\?", this.emailConsulta);
				dataBaseConection1.consultarDB(query2);
				final ResultSet resultConsulta1 = dataBaseConection1.getResult();
				
				while (resultConsulta1.next()) {
					Correspondencia correspondencia = new Correspondencia();
					correspondencia.setFldidCorrespondencia(resultConsulta1.getInt("fldidcorrespondencia"));
					correspondencia.setFecha(resultConsulta1.getString("fecha"));
					correspondencia.setRadicado(resultConsulta1.getString("radicado"));
					correspondencia.setElAsunto(resultConsulta1.getString("elasunto"));
					correspondencia.setEstado(resultConsulta1.getString("estado"));
					correspondencia.setTrazabilidad(resultConsulta1.getString("trazabilidad"));
					correspondencia.setTablaenviorecibo(resultConsulta1.getString("tablaenviorecibo"));
					correspondencia.setTieneAdjuntos(resultConsulta1.getString("tieneAdjuntos"));
					correspondencia.setRequiereRespuesta(resultConsulta1.getString("requiererespuesta"));
										
					String tr = correspondencia.getTrazabilidad();
					if(tr != null) {
						tr = tr.replace("\r\n", "<br/>");
						correspondencia.setTrazabilidad(tr);	
					}
					
					getRegistros().add(correspondencia);
				}
				
			} catch (Exception ex) {
				Logger.getLogger(ConsultarPQRD.class.getName()).log(Level.SEVERE, null, ex);
				throw ex;
			}

    	}
   	

   	public void setIdArchivo(Long idArchivo) {
   	    this.idArchivo = idArchivo;
   	}

   	public Long getIdArchivo() {
   	    return idArchivo;
   	}
   	
    public void descargarArchivoUsuario(){
    	descargarArchivoGeneric("usuario");
    }
	public void descargarArchivoRespuesta(){
		descargarArchivoGeneric("respuesta");
	}
    		
    public void descargarArchivoGeneric(String tipo){

        ArchivoFull archivoFull;
    	try {

        if(tipo.equals("usuario")) {
        	archivoFull = obtenerArchivo();
        }
        else {
        	archivoFull = obtenerArchivoRespuesta();
        }
        
        
        byte[] contenido;
        String nombreArchivo = "";
        String extension = "";
        
        if (archivoFull.getArchivos().size() > 1)
        {
        	contenido = archivoFull.comprimirArchivos();
        	extension = ".zip";
        	nombreArchivo = "Radicado " + archivoFull.getNumeroradicacioninterno();
        	nombreArchivo += tipo.equals("usuario") ? "" :
        		" respuesta de " + archivoFull.getNumeroradicacioninterno(); 
        	nombreArchivo += extension;
        }
        else {
        	Archivo archivo = archivoFull.getArchivos().get(0);
        	contenido = archivo.getBytesData();
        	nombreArchivo = archivo.getNombre();
        	if(nombreArchivo != null) {
            	int index = nombreArchivo.lastIndexOf(".");
            	if(index >= 0) {
            		extension = nombreArchivo.substring(index);
            	}
            }
        	nombreArchivo = "Radicado " + archivoFull.getNumeroradicacioninterno(); 
        	nombreArchivo += tipo.equals("usuario") ? "" :
        		" respuesta de " + archivoFull.getNumeroradicacioninterno(); 
        	nombreArchivo += extension;
        }
         
        
        String mime = "application/octet-stream";
        if(nombreArchivo != null && nombreArchivo.trim().toLowerCase().endsWith("pdf")) {
        	mime = "application/pdf";
        }else if(nombreArchivo != null && nombreArchivo.trim().toLowerCase().endsWith("zip")) {
        	mime = "application/zip";
        } 
        	
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();

        ec.responseReset();
        ec.setResponseContentType(mime);
        ec.setResponseContentLength(contenido.length);
        ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + nombreArchivo + "\"");

        OutputStream out = ec.getResponseOutputStream();
        out.write(contenido);
        out.flush();

        fc.responseComplete(); // <--- importante
        
    	} catch (Exception ex) {
			Logger.getLogger(ConsultarPQRD.class.getName()).log(Level.SEVERE, null, ex);
			String msg = ex.getMessage();
			msg = msg.replace("\r\n", "\\r\\n"); 
			RequestContext.getCurrentInstance().execute("mensajeErrorDbg('" + msg + "')");
		}
		finally {
			archivoFull = null;
			if(dataBaseConection1 != null) {
				dataBaseConection1.logoutDB();	
			}
		}
		
    }
    
    public String verificaExisteArchivo() {
    	return verificaExisteArchivoEnvio("usuario"); 
    }

    public String verificaExisteArchivoRespuesta() {
    	return verificaExisteArchivoEnvio("respuesta");
    }

    public String verificaExisteArchivoEnvio(String tipo) {
   
    	try {
    		Archivo archivo;
    		if (tipo.equals("usuario")) {
    			archivo = existeArchivo();	
    		}
    		else {
    			archivo = existeArchivoRespuesta();
    		}
    		
    		if(archivo == null) {
    			String msg = "El archivo adjunto todavía no ha sido cargado en el sistema; favor intentar más tarde";
    			RequestContext.getCurrentInstance().execute("mensajeArchivo('" + msg + "')");
    			return null;
    		}
    		
    		RequestContext.getCurrentInstance().execute("descargaArchivo('" + tipo + "', '" + this.idArchivo + "')");
    		return null;
    		
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
    
    
    
    public ArchivoFull obtenerArchivo() throws Exception {

		ArchivoFull archivoFull = new ArchivoFull();
		try {

			final DataBaseConection dataBaseConection1 = getConnection(); 
			String query2 = "SELECT archivo,nombrearchivoarchivo, tablatipoadjuntocorrespon, numeroradicacioninterno\r\n"
					+ "FROM CORRESPONDENCIA \r\n"
					+ "INNER JOIN tablasino AS tablasinoadjunto\r\n"
					+ "ON CORRESPONDENCIA.tieneadjuntos = tablasinoadjunto.fldidtablasino\r\n"
					+ "INNER JOIN IMAGENESANEXAS \r\n"
					+ "ON CORRESPONDENCIA.fldidcorrespondencia = IMAGENESANEXAS.fldidcorrespondencia\r\n"
					+ "INNER JOIN tablatipoadjuntocorrespon\r\n"
					+ "ON IMAGENESANEXAS.tipoadjunto = tablatipoadjuntocorrespon.fldidtablatipoadjuntocorrespon\r\n"
					+ "WHERE CORRESPONDENCIA.fldidcorrespondencia = ? AND tablatipoadjuntocorrespon = 'ORIGINAL'\r\n"
					+ "AND tablasinoadjunto.tablasino = 'SI'";

			query2 = query2.replaceFirst("\\?", this.idArchivo + "");
			
			dataBaseConection1.consultarDB(query2);
			final ResultSet resultConsulta1 = dataBaseConection1.getResult();
			
			while (resultConsulta1.next()) {
				Archivo archivo = new Archivo();	
				archivo.setBytesData(resultConsulta1.getBytes("archivo"));
				archivo.setNombre(resultConsulta1.getString("nombrearchivoarchivo"));
				archivo.setNumeroradicacioninterno(resultConsulta1.getString("numeroradicacioninterno"));
				archivoFull.getArchivos().add(archivo);
				archivoFull.setNumeroradicacioninterno(resultConsulta1.getString("numeroradicacioninterno"));
			}
			
		} catch (Exception ex) {
			Logger.getLogger(ConsultarPQRD.class.getName()).log(Level.SEVERE, null, ex);
			throw ex;
		}
		
		return archivoFull; 

	}

    public ArchivoFull obtenerArchivoRespuesta() throws Exception {

		ArchivoFull archivoFull = new ArchivoFull();
		try {

			final DataBaseConection dataBaseConection1 = getConnection(); 
			String query2 = "SELECT archivo,nombrearchivoarchivo, tablatipoadjuntocorrespon, numeroradicacioninterno\r\n"
					+ "FROM CORRESPONDENCIA \r\n"
					+ "INNER JOIN tablasino AS tablasinoadjunto\r\n"
					+ "ON CORRESPONDENCIA.tieneadjuntos = tablasinoadjunto.fldidtablasino\r\n"
					+ "INNER JOIN tablarequiererespuestacor\r\n"
					+ "ON CORRESPONDENCIA.requiererespuesta = tablarequiererespuestacor.fldidtablarequiererespuestacor\r\n"
					+ "INNER JOIN IMAGENESANEXAS \r\n"
					+ "ON CORRESPONDENCIA.fldidcorrespondencia = IMAGENESANEXAS.fldidcorrespondencia\r\n"
					+ "INNER JOIN tablatipoadjuntocorrespon\r\n"
					+ "ON IMAGENESANEXAS.tipoadjunto = tablatipoadjuntocorrespon.fldidtablatipoadjuntocorrespon\r\n"
					+ "WHERE CORRESPONDENCIA.fldidcorrespondencia = ? "
					+ "AND tablarequiererespuestacor = 'RESPONDIDO'\r\n"
					+ "AND primergrabado = 'SI'\r\n"
					+ "AND tablasinoadjunto.tablasino = 'SI'\r\n"
					+ "AND tablatipoadjuntocorrespon = 'ORIGINAL'";
			

			query2 = query2.replaceFirst("\\?", this.idArchivo + "");
			
			dataBaseConection1.consultarDB(query2);
			final ResultSet resultConsulta1 = dataBaseConection1.getResult();
			
			while (resultConsulta1.next()) {
				Archivo archivo = new Archivo();	
				archivo.setBytesData(resultConsulta1.getBytes("archivo"));
				archivo.setNombre(resultConsulta1.getString("nombrearchivoarchivo"));
				archivo.setNumeroradicacioninterno(resultConsulta1.getString("numeroradicacioninterno"));
				archivoFull.getArchivos().add(archivo);
				archivoFull.setNumeroradicacioninterno(resultConsulta1.getString("numeroradicacioninterno"));
			}
			
		} catch (Exception ex) {
			Logger.getLogger(ConsultarPQRD.class.getName()).log(Level.SEVERE, null, ex);
			throw ex;
		}
		
		return archivoFull; 

	}

    
    public Archivo existeArchivo() throws Exception {

		Archivo archivo = null;
		try {

			final DataBaseConection dataBaseConection1 = getConnection(); 
			String query2 = "SELECT nombrearchivoarchivo, tablatipoadjuntocorrespon, numeroradicacioninterno\r\n"
					+ "FROM CORRESPONDENCIA \r\n"
					+ "INNER JOIN tablasino AS tablasinoadjunto\r\n"
					+ "ON CORRESPONDENCIA.tieneadjuntos = tablasinoadjunto.fldidtablasino\r\n"
					+ "INNER JOIN IMAGENESANEXAS \r\n"
					+ "ON CORRESPONDENCIA.fldidcorrespondencia = IMAGENESANEXAS.fldidcorrespondencia\r\n"
					+ "INNER JOIN tablatipoadjuntocorrespon\r\n"
					+ "ON IMAGENESANEXAS.tipoadjunto = tablatipoadjuntocorrespon.fldidtablatipoadjuntocorrespon\r\n"
					+ "WHERE CORRESPONDENCIA.fldidcorrespondencia = ? AND tablatipoadjuntocorrespon = 'ORIGINAL'\r\n"
					+ "AND tablasinoadjunto.tablasino = 'SI'";
			query2 = query2.replaceFirst("\\?", this.idArchivo + "");
			
			dataBaseConection1.consultarDB(query2);
			final ResultSet resultConsulta1 = dataBaseConection1.getResult();
			
			while (resultConsulta1.next()) {
				archivo = new Archivo();
				archivo.setNombre(resultConsulta1.getString("nombrearchivoarchivo"));
				archivo.setNumeroradicacioninterno(resultConsulta1.getString("numeroradicacioninterno"));
			}
			
		} catch (Exception ex) {
			Logger.getLogger(ConsultarPQRD.class.getName()).log(Level.SEVERE, null, ex);
			throw ex;
		}
		
		return archivo; 

	}
    
    public Archivo existeArchivoRespuesta() throws Exception {

		Archivo archivo = null;
		try {

			final DataBaseConection dataBaseConection1 = getConnection(); 
			String query2 = "SELECT nombrearchivoarchivo, tablatipoadjuntocorrespon, numeroradicacioninterno\r\n"
					+ "FROM CORRESPONDENCIA \r\n"
					+ "INNER JOIN tablasino AS tablasinoadjunto\r\n"
					+ "ON CORRESPONDENCIA.tieneadjuntos = tablasinoadjunto.fldidtablasino\r\n"
					+ "INNER JOIN tablarequiererespuestacor\r\n"
					+ "ON CORRESPONDENCIA.requiererespuesta = tablarequiererespuestacor.fldidtablarequiererespuestacor\r\n"
					+ "INNER JOIN IMAGENESANEXAS \r\n"
					+ "ON CORRESPONDENCIA.fldidcorrespondencia = IMAGENESANEXAS.fldidcorrespondencia\r\n"
					+ "INNER JOIN tablatipoadjuntocorrespon\r\n"
					+ "ON IMAGENESANEXAS.tipoadjunto = tablatipoadjuntocorrespon.fldidtablatipoadjuntocorrespon\r\n"
					+ "WHERE CORRESPONDENCIA.fldidcorrespondencia = ? "
					+ "AND tablarequiererespuestacor = 'RESPONDIDO'\r\n"
					+ "AND primergrabado = 'SI'\r\n"
					+ "AND tablasinoadjunto.tablasino = 'SI'\r\n"
					+ "AND tablatipoadjuntocorrespon = 'ORIGINAL'";
			
			
			query2 = query2.replaceFirst("\\?", this.idArchivo + "");
			
			dataBaseConection1.consultarDB(query2);
			final ResultSet resultConsulta1 = dataBaseConection1.getResult();
			
			while (resultConsulta1.next()) {
				archivo = new Archivo();
				archivo.setNombre(resultConsulta1.getString("nombrearchivoarchivo"));
				archivo.setNumeroradicacioninterno(resultConsulta1.getString("numeroradicacioninterno"));
			}
			
		} catch (Exception ex) {
			Logger.getLogger(ConsultarPQRD.class.getName()).log(Level.SEVERE, null, ex);
			throw ex;
		}
		
		return archivo; 

	}

}