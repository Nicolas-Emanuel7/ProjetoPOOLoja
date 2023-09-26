package DAO;

import java.sql.*;
import java.util.*;

import Entidades.*;
import Excecoes.DadosInvalidosException;
import Servicos.PedidoServico;

public class PedidoDao {
    
    private static Connection conexaoPedido;

    public PedidoDao(Connection conexaoPedido){
        PedidoDao.conexaoPedido = conexaoPedido;
    }

    public static void inserirPedido(Pedido pedido) throws SQLException{
        String sql = "INSERT INTO pedido (id_cliente, status, valor_final) VALUES (?, ?, ?);";

        try(PreparedStatement preparedStatement = conexaoPedido.prepareStatement(sql)){
            preparedStatement.setInt(1, pedido.getCliente().getIdUsuario());
            preparedStatement.setInt(2, pedido.getStatus());
            preparedStatement.setDouble(3, pedido.getValorFinal());

            preparedStatement.executeUpdate();
        }
    }

    public static Pedido buscarPedidoPorId(int id) throws SQLException, InputMismatchException, DadosInvalidosException{
        String sql = "SELECT * FROM pedido WHERE id_pedido = ?;";

        try(PreparedStatement preparedStatement = conexaoPedido.prepareStatement(sql)){
            preparedStatement.setInt(1, id);

            try(ResultSet resultSet = preparedStatement.executeQuery()){
                if(resultSet.next()){
                    return mapearResultSetParaPedido(resultSet);
                }
            }
        }
        return null;
    }

    public static List<Pedido> buscarPedidosPorCliente(int idCliente) throws SQLException, InputMismatchException, DadosInvalidosException{
        String sql = "SELECT * FROM pedido WHERE id_cliente = ?;";
        List<Pedido> pedidosPorCliente = new ArrayList<>();
        try(PreparedStatement preparedStatement = conexaoPedido.prepareStatement(sql)){
            preparedStatement.setInt(1, idCliente);

            try(ResultSet resultSet = preparedStatement.executeQuery()){
                while(resultSet.next()){
                    pedidosPorCliente.add(mapearResultSetParaPedido(resultSet));
                }
                return pedidosPorCliente;
            }
        }
    }

    public static List<Pedido> listarTodosPedidos() throws SQLException, InputMismatchException, DadosInvalidosException{
        List<Pedido> pedidos = new ArrayList<>();
        String sql = "SELECT * FROM pedido;";

        try(PreparedStatement preparedStatement = conexaoPedido.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery()){
            while(resultSet.next()){
                pedidos.add(mapearResultSetParaPedido(resultSet));
            }
        }
        return pedidos;
    }

    public static void atualizarPedido(Pedido pedido) throws SQLException{
        String sql = "UPDATE pedido SET id_cliente = ?, status = ?, valor_final = ? WHERE id_pedido = ?;";

        try(PreparedStatement preparedStatement = conexaoPedido.prepareStatement(sql)){
            preparedStatement.setInt(1, pedido.getCliente().getIdUsuario());
            preparedStatement.setInt(2, pedido.getStatus());
            preparedStatement.setDouble(3, pedido.getValorFinal());
            preparedStatement.setInt(4, pedido.getIdPedido());

            preparedStatement.executeUpdate();
        }
    }

    public static void atualizarValorFinal(int id, Pedido pedido) throws SQLException{
        String sql = "UPDATE pedido SET valor_final = ? WHERE id_pedido = ?;";

        try(PreparedStatement preparedStatement = conexaoPedido.prepareStatement(sql)){
            preparedStatement.setDouble(1, pedido.getValorFinal());
            preparedStatement.setInt(2, id);

            preparedStatement.executeUpdate();
        }
    }

    public static void atualizarStatusDao(int idPedido, int status)throws SQLException{
        String sql = "UPDATE pedido SET status = ? WHERE id_pedido = ?;";

        try(PreparedStatement preparedStatement = conexaoPedido.prepareStatement(sql)){
            preparedStatement.setInt(1, status);
            preparedStatement.setInt(2, idPedido);

            preparedStatement.executeUpdate();
        }
    }

    public static void excluirPedido(int id) throws SQLException{
        String sql = "DELETE FROM pedido WHERE id_pedido = ?;";

        try(PreparedStatement preparedStatement = conexaoPedido.prepareStatement(sql)){
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
        }
    }

    // método auxiliar para mapear um resultSet para um objeto pedido
    private static Pedido mapearResultSetParaPedido(ResultSet resultSet) throws SQLException, InputMismatchException, DadosInvalidosException{
        int id = resultSet.getInt("id_pedido");
        int idCliente = resultSet.getInt("id_cliente");
        int status = resultSet.getInt("status");
        double valorFinal = resultSet.getDouble("valor_final");

        Usuario cliente = UsuarioDao.buscarUsuarioPorId(idCliente);

        return new Pedido(id, cliente, PedidoServico.returnStatus(status), valorFinal); 
    }
}