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
import java.util.Random;

import org.primefaces.model.StreamedContent;
import Objetos.ConsultaPQRD;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.bean.ManagedBean;


//@ManagedBean
//@RequestScoped
@ManagedBean(name = "misPQRD")
@ViewScoped
public class MisPQRD implements Serializable {
	
	private String emailConsulta;
	private String nroVerificacion;
	

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

	

	public MisPQRD() {
		obtenerVariable();
		
	}
	
	// Método para obtener la variable del bean de origen
    public void obtenerVariable() {
        // Usamos el contexto de JSF para obtener la instancia del bean
        OpcionesPQRD opcionesPQRD = (OpcionesPQRD) FacesContext.getCurrentInstance()
            .getExternalContext().getApplicationMap().get("opcionesPQRD");

        if (opcionesPQRD != null) {
            this.emailConsulta = opcionesPQRD.getEmailConsulta();
        }
    }
}