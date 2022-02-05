package com.dowczarek.logic;

import com.dowczarek.TaskConfigurationProperties;
import com.dowczarek.model.ProjectRepository;
import com.dowczarek.model.TaskGroupRepository;
import com.dowczarek.model.TaskRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LogicConfiguration {
    @Bean
    ProjectService projectService(
            ProjectRepository repository,
            TaskGroupRepository taskGroupRepository,
            TaskGroupService taskGroupService,
            TaskConfigurationProperties config
    ) {
        return new ProjectService(repository, taskGroupRepository, taskGroupService, config);
    }

    @Bean
    TaskGroupService taskGroupService(
            TaskGroupRepository repository,
            TaskRepository taskRepository
    ) {
        return new TaskGroupService(repository, taskRepository);
    }
}
