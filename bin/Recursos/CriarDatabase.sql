create schema estacionamento;
use estacionamento;
create table tb_movimentacao (
	placa varchar(100),
	marca varchar(100),
    modelo varchar(100),
    cor varchar(100),
    data_entrada datetime not null,
    data_saida datetime,
    valor float
);
create table tb_status_vaga (
	id int,
	ocupadas varchar(100)
);

insert into tb_movimentacao (placa, marca, modelo, cor, data_entrada) values ('FVK-4190', 'CHEVROLET', 'CELTA', 'PRETO', '2021-02-07 15:38:00');
select * from tb_movimentacao;
select * from tb_status_vaga;
insert into tb_status_vaga values (1, '1');