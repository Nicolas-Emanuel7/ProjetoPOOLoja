package Excecoes;
// A classe DadosInvalidosException herda a classe Exception
public class DadosInvalidosException extends Exception{ // Exception é uma classe padrão em Java que representa uma exceção genérica.
    public DadosInvalidosException(String mensagem){ // construtor 
        super(mensagem); // mensagem de erro que é associada com a exceção.
    }
}
