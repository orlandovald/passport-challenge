<template>
  <div id="app">
    <tree-view :treeNodes="treeNodes" />
  </div>
</template>

<script>
import TreeView from '@/components/TreeView.vue'
export default {
  name: "app",
  components: {
    TreeView,
  },
  data() {
    return {
      treeNodes: [
        {
          id: 1,
          name: 'Orlando',
          childs: [202539349, 107924618, 210693730],
          lower_bound: 1,
          upper_bound: 256432534
        },
        {
          id: 2,
          name: 'Second parent',
          childs: [156, 524, 280, 922, 790],
          lower_bound: 100,
          upper_bound: 1000
        }
      ],
    }
  },
};
</script>

<style>
#app {
  font-family: "Avenir", Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: left;
  color: #2c3e50;
  margin-top: 0px;
}

</style>
