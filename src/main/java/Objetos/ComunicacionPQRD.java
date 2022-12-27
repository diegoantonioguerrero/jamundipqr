// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ComunicacionPQRD.java

package Objetos;

import java.io.File;
import java.util.Date;

public class ComunicacionPQRD
{

    public ComunicacionPQRD()
    {
    	this.fechaHoraRealizacionFormat = "fechaHoraRealizacionFormat";
    }

    public String getNumero_verificacion()
    {
        return numero_verificacion;
    }

    public void setNumero_verificacion(String numero_verificacion)
    {
        this.numero_verificacion = numero_verificacion;
    }

    public int getId_comunicacionpqrd()
    {
        return id_comunicacionpqrd;
    }

    public void setId_comunicacionpqrd(int id_comunicacionpqrd)
    {
        this.id_comunicacionpqrd = id_comunicacionpqrd;
    }

    public Date getFechaHora()
    {
        return fechaHora;
    }

    public void setFechaHora(Date fechaHora)
    {
        this.fechaHora = fechaHora;
    }

    public String getAsunto()
    {
        return asunto;
    }

    public void setAsunto(String asunto)
    {
        this.asunto = asunto;
    }

    public String getDetalle()
    {
        return detalle;
    }

    public void setDetalle(String detalle)
    {
        this.detalle = detalle;
    }

    public File getFileAdjunto()
    {
        return fileAdjunto;
    }

    public void setFileAdjunto(File fileAdjunto)
    {
        this.fileAdjunto = fileAdjunto;
    }

    public String getNombre_archivo_viejo()
    {
        return nombre_archivo_viejo;
    }

    public void setNombre_archivo_viejo(String nombre_archivo_viejo)
    {
        this.nombre_archivo_viejo = nombre_archivo_viejo;
    }

    public String getNombre_archivo_adjunto()
    {
        return nombre_archivo_adjunto;
    }

    public void setNombre_archivo_adjunto(String nombre_archivo_adjunto)
    {
        this.nombre_archivo_adjunto = nombre_archivo_adjunto;
    }

    public String getNombrearchivoarchivoadjuntorar()
    {
        return nombrearchivoarchivoadjuntorar;
    }

    public void setNombrearchivoarchivoadjuntorar(String nombrearchivoarchivoadjuntorar)
    {
        this.nombrearchivoarchivoadjuntorar = nombrearchivoarchivoadjuntorar;
    }

    public File getFileZip()
    {
        return fileZip;
    }

    public void setFileZip(File fileZip)
    {
        this.fileZip = fileZip;
    }

    public int getLongitudBytesRar()
    {
        return longitudBytesRar;
    }

    public void setLongitudBytesRar(int longitudBytesRar)
    {
        this.longitudBytesRar = longitudBytesRar;
    }

    public String getNro_radicacion()
    {
        return nro_radicacion;
    }

    public void setNro_radicacion(String nro_radicacion)
    {
        this.nro_radicacion = nro_radicacion;
    }

    public int getId_estado()
    {
        return id_estado;
    }

    public void setId_estado(int id_estado)
    {
        this.id_estado = id_estado;
    }

    public int getId_usuario()
    {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario)
    {
        this.id_usuario = id_usuario;
    }

    public int getId_tipo_pqrd()
    {
        return id_tipo_pqrd;
    }

    public void setId_tipo_pqrd(int id_tipo_pqrd)
    {
        this.id_tipo_pqrd = id_tipo_pqrd;
    }

    public String getNombreTipoPQRD()
    {
        return nombreTipoPQRD;
    }

    public void setNombreTipoPQRD(String nombreTipoPQRD)
    {
        this.nombreTipoPQRD = nombreTipoPQRD;
    }

    public int getId_clasificacion_pqrd()
    {
        return id_clasificacion_pqrd;
    }

    public void setId_clasificacion_pqrd(int id_clasificacion_pqrd)
    {
        this.id_clasificacion_pqrd = id_clasificacion_pqrd;
    }

    public String getNombreClasificacion()
    {
        return nombreClasificacion;
    }

    public void setNombreClasificacion(String nombreClasificacion)
    {
        this.nombreClasificacion = nombreClasificacion;
    }

    public int getId_tipo_respuesta()
    {
        return id_tipo_respuesta;
    }

    public void setId_tipo_respuesta(int id_tipo_respuesta)
    {
        this.id_tipo_respuesta = id_tipo_respuesta;
    }

    public String getNombreTipoRespuesta()
    {
        return nombreTipoRespuesta;
    }

    public void setNombreTipoRespuesta(String nombreTipoRespuesta)
    {
        this.nombreTipoRespuesta = nombreTipoRespuesta;
    }

    public int getId_dependencia()
    {
        return id_dependencia;
    }

    public void setId_dependencia(int id_dependencia)
    {
        this.id_dependencia = id_dependencia;
    }

    public String getNombreDependencia()
    {
        return nombreDependencia;
    }

    public void setNombreDependencia(String nombreDependencia)
    {
        this.nombreDependencia = nombreDependencia;
    }

    public String getTipo_identificacion()
    {
        return tipo_identificacion;
    }

    public void setTipo_identificacion(String tipo_identificacion)
    {
        this.tipo_identificacion = tipo_identificacion;
    }

    public String getPrimer_nombre()
    {
        return primer_nombre;
    }

    public void setPrimer_nombre(String primer_nombre)
    {
        this.primer_nombre = primer_nombre;
    }

    public String getSegundo_nombre()
    {
        return segundo_nombre;
    }

    public void setSegundo_nombre(String segundo_nombre)
    {
        this.segundo_nombre = segundo_nombre;
    }

    public String getPrimer_apellido()
    {
        return primer_apellido;
    }

    public void setPrimer_apellido(String primer_apellido)
    {
        this.primer_apellido = primer_apellido;
    }

    public String getSegundo_apellido()
    {
        return segundo_apellido;
    }

    public void setSegundo_apellido(String segundo_apellido)
    {
        this.segundo_apellido = segundo_apellido;
    }

    public String getNro_identificacion()
    {
        return nro_identificacion;
    }

    public void setNro_identificacion(String nro_identificacion)
    {
        this.nro_identificacion = nro_identificacion;
    }

    public String getRazon_social()
    {
        return razon_social;
    }

    public void setRazon_social(String razon_social)
    {
        this.razon_social = razon_social;
    }

    public String getCelular()
    {
        return celular;
    }

    public void setCelular(String celular)
    {
        this.celular = celular;
    }

    public String getDireccion()
    {
        return direccion;
    }

    public void setDireccion(String direccion)
    {
        this.direccion = direccion;
    }

    public int getId_ciudad()
    {
        return id_ciudad;
    }

    public void setId_ciudad(int id_ciudad)
    {
        this.id_ciudad = id_ciudad;
    }

    public String getNombre_ciudad()
    {
        return nombre_ciudad;
    }

    public void setNombre_ciudad(String nombre_ciudad)
    {
        this.nombre_ciudad = nombre_ciudad;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public int getId_tipo_documento()
    {
        return id_tipo_documento;
    }

    public void setId_tipo_documento(int id_tipo_documento)
    {
        this.id_tipo_documento = id_tipo_documento;
    }

    public String getNombreTipoDocumento()
    {
        return nombreTipoDocumento;
    }

    public void setNombreTipoDocumento(String nombreTipoDocumento)
    {
        this.nombreTipoDocumento = nombreTipoDocumento;
    }

    public String getTipo_persona()
    {
        return tipo_persona;
    }

    public void setTipo_persona(String tipo_persona)
    {
        this.tipo_persona = tipo_persona;
    }

    public String getHoraRealizacion()
    {
        return horaRealizacion;
    }

    public void setHoraRealizacion(String horaRealizacion)
    {
        this.horaRealizacion = horaRealizacion;
    }

    public String getFechaHoraRealizacionFormat() {
		return fechaHoraRealizacionFormat;
	}

	public void setFechaHoraRealizacionFormat(String horaRealizacionFormat) {
		this.fechaHoraRealizacionFormat = horaRealizacionFormat;
	}

	private int id_comunicacionpqrd;
    private Date fechaHora;
    private String asunto;
    private String detalle;
    private String nombre_archivo_viejo;
    private File fileAdjunto;
    private File fileZip;
    private String nombre_archivo_adjunto;
    private String nombrearchivoarchivoadjuntorar;
    private int longitudBytesRar;
    private String nro_radicacion;
    private int id_estado;
    private int id_usuario;
    private int id_tipo_pqrd;
    private String nombreTipoPQRD;
    private int id_clasificacion_pqrd;
    private String nombreClasificacion;
    private int id_tipo_respuesta;
    private String nombreTipoRespuesta;
    private int id_dependencia;
    private String nombreDependencia;
    private String tipo_identificacion;
    private String horaRealizacion;
    private String primer_nombre;
    private String segundo_nombre;
    private String primer_apellido;
    private String segundo_apellido;
    private String nro_identificacion;
    private String razon_social;
    private String celular;
    private String direccion;
    private int id_ciudad;
    private String nombre_ciudad;
    private String email;
    private int id_tipo_documento;
    private String nombreTipoDocumento;
    private String tipo_persona;
    private String numero_verificacion;
    private String fechaHoraRealizacionFormat;

}
