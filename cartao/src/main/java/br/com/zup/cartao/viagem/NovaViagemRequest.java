package br.com.zup.cartao.viagem;

import br.com.zup.cartao.cartao.Cartao;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class NovaViagemRequest {

    @NotBlank
    private String destino;
    @NotNull
    @Future
    private LocalDate validoAte;

    @Deprecated
    public NovaViagemRequest() {
    }

    public String getDestino() {
        return destino;
    }

    public LocalDate getValidoAte() {
        return validoAte;
    }

    public Viagem toModel(HttpServletRequest httpServletRequest, Cartao cartao) {
        return new Viagem(destino, validoAte, httpServletRequest.getRemoteAddr(), httpServletRequest.getHeader("User-Agent"), cartao);
    }
}
