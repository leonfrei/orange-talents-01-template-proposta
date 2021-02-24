package br.com.zup.cartao.biometria;

import br.com.zup.cartao.cartao.Cartao;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class Biometria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String fingerprint;
    @NotNull
    @ManyToOne
    private Cartao cartao;

    public Biometria() {
    }

    public Biometria(@NotBlank String fingerprint, @NotNull Cartao cartao) {
        this.fingerprint = fingerprint;
        this.cartao = cartao;
    }

    public Long getId() {
        return id;
    }

    public String getFingerprint() {
        return fingerprint;
    }

    public Cartao getCartao() {
        return cartao;
    }
}
