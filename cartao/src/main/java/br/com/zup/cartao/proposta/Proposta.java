package br.com.zup.cartao.proposta;

import br.com.zup.cartao.cartao.Cartao;
import br.com.zup.cartao.endereco.Endereco;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Entity
@Table(name = "propostas")
public class Proposta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String documento;
    @Email
    @NotBlank
    private String email;
    @NotBlank
    private String nome;
    @NotNull
    @Embedded
    private Endereco endereco;
    @NotNull
    @Positive
    private BigDecimal salario;
    @Enumerated(EnumType.STRING)
    private Status status;
    @JoinColumn(name = "cartao_id")
    @OneToOne(cascade = CascadeType.ALL)
    private Cartao cartao;

    @Deprecated
    public Proposta() {
    }

    public Proposta(@NotBlank String documento, @Email @NotBlank String email, @NotBlank String nome, @NotBlank Endereco endereco, @NotNull @Positive BigDecimal salario) {
        this.documento = documento;
        this.email = email;
        this.nome = nome;
        this.endereco = endereco;
        this.salario = salario;
    }

    public Long getId() {
        return id;
    }

    public String getDocumento() {
        return documento;
    }

    public String getNome() {
        return nome;
    }

    public Cartao getCartao() {
        return cartao;
    }

    public void atualizaStatus(String solicitacao) {
        this.status = Status.resultadoPara(solicitacao);
    }

    public void setCartao(Cartao cartao) {
        this.cartao = cartao;
    }

    public String getEmail() {
        return email;
    }

    public Status getStatus() {
        return status;
    }
}
