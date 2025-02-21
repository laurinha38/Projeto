create database Projeto;
use Projeto;

create table Usuario(
	id integer auto_increment primary key not null,
    nome varchar(50),
    cpf varchar(50),
    email varchar(50),
    login varchar(50),
    senha varchar(50),
    situacao varchar(50)
);
use Projeto;
select * from Usuario;

create table Livro(
	id integer auto_increment key not null,
    titulo varchar(50),
    autor varchar(50),
    genero varchar(50),
    faixaEtaria varchar(50),
    quantidade integer
);
use Projeto;
select * from Livro;

create table LivrosEmprestimos(
    titulo varchar(50),
    quantidade integer,
    dataEmprestimo date,
    dataEstimadaDev date,
    dataRen date,
    dataDev date,
    
    id_UsuarioFK integer,
    foreign key(id_UsuarioFK) references Usuario(id)
);
use Projeto;
select * from LivrosEmprestimos;