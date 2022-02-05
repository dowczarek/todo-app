package com.dowczarek.logic;

import com.dowczarek.model.TaskGroup;
import com.dowczarek.model.TaskGroupRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

class InMemoryGroupRepository implements TaskGroupRepository {
    private int index = 0;
    private final Map<Integer, TaskGroup> map = new HashMap<>();

    public int count() {
        return map.size();
    }

    @Override
    public List<TaskGroup> findAll() {
        return new ArrayList<>(map.values());
    }

    @Override
    public Optional<TaskGroup> findById(Integer id) {
        return Optional.ofNullable(map.get(id));
    }

    @Override
    public TaskGroup save(TaskGroup entity) {
        if (entity.getId() == 0) {
            try {
                var field = TaskGroup.class.getDeclaredField("id");
                field.setAccessible(true);
                field.set(entity, ++index);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        map.put(entity.getId(), entity);

        return entity;
    }

    @Override
    public boolean existsByDoneIsFalseAndProject_Id(Integer projectId) {
        return map.values().stream()
                .filter(group -> !group.isDone())
                .filter(group -> group.getProject() != null && group.getProject().getId() == projectId)
                .findAny()
                .isPresent();
    }
}
