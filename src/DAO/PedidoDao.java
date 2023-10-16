package DAO;
// importando pacotes
import java.sql.*;
import java.util.*;

import Entidades.*;
import Excecoes.DadosInvalidosException;
import Servicos.PedidoServico;

public class PedidoDao {
    
    private static Connection conexaoPedido; // Atributo estático para armazenar a conexão com o banco de dados

    public PedidoDao(Connection conexaoPedido){ // construtor da classe PedidoDao
        PedidoDao.conexaoPedido = conexaoPedido; // Inicializa a conexão estática com o banco de dados
    }
    // Método para inserir um objeto Pedido no banco de dados
    public static int inserirPedido(Pedido pedido) throws SQLException{
        // String SQL para inserir dados na tabela 'pedido'
        String sql = "INSERT INTO pedido (fk_id_usuario, status, valor_final) VALUES (?, ?, ?) RETURNING id_pedido;";
        // Inicia um bloco 'try' para manipular possíveis exceções do SQL
        try(PreparedStatement preparedStatement = conexaoPedido.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
             // PreparedStatement defini os valores dos espaços reservados antes de executar a instrução (sql)
            preparedStatement.setInt(1, pedido.getCliente().getIdUsuario());
            preparedStatement.setInt(2, pedido.getStatus());
            preparedStatement.setDouble(3, pedido.getValorFinal());
            // Executa a instrução SQL no banco de dados após definir todos os valores dos espaços reservados.
            preparedStatement.executeUpdate();
            // Obtém um objeto ResultSet contendo as chaves geradas durante a execução da instrução SQL de inserção. 
            try(ResultSet resultSet = preparedStatement.getGeneratedKeys()){
                if(resultSet.next()){
                    int idPedido = resultSet.getInt(1);
                    return idPedido;// Retorna o ID gerado para o novo pedido
                }
            }
        }
        return 0; // processo de inserção falhou 
    }
    // Método para buscar um objeto Pedido no banco de dados pelo id do pedido
    public static Pedido buscarPedidoPorId(int id) throws SQLException, InputMismatchException, DadosInvalidosException{
        String sql = "SELECT * FROM pedido WHERE id_pedido = ?;";// Consulta SQL para buscar um objeto Pedido pelo id do pedido
        // Inicia um bloco 'try' para manipular possíveis exceções do SQL
        try(PreparedStatement preparedStatement = conexaoPedido.prepareStatement(sql)){
             // Define o primeiro espaço reservado na instrução SQL com o valor do ID
            preparedStatement.setInt(1, id);

            try(ResultSet resultSet = preparedStatement.executeQuery()){// Executa a consulta SQL e obtém o resultado
                if(resultSet.next()){// Verifica se há um próximo resultado no conjunto de resultados
                    // Chama o método mapearResultSetParaPedido para converter o resultado em um objeto Pedido
                    return mapearResultSetParaPedido(resultSet);
                }
            }
        }
        return null; // retorna null se não tiver nenhum Pedido com id inserido
    }
    // Método estático para buscar objetos Pedido pelo id do cliente no banco de dados
    public static List<Pedido> buscarPedidosPorCliente(int idCliente) throws SQLException, InputMismatchException, DadosInvalidosException{
        // Consulta SQL para buscar pedidos associados a um cliente específico.
        String sql = "SELECT * FROM pedido WHERE fk_id_usuario = ?;";
        
        List<Pedido> pedidosPorCliente = new ArrayList<>();
        try(PreparedStatement preparedStatement = conexaoPedido.prepareStatement(sql)){
            // Define o ID do cliente no espaço reservado na consulta SQL.
            preparedStatement.setInt(1, idCliente);

            try(ResultSet resultSet = preparedStatement.executeQuery()){
                while(resultSet.next()){// Mapeia os resultados do banco de dados para objetos Pedido e os adiciona à lista.
                    pedidosPorCliente.add(mapearResultSetParaPedido(resultSet));
                }
                return pedidosPorCliente;// Retorna a lista de objetos Pedido associados ao cliente especificado.
            }
        }
    }
    // Método estático para listar todos os objetos Pedido no banco de dados
    public static List<Pedido> listarTodosPedidos() throws SQLException, InputMismatchException, DadosInvalidosException{
        List<Pedido> pedidos = new ArrayList<>(); // Cria uma lista vazia para armazenar objetos Pedido
        String sql = "SELECT * FROM pedido;";// Consulta SQL para selecionar todos os pedidos da tabela
        // Inicia um bloco 'try-with-resources' para garantir o fechamento automático dos recursos (PreparedStatement e ResultSet)
        try(PreparedStatement preparedStatement = conexaoPedido.prepareStatement(sql);
            // Prepara e executa a consulta SQL, obtendo um conjunto de resultados
            ResultSet resultSet = preparedStatement.executeQuery()){
            while(resultSet.next()){//Itera sobre os resultados do conjunto de resultados
                // Mapeia os resultados da linha atual para um objeto pedido usando o método mapearResultSetParaPedido()
                pedidos.add(mapearResultSetParaPedido(resultSet));
            }
        }
        return pedidos; // Retorna a lista de objetos pedido 
    }
    // Método estático para atualizar um objeto Pedido no banco de dados
    public static void atualizarPedido(Pedido pedido) throws SQLException{
         // Consulta SQL para atualizar os dados do pedido na tabela 'pedido'
        String sql = "UPDATE pedido SET fk_id_usuario = ?, status = ?, valor_final = ? WHERE id_pedido = ?;";
        // Inicia um bloco 'try-with-resources' para garantir o fechamento automático do PreparedStatement
        try(PreparedStatement preparedStatement = conexaoPedido.prepareStatement(sql)){
            preparedStatement.setInt(1, pedido.getCliente().getIdUsuario());
            preparedStatement.setInt(2, pedido.getStatus());
            preparedStatement.setDouble(3, pedido.getValorFinal());
            preparedStatement.setInt(4, pedido.getIdPedido());

            preparedStatement.executeUpdate();// Executa a atualização no banco de dados
        }
    }
    // Método estático para atualizar o valor final de um pedido no banco de dados.
    public static void atualizarValorFinal(Pedido pedido) throws SQLException{
         // Consulta SQL para atualizar o valor final do pedido.
        String sql = "UPDATE pedido SET valor_final = ? WHERE id_pedido = ?;";

        try(PreparedStatement preparedStatement = conexaoPedido.prepareStatement(sql)){
            preparedStatement.setDouble(1, pedido.getValorFinal());// Define o novo valor final no primeiro espaço reservado na consulta SQL.
            preparedStatement.setInt(2, pedido.getIdPedido()); // Define o ID do pedido no segundo espaço reservado na consulta SQL.

            preparedStatement.executeUpdate();// Executa a atualização no banco de dados.
        }
    }
   // Método estático para atualizar o status de um pedido no banco de dados.
    public static void atualizarStatusDao(int idPedido, int status)throws SQLException{
        // Consulta SQL para atualizar o status do pedido.
        String sql = "UPDATE pedido SET status = ? WHERE id_pedido = ?;";

        try(PreparedStatement preparedStatement = conexaoPedido.prepareStatement(sql)){
            preparedStatement.setInt(1, status);// Define o novo status no primeiro espaço reservado na consulta SQL.
            preparedStatement.setInt(2, idPedido);

            preparedStatement.executeUpdate(); // Executa a atualização no banco de dados
        }
    }
    // Método estático para excluir um pedido do banco de dados com base no ID do pedido fornecido.
    public static void excluirPedido(int id) throws SQLException{
         // Consulta SQL para excluir um pedido do banco de dados.
        String sql = "DELETE FROM pedido WHERE fk_id_usuario = ?;";
        // Inicia um bloco 'try-with-resources' para garantir o fechamento automático do PreparedStatement
        try(PreparedStatement preparedStatement = conexaoPedido.prepareStatement(sql)){
            preparedStatement.setInt(1, id);// Define o ID do pedido no espaço reservado na consulta SQL.

            preparedStatement.executeUpdate();// Executa a exclusão no banco de dados
        }
    }

    // método auxiliar para mapear um resultSet para um objeto pedido
    private static Pedido mapearResultSetParaPedido(ResultSet resultSet) throws SQLException, InputMismatchException, DadosInvalidosException{
       // Extrai os valores das colunas do ResultSet
        int id = resultSet.getInt("id_pedido");
        int idCliente = resultSet.getInt("fk_id_usuario");
        int status = resultSet.getInt("status");
        double valorFinal = resultSet.getDouble("valor_final");
         // Obtém o objeto cliente associado ao pedido utilizando o método buscarUsuarioPorId da classe UsuarioDao
        Usuario cliente = UsuarioDao.buscarUsuarioPorId(idCliente);
        // Cria um novo objeto Pedido com os valores extraídos do ResultSet e retorna-o
        return new Pedido(id, cliente, PedidoServico.returnStatus(status), valorFinal); 
    }
}