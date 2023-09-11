package Entidades;

public class Pedido {
    private int idPedido;
    private Cliente cliente;
    private StatusPedido status;
    private double valorFinal;

    public Pedido(int idPedido, Cliente cliente, StatusPedido status, double valorFinal){
        this.idPedido = idPedido;
        this.cliente = cliente;
        this.status = status;
        this.valorFinal = valorFinal;
    }

    public Pedido(Cliente cliente, StatusPedido status, double valorFinal){
        this.cliente = cliente;
        this.status = status;
        this.valorFinal = valorFinal;
    }

    public double getValorFinal() {
        return valorFinal;
    }
    public void setValorFinal(double valorFinal) {
        this.valorFinal = valorFinal;
    }
    public void setIdePedido(int idPedido){
        this.idPedido = idPedido;
    }
    public int getIdPedido(){
        return idPedido;
    }
    public void setCliente(Cliente cliente){
        this.cliente = cliente;
    }
    public Cliente getCliente(){
        return cliente;
    }
    public void setStatus(StatusPedido status){
        this.status =status;
    }
    public int getStatus(){
        if(this.status == StatusPedido.PROCESSANDO){
            return 1;
        } else if(this.status == StatusPedido.ENVIADO){
            return 2;
        } else if(this.status == StatusPedido.ENTREGUE){
            return 3;
        } else if(this.status == StatusPedido.CANCELADO){
            return 4;
        }
        return 0;
    }

    public String toString(){
        String str = "ID: "+this.idPedido;
        str += "\tCliente: "+this.cliente.getNome();
        str += "\tValor: "+this.valorFinal;
        str += "\tStatus: "+this.status+"\n";

        return str;
    }
}
