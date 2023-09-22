import java.sql.SQLException;
import java.util.*;

import Entidades.*;
import Servicos.*;

public class Menu {

    static ClienteServico clienteServico = new ClienteServico();
    static CalcadoServico calcadoServico = new CalcadoServico();
    static UsuarioServico usuarioServico = new UsuarioServico();
    
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

    public static void criarProduto(Usuario gerente) throws SQLException{ // funciona

        Scanner entrada = new Scanner(System.in);

        try{
            System.out.println(">>>Tipos de Calçados:");
            System.out.println(calcadoServico.mostrarTiposDeCalcados());
            System.out.println(">>>Digite o número correspondente ao tipo que deseja adicionar ao sistema: ");
            String tipo = entrada.nextLine();
            System.out.println(">>>Digite o modelo desse calçado: ");
            String modelo = entrada.nextLine();
            System.out.println(">>>Digite o preço ao qual esse calçado ( "+modelo+" ) será vendido: ");
            String preco = entrada.nextLine();

            calcadoServico.addCalcado(CalcadoServico.returnTipoCalcado(Integer.parseInt(tipo)), gerente.getIdUsuario(), modelo, Double.parseDouble(preco));
        } catch(InputMismatchException e){
            e.printStackTrace();
        } catch(NumberFormatException e){
            System.out.println("Preço inválido.");
        } finally{
            clearBuffer(entrada);
        }
    }

    public static void atualizarEstoque() throws SQLException{
        Scanner entrada = new Scanner(System.in);

        try{
            System.out.println(">>>Produtos no sistema:");
            System.out.println(calcadoServico.mostrarCalcados());
            System.out.println(">>>Digite o ID do calçado ao qual deseja atualizar o estoque: ");
            int idCalcado = entrada.nextInt();

            Calcado calcadoAtualizar = CalcadoServico.getCalcadoPorId(idCalcado);
            
            if(calcadoAtualizar != null){
                System.out.println(">>>O novo estoque inclui quantos tamanhos: ");
                int tamanhos = entrada.nextInt();
                int i = 0;
                do{
                    System.out.println("\n>>>Que tamanho chegou o estoque: ");
                    int tamanhoEscolhido = entrada.nextInt();
                    System.out.println(">>>Digite a quantidade recebida: ");
                    int quantidadeRecebida = entrada.nextInt();
                    calcadoServico.aumentarEstoque(calcadoAtualizar.getIdCalcado(), tamanhoEscolhido, quantidadeRecebida, tamanhos);

                    i++;
                } while(i < tamanhos);
            } else{
                System.out.println(">>>Produto não encontrado.");
            }
        }catch(InputMismatchException e){
            e.printStackTrace();
        }finally{
            clearBuffer(entrada);
        }
    }

    public static void atualizarProduto(Usuario gerente) throws SQLException{ // funciona, mas falta arrumar

        Scanner entrada = new Scanner(System.in);

        try{
            System.out.println(calcadoServico.mostrarCalcados());
            System.out.println(">>>Digite o ID do calçado que deseja atualizar: ");
            int idEscolha = entrada.nextInt();

            Calcado calcadoAtualizar = CalcadoServico.getCalcadoPorId(idEscolha);

            if(calcadoAtualizar != null){
                System.out.println(calcadoServico.mostrarTiposDeCalcados());
                System.out.println(">>>Digite o número correspondente ao novo tipo desse calçado: ");
                String novoTipo = entrada.nextLine();
                System.out.println(">>>Digite o nome desse modelo: ");
                String novoModelo = entrada.nextLine();
                System.out.println(">>>Digite o preço desse modelo: ");
                double novoPreco = entrada.nextDouble();

                calcadoAtualizar.setModeloCalcado(novoModelo);
                calcadoAtualizar.setPreco(novoPreco);
                calcadoAtualizar.setTipoCalcado(CalcadoServico.returnTipoCalcado(Integer.parseInt(novoTipo)));
                calcadoServico.atualizarCalcado(calcadoAtualizar, gerente.getIdUsuario());
            }
        } catch (InputMismatchException e) {
            e.printStackTrace();
        } finally{
            clearBuffer(entrada);
        }
    }

    public static void deletarProduto() throws SQLException{ // não testei
        
        Scanner entrada = new Scanner(System.in);

        try{
            System.out.println(">>>Calçados cadastrados no sistema:\n");
            System.out.println(calcadoServico.mostrarCalcados());
            System.out.println(">>>Digite o ID do calçado que deseja deletar (0 para cancelar)");
            int idProdutoApagar = entrada.nextInt();

            calcadoServico.apagarCalcado(idProdutoApagar);;
        }catch(InputMismatchException e){
            e.printStackTrace();
        }finally{
            clearBuffer(entrada);
        }
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

    public static void listarProduto() throws InputMismatchException, SQLException{ // funciona

        Scanner entrada = new Scanner(System.in);
        System.out.println(calcadoServico.mostrarCalcados()+"\n");

        try{
            System.out.println(">>>Deseja filtrar os produtos? ");
            System.out.println("(1) - Preço Crescente");
            System.out.println("(2) - Preço Decrescente");
            System.out.println("(3) - Por tipo de calçado\n");
            System.out.println("(0) - Cancelar");
            int opcao = entrada.nextInt();
            switch(opcao){
                case 1:
                    System.out.println(calcadoServico.listarCalcadosPorPreco(1));
                break;
                case 2:
                    System.out.println(calcadoServico.listarCalcadosPorPreco(2));
                break;
                case 3:
                    System.out.println(calcadoServico.mostrarTiposDeCalcados());
                    System.out.print(">>>Qual tipo deseja listar? ");
                    int tipo = entrada.nextInt();
                    TipoCalcado tipoCalcado = CalcadoServico.returnTipoCalcado(tipo);
                    if(tipoCalcado != null){
                        System.out.println(calcadoServico.listarCalcadosPorTipo(tipo)); 
                    } else{
                        System.out.println(">>>Tipo inválido.");
                    }
                break;
            }
        }catch(InputMismatchException e){
            e.printStackTrace();
        }finally{
            clearBuffer(entrada);
        }
    }

    public static void buscarProduto() throws SQLException{ // funciona

        Scanner entrada = new Scanner(System.in);

        try{
            System.out.print(">>>Digite o ID do produto que deseja buscar: ");
            int idBusca = entrada.nextInt();

            Calcado calcadoEncontrado = CalcadoServico.getCalcadoPorId(idBusca);

            if(calcadoEncontrado != null){
                System.out.println(">>>Produto encontrado: "+calcadoServico.mostrarCalcadoIndividual(calcadoEncontrado)); //msm coisa do dos clientes
            }
            else{
                System.out.println(">>>Produto não encontrado.\n");
            }
        } catch (InputMismatchException e) {
            e.printStackTrace();
        } finally{
            clearBuffer(entrada);
        }
    }

    // OPCAO USER ////////////////////////////////////////////////////////////////

    public static void cadastroUsuario(boolean cargo) throws SQLException{
        Scanner entrada = new Scanner(System.in);

        try{
            System.out.println("Digite o seu Nome: ");
            String nome = entrada.nextLine();
            System.out.println("Digite o seu Login ");
            String login = entrada.nextLine();
            System.out.println("Digite a sua Senha:  ");
            String senha = entrada.nextLine();

            if(usuarioServico.verificarLogin(login) == true){
                System.out.println("Login já cadastrado.");
            }else{
                usuarioServico.cadastrarUsuario(nome, login, senha, cargo);
            }
        }catch(InputMismatchException e){
            e.printStackTrace();
        }finally{
            clearBuffer(entrada);
        }
    }

    public static Usuario loginUsuario(boolean cargo) throws SQLException{
        Scanner entrada = new Scanner(System.in);
    
        try{
            System.out.println("Digite o seu login: ");
            String login = entrada.nextLine();
            System.out.println("Digite a sua senha: ");
            String senha = entrada.nextLine();

            if(usuarioServico.loginUsuario(login, senha, cargo) != null){
                System.out.println("Login bem sucedido.");
                return usuarioServico.loginUsuario(login, senha, cargo);
            }else{
                System.out.println("Login não sucedido.");
                return null;
            }
        }catch(InputMismatchException e){
            e.printStackTrace();
        }finally{
            clearBuffer(entrada);
        }
        return null;
    }

    public static void atualizarUsuario(Usuario usuario) throws SQLException{ // funciona, mas falta arrumar

        Scanner entrada = new Scanner(System.in);
        try{
            if(usuario != null){
                System.out.println(">>>Seus dados: "+usuario.toString());
                System.out.println(">>>Deseja atualizar seus dados? (1)-Sim (2)-Não");
                int opcao = entrada.nextInt();
                if(opcao == 1){
                    System.out.println(">>>Digite o novo nome do usuario: ");
                    String novoNome = entrada.nextLine();
                    System.out.println(">>>Digite o novo login do usuario: ");
                    String novoLogin = entrada.nextLine();
                    System.out.println(">>>Digite a nova senha do usuario: ");
                    String novaSenha = entrada.nextLine();

                    if(novoLogin != usuario.getLoginUsuario()){
                        if(usuarioServico.verificarLogin(novoLogin) == true){
                            System.out.println("Login já cadastrado.");
                        }else{
                            usuario.setNomeUsuario(novoNome);
                            usuario.setLoginUsuario(novoLogin);
                            usuario.setSenhaUsuario(novaSenha);
                            usuarioServico.atualizarUsuario(usuario);
                        }
                    }else{
                        usuario.setNomeUsuario(novoNome);
                        usuario.setLoginUsuario(novoLogin);
                        usuario.setSenhaUsuario(novaSenha);
                        usuarioServico.atualizarUsuario(usuario);
                    }
                }
                else{
                    System.out.println(">>>Operação cancelada.");
                }
            }
            else{
                System.out.println(">>>Usuário não encontrado.\n");
            }
        }
        catch (InputMismatchException e){
            e.printStackTrace();
        } finally{
            clearBuffer(entrada);
        }
    }

    public static void listarUsuariosPorCargo(boolean cargo) throws SQLException{
        System.out.println(usuarioServico.mostrarUsuariosPorCargo(cargo));
    }

    public static void deletarUsuario(Usuario usuario) throws SQLException{
        Scanner entrada = new Scanner(System.in);
        try{
            System.out.println(">>>Tem certeza que deseja apagar sua conta? (1)-Sim (0)-Não");
            int opcao = entrada.nextInt();

            if(opcao == 1){
                System.out.println(">>>Para confirmar, digite sua senha: ");
                String senhaApagar = entrada.nextLine();

                if(senhaApagar == usuario.getSenhaUsuario()){
                    usuarioServico.apagarUsuario(usuario.getIdUsuario());
                } else{
                    System.out.println(">>>Senha incorreta.");
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

    // CLEAR BUFFER ///////////////////////////////////////////////////////////////

    public static void clearBuffer(Scanner scanner){
        if(scanner.hasNextLine()){
            scanner.nextLine();
        }
    }
}
