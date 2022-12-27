// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TipoRespuesta.java

package Objetos;


public class TipoRespuesta
{

    public TipoRespuesta(int id_tipo_respuesta, String nombre_tipo_respuesta)
    {
        this.id_tipo_respuesta = id_tipo_respuesta;
        this.nombre_tipo_respuesta = nombre_tipo_respuesta;
    }

    public int getId_tipo_respuesta()
    {
        return id_tipo_respuesta;
    }

    public void setId_tipo_respuesta(int id_tipo_respuesta)
    {
        this.id_tipo_respuesta = id_tipo_respuesta;
    }

    public String getNombre_tipo_respuesta()
    {
        return nombre_tipo_respuesta;
    }

    public void setNombre_tipo_respuesta(String nombre_tipo_respuesta)
    {
        this.nombre_tipo_respuesta = nombre_tipo_respuesta;
    }

    private int id_tipo_respuesta;
    private String nombre_tipo_respuesta;
}
