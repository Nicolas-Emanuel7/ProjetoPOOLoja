package Entidades;

public class Gerente {
    private int idGerente;
    private String nomeGerente;
    private String loginGerente;
    private String senhaGerente;

    public Gerente(int idGerente, String nomeGerente, String loginGerente, String senhaGerente){
        this.idGerente = idGerente;
        this.nomeGerente = nomeGerente;
        this.loginGerente = loginGerente;
        this.senhaGerente = senhaGerente;
    }

    public Gerente(String nomeGerente, String loginGerente, String senhaGerente){
        this.nomeGerente = nomeGerente;
        this.loginGerente = loginGerente;
        this.senhaGerente = senhaGerente;
    }

    public int getIdGerente() {
        return idGerente;
    }
    public void setIdGerente(int idGerente) {
        this.idGerente = idGerente;
    }
    public String getNomeGerente() {
        return nomeGerente;
    }
    public void setNomeGerente(String nomeGerente) {
        this.nomeGerente = nomeGerente;
    }
    public String getLoginGerente() {
        return loginGerente;
    }
    public void setLoginGerente(String loginGerente) {
        this.loginGerente = loginGerente;
    }
    public String getSenhaGerente() {
        return senhaGerente;
    }
    public void setSenhaGerente(String senhaGerente) {
        this.senhaGerente = senhaGerente;
    }

    public String toString(){
        String str = "ID: "+this.idGerente+"\tNome: "+this.nomeGerente+"\tLogin: "+this.loginGerente+"\tSenha: "+this.senhaGerente+"\n";

        return str;
    }
}
