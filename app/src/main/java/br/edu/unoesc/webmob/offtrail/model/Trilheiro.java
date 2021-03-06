package br.edu.unoesc.webmob.offtrail.model;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable
public class Trilheiro  implements Serializable {

    public Trilheiro() {

    }

    @DatabaseField(generatedId = true)
    private Integer codigo;

    @DatabaseField
    private String nome;

    @DatabaseField
    private Integer idade;

    @DatabaseField(dataType = DataType.BYTE_ARRAY)
    private byte[] foto;

    @DatabaseField(foreign = true, foreignColumnName = "codigo")
    private Moto moto;

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getIdade() {
        return idade;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    public Moto getMoto() {
        return moto;
    }

    public void setMoto(Moto moto) {
        this.moto = moto;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }
}
