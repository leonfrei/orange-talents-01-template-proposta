package br.com.zup.cartao.clients;


import br.com.zup.cartao.bloqueio.NovoBloqueioRequest;
import br.com.zup.cartao.carteira.NovaCarteiraRequest;
import br.com.zup.cartao.carteira.NovaCarteiraResponse;
import br.com.zup.cartao.cartao.Cartao;
import br.com.zup.cartao.viagem.NovaViagemRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "cartaoClient", url = "${cartao.url}")
public interface CartaoClient {

    @GetMapping("/cartoes")
    ConsultaPropostaResponse checarPropostaPeloId(@RequestParam("idProposta") String idProposta);

    @PostMapping(value = "/cartoes/{id}/bloqueios")
    String bloqueiaCartao(@PathVariable String id, NovoBloqueioRequest request);

    @PostMapping(value = "/cartoes/{id}/avisos")
    String avisoViagem(@PathVariable String id, NovaViagemRequest novaViagemRequest);

    @PostMapping(value = "/cartoes/{id}/carteiras")
    NovaCarteiraResponse cadastraCarteira(@PathVariable String id, NovaCarteiraRequest request);

    class ConsultaPropostaResponse {

        private String id;

        public String getId() {
            return id;
        }

        public Cartao toModel() {
            return new Cartao(id);
        }
    }
}
