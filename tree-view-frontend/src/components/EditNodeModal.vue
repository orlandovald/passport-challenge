<template>
    <transition name="modal">
    <div class="modal-mask">
      <div class="modal-wrapper">
        <div class="modal-container">

          <div class="modal-header">
            <slot name="header">
              Edit Node
            </slot>
          </div>

          <div class="modal-body">
              <input id="node_name" type="text" v-model="nodeName" placeholder="">
              <label for="node_name">Name</label>
              <input id="lower_bound" type="number" v-model="lowerBound" placeholder="">
              <label for="lower_bound">Lower Bound</label>
              <input id="upper_bound" type="number" v-model="upperBound" placeholder="">
              <label for="upper_bound">Upper Bound</label>
          </div>

          <div class="modal-footer">
            <slot name="footer">
              <a @click="edit" class="waves-effect waves-light btn-small">Update</a>&nbsp;
              <a @click="$emit('close')" class="waves-effect waves-light btn-small grey darken-3"><i class="tiny material-icons">close</i></a>&nbsp;
              <a @click="deleteNode" class="waves-effect waves-light btn-small red"><i class="tiny material-icons">delete</i></a>
            </slot>
          </div>

          <div class="modal-header" style="margin-top:14px;">
            <slot name="header">
              Re-generate childs
            </slot>
          </div>

          <div class="modal-body">
              <input id="child_count" type="number" v-model="childCount" placeholder="">
              <label for="child_count">Child Count</label>
          </div>

          <div class="modal-footer">
            <slot name="footer">
              <a @click="regenerate" class="waves-effect waves-light btn-small">Regenerate</a>&nbsp;
              <a @click="$emit('close')" class="waves-effect waves-light btn-small grey darken-3"><i class="tiny material-icons">close</i></a>&nbsp;
            </slot>
          </div>

        </div>
      </div>
    </div>
  </transition>
</template>

<script>

function isPositiveNumberOrCero(value) {
    if(value.length == 0 || isNaN(value) || Number(value) < 0) {
        return false;
    }
    return true;
}

export default {
    name: 'edit-node-modal',
    props: {
            editNodeObject: Object,
        },
        data: function () {
            return {
                nodeName: this.editNodeObject.name,
                upperBound: this.editNodeObject.upper_bound,
                lowerBound: this.editNodeObject.lower_bound,
                childCount: "",
            }
        },
        methods: {
            edit() {
                if(this.nodeName.length <= 0) {
                    M.toast({classes: "red", html: '"Name" is required'})
                    return;
                }
                if(!isPositiveNumberOrCero(this.lowerBound)) {
                    M.toast({classes: "red", html: '"Lower Bound" should be a positive number'})
                    return;
                }
                if(!isPositiveNumberOrCero(this.upperBound)) {
                    M.toast({classes: "red", html: '"Upper Bound" should be a positive number'})
                    return;
                }
                if(Number(this.lowerBound) >= Number(this.upperBound)) {
                    M.toast({classes: "red", html: '"Upper Bound" should be greater than "Lower Bound"'})
                    return;
                }
                
                this.$parent.updateNode(this.editNodeObject.id, this.nodeName, this.lowerBound, this.upperBound);
            },
            regenerate() {
                if(!isPositiveNumberOrCero(this.childCount) || Number(this.childCount) > 15) {
                    M.toast({classes: "red", html: '"Child Count" should be number between 0-15'})
                    return;
                }
                this.$parent.regenerate(this.editNodeObject.id, this.childCount);
            },
            deleteNode() {
                if(confirm("Are you sure you want to delete this Node?")) {
                    this.$parent.deleteNode(this.editNodeObject.id);
                }
            },
        },
}
</script>

<style scoped>
.modal-mask {
  position: fixed;
  z-index: 9998;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, .5);
  display: table;
  transition: opacity .3s ease;
}

.modal-wrapper {
  display: table-cell;
  vertical-align: middle;
}

.modal-container {
  width: 300px;
  margin: 0px auto;
  padding: 20px 30px;
  background-color: #fff;
  border-radius: 2px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, .33);
  transition: all .3s ease;
  font-family: Helvetica, Arial, sans-serif;
}

.modal-header h3 {
  margin-top: 0;
  color: #42b983;
}

.modal-body {
  margin: 20px 0;
}

.modal-default-button {
  float: right;
}

.modal-enter {
  opacity: 0;
}

.modal-leave-active {
  opacity: 0;
}

.modal-enter .modal-container,
.modal-leave-active .modal-container {
  -webkit-transform: scale(1.1);
  transform: scale(1.1);
}
</style>