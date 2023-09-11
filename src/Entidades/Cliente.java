package Entidades;

public class Cliente {
    private int idCliente;
    private String nomeCliente;
    private String email;
    private String endereco;

    public Cliente(int idCliente, String nomeCliente, String email, String endereco){
        this.idCliente = idCliente;
        this.nomeCliente = nomeCliente;
        this.email = email;
        this.endereco = endereco;
    }

    public Cliente(String nomeCliente, String email, String endereco){
        this.nomeCliente = nomeCliente;
        this.email = email;
        this.endereco = endereco;
    }

    public void setIdCliente(int idCliente){
        this.idCliente = idCliente;
    }
    public int getIdCliente(){
        return idCliente;
    }
    public void setNome(String nomeCliente){
        this.nomeCliente = nomeCliente;
    }
    public String getNome(){
        return nomeCliente;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public String getEmail(){
        return email;
    }
    public String getEndereco() {
        return endereco;
    }
    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String toString(){
        String str = "ID: "+this.idCliente;
        str += "\tNome: "+this.nomeCliente;
        str += "\tEmail: "+this.email;
        str += "\tEndereço: "+this.endereco+"\n";

        return str;
    }
}
