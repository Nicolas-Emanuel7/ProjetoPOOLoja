package DAO;

import java.sql.*;

public class ConexaoPostgreSQL {
    public static Connection conectar(){
        String url = "jdbc:postgresql://localhost:5432/ProjetoPOOLoja";
        String usuario = "postgres";
        String senha = "55879276";

        Connection conexao = null;

        try{
            conexao = DriverManager.getConnection(url, usuario, senha);
            System.out.println("Conexão com o PostgreSQL estabelecida");
        }catch(SQLException e){
            System.err.println("Erro ao conectar: "+ e.getMessage());
        }
        return conexao;
    }
}
