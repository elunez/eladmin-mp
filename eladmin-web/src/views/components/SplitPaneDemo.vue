<template>
  <div>
    <el-divider>分割成两列</el-divider>
    <div class="wrap">
      <split-pane :min-percent="20" :default-percent="30" split="vertical" @resize="resize">
        <template slot="paneL">
          <!-- 编辑自己的代码 -->
          <div class="paneL" />
        </template>
        <template slot="paneR">
          <!-- 编辑自己的代码 -->
          <div class="paneR" />
        </template>
      </split-pane>
    </div>
    <el-divider>分割成三列</el-divider>
    <div class="wrap">
      <split-pane :min-percent="20" :default-percent="30" split="vertical" @resize="resize">
        <template slot="paneL">
          A
        </template>
        <template slot="paneR">
          <split-pane split="horizontal">
            <template slot="paneL">
              B
            </template>
            <template slot="paneR">
              C
            </template>
          </split-pane>
        </template>
      </split-pane>
    </div>
    <el-divider>水平分割</el-divider>
    <div class="wrap">
      <split-pane :min-percent="20" :default-percent="30" split="horizontal" @resize="resize">
        <div slot="first" style="width: 100%;height: 100%;background-color: red" />
        <div slot="second" style="width: 100%;height: 100%;background-color: blue" />
      </split-pane>
    </div>
  </div>
</template>

<script>
export default {
  name: 'SplitPaneDemo',
  methods: {
    resize() {}
  }
}
</script>

<style lang="scss" scoped>
.wrap{
  height:300px;
  .paneL{
    background-color:red;
    height:100%;
    width:100%;
  }
  .paneR{
    background-color: blue;
    height:100%;
    width:100%;
  }
}
</style>
