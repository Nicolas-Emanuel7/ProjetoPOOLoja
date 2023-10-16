package DAO;
// importações de pacotes
import Entidades.*;
import java.sql.*;
import java.util.*;

public class EstoqueDao {
    
    private static Connection conexaoEstoque; // Atributo estático para armazenar a conexão com o banco de dados
    
    public EstoqueDao(Connection conexaoEstoque){// construtor da classe EstoqueDao
        EstoqueDao.conexaoEstoque = conexaoEstoque; // Inicializa a conexão estática com o banco de dados
    }
    
    // Método para inserir um objeto estoque no banco de dados
    public static void inserirEstoque(Estoque estoque) throws SQLException{
        // String SQL para inserir dados na tabela 'estoque'
        String sql = "INSERT INTO estoque (fk_id_calcado, tamanho, quantidade_disponivel) VALUES (?, ?, ?);";
       // Inicia um bloco 'try' para manipular possíveis exceções do SQL
        try(PreparedStatement preparedStatement = conexaoEstoque.prepareStatement(sql)){
            // PreparedStatement defini os valores dos espaços reservados antes de executar a instrução (sql)
            preparedStatement.setInt(1, estoque.getIdCalcado()); // define o primeiro espaço reservado na instrução SQL 
            preparedStatement.setInt(2, estoque.getTamanhoDisponivel());
            preparedStatement.setInt(3, estoque.getQuantidadeEstoque());
            // Executa a instrução SQL no banco de dados após definir todos os valores dos espaços reservados.
            preparedStatement.executeUpdate();
        }
    }
    // Método para buscar um objeto Estoque no banco de dados pelo id do calçado
    public static Estoque buscarEstoquePorId(int idCalcado) throws SQLException{
        String sql = "SELECT * FROM estoque WHERE id_calcado = ?;";// Consulta SQL para buscar um objeto estoque pelo id do calçado
        // Inicia um bloco 'try' para manipular possíveis exceções do SQL
        try(PreparedStatement preparedStatement = conexaoEstoque.prepareStatement(sql)){
           // Define o primeiro espaço reservado na instrução SQL com o valor do ID
            preparedStatement.setInt(1, idCalcado);
         
            try(ResultSet resultSet = preparedStatement.executeQuery()){ // Executa a consulta SQL e obtém o resultado
                if(resultSet.next()){ // Verifica se há um próximo resultado no conjunto de resultados
                    // Chama o método mapearResultSetParaEstoque para converter o resultado em um objeto Estoque
                    return mapearResultSetParaEstoque(resultSet);
                }
            }
        }
        return null;// retorna null se não tiver nenhum calçado com o id inserido
    }
    //  Busca e retorna um objeto Estoque específico com base no ID do calçado e no tamanho especificados.
    public static Estoque buscarEstoqueEspecifico(int idCalcado, int tamanho) throws SQLException{
        // Consulta SQL para buscar um objeto estoque especifico pelo id e tamanho do calçado
        String sql = "SELECT * FROM estoque WHERE fk_id_calcado = ? AND tamanho = ?;";
        // Inicia um bloco 'try' para manipular possíveis exceções do SQL
        try(PreparedStatement preparedStatement = conexaoEstoque.prepareStatement(sql)){
            // Define os valores dos espaços reservados na consulta SQL com os parâmetros passados
            preparedStatement.setInt(1, idCalcado);
            preparedStatement.setInt(2, tamanho);

            try(ResultSet resultSet = preparedStatement.executeQuery()){// Executa a consulta SQL e obtém o resultado
                if(resultSet.next()){ // Verifica se há um próximo resultado no conjunto de resultados
                    // Chama o método mapearResultSetParaEstoque para converter o resultado em um objeto Estoque
                    return mapearResultSetParaEstoque(resultSet);
                }
            }
        }// Se nenhum resultado foi encontrado, retorna null
        return null;
    }
    // Método estático para listar todos os objetos Estoque representando os tamanhos disponíveis do calçado especificado no banco de dados
    public static List<Estoque> listarTamanhosDisponiveis(int id_calcado) throws SQLException{
        List<Estoque> tamanhosDisponiveis = new ArrayList<>();// Cria uma lista vazia para armazenar objetos Estoque
        String sql = "SELECT * FROM estoque WHERE fk_id_calcado = ? AND quantidade_disponivel > 0;";
        // Inicia um bloco 'try-with-resources' para garantir o fechamento automático dos recursos
        try(PreparedStatement preparedStatement = conexaoEstoque.prepareStatement(sql)){
            // Define o primeiro espaço reservado na instrução SQL com o valor do ID do calçado
            preparedStatement.setInt(1, id_calcado);

            try(ResultSet resultSet = preparedStatement.executeQuery()){// Executa a consulta SQL e obtém o resultado
                while(resultSet.next()){  // Itera sobre os resultados do conjunto de resultados
                    // Mapeia os resultados para objetos Estoque e os adiciona à lista
                    tamanhosDisponiveis.add(mapearResultSetParaEstoque(resultSet));
                }
            }
        }
        return tamanhosDisponiveis; // Retorna a lista de objetos Estoque contendo todos os tamanhos de calçado do banco de dados.
    }
    // Método estático para atualizar um objeto Estoque no banco de dados
    public static void atualizarEstoque(Estoque estoque) throws SQLException{
         // Consulta SQL para atualizar os dados do estoque na tabela 'estoque'
        String sql = "UPDATE estoque SET quantidade_disponivel = ? WHERE fk_id_calcado = ? AND tamanho = ?;";
        // Inicia um bloco 'try-with-resources' para garantir o fechamento automático do PreparedStatement
        try(PreparedStatement preparedStatement = conexaoEstoque.prepareStatement(sql)){
            preparedStatement.setInt(1, estoque.getQuantidadeEstoque());// Define a nova quantidade disponível no estoque
            preparedStatement.setInt(2, estoque.getIdCalcado());
            preparedStatement.setInt(3, estoque.getTamanhoDisponivel());

            preparedStatement.executeUpdate(); // Executa a atualização no banco de dados
        }
    }
   // Método para excluir um estoque do banco de dados com base no ID do tipo de calçado fornecido
    public static void excluirEstoque(int idCalcado) throws SQLException{
        // Consulta SQL para excluir um estoque da tabela 'estoque' com base no ID do calçado 
        String sql = "DELETE FROM estoque WHERE fk_id_calcado = ?;";
       // Inicia um bloco 'try-with-resources' para garantir o fechamento automático do PreparedStatement
        try(PreparedStatement preparedStatement = conexaoEstoque.prepareStatement(sql)){
            // Define o valor do primeiro espaço reservado na consulta SQL para o ID do calçado a ser excluído do estoque
            preparedStatement.setInt(1, idCalcado);
            
            preparedStatement.executeUpdate(); // Executa a exclusão do calçado no banco de dados
        }
    }

    // Método para mapear os resultados de uma consulta SQL para um objeto do Estoque.
    /* ResultSet está sendo utilizado para armazenar os resultados de uma consulta ao banco de dados 
    que retorna informações sobre o estoque de calçados. */

    // SQLException Se ocorrer algum erro ao acessar ou manipular os dados do ResultSet.
    private static Estoque mapearResultSetParaEstoque(ResultSet resultSet) throws SQLException{
        // Extrai os valores das colunas do ResultSet
        int idEstoque = resultSet.getInt("id_estoque");
        int idCalcado = resultSet.getInt("fk_id_calcado");
        int tamanho = resultSet.getInt("tamanho");
        int quantidade = resultSet.getInt("quantidade_disponivel");
        // Cria um novo objeto Estoque com os valores extraídos do ResultSet e retorna-o
        return new Estoque(idEstoque, idCalcado, tamanho, quantidade);
    }
    
}

