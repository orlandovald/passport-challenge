<template>
  <div id="app">
    <!-- <add-button /> -->
    <div class="row">
      <div class="col s12">
        <a class="waves-effect waves-light btn-small" @click="showModal = true"><i class="material-icons right">add_circle</i>Add node</a>
      </div>
    </div>
    <add-node-modal v-if="showModal" @close="showModal = false" />
    <div class="row blue-grey lighten-5">
      <div class="col s12">
        <tree-view :treeNodes="treeNodes" />  
      </div>
    </div>
  </div>
</template>

<script>
function buildRequest(type) {
  return {
    "type":type,
    "node": {
      "id": -1,
      "childs": []
    }
  }
}

var treeNodes = [];
import TreeView from '@/components/TreeView.vue'
import AddButton from '@/components/AddButton.vue'
import AddNodeModal from '@/components/AddNodeModal.vue'
export default {
  name: "app",
  components: {
    TreeView,
    AddButton,
    AddNodeModal,
  },
  methods: {
    // Request handlers
    createNode: function(name, lowerBound, upperBound, count) {
      var req = buildRequest("NODE_CREATE");
      req.node.name=name;
      req.node.lower_bound=lowerBound;
      req.node.upper_bound=upperBound;
      req.count=count;
      this.sendMessage(req);
      this.showModal = false;
    },
    deleteChild: function(nodeId, num) {
      var req = buildRequest("CHILD_DELETE");
      req.node.id = nodeId;
      req.node.childs.push(num);
      this.sendMessage(req);
    },
    sendMessage: function(obj) {
        this.$socket.sendObj(obj);
    },
    // Response handlers
    childDeleted: function (data) {
      var newNode = data.nodes[0];
      var idx = this.treeNodes.findIndex(function(element) { 
        return element.id == newNode.id; 
      });
      this.replaceNode(idx, newNode);
    },
    replaceNode: function(idx, newNode) {
      var clone = this.treeNodes.splice(0);
      clone[idx] = newNode;
      this.updateNodes(clone);
    },
    updateNodes: function(nodes) {
      this.treeNodes = nodes;
    },
  },
  data() {
    return {
      isConnected: false,
      treeNodes: [],
      showModal: false,
    }
  },
  created () {
    this.$options.sockets.onmessage = function(event) {
      var data = JSON.parse(event.data);
      var type = String(data.type);
      console.log("Message received: " + type);
      if(type == "REFRESH_ALL_NODES") {
        this.updateNodes(data.nodes);
      }
      else if(type == "CHILD_DELETED") {
        this.childDeleted(data);
      }
      else {
        console.log("Unknown response\n" + event);
      }
    };
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
