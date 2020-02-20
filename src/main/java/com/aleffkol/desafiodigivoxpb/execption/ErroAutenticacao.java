package com.aleffkol.desafiodigivoxpb.execption;

public class ErroAutenticacao extends RuntimeException {
    public ErroAutenticacao(String msg){
        super(msg);
    }
}
