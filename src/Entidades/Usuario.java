package Entidades;

import Excecoes.DadosInvalidosException;

public class Usuario {
    
    private int idUsuario;  // atributos da classe Usuario
    private String nomeUsuario;
    private String loginUsuario;
    private String senhaUsuario;
    private String endereco;
    private boolean gerencia;
    // Construtor da classe Calcado com todos os parâmetros
    public Usuario(int idUsuario, String nomeUsuario, String loginUsuario, String senhaUsuario, String endereco, boolean gerencia) throws DadosInvalidosException{
        this.idUsuario = idUsuario;
        this.nomeUsuario = nomeUsuario;
        this.loginUsuario = loginUsuario;
        this.senhaUsuario = senhaUsuario;
        this.endereco = endereco;
        this.gerencia = gerencia;
    }
    // Construtor para inserção de banco de dados, com validação dos dados
    public Usuario(String nomeUsuario, String loginUsuario, String senhaUsuario, String endereco, boolean gerencia) throws DadosInvalidosException{
         // Validação dos dados
        if(nomeUsuario.isEmpty()){// Lança exceção se o nome estiver vazio
            throw new DadosInvalidosException(">>>Nome de usuário não pode estar vazio.");
        }
        if(loginUsuario.isEmpty()){ // Lança exceção se o login de usuário estiver vazio
            throw new DadosInvalidosException(">>>Login de usuário não pode estar vazio.");
        }
        if(senhaUsuario.isEmpty()){ // Lança exceção se a senha estiver vazia
            throw new DadosInvalidosException(">>>Senha de usuário não pode estar vazia.");
        }
        if(endereco.isEmpty()){ // Lança exceção se o endereço estiver vazio
            throw new DadosInvalidosException(">>>Endereço de usuário não pode estar vazio.");
        }// Se os dados são válidos, atribui os valores aos atributos
        this.nomeUsuario = nomeUsuario;
        this.loginUsuario = loginUsuario;
        this.senhaUsuario = senhaUsuario;
        this.endereco = endereco;
        this.gerencia = gerencia;
    }
   // Métodos getters e setters para acessar e modificar os atributos da classe
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
    public String getEndereco(){
        return endereco;
    }
    public void setEndereco(String endereco){
        this.endereco = endereco;
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
        saida += "\tEndereço: "+this.endereco;
        if(gerencia == true){
            saida += "\tCargo: Gerente\n"; 
        } else{
            saida += "\tCargo: Cliente\n";
        }
        return saida;
    }

}
