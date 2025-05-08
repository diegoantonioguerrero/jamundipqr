//Decompiled by Procyon v0.5.36
// 

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
import Objetos.ConsultaPQRD;
import Objetos.Correspondencia;

import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;


@ManagedBean(name = "misPQRD")
@ViewScoped
public class MisPQRD implements Serializable {
	
	private String emailConsulta;
	private String nroVerificacion;
	private List<Correspondencia> registros;
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
	
	public List<Correspondencia> getRegistros() {
		return registros;
	}

	public void setRegistros(List<Correspondencia> registros) {
		this.registros = registros;
	}


	public MisPQRD() {
		this.setRegistros(new ArrayList<Correspondencia>());
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
						+ "	tipomarcacorrespondencia.marca\r\n"
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
						+ "WHERE '?' = ANY (string_to_array(TRIM(REPLACE(email , ' ', '')), ';'))\r\n"
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
						+ "	tipomarcacorrespondencia.marca\r\n"
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
						+ "WHERE \r\n"
						+ "'?' = ANY (string_to_array(TRIM(REPLACE(email , ' ', '')), ';'))\r\n"
						+ "AND tablaenviorecibo.tablaenviorecibo = 'SALE'\r\n"
						+ "AND tablaexternointerno = 'EXTERNO'\r\n"
						+ "AND primergrabado = 'SI'\r\n"
						+ "AND tablasino = 'NO'\r\n"
						+ "ORDER BY fecha;";
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

	
}