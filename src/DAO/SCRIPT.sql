CREATE TABLE usuario(
id_usuario SERIAL PRIMARY KEY,
nome_usuario VARCHAR (255),
login_usuario VARCHAR (30),
senha_usuario VARCHAR (10),
endereco VARCHAR (50),
gerencia BOOLEAN );

CREATE TABLE pedido(
id_pedido SERIAL PRIMARY KEY,
fk_id_usuario INTEGER,
status INTEGER,
valor_final DOUBLE PRECISION,
FOREIGN KEY (fk_id_usuario) REFERENCES usuario (id_usuario));

CREATE TABLE calcado(
id_calcado SERIAL PRIMARY KEY,
fk_id_usuario INTEGER,
tipo_calcado INTEGER,
modelo_calcado VARCHAR (50),
preco DOUBLE PRECISION,
FOREIGN KEY (fk_id_usuario) REFERENCES usuario (id_usuario));

CREATE TABLE estoque(
id_estoque SERIAL PRIMARY KEY,
fk_id_calcado INTEGER,
tamanho INTEGER,
quantidade_disponivel INTEGER,
FOREIGN KEY (fk_id_calcado) REFERENCES calcado (id_calcado));

CREATE TABLE item_de_pedido(
id_item SERIAL PRIMARY KEY,
fk_id_pedido INTEGER,
fk_id_calcado INTEGER,
fk_id_estoque INTEGER,
quantidade INTEGER,
FOREIGN KEY (fk_id_pedido) REFERENCES pedido (id_pedido),
FOREIGN KEY (fk_id_calcado) REFERENCES calcado (id_calcado),
FOREIGN KEY (fk_id_estoque) REFERENCES estoque (id_estoque));


// INSERTS //

INSERT INTO usuario (nome_usuario, login_usuario, senha_usuario, endereco, gerencia) VALUES ('Nicolas Emanuel', 'nicolas.costa', 'nic12345', 'Sao Miguel', true);
INSERT INTO usuario (nome_usuario, login_usuario, senha_usuario, endereco, gerencia) VALUES ('Anabel Marinho', 'anabel.marinho', 'bel12345', 'Agua Nova', true);
INSERT INTO usuario (nome_usuario, login_usuario, senha_usuario, endereco, gerencia) VALUES ('Thiago Luan', 'thiago.moreira', 'thiago123', 'Santana', false);
INSERT INTO usuario (nome_usuario, login_usuario, senha_usuario, endereco, gerencia) VALUES ('Ytalo Dias', 'ytalo.dias', 'ytalo123', 'Portalegre', false);

INSERT INTO calcado (fk_id_usuario, tipo_calcado, modelo_calcado, preco) VALUES (2, 1, 'Bota Cowboy', 200.00);
INSERT INTO calcado (fk_id_usuario, tipo_calcado, modelo_calcado, preco) VALUES (1, 4, 'Chuteira de Futsal', 120.00);

INSERT INTO estoque (fk_id_calcado, tamanho, quantidade_disponivel) VALUES (1, 38, 12);
INSERT INTO estoque (fk_id_calcado, tamanho, quantidade_disponivel) VALUES (1, 40, 8);
INSERT INTO estoque (fk_id_calcado, tamanho, quantidade_disponivel) VALUES (2, 36, 10);
INSERT INTO estoque (fk_id_calcado, tamanho, quantidade_disponivel) VALUES (2, 38, 25);

INSERT INTO pedido (fk_id_usuario, status, valor_final) VALUES (3, 1, 400.00);
INSERT INTO pedido (fk_id_usuario, status, valor_final) VALUES (4, 3, 320.00);

INSERT INTO item_de_pedido (fk_id_pedido, fk_id_calcado, quantidade, fk_id_estoque) VALUES (1, 1, 2, 2);
INSERT INTO item_de_pedido (fk_id_pedido, fk_id_calcado, quantidade, fk_id_estoque) VALUES (2, 1, 1, 1);
INSERT INTO item_de_pedido (fk_id_pedido, fk_id_calcado, quantidade, fk_id_estoque) VALUES (2, 2, 1, 4);

// SELECTS //

SELECT * FROM usuario;
SELECT * FROM usuario WHERE id_usuario = 2;
SELECT * FROM calcado ORDER BY preco ASC;

// UPDATES //

UPDATE calcado SET tipo_calcado = 8, modelo_calcado = 'Tenis Nike SB', preco = 170.00 WHERE id_calcado = 2;
UPDATE usuario SET nome_usuario = 'Samira Franca', login_usuario = 'samira.franca', senha_usuario = 'samira123', endereco = 'Agua Nova' WHERE id_usuario = 4;

// DELETES //

DELETE * FROM item_de_pedido WHERE id_item = 1;
DELETE * FROM pedido WHERE id_pedido = 1;