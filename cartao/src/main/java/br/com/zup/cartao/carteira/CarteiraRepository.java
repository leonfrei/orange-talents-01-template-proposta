package br.com.zup.cartao.carteira;

import br.com.zup.cartao.cartao.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarteiraRepository extends JpaRepository<Carteira, Long> {

    Optional<Carteira> findByCartaoAndTipoCarteira(Cartao cartao, String tipoCarteira);
}
