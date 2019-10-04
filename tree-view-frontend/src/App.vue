<template>
  <div id="app">
    <div class="row">
      <div class="col s12">
        <a class="waves-effect waves-light btn-small" @click="showAddNodeModal = true"><i class="material-icons right">add_circle</i>Add node</a> (double click on node name for more options)
      </div>
    </div>
    <add-node-modal v-if="showAddNodeModal" @close="showAddNodeModal = false" />
    <edit-node-modal v-if="showEditNodeModal" @close="showEditNodeModal = false" :editNodeObject="editNodeObject"/>
    <div class="row blue-grey lighten-5">
      <div class="col s12">
        <tree-view :treeNodes="treeNodes" />  
      </div>
    </div>
    <div class="row">
      <div class="col s12">
        <a class="waves-effect waves-light btn-small red" @click="deleteAll"><i class="material-icons right">warning</i>Delete all nodes</a>
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
import AddNodeModal from '@/components/AddNodeModal.vue'
import EditNodeModal from '@/components/EditNodeModal.vue'
export default {
  name: "app",
  components: {
    TreeView,
    AddNodeModal,
    EditNodeModal,
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
      this.showAddNodeModal = false;
    },
    deleteChild: function(nodeId, num) {
      var req = buildRequest("CHILD_DELETE");
      req.node.id = nodeId;
      req.node.childs.push(num);
      this.sendMessage(req);
    },
    updateNode: function(nodeId, name, lowerBound, upperBound) {
      var req = buildRequest("NODE_UPDATE");
      req.node.id = nodeId;
      req.node.name = name,
      req.node.lower_bound = lowerBound;
      req.node.upper_bound = upperBound;
      this.sendMessage(req);
    },
    regenerate: function(nodeId, count) {
      var req = buildRequest("CHILD_UPDATE");
      req.count = count;
      req.node.id = nodeId;
      this.sendMessage(req);
    },
    deleteNode: function(nodeId) {
      var req = buildRequest("NODE_DELETE");
      req.node.id = nodeId;
      this.sendMessage(req);
    },
    sendMessage: function(obj) {
        this.$socket.sendObj(obj);
    },
    deleteAll: function() {
        if(confirm("Are you sure you want to delete all nodes?")) {
          this.sendMessage(buildRequest("TREE_CLEAR"));
        }
    },
    // Response handlers
    childDeleted: function (data) {
      var newNode = data.nodes[0];
      var idx = this.treeNodes.findIndex(function(element) { 
        return element.id == newNode.id; 
      });
      this.replaceNode(idx, newNode);
    },
    childsUpdated: function (data) {
      var newNode = data.nodes[0];
      var idx = this.treeNodes.findIndex(function(element) { 
        return element.id == newNode.id; 
      });
      this.replaceNode(idx, newNode);
      this.showEditNodeModal = false;
    },
    nodeCreated: function(data) {
      if(this.treeNodes === undefined || this.treeNodes.length == 0) {
        this.treeNodes = [data.nodes[0]];
      } else {
        this.treeNodes.unshift(data.nodes[0]);
      }
    },
    nodeDeleted: function(data) {
      var deletedNode = data.nodes[0];
      var idx = this.treeNodes.findIndex(function(element) { 
        return element.id == deletedNode.id; 
      });
      if(idx == 0) {
        this.treeNodes.shift();
      } else {
        this.treeNodes.splice(idx-1, 1);
      }
      this.showEditNodeModal = false;
    },
    nodeUpdated: function(data) {
      var newNode = data.nodes[0];
      var idx = this.treeNodes.findIndex(function(element) { 
        return element.id == newNode.id; 
      });
      this.replaceNode(idx, newNode);
      this.showEditNodeModal = false;
    },
    replaceNode: function(idx, newNode) {
      var clone = this.treeNodes.splice(0);
      clone[idx] = newNode;
      this.updateNodes(clone);
    },
    updateNodes: function(nodes) {
      this.treeNodes = nodes;
    },
    // Other methods
    editNode: function(nodeId) {
      var idx = this.treeNodes.findIndex(function(element) { 
        return element.id == nodeId; 
      });
      console.log(idx);
      console.log(this.treeNodes[idx]);
      this.editNodeObject = this.treeNodes[idx];
      this.showEditNodeModal = true;
    },
  },
  data() {
    return {
      isConnected: false,
      treeNodes: [],
      editNodeObject: {},
      showAddNodeModal: false,
      showEditNodeModal: false,
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
      else if(type == "NODE_CREATED") {
        this.nodeCreated(data);
      }
      else if(type == "ERROR") {
        M.toast({classes: "red", html: data.msg})
      }
      else if(type == "NODE_UPDATED") {
        this.nodeUpdated(data);
      }
      else if(type == "NODE_DELETED") {
        this.nodeDeleted(data);
      }
      else if(type == "CHILD_UPDATED") {
        this.childsUpdated(data);
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
