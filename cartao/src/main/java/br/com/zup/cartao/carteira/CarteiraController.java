package br.com.zup.cartao.carteira;

import br.com.zup.cartao.cartao.Cartao;
import br.com.zup.cartao.clients.CartaoClient;
import br.com.zup.cartao.cartao.CartaoRepository;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController("/carteiras")
public class CarteiraController {

    @Autowired
    private CartaoRepository cartaoRepository;
    @Autowired
    private CartaoClient cartaoClient;
    @Autowired
    private CarteiraRepository carteiraRepository;

    @PostMapping("/{id}")
    public ResponseEntity<?> cadastrar(@PathVariable("idCartao") Long idCartao, @RequestBody @Valid NovaCarteiraRequest novaCarteiraRequest, UriComponentsBuilder uriComponentsBuilder) {
        Carteira carteira;
        Optional<Cartao> possivelCartao = cartaoRepository.findById(idCartao);
        if (possivelCartao.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Optional<Carteira> possivelCarteira = carteiraRepository.findByCartaoAndTipoCarteira(possivelCartao.get(), novaCarteiraRequest.getTipoCarteira());
        if (possivelCarteira.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }
        try {
            NovaCarteiraResponse novaCarteiraResponse = cartaoClient.cadastraCarteira(possivelCartao.get().getNumeroCartao(), novaCarteiraRequest);
            carteira = novaCarteiraRequest.toModel(novaCarteiraResponse.getId(), possivelCartao.get());
            carteiraRepository.save(carteira);
        } catch (FeignException.FeignClientException feignClientException) {
            return ResponseEntity.badRequest().build();
        }
        URI location = uriComponentsBuilder.path("/carteira/{id}").buildAndExpand(carteira.getId()).toUri();
        return ResponseEntity.created(location).build();
    }
}
