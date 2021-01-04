<template>
  <div>  
      <input type="text" ref="newTaskInput" class="task-input" placeholder="Write your task here" v-model="newTask">
      <button class="task-add-button" @click="addTodo">Add task</button>
      <div class="task-item-block">
        <div class="task-headers">Not finished tasks</div>
        <div v-for="(task, index) in  tasks" :key="task.id">
          <div v-if="!task.done"  class="task-item">
            <div class="task-item-left">
              <input type="checkbox" class="task-checkbox" v-model="task.done" v-on:click="updateTask(task)">
              <div class="task-item-label">{{ task.taskName }}</div>
            </div>
            <div class="task-remove-button" @click="removeTodo(index, task.id)">
              &times;
            </div>
          </div>
        </div>
      </div>
      <div class="task-item-block">
        <div class="task-headers">Finished tasks</div>
        <div v-for="(task, index) in  tasks" :key="task.id">
          <div v-if="task.done" class="task-item">
            <div class="task-item-left">
              <input type="checkbox" class="task-checkbox" v-model="task.done" v-on:click="updateTask(task)">
              <div class="task-item-done-label ">{{ task.taskName }}</div>
            </div>
            <div class="task-remove-button" @click="removeTodo(index, task.id)">
              &times;
            </div>
          </div>
        </div>
      </div>
  </div>
</template>

<script>
  import swal from 'sweetalert'// https://github.com/t4t5/sweetalert
  import axios from 'axios'

  const instance = axios.create({
    baseURL: 'http://localhost:30808/v2',
    timeout: 1000
  });

  export default {
    name: "TaskListComponent",
    data () 
    {
      return {
        newTask: '',
        tasks: []        
      }
    },

    methods: 
    {
      addTodo()
      {
        // new task must not be blank
        if(this.newTask.trim().length != 0)
        {
          instance.post('/tasklist/new',
            {
               "taskName": this.newTask
            }
          ).then(response => {
            this.tasks.push(response.data);
          }).catch(error => {
            swal({
              title: "Error!",
              text: "New task could not be created! ["+error.message+"]",
              icon: "error"
            });
          })
          this.newTask = '';
        }else {
          swal({
              title: "Error!",
              text: "New task must not be empty!",
              icon: "error"
          });
        }
      },
      removeTodo(index, taskID)
      {
        instance.delete('/tasklist/remove/'+taskID)
        .then(response =>
          {
            this.tasks.splice(index, 1);
          }
        )
        .catch(error => 
        {
          swal({
            title: "Error!",
            text: "Could not delete task due server error! ["+error.message+"]",
            icon: "error"
          });
        });     
      },
      updateTask(task)
      {
        task.done = !task.done;
        instance.put('/tasklist/update', task)
        .catch(error => 
        {
          swal({
            title: "Error!",
            text: "Could not finish task due server error! ["+error.message+"]",
            icon: "error"
          });
        });
      }
    },

    mounted: function() 
    {
      /** get task list request */
      instance.get('/tasklist')
        .then(response => {
          response.data.forEach(task => {
            this.tasks.push(task);
          })
        })
        .catch(error => 
        {
          swal({
              title: "Error!",
              text: "No connection to the task server!",
              icon: "error"
          });
        });    
    }
  }
</script>


<style lang="scss" scoped>

    .task-checkbox
    {
      appearance: none;

      display: inline-flex;
      position: relative;

      margin-left: 10px;
      height: 25px;
      width: 25px;
      border: 3px solid grey;
      outline: none;
      transition-duration: 0.3s;
      cursor: pointer;
    }


    .task-checkbox:checked:after
    {
      content: '\2713';
      font-size: 175%;
      color: gray;
      position: relative;
      left: 1px;
      top: -5px;
    }

    .task-item-block
    {
      margin-bottom: 40px;
    }

    .task-input
    {
        width: 100%;
        padding: 12px 10px;
        font-size: 22px;
        margin-bottom: 40px;
        box-sizing: border-box;
        border: hidden;
        border-radius: 15px;
        box-shadow: 0px 7px 3px 2px rgba(173,173,173,0.5);

        &:focus{
            outline: 0;
        }
    }
    .task-remove-button 
    {
      cursor: pointer;
      margin-right: 2%;

      background: none;
      border:none;
      outline: none;

      font-size: 125%;
      font-weight: bold;
      color:gray;


      &:hover 
      {
        color: black;
      }
    }
    .task-add-button
    {
        background-color: rgb(0, 185, 0);
        border: none;
        border-radius: 25px;
        outline: none;

        font-size: 22px;
        color: white;
        text-align: center;
        padding: 12px 10px;

        display: block;
        width : 30%;
        margin: 0 auto;
        margin-bottom: 40px;

        cursor: pointer;
    }

    .task-headers
    {
        font-size: 22px;
        color:gray;

        margin-bottom: 15px;
    }

    .task-item-left 
    {
        display: flex;
        align-items: center;
    }

    .task-item 
    {
      box-sizing: border-box;
      border: hidden;
      box-shadow: 0px 7px 3px 2px rgba(173,173,173,0.5);

      margin-bottom: 15px;

      display: flex;
      align-items: center;
      justify-content: space-between;
      animation-duration: 0.3s;
    }

    .task-item-label 
    {
      font-size: 22px;
      padding: 12px;
    }

    .task-item-done-label 
    {
      font-size: 22px;
      color: gray;
      text-decoration: line-through;

      padding: 12px;
    }
</style>