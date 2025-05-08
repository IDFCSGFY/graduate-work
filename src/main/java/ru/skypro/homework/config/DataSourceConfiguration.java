package ru.skypro.homework.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

public class DataSourceConfiguration {

    @Bean(name = "dataSource")
    public DataSource dataSource(@Value("${spring.datasource.url}") String dbUrl) {
        var dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(dbUrl);
        dataSource.setDriverClassName("org.postgresql.Driver");
        return dataSource;
    }

    @Bean(name = "jdbcTemplate")
    public JdbcTemplate jdbcTemplate(@Qualifier("usersDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
