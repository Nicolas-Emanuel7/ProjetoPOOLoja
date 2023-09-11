package Entidades;

public class Calcado {
    private int idCalcado;
    private int idGerente;
    private TipoCalcado tipoCalcado;
    private String modeloCalcado;
    private int tamanhos;
    private double preco;

    public Calcado(int idCalcado, int idGerente, TipoCalcado tipoCalcado, String modeloCalcado, int tamanhos, double preco){
        this.idCalcado = idCalcado;
        this.idGerente = idGerente;
        this.tipoCalcado = tipoCalcado;
        this.modeloCalcado = modeloCalcado;
        this.tamanhos = tamanhos;
        this.preco = preco;
    }

    public Calcado(int idGerente, TipoCalcado tipoCalcado, String modeloCalcado, int tamanhos, double preco){
        this.idGerente = idGerente;
        this.tipoCalcado = tipoCalcado;
        this.modeloCalcado = modeloCalcado;
        this.tamanhos = tamanhos;
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
    public int getTamanhos() {
        return tamanhos;
    }
    public void setTamanhos(int tamanhos) {
        this.tamanhos = tamanhos;
    }
    public double getPreco() {
        return preco;
    }
    public void setPreco(double preco) {
        this.preco = preco;
    }
}
