package DAO;

import java.sql.*; // importando pacote para trabalhar com banco de dados

// estabelecer uma conexão com o banco de dados PostgreSQL usando as informações fornecidas
public class ConexaoPostgreSQL { 
    public static Connection conectar(){// método conectar(), que retorna um objeto do tipo Connection.
        String url = "jdbc:postgresql://localhost:5432/ProjetoPOOLoja"; // Define a URL de conexão com o banco de dados PostgreSQL. 
        String usuario = "postgres"; // Define o nome de usuário usado para autenticação no banco de dados PostgreSQL.
        String senha = "55879276";

        Connection conexao = null; // inicializar conexao como null

        try{ // Estabelece a conexão usando o DriverManager e os detalhes fornecidos
            conexao = DriverManager.getConnection(url, usuario, senha);
            System.out.println("Conexão com o PostgreSQL estabelecida");
        }catch(SQLException e){// Se ocorrer uma exceção SQLException, imprime a mensagem de erro
            System.err.println("Erro ao conectar: "+ e.getMessage());
        }
        return conexao;  // Retorna o objeto Connection
    }
}
