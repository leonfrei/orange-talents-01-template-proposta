package br.com.zup.cartao.carteira;

import br.com.zup.cartao.cartao.Cartao;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class Carteira {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String idSistemaLegado;
    @NotBlank
    @Email
    private String email;
    @Enumerated(EnumType.STRING)
    private TipoCarteira tipoCarteira;
    @ManyToMany
    @NotNull
    private Cartao cartao;

    public Carteira() {
    }

    public Carteira(@NotNull String idSistemaLegado, @NotBlank @Email String email, TipoCarteira tipoCarteira, @NotNull Cartao cartao) {
        this.idSistemaLegado = idSistemaLegado;
        this.email = email;
        this.tipoCarteira = tipoCarteira;
        this.cartao = cartao;
    }

    public Long getId() {
        return id;
    }

    public String getIdSistemaLegado() {
        return idSistemaLegado;
    }

    public String getEmail() {
        return email;
    }

    public TipoCarteira getTipoCarteira() {
        return tipoCarteira;
    }

    public Cartao getCartao() {
        return cartao;
    }
}
