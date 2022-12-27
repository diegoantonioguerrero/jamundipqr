// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TipoPersona.java

package Objetos;


public class TipoPersona
{

    public TipoPersona(int id_tipo_persona, String nombre_tipo_persona)
    {
        this.id_tipo_persona = id_tipo_persona;
        this.nombre_tipo_persona = nombre_tipo_persona;
    }

    public int getId_tipo_persona()
    {
        return id_tipo_persona;
    }

    public void setId_tipo_persona(int id_tipo_persona)
    {
        this.id_tipo_persona = id_tipo_persona;
    }

    public String getNombre_tipo_persona()
    {
        return nombre_tipo_persona;
    }

    public void setNombre_tipo_persona(String nombre_tipo_persona)
    {
        this.nombre_tipo_persona = nombre_tipo_persona;
    }

    private int id_tipo_persona;
    private String nombre_tipo_persona;
}
