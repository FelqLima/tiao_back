package br.com.projetotiao.infra.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import jakarta.annotation.PostConstruct;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Configuration
public class DataInitializationConfig {

    private static final Logger logger = LoggerFactory.getLogger(DataInitializationConfig.class);

    @Value("classpath:insert-data.sql")
    private Resource dataScript;

    @Bean
    public DataSourceInitializer dataSourceInitializer(DataSource dataSource) {
        logger.info("Iniciando a configuração de inicialização de dados...");
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator(dataScript);
        databasePopulator.setContinueOnError(true); // para continuar se houver erros
        DataSourceInitializer initializer = new DataSourceInitializer();
        initializer.setDataSource(dataSource);
        initializer.setDatabasePopulator(databasePopulator);
        logger.info("Configuração de inicialização de dados concluída.");
        return initializer;
    }

    @PostConstruct
    public void logDataScriptContent() {
        logger.info("Conteúdo do arquivo SQL de inicialização de dados:");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(dataScript.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                logger.info(line);
            }
        } catch (IOException e) {
            logger.error("Erro ao ler o arquivo SQL de inicialização de dados.", e);
        }
    }
}