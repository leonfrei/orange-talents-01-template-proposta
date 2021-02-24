package br.com.zup.cartao.biometria;

import br.com.zup.cartao.cartao.Cartao;

import javax.validation.constraints.NotBlank;

public class NovaBiometriaRequest {

    @NotBlank
    private String fingerprint;

    public String getFingerprint() {
        return fingerprint;
    }

    public Biometria toModel(Cartao cartao) {
        return new Biometria(fingerprint, cartao);
    }
}
