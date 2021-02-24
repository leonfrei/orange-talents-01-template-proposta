package br.com.zup.cartao.validacao;

public class ErroDeValidacaoDTO {

    private String campo;
    private String erro;

    public ErroDeValidacaoDTO(String campo, String erro) {
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
