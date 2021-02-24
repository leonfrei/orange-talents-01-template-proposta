package br.com.zup.cartao.bloqueio;

import br.com.zup.cartao.cartao.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BloqueioRepository extends JpaRepository<Bloqueio, Long> {

    Optional<Bloqueio> findByCartaoAndStatus(Cartao possivelCartao, Boolean status);
}
