package Servicos;

// importação de pacotes
import DAO.*;
import Entidades.*;
import Excecoes.DadosInvalidosException;

import java.sql.*;
import java.util.*;

public class UsuarioServico {
    //  Método para cadastrar um novo usuário no sistema
    public void cadastrarUsuario(String nome, String login, String senha, String endereco, boolean gerencia)throws SQLException, DadosInvalidosException{
        try{
            Usuario novoUsuario = new Usuario(nome, login, senha, endereco, gerencia); // método de criação do objeto Usuario
            System.out.println(">>>Cadastro bem sucedido");
            UsuarioDao.inserirUsuario(novoUsuario); // chama o método de inserção do usuário ao banco de dados
        }catch(InputMismatchException e){
            e.printStackTrace();
        }
    }
    // Método para verificar a disponibilidade de um login no sistema
    public boolean verificarLogin(String login)throws SQLException{
        if(UsuarioDao.verificarLoginDisponivel(login) == true){ // chama o método de verificação de login no banco de dados
            return true; // , se o login não existir, permite um novo cadastro
        }else{
            return false; // o login já existe
        }
    }
    // Método para realizar o login de um usuário no sistema
    public Usuario loginUsuario(String login, String senha, boolean gerencia)throws SQLException, DadosInvalidosException{
        try{  // Verifica se há usuários cadastrados com o cargo (cliente ou gerente) especificado
            if(UsuarioDao.listarUsuariosPorCargo(gerencia).isEmpty()){
                if(gerencia == true){// se estiver vazia retorna null
                    System.out.println(">>>Não há gerentes cadastrados.");
                    return null;
                }else{ 
                    System.out.println(">>>Não há clientes cadastrados.");
                    return null;
                }
            }else{ // se o usuário ja estiver cadastrado o login é feito
                Usuario usuarioLogin = UsuarioDao.buscarUsuarioPorLoginSenha(login, senha, gerencia);
                if(usuarioLogin != null){ // verifica se os dados são de algum usuário cadastrado para ele poder entrar no site
                    return usuarioLogin;
                }else{
                    return null;
                }
            }
        }catch(InputMismatchException e){
            e.printStackTrace();
        }
        return null;
    }
   // busca o usuário por id
    public Usuario getUsuarioPorId(int idBusca)throws SQLException, DadosInvalidosException{
        Usuario usuarioSolicitado = UsuarioDao.buscarUsuarioPorId(idBusca); // chama o metodo de busca do banco de dados 
        if(usuarioSolicitado != null){
            return usuarioSolicitado; // e retorna esse objeto usuário
        }else{
            return null;
        }
    }
    // Método para atualizar os dados de um usuário (cliente ou gerente) no sistema
    public void atualizarUsuario(Usuario usuarioAtualizado)throws SQLException{
        try{
            UsuarioDao.atualizarUsuario(usuarioAtualizado); // chama o método do DAO para atualizar
            if(usuarioAtualizado.isGerencia() == true){
                System.out.println(">>>Dados do Gerente atualizados.");
                System.out.println(usuarioAtualizado.toString());
            }else{
                System.out.println(">>>Dados do Cliente atualizados.");
                System.out.println(usuarioAtualizado.toString());
            }
        }catch(InputMismatchException e){
            e.printStackTrace();
        }
    }
    // Método para apagar um usuário do sistema pelo seu ID
    public void apagarUsuario(int idUsuario) throws SQLException, InputMismatchException, DadosInvalidosException{
        if(idUsuario == 0){
            System.out.println(">>>Operação cancelada.");
        }else{
            for(Pedido pedido : PedidoDao.buscarPedidosPorCliente(idUsuario)){

                ItemPedidoDao.excluirItensDoPedido(pedido.getIdPedido());
            }
            PedidoDao.excluirPedido(idUsuario); // primeiro é necessário excluir os pedidos desse usuário
            UsuarioDao.excluirUsuario(idUsuario); // depois exclui ele :c
            System.out.println(">>>Operação concluída.");
        }
    }
    // Lista os usuários do sistema por cargo 
    public String mostrarUsuariosPorCargo(boolean gerencia) throws SQLException, DadosInvalidosException{
        String usuariosLista = "";
        if(gerencia == true){
            usuariosLista += ">>>Gerentes cadastrados no sistema:\n";
        }else{
            usuariosLista += ">>>Clientes cadastrados no sistema:\n";
        }
        for(Usuario usuario : UsuarioDao.listarUsuariosPorCargo(gerencia)){
            usuariosLista += usuario.toString(); // imprime todos os dados desses usuários baseado no cargo
        }
        return usuariosLista;
    }
}
