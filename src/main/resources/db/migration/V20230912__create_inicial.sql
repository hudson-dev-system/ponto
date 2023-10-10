CREATE TABLE empresa(
    id SERIAL NOT NULL,
    cnpj VARCHAR(255) NOT NULL,
    razao_social VARCHAR(255) NOT NULL,
    data_cadastro TIMESTAMP NOT NULL,
    data_atualizacao TIMESTAMP NOT NULL,
    PRIMARY KEY (id)
);
CREATE TABLE funcionario(
    id SERIAL NOT NULL,
    empresa_id INT NOT NULL,
    cpf VARCHAR(255) NOT NULL,
    data_cadastro TIMESTAMP NOT NULL,
    data_atualizacao TIMESTAMP NOT NULL,
    email VARCHAR(255) NOT NULL,
    nome VARCHAR(255) NOT NULL,
    perfil VARCHAR(255) NOT NULL,
    qtd_horas_almoco FLOAT DEFAULT NULL,
    qtd_horas_trabalhadas_dia FLOAT DEFAULT NULL,
    senha VARCHAR(255) NOT NULL,
    valor_hora DECIMAL(19,2) DEFAULT NULL,
    FOREIGN KEY (empresa_id) REFERENCES empresa(id),
    PRIMARY KEY (id)
);
CREATE TABLE lancamentos(
    id SERIAL NOT NULL,
    funcionario_id INT NOT NULL,
    descricao VARCHAR(255) DEFAULT NULL, 
    localizacao VARCHAR(255) DEFAULT NULL,
    tipo VARCHAR(255) NOT NULL,
    data TIMESTAMP NOT NULL,
    data_cadastro TIMESTAMP NOT NULL,
    data_atualizacao TIMESTAMP NOT NULL,
    FOREIGN KEY(funcionario_id) REFERENCES funcionario(id),
    PRIMARY KEY(id)
);