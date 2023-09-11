package Entidades;

public class Tamanho {
    private int idCalcado;
    private int tamanhoDisponivel;
    private int quantidadeEstoque;

    public Tamanho(int idCalcado, int tamanhoDisponivel, int quantidadeEstoque){
        this.idCalcado = idCalcado;
        this.tamanhoDisponivel = tamanhoDisponivel;
        this.quantidadeEstoque = quantidadeEstoque;
    }

    public Tamanho(int tamanhoDisponivel, int quantidadeEstoque){
        this.tamanhoDisponivel = tamanhoDisponivel;
        this.quantidadeEstoque = quantidadeEstoque;
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
