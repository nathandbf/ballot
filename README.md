INFORMAÇÕES PRÉ EXECUÇÃO
Pré requisitos:
Postgre
Kafka e zookeeper (versões utilizadas kafka_2.12-2.8.1 e apache-zookeeper-3.7.0-bin)

Para o SQL do banco de dados utilizar o script:
ScriptBallot.sql

Para a criação do topico do kafka utilizar o comando:
kafka-topics.bat -create -zookeeper localhost:2181 -replication-factor 1 -partitions 1 -topic BALLOT_MSG

Caso o Kafka não esteja sendo utilizado no localhost, alterar o linha 8 do arquivo ConstanteConfig:
public static final String URL_KAFKA ="localhost:9092";

No arquivo application.properties é necessario alterar a senha do postgres.
spring.datasource.password= *******



