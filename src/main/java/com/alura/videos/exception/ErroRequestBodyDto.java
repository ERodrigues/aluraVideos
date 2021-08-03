package com.alura.videos.exception;

public class ErroRequestBodyDto {
    private String campo;
    private String erro;

    public ErroRequestBodyDto(String campo, String erro) {
        this.campo = campo;
        this.erro = erro;
    }

    public String getCampo() {
        return campo;
    }

    public String getErro() {
        return erro;
    }
}
