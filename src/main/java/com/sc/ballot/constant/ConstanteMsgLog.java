package com.sc.ballot.constant;

public class ConstanteMsgLog {
    public static final String CADASTRO_SUCESSO= "O usuario de ID: %s realizou o cadastro da pauta de ID: %s";
    public static final String CADASTRO_NOME_EXISTENTE = "O usuario de ID: %s tentou cadastrar uma pauta de nome: %s , mas ela já está em andamento ou não inicializada";
    public static final String ERRO_LOG = "Erro causado pelo usuario: %s no método %s com os parametros: %s \n Exception: %s ";
    public static final String ERRO_LOG_WE = "Erro causado pelo usuario: %s no método %s com os parametros: %s";
    public static final String ERRO_FECHAR = "Um erro ocorreu no método %s com os parametros: %s \n Exception: %s ";
    public static final String ERRO_FECHAR_TO = "Um timeout ocorreu no serviço externo, no método %s com os parametros: %s \n Exception: %s ";
    public static final String ABRIR_PAUTA_ID_INEXISTENTE = "O usuario de ID: %s tentou abrir uma pauta não existente, com o ID: %s";
    public static final String PAUTA_ABERTA_SUCESSO = "O usuario de ID: %s abriu a pauta de ID: %s  com duração de %s segundos" ;
    public static final String PAUTA_FECHADA_SUCESSO = "A pauta de ID: %s foi encerrada com sucesso." ;
    public static final String VOTO_SUCESSO = "O usuario de ID: %s votou %s com sucesso na pauta de ID: %s" ;
    public static final String MSG_SUCESSO = "A mensagem %s foi entregue com sucesso ao Kafka";
    public static final String MSG_FAIL = "A mensagem %s não foi entregue ao Kafka \n Exception: %s ";
    public static final String CPF_NAO_AUTORIZADO ="O cpf: %s não está autorizado a votar no momento." ;
    public static final String VOTO_REPETIDO = "O usuario de ID: %s tentou votar mais de uma vez na pauta de ID: %s";
    public static final String VOTO_FORMATO_INVALIDO = "O usuario de ID: %s informou um tipo de voto invalido: %s";
    public static final String VOTO_NAO_ANDAMENTO ="O usuario de ID: %s tentou votar na pauta de ID: %s que não está em andamento";
}
