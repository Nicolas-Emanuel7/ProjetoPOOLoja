import java.sql.*; //Fornece classes para gerenciar interações com o banco de dados.
import java.util.*;

import Entidades.*;
import Excecoes.DadosInvalidosException;
import Servicos.*;

public class Menu {  
    // criando instancias estáticas para as classes de serviços
    static CalcadoServico calcadoServico = new CalcadoServico(); 
    static UsuarioServico usuarioServico = new UsuarioServico();
    static PedidoServico pedidoServico = new PedidoServico();
    
    // OPÇÕES DE USUÁRIO ////////////////////////////////////////////////////////////////
    // método para cadastrar um novo usuário no sistema
    public static void cadastroUsuario(boolean cargo) throws SQLException, DadosInvalidosException{ // o método 'cadastroUsuario' pode lançar duas exceções
        Scanner entrada = new Scanner(System.in);

        try{ // solicitando informações do usuário
            System.out.println(">>>Digite o seu Nome: ");
            String nome = entrada.nextLine();
            System.out.println(">>>Digite o seu Login: ");
            String login = entrada.nextLine();
            System.out.println(">>>Digite a sua Senha:  ");
            String senha = entrada.nextLine();
            System.out.println(">>>Digite seu endereço: ");
            String endereco = entrada.nextLine();
             // verificando se o login do usuário já está cadastrado
            if(usuarioServico.verificarLogin(login) == true){
                System.out.println(">>>Login já cadastrado.");
            }else{ // se não estiver cadastrado, cadastra um novo usuário
                usuarioServico.cadastrarUsuario(nome, login, senha, endereco, cargo);
            }
        }catch(InputMismatchException e){ // Se ocorrer uma exceção de tipo InputMismatchException,
            e.printStackTrace();         // imprime o rastreamento da pilha para identificar o erro
        }finally{ // limpa o buffer do Scanner
            clearBuffer(entrada);
        }
    }
    // Método para realizar o login de um usuário no sistema
    public static Usuario loginUsuario(boolean cargo) throws SQLException, DadosInvalidosException{
        Scanner entrada = new Scanner(System.in);// obter entrada do usuário
    
        try{ // solicitando informações do usuário
            System.out.println(">>>Digite o seu login: ");
            String login = entrada.nextLine();
            System.out.println(">>>Digite a sua senha: ");
            String senha = entrada.nextLine();
            // Tenta realizar o login usando o serviço de usuário
            if(usuarioServico.loginUsuario(login, senha, cargo) != null){
                System.out.println(">>>Login bem sucedido.");
                return usuarioServico.loginUsuario(login, senha, cargo);
            }else{  // se o login falhar, retorna null
                System.out.println(">>>Login não sucedido.");
                return null;
            }
        }catch(InputMismatchException e){ // Se ocorrer uma exceção de tipo InputMismatchException,
            e.printStackTrace();         // imprime o rastreamento da pilha para identificar o erro
        }finally{ // limpa o buffer do Scanner
            clearBuffer(entrada);
        }
        return null;
    }
    // Método para atualizar um usuário no sistema
    public static void atualizarUsuario(Usuario usuario) throws SQLException{

        Scanner entrada = new Scanner(System.in);
        try{
            System.out.println(">>>Seus dados: "+usuario.toString()); // exibe dados atuais 
            System.out.println(">>>Deseja atualizar seus dados? (1)-Sim (2)-Não");
            String opcao = entrada.nextLine();
            if(opcao.equals("1")){
                System.out.println(">>>Digite o novo nome do usuario: ");
                String novoNome = entrada.nextLine();
                System.out.println(">>>Digite o novo login do usuario: ");
                String novoLogin = entrada.nextLine();
                System.out.println(">>>Digite a nova senha do usuario: ");
                String novaSenha = entrada.nextLine();
                System.out.println(">>>Digite o seu novo endereco: ");
                String novoEndereco = entrada.nextLine();
                // Verifica se o novo login fornecido pelo usuário é igual ao login atual do usuário.
                if(novoLogin.equals(usuario.getLoginUsuario())){
                    usuario.setNomeUsuario(novoNome);
                    usuario.setSenhaUsuario(novaSenha);
                    usuario.setEndereco(novoEndereco);
                    usuarioServico.atualizarUsuario(usuario);
                }else{  // Se o login é diferente, verifica se o novo login já está cadastrado no sistema.
                    if(usuarioServico.verificarLogin(novoLogin) == true){
                        System.out.println(">>>Login já cadastrado.");
                    }else{ // Se não estiver, atualiza as informações do usuário com o novo login, nome, senha e endereço.
                        usuario.setNomeUsuario(novoNome);
                        usuario.setLoginUsuario(novoLogin);
                        usuario.setSenhaUsuario(novaSenha);
                        usuario.setEndereco(novoEndereco);
                        usuarioServico.atualizarUsuario(usuario);
                    }
                }
            }
            else{ // usuário não quis atualizar 
                System.out.println(">>>Operação cancelada.");
            }
        }
        catch (InputMismatchException e){// Se ocorrer uma exceção de tipo InputMismatchException,
            e.printStackTrace();      // imprime o rastreamento da pilha para identificar o erro
        } finally{
            clearBuffer(entrada);
        }
    }
    // Lista o usuário com base no cargo (gerente ou cliente)
    public static void listarUsuariosPorCargo(boolean cargo) throws SQLException, DadosInvalidosException{
        System.out.println(usuarioServico.mostrarUsuariosPorCargo(cargo));
    }
    // Este método permite que um usuário delete sua própria conta.
    public static void deletarUsuario(Usuario usuario) throws SQLException{
        Scanner entrada = new Scanner(System.in);
        try{
            System.out.println(">>>Tem certeza que deseja apagar sua conta? (1)-Sim (0)-Não");
            String opcao = entrada.nextLine();

            if(opcao.equals("1")){
                System.out.println(">>>Para confirmar, digite sua senha: ");
                String senhaApagar = entrada.nextLine();

                if(senhaApagar == usuario.getSenhaUsuario()){
                    usuarioServico.apagarUsuario(usuario.getIdUsuario());
                } else{
                    System.out.println(">>>Senha incorreta.");
                }
            } else{ // não apagar
                System.out.println(">>>Operação cancelada.");
            }
        }catch(InputMismatchException e){ // Se ocorrer uma exceção de tipo InputMismatchException,
            e.printStackTrace();         // imprime o rastreamento da pilha para identificar o erro
        }finally{  // limpa o buffer do Scanner
            clearBuffer(entrada);
        }
    }
    // Deletar um usuário pelo id (o gerente deleta)
    public static void deletarUsuarioPorId(boolean cargo) throws SQLException, DadosInvalidosException{
        Scanner entrada = new Scanner(System.in);
        try{
            listarUsuariosPorCargo(cargo);
            System.out.println(">>>Digite o ID daquele que deseja remover do sistema (0 para cancelar): ");
            int opcao = entrada.nextInt();
            if(opcao != 0){
                Usuario usuarioApagar = usuarioServico.getUsuarioPorId(opcao);
                if(usuarioApagar != null){  // Se o usuário é encontrado, ele é removido do sistema usando o serviço 'usuarioServico'.
                    usuarioServico.apagarUsuario(usuarioApagar.getIdUsuario());
                    System.out.println(">>>Usuário removido com sucesso.");
                }else{
                    System.out.println(">>>Usuário não encontrado.");
                }
            }else{ // Se o usuário é encontrado, ele é removido do sistema usando o serviço 'usuarioServico'.
                System.out.println(">>>Operação cancelada.");
            }
        }catch(InputMismatchException e){// Se ocorrer uma exceção de tipo InputMismatchException,
            e.printStackTrace();  // imprime o rastreamento da pilha para identificar o erro
        }finally{  // limpa o buffer do Scanner
            clearBuffer(entrada);
        }
    }

    // OPÇÕES DE PEDIDO ///////////////////////////////////////////////////////////////////////////
    // permite que um cliente faça um pedido de calçados.
    public static void criarPedido(Usuario cliente) throws SQLException, DadosInvalidosException{

        Scanner entrada = new Scanner(System.in);
        try{//um novo objeto Pedido é criado usando o serviço pedidoServico.
            Pedido pedidoAtual = pedidoServico.criarPedido(cliente);// O cliente que está fazendo o pedido é passado como argumento.

            System.out.println(">>>CALÇADOS DISPONÍVEIS\n");
            System.out.println(calcadoServico.mostrarCalcados());

            int opcao = 1;

            while(opcao != 0){
                System.out.println(">>>Deseja adicionar um produto ao carrinho? (1)-Sim (0)-Não");
                opcao = entrada.nextInt();
                switch(opcao){
                    case 1:
                        System.out.println(">>>Digite o ID do Calçado que deseja adicionar ao carrinho");
                        int idCalcadoEscolhido = entrada.nextInt();
                        System.out.println(">>>Digite o tamanho que deseja comprar: ");
                        int tamanhoEscolhido = entrada.nextInt();
                        System.out.println(">>>Digite a quantidade que deseja comprar: ");
                        int quantidadeEscolhida = entrada.nextInt();

                        Calcado calcadoEscolhido = CalcadoServico.getCalcadoPorId(idCalcadoEscolhido);

                        if(calcadoEscolhido != null){//se o calçado escolhido pelo cliente está disponível 
                            if(calcadoServico.checarCompra(calcadoEscolhido.getIdCalcado(), tamanhoEscolhido, quantidadeEscolhida) == true){
                                pedidoServico.addItemAoPedido(pedidoAtual, quantidadeEscolhida, calcadoEscolhido, tamanhoEscolhido);// calçado é adicionado
                                calcadoServico.diminuirEstoque(calcadoEscolhido.getIdCalcado(), tamanhoEscolhido, quantidadeEscolhida);//estoque é reduzido

                                System.out.println(">>>Pedido adicionado ao carrinho\n");
                            }else{
                                System.out.println(">>>Estoque insuficiente. Desculpa\n");
                            }
                        } else{
                            System.out.println(">>>ID de produto inválido.");
                        }
                    break;

                    case 0:
                        pedidoServico.addItemAoPedido(pedidoAtual, 0, null, 0);
                        System.out.println("\n>>>Pedido finalizado.\n");
                    break;

                    default:
                        System.out.println(">>>Opção inválida.");
                    break;
                }
            }
        } catch (InputMismatchException e) {// Se ocorrer uma exceção de tipo InputMismatchException,
            e.printStackTrace();  // imprime o rastreamento da pilha para identificar o erro
        } finally{  // limpa o buffer do Scanner
            clearBuffer(entrada);
        }
    }
    // permite que um cliente cancele um pedido que tenha feito. 
    public static void cancelarPedido(Usuario cliente) throws SQLException, DadosInvalidosException{ 
        
        Scanner entrada = new Scanner(System.in);
        try{
            listarPedidosPorCliente(cliente);
            System.out.println(">>>Deseja cancelar um pedido? (1)-Sim (0)-Não");
            int opcao = entrada.nextInt();

            if(opcao == 1){
                System.out.println(">>>Por favor, digite o ID do pedido que deseja cancelar: ");
                int idPedido = entrada.nextInt();
                // pedidoServico é então usado para cancelar o pedido com o ID fornecido. 
                pedidoServico.cancelarPedido(idPedido);
                System.out.println(">>>Pedido cancelado.");
            }else{ // cliente desistiu de cancelar um pedido
                System.out.println(">>>Operação cancelada.");
            }
        } catch(InputMismatchException e){// Se ocorrer uma exceção de tipo InputMismatchException,
            e.printStackTrace();  // imprime o rastreamento da pilha para identificar o erro
        } finally{  // limpa o buffer do Scanner
            clearBuffer(entrada);
        }
    }
    // LISTA OS PEDIDOS POR CLIENTE
    public static void listarPedidosPorCliente(Usuario cliente) throws InputMismatchException, SQLException, DadosInvalidosException{ 
        System.out.println(pedidoServico.getPedidosPorCLiente(cliente.getIdUsuario())+"\n");
    }
    // o gerente buscar os pedidos de um cliente específico pelo id.
    public static void buscarPedidosPorCliente() throws SQLException, DadosInvalidosException{
        
        Scanner entrada = new Scanner(System.in);
        
        try{
            System.out.println(">>>Clientes cadastrados no sistema: ");
            System.out.println(usuarioServico.mostrarUsuariosPorCargo(false));
            System.out.println("\n>>>Digite o ID daquele que deseja ver os pedidos: ");
            int idBusca = entrada.nextInt();

            System.out.println(pedidoServico.getPedidosPorCLiente(idBusca)); 
        } catch (InputMismatchException e) {// Se ocorrer uma exceção de tipo InputMismatchException,
            e.printStackTrace();  // imprime o rastreamento da pilha para identificar o erro
        } finally{ // limpa o buffer do Scanner
            clearBuffer(entrada);
        }
    }
    // permitir a um gerente atualizar o status de um pedido no sistema. 
    public static void atualizarPedidoStatus() throws SQLException, DadosInvalidosException{ 

        Scanner entrada = new Scanner(System.in);

        try{
            System.out.println(">>>Pedidos no sistema: ");
            System.out.println(pedidoServico.mostrarPedidos());
            System.out.println("\n>>>Digite o id do qual deseja atualizar o status: ");
            int idPedidoEscolha = entrada.nextInt();
            // atualizar o status do pedido correspondente ao ID fornecido pelo gerente.
            pedidoServico.AtualizarStatusPedido(idPedidoEscolha); 
        } catch (InputMismatchException e) {// Se ocorrer uma exceção de tipo InputMismatchException,
            e.printStackTrace();  // imprime o rastreamento da pilha para identificar o erro
        } finally{  // limpa o buffer do Scanner
            clearBuffer(entrada);
        }
    }
    // Lista os pedidos do sistema
    public static void listarPedidos() throws InputMismatchException, SQLException, DadosInvalidosException{ 
        System.out.println(pedidoServico.mostrarPedidos());
    }

    // OPÇÕES DE PRODUTO /////////////////////////////////////////////////////////////////////////
    // permitir que um gerente adicione um novo produto ao sistema. 
    public static void criarProduto(Usuario gerente) throws SQLException, DadosInvalidosException{

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
            // Adiciona o novo calçado ao sistema usando o serviço de calçados
            calcadoServico.addCalcado(CalcadoServico.returnTipoCalcado(Integer.parseInt(tipo)), gerente.getIdUsuario(), modelo, Double.parseDouble(preco));
        } catch(InputMismatchException e){// Se ocorrer uma exceção de tipo InputMismatchException,
            e.printStackTrace();  // imprime o rastreamento da pilha para identificar o erro
        } catch(NumberFormatException e){
            System.out.println(">>>Preço inválido.");
        } finally{  // limpa o buffer do Scanner
            clearBuffer(entrada);
        }
    }
    // Este método permite aos gerentes atualizar as informações de estoque no sistema.
    public static void atualizarEstoque() throws SQLException{
        Scanner entrada = new Scanner(System.in);

        try{
            System.out.println(">>>Produtos no sistema:");
            System.out.println(calcadoServico.mostrarCalcados());
            System.out.println(">>>Digite o ID do calçado ao qual deseja atualizar o estoque: ");
            int idCalcado = entrada.nextInt();
            //// Obtém o objeto Calcado correspondente ao ID fornecido.
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
                    // Chama o método para aumentar o estoque do produto com as informações fornecidas.
                    calcadoServico.aumentarEstoque(calcadoAtualizar.getIdCalcado(), tamanhoEscolhido, quantidadeRecebida, tamanhos);

                    i++;
                } while(i < tamanhos);
            } else{
                System.out.println(">>>Produto não encontrado.");
            }
        }catch(InputMismatchException e){// Se ocorrer uma exceção de tipo InputMismatchException,
            e.printStackTrace();  // imprime o rastreamento da pilha para identificar o erro
        }finally{  // limpa o buffer do Scanner
            clearBuffer(entrada);
        }
    }
   // Este método permite aos gerentes atualizar as informações de um produto no sistema.
    public static void atualizarProduto(Usuario gerente) throws SQLException{ 

        Scanner entrada = new Scanner(System.in);

        try{
            System.out.println(calcadoServico.mostrarCalcados());
            System.out.println(">>>Digite o ID do calçado que deseja atualizar: ");
            String idEscolha = entrada.nextLine();
            // O objeto Calcado retornado será usado para realizar atualizações no calçado existente.
            Calcado calcadoAtualizar = CalcadoServico.getCalcadoPorId(Integer.parseInt(idEscolha));

            if(calcadoAtualizar != null){ // id encontrado
                System.out.println("\n"+calcadoServico.mostrarTiposDeCalcados()); // exibe os tipos de calçado
                System.out.println(">>>Digite o número correspondente ao tipo que deseja adicionar ao sistema: ");
                String novoTipo = entrada.nextLine();
                System.out.println(">>>Digite o modelo desse calçado: ");
                String novoModelo = entrada.nextLine();
                System.out.println(">>>Digite o preço ao qual esse calçado ( "+novoModelo+" ) será vendido: ");
                String novoPreco = entrada.nextLine();
                // atualiza as propriedades do objeto calcadoAtualizar 
                calcadoAtualizar.setModeloCalcado(novoModelo);
                calcadoAtualizar.setPreco(Double.parseDouble(novoPreco));
                calcadoAtualizar.setTipoCalcado(CalcadoServico.returnTipoCalcado(Integer.parseInt(novoTipo)));
                // chama o método atualizarCalcado do serviço CalcadoServico para efetivar as alterações no calçado.
                calcadoServico.atualizarCalcado(calcadoAtualizar, gerente.getIdUsuario());
            }
        } catch (InputMismatchException e) {// Se ocorrer uma exceção de tipo InputMismatchException,
            e.printStackTrace();  // imprime o rastreamento da pilha para identificar o erro
        } finally{  // limpa o buffer do Scanner
            clearBuffer(entrada);
        }
    }
    // Deletar um produto do sistema pelo seu ID.
    public static void deletarProduto() throws SQLException{ 
        
        Scanner entrada = new Scanner(System.in);

        try{
            System.out.println(">>>Calçados cadastrados no sistema:\n");
            System.out.println(calcadoServico.mostrarCalcados());//exibe todos os produtos no sistema 
            System.out.println(">>>Digite o ID do calçado que deseja deletar (0 para cancelar)");
            int idProdutoApagar = entrada.nextInt();

            calcadoServico.apagarCalcado(idProdutoApagar);;
        }catch(InputMismatchException e){// Se ocorrer uma exceção de tipo InputMismatchException,
            e.printStackTrace();  // imprime o rastreamento da pilha para identificar o erro
        }finally{  // limpa o buffer do Scanner
            clearBuffer(entrada);
        }
    }
    // Este método permite aos usuários listar produtos do sistema com várias opções de filtro.
    public static void listarProduto() throws InputMismatchException, SQLException{

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
        }catch(InputMismatchException e){// Se ocorrer uma exceção de tipo InputMismatchException,
            e.printStackTrace();  // imprime o rastreamento da pilha para identificar o erro
        }finally{  // limpa o buffer do Scanner
            clearBuffer(entrada);
        }
    }
    // Este método permite aos usuários buscar um produto no sistema com base no ID fornecido.
    public static void buscarProduto() throws SQLException{ 

        Scanner entrada = new Scanner(System.in);

        try{ //procura o produto no banco de dados usando o serviço CalcadoServico.
            System.out.print(">>>Digite o ID do produto que deseja buscar: ");
            int idBusca = entrada.nextInt();

            Calcado calcadoEncontrado = CalcadoServico.getCalcadoPorId(idBusca);

            if(calcadoEncontrado != null){// se encontrado, suas informações são exibidas 
                System.out.println(">>>Produto encontrado: "+calcadoServico.mostrarCalcadoIndividual(calcadoEncontrado));
            }
            else{
                System.out.println(">>>Produto não encontrado.\n");
            }
        } catch (InputMismatchException e) {// Se ocorrer uma exceção de tipo InputMismatchException,
            e.printStackTrace();  // imprime o rastreamento da pilha para identificar o erro
        } finally{  // limpa o buffer do Scanner
            clearBuffer(entrada);
        }
    } 

    // CLEAR BUFFER ///////////////////////////////////////////////////////////////
    // Ele verifica se há uma nova linha pendente no buffer do Scanner e, se houver, consome essa linha em branco
    public static void clearBuffer(Scanner scanner){
        if(scanner.hasNextLine()){
            scanner.nextLine();
        }
    }
}
