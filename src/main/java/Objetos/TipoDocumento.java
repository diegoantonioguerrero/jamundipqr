// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TipoDocumento.java

package Objetos;


public class TipoDocumento
{

    public TipoDocumento(int id_tipo_documento, String nombre_tipo_documento)
    {
        this.id_tipo_documento = id_tipo_documento;
        this.nombre_tipo_documento = nombre_tipo_documento;
    }

    public int getId_tipo_documento()
    {
        return id_tipo_documento;
    }

    public void setId_tipo_documento(int id_tipo_documento)
    {
        this.id_tipo_documento = id_tipo_documento;
    }

    public String getNombre_tipo_documento()
    {
        return nombre_tipo_documento;
    }

    public void setNombre_tipo_documento(String nombre_tipo_documento)
    {
        this.nombre_tipo_documento = nombre_tipo_documento;
    }

    private int id_tipo_documento;
    private String nombre_tipo_documento;
}
