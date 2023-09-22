package Entidades;

public class Usuario {
    
    private int idUsuario;
    private String nomeUsuario;
    private String loginUsuario;
    private String senhaUsuario;
    private boolean gerencia;

    public Usuario(int idUsuario, String nomeUsuario, String loginUsuario, String senhaUsuario, boolean gerencia){
        this.idUsuario = idUsuario;
        this.nomeUsuario = nomeUsuario;
        this.loginUsuario = loginUsuario;
        this.senhaUsuario = senhaUsuario;
        this.gerencia = gerencia;
    }

    public Usuario(String nomeUsuario, String loginUsuario, String senhaUsuario, boolean gerencia){
        this.nomeUsuario = nomeUsuario;
        this.loginUsuario = loginUsuario;
        this.senhaUsuario = senhaUsuario;
        this.gerencia = gerencia;
    }

    public int getIdUsuario() {
        return idUsuario;
    }
    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
    public String getNomeUsuario() {
        return nomeUsuario;
    }
    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }
    public String getLoginUsuario() {
        return loginUsuario;
    }
    public void setLoginUsuario(String loginUsuario) {
        this.loginUsuario = loginUsuario;
    }
    public String getSenhaUsuario() {
        return senhaUsuario;
    }
    public void setSenhaUsuario(String senhaUsuario) {
        this.senhaUsuario = senhaUsuario;
    }
    public boolean isGerencia() {
        return gerencia;
    }
    public void setGerencia(boolean gerencia) {
        this.gerencia = gerencia;
    }

    public String toString(){
        String saida = "";
        saida += "ID: "+this.idUsuario;
        saida += "\tNome: "+this.nomeUsuario;
        saida += "\tLogin: "+this.loginUsuario;
        saida += "\tSenha: "+this.senhaUsuario;
        if(gerencia == true){
            saida += "\tCargo: Gerente\n"; 
        } else{
            saida += "\tCargo: Cliente\n";
        }
        return saida;
    }

}
