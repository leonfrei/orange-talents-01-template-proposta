package br.com.zup.cartao.clients;

import br.com.zup.cartao.proposta.Proposta;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "analiseCliente", url = "${analise.url}")
public interface AnaliseClient {

    @PostMapping("/solicitacao")
    ConsultaStatusResponse consulta(@RequestBody ConsultaStatusRequest consultaStatusRequest);

    class ConsultaStatusRequest{
        private String documento;
        private String nome;
        private Long idProposta;

        public ConsultaStatusRequest(Proposta proposta) {
            this.documento = proposta.getDocumento();
            this.nome = proposta.getNome();
            this.idProposta = proposta.getId();
        }

        public String getDocumento() {
            return documento;
        }

        public String getNome() {
            return nome;
        }

        public Long getIdProposta() {
            return idProposta;
        }
    }

    class ConsultaStatusResponse {

        private String resultadoSolicitacao;
        private String documento;
        private String nome;
        private Long idProposta;

        public String getResultadoSolicitacao() {
            return resultadoSolicitacao;
        }

        public String getDocumento() {
            return documento;
        }

        public String getNome() {
            return nome;
        }

        public Long getIdProposta() {
            return idProposta;
        }
    }
}
