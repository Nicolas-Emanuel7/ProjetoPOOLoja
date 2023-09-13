import java.util.*;

import Entidades.*;

public class Menu {
    
    // OPÇÕES DO CLIENTE ///////////////////////////////////////////////////////////
    public static void cadastroCliente(){

    }

    public static Cliente loginCliente(){

        return new Cliente(0, null, null, null);
    }

    public static void atualizarCliente(){

    }

    public static void deletarCliente(){

    }

    public static void criarPedido(){

    }

    public static void adicionarItemPedido(){

    }

    public static void cancelarPedido(){

    }

    public static void listarPedidosPorCliente(){

    }

    // OPÇÕES DO GERENTE ///////////////////////////////////////////////////////

    public static void cadastroGerente(){

    }

    public static Gerente loginGerente(){

        return new Gerente(0, null, null, null);
    }

    public static void atualizarGerente(){

    }

    public static void listarGerentes(){

    }

    public static void deletarGerente(){
        
    }

    public static void criarProduto(){

    }

    public static void atualizarEstoque(){

    }

    public static void atualizarProduto(){

    }

    public static void deletarProduto(){

    }

    public static void listarClientes(){

    }

    public static void buscarCliente(){

    }

    public static void apagarClienteComoGerente(){

    }

    public static void buscarPedidoPorCliente(){

    }

    public static void atualizarPedidoStatus(){

    }

    public static void listarPedidos(){

    }

    public static void listarProdutos(){

    }

    public static void buscarProduto(){

    }

    // CLEAR BUFFER ///////////////////////////////////////////////////////////////

    public static void clearBuffer(Scanner scanner){
        if(scanner.hasNextLine()){
            scanner.nextLine();
        }
    }
}
