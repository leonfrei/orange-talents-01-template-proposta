package br.com.zup.cartao.proposta;

import br.com.zup.cartao.endereco.Endereco;
import br.com.zup.cartao.endereco.EnderecoRequest;
import org.hibernate.validator.internal.constraintvalidators.hv.br.CNPJValidator;
import org.hibernate.validator.internal.constraintvalidators.hv.br.CPFValidator;
import org.springframework.util.Assert;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class NovaPropostaRequest {

    @NotBlank
    private String documento;
    @Email
    @NotBlank
    private String email;
    @NotBlank
    private String nome;
    @NotNull
    private EnderecoRequest endereco;
    @NotNull
    @Positive
    private BigDecimal salario;

    public String getEmail() {
        return email;
    }

    public String getNome() {
        return nome;
    }

    public EnderecoRequest getEndereco() {
        return endereco;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public String getDocumento() {
        return documento;
    }

    public Proposta toModel() {
        return new Proposta(documento, email, nome, new Endereco(endereco.getLogradouro(), endereco.getCep(), endereco.getNumero(), endereco.getComplemento()), salario);
    }

    public boolean documentoValido() {
        Assert.hasLength(documento, "você não deveria validar o documento se ele nao estiver preechido");
        CPFValidator cpfValidator = new CPFValidator();
        cpfValidator.initialize(null);

        CNPJValidator cnpjValidator = new CNPJValidator();
        cnpjValidator.initialize(null);

        return cpfValidator.isValid(documento, null) || cnpjValidator.isValid(documento, null);
    }
}
