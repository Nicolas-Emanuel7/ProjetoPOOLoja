package Servicos;
// importação de pacotes
import java.util.*;
import java.sql.*;

import Entidades.*;
import Excecoes.DadosInvalidosException;
import DAO.*;

public class PedidoServico {

    List<ItemDePedido> Carrinho = new ArrayList<>(); // criada uma list para armazenar todos os itens de cada pedido
    // Método para criar um novo pedido para um cliente
    public Pedido criarPedido(Usuario clienteEscolhido) throws InputMismatchException, SQLException{
        StatusPedido status = StatusPedido.PROCESSANDO; 
        double valorFinal = 0.0;  // é dado valores iniciais que irão mudar com o decorrer do uso

        try { // Limpa o carrinho e cria um novo pedido
            Carrinho.clear();
            Pedido novoPedido = new Pedido(clienteEscolhido, status, valorFinal); 
            novoPedido.setIdePedido(PedidoDao.inserirPedido(novoPedido)); // chama a função de inserir pedido do banco de dados, função essa que retorna o id criado pelo prorpio banco de dados :p
            return novoPedido;                                            // então esse id é inserido no objeto novoPedido, para uso imediato, depois retorna esse objeto.
        } catch (InputMismatchException e) {
            e.printStackTrace();
        } 
        return null; 
    }
    // Adiciona um item ao pedido
    public void addItemAoPedido(Pedido pedido, int quantidade, Calcado calcadoEscolhido, int idEstoque) throws SQLException, DadosInvalidosException{

        try{
            if(calcadoEscolhido != null){ // caso algum calçado seja escolhido na hora da compra
                // Cria um novo item de pedido e o adiciona ao carrinho
                ItemDePedido novoItem = new ItemDePedido(pedido.getIdPedido(), calcadoEscolhido.getIdCalcado(), quantidade, idEstoque);
                ItemPedidoDao.inserirItemDePedido(novoItem);

                Carrinho.add(novoItem);
                System.out.println(">>>Subtotal: R$ "+calcularSubTotal(calcadoEscolhido, quantidade)+"\n");// é impresso o valor do item especifico
            }
            else{// se nenhum calçado for adicionado ao carrinho, calcula o preço final da compra e atualiza o pedido com esse valor
                System.out.println(">>>Preço final da compra: R$ "+calcularTotalPedido(Carrinho)+"\n");
                
                pedido.setValorFinal(calcularTotalPedido(Carrinho));
                
                PedidoDao.atualizarValorFinal(pedido); // valor final é salvo no objeto pedido
            }
        } catch(InputMismatchException e){
            e.printStackTrace();
        }
    }
    // Calcula o subtotal de um item de pedido
    public double calcularSubTotal(Calcado calcado, int quantidade){
        double subtotal = 0;
        subtotal = calcado.getPreco() * quantidade; // faz a multiplicação do preço do calçado pela quantidade comprada
        return subtotal;
    }
    // Calcula o preço total do pedido
    public double calcularTotalPedido(List<ItemDePedido> Carrinho) throws InputMismatchException, SQLException{
        double resultado = 0;

        for(ItemDePedido item : Carrinho){ // pega todos os itens individuais salvos no carrinho 
            Calcado calcado = CalcadoServico.getCalcadoPorId(item.getidCalcado());
            resultado += calcado.getPreco() * item.getQuantidade(); // faz a multiplicação do preço de cada calçado pela quant. comprada
        }
        return resultado;
    }
    // Obtém todos os pedidos de um cliente
    public String getPedidosPorCLiente(int idCliente) throws InputMismatchException, SQLException, DadosInvalidosException{
        String pedidosPorCliente = "";

        for(Pedido pedido : PedidoDao.buscarPedidosPorCliente(idCliente)){ // faz a busca dos pedidos do cliente especificado
            pedidosPorCliente += pedido.toString(); // e os imprime
        }
        return pedidosPorCliente;
    }
    // Atualiza o status de um pedido com base no ID do pedido
    public void AtualizarStatusPedido(int idPedido) throws InputMismatchException, SQLException, DadosInvalidosException{
        
        Pedido pedidoAtt = PedidoDao.buscarPedidoPorId(idPedido); // verifica se o pedido procurado está no banco de dados
        if(pedidoAtt != null){//se estiver:
            if(pedidoAtt.getStatus() == 1){  // verfica se o status do pedido é 1 (PROCESSANDO)
                pedidoAtt.setStatus(StatusPedido.ENVIADO); // então atualiza o status para ENVIADO
                PedidoDao.atualizarStatusDao(idPedido, 2); 
                System.out.println(">>>O pedido foi enviado.\n");
            }
            else if(pedidoAtt.getStatus() == 2){  // verfica se o status do pedido é 2 (ENVIADO)
                pedidoAtt.setStatus(StatusPedido.ENTREGUE); // então atualiza o status para ENTREGUE
                PedidoDao.atualizarStatusDao(idPedido, 3);
                System.out.println(">>>O pedido agora foi entregue.\n");
            }
            else if(pedidoAtt.getStatus() == 3){ // verfica se o status do pedido é 3 (ENTREGUE)
                System.out.println(">>>O pedido já foi entregue\n"); // se for, o status chega ao final
            }
        }
    }
    // Método para receber o valor de statusPedido do banco de dados, pois lá esse atributo é salvo como INT
    public static StatusPedido returnStatus(int status){ // então faz a conversão do INT par status correspondente
        if(status == 1){
            return StatusPedido.PROCESSANDO;
        } else if(status == 2){
            return StatusPedido.ENVIADO;
        } else if(status == 3){
            return StatusPedido.ENTREGUE;
        } else if(status == 4){
            return StatusPedido.CANCELADO;
        }
        return null;
    }
      
    // Cancela um pedido com base no ID do pedido
    public void cancelarPedido(int idPedido) throws InputMismatchException, SQLException, DadosInvalidosException{
        
        Pedido pedidoCancelar = PedidoDao.buscarPedidoPorId(idPedido); // verifica se o pedido está salvo no banco de dados
        if(pedidoCancelar != null){
            pedidoCancelar.setStatus(StatusPedido.CANCELADO);
            PedidoDao.atualizarStatusDao(idPedido, 4); // atualiza os status do pedido para 4 (CANCELADO)
            System.out.println(">>>Pedido cancelado.");
        }
    }
    // Apaga um pedido com base no ID do pedido
    public void apagarPedido(int idPedido) throws SQLException{

        if(idPedido == 0){
            System.out.println(">>>Operação cancelada.");
        } else{ // Exclui o pedido do banco de dados
            ItemPedidoDao.excluirItensDoPedido(idPedido);//  primeiro exclui do bd todos os itens daquele pedido
            PedidoDao.excluirPedido(idPedido); // faz a chamada de excluir o pedido do banco de dados
            System.out.println(">>>Pedido excluido com sucesso.");
        }
    }
    // Lista todos os pedidos
    public String mostrarPedidos() throws InputMismatchException, SQLException, DadosInvalidosException{ 
        String pedidosLista = "";
        if(PedidoDao.listarTodosPedidos() != null){
            for(Pedido pedido : PedidoDao.listarTodosPedidos()){
                pedidosLista += pedido.toString();
            }
            return pedidosLista;
        }else{
            pedidosLista += "Não há pedidos cadastrados.";
            return pedidosLista;
        }
    }
}
