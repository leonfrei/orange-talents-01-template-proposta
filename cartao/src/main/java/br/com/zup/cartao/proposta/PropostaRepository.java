package br.com.zup.cartao.proposta;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PropostaRepository extends JpaRepository<Proposta, Long> {

    boolean existsByDocumento(String documento);

    @Query(value = "SELECT * FROM propostas WHERE propostas.cartao_id IS NULL AND propostas.status = 'ELEGIVEL' LIMIT 100 FOR UPDATE SKIP LOCKED", nativeQuery = true)
    List<Proposta> findAllWhereCartaoIdIsNull();

}
