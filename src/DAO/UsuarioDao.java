package DAO;

import Entidades.*;
import Excecoes.DadosInvalidosException;

import java.sql.*;
import java.util.*;

public class UsuarioDao {

    private static Connection conexaoUsuario;

    public UsuarioDao(Connection conexaoUsuario){
        UsuarioDao.conexaoUsuario = conexaoUsuario;
    }

    public static void inserirUsuario(Usuario usuario) throws SQLException{
        String sql = "INSERT INTO usuario (nome_usuario, login_usuario, senha_usuario, endereco, gerencia) VALUES (?, ?, ?, ?, ?);";

        try(PreparedStatement preparedStatement = conexaoUsuario.prepareStatement(sql)){
            preparedStatement.setString(1, usuario.getNomeUsuario());
            preparedStatement.setString(2, usuario.getLoginUsuario());
            preparedStatement.setString(3, usuario.getSenhaUsuario());
            preparedStatement.setString(4, usuario.getEndereco());
            preparedStatement.setBoolean(5, usuario.isGerencia());

            preparedStatement.executeUpdate();
        }
    }

    public static Usuario buscarUsuarioPorId(int id) throws SQLException, DadosInvalidosException{
        String sql = "SELECT * FROM usuario WHERE id_usuario = ?;";

        try(PreparedStatement preparedStatement = conexaoUsuario.prepareStatement(sql)){
            preparedStatement.setInt(1, id);

            try(ResultSet resultSet = preparedStatement.executeQuery()){
                if(resultSet.next()){
                    return mapearResultSetParaUsuario(resultSet);
                }
            }
        }
        return null;
    }

    public static Usuario buscarUsuarioPorLoginSenha(String login, String senha, boolean gerencia) throws SQLException, DadosInvalidosException{
        String sql = "SELECT * FROM usuario WHERE login_usuario = ? AND senha_usuario = ? AND gerencia = ?;";

        try(PreparedStatement preparedStatement = conexaoUsuario.prepareStatement(sql)){
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, senha);
            preparedStatement.setBoolean(3, gerencia);

            try(ResultSet resultSet = preparedStatement.executeQuery()){
                if(resultSet.next()){
                    return mapearResultSetParaUsuario(resultSet);
                }
            }
        }
        return null;
    }

    public static boolean verificarLoginDisponivel(String login) throws SQLException{
        String sql = "SELECT * FROM usuario WHERE login_usuario = ?;";

        try(PreparedStatement preparedStatement = conexaoUsuario.prepareStatement(sql)){
            preparedStatement.setString(1, login);

            try(ResultSet resultSet = preparedStatement.executeQuery()){
                if(resultSet.next()){
                    return true;
                } else{
                    return false;
                }
            }
        }
    }

    public static List<Usuario> listarUsuariosPorCargo(boolean gerencia) throws SQLException, DadosInvalidosException{
        List<Usuario> Usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuario WHERE gerencia = ?;";

        try(PreparedStatement preparedStatement = conexaoUsuario.prepareStatement(sql)){
            preparedStatement.setBoolean(1, gerencia);

            try(ResultSet resultSet = preparedStatement.executeQuery()){
                while(resultSet.next()){
                    Usuarios.add(mapearResultSetParaUsuario(resultSet));
                }
            }
        }
        return Usuarios;
    }

    public static void atualizarUsuario(Usuario usuario) throws SQLException{
        String sql = "UPDATE usuario SET nome_usuario = ?, login_usuario = ?, senha_usuario = ?, endereco = ? WHERE id_usuario = ?;";

        try(PreparedStatement preparedStatement = conexaoUsuario.prepareStatement(sql)){
            preparedStatement.setString(1, usuario.getNomeUsuario());
            preparedStatement.setString(2, usuario.getLoginUsuario());
            preparedStatement.setString(3, usuario.getSenhaUsuario());
            preparedStatement.setString(4, usuario.getEndereco());
            preparedStatement.setInt(5, usuario.getIdUsuario());

            preparedStatement.executeUpdate();
        }
    }

    public static void excluirUsuario(int id) throws SQLException{
        String sql = "DELETE FROM usuario WHERE id_usuario = ?;";

        try(PreparedStatement preparedStatement = conexaoUsuario.prepareStatement(sql)){
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
        }
    }

    private static Usuario mapearResultSetParaUsuario(ResultSet resultSet) throws SQLException, DadosInvalidosException{
        int id = resultSet.getInt("id_usuario");
        String nome = resultSet.getString("nome_usuario");
        String login = resultSet.getString("login_usuario");
        String senha = resultSet.getString("senha_usuario");
        String endereco = resultSet.getString("endereco");
        boolean cargo = resultSet.getBoolean("gerencia");

        return new Usuario(id, nome, login, senha, endereco, cargo);
    }
}
