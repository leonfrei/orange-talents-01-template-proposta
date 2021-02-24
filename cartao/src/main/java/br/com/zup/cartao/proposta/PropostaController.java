package br.com.zup.cartao.proposta;

import br.com.zup.cartao.clients.AnaliseClient;
import br.com.zup.cartao.validacao.CpfCnpjValidator;
import feign.FeignException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.HashMap;
import java.util.Optional;

@RestController
@RequestMapping("/api/propostas")
public class PropostaController {

    private final PropostaRepository propostaRepository;
    private final AnaliseClient analiseClient;
    private final CpfCnpjValidator cpfCnpjValidator;

    public PropostaController(PropostaRepository propostaRepository, AnaliseClient analiseClient, CpfCnpjValidator cpfCnpjValidator) {
        this.propostaRepository = propostaRepository;
        this.analiseClient = analiseClient;
        this.cpfCnpjValidator = cpfCnpjValidator;
    }

    @InitBinder
    private void initBinder(WebDataBinder binder) {
        binder.addValidators(cpfCnpjValidator);
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody @Valid NovaPropostaRequest novaPropostaRequest, UriComponentsBuilder uriBuilder) {
        if (propostaRepository.existsByDocumento(novaPropostaRequest.getDocumento())) {
            HashMap<String, Object> resposta = new HashMap<>();
            resposta.put("mensagem", "Já existe documento cadastrado");
            return ResponseEntity.unprocessableEntity().body(resposta);
        }
        Proposta proposta = novaPropostaRequest.toModel();
        propostaRepository.save(proposta);
        AnaliseClient.ConsultaStatusRequest consultaRequest = new AnaliseClient.ConsultaStatusRequest(proposta);
        try {
            AnaliseClient.ConsultaStatusResponse consultaResponse = analiseClient.consulta(consultaRequest);
            proposta.atualizaStatus(consultaResponse.getResultadoSolicitacao());
        } catch (FeignException.FeignClientException.UnprocessableEntity e) {
            System.out.println("Proposta com restrição no documento " + novaPropostaRequest.getDocumento());
            proposta.atualizaStatus("COM_RESTRICAO");
        }
        propostaRepository.save(proposta);
        URI location = uriBuilder.path("/api/propostas/{id}").buildAndExpand(proposta.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    //@GetMapping
    @GetMapping("/{id}")
//  public ResponseEntity<?> consultarProposta(@RequestParam("id") Long id){
    public ResponseEntity<?> consultarProposta(@PathVariable("id") Long id) {
        Optional<Proposta> proposta = propostaRepository.findById(id);
        if (proposta.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new PropostaRespose(proposta.get()));
    }
}
