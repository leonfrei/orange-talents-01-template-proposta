package br.com.zup.cartao.bloqueio;

import br.com.zup.cartao.cartao.Cartao;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
public class Bloqueio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate bloqueadoEm;
    @NotNull
    private String ip;
    @NotNull
    private String userAgent;
    @NotNull
    private Boolean status;
    @OneToOne
    private Cartao cartao;

    @Deprecated
    public Bloqueio() {

    }

    public Bloqueio(@NotNull String ip, @NotNull String userAgent, Cartao cartao) {
        this.bloqueadoEm = LocalDate.now();
        this.ip = ip;
        this.userAgent = userAgent;
        this.status = false;
        this.cartao = cartao;
    }

    public LocalDate getBloqueadoEm() {
        return bloqueadoEm;
    }

    public String getIp() {
        return ip;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public Boolean getStatus() {
        return status;
    }

    public Cartao getCartao() {
        return cartao;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
