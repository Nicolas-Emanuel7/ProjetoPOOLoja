package Servicos;

import java.util.*;
import java.sql.*;

import Entidades.*;
import DAO.*;

public class CalcadoServico {
    public void addCalcado(TipoCalcado tipoCalcado, int idGerente, String modeloCalcado, double preco) throws SQLException{
        try{
            Calcado novoCalcado = new Calcado(idGerente, tipoCalcado, modeloCalcado, preco);
            System.out.println(">>>Calçado adicionado ao sistema.");
            CalcadoDao.inserirCalcado(novoCalcado);
        } catch(InputMismatchException e){
            e.printStackTrace();
        }
    }

    public void addTamanhosDoCalcado(int idCalcado, int tamanho, int quantidade, int tamanhosQuantidade) throws SQLException{
        try{
            Estoque tamanhoDisponivel = new Estoque(idCalcado, tamanho, quantidade);
            System.out.println(">>>Estoque recebido com sucesso");
            EstoqueDao.inserirEstoque(tamanhoDisponivel);
        } catch(InputMismatchException e){
            e.printStackTrace();
        }
    }

    public static Calcado getCalcadoPorId(int idBusca) throws SQLException{
        Calcado calcadoSolicitado = CalcadoDao.buscarCalcadoPorId(idBusca);
        if(calcadoSolicitado != null){
            return calcadoSolicitado;
        }else{
            return null;
        }
    }

    public double getPrecoPorId(int idBusca) throws SQLException{
        Calcado calcadoSolicitado = CalcadoDao.buscarCalcadoPorId(idBusca);
        if(calcadoSolicitado != null){
            return calcadoSolicitado.getPreco();
        }else{
            return 0;
        }
    }

    public void atualizarCalcado(Calcado calcadoAtualizado, int idGerente) throws SQLException{
        try{
            CalcadoDao.atualizarCalcado(calcadoAtualizado, idGerente);
            EstoqueDao.excluirEstoque(calcadoAtualizado.getIdCalcado());
            System.out.println(">>>Produto atualizado com sucesso.");
            System.out.println(calcadoAtualizado.toString());
            
        } catch(InputMismatchException e){
            e.printStackTrace();
        }
    }

    public void aumentarEstoque(int idBusca, int tamanho, int novoEstoque, int quantidadeTamanhos) throws SQLException{
        try{
            Estoque calcadoAumentarEstoque = EstoqueDao.buscarEstoqueEspecifico(idBusca, tamanho);
            if(calcadoAumentarEstoque != null){
                novoEstoque += calcadoAumentarEstoque.getQuantidadeEstoque();
                calcadoAumentarEstoque.setQuantidadeEstoque(novoEstoque);
                EstoqueDao.atualizarEstoque(calcadoAumentarEstoque);
                System.out.println(">>>Estoque atualizado com suceso.");
            }else if(calcadoAumentarEstoque == null){
                addTamanhosDoCalcado(idBusca, tamanho, novoEstoque, quantidadeTamanhos);
            }
        } catch(InputMismatchException e){
            e.printStackTrace();
        }
    }

    public void diminuirEstoque(int idBusca, int tamanho, int quantidadeComprada) throws SQLException{
        try{
            Estoque calcadoDiminuirEstoque = EstoqueDao.buscarEstoqueEspecifico(idBusca, tamanho);
            if(calcadoDiminuirEstoque != null){
                int novoEstoque = calcadoDiminuirEstoque.getQuantidadeEstoque() - quantidadeComprada;
                calcadoDiminuirEstoque.setQuantidadeEstoque(novoEstoque);
                EstoqueDao.atualizarEstoque(calcadoDiminuirEstoque);
            }
        } catch(InputMismatchException e){
            e.printStackTrace();
        }
    }

    public boolean checarCompra(int idBusca, int tamanhoEscolhido, int quantidade) throws SQLException{
        try{
            Estoque calcadoDiminuirEstoque = EstoqueDao.buscarEstoqueEspecifico(idBusca, tamanhoEscolhido);
            if(calcadoDiminuirEstoque != null){
                int novoEstoque = calcadoDiminuirEstoque.getQuantidadeEstoque() - quantidade;
                if(novoEstoque >= 0){
                    return true;
                } else{
                    return false;
                }
            }
        } catch(InputMismatchException e){
            e.printStackTrace();
        }
        return false;
    }

    public void apagarCalcado(int idCalcado) throws SQLException{
        if(idCalcado == 0){
            System.out.println(">>>Operação cancelada.");
        } else{
            CalcadoDao.excluirCalcado(idCalcado);
            System.out.println(">>>Operação concluída");
        }
    }

    public List <Calcado> getTodosCalcados() throws SQLException{
        return CalcadoDao.listarTodosCalcados();
    }

    public List <Calcado> getCalcadosPorTipo(int tipoCalcado) throws SQLException{
        return CalcadoDao.listarCalcadoPorTipo(tipoCalcado);
    }

    public String listarCalcadosPorTipo(int tipoCalcado) throws SQLException{
        String listarCalcadosTipo = "";
        for(Calcado calcado : CalcadoDao.listarCalcadoPorTipo(tipoCalcado)){
            listarCalcadosTipo += mostrarCalcadoIndividual(calcado);
        }
        return listarCalcadosTipo;
    }

    public String listarCalcadosPorPreco(int opcaoCouD) throws SQLException{ // CouD = Crescente ou Decrescente
        String listarCalcadosPreco = "";
        if(opcaoCouD == 1){
            for(Calcado calcadoLista : CalcadoDao.listarCalcadoPrecoCrescente()){
                listarCalcadosPreco += mostrarCalcadoIndividual(calcadoLista);
            }
        }else if(opcaoCouD == 2){
            for(Calcado calcadoLista : CalcadoDao.listarCalcadoPrecoDecrescente()){
                listarCalcadosPreco += mostrarCalcadoIndividual(calcadoLista);
            }
        }
        return listarCalcadosPreco;
    }

    public String mostrarCalcadoIndividual(Calcado calcado) throws SQLException{
        String calcadoIndividual = "";
        calcadoIndividual += calcado.toString();
        calcadoIndividual += mostrarTamanhosPorCalcado(calcado)+"\n";
        return calcadoIndividual;
    }

    public String mostrarCalcados() throws SQLException{
        String calcadosLista = "";
        for(Calcado calcado : CalcadoDao.listarTodosCalcados()){
            calcadosLista += calcado.toString();
            calcadosLista += mostrarTamanhosPorCalcado(calcado)+"\n";
        }
        return calcadosLista;
    }

    public String mostrarTamanhosPorCalcado(Calcado calcado) throws SQLException{
        String tamanhosPorCalcado = ">>>Tamanhos disponíveis: ";
        for(Estoque tamanho : EstoqueDao.listarTamanhosDisponiveis(calcado.getIdCalcado())){
            tamanhosPorCalcado += "T "+tamanho.getTamanhoDisponivel()+"("+tamanho.getQuantidadeEstoque()+")   ";
        }
        return tamanhosPorCalcado;
    }

    public String mostrarTiposDeCalcados(){
        String tiposCalcados = "1-Bota         2-Chuteira      3-Rasteirinha      4-Salto\n";
            tiposCalcados   += "5-Sandália     6-Sapatinha     7-Sapato           8-Tênis\n";

        return tiposCalcados;
    }

    public static String tipoCalcadoTexto(TipoCalcado tipoCalcado){
        String tipo = "";
        if(tipoCalcado == TipoCalcado.BOTA){
            tipo += "Bota";
            return tipo;
        } else if(tipoCalcado == TipoCalcado.CHUTEIRA){
            tipo += "Chuteira";
            return tipo;
        } else if(tipoCalcado == TipoCalcado.RASTEIRINHA){
            tipo += "Rasteirinha";
            return tipo;
        } else if(tipoCalcado == TipoCalcado.SALTO){
            tipo += "Salto";
            return tipo;
        } else if(tipoCalcado == TipoCalcado.SANDALIA){
            tipo += "Sandália";
            return tipo;
        } else if(tipoCalcado == TipoCalcado.SAPATILHA){
            tipo += "Sapatilha";
            return tipo;
        } else if(tipoCalcado == TipoCalcado.SAPATO){
            tipo += "Sapato";
            return tipo;
        } else if(tipoCalcado == TipoCalcado.TENIS){
            tipo += "Tênis";
            return tipo;
        }
        return tipo;
    }

    public static int tipoCalcadoNumero(TipoCalcado tipoCalcado){
        int tipo = 0;
        if(tipoCalcado == TipoCalcado.BOTA){
            tipo = 1;
        } else if(tipoCalcado == TipoCalcado.CHUTEIRA){
            tipo = 2;
        } else if(tipoCalcado == TipoCalcado.RASTEIRINHA){
            tipo = 3;
        } else if(tipoCalcado == TipoCalcado.SALTO){
            tipo = 4;
        } else if(tipoCalcado == TipoCalcado.SANDALIA){
            tipo = 5;
        } else if(tipoCalcado == TipoCalcado.SAPATILHA){
            tipo = 6;
        } else if(tipoCalcado == TipoCalcado.SAPATO){
            tipo = 7;
        } else if(tipoCalcado == TipoCalcado.TENIS){
            tipo = 8;
        }
        return tipo;
    }

    public static TipoCalcado returnTipoCalcado(int tipo){
        if(tipo == 1){
            return TipoCalcado.BOTA;
        } else if(tipo == 2){
            return TipoCalcado.CHUTEIRA;
        } else if(tipo == 3){
            return TipoCalcado.RASTEIRINHA;
        } else if(tipo == 4){
            return TipoCalcado.SALTO;
        } else if(tipo == 5){
            return TipoCalcado.SANDALIA;
        } else if(tipo == 6){
            return TipoCalcado.SAPATILHA;
        } else if(tipo == 7){
            return TipoCalcado.SAPATO;
        } else if(tipo == 8){
            return TipoCalcado.TENIS;
        } else{
            return null;
        }
    }
}

