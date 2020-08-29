package com.example.curso.exception;

public class ErroAutenticacao extends RuntimeException{

    public ErroAutenticacao(String mensagem){
        super(mensagem);
    }
}
