package Objetos;

import java.io.Serializable;

public class TipoAdjunto implements Serializable
{
    private static final long serialVersionUID = 1L;

    public TipoAdjunto(String nombreadjunto, int posicion, int obligatorio)
    {
        this.posicion = posicion;
        this.obligatorio = obligatorio;
        this.nombreadjunto = nombreadjunto;
    }

    public int getPosicion()
    {
        return posicion;
    }

    public void setPosicion(int posicion)
    {
        this.posicion = posicion;
    }
    
    public int getObligatorio()
    {
        return obligatorio;
    }

    public void setObligatorio(int obligatorio)
    {
        this.obligatorio = obligatorio;
    }

    public String getNombreadjunto()
    {
        return nombreadjunto;
    }

    public void setNombreadjunt(String nombreadjunto)
    {
        this.nombreadjunto = nombreadjunto;
    }

    private int posicion;
    private int obligatorio;
    private String nombreadjunto;
}
