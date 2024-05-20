Resumo para executar:

Clonar o repositório na máquina local/ Fazer a sua Branch
Importar para o JavaEE como Existing Maven Project
Instalar o Lombok ;-; (É só ir no site deles, baixar e instalar)
Botão direito do mouse no projeto -> Maven -> Update Project 
-> Habilita Force Update (pelo menos da primeira vez) e aperta OK
Executar o main (br.com.projetotiao.ProjetotiaoApplication.java)
Não precisa configurar Banco de Dados, pois coloquei o H2 que é em memória.

Se olhar o log, vai ver que já vai criar sozinho as tabelas e inserir alguns dados via arquivo resources/insert-data.sql
Se quiser automatizar mais dados ao subir o banco, só colocar o insert nesse arquivo.
Cuidado ao inserir novas linhas, porque no H2 aparentemente não tem log e não vai mostrar se deu erro.
Teria que ir no http://localhost:8080/h2-console para verificar e dar um select.

Resumo para testar login:

---------- POST http://localhost:8080/auth/login

-- Joelton (cliente)
{
	"email" : "cliente@teste.com",
	"password" : "senha123"
}

-- Helton (profissional)
{
	"email" : "profissional1@teste.com",
	"password" : "senha123"
}

OS LINKS
	POST http://localhost:8080/auth/login
	POST http://localhost:8080/auth/register
	GET  http://localhost:8080/h2-console
	
	não exigem autenticação
	
Os demais links, depois que logar, vai retornar um Token e vc usa ele no header das requisições...
Salvar esse token no localStorage via front e adicionar no header Authentication

Para testar no Insomnia:
Depois de fazer o Post do login, vai receber um token
Copiar o token, fazer outra requisição GET  http://localhost:8080/user/profissional
Ir na aba de autenticação, selecionar Bearer Token (Spring Security usa esse tipo)
Preencher o Prefix com Bearer msm
Colar o Token que recebeu no espaço do Token

Enviar (vazio msm pq é um GET)
É pra retornar todos os Profissionais.
Se fizer errado, retorna 403 porque o Spring Security barrou.

Para olhar a tabela em memória do H2:
http://localhost:8080/h2-console
Talvez tenha que preencher com informações do banco diretamente do application.properties

spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=


---------- EndPoints:


---------- POST /auth/register

-- Profissional

{
  "name": "Felipe",
  "email": "profissional3@teste.com",
  "password": "senha123",
  "birthday": "1990-01-01",
  "phone": "123456789",
  "phone2": "987654321",
  "cpf": "123.456.789-10",
  "bio": "Texto da biografia do profissional",
  "type": "P",
  "profissoes": [
    {
      "nome": "Engenheiro Civil",
      "dataInicio": "2021-05-12"
    },
    {
      "nome": "Arquiteto",
      "dataInicio": "2013-05-10"
    }
  ]
}

-- Cliente

{
  "name": "Manoel",
  "email": "cliente33@teste.com",
  "password": "senha123",
  "birthday": "1996-01-01",
  "phone": "2222222222",
  "phone2": "33333333333",
  "cpf": "111.111.789-10",
  "bio": "Texto da biografia do cliente aaaaaa",
  "type": "C"
}

OBS: phone2, bio e profissoes são Opcionais
OBS2: não coloquei pra comportar uma imagem ainda (para a foto)

---------- RETORNO /auth/register

{
	"name": "Felipe",
	"type": "P",
	"Token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJwcm9qZXRvdGlhbyIsInN1YiI6InByb2Zpc3Npb25hbEB0ZXN0ZS5jb20iLCJleHAiOjE3MTYxNjY1OTR9.gYOpro5HsS_2hVfakxOwTk0eqxt2z2baY5VfDh-t2R8"
}

---------- POST /auth/login

-- Joelton (cliente)
{
	"email" : "cliente@teste.com",
	"password" : "senha123"
}

-- Helton (profissional)
{
	"email" : "profissional1@teste.com",
	"password" : "senha123"
}

-- Josemo (profissional)
{
	"email" : "profissional2@teste.com",
	"password" : "senha123"
}

---------- RETORNO /auth/login

{
	"name": "Helton",
	"type": "P",
	"Token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJwcm9qZXRvdGlhbyIsInN1YiI6InByb2Zpc3Npb25hbEB0ZXN0ZS5jb20iLCJleHAiOjE3MTYxNjY1OTR9.gYOpro5HsS_2hVfakxOwTk0eqxt2z2baY5VfDh-t2R8"
}


---------- POST /user/profile

--
{
	"email" : "profissional1@teste.com"
}
--
{
	"email" : "profissional2@teste.com"
}
--
{
	"email" : "cliente@teste.com"
}

---------- RETORNO /user/profile

--
{
	"name": "Joelton",
	"email": "cliente@teste.com",
	"birthday": "1992-01-01",
	"phone": "42144141",
	"phone2": "424343434",
	"cpf": "122.456.789-10",
	"bio": "Texto da biografia do cliente",
	"profissoes": null
}

--
{
	"name": "Helton",
	"email": "profissional1@teste.com",
	"birthday": "1994-01-01",
	"phone": "8388288282",
	"phone2": "8399892898",
	"cpf": "123.333.333-11",
	"bio": "Texto da biografia do profissional lllllll",
	"profissoes": [
		{
			"nome": "Arquiteto",
			"dataInicio": "2020-11-10"
		}
	]
}


---------- GET /user/profissional

Retorna todos os profissionais. (Para aparecer na tela de busca de Profissionais)

---------- RETORNO /user/profissional

-- Exemplo
[
	{
		"name": "Felipe",
		"email": "profissional@teste.com",
		"birthday": "1990-01-01",
		"phone": "123456789",
		"phone2": "123123124",
		"cpf": "123.456.789-10",
		"bio": "Texto da biografia do profissional",
		"profissoes": [
			{
				"nome": "Arquiteto",
				"dataInicio": "2019-05-10"
			},
			{
				"nome": "Engenheiro Civil",
				"dataInicio": "2023-05-12"
			}
		]
	},
	{
		"name": "Josemo",
		"email": "profissional2@teste.com",
		"birthday": "1998-09-11",
		"phone": "123123123123",
		"phone2": "313123123123",
		"cpf": "123.456.232-99",
		"bio": "Texto da biografia do profissional 22222",
		"profissoes": [
			{
				"nome": "Engenheiro Civil",
				"dataInicio": "2023-05-18"
			}
		]
	}
]


--------------------  Dados inseridos automaticamente via arquivo resources/insert-data.sql:

-- Inserir as profissões
INSERT INTO profissoes (nome) VALUES ('Engenheiro Civil');
INSERT INTO profissoes (nome) VALUES ('Arquiteto');

-- Inserir os usuários
INSERT INTO `users` (`user_type`, `bio`, `birthday`, `cpf`, `email`, `name`, `password`, `phone`, `phone2`) VALUES
('PROFISSIONAL', 'Texto da biografia do profissional 22222', '1998-09-11', '123.456.232-99', 'profissional2@teste.com', 'Josemo', '$2a$10$D5p7CyNmXZspBhDj.TV1duD0/w17VQYNi7cEcPdVY5b3DIQd9kQz2', '123123123123', '313123123123'),
('CLIENTE', 'Texto da biografia do cliente', '1992-01-01', '122.456.789-10', 'cliente@teste.com', 'Joelton', '$2a$10$DMiZYD1xm/VdnPqYH1ZtP.Umxv8TkbbTGIrnwyQPB5cf/pomPfo/u', '42144141', '424343434'),
('PROFISSIONAL', 'Texto da biografia do profissional lllllll', '1994-01-01', '123.333.333-11', 'profissional1@teste.com', 'Helton', '$2a$10$oClb.jZWfXn45qgzNIbTnOroFvcP7Edc/VxLXmLPvc/qP1lJQD0ZO', '8388288282', '8399892898');

-- Inserir o relacionamento entre profissional e profissão
INSERT INTO profissional_profissao (profissao_id, profissional_id, data_inicio)
SELECT 
    (SELECT id FROM profissoes WHERE nome = 'Engenheiro Civil'), 
    (SELECT id FROM users WHERE name = 'Josemo'), 
    '2023-05-18';

INSERT INTO profissional_profissao (profissao_id, profissional_id, data_inicio)
SELECT 
    (SELECT id FROM profissoes WHERE nome = 'Arquiteto'), 
    (SELECT id FROM users WHERE name = 'Josemo'), 
    '2019-02-01';

INSERT INTO profissional_profissao (profissao_id, profissional_id, data_inicio)
SELECT 
    (SELECT id FROM profissoes WHERE nome = 'Arquiteto'), 
    (SELECT id FROM users WHERE name = 'Helton'), 
    '2020-11-10';
