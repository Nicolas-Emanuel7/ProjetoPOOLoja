package Servicos;

import java.sql.*;
import java.util.*;

import DAO.*;
import Entidades.*;

public class ClienteServico {

    public void cadastrarCliente(String nomeCliente, String email, String endereco) throws SQLException, InputMismatchException{

        try {
            Cliente novoCliente = new Cliente(nomeCliente, email, endereco);
            System.out.println("Cadastro bem sucedido.\n");
            ClienteDao.inserirCliente(novoCliente);
        } catch (InputMismatchException e) {
            e.printStackTrace();
        }
        System.out.println(mostrarClientes()); 
        
    }

    public Cliente loginCliente(String emailCliente) throws SQLException{
        try{
            if(ClienteDao.listarTodosClientesDao().isEmpty()){
                System.out.println("Não há usuários cadastrados.");
                return null;
            }else{
                Cliente clienteLogin = ClienteDao.buscarClientePorEmail(emailCliente);
                if(clienteLogin != null){
                    return clienteLogin;
                } else{
                    return null;
                }
            }
        } catch(InputMismatchException e){
            e.printStackTrace();
        }
        return null;
    }

    public Boolean verfificarEmail(String emailCliente) throws InputMismatchException, SQLException{

        if(ClienteDao.verificarEmailDao(emailCliente) == true){ 
            return true;
        } else{
            return false;
        }
    }

    public List<Cliente> getTodosClientes() throws InputMismatchException, SQLException{
        return ClienteDao.listarTodosClientesDao();
    }
     
    public Cliente getClientePorId(int idBusca) throws InputMismatchException, SQLException{
        
        Cliente clienteSolicitado = ClienteDao.buscarClientePorId(idBusca);

        if(clienteSolicitado != null){
            return clienteSolicitado;
        } else{
            return null;
        }
    }
     
    public void atualizarCliente(Cliente cliente) throws SQLException {
        try{
            if(cliente != null){
                ClienteDao.atualizarCliente(cliente);
                System.out.println("Dados atualizados com sucesso.");
                System.out.println(cliente.toString());
            } else{
                System.out.println("Cliente não encontrado.");
            }
        } catch(InputMismatchException e){
            e.printStackTrace();
        }
    }

    public void apagarConta(int idCliente) throws SQLException{
        if(idCliente == 0){
            System.out.println("Operação cancelada.");
        } else{
            ClienteDao.excluirCliente(idCliente);
            System.out.println("Conta deletada com sucesso.");
        }
    }
    
    public String mostrarClientes() throws InputMismatchException, SQLException{ 
        String clientesLista = "";
        for(Cliente cliente : ClienteDao.listarTodosClientesDao()){
            clientesLista += "ID: "+cliente.getIdCliente()+"\tNome: "+cliente.getNome()+"\tEmail: "+cliente.getEmail()+"\tEndereço: "+cliente.getEndereco()+"\n";
        }
        return clientesLista;
    }
}