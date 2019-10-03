<template>
  <div id="tree-view">
    <h5>Root</h5>
    <ul class="tree">
      <li v-for="treeNode in treeNodes" :key="treeNode.id">{{treeNode.name}} 
          <span class="new badge" data-badge-caption="range">{{ treeNode.lower_bound }} / {{ treeNode.upper_bound }}</span>
        <ul v-if = "treeNode.childs!=null">
          <li v-for="num in treeNode.childs" :key="num" style="font-weight:normal">{{ num }}</li>
        </ul>
      </li>
    </ul>
  </div>
</template>

<script>
    export default {
    name: 'tree-view',
    props: {
        treeNodes: Array,
    },
    }
</script>

<style scoped>
    .item {
    cursor: pointer;
    }
    .bold {
    font-weight: bold;
    }

    ul.tree, ul.tree ul {
    list-style: none;
    margin: 10px;
    padding: 0;
    }

    ul.tree ul {
    margin-left: 20px;
    }

    ul.tree li {
    margin: 0;
    padding: 0 7px;
    line-height: 20px;
    color: #369;
    font-weight: bold;
    border-left: 1px dotted rgb(100, 100, 100);
    }

    ul.tree li:last-child {
    border-left: none;
    }

    ul.tree li:before {
    position: relative;
    top: -0.3em;
    height: 1em;
    width: 12px;
    color: white;
    border-bottom: 1px dotted rgb(100, 100, 100);
    content: '';
    display: inline-block;
    left: -7px;
    }

    ul.tree li:last-child:before {
    border-left: 1px dotted rgb(100, 100, 100);
    }
</style>