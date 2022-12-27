// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TipoIdentificacion.java

package Objetos;


public class TipoIdentificacion
{

    public TipoIdentificacion(int id_tipo_identificacion, String nombre_tipo_identificacion)
    {
        this.id_tipo_identificacion = id_tipo_identificacion;
        this.nombre_tipo_identificacion = nombre_tipo_identificacion;
    }

    public int getId_tipo_identificacion()
    {
        return id_tipo_identificacion;
    }

    public void setId_tipo_identificacion(int id_tipo_identificacion)
    {
        this.id_tipo_identificacion = id_tipo_identificacion;
    }

    public String getNombre_tipo_identificacion()
    {
        return nombre_tipo_identificacion;
    }

    public void setNombre_tipo_identificacion(String nombre_tipo_identificacion)
    {
        this.nombre_tipo_identificacion = nombre_tipo_identificacion;
    }

    private int id_tipo_identificacion;
    private String nombre_tipo_identificacion;
}
