// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TipoPqrd.java

package Objetos;


public class TipoPqrd
{

    public TipoPqrd(int id_tipo_pqrd, String nombre_tipo_pqrd)
    {
        this.id_tipo_pqrd = id_tipo_pqrd;
        this.nombre_tipo_pqrd = nombre_tipo_pqrd;
    }

    public int getId_tipo_pqrd()
    {
        return id_tipo_pqrd;
    }

    public void setId_tipo_pqrd(int id_tipo_pqrd)
    {
        this.id_tipo_pqrd = id_tipo_pqrd;
    }

    public String getNombre_tipo_pqrd()
    {
        return nombre_tipo_pqrd;
    }

    public void setNombre_tipo_pqrd(String nombre_tipo_pqrd)
    {
        this.nombre_tipo_pqrd = nombre_tipo_pqrd;
    }

    private int id_tipo_pqrd;
    private String nombre_tipo_pqrd;
}
