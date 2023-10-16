package Entidades;

import Excecoes.DadosInvalidosException;

public class Estoque { // Atributos da classe Estoque
    private int idEstoque;
    private int idCalcado;
    private int tamanhoDisponivel;
    private int quantidadeEstoque;
     // Construtor da classe Estoque com todos os parâmetros
    public Estoque(int idEstoque, int idCalcado, int tamanhoDisponivel, int quantidadeEstoque){
        this.idEstoque = idEstoque;
        this.idCalcado = idCalcado;
        this.tamanhoDisponivel = tamanhoDisponivel;
        this.quantidadeEstoque = quantidadeEstoque;
    }
    // Construtor para inserção de banco de dados, com validação dos dados
    public Estoque(int idCalcado, int tamanhoDisponivel, int quantidadeEstoque) throws DadosInvalidosException{
        // Validação dos dados
        if(tamanhoDisponivel <= 0){  // Lança exceção se o modelo estiver vazio
            throw new DadosInvalidosException(">>>Tamanho inválido.");
        }
        if(quantidadeEstoque < 0){ // Lança exceção se a quantidade for inválida
            throw new DadosInvalidosException(">>>Quantidade inválida.");
        }
        this.tamanhoDisponivel = tamanhoDisponivel;
        this.quantidadeEstoque = quantidadeEstoque;
    }
    // Métodos getters e setters para acessar e modificar os atributos da classe
    public int getIdEstoque() {
        return idEstoque;
    }
    public void setIdEstoque(int idEstoque) {
        this.idEstoque = idEstoque;
    }
    public int getIdCalcado() {
        return idCalcado;
    }
    public void setIdCalcado(int idCalcado) {
        this.idCalcado = idCalcado;
    }
    public int getTamanhoDisponivel() {
        return tamanhoDisponivel;
    }
    public void setTamanhoDisponivel(int tamanhoDisponivel) {
        this.tamanhoDisponivel = tamanhoDisponivel;
    }
    public int getQuantidadeEstoque() {
        return quantidadeEstoque;
    }
    public void setQuantidadeEstoque(int quantidadeEstoque) {
        this.quantidadeEstoque = quantidadeEstoque;
    }
}
