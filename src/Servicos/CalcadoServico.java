package Servicos;
// importação de pacotes
import java.util.*;
import java.sql.*;

import Entidades.*;
import Excecoes.DadosInvalidosException;
import DAO.*;

public class CalcadoServico {
    // Método para adicionar um novo calçado ao sistema
    public void addCalcado(TipoCalcado tipoCalcado, int idGerente, String modeloCalcado, double preco) throws SQLException, DadosInvalidosException{
        try{ // Cria um novo objeto Calcado com os dados fornecidos
            Calcado novoCalcado = new Calcado(idGerente, tipoCalcado, modeloCalcado, preco);
            System.out.println(">>>Calçado adicionado ao sistema.");
            CalcadoDao.inserirCalcado(novoCalcado); // chama o método de inserção ao banco de dados
        } catch(InputMismatchException e){
            e.printStackTrace();
        }
    }
    // Método para adicionar tamanhos disponíveis para um calçado no estoque
    public void addTamanhosDoCalcado(int idCalcado, int tamanho, int quantidade, int tamanhosQuantidade) throws SQLException{
        try{ //Cria um novo objeto Estoque com os dados fornecidos
            Estoque tamanhoDisponivel = new Estoque(idCalcado, tamanho, quantidade);
            System.out.println(">>>Estoque recebido com sucesso");
            EstoqueDao.inserirEstoque(tamanhoDisponivel);  // chama o método de inserção ao banco de dados
        } catch(InputMismatchException e){
            e.printStackTrace();
        }
    }
    // Método para obter um calçado pelo ID
    public static Calcado getCalcadoPorId(int idBusca) throws SQLException{
         // Busca o calçado no banco de dados pelo ID
        Calcado calcadoSolicitado = CalcadoDao.buscarCalcadoPorId(idBusca);
        if(calcadoSolicitado != null){
            return calcadoSolicitado;
        }else{
            return null;
        }
    }
    // Método para atualizar um calçado e seu estoque associado
    public void atualizarCalcado(Calcado calcadoAtualizado, int idGerente) throws SQLException{
        try{ // chama a atualização do calçado no banco de dados
            CalcadoDao.atualizarCalcado(calcadoAtualizado, idGerente);
            EstoqueDao.excluirEstoque(calcadoAtualizado.getIdCalcado());// Exclui o estoque associado ao calçado atualizado
            System.out.println(">>>Produto atualizado com sucesso.");
            System.out.println(calcadoAtualizado.toString());
            
        } catch(InputMismatchException e){
            e.printStackTrace();
        }
    }
    // Método para aumentar a quantidade de um determinado calçado no estoque
    public void aumentarEstoque(int idBusca, int tamanho, int novoEstoque, int quantidadeTamanhos) throws SQLException{
        try{  // Busca o estoque do calçado no banco de dados pelo ID e tamanho
            Estoque calcadoAumentarEstoque = EstoqueDao.buscarEstoqueEspecifico(idBusca, tamanho);
            if(calcadoAumentarEstoque != null){ // se achar ele, atualiza a quantidade disponível no estoque
                novoEstoque += calcadoAumentarEstoque.getQuantidadeEstoque();
                calcadoAumentarEstoque.setQuantidadeEstoque(novoEstoque);
                EstoqueDao.atualizarEstoque(calcadoAumentarEstoque);
                System.out.println(">>>Estoque atualizado com suceso.");
            }else if(calcadoAumentarEstoque == null){ // Se não existe um estoque para o tamanho, cria um novo
                addTamanhosDoCalcado(idBusca, tamanho, novoEstoque, quantidadeTamanhos);
            }
        } catch(InputMismatchException e){
            e.printStackTrace();
        }
    }
    // Método para diminuir a quantidade de um determinado calçado no estoque
    public void diminuirEstoque(int idBusca, int tamanho, int quantidadeComprada) throws SQLException{
        try{ // Busca o estoque do calçado no banco de dados pelo ID e tamanho
            Estoque calcadoDiminuirEstoque = EstoqueDao.buscarEstoqueEspecifico(idBusca, tamanho);
            if(calcadoDiminuirEstoque != null){ // se achar ele, subtrai o estoque atual com a quantida comprada
                int novoEstoque = calcadoDiminuirEstoque.getQuantidadeEstoque() - quantidadeComprada;
                calcadoDiminuirEstoque.setQuantidadeEstoque(novoEstoque);
                EstoqueDao.atualizarEstoque(calcadoDiminuirEstoque); // atualiza essa quantidade no objeto e no banco de dados
            }
        } catch(InputMismatchException e){
            e.printStackTrace();
        }
    }
   // Método para verificar se há estoque disponível para uma compra
    public boolean checarCompra(int idBusca, int tamanhoEscolhido, int quantidade) throws SQLException{
        try{// Busca o estoque do calçado no banco de dados pelo ID e tamanho
            Estoque calcadoDiminuirEstoque = EstoqueDao.buscarEstoqueEspecifico(idBusca, tamanhoEscolhido);
            if(calcadoDiminuirEstoque != null){ // se achar ele, subtrai o estoque atual com a quantida comprada
                int novoEstoque = calcadoDiminuirEstoque.getQuantidadeEstoque() - quantidade;
                if(novoEstoque >= 0){// se o restante do estoque for >=0 , irá permitir a compra
                    return true; 
                } else{
                    return false; // recusa a compra
                }
            }
        } catch(InputMismatchException e){
            e.printStackTrace();
        }
        return false;
    }
     // Método para apagar um calçado do sistema
    public void apagarCalcado(int idCalcado) throws SQLException{
        if(idCalcado == 0){// se a entrada for = 0, irá cancelar a operação
            System.out.println(">>>Operação cancelada.");
        } else{  
            EstoqueDao.excluirEstoque(idCalcado);// Exclui o estoque associado ao calçado
            CalcadoDao.excluirCalcado(idCalcado);// Exclui o calçado do banco de dados
            System.out.println(">>>Operação concluída.");
        }
    }
    // Método para listar calçados de um tipo específico
    public String listarCalcadosPorTipo(int tipoCalcado) throws SQLException{
        String listarCalcadosTipo = "";
        for(Calcado calcado : CalcadoDao.listarCalcadoPorTipo(tipoCalcado)){
            // chama a função que lista todos os objetos da tabela e adiciona 1 por 1 na string
            listarCalcadosTipo += mostrarCalcadoIndividual(calcado);
        }
        return listarCalcadosTipo;
    }
   // Método para listar calçados pelo preço
    public String listarCalcadosPorPreco(int opcaoCouD) throws SQLException{ // CouD = Crescente ou Decrescente
        String listarCalcadosPreco = "";
        if(opcaoCouD == 1){ // Lista os calçados por preço em ordem crescente
            for(Calcado calcadoLista : CalcadoDao.listarCalcadoPrecoCrescente()){
                listarCalcadosPreco += mostrarCalcadoIndividual(calcadoLista);
            }
        }else if(opcaoCouD == 2){ // Lista os calçados por preço em ordem crescente
            for(Calcado calcadoLista : CalcadoDao.listarCalcadoPrecoDecrescente()){
                listarCalcadosPreco += mostrarCalcadoIndividual(calcadoLista);
            }
        }
        return listarCalcadosPreco;
    }
    // Método para mostrar informações detalhadas de um calçado
    public String mostrarCalcadoIndividual(Calcado calcado) throws SQLException{
        String calcadoIndividual = "";
        calcadoIndividual += calcado.toString(); // é adicionado a string todos os dados do calçado
        calcadoIndividual += mostrarTamanhosPorCalcado(calcado)+"\n"; // depois é mostrado o estoque disponivel para esse calçado
        return calcadoIndividual;
    }
    // Método para mostrar informações de todos os calçados do sistema
    public String mostrarCalcados() throws SQLException{
        String calcadosLista = "";
        for(Calcado calcado : CalcadoDao.listarTodosCalcados()){ // faz a impressão de todos os calçados do bd
            calcadosLista += calcado.toString();
            calcadosLista += mostrarTamanhosPorCalcado(calcado)+"\n";
        }
        return calcadosLista;
    }
    // Método para mostrar informações de todos os calçados do sistema por tamanho
    public String mostrarTamanhosPorCalcado(Calcado calcado) throws SQLException{
        String tamanhosPorCalcado = ">>>Tamanhos disponíveis: ";
        for(Estoque tamanho : EstoqueDao.listarTamanhosDisponiveis(calcado.getIdCalcado())){//Faz a listagem dos estoques disponiveis do calçado específico
            tamanhosPorCalcado += "T "+tamanho.getTamanhoDisponivel()+"("+tamanho.getQuantidadeEstoque()+")   "; //e os imprime dando o numero do tamanho e quantidade disponivel
        }
        return tamanhosPorCalcado;
    }
    // Método para mostrar os tipos de calçados disponíveis
    public String mostrarTiposDeCalcados(){
        String tiposCalcados = "1-Bota         2-Chuteira      3-Rasteirinha      4-Salto\n";
            tiposCalcados   += "5-Sandália     6-Sapatinha     7-Sapato           8-Tênis\n";

        return tiposCalcados; 
    }
     // Método  para ENVIAR o TipoCalcado para o banco de dados 
     // é necessário pois o TipoCalcado é salvo como um INT no banco de dados, por isso se faz essa tranzição
    public static int tipoCalcadoNumero(TipoCalcado tipoCalcado){
        int tipo = 0;
        if(tipoCalcado == TipoCalcado.BOTA){ // Verifica se o tipo de calçado é BOTA.
            tipo = 1;//Se o tipo de calçado for BOTA, atribui o valor 1 à variável tipo.
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
    // Método usado para RECEBER o TipoCalcado do banco de dados
    // o banco de dados retorna um INT que é transformado no atributo TipoCalcado
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

