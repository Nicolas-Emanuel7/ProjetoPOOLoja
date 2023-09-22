package Servicos;


import DAO.*;
import Entidades.*;
import java.sql.*;
import java.util.*;

public class UsuarioServico {
    
    public void cadastrarUsuario(String nome, String login, String senha, boolean gerencia)throws SQLException{
        try{
            Usuario novoUsuario = new Usuario(nome, login, senha, gerencia);
            System.out.println(">>>Cadastro bem sucedido");
            UsuarioDao.inserirUsuario(novoUsuario);
        }catch(InputMismatchException e){
            e.printStackTrace();
        }
    }

    public boolean verificarLogin(String login)throws SQLException{
        if(UsuarioDao.verificarLoginDisponivel(login) == true){
            return true;
        }else{
            return false;
        }
    }

    public Usuario loginUsuario(String login, String senha, boolean gerencia)throws SQLException{
        try{
            if(UsuarioDao.listarUsuariosPorCargo(gerencia).isEmpty()){
                if(gerencia == true){
                    System.out.println(">>>Não há gerentes cadastrados.");
                    return null;
                }else{
                    System.out.println(">>>Não há clientes cadastrados.");
                    return null;
                }
            }else{
                Usuario usuarioLogin = UsuarioDao.buscarUsuarioPorLoginSenha(login, senha, gerencia);
                if(usuarioLogin != null){
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

    public List<Usuario> getUsuariosPorCargo(boolean gerencia) throws SQLException{
        return UsuarioDao.listarUsuariosPorCargo(gerencia);
    }

    public Usuario getUsuarioPorId(int idBusca)throws SQLException{
        Usuario usuarioSolicitado = UsuarioDao.buscarUsuarioPorId(idBusca);
        if(usuarioSolicitado != null){
            return usuarioSolicitado;
        }else{
            return null;
        }
    }

    public void atualizarUsuario(Usuario usuarioAtualizado)throws SQLException{
        try{
            UsuarioDao.atualizarUsuario(usuarioAtualizado);
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

    public void apagarUsuario(int idUsuario) throws SQLException{
        if(idUsuario == 0){
            System.out.println(">>>Operação cancelada.");
        }else{
            UsuarioDao.excluirUsuario(idUsuario);
            System.out.println(">>>Operação concluída.");
        }
    }

    public String mostrarUsuariosPorCargo(boolean gerencia) throws SQLException{
        String usuariosLista = "";
        if(gerencia == true){
            usuariosLista += ">>>Gerentes cadastrados no sistema:\n";
        }else{
            usuariosLista += ">>>Clientes cadastrados no sistema:\n";
        }
        for(Usuario usuario : UsuarioDao.listarUsuariosPorCargo(gerencia)){
            usuariosLista += usuario.toString();
        }
        return usuariosLista;
    }
}
