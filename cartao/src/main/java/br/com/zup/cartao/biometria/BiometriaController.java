package br.com.zup.cartao.biometria;

import br.com.zup.cartao.cartao.Cartao;
import br.com.zup.cartao.cartao.CartaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
public class BiometriaController {

    @Autowired
    private CartaoRepository cartaoRepository;
    @Autowired
    private BiometriaRepository biometriaRepository;

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(new BiometriaFingerPrintValidador());
    }

    @PostMapping("cartao/{idCartao}/biometria")
    public ResponseEntity<?> criar(@PathVariable("idCartao") Long idCartao, @RequestBody @Valid NovaBiometriaRequest novaBiometriaRequest, UriComponentsBuilder uriBuilder) {
        Optional<Cartao> possivelCartao = cartaoRepository.findById(idCartao);
        if (possivelCartao.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Biometria biometria = novaBiometriaRequest.toModel(possivelCartao.get());
        biometriaRepository.save(biometria);
        URI location = uriBuilder.path("cartao/{idCartao}/biometria/{id}").buildAndExpand(idCartao, biometria.getId()).toUri();
        return ResponseEntity.created(location).build();
    }
}
