-- Inserir as profissões
INSERT INTO profissoes (nome) VALUES ('Engenheiro Civil');
INSERT INTO profissoes (nome) VALUES ('Arquiteto');

-- Inserir os usuários
INSERT INTO `users` (`user_type`, `bio`, `birthday`, `cpf`, `email`, `name`, `password`, `phone`, `phone2`) VALUES
('PROFISSIONAL', 'Mussum Ipsum, cacilds vidis litro abertis. Aenean aliquam molestie leo, vitae iaculis nisl. Cevadis im ampola pa arma uma pindureta.', '1998-09-11', '123.456.232-99', 'profissional2@teste.com', 'Josemo', '$2a$10$D5p7CyNmXZspBhDj.TV1duD0/w17VQYNi7cEcPdVY5b3DIQd9kQz2', '123123123123', '313123123123'),
('CLIENTE', 'Texto da biografia do cliente', '1992-01-01', '122.456.789-10', 'cliente@teste.com', 'Joelton', '$2a$10$DMiZYD1xm/VdnPqYH1ZtP.Umxv8TkbbTGIrnwyQPB5cf/pomPfo/u', '42144141', '424343434'),
('PROFISSIONAL', 'Mussum Ipsum, cacilds vidis litro abertis. Em pé sem cair, deitado sem dormir, sentado sem cochilar e fazendo pose. Per aumento de cachacis, eu reclamis.', '1994-01-01', '123.333.333-11', 'profissional1@teste.com', 'Helton', '$2a$10$oClb.jZWfXn45qgzNIbTnOroFvcP7Edc/VxLXmLPvc/qP1lJQD0ZO', '8388288282', '8399892898'),
('PROFISSIONAL', 'Mussum Ipsum, cacilds vidis litro abertis. Quem manda na minha terra sou euzis! Bota 1 metro de cachacis aí pra viagem', '1995-06-01', '123.333.333-29', 'profissional3@teste.com', 'Manoel', '$2a$10$oClb.jZWfXn45qgzNIbTnOroFvcP7Edc/VxLXmLPvc/qP1lJQD0ZO', '8382328282', '8399892328');

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
    (SELECT id FROM profissoes WHERE nome = 'Engenheiro Civil'), 
    (SELECT id FROM users WHERE name = 'Manoel'), 
    '2014-07-01';

INSERT INTO profissional_profissao (profissao_id, profissional_id, data_inicio)
SELECT 
    (SELECT id FROM profissoes WHERE nome = 'Arquiteto'), 
    (SELECT id FROM users WHERE name = 'Helton'), 
    '2020-11-10';