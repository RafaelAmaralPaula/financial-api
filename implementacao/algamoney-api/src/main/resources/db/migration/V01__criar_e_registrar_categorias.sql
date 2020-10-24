CREATE TABLE categoria(
	
	codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	nome VARCHAR(50) NOT NULL	
	
)Engine=InnoDB DEFAULT CHARSET=utf8;


INSERT INTO categoria(nome) VALUES
('Lazer'),
('Alimentação'),
('Supermercado'),
('Farmácia'),
('Outros');