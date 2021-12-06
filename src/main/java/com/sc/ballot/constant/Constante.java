package com.sc.ballot.constant;

public class Constante {
    public static final Integer TEMPO_INICIAL_PADRAO_PAUTA = 0;
    public static final Integer TEMPO_PADRAO_PAUTA = 60;
    public static final Integer MAX_THREAD = 8;


    public static final String PAUTA_200_CADASTRO = "PAUTA INSERIDA COM SUCESSO!";
    public static final String PAUTA_200_ABERTURA = "PAUTA ABERTA COM SUCESSO!";

    public static final String ERROR_400 = "BAD REQUEST ";
    public static final String ERROR_400_COMP_ABERTURA_PAUTA = " - NENHUMA PAUTA NÃO INICIALIZADA FOI ENCONTRADA PARA ESTE ID";

    public static final String ERROR_403 = "FORBIDDEN ";
    public static final String ERROR_403_PAUTA = " - UMA PAUTA COM ESSE NOME ENCONTRA-SE EM ANDAMENTO OU NÃO INICIALIZADA";
    public static final String ERROR_500 = "INTERNAL SERVER ERROR";

}
