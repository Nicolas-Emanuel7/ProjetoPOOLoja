import java.sql.SQLException;
import java.util.*;

import Entidades.*;
import Servicos.*;

public class Menu {

    static ClienteServico clienteServico = new ClienteServico();
    
    // OPÇÕES DO CLIENTE ///////////////////////////////////////////////////////////
    public static void cadastroCliente() throws SQLException{ // essa funciona
        
        Scanner entrada = new Scanner(System.in);
        try{
            System.out.println(">>>Digite o nome do cliente: ");
            String nomeCliente = entrada.nextLine();
            System.out.println(">>>Digite o email do cliente: ");
            String email = entrada.nextLine();
            System.out.println(">>>Digite o endereço do cliente: ");
            String endereco = entrada.nextLine();

            if(clienteServico.verfificarEmail(email) == true){
                System.out.println(">>>Email já cadastrado");
            } else if(clienteServico.verfificarEmail(email) == false){
                clienteServico.cadastrarCliente(nomeCliente, email, endereco); 
            }
                    
        } catch (InputMismatchException e) {
            e.printStackTrace();
        } 
        finally{
            clearBuffer(entrada);
        }        
    }

    public static Cliente loginCliente() throws SQLException{ // bugada

        Scanner entrada = new Scanner(System.in);
        try{
            System.out.println(">>>Digite o seu email: ");
            String emailCliente = entrada.nextLine();
                            
            if(clienteServico.loginCliente(emailCliente) != null){
                System.out.println(">>>Login bem sucedido.");
                return clienteServico.loginCliente(emailCliente);
            } else{
                System.out.println(">>>Login não sucedido.");
            }
        } catch(InputMismatchException e){
            e.printStackTrace();
        } finally{
            clearBuffer(entrada);
        }
        return null;
    }

    public static void atualizarCliente(Cliente cliente) throws SQLException{ // funciona

        Scanner entrada = new Scanner(System.in);

        try{ 
            System.out.println(">>>Seus dados: "+cliente.toString()); 
            
            System.out.println(">>>Digite o novo nome do cliente: ");
            cliente.setNome(entrada.nextLine());
            System.out.println(">>>Digite o novo email do cliente: ");
            cliente.setEmail(entrada.nextLine());
            System.out.println(">>>Digite o novo endereço do cliente: ");
            cliente.setEndereco(entrada.nextLine());

            clienteServico.atualizarCliente(cliente); //ai atualiza os dados, mantendo o id
        } catch (InputMismatchException e) {
            e.printStackTrace();
        } finally{
            clearBuffer(entrada);
        }
    }

    public static void deletarCliente(Cliente cliente) throws SQLException{ // não testei
        
        Scanner entrada = new Scanner(System.in);
        try{
            System.out.println(">>>Tem certeza que deseja apagar sua conta? (1)-Sim (0)-Não");
            int opcao = entrada.nextInt();

            if(opcao == 1){
                System.out.println(">>>Para confirmar, digite seu email: ");
                String emailApagar = entrada.nextLine();

                if(emailApagar == cliente.getEmail()){
                    clienteServico.apagarConta(cliente.getIdCliente());
                } else{
                    System.out.println(">>>Email incorreto.");
                }
            } else{
                System.out.println(">>>Operação cancelada.");
            }
        }catch(InputMismatchException e){
            e.printStackTrace();
        }finally{
            clearBuffer(entrada);
        }
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
