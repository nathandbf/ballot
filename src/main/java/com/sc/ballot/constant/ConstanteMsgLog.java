package com.sc.ballot.constant;

public class ConstanteMsgLog {
    public static final String CADASTRO_SUCESSO= "O usuario de ID: %s realizou o cadastro da pauta de ID: %s";
    public static final String CADASTRO_NOME_EXISTENTE = "O usuario de ID: %s tentou cadastrar uma pauta de nome: %s , mas ela já está em andamento ou não inicializada";
    public static final String ERRO_LOG = "Erro causado pelo usuario: %s no método %s com os parametros: %s \n Exception: %s ";
    public static final String ERRO_LOG_WE = "Erro causado pelo usuario: %s no método %s com os parametros: %s";
    public static final String ERRO_FECHAR = "Um erro ocorreu no método %s com os parametros: %s \n Exception: %s ";
    public static final String ERRO_FECHAR_TO = "Um timeout ocorreu no serviço externo, no método %s com os parametros: %s \n Exception: %s ";
    public static final String ABRIR_PAUTA_ID_INEXISTENTE = "O usuario de ID: %s tentou abrir uma pauta não existe ou que não está como NÃO_INICIALIZADA, com o ID: %s";
    public static final String PAUTA_ABERTA_SUCESSO = "O usuario de ID: %s abriu a pauta de ID: %s  com duração de %s segundos" ;
    public static final String PAUTA_FECHADA_SUCESSO = "A pauta de ID: %s foi encerrada com sucesso." ;
    public static final String VOTO_SUCESSO = "O usuario de ID: %s votou %s com sucesso na pauta de ID: %s" ;
    public static final String MSG_SUCESSO = "A mensagem %s foi entregue com sucesso ao Kafka";
    public static final String MSG_FAIL = "A mensagem %s não foi entregue ao Kafka \n Exception: %s ";
    public static final String CPF_NAO_AUTORIZADO ="O cpf: %s não está autorizado a votar no momento." ;
    public static final String VOTO_REPETIDO = "O usuario de ID: %s tentou votar mais de uma vez na pauta de ID: %s";
    public static final String VOTO_FORMATO_INVALIDO = "O usuario de ID: %s informou um tipo de voto invalido: %s";
    public static final String VOTO_NAO_ANDAMENTO ="O usuario de ID: %s tentou votar na pauta de ID: %s que não está em andamento";
    public static final String CONSULTA_VOTO_NAO_INICIADO = "O usuario de ID: %s tentou consultar os votos da pauta de ID: %s porém ela não foi aberta ainda";
    public static final String ERRO_LOG_JSON = "Um problema ocorreu no método %s ao gerar o JSON da pauta de ID: %s \n Exception: %s ";
    public static final String ERRO_REST = "Um problema ocorreu ao se conectar com o serviço externo: %s com o parametro: %s \n Exception: %s" ;
    public static final String INICIO_FLUXO = "O usuario de ID: %s iniciou o fluxo: %s com os parametros %s";
    public static final String ERRO_USUARIO_NAO_CADASTRADO = "O usuario de ID: %s não possui um usuario cadastrado no sistema \n Exception: %s";
    public static final String CONSULTAR_PAUTA_ID_NAO_EXISTE = "O usuario de ID: %s tentou consultar o resultado de uma pauta que com o ID: %s";
    public static final String ERRO_VIOLACAO_BANCO = "O usuario de ID: %s informou os parametros: %s e isso causou uma violação no banco. \n Exception: %s";

}
