// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Clasificacion.java

package Objetos;


public class Clasificacion
{

    public Clasificacion(int id_clasificacion_pqrd, String nombre_clasificacion_pqrd)
    {
        this.id_clasificacion_pqrd = id_clasificacion_pqrd;
        this.nombre_clasificacion_pqrd = nombre_clasificacion_pqrd;
    }

    public int getId_clasificacion_pqrd()
    {
        return id_clasificacion_pqrd;
    }

    public void setId_clasificacion_pqrd(int id_clasificacion_pqrd)
    {
        this.id_clasificacion_pqrd = id_clasificacion_pqrd;
    }

    public String getNombre_clasificacion_pqrd()
    {
        return nombre_clasificacion_pqrd;
    }

    public void setNombre_clasificacion_pqrd(String nombre_clasificacion_pqrd)
    {
        this.nombre_clasificacion_pqrd = nombre_clasificacion_pqrd;
    }

    private int id_clasificacion_pqrd;
    private String nombre_clasificacion_pqrd;
}
