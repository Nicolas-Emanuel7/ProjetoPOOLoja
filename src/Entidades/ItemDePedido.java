package Entidades;

public class ItemDePedido {
    private int quantidade;
    private int idPedido;
    private int idItem;
    private int idCalcado;
    private int tamanho;

    public ItemDePedido(int idItem, int idPedido, int idCalcado, int quantidade, int tamanho){
        this.idItem = idItem;
        this.idPedido = idPedido;
        this.idCalcado = idCalcado;
        this.quantidade = quantidade;
        this.tamanho = tamanho;
    }

    public ItemDePedido(int idPedido, int idCalcado, int quantidade, int tamanho){
        this.idPedido = idPedido;
        this.idCalcado = idCalcado;
        this.quantidade = quantidade;
        this.tamanho = tamanho;
    }

    public int getIdPedido() {
        return idPedido;
    }
    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }
    public void setQuantidade(int quantidade){
        this.quantidade =quantidade;
    }
    public int getQuantidade(){
        return quantidade;
    }
    public int getIdItem() {
        return idItem;
    }
    public void setIdItem(int idItem) {
        this.idItem = idItem;
    }
    public int getidCalcado() {
        return idCalcado;
    }
    public void setidCalcado(int idCalcado) {
        this.idCalcado = idCalcado;
    }
    public int getTamanho() {
        return tamanho;
    }
    public void setTamanho(int tamanho) {
        this.tamanho = tamanho;
    }
}
