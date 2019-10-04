<template>
<div id="add-button">
    <div class="row">
      <div class="col s12" v-if="!display">
        <a class="waves-effect waves-light btn-small" v-on:click='display = !display'><i class="material-icons right">add_circle</i>Add node</a>
      </div>
    </div> 
    <div v-if="display">
        <div class="row">
        <div class="col s12">
            <a class="waves-effect waves-light btn-small"><i class="material-icons right">add_circle</i>Add node</a>
        </div>
        </div>
        <div class="row">
        <div class="col s12">
            <a class="waves-effect waves-light btn-small teal darken-4"><i class="material-icons right">save</i>Save</a>&nbsp;
            <a class="waves-effect waves-light btn-small grey darken-4" v-on:click='display = !display'><i class="material-icons right">cancel</i>Cancel</a>
        </div>
        </div>
    </div>
</div>
</template>

<script>
export default {
    name: 'add-button',
    data: function () {
            return {
                display: false
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

</style>