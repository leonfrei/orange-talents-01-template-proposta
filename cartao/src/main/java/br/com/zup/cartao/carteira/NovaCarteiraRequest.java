package br.com.zup.cartao.carteira;

import br.com.zup.cartao.cartao.Cartao;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class NovaCarteiraRequest {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String tipoCarteira;

    public NovaCarteiraRequest(@NotBlank @Email String email, @NotBlank String tipoCarteira) {
        this.email = email;
        this.tipoCarteira = tipoCarteira;
    }

    public String getEmail() {
        return email;
    }

    public String getTipoCarteira() {
        return tipoCarteira;
    }

    public Carteira toModel(String idSistemaLegado, Cartao cartao) {
        return new Carteira(idSistemaLegado, email, TipoCarteira.resultadoPara(tipoCarteira), cartao);
    }
}
