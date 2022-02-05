package com.dowczarek.logic;

import com.dowczarek.TaskConfigurationProperties;
import com.dowczarek.model.ProjectRepository;
import com.dowczarek.model.TaskGroupRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LogicConfiguration {
    @Bean
    ProjectService service(
            ProjectRepository repository,
            TaskGroupRepository taskGroupRepository,
            TaskConfigurationProperties config
    ) {
        return new ProjectService(repository, taskGroupRepository, config);
    }
}
