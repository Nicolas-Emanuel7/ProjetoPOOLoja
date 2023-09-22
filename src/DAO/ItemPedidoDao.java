package DAO;

import java.sql.*;
import java.util.*;

import Entidades.*;

public class ItemPedidoDao {
    
    private static Connection conexaoItem;

    public ItemPedidoDao(Connection conexaoItem){
        ItemPedidoDao.conexaoItem = conexaoItem;
    }

    public static void inserirItemDePedido(ItemDePedido itemDePedido) throws SQLException{
        String sql = "INSERT INTO item_de_pedido (id_pedido, id_calcado, quantidade, tamanho) VALUES (?, ?, ?, ?);";

        try(PreparedStatement preparedStatement = conexaoItem.prepareStatement(sql)){
            preparedStatement.setInt(1, itemDePedido.getIdPedido());
            preparedStatement.setInt(2, itemDePedido.getidCalcado());
            preparedStatement.setInt(3, itemDePedido.getQuantidade());
            preparedStatement.setInt(4, itemDePedido.getTamanho());

            preparedStatement.executeUpdate();
        }
    }

    public static ItemDePedido buscarItemDePedidoPorId(int id) throws SQLException, InputMismatchException{
        String sql = "SELECT * FROM item_de_pedido WHERE id_item = ?;";

        try(PreparedStatement preparedStatement = conexaoItem.prepareStatement(sql)){
            preparedStatement.setInt(1, id);

            try(ResultSet resultSet = preparedStatement.executeQuery()){
                if(resultSet.next()){
                    return mapearResultSetParaItemDePedido(resultSet);
                }
            }
        }
        return null;
    }

    public static List<ItemDePedido> listarTodosItemDePedidos() throws SQLException, InputMismatchException{
        List<ItemDePedido> ItemDePedidos = new ArrayList<>();
        String sql = "SELECT * FROM item_de_pedido;";

        try(PreparedStatement preparedStatement = conexaoItem.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery()){
            while(resultSet.next()){
                ItemDePedidos.add(mapearResultSetParaItemDePedido(resultSet));
            }
        }
        return ItemDePedidos;
    }

    public static void atualizarItemDePedido(ItemDePedido itemDePedido) throws SQLException{
        String sql = "UPDATE item_de_pedido SET id_pedido = ?, id_calcado = ?, quantidade = ?, tamanho = ? WHERE id_item = ?;";

        try(PreparedStatement preparedStatement = conexaoItem.prepareStatement(sql)){
            preparedStatement.setInt(1, itemDePedido.getIdPedido());
            preparedStatement.setInt(2, itemDePedido.getidCalcado());
            preparedStatement.setInt(3, itemDePedido.getQuantidade());
            preparedStatement.setInt(4, itemDePedido.getTamanho());
            preparedStatement.setInt(5, itemDePedido.getIdItem());

            preparedStatement.executeUpdate();
        }
    }

    public static void excluirItemDePedido(int id) throws SQLException{
        String sql = "DELETE FROM item_de_pedido WHERE id_item = ?;";

        try(PreparedStatement preparedStatement = conexaoItem.prepareStatement(sql)){
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
        }
    }

    // método auxiliar para mapear um resultSet para um objeto ItemDePedido
    private static ItemDePedido mapearResultSetParaItemDePedido(ResultSet resultSet) throws SQLException, InputMismatchException{
        int idItem = resultSet.getInt("id_item");
        int idPedido = resultSet.getInt("id_pedido");
        int idCalcado = resultSet.getInt("id_calcado");
        int quantidade = resultSet.getInt("quantidade");
        int tamanho = resultSet.getInt("tamanho"); 

        return new ItemDePedido(idItem, idPedido, idCalcado, quantidade, tamanho);
    }  
}
