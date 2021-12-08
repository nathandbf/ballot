package com.sc.ballot.constant;

public class Constante {

    public static final String VOTO_OPCAO1 = "SIM";
    public static final Integer VOTO_OPCAO1_INT = 1;
    public static final String VOTO_OPCAO2 = "NÃO";
    public static final Integer VOTO_OPCAO2_INT = 2;

    public static final String PAUTA_200_CADASTRO = "PAUTA INSERIDA COM SUCESSO!";
    public static final String PAUTA_200_ABERTURA = "PAUTA ABERTA COM SUCESSO!";
    public static final String PAUTA_200_VOTO = "VOTO REGISTRADO COM SUCESSO!";
    public static final String PAUTA_200_BUSCA_FINALIZADA = "VOTOS PESQUISADOS COM SUCESSO!";
    public static final String PAUTA_200_BUSCA_NAO_FINALIZADA = "VOTOS PESQUISADOS COM SUCESSO! PORÉM A PAUTA CONTINUA EM ABERTO";


    public static final String ERROR_400 = "BAD REQUEST ";
    public static final String ERROR_400_COMP_NOME_NULL = " - NOME NÃO PODE SER NULL";
    public static final String ERROR_400_COMP_ABERTURA_PAUTA = " - NENHUMA PAUTA NÃO INICIALIZADA FOI ENCONTRADA PARA ESTE ID";
    public static final String ERROR_400_COMP_VOTAR = " - O VOTO NÃO ESTÁ EM UM FORMATO VÁLIDO";
    public static final String ERROR_400_COMP_PAUTA_NAO_INICIALIZADA = " - A PAUTA SOLICITADA, AINDA NÃO ESTÁ ABERTA.";
    public static final String ERROR_400_COMP_PAUTA_ENCERRADA = " - A PAUTA SOLICITADA, JÁ ENCERROU SUA VOTAÇÃO.";

    public static final String ERROR_403 = "FORBIDDEN ";
    public static final String ERROR_403_PAUTA = " - UMA PAUTA COM ESSE NOME ENCONTRA-SE EM ANDAMENTO OU NÃO INICIALIZADA";
    public static final String ERROR_403_REPETIDO = " - SEU CPF JÁ VOTOU NESTA PAUTA";
    public static final String ERROR_403_VOTO = " - SEU CPF NÃO ESTÁ AUTORIZADO A VOTAR NO MOMENTO";

    public static final String ERROR_404 = "NOT FOUND";
    public static final String ERROR_404_COMP_PAUTA_NAO_EXISTE = " - NENHUMA PAUTA FOI ENCONTRADA PARA ESTE ID";

    public static final String ERROR_500 = "INTERNAL SERVER ERROR";

    public static final String UNABLE_TO_VOTE = "UNABLE_TO_VOTE";

    public static final String STATUS_FINALIZADA = "FINALIZADO";
    public static final String STATUS_ANDAMENTO = "ANDAMENTO";

    public static final String VOTO_EMPATE = "EMPATE";
    public static final String INDEFINIDO = "INDEFINIDO";
}
