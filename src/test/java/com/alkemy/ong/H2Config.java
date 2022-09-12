package com.alkemy.ong;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.context.ActiveProfiles;

import javax.sql.DataSource;

@ActiveProfiles("test")
@TestConfiguration
@EnableJpaRepositories
@ComponentScan(basePackages = "com.alkemy.ong.core")
public class H2Config {

    @Bean
    public DataSource dataSourceWithEmbeddedDatabaseBuilder() {
        return new EmbeddedDatabaseBuilder()
                .setName("test")
                .setType(EmbeddedDatabaseType.H2)
                .build();
    }
}
