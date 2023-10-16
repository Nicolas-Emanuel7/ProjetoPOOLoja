package DAO;
// importando pacotes
import java.sql.*; 
import java.util.*;

import Entidades.*;

public class ItemPedidoDao {
    
    private static Connection conexaoItem; // Atributo estático para armazenar a conexão com o banco de dados

    public ItemPedidoDao(Connection conexaoItem){ // Construtor da classe ItemDePedidoDao
        ItemPedidoDao.conexaoItem = conexaoItem; // Inicializa a conexão estática com o banco de dados
    }
    // Método para inserir um objeto item de pedido no banco de dados
    public static void inserirItemDePedido(ItemDePedido itemDePedido) throws SQLException{
        // String SQL para inserir dados na tabela 'item_de_pedido'
        String sql = "INSERT INTO item_de_pedido (fk_id_pedido, fk_id_calcado, quantidade, fk_id_estoque) VALUES (?, ?, ?, ?);";
        // Inicia um bloco 'try' para manipular possíveis exceções do SQL
        try(PreparedStatement preparedStatement = conexaoItem.prepareStatement(sql)){
            // PreparedStatement defini os valores dos espaços reservados antes de executar a instrução (sql)
            preparedStatement.setInt(1, itemDePedido.getIdPedido());// define o primeiro espaço reservado na instrução SQL 
            preparedStatement.setInt(2, itemDePedido.getidCalcado());
            preparedStatement.setInt(3, itemDePedido.getQuantidade());
            preparedStatement.setInt(4, itemDePedido.getTamanho());
            // Executa a instrução SQL no banco de dados após definir todos os valores dos espaços reservados.
            preparedStatement.executeUpdate();
        }
    }
   // Método para buscar um objeto ItemDePedido no banco de dados pelo id do item
    public static ItemDePedido buscarItemDePedidoPorId(int id) throws SQLException, InputMismatchException{
        String sql = "SELECT * FROM item_de_pedido WHERE id_item = ?;";// Consulta SQL para buscar um objeto ItemDePedido pelo id do item
       // Inicia um bloco 'try' para manipular possíveis exceções do SQL
        try(PreparedStatement preparedStatement = conexaoItem.prepareStatement(sql)){
            // Define o primeiro espaço reservado na instrução SQL com o valor do ID
            preparedStatement.setInt(1, id);

            try(ResultSet resultSet = preparedStatement.executeQuery()){ // Executa a consulta SQL e obtém o resultado
                if(resultSet.next()){// Verifica se há um próximo resultado no conjunto de resultados
                   // Chama o método mapearResultSetParaItemDePedido para converter o resultado em um objeto ItemDePedido
                    return mapearResultSetParaItemDePedido(resultSet);
                }
            }
        }
        return null;// retorna null se não tiver nenhum item com o id inserido
    }
    // Método estático para listar todos os objetos ItemDePedido no banco de dados
    public static List<ItemDePedido> listarTodosItemDePedidos() throws SQLException, InputMismatchException{
        // Cria uma lista vazia para armazenar objetos ItemDePedido
        List<ItemDePedido> ItemDePedidos = new ArrayList<>();
        // Consulta SQL para selecionar todos os registros da tabela 'item_de_pedido'
        String sql = "SELECT * FROM item_de_pedido;";
        // Inicia um bloco 'try-with-resources' para garantir o fechamento automático dos recursos (PreparedStatement e ResultSet)
        try(PreparedStatement preparedStatement = conexaoItem.prepareStatement(sql);
            // Prepara e executa a consulta SQL, obtendo um conjunto de resultados
            ResultSet resultSet = preparedStatement.executeQuery()){
            while(resultSet.next()){ //Itera sobre os resultados do conjunto de resultados
                // Mapeia os resultados da linha atual para um objeto ItemDePedido usando o método mapearResultSetParaItemDePedido()
                ItemDePedidos.add(mapearResultSetParaItemDePedido(resultSet));
            }
        }
        return ItemDePedidos;
    }
    // Método estático para atualizar um objeto ItemDePedido no banco de dados
    public static void atualizarItemDePedido(ItemDePedido itemDePedido) throws SQLException{
         // Consulta SQL para atualizar os dados do item de pedido na tabela 'item_de_pedido'
        String sql = "UPDATE item_de_pedido SET fk_id_pedido = ?, fk_id_calcado = ?, quantidade = ?, fk_id_estoque = ? WHERE id_item = ?;";
        // Inicia um bloco 'try-with-resources' para garantir o fechamento automático do PreparedStatement
        try(PreparedStatement preparedStatement = conexaoItem.prepareStatement(sql)){
            preparedStatement.setInt(1, itemDePedido.getIdPedido());// Define o novo ID do pedido
            preparedStatement.setInt(2, itemDePedido.getidCalcado());
            preparedStatement.setInt(3, itemDePedido.getQuantidade());
            preparedStatement.setInt(4, itemDePedido.getTamanho());
            preparedStatement.setInt(5, itemDePedido.getIdItem());

            preparedStatement.executeUpdate();// Executa a atualização no banco de dados
        }
    }
    // Método para excluir um item de pedido do banco de dados com base no ID fornecido
    public static void excluirItemDePedido(int id) throws SQLException{
        // Consulta SQL para excluir um item de pedido da tabela 'item_de_pedido' com base no ID
        String sql = "DELETE FROM item_de_pedido WHERE id_item = ?;";
        // Inicia um bloco 'try-with-resources' para garantir o fechamento automático do PreparedStatement
        try(PreparedStatement preparedStatement = conexaoItem.prepareStatement(sql)){
            // Define o valor do primeiro espaço reservado na consulta SQL para o ID do a item ser excluído 
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();// Executa a exclusão do item no banco de dados
        }
    }
   // Este método é responsável por excluir todos os itens associados a um pedido específico no banco de dados.
    public static void excluirItensDoPedido(int idPedido) throws SQLException{
        // Define a instrução SQL para excluir itens de pedido associados ao pedido com o ID fornecido.
        String sql = "DELETE FROM item_de_pedido WHERE fk_id_pedido = ?;";

        try(PreparedStatement preparedStatement = conexaoItem.prepareStatement(sql)){
            preparedStatement.setInt(1, idPedido);
            // Executa a instrução SQL DELETE no banco de dados, removendo todos os itens associados ao pedido com o ID fornecido
            preparedStatement.executeUpdate();
        }
    }
    
    public static void excluirPedidosPorCalcado(int idCalcado) throws SQLException{
        // Define a instrução SQL para excluir itens de pedido associados ao pedido com o ID fornecido.
        String sql = "DELETE FROM item_de_pedido WHERE fk_id_calcado = ?;";

        try(PreparedStatement preparedStatement = conexaoItem.prepareStatement(sql)){
            preparedStatement.setInt(1, idCalcado);
            // Executa a instrução SQL DELETE no banco de dados, removendo todos os itens associados ao pedido com o ID fornecido
            preparedStatement.executeUpdate();
        }
    }

    // método auxiliar para mapear um resultSet para um objeto ItemDePedido
    private static ItemDePedido mapearResultSetParaItemDePedido(ResultSet resultSet) throws SQLException, InputMismatchException{
        // Extrai os valores das colunas do ResultSet
        int idItem = resultSet.getInt("id_item");
        int idPedido = resultSet.getInt("fk_id_pedido");
        int idCalcado = resultSet.getInt("fk_id_calcado");
        int quantidade = resultSet.getInt("quantidade");
        int tamanho = resultSet.getInt("fk_id_estoque"); 
        // Cria um novo objeto ItemDePedido com os valores extraídos do ResultSet e retorna-o
        return new ItemDePedido(idItem, idPedido, idCalcado, quantidade, tamanho);
    }  
}

