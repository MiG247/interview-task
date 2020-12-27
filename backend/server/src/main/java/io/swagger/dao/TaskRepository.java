package io.swagger.dao;

import io.swagger.model.Task;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Objects;
import java.util.Optional;

/**
 * Interface to provide functions to store in the database for the Entity Task
 * */
public interface TaskRepository extends JpaRepository<Task, String> {

    /**
     * Update the task entity in the database.
     * If a property of taskWithNewProperties is invalid then it will not be changed.
     * The id of taskWithNewProperties must not be <code>null</code>!
     *
     * @param taskWithNewProperties the task with the new properties. Never <code>null</code>.
     * @return the updated task. Null if the task could not be found.
     */
    default Task updateTask(final Task taskWithNewProperties){
        Objects.requireNonNull(taskWithNewProperties.getId(), "The id of the task to update must not be null!");

        Optional<Task> foundTask = this.findById(taskWithNewProperties.getId());
        if(foundTask.isPresent())
        {
            Task task = foundTask.get();
            if(StringUtils.isNotBlank(taskWithNewProperties.getTaskName())){
                task.setTaskName(taskWithNewProperties.getTaskName());
            }
            if(taskWithNewProperties.isDone()!= null){
                task.setDone(taskWithNewProperties.isDone());
            }
            return this.save(task);
        }
        return null;
    }
}
