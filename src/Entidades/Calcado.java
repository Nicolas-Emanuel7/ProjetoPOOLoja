package Entidades;

import Excecoes.DadosInvalidosException;

public class Calcado {  // Atributos da classe Calcado
    private int idCalcado;
    private int idGerente;
    private TipoCalcado tipoCalcado;
    private String modeloCalcado;
    private double preco;

    // Construtor da classe Calcado com todos os parâmetros
    public Calcado(int idCalcado, int idGerente, TipoCalcado tipoCalcado, String modeloCalcado, double preco){
        this.idCalcado = idCalcado;
        this.idGerente = idGerente;
        this.tipoCalcado = tipoCalcado;
        this.modeloCalcado = modeloCalcado;
        this.preco = preco;
    }
    // Construtor para inserção de banco de dados, com validação dos dados
    public Calcado(int idGerente, TipoCalcado tipoCalcado, String modeloCalcado, double preco) throws DadosInvalidosException{
         // Validação dos dados
        if(modeloCalcado.isEmpty()){ // Lança exceção se o modelo estiver vazio
            throw new DadosInvalidosException(">>>Modelo do calçado não pode estar vazio.");
        }
        if(preco <= 0){// Lança exceção se o preço for inválido
            throw new DadosInvalidosException(">>>Preço de produto inválido.");
        } // Se os dados são válidos, atribui os valores aos atributos
        this.idGerente = idGerente;
        this.tipoCalcado = tipoCalcado;
        this.modeloCalcado = modeloCalcado;
        this.preco = preco;
    }
    // Métodos getters e setters para acessar e modificar os atributos da classe
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
    // Método toString para retornar uma representação em forma de string do objeto Calcado
    public String toString(){
        String saida = "";
        saida += "ID: "+this.idCalcado;
        saida += "\tTipo: "+this.tipoCalcado;
        saida += "\tModelo: "+this.modeloCalcado;
        saida += "\tPreço: "+this.preco+"\n";
        return saida;
    }
}
