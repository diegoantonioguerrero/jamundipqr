// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   NumeracionPQRD.java

package Objetos;


public class NumeracionPQRD
{

    public NumeracionPQRD()
    {
    }

    public NumeracionPQRD(int fldidnumeracionpqrd, int ano, int proximonroradicacion, String prefijo)
    {
        this.fldidnumeracionpqrd = fldidnumeracionpqrd;
        this.ano = ano;
        this.proximonroradicacion = proximonroradicacion;
        this.prefijo = prefijo;
    }

    public int getFldidnumeracionpqrd()
    {
        return fldidnumeracionpqrd;
    }

    public void setFldidnumeracionpqrd(int fldidnumeracionpqrd)
    {
        this.fldidnumeracionpqrd = fldidnumeracionpqrd;
    }

    public int getAno()
    {
        return ano;
    }

    public void setAno(int ano)
    {
        this.ano = ano;
    }

    public int getProximonroradicacion()
    {
        return proximonroradicacion;
    }

    public void setProximonroradicacion(int proximonroradicacion)
    {
        this.proximonroradicacion = proximonroradicacion;
    }

    public String getPrefijo()
    {
        return prefijo;
    }

    public void setPrefijo(String prefijo)
    {
        this.prefijo = prefijo;
    }

    private int fldidnumeracionpqrd;
    private int ano;
    private int proximonroradicacion;
    private String prefijo;
}
