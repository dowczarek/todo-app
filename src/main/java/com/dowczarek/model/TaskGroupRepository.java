package com.dowczarek.model;

import java.util.List;
import java.util.Optional;

public interface TaskGroupRepository {
    List<TaskGroup> findAll();

    Optional<TaskGroup> findById(Integer id);

    List<TaskGroup> findAllByDoneIsFalseAndProject_Id(Integer projectId);

    TaskGroup save(TaskGroup entity);
}