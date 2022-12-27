// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Dependencia.java

package Objetos;


public class Dependencia
{

    public Dependencia(int id_dependencia, String nombre_dependencia)
    {
        this.id_dependencia = id_dependencia;
        this.nombre_dependencia = nombre_dependencia;
    }

    public int getId_dependencia()
    {
        return id_dependencia;
    }

    public void setId_dependencia(int id_dependencia)
    {
        this.id_dependencia = id_dependencia;
    }

    public String getNombre_dependencia()
    {
        return nombre_dependencia;
    }

    public void setNombre_dependencia(String nombre_dependencia)
    {
        this.nombre_dependencia = nombre_dependencia;
    }

    private int id_dependencia;
    private String nombre_dependencia;
}
