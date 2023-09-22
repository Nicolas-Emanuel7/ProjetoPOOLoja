package Servicos;

import java.util.*;
import java.sql.*;

import Entidades.*;
import DAO.*;

public class PedidoServico {

    List<ItemDePedido> Carrinho = new ArrayList<>();
    
    public Pedido criarPedido(Usuario clienteEscolhido) throws InputMismatchException, SQLException{
        
        StatusPedido status = StatusPedido.PROCESSANDO; // criando o status inicial sempre como processando
        double valorFinal = 0.0; // criando valor final como 0

        try {
            Carrinho.clear();
            Pedido novoPedido = new Pedido(clienteEscolhido, status, valorFinal); // criando o novo pedido
            
            return novoPedido;
        } catch (InputMismatchException e) {
            e.printStackTrace();
        } 
        return null; // retornando o id pedido pra ser usando em outras funções

    }

    public List<ItemDePedido> addItemAoPedido(Pedido pedido, int quantidade, Calcado calcadoEscolhido, int tamanho) throws SQLException{

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
                System.out.println(pedido);
                PedidoDao.inserirPedido(pedido);
                
                return Carrinho; // eu n sei como esse return vai ficar, mas sla
            }
        } catch(InputMismatchException e){
            e.printStackTrace();
        }
        return null;
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

    public String getPedidosPorCLiente(int idCliente) throws InputMismatchException, SQLException{
        String pedidosPorCliente = "";

        for(Pedido pedido : PedidoDao.buscarPedidosPorCliente(idCliente)){
            pedidosPorCliente += pedido.toString();
        }
        return pedidosPorCliente;
    }

    public void AtualizarStatusPedido(int idPedido) throws InputMismatchException, SQLException{
        
        Pedido pedidoAtt = PedidoDao.buscarPedidoPorId(idPedido);
        if(pedidoAtt != null){
            if(pedidoAtt.getStatus() == 1){
                pedidoAtt.setStatus(StatusPedido.ENVIADO);
                PedidoDao.atualizarStatusDao(idPedido, 2); // ta atualizando no banco de dados o status, ai precisa q seja um int
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

    public static String returnStatusTexto(int status){
        String statusTexto = "";
        if(status == 1){
            return statusTexto += ">>>Processando.";
        } else if(status == 2){
            return statusTexto += ">>>Enviado.";
        } else if(status == 3){
            return statusTexto += ">>>Entregue.";
        } else if(status == 4){
            return statusTexto += ">>>Cancelado.";
        }
        return null;
    }

    public void cancelarPedido(int idPedido) throws InputMismatchException, SQLException{
        
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

    public String mostrarPedidos() throws InputMismatchException, SQLException{ // to string padrão
        String pedidosLista = "";
        for(Pedido pedido : PedidoDao.listarTodosPedidos()){
            pedidosLista += pedido.toString();
        }
        return pedidosLista;
    }

}
