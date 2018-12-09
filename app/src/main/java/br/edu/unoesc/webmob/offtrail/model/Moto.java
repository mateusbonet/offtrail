package br.edu.unoesc.webmob.offtrail.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable
public class Moto implements Serializable {

    public Moto(){

    }

    @Override
    public String toString() {
        return getModeloMoto() + " - " + getCilindradasMoto();
    }

    @DatabaseField(generatedId = true)
    private Integer codigo;

    @DatabaseField
    private String descricaoMoto;

    @DatabaseField
    private String modeloMoto;

    @DatabaseField
    private String descricaoMarca;

    @DatabaseField
    private String cilindradasMoto;

    @DatabaseField
    private String corMoto;

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getDescricaoMoto() {
        return descricaoMoto;
    }

    public void setDescricaoMoto(String descricaoMoto) {
        this.descricaoMoto = descricaoMoto;
    }

    public String getDescricaoMarca() {
        return descricaoMarca;
    }

    public void setDescricaoMarca(String descricaoMarca) {
        this.descricaoMarca = descricaoMarca;
    }

    public String getCilindradasMoto() {
        return cilindradasMoto;
    }

    public void setCilindradasMoto(String cilindradasMoto) {
        this.cilindradasMoto = cilindradasMoto;
    }

    public String getCorMoto() {
        return corMoto;
    }

    public void setCorMoto(String corMoto) {
        this.corMoto = corMoto;
    }

    public String getModeloMoto() {
        return modeloMoto;
    }

    public void setModeloMoto(String modeloMoto) {
        this.modeloMoto = modeloMoto;
    }
}
