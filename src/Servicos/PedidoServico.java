package Servicos;

import java.util.*;
import java.sql.*;

import Entidades.*;
import Excecoes.DadosInvalidosException;
import DAO.*;

public class PedidoServico {

    List<ItemDePedido> Carrinho = new ArrayList<>();
    
    public Pedido criarPedido(Usuario clienteEscolhido) throws InputMismatchException, SQLException{
        
        StatusPedido status = StatusPedido.PROCESSANDO; 
        double valorFinal = 0.0; 

        try {
            Carrinho.clear();
            Pedido novoPedido = new Pedido(clienteEscolhido, status, valorFinal); 
            PedidoDao.inserirPedido(novoPedido);
            
            return novoPedido;
        } catch (InputMismatchException e) {
            e.printStackTrace();
        } 
        return null; 

    }

    public void addItemAoPedido(Pedido pedido, int quantidade, Calcado calcadoEscolhido, int tamanho) throws SQLException, DadosInvalidosException{

        try{
            if(calcadoEscolhido != null){
                ItemDePedido novoItem = new ItemDePedido(pedido.getIdPedido(), calcadoEscolhido.getIdCalcado(), quantidade, tamanho);
                ItemPedidoDao.inserirItemDePedido(novoItem);

                Carrinho.add(novoItem);
                System.out.println(">>>Subtotal: R$ "+calcularSubTotal(calcadoEscolhido, quantidade)+"\n");
            }
            else{
                System.out.println(">>>Preço final da compra: R$ "+calcularTotalPedido(Carrinho)+"\n");
                
                pedido.setValorFinal(calcularTotalPedido(Carrinho));
                
                int idPedido = PedidoDao.listarTodosPedidos().size();
                PedidoDao.atualizarValorFinal(idPedido, pedido);
            }
        } catch(InputMismatchException e){
            e.printStackTrace();
        }
    }

    public double calcularSubTotal(Calcado calcado, int quantidade){
        double subtotal = 0;
        subtotal = calcado.getPreco() * quantidade;
        return subtotal;
    }

    public double calcularTotalPedido(List<ItemDePedido> Carrinho) throws InputMismatchException, SQLException{
        double resultado = 0;

        for(ItemDePedido item : Carrinho){
            Calcado calcado = CalcadoServico.getCalcadoPorId(item.getidCalcado());
            resultado += calcado.getPreco() * item.getQuantidade();
        }
        return resultado;
    }

    public String getPedidosPorCLiente(int idCliente) throws InputMismatchException, SQLException, DadosInvalidosException{
        String pedidosPorCliente = "";

        for(Pedido pedido : PedidoDao.buscarPedidosPorCliente(idCliente)){
            pedidosPorCliente += pedido.toString();
        }
        return pedidosPorCliente;
    }

    public void AtualizarStatusPedido(int idPedido) throws InputMismatchException, SQLException, DadosInvalidosException{
        
        Pedido pedidoAtt = PedidoDao.buscarPedidoPorId(idPedido);
        if(pedidoAtt != null){
            if(pedidoAtt.getStatus() == 1){
                pedidoAtt.setStatus(StatusPedido.ENVIADO);
                PedidoDao.atualizarStatusDao(idPedido, 2); 
                System.out.println(">>>O pedido foi enviado.\n");
            }
            else if(pedidoAtt.getStatus() == 2){
                pedidoAtt.setStatus(StatusPedido.ENTREGUE);
                PedidoDao.atualizarStatusDao(idPedido, 3);
                System.out.println(">>>O pedido agora foi entregue.\n");
            }
            else if(pedidoAtt.getStatus() == 3){
                System.out.println(">>>O pedido já foi entregue\n");
            }
        }
    }

    public static StatusPedido returnStatus(int status){
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

    public void cancelarPedido(int idPedido) throws InputMismatchException, SQLException, DadosInvalidosException{
        
        Pedido pedidoCancelar = PedidoDao.buscarPedidoPorId(idPedido);
        if(pedidoCancelar != null){
            pedidoCancelar.setStatus(StatusPedido.CANCELADO);
            PedidoDao.atualizarStatusDao(idPedido, 4);
            System.out.println(">>>Pedido cancelado.");
        }
    }

    public void apagarPedido(int idPedido) throws SQLException{

        if(idPedido == 0){
            System.out.println(">>>Operação cancelada.");
        } else{
            PedidoDao.excluirPedido(idPedido);
            System.out.println(">>>Pedido excluido com sucesso.");
        }
    }

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
