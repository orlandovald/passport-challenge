<template>
  <div id="tree-view">
    <h5>Root</h5>
    <ul class="tree">
      <li v-for="treeNode in treeNodes" :key="treeNode.id">
          <span @mouseover="showByIndex = treeNode.id" @mouseout="showByIndex = null">{{ treeNode.name }} </span> 
          <i class="tiny material-icons" v-show="showByIndex === treeNode.id">delete</i>
          <span class="new badge" data-badge-caption="range">[{{ treeNode.lower_bound }} - {{ treeNode.upper_bound }}]</span>
        <ul v-if = "treeNode.childs!=null">
          <li v-for="num in treeNode.childs" :key="treeNode.id + '-' + num" style="font-weight:normal">
            <div class="chip-child">
                <div class="chip-child-content">{{ num }}&nbsp;<i v-on:click="deleteChild(treeNode.id,num)" class="tiny material-icons">close</i></div>
            </div>
          </li>
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
        data: function () {
            return {
                showByIndex: null
            }
        },
        methods: {
            deleteChild(nodeId, num){
                this.$parent.deleteChild(nodeId, num);
            }
        },
    }
</script>

<style scoped>
    li i{
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

    .chip-child{
    display: inline-flex;
    flex-direction: row;
    background-color: #e5e5e5;
    border: none;
    cursor: default;
    height: 30px;
    outline: none;
    padding: 0;
    font-size: 14px;
    font-color: #333333;
    font-family:"Open Sans", sans-serif;
    white-space: nowrap;
    align-items: center;
    border-radius: 16px;
    vertical-align: middle;
    text-decoration: none;
    justify-content: center;
}

.chip-child-content{
    cursor: inherit;
    display: flex;
    align-items: center;
    user-select: none;
    white-space: nowrap;
    padding-left: 10px;
    padding-right: 10px;
}
</style>