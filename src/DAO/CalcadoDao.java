package DAO;
// importações de pacotes
import Entidades.*;
import Servicos.*;
import java.sql.*;
import java.util.*;

public class CalcadoDao {
    private static Connection conexaoCalcado;// Atributo estático para armazenar a conexão com o banco de dados
    
    public CalcadoDao(Connection conexaoCalcado){ // construtor da classe CalcadoDao
        CalcadoDao.conexaoCalcado = conexaoCalcado;// Inicializa a conexão estática com o banco de dados
    }
    // Método para inserir um objeto Calcado no banco de dados
    public static void inserirCalcado(Calcado calcado) throws SQLException{
         // String SQL para inserir dados na tabela 'calcado'
        String sql = "INSERT INTO calcado (id_gerente, tipo_calcado, modelo_calcado, preco) VALUES (?, ?, ?, ?);";

        // Inicia um bloco 'try' para manipular possíveis exceções do SQL
        try(PreparedStatement preparedStatement = conexaoCalcado.prepareStatement(sql)){ 
             // PreparedStatement defini os valores dos espaços reservados antes de executar a instrução (sql)
            preparedStatement.setInt(1, calcado.getIdGerente()); // define o primeiro espaço reservado na instrução SQL 
            preparedStatement.setInt(2, CalcadoServico.tipoCalcadoNumero(calcado.getTipoCalcado())); 
            preparedStatement.setString(3, calcado.getModeloCalcado());
            preparedStatement.setDouble(4, calcado.getPreco());
            // Executa a instrução SQL no banco de dados após definir todos os valores dos espaços reservados.
            preparedStatement.executeUpdate();
        }
    }
    // Método para buscar um objeto Calcado no banco de dados pelo seu id
    public static Calcado buscarCalcadoPorId(int id) throws SQLException{
        String sql = "SELECT * FROM calcado WHERE id_calcado = ?;"; // Consulta SQL para buscar um calcado pelo id
        // Inicia um bloco 'try' para manipular possíveis exceções do SQL
        try(PreparedStatement preparedStatement = conexaoCalcado.prepareStatement(sql)){
            // Define o primeiro espaço reservado na instrução SQL com o valor do ID
            preparedStatement.setInt(1, id);

            try(ResultSet resultSet = preparedStatement.executeQuery()){// Executa a consulta SQL e obtém o resultado
                if(resultSet.next()){ // Se houver resultados no conjunto de resultados
                    return mapearResultSetParaCalcado(resultSet);// Mapeia os resultados para um objeto Calcado e retorna
                }
            }
        }
        return null; // retorna null se não tiver nenhum calçado com o id inserido
    }
    // Método estático para listar todos os objetos Calcado no banco de dados
    public static List<Calcado> listarTodosCalcados() throws SQLException{
        List<Calcado> calcados = new ArrayList<>(); // Cria uma lista vazia para armazenar objetos Calcado
        String sql = "SELECT * FROM calcado;"; // Consulta SQL para selecionar todos os calçados da tabela
        // Inicia um bloco 'try-with-resources' para garantir o fechamento automático dos recursos (PreparedStatement e ResultSet)
        try(PreparedStatement preparedStatement = conexaoCalcado.prepareStatement(sql);
            // Prepara e executa a consulta SQL, obtendo um conjunto de resultados
            ResultSet resultSet = preparedStatement.executeQuery()){
            while(resultSet.next()){ //Itera sobre os resultados do conjunto de resultados
                // Mapeia os resultados da linha atual para um objeto Calcado usando o método mapearResultSetParaCalcado()
                calcados.add(mapearResultSetParaCalcado(resultSet));
            }
        }
        return calcados; // Retorna a lista de objetos Calcado contendo todos os calçados do banco de dados.

    }
    // Método estático para listar objetos Calcado em ordem crescente de preço no banco de dados
    public static List<Calcado> listarCalcadoPrecoCrescente() throws SQLException{
        List<Calcado> calcadosOrdem = new ArrayList<>(); // Cria uma lista vazia 
        // Consulta SQL para selecionar todos os calçados ordenados por preço em ordem crescente
        String sql = "SELECT * FROM calcado ORDER BY preco ASC;";
       // Inicia um bloco 'try-with-resources' para garantir o fechamento automático dos recursos (PreparedStatement e ResultSet)
        try(PreparedStatement preparedStatement = conexaoCalcado.prepareStatement(sql);
            // Prepara e executa a consulta SQL, obtendo um conjunto de resultados
            ResultSet resultSet = preparedStatement.executeQuery()){
            while(resultSet.next()){//Itera sobre os resultados do conjunto de resultados
                // Mapeia os resultados para objetos Calcado usando o método mapearResultSetParaCalcado()
                calcadosOrdem.add(mapearResultSetParaCalcado(resultSet));
            }
        }
        return calcadosOrdem; // Retorna a lista de objetos Calcado ordenados por preço em ordem crescente
    }
    
    // mesmas funções do metódo anterior, mas lista em ordem decrescente
    public static List<Calcado> listarCalcadoPrecoDecrescente() throws SQLException{
        List<Calcado> calcadosOrdem = new ArrayList<>();
        String sql = "SELECT * FROM calcado ORDER BY preco DESC;";

        try(PreparedStatement preparedStatement = conexaoCalcado.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery()){
            while(resultSet.next()){
                calcadosOrdem.add(mapearResultSetParaCalcado(resultSet));
            }
        }
        return calcadosOrdem;
    }
    
    // quase igual as funções do metódo anterior, mas lista o objeto calçado pelo tipo
    public static List<Calcado> listarCalcadoPorTipo(int tipoCalcado) throws SQLException{
        List<Calcado> calcadosOrdem = new ArrayList<>();
        // Consulta SQL para selecionar calçados pelo tipo
        String sql = "SELECT * FROM calcado WHERE tipo_calcado = ? ORDER BY modelo_calcado ASC;";

        try(PreparedStatement preparedStatement = conexaoCalcado.prepareStatement(sql)){
            // Define o valor do parâmetro na consulta SQL com o tipo de calçado desejado
            preparedStatement.setInt(1, tipoCalcado);

            try(ResultSet resultSet = preparedStatement.executeQuery()){
                while(resultSet.next()){
                    calcadosOrdem.add(mapearResultSetParaCalcado(resultSet));
                }
            }
        }
        return calcadosOrdem;
    }
    // Método estático para atualizar um objeto Calcado no banco de dados
    public static void atualizarCalcado(Calcado calcado, int idGerente) throws SQLException{
         // Consulta SQL para atualizar os dados do calçado na tabela 'calcado'
        String sql = "UPDATE calcado SET id_gerente = ?, tipo_calcado = ?, modelo_calcado = ?, preco = ? WHERE id_calcado = ?;";
        // Inicia um bloco 'try-with-resources' para garantir o fechamento automático do PreparedStatement
        try(PreparedStatement preparedStatement = conexaoCalcado.prepareStatement(sql)){
            preparedStatement.setInt(1, idGerente);// Define o novo ID do gerente
            preparedStatement.setInt(2, CalcadoServico.tipoCalcadoNumero(calcado.getTipoCalcado()));// Converte e define o tipo do calçado
            preparedStatement.setString(3, calcado.getModeloCalcado());
            preparedStatement.setDouble(4, calcado.getPreco());
            preparedStatement.setInt(5, calcado.getIdCalcado());

            preparedStatement.executeUpdate(); // Executa a atualização no banco de dados
        }
    }
   // Método para excluir um calçado do banco de dados com base no ID fornecido
    public static void excluirCalcado(int id) throws SQLException{
         // Consulta SQL para excluir um calçado da tabela 'calcado' com base no ID
        String sql = "DELETE FROM calcado WHERE id_calcado = ?;";
         // Inicia um bloco 'try-with-resources' para garantir o fechamento automático do PreparedStatement
        try(PreparedStatement preparedStatement = conexaoCalcado.prepareStatement(sql)){
            // Define o valor do primeiro espaço reservado na consulta SQL para o ID do calçado a ser excluído 
            preparedStatement.setInt(1, id); 

            preparedStatement.executeUpdate(); // Executa a exclusão do calçado no banco de dados
            // Após excluir o calçado, também excluímos as informações correspondentes no estoque
            EstoqueDao.excluirEstoque(id);
        }
    }
    
    // Método para mapear os resultados de uma consulta SQL para um objeto Calcado
    /* ResultSet está sendo utilizado para armazenar os resultados de uma consulta ao banco de dados 
    que retorna informações sobre calçados. */

    // SQLException Se ocorrer algum erro ao acessar ou manipular os dados do ResultSet.
    public static Calcado mapearResultSetParaCalcado(ResultSet resultSet) throws SQLException{
         // Extrai os valores das colunas do ResultSet
        int idCalcado = resultSet.getInt("id_calcado"); 
        int idGerente = resultSet.getInt("id_gerente");
        int tipoCalcado = resultSet.getInt("tipo_calcado");
        String modeloCalcado = resultSet.getString("modelo_calcado");
        double preco = resultSet.getDouble("preco");
        // Cria um novo objeto Calcado com os valores extraídos do ResultSet e retorna-o
        return new Calcado(idCalcado, idGerente, CalcadoServico.returnTipoCalcado(tipoCalcado), modeloCalcado, preco);
    }
}
