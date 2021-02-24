package br.com.zup.cartao.bloqueio;

import br.com.zup.cartao.cartao.Cartao;
import br.com.zup.cartao.clients.CartaoClient;
import br.com.zup.cartao.cartao.CartaoRepository;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController("/bloqueio")
public class BloqueiCartaoController {

    @Autowired
    private CartaoRepository cartaoRepository;
    @Autowired
    private BloqueioRepository bloqueioRepository;
    @Autowired
    private CartaoClient cartaoClient;

    @PostMapping("/{idCartao}")
    public ResponseEntity<?> bloquear(@RequestParam("idCartao") Long idCartao, HttpServletRequest httpServletRequest) {
        Optional<Cartao> possivelCartao = cartaoRepository.findById(idCartao);
        if (possivelCartao.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Optional<Bloqueio> possivelBloqueio = bloqueioRepository.findByCartaoAndStatus(possivelCartao.get(), true);
        if (possivelBloqueio.isPresent()) {
            return ResponseEntity.unprocessableEntity().body("O cartão informado já se encontra como bloqueado no sistema");
        }
        Bloqueio bloqueio = new Bloqueio(httpServletRequest.getRemoteAddr(), httpServletRequest.getHeader("User-Agent"), possivelCartao.get());
        try {
            String statusBloqueio = cartaoClient.bloqueiaCartao(possivelCartao.get().getNumeroCartao(), new NovoBloqueioRequest("Sistema de propostas"));
            verificaESetaStatus(statusBloqueio, bloqueio);
        } catch (FeignException.UnprocessableEntity e) {
            bloqueioRepository.save(bloqueio);
            return ResponseEntity.badRequest().build();
        }
        bloqueioRepository.save(bloqueio);
        return ResponseEntity.ok().build();
    }

    private void verificaESetaStatus(String status, Bloqueio bloqueio) {
        if (status.equals("BLOQUEADO")) {
            bloqueio.setStatus(true);
        }
    }
}
