package com.dowczarek.adapter;

import com.dowczarek.model.TaskGroup;
import com.dowczarek.model.TaskGroupRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface SqlTaskGroupRepository extends TaskGroupRepository, JpaRepository<TaskGroup, Integer> {
    @Override
    List<TaskGroup> findAll();
}
