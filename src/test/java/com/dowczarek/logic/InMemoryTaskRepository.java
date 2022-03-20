package com.dowczarek.logic;

import com.dowczarek.model.Task;
import com.dowczarek.model.TaskRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

class InMemoryTaskRepository implements TaskRepository {
    private final Map<Integer, Task> tasks = new HashMap<>();

    @Override
    public List<Task> findAll() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public Page<Task> findAll(Pageable page) {
        return null;
    }

    @Override
    public List<Task> findAllByGroup_Id(int groupId) {
//        return tasks.values().stream()
//                .filter(task -> task.getGroup().getId() == groupId)
//                .collect(Collectors.toList());

        return List.of();
    }

    @Override
    public Optional<Task> findById(Integer id) {
        return Optional.ofNullable(tasks.get(id));
    }

    @Override
    public boolean existsById(Integer id) {
        return tasks.containsKey(id);
    }

    @Override
    public boolean existsByDoneIsFalseAndGroup_Id(Integer groupId) {
        return false;
    }

    @Override
    public List<Task> findByDone(boolean done) {
        return null;
    }

    @Override
    public Task save(Task entity) {
        int key = tasks.size() + 1;
        try {
            var filed = Task.class.getDeclaredField("id");
            filed.setAccessible(true);
            filed.set(entity, key);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        tasks.put(key, entity);

        return tasks.get(key);
    }
}
