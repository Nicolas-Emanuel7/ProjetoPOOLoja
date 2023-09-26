package Entidades;

import Excecoes.DadosInvalidosException;

public class Calcado {
    private int idCalcado;
    private int idGerente;
    private TipoCalcado tipoCalcado;
    private String modeloCalcado;
    private double preco;

    public Calcado(int idCalcado, int idGerente, TipoCalcado tipoCalcado, String modeloCalcado, double preco){
        this.idCalcado = idCalcado;
        this.idGerente = idGerente;
        this.tipoCalcado = tipoCalcado;
        this.modeloCalcado = modeloCalcado;
        this.preco = preco;
    }

    public Calcado(int idGerente, TipoCalcado tipoCalcado, String modeloCalcado, double preco) throws DadosInvalidosException{
        if(modeloCalcado.isEmpty()){
            throw new DadosInvalidosException(">>>Modelo do calçado não pode estar vazio.");
        }
        if(preco <= 0){
            throw new DadosInvalidosException(">>>Preço de produto inválido.");
        }
        this.idGerente = idGerente;
        this.tipoCalcado = tipoCalcado;
        this.modeloCalcado = modeloCalcado;
        this.preco = preco;
    }

    public int getIdCalcado() {
        return idCalcado;
    }
    public void setIdCalcado(int idCalcado) {
        this.idCalcado = idCalcado;
    }
    public int getIdGerente() {
        return idGerente;
    }
    public void setIdGerente(int idGerente) {
        this.idGerente = idGerente;
    }
    public TipoCalcado getTipoCalcado() {
        return tipoCalcado;
    }
    public void setTipoCalcado(TipoCalcado tipoCalcado) {
        this.tipoCalcado = tipoCalcado;
    }
    public String getModeloCalcado() {
        return modeloCalcado;
    }
    public void setModeloCalcado(String modeloCalcado) {
        this.modeloCalcado = modeloCalcado;
    }
    public double getPreco() {
        return preco;
    }
    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String toString(){
        String saida = "";
        saida += "ID: "+this.idCalcado;
        saida += "\tTipo: "+this.tipoCalcado;
        saida += "\tModelo: "+this.modeloCalcado;
        saida += "Preço: "+this.preco+"\n";
        return saida;
    }
}
