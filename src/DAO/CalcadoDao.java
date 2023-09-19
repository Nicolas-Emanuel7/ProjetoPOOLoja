package DAO;

import Entidades.*;
import Servicos.*;
import java.sql.*;
import java.util.*;

public class CalcadoDao {
    private static Connection conexaoCalcado;

    public CalcadoDao(Connection conexaoCalcado){
        CalcadoDao.conexaoCalcado = conexaoCalcado;
    }

    public static void inserirCalcado(Calcado calcado) throws SQLException{
        String sql = "INSERT INTO calcado (id_gerente, tipo_calcado, modelo_calcado, preco) VALUES (?, ?, ?, ?);";

        try(PreparedStatement preparedStatement = conexaoCalcado.prepareStatement(sql)){
            preparedStatement.setInt(1, calcado.getIdGerente());
            preparedStatement.setInt(2, CalcadoServico.tipoCalcadoNumero(calcado.getTipoCalcado()));
            preparedStatement.setString(3, calcado.getModeloCalcado());
            preparedStatement.setDouble(4, calcado.getPreco());

            preparedStatement.executeUpdate();
        }
    }

    public static Calcado buscarCalcadoPorId(int id) throws SQLException{
        String sql = "SELECT * FROM calcado WHERE id_calcado = ?;";

        try(PreparedStatement preparedStatement = conexaoCalcado.prepareStatement(sql)){
            preparedStatement.setInt(1, id);

            try(ResultSet resultSet = preparedStatement.executeQuery()){
                if(resultSet.next()){
                    return mapearResultSetParaCalcado(resultSet);
                }
            }
        }
        return null;
    }

    public static List<Calcado> listarTodosCalcados() throws SQLException{
        List<Calcado> calcados = new ArrayList<>();
        String sql = "SELECT * FROM calcado;"; // ta colcoando em ordem alfabetica

        try(PreparedStatement preparedStatement = conexaoCalcado.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery()){
            while(resultSet.next()){
                calcados.add(mapearResultSetParaCalcado(resultSet));   
            }
        }
        return calcados;
    }

    public static List<Calcado> listarCalcadoPrecoCrescente() throws SQLException{
        List<Calcado> calcadosOrdem = new ArrayList<>();
        String sql = "SELECT * FROM calcado ORDER BY preco ASC;";

        try(PreparedStatement preparedStatement = conexaoCalcado.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery()){
            while(resultSet.next()){
                calcadosOrdem.add(mapearResultSetParaCalcado(resultSet));
            }
        }
        return calcadosOrdem;
    }

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

    public static List<Calcado> listarCalcadoPorTipo(int tipoCalcado) throws SQLException{
        List<Calcado> calcadosOrdem = new ArrayList<>();
        String sql = "SELECT * FROM calcado WHERE tipo_calcado = ? ORDER BY modelo_calcado ASC;";

        try(PreparedStatement preparedStatement = conexaoCalcado.prepareStatement(sql)){
            preparedStatement.setInt(1, tipoCalcado);

            try(ResultSet resultSet = preparedStatement.executeQuery()){
                while(resultSet.next()){
                    calcadosOrdem.add(mapearResultSetParaCalcado(resultSet));
                }
            }
        }
        return calcadosOrdem;
    }

    public static void atualizarCalcado(Calcado calcado, int idGerente) throws SQLException{
        String sql = "UPDATE calcado SET id_gerente = ? tipo_calcado = ?, modelo_calcado = ?, preco = ? WHERE id_calcado = ?;";

        try(PreparedStatement preparedStatement = conexaoCalcado.prepareStatement(sql)){
            preparedStatement.setInt(1, idGerente);
            preparedStatement.setInt(2, CalcadoServico.tipoCalcadoNumero(calcado.getTipoCalcado()));
            preparedStatement.setString(3, calcado.getModeloCalcado());
            preparedStatement.setDouble(4, calcado.getPreco());
            preparedStatement.setInt(5, calcado.getIdCalcado());

            preparedStatement.executeUpdate();
        }
    }

    public static void excluirCalcado(int id) throws SQLException{
        String sql = "DELETE FROM calcado WHERE id_calcado = ?;";

        try(PreparedStatement preparedStatement = conexaoCalcado.prepareStatement(sql)){
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
            EstoqueDao.excluirEstoque(id);
        }
    }

    public static Calcado mapearResultSetParaCalcado(ResultSet resultSet) throws SQLException{
        int idCalcado = resultSet.getInt("id_calcado");
        int idGerente = resultSet.getInt("id_gerente");
        int tipoCalcado = resultSet.getInt("tipo_calcado");
        String modeloCalcado = resultSet.getString("modelo_calcado");
        double preco = resultSet.getDouble("preco");

        return new Calcado(idCalcado, idGerente, CalcadoServico.returnTipoCalcado(tipoCalcado), modeloCalcado, preco);
    }
}
