package br.com.zup.cartao.proposta;

public class PropostaRespose {

    private Long idProposta;
    private String nome;
    private String email;
    private String status;

    @Deprecated
    public PropostaRespose() {
    }

    public PropostaRespose(Proposta proposta) {
        this.idProposta = proposta.getId();
        this.nome = proposta.getNome();
        this.email = proposta.getEmail();
        this.status = proposta.getStatus().name();
    }

    public Long getIdProposta() {
        return idProposta;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getStatus() {
        return status;
    }
}
