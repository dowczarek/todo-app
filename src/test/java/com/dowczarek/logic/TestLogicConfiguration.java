package com.dowczarek.logic;

import com.dowczarek.model.TaskRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
class TestLogicConfiguration {

    @Bean
    @Primary
    @Profile("!integration")
    DataSource e2eTestDataSource() {
        var result = new DriverManagerDataSource("jdbc:h2:mem:test", "sa", "");
        result.setDriverClassName("org.h2.Driver");

        return result;
    }

    @Bean
    @Primary
    @Profile("integration")
    TaskRepository testTaskRepository() {
        return new InMemoryTaskRepository();
    }
}
