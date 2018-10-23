package br.edu.unoesc.webmob.offtrail.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Moto {

    public Moto(){

    }

    @DatabaseField(generatedId = true)
    private Integer codigo;

    @DatabaseField
    private String descricaoMoto;

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
}
