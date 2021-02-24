package br.com.zup.cartao.proposta;

public enum Status {
    NAO_ELEGIVEL, ELEGIVEL;

    public static Status resultadoPara(String solicitacao) {
        if (solicitacao.equals("COM_RESTRICAO")){
            return NAO_ELEGIVEL;
        }
        return ELEGIVEL;
    }
}
