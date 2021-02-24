package br.com.zup.cartao.bloqueio;

import javax.validation.constraints.NotBlank;

public class NovoBloqueioRequest {

    @NotBlank
    private String sistemaResponsavel;

    @Deprecated
    public NovoBloqueioRequest() {
    }

    public NovoBloqueioRequest(@NotBlank String sistemaResponsavel) {
        this.sistemaResponsavel = sistemaResponsavel;
    }

    public String getSistemaResponsavel() {
        return sistemaResponsavel;
    }
}
