package DAO;

import java.sql.*;
import java.util.*;

import Entidades.*;

public class ClienteDao {
    private static Connection conexaoCliente; // conexão com o banco de dados

    public ClienteDao(Connection conexaoCliente){
        ClienteDao.conexaoCliente = conexaoCliente;
    }

    public static void inserirCliente(Cliente cliente) throws SQLException{ // método para inserir um novo cliente no banco de dados
        String sql = "INSERT INTO cliente (nome_cliente, email_cliente, endereco_cliente) VALUES (?, ?, ?);";

        try(PreparedStatement preparedStatement = conexaoCliente.prepareStatement(sql)){
            preparedStatement.setString(1, cliente.getNome());
            preparedStatement.setString(2, cliente.getEmail());
            preparedStatement.setString(3, cliente.getEndereco());

            preparedStatement.executeUpdate();
        }
    }

    public static Cliente buscarClientePorId(int id) throws SQLException, InputMismatchException{
        String sql = "SELECT * FROM cliente WHERE id_cliente = ?;";

        try(PreparedStatement preparedStatement = conexaoCliente.prepareStatement(sql)){
            preparedStatement.setInt(1, id);

            try(ResultSet resultSet = preparedStatement.executeQuery()){
                if(resultSet.next()){
                    return mapearResultSetParaCliente(resultSet);
                }
            }
        }
        return null;
    }

    public static Cliente buscarClientePorEmail(String email) throws SQLException, InputMismatchException{
        String sql = "SELECT * FROM cliente WHERE email_cliente = ?;";

        try(PreparedStatement preparedStatement = conexaoCliente.prepareStatement(sql)){
            preparedStatement.setString(1, email);

            try(ResultSet resultSet = preparedStatement.executeQuery()){
                if(resultSet.next()){
                    return mapearResultSetParaCliente(resultSet);
                }
            }
        }
        return null;
    }

    public static boolean verificarEmailDao(String email) throws SQLException, InputMismatchException{
        String sql = "SELECT * FROM cliente WHERE email_cliente = ?;";

        try(PreparedStatement preparedStatement = conexaoCliente.prepareStatement(sql)){
            preparedStatement.setString(1, email);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                if(resultSet.next()){
                    return true;
                } else{
                    return false;
                }
            }
        }
    }

    public static List<Cliente> listarTodosClientesDao() throws SQLException, InputMismatchException{
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM cliente;";

        try(PreparedStatement preparedStatement = conexaoCliente.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery()){
            while(resultSet.next()){
                clientes.add(mapearResultSetParaCliente(resultSet));
            }
        }
        return clientes;
    }

    public static void atualizarCliente(Cliente cliente) throws SQLException{
        String sql = "UPDATE cliente SET nome_cliente = ?, email_cliente = ?, endereco_cliente = ? WHERE id_cliente = ?;";

        try(PreparedStatement preparedStatement = conexaoCliente.prepareStatement(sql)){
            preparedStatement.setString(1, cliente.getNome());
            preparedStatement.setString(2, cliente.getEmail());
            preparedStatement.setString(3, cliente.getEndereco());
            preparedStatement.setInt(4, cliente.getIdCliente());

            preparedStatement.executeUpdate();
        }
    }

    public static void excluirCliente(int id) throws SQLException{
        String sql = "DELETE FROM cliente WHERE id_cliente = ?;";

        try(PreparedStatement preparedStatement = conexaoCliente.prepareStatement(sql)){
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
        }
    }

    // método auxiliar para mapear um resultSet para um objeto cliente
    private static Cliente mapearResultSetParaCliente(ResultSet resultSet) throws SQLException, InputMismatchException{
        int id = resultSet.getInt("id_cliente");
        String nome = resultSet.getString("nome_cliente");
        String email = resultSet.getString("email_cliente");
        String endereco = resultSet.getString("endereco_cliente");

        return new Cliente(id, nome, email, endereco);
    }
}

