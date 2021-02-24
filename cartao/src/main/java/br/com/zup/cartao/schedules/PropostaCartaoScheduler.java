package br.com.zup.cartao.schedules;

import br.com.zup.cartao.cartao.Cartao;
import br.com.zup.cartao.clients.CartaoClient;
import br.com.zup.cartao.proposta.Proposta;
import br.com.zup.cartao.proposta.PropostaRepository;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Component
public class PropostaCartaoScheduler {

    @Autowired
    private CartaoClient cartaoClient;
    @Autowired
    private PropostaRepository propostaRepository;

    @Transactional
    @Scheduled(fixedDelay = 60000)
    public void checarProposta(){
        List<Proposta> propostasEmAberto = propostaRepository.findAllWhereCartaoIdIsNull();
        while(!propostasEmAberto.isEmpty()) {
            for (Proposta proposta : propostasEmAberto) {
                try {
                    CartaoClient.ConsultaPropostaResponse consultaPropostaResponse = cartaoClient.checarPropostaPeloId(proposta.getId().toString());
                    Cartao cartao = consultaPropostaResponse.toModel();
                    proposta.setCartao(cartao);
                    propostaRepository.save(proposta);
                } catch (FeignException.FeignClientException.InternalServerError e) {
                    System.out.println("Proposta ainda sem cartao para associar" + e);
                }
            }
            propostasEmAberto = propostaRepository.findAllWhereCartaoIdIsNull();
        }
    }
}