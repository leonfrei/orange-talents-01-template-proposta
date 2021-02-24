package br.com.zup.cartao.viagem;

import br.com.zup.cartao.cartao.Cartao;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
public class Viagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String destino;
    @NotNull
    @Future
    private LocalDate validoAte;
    private LocalDate instanteAviso;
    @NotNull
    private String ip;
    @NotNull
    private String userAgent;
    @ManyToOne
    @NotNull
    private Cartao cartao;

    @Deprecated
    public Viagem() {
    }

    public Viagem(@NotBlank String destino, @NotNull @Future LocalDate validoAte, @NotNull String ip, @NotNull String userAgent, @NotNull Cartao cartao) {
        this.destino = destino;
        this.validoAte = validoAte;
        this.instanteAviso = LocalDate.now();
        this.ip = ip;
        this.userAgent = userAgent;
        this.cartao = cartao;
    }

    public String getDestino() {
        return destino;
    }

    public LocalDate getValidoAte() {
        return validoAte;
    }

    public LocalDate getInstanteAviso() {
        return instanteAviso;
    }

    public String getIp() {
        return ip;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public Cartao getCartao() {
        return cartao;
    }
}
