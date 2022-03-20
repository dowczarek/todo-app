package com.dowczarek.controller;

import com.dowczarek.logic.TaskGroupService;
import com.dowczarek.model.Task;
import com.dowczarek.model.TaskRepository;
import com.dowczarek.model.projection.GroupReadModel;
import com.dowczarek.model.projection.GroupWriteModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/groups")
class TaskGroupController {
    private static final Logger logger = LoggerFactory.getLogger(TaskGroupController.class);
    private final TaskGroupService service;
    private final TaskRepository repository;

    TaskGroupController(TaskGroupService service, TaskRepository repository) {
        this.service = service;
        this.repository = repository;
    }

    @PostMapping()
    ResponseEntity<GroupReadModel> createGroup(@RequestBody @Valid GroupWriteModel toCreate) {
        logger.info("Create group");
        GroupReadModel result = service.createGroup(toCreate);
        return ResponseEntity.created(URI.create("/groups/" + result.getId())).body(result);
    }

    @GetMapping()
    ResponseEntity<List<GroupReadModel>> readAllGroups() {
        logger.info("Read all groups");
        return ResponseEntity.ok(service.readAll());
    }

    @GetMapping("/{id}/tasks")
    ResponseEntity<List<Task>> readAllTasksByGroup(@PathVariable int id) {
        logger.info("Read all tasks by group id");
        return ResponseEntity.ok(repository.findAllByGroup_Id(id));
    }

    @PatchMapping("/{id}")
    ResponseEntity<?> toggleGroup(@PathVariable int id) {
        logger.info("Toggle group");
        service.toggleGroup(id);
        return ResponseEntity.noContent().build();
    }
}
