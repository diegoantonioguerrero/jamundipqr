// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ConsultaPQRD.java

package Objetos;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

public class ConsultaPQRD
{

    public ConsultaPQRD()
    {
        trazabilidad = new ArrayList();
    }

    public List getTrazabilidad()
    {
        return trazabilidad;
    }

    public void setTrazabilidad(List trazabilidad)
    {
        this.trazabilidad = trazabilidad;
    }

    public String getNro_radicacion_respuesta()
    {
        return nro_radicacion_respuesta;
    }

    public void setNro_radicacion_respuesta(String nro_radicacion_respuesta)
    {
        this.nro_radicacion_respuesta = nro_radicacion_respuesta;
    }

    public InputStream getDatos_archivo_respuesta()
    {
        return datos_archivo_respuesta;
    }

    public void setDatos_archivo_respuesta(InputStream datos_archivo_respuesta)
    {
        this.datos_archivo_respuesta = datos_archivo_respuesta;
    }

    public String getNombre_archivo_respuesta()
    {
        return nombre_archivo_respuesta;
    }

    public void setNombre_archivo_respuesta(String nombre_archivo_respuesta)
    {
        this.nombre_archivo_respuesta = nombre_archivo_respuesta;
    }

    public String getContenttype_archivo_respuesta()
    {
        return contenttype_archivo_respuesta;
    }

    public void setContenttype_archivo_respuesta(String contenttype_archivo_respuesta)
    {
        this.contenttype_archivo_respuesta = contenttype_archivo_respuesta;
    }

    public Date getFecha_respuesta()
    {
        return fecha_respuesta;
    }

    public void setFecha_respuesta(Date fecha_respuesta)
    {
        this.fecha_respuesta = fecha_respuesta;
    }

    public String getFechaPosibleRespuesta()
    {
        return fechaPosibleRespuesta;
    }

    public void setFechaPosibleRespuesta(String fechaPosibleRespuesta)
    {
        this.fechaPosibleRespuesta = fechaPosibleRespuesta;
    }

    public String getEstado()
    {
        return estado;
    }

    public void setEstado(String estado)
    {
        this.estado = estado;
    }

    public String getFechaRespuestaString()
    {
        if(fecha_respuesta == null)
            return "";
        else
            return (new SimpleDateFormat("yyyy/MM/dd")).format(fecha_respuesta);
    }

    public boolean isMostrarFecha()
    {
        return false;
    }

    public boolean isMostrarRadicadoRespuesta()
    {
        return nro_radicacion_respuesta != null;
    }

    public boolean isMostrarDescargarRespuesta()
    {
        return datos_archivo_respuesta != null;
    }

    public Date getFechaprimeraconsultarespu()
    {
        return fechaprimeraconsultarespu;
    }

    public void setFechaprimeraconsultarespu(Date fechaprimeraconsultarespu)
    {
        this.fechaprimeraconsultarespu = fechaprimeraconsultarespu;
    }

    private String nro_radicacion_respuesta;
    private InputStream datos_archivo_respuesta;
    private String nombre_archivo_respuesta;
    private String contenttype_archivo_respuesta;
    private Date fecha_respuesta;
    private String estado;
    private String fechaRespuestaString;
    private String fechaPosibleRespuesta;
    private List trazabilidad;
    private Date fechaprimeraconsultarespu;
}
