package br.com.zup.cartao.carteira;

public enum TipoCarteira {
    PAYPAL, SAMSUNGPAY;

    public static TipoCarteira resultadoPara(String solicitacao) {
        if (solicitacao.equals("PAYPAL")){
            return PAYPAL;
        }
        return SAMSUNGPAY;
    }
}
