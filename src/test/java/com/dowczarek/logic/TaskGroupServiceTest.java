package com.dowczarek.logic;

import com.dowczarek.model.TaskGroupRepository;
import com.dowczarek.model.TaskRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TaskGroupServiceTest {

    @Test
    @DisplayName("should throw IllegalStateException when undone tasks in group exists")
    void toggleGroup_undoneTasksInGroup_throwsIllegalStateException() {
        // given
        TaskRepository mockTaskRepository = taskRepositoryReturning(true);
        // system under test
        var toTest = new TaskGroupService(null, mockTaskRepository);

        // when
        var exception = catchThrowable(() -> toTest.toggleGroup(0));

        // then
        assertThat(exception)
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Group has undone tasks");
    }

    @Test
    @DisplayName("should throw IllegalArgumentException when no undone tasks in group and no task groups for given id")
    void toggleGroup_noUndoneTasksInGroup_And_noTaskGroup_throwsIllegalArgumentException() {
        // given
        TaskGroupRepository mockTaskGroupRepository = mock(TaskGroupRepository.class);
        when(mockTaskGroupRepository.findById(anyInt())).thenReturn(Optional.empty());
        // and
        TaskRepository mockTaskRepository = taskRepositoryReturning(false);
        // system under test
        var toTest = new TaskGroupService(mockTaskGroupRepository, mockTaskRepository);

        // when
        var exception = catchThrowable(() -> toTest.toggleGroup(0));

        // then
        assertThat(exception)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("id not found");
    }

    private TaskRepository taskRepositoryReturning(boolean result) {
        TaskRepository mockTaskRepository = mock(TaskRepository.class);
        when(mockTaskRepository.existsByDoneIsFalseAndGroup_Id(anyInt())).thenReturn(result);

        return mockTaskRepository;
    }
}
