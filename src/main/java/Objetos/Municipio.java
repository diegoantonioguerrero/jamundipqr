// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Municipio.java

package Objetos;


public class Municipio
{

    public Municipio(int fldidmunicipios, String nombre)
    {
        this.fldidmunicipios = fldidmunicipios;
        this.nombre = nombre;
    }

    public int getFldidmunicipios()
    {
        return fldidmunicipios;
    }

    public void setFldidmunicipios(int fldidmunicipios)
    {
        this.fldidmunicipios = fldidmunicipios;
    }

    public String getNombre()
    {
        return nombre;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    private int fldidmunicipios;
    private String nombre;
}
