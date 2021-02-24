package br.com.zup.cartao.carteira;

public class NovaCarteiraResponse {

    private String resultado;

    private String id;

    public NovaCarteiraResponse(String resultado, String id) {
        this.resultado = resultado;
        this.id = id;
    }

    public String getResultado() {
        return resultado;
    }

    public String getId() {
        return id;
    }
}
