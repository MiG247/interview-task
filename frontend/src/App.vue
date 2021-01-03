<template>
  <div id="app" class="container">
    <h2 style="text-align:center; color:grey; font-weight:inherit;">TO-DO List</h2>
    <task-list/>
  </div>
</template>

<script>

import TaskList from './components/TaskList.vue'

export default {
  name: 'app',
  components: {
    TaskList
  }
}
</script>

<style>
.container{
  max-width: 600px;
  margin: 0 auto;
}

#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
  margin-top: 60px;
  font-size: 24px;
}

</style>
