package DAO;

import Entidades.*;
import java.sql.*;
import java.util.*;

public class EstoqueDao {
    
    private static Connection conexaoEstoque;
    
    public EstoqueDao(Connection conexaoEstoque){
        EstoqueDao.conexaoEstoque = conexaoEstoque;
    }

    public static void inserirEstoque(Estoque estoque) throws SQLException{
        String sql = "INSERT INTO estoque (id_calcado, tamanho, quantidade_disponivel) VALUES (?, ?, ?);";

        try(PreparedStatement preparedStatement = conexaoEstoque.prepareStatement(sql)){
            preparedStatement.setInt(1, estoque.getIdCalcado());
            preparedStatement.setInt(2, estoque.getTamanhoDisponivel());
            preparedStatement.setInt(3, estoque.getQuantidadeEstoque());

            preparedStatement.executeUpdate();
        }
    }

    public static Estoque buscarEstoquePorId(int idCalcado) throws SQLException{
        String sql = "SELECT * FROM estoque WHERE id_calcado = ?;";

        try(PreparedStatement preparedStatement = conexaoEstoque.prepareStatement(sql)){
            preparedStatement.setInt(1, idCalcado);

            try(ResultSet resultSet = preparedStatement.executeQuery()){
                if(resultSet.next()){
                    return mapearResultSetParaEstoque(resultSet);
                }
            }
        }
        return null;
    }

    public static Estoque buscarEstoqueEspecifico(int idCalcado, int tamanho) throws SQLException{
        String sql = "SELECT * FROM estoque WHERE id_calcado = ? AND tamanho = ?;";

        try(PreparedStatement preparedStatement = conexaoEstoque.prepareStatement(sql)){
            preparedStatement.setInt(1, idCalcado);
            preparedStatement.setInt(2, tamanho);

            try(ResultSet resultSet = preparedStatement.executeQuery()){
                if(resultSet.next()){
                    return mapearResultSetParaEstoque(resultSet);
                }
            }
        }
        return null;
    }

    public static List<Estoque> listarTamanhosDisponiveis(int id_calcado) throws SQLException{
        List<Estoque> tamanhosDisponiveis = new ArrayList<>();
        String sql = "SELECT * FROM estoque WHERE id_calcado = ? AND quantidade_disponivel > 0;";

        try(PreparedStatement preparedStatement = conexaoEstoque.prepareStatement(sql)){
            preparedStatement.setInt(1, id_calcado);

            try(ResultSet resultSet = preparedStatement.executeQuery()){
                while(resultSet.next()){
                    tamanhosDisponiveis.add(mapearResultSetParaEstoque(resultSet));
                }
            }
        }
        return tamanhosDisponiveis;
    }

    public static void atualizarEstoque(Estoque estoque) throws SQLException{
        String sql = "UPDATE estoque SET quantidade_disponivel = ? WHERE id_calcado = ? AND tamanho = ?;";

        try(PreparedStatement preparedStatement = conexaoEstoque.prepareStatement(sql)){
            preparedStatement.setInt(1, estoque.getQuantidadeEstoque());
            preparedStatement.setInt(2, estoque.getIdCalcado());
            preparedStatement.setInt(3, estoque.getTamanhoDisponivel());

            preparedStatement.executeUpdate();
        }
    }

    public static void excluirEstoque(int idCalcado) throws SQLException{
        String sql = "DELETE FROM estoque WHERE id_calcado = ?;";

        try(PreparedStatement preparedStatement = conexaoEstoque.prepareStatement(sql)){
            preparedStatement.setInt(1, idCalcado);

            preparedStatement.executeUpdate();
        }
    }

    private static Estoque mapearResultSetParaEstoque(ResultSet resultSet) throws SQLException{
        int idCalcado = resultSet.getInt("id_calcado");
        int tamanho = resultSet.getInt("tamanho");
        int quantidade = resultSet.getInt("quantidade_disponivel");

        return new Estoque(idCalcado, tamanho, quantidade);
    }
    
}

