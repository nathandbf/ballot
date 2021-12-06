--DROP TABLE TB_VOTO;
--DROP TABLE TB_ASSOCIADO;
--DROP TABLE TB_PAUTA;

CREATE TABLE TB_ASSOCIADO
(CPF varchar (11) PRIMARY KEY,
NOME varchar(255));


CREATE TABLE TB_PAUTA
(ID integer PRIMARY KEY,
NOME varchar(255),
STATUS integer,
DURACAO_SEGUNDOS integer
);

CREATE TABLE TB_VOTO
(ID integer PRIMARY KEY,
CPF_ASSOCIADO varchar (12),
ID_PAUTA integer,
VOTO integer,
CONSTRAINT FK_ASSOCIADO
   FOREIGN KEY(CPF_ASSOCIADO) 
      REFERENCES TB_ASSOCIADO(CPF),

);

INSERT INTO TB_ASSOCIADO (CPF, NOME)
    VALUES ('03086012030', 'Nathan Flores');
INSERT INTO TB_ASSOCIADO (CPF, NOME)
    VALUES ('53313572022', 'Dummy 002');
INSERT INTO TB_ASSOCIADO (CPF, NOME)
    VALUES ('93868300007', 'Dummy 003');
INSERT INTO TB_ASSOCIADO (CPF, NOME)
    VALUES ('19839091069', 'Dummy');	
	
