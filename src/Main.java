import Entidades.*;

import java.sql.Connection;
import java.util.*;

import DAO.ConexaoPostgreSQL;
public class Main {
    

    Connection conexao = ConexaoPostgreSQL.conectar();
    public static void main(String[] args) {
        
        Scanner scanner = new Scanner(System.in);
        int op1 = 1;

        do{
            System.out.println("\nBem vindo à loja Marinho!");
            System.out.println("Que tipo de usuário você é?\n");
            System.out.println("1- GERENTE");
            System.out.println("2- CLIENTE\n");
            System.out.println("0- SAIR DO SISTEMA\n");

                op1 = scanner.nextInt();
                Menu.clearBuffer(scanner);

                int opLogin = 1;

                switch(op1){

                    // MENU DO GERENTE /////////////////////////////
                    case 1:
                    while(opLogin != 0){
                        menuLogin();
                        opLogin = scanner.nextInt();
                        Menu.clearBuffer(scanner);

                        switch(opLogin){

                            case 1:
                                Gerente gerente = Menu.loginGerente();
                                int opMenuGerente = 1;
                                if(gerente != null){
                                    do{
                                        menuGerente();
                                        opMenuGerente = scanner.nextInt();
                                        Menu.clearBuffer(scanner);

                                        switch(opMenuGerente){
                                            // SUBMENU DE PRODUTOS PARA GERENTE
                                            case 1:
                                                int opSubmenuGerente1 = 1;
                                                do{
                                                    menuProdutosGerente();
                                                    opSubmenuGerente1 = scanner.nextInt();
                                                    Menu.clearBuffer(scanner);

                                                    switch(opSubmenuGerente1){
                                                        case 1:
                                                            Menu.criarProduto();
                                                        break;
                                                        case 2:
                                                            Menu.atualizarEstoque();
                                                        break;
                                                        case 3:
                                                            Menu.listarProdutos();
                                                        break;
                                                        case 4:
                                                            Menu.buscarProduto();
                                                        break;
                                                        case 5:
                                                            Menu.atualizarProduto();
                                                        break;
                                                        case 6:
                                                            Menu.deletarProduto();
                                                        break;
                                                        case 0:
                                                            System.out.println("Voltando...");
                                                        break;
                                                        default:
                                                            System.out.println("Opção inválida.");
                                                        break;
                                                    }

                                                } while(opSubmenuGerente1 != 0);
                                            break;

                                            // SUBMENU DE CLIENTES PARA GERENTE
                                            case 2:
                                                int opSubmenuGerente2 = 1;
                                                do{
                                                    menuClientesGerente();
                                                    opSubmenuGerente2 = scanner.nextInt();
                                                    Menu.clearBuffer(scanner);

                                                    switch(opSubmenuGerente2){
                                                        case 1:
                                                            Menu.listarClientes();
                                                        break;
                                                        case 2:
                                                            Menu.buscarCliente();
                                                        break;
                                                        case 3:
                                                            //Menu.deletarCliente();
                                                        break;
                                                        case 0:
                                                            System.out.println("Voltando...");
                                                        break;
                                                        default:
                                                            System.out.println("Opção inválida.");
                                                        break;
                                                    }

                                                } while(opSubmenuGerente2 != 0);
                                            break;

                                            // SUBMENU DE PEDIDOS PARA GERENTE
                                            case 3:
                                                int opSubmenuGerente3 = 1;
                                                do{
                                                    menuPedidosGerente();
                                                    opSubmenuGerente3 = scanner.nextInt();
                                                    Menu.clearBuffer(scanner);

                                                    switch(opSubmenuGerente3){
                                                        case 1:
                                                            Menu.listarPedidos();
                                                        break;
                                                        case 2:
                                                            Menu.buscarPedidoPorCliente();
                                                        break;
                                                        case 3:
                                                            Menu.atualizarPedidoStatus();
                                                        break;
                                                        case 0:
                                                            System.out.println("Voltando...");
                                                        break;
                                                        default:
                                                            System.out.println("Opção inválida.");
                                                        break;
                                                    }

                                                } while(opSubmenuGerente3 != 0);
                                            break;

                                            // SUBMENU DE CONFIGURAÇÕES DE GERENTE
                                            case 4:
                                                int opSubmenuGerente4 = 1;
                                                do{
                                                    menuGerenteConfigurações();
                                                    opSubmenuGerente4 = scanner.nextInt();
                                                    Menu.clearBuffer(scanner);

                                                    switch(opSubmenuGerente4){
                                                        case 1:
                                                            Menu.atualizarGerente();
                                                        break;
                                                        case 2:
                                                            Menu.listarGerentes();
                                                        break;
                                                        case 3:
                                                            Menu.deletarGerente();
                                                        break;
                                                        case 0:
                                                            System.out.println("Voltando...");
                                                        break;
                                                        default:
                                                            System.out.println("Opção inválida.");
                                                        break;
                                                    }

                                                } while(opSubmenuGerente4 != 0);
                                            break;

                                            case 0:
                                                System.out.println("Voltando...");
                                            break;

                                            default:
                                                System.out.println("Opção inválida.");
                                            break;
                                        }
                                    } while(opMenuGerente != 0);   
                                }
                            break;

                            case 2:
                                Menu.cadastroGerente();
                            break;

                            case 0:
                                System.out.println("Voltando...");
                            break;
                            
                            default:
                                System.out.println("Opção inválida.");
                            break;
                        }
                    }
                    break;

                    // MENU DO CLIENTE /////////////////////////////
                    case 2:

                    while(opLogin != 0){
                        menuLogin();
                        opLogin = scanner.nextInt();
                        Menu.clearBuffer(scanner);

                        switch(opLogin){

                            case 1:
                                //Menu.loginCliente();
                                Cliente cliente = Menu.loginCliente();
                                int opMenuCliente = 1;
                                if(cliente != null){
                                    do{
                                        menuCliente();
                                        opMenuCliente = scanner.nextInt();
                                        Menu.clearBuffer(scanner);

                                        switch(opMenuCliente){
                                            // SUBMENU DE PRODUTOS PARA CLIENTE
                                            case 1:
                                                int opSubmenuCliente1 = 1;
                                                do{
                                                    menuProdutosCliente();
                                                    opSubmenuCliente1 = scanner.nextInt();
                                                    Menu.clearBuffer(scanner);

                                                    switch(opSubmenuCliente1){
                                                        case 1:
                                                            Menu.listarProdutos();
                                                        break;
                                                        case 2:
                                                            Menu.buscarProduto();
                                                        break;
                                                        case 0:
                                                            System.out.println("Voltando...");
                                                        break;
                                                        default:
                                                            System.out.println("Opção inválida.");
                                                        break;
                                                    }
                                                } while(opSubmenuCliente1 != 0);
                                            break;

                                            // SUBMENU DE PEDIDOS PARA CLIENTE
                                            case 2:
                                                int opSubmenuCliente2 = 1;
                                                do{
                                                    menuPedidosCliente();
                                                    opSubmenuCliente2 = scanner.nextInt();
                                                    Menu.clearBuffer(scanner);
                                                    
                                                    switch(opSubmenuCliente2){
                                                        case 1:
                                                            Menu.criarPedido();
                                                        break;
                                                        case 2:
                                                            Menu.listarPedidosPorCliente();
                                                        break;
                                                        case 3:
                                                            Menu.cancelarPedido();
                                                        break;
                                                        case 0:
                                                            System.out.println("Voltando...");
                                                        break;
                                                        default:
                                                            System.out.println("Opção inválida.");
                                                        break;
                                                    }
                                                } while(opSubmenuCliente2 != 0);
                                            break;

                                            // SUBMENU DE CONFIGURAÇÕES PARA CLIENTE
                                            case 3:
                                                int opSubmenuCliente3 = 1;
                                                do{
                                                    menuClienteConfigurações();
                                                    opSubmenuCliente3 = scanner.nextInt();
                                                    Menu.clearBuffer(scanner);
                                                    
                                                    switch(opSubmenuCliente3){
                                                        case 1:
                                                            Menu.atualizarCliente();
                                                        break;
                                                        case 2:
                                                            Menu.deletarCliente();
                                                        break;
                                                        case 0:
                                                            System.out.println("Voltando...");
                                                        break;
                                                        default:
                                                            System.out.println("Opção inválida.");
                                                        break;
                                                    }
                                                } while(opSubmenuCliente3 != 0);
                                            break;

                                            case 0:
                                                System.out.println("Voltando...");
                                            break;

                                            default:
                                                System.out.println("Opção inválida.");
                                            break;
                                        }
                                    } while(opMenuCliente != 0);
                                }
                                
                            break;

                            case 2:
                                Menu.cadastroCliente();
                            break;

                            case 0:
                                System.out.println("Voltando...");
                            break;

                            default:
                                System.out.println("Opção inválida.");
                            break;
                        }
                    }
                    break;

                    // ENCERRAR O PROGRAMA ////////////////////////
                    case 0:
                        System.out.println("Encerrando o programa...");
                    break;

                    // OPÇÃO INVÁLIDA ////////////////////////////
                    default:
                        System.out.println("Opção inválida.");
                    break;
                }
        }while(op1 != 0);
    }

    // OPÇÕES DE LOGIN //
    public static void menuLogin(){
        System.out.println("\nJá possui cadastro?");
        System.out.println("(1)- LOGIN");
        System.out.println("(2)- CADASTRO\n");
        System.out.println("(0)- VOLTAR\n");
    }

    // MENU DO GERENTE //
    public static void menuGerente(){
        System.out.println("\n(1)- GERENCIAMENTO DE PRODUTOS");
        System.out.println("(2)- GERENCIAMENTO DE CLIENTES");
        System.out.println("(3)- GERENCIAMENTO DE PEDIDOS");
        System.out.println("(4)- CONFIGURAÇÕES DE GERENTES");
        System.out.println("(0)- VOLTAR\n");
    }

    // SUBMENUS DO GERENTE
    public static void menuProdutosGerente(){
        System.out.println("\n(1)- ADICIONAR UM NOVO PRODUTO AO SISTEMA");
        System.out.println("(2)- ATUALIZAR ESTOQUE DE UM PRODUTO");
        System.out.println("(3)- LISTAR PRODUTOS CADASTRADOS");
        System.out.println("(4)- BUSCAR PRODUTO ESPECÍFICO");
        System.out.println("(5)- ATUALIZAR DADOS DE UM PRODUTO");
        System.out.println("(6)- DELETAR PRODUTO DO SISTEMA");
        System.out.println("(0)- VOLTAR\n");
    }
    public static void menuClientesGerente(){
        System.out.println("\n(1)- LISTAR CLIENTES CADASTRADOS");
        System.out.println("(2)- BUSCAR CLIENTE ESPECÍFICO");
        System.out.println("(3)- DELETAR CLIENTE DO SISTEMA");
        System.out.println("(0)- VOLTAR\n");
    }
    public static void menuPedidosGerente(){
        System.out.println("\n(1)- LISTAR PEDIDOS NO SISTEMA");
        System.out.println("(2)- BUSCAR PEDIDOS POR CLIENTE");
        System.out.println("(3)- ATUALIZAR STATUS DE UM PEDIDO");
        System.out.println("(0)- VOLTAR\n");
    }
    public static void menuGerenteConfigurações(){
        System.out.println("\n(1)- ATUALIZAR DADOS DA CONTA");
        System.out.println("(2)- LISTAR GERENTES CADASTRADOS");
        System.out.println("(3)- DELETAR CONTA");
        System.out.println("(0)- VOLTAR\n");
    }

    // MENU DO CLIENTE //
    public static void menuCliente(){
        System.out.println("\n(1)- MENU DE PRODUTOS");
        System.out.println("(2)- MENU DE PEDIDOS");
        System.out.println("(3)- CONFIGURAÇÕES DE CONTA");
        System.out.println("(0)- VOLTAR\n");
    }

    // SUBMENUS DO CLIENTE //
    public static void menuProdutosCliente(){
        System.out.println("\n(1)- LISTAR PRODUTOS DISPONÍVEIS");
        System.out.println("(2)- BUSCAR PRODUTO ESPECÍFICO");
        System.out.println("(0)- VOLTAR\n");
    }
    public static void menuPedidosCliente(){
        System.out.println("\n(1)- FAZER UM NOVO PEDIDO");
        System.out.println("(2)- LISTAR PEDIDOS FEITOS");
        System.out.println("(3)- CANCELAR PEDIDO");
        System.out.println("(0)- VOLTAR\n");
    }
    public static void menuClienteConfigurações(){
        System.out.println("\n(1)- ATUALIZAR DADOS DA CONTA");
        System.out.println("(2)- DELETAR CONTA");
        System.out.println("(0)- VOLTAR\n");
    }
}
