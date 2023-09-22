package Entidades;

public class Pedido {
    private int idPedido;
    private Usuario cliente;
    private StatusPedido status;
    private double valorFinal;

    public Pedido(int idPedido, Usuario cliente, StatusPedido status, double valorFinal){
        this.idPedido = idPedido;
        this.cliente = cliente;
        this.status = status;
        this.valorFinal = valorFinal;
    }

    public Pedido(Usuario cliente, StatusPedido status, double valorFinal){
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
    public void setCliente(Usuario cliente){
        this.cliente = cliente;
    }
    public Usuario getCliente(){
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
        String saida = "ID: "+this.idPedido;
        saida += "\tCliente: "+this.cliente.getNomeUsuario();
        saida += "\tValor: "+this.valorFinal;
        saida += "\tStatus: "+this.status+"\n";
        return saida;
    }
}
