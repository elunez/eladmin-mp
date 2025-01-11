<template>
  <div>
    <el-card class="box-card text">
      <countTo :start-val="startVal" :end-val="endVal" :duration="3000" />
    </el-card>
    <el-card class="box-card text">
      <countTo :start-val="startVal" :end-val="endVal" :duration="3000" />
    </el-card>
    <el-card class="box-card text">
      <countTo :start-val="startVal" :end-val="endVal" :duration="3000" />
    </el-card>
  </div>
</template>

<script>
import CountTo from 'vue-count-to'

export default {
  name: 'DragTable',
  components: { CountTo },
  data() {
    return {
      startVal: 0,
      endVal: 2024
    }
  }
}
</script>
<style>
.text {
  font-size: 36px;
  text-align: center;
  padding-top: 10%;
}

.box-card {
  width: 480px;
  display: inline-block;
}
</style>
