package br.com.zup.cartao.biometria;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Base64;

public class BiometriaFingerPrintValidador implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return NovaBiometriaRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        NovaBiometriaRequest novaBiometriaRequest = (NovaBiometriaRequest) target;
        Base64.Decoder decoder = Base64.getDecoder();
        try {
            decoder.decode(novaBiometriaRequest.getFingerprint());
        } catch(IllegalArgumentException iae) {
            errors.rejectValue("fingerprint", null, "Fingerprint não foi passada em Base64");
        }
    }
}
