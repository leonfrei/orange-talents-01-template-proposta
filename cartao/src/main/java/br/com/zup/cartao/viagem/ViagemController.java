package br.com.zup.cartao.viagem;

import br.com.zup.cartao.cartao.Cartao;
import br.com.zup.cartao.clients.CartaoClient;
import br.com.zup.cartao.cartao.CartaoRepository;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;

@RestController("/viagem")
public class ViagemController {

    @Autowired
    private CartaoClient cartaoClient;
    @Autowired
    private CartaoRepository cartaoRepository;
    @Autowired
    private ViagemRepository viagemRepository;

    @PostMapping("/{id}")
    public ResponseEntity<?> cadastrar(@RequestParam("idCartao") Long idCartao, @RequestBody @Valid NovaViagemRequest novaViagemRequest, HttpServletRequest httpServletRequest) {
        Optional<Cartao> possivelCartao = cartaoRepository.findById(idCartao);
        if (possivelCartao.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Viagem viagem = novaViagemRequest.toModel(httpServletRequest, possivelCartao.get());
        try {
            String aviso = cartaoClient.avisoViagem(possivelCartao.get().getNumeroCartao(), novaViagemRequest);
            if (aviso.equals("CRIADO")) viagemRepository.save(viagem);
        } catch (FeignException.FeignClientException feignClientException) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }
}
