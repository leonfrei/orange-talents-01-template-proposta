package br.com.zup.cartao.endereco;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;

@Embeddable
public class Endereco {

    @NotBlank
    private String logradouro;
    @NotBlank
    private String cep;
    @NotBlank
    private String numero;
    @NotBlank
    private String complemento;

    @Deprecated
    public Endereco() {
    }

    public Endereco(@NotBlank String logradouro, @NotBlank String cep, @NotBlank String numero, @NotBlank String complemento) {
        this.logradouro = logradouro;
        this.cep = cep;
        this.numero = numero;
        this.complemento = complemento;
    }
}
