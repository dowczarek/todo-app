package com.dowczarek.logic;

import com.dowczarek.TaskConfigurationProperties;
import com.dowczarek.model.Project;
import com.dowczarek.model.ProjectRepository;
import com.dowczarek.model.TaskGroupRepository;
import com.dowczarek.model.projection.GroupReadModel;
import com.dowczarek.model.projection.GroupTaskWriteModel;
import com.dowczarek.model.projection.GroupWriteModel;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class ProjectService {
    private final ProjectRepository projectRepository;
    private final TaskGroupRepository taskGroupRepository;
    private final TaskGroupService taskGroupService;
    private final TaskConfigurationProperties config;

    public ProjectService(
            ProjectRepository projectRepository,
            TaskGroupRepository taskGroupRepository,
            TaskGroupService taskGroupService,
            TaskConfigurationProperties config
    ) {
        this.projectRepository = projectRepository;
        this.taskGroupRepository = taskGroupRepository;
        this.taskGroupService = taskGroupService;
        this.config = config;
    }

    public Project create(Project source) {
        return projectRepository.save(source);
    }

    public List<Project> readAll() {
        return projectRepository.findAll();
    }

    public GroupReadModel createGroup(int projectId, LocalDateTime deadline) {
        if (!config.getTemplate().isAllowMultipleTask() && taskGroupRepository.existsByDoneIsFalseAndProject_Id(projectId)) {
            throw new IllegalStateException("Only one undone group from project is allowed");
        }

        return projectRepository.findById(projectId)
                .map(project -> {
                    var targetGroup = new GroupWriteModel();
                    targetGroup.setDescription(project.getDescription());
                    targetGroup.setTasks(
                            project.getSteps().stream()
                                    .map(projectStep -> {
                                            var task = new GroupTaskWriteModel();
                                            task.setDescription(projectStep.getDescription());
                                            task.setDeadline(deadline.plusDays(projectStep.getDaysToDeadline()));
                                            return task;
                                        }
                                    ).collect(Collectors.toSet())
                    );
                    return taskGroupService.createGroup(targetGroup);
                }).orElseThrow(() -> new IllegalArgumentException("Project with given id not found"));
    }
}
