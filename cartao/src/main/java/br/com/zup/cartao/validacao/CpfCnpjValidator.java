package br.com.zup.cartao.validacao;

import br.com.zup.cartao.proposta.NovaPropostaRequest;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class CpfCnpjValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return NovaPropostaRequest.class.equals(clazz);
    }

    @Override
    public void validate(Object object, Errors errors) {
        final NovaPropostaRequest request = (NovaPropostaRequest) object;
        if (!request.documentoValido()) {
            errors.rejectValue("documento", "Documento inválido");
        }
    }
}
