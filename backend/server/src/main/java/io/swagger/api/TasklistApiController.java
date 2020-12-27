package io.swagger.api;

import io.swagger.dao.TaskRepository;
import io.swagger.model.ModelApiResponse;
import io.swagger.model.Task;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sun.management.VMOptionCompositeData;

import javax.annotation.Generated;
import javax.validation.constraints.*;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2020-12-20T11:00:35.176Z")
@RestController
public class TasklistApiController implements TasklistApi {

    private static final Logger log = LoggerFactory.getLogger(TasklistApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Autowired
    private TaskRepository m_TaskRepo;

    @Autowired
    public TasklistApiController(ObjectMapper objectMapper, HttpServletRequest request) throws IOException {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    @Override
    public ResponseEntity<? extends Object> addTask(@ApiParam(value = "Task object with a valid task name. (Not null or blank)" ,required=true )  @Valid @RequestBody Task newTask) {
        String accept = request.getHeader("Accept");
        ModelApiResponse modelApiResponse = new ModelApiResponse();
        if (accept != null && accept.contains("application/json"))
        {
            if(StringUtils.isNotBlank(newTask.getTaskName()))
            {
                    Task createdTask = m_TaskRepo.save(newTask);
                    return new ResponseEntity<Task>(createdTask,
                            HttpStatus.CREATED);
            }
            //the provided task name is not valid
            modelApiResponse.setCode(HttpStatus.BAD_REQUEST.value());
            modelApiResponse.setMessage("The provided task name ["+newTask.getTaskName()+"] is not valid!");
            return new ResponseEntity<ModelApiResponse>(modelApiResponse, HttpStatus.BAD_REQUEST);
        }
        // if the accept header is not valid
        modelApiResponse.setCode(HttpStatus.NOT_ACCEPTABLE.value());
        modelApiResponse.setMessage("Accept Header was not set or does not contain application/json!");
        return new ResponseEntity<ModelApiResponse>(modelApiResponse, HttpStatus.NOT_ACCEPTABLE);
    }

    @Override
    public ResponseEntity<? extends Object> deleteTask(@DecimalMin("1")@ApiParam(value = "Task with the id to delete",required=true) @PathVariable("taskID") String taskID) {

        ModelApiResponse modelApiResponse = new ModelApiResponse();
        if(taskID == null){
            modelApiResponse.setCode(HttpStatus.NOT_FOUND.value());
            modelApiResponse.setMessage("The taskID must not be null!");
            return new ResponseEntity<ModelApiResponse>(modelApiResponse, HttpStatus.NOT_FOUND);
        }
        try{
            m_TaskRepo.deleteById(taskID);
        }finally {
            return new ResponseEntity<Void>(HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<? extends Object> getTasks() {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
                List<Task> allTasks = m_TaskRepo.findAll();
                return new ResponseEntity<List<Task>>(allTasks, HttpStatus.OK);
        }
        ModelApiResponse modelApiResponse = new ModelApiResponse();
        modelApiResponse.setCode(HttpStatus.NOT_ACCEPTABLE.value());
        modelApiResponse.setMessage("Accept Header was not set or does not contain application/json!");
        return new ResponseEntity<ModelApiResponse>(modelApiResponse, HttpStatus.NOT_ACCEPTABLE);
    }

    @Override
    public ResponseEntity<? extends  Object> updateTask(@ApiParam(value = "Task object with changed data. Invalid properties will not be changed. E.g.: blank taskName." ,required=true )  @Valid @RequestBody Task updatedTask) {
        String accept = request.getHeader("Accept");
        ModelApiResponse modelApiResponse = new ModelApiResponse();

        if (accept != null && accept.contains("application/json")) {
            if(StringUtils.isBlank(updatedTask.getId())){
                // return 400 if the id is invalid
                modelApiResponse.setCode(HttpStatus.BAD_REQUEST.value());
                modelApiResponse.setMessage("Task id ["+updatedTask.getId()+"] is not valid!");
                return new ResponseEntity<ModelApiResponse>(modelApiResponse, HttpStatus.BAD_REQUEST);
            }

            Task changedTask = m_TaskRepo.updateTask(updatedTask);
            return changedTask == null ?
                    new ResponseEntity(HttpStatus.BAD_REQUEST) : // return 400 if the task was not found
                    new ResponseEntity<Task>(changedTask, HttpStatus.OK);
        }
        // return 406 if the accept header is not valid
        modelApiResponse.setCode(HttpStatus.NOT_ACCEPTABLE.value());
        modelApiResponse.setMessage("Accept Header was not set or does not contain application/json!");
        return new ResponseEntity<ModelApiResponse>(modelApiResponse, HttpStatus.NOT_ACCEPTABLE);
    }

}
