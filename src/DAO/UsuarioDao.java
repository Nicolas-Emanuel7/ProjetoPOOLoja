package DAO;
// importação de pacotes
import Entidades.*;
import Excecoes.DadosInvalidosException;

import java.sql.*;
import java.util.*;

public class UsuarioDao {

    private static Connection conexaoUsuario; // Atributo estático para armazenar a conexão com o banco de dados

    public UsuarioDao(Connection conexaoUsuario){ // construtor da classe UsuarioDao
        UsuarioDao.conexaoUsuario = conexaoUsuario;// Inicializa a conexão estática com o banco de dados
    }
   // Método para inserir um objeto Usuário no banco de dados
    public static void inserirUsuario(Usuario usuario) throws SQLException{
        String sql = "INSERT INTO usuario (nome_usuario, login_usuario, senha_usuario, endereco, gerencia) VALUES (?, ?, ?, ?, ?);";
        // Inicia um bloco 'try' para manipular possíveis exceções do SQL    
        try(PreparedStatement preparedStatement = conexaoUsuario.prepareStatement(sql)){
            // PreparedStatement define os valores dos espaços reservados antes de executar a instrução (sql)
            preparedStatement.setString(1, usuario.getNomeUsuario());// define o primeiro espaço reservado na instrução SQL 
            preparedStatement.setString(2, usuario.getLoginUsuario());
            preparedStatement.setString(3, usuario.getSenhaUsuario());
            preparedStatement.setString(4, usuario.getEndereco());
            preparedStatement.setBoolean(5, usuario.isGerencia());
            // Executa a instrução SQL no banco de dados após definir todos os valores dos espaços reservados.
            preparedStatement.executeUpdate();
        }
    }
    // Método para buscar um objeto Usuário no banco de dados pelo id do usuário
    public static Usuario buscarUsuarioPorId(int id) throws SQLException, DadosInvalidosException{
        String sql = "SELECT * FROM usuario WHERE id_usuario = ?;";// Consulta SQL para buscar um objeto Usuário pelo id do usuário
        // Inicia um bloco 'try' para manipular possíveis exceções do SQL
        try(PreparedStatement preparedStatement = conexaoUsuario.prepareStatement(sql)){
                                                                
            preparedStatement.setInt(1, id);

            try(ResultSet resultSet = preparedStatement.executeQuery()){// Executa a consulta SQL e obtém o resultado
                if(resultSet.next()){// Verifica se há um próximo resultado no conjunto de resultados
                    // Chama o método mapearResultSetParaUsuario para converter o resultado em um objeto Usuário
                    return mapearResultSetParaUsuario(resultSet);
                }
            }
        }
        return null; // retorna null se não tiver nenhum usuário com o id inserido
    }
    // Método estático para buscar um usuário no banco de dados com base no login, senha e flag de gerência
    public static Usuario buscarUsuarioPorLoginSenha(String login, String senha, boolean gerencia) throws SQLException, DadosInvalidosException{
        // Consulta SQL para buscar um usuário com os critérios fornecidos
        String sql = "SELECT * FROM usuario WHERE login_usuario = ? AND senha_usuario = ? AND gerencia = ?;";
       // Inicia um bloco 'try-with-resources' para garantir o fechamento automático dos recursos PreparedStatement e ResultSet
        try(PreparedStatement preparedStatement = conexaoUsuario.prepareStatement(sql)){
            preparedStatement.setString(1, login); // Define o valor do primeiro espaço reservado na instrução SQL com base no parâmetro 'login'
            preparedStatement.setString(2, senha);
            preparedStatement.setBoolean(3, gerencia);

            try(ResultSet resultSet = preparedStatement.executeQuery()){
                if(resultSet.next()){// Verifica se há um próximo resultado no conjunto de resultados
                    return mapearResultSetParaUsuario(resultSet);// Mapeia o resultado para um objeto Usuario e o retorna
                }
            }
        }
        return null; // usuário não encontrado
    }
    // Método estático para verificar se um login está disponível no banco de dados
    public static boolean verificarLoginDisponivel(String login) throws SQLException{
        String sql = "SELECT * FROM usuario WHERE login_usuario = ?;";// Consulta SQL para verificar se o login já está em uso
        // Inicia um bloco 'try-with-resources' para garantir o fechamento automático do PreparedStatement
        try(PreparedStatement preparedStatement = conexaoUsuario.prepareStatement(sql)){
            preparedStatement.setString(1, login);// Define o valor do espaço reservado na instrução SQL com base no parâmetro 'login'

            try(ResultSet resultSet = preparedStatement.executeQuery()){// Verifica se há um próximo resultado no conjunto de resultados
                if(resultSet.next()){
                    return true;// Se existir, o login não está disponível
                } else{
                    return false;
                }
            }
        }
    }
    // Método estático para listar todos os objetos Usuario por cargo no banco de dados
    public static List<Usuario> listarUsuariosPorCargo(boolean gerencia) throws SQLException, DadosInvalidosException{
        // Cria uma lista vazia para armazenar objetos Usuarios
        List<Usuario> Usuarios = new ArrayList<>();
        // Consulta SQL para selecionar usuários com base no cargo
        String sql = "SELECT * FROM usuario WHERE gerencia = ?;";

        try(PreparedStatement preparedStatement = conexaoUsuario.prepareStatement(sql)){
            preparedStatement.setBoolean(1, gerencia);// Define o valor do espaço reservado na instrução SQL com base no parâmetro 'gerencia'

            try(ResultSet resultSet = preparedStatement.executeQuery()){
                while(resultSet.next()){
                    // Adiciona objetos Usuario mapeados a partir do ResultSet à lista
                    Usuarios.add(mapearResultSetParaUsuario(resultSet));
                }
            }
        }
        return Usuarios;  // Retorna a lista de objetos usuario
    }
     // Método estático para atualizar um objeto Usuário no banco de dados
    public static void atualizarUsuario(Usuario usuario) throws SQLException{
         // Consulta SQL para atualizar os dados do usuário na tabela 'usuario'
        String sql = "UPDATE usuario SET nome_usuario = ?, login_usuario = ?, senha_usuario = ?, endereco = ? WHERE id_usuario = ?;";
        // Inicia um bloco 'try-with-resources' para garantir o fechamento automático do PreparedStatement
        try(PreparedStatement preparedStatement = conexaoUsuario.prepareStatement(sql)){
            preparedStatement.setString(1, usuario.getNomeUsuario());
            preparedStatement.setString(2, usuario.getLoginUsuario());
            preparedStatement.setString(3, usuario.getSenhaUsuario());
            preparedStatement.setString(4, usuario.getEndereco());
            preparedStatement.setInt(5, usuario.getIdUsuario());

            preparedStatement.executeUpdate();// Executa a atualização no banco de dados
        }
    }
    // Método estático para excluir um usuáriodo banco de dados com base no ID fornecido.
    public static void excluirUsuario(int id) throws SQLException{
        // Consulta SQL para excluir um usuário do banco de dados.
        String sql = "DELETE FROM usuario WHERE id_usuario = ?;";

         // Inicia um bloco 'try-with-resources' para garantir o fechamento automático do PreparedStatement
        try(PreparedStatement preparedStatement = conexaoUsuario.prepareStatement(sql)){
            preparedStatement.setInt(1, id);// Define o ID do usuário no espaço reservado na consulta SQL.

            preparedStatement.executeUpdate();// Executa a exclusão no banco de dados
        }
    }
    // método auxiliar para mapear um resultSet para um objeto usuário
    private static Usuario mapearResultSetParaUsuario(ResultSet resultSet) throws SQLException, DadosInvalidosException{
         // Extrai os valores das colunas do ResultSet
        int id = resultSet.getInt("id_usuario");
        String nome = resultSet.getString("nome_usuario");
        String login = resultSet.getString("login_usuario");
        String senha = resultSet.getString("senha_usuario");
        String endereco = resultSet.getString("endereco");
        boolean cargo = resultSet.getBoolean("gerencia");
        // Cria um novo objeto Usuario com os valores extraídos do ResultSet e retorna-o
        return new Usuario(id, nome, login, senha, endereco, cargo);
    }
}
