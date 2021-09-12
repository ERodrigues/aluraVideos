package com.alura.videos.exception;

public class CategoriaInexistenteException extends NullPointerException{
    public CategoriaInexistenteException(String mensagem){
        super(mensagem);
    }
}
