<template>
  <!-- el-table中 添加 row-key="id" ，ref 创建拖拽时用到获取表格节点 -->
  <el-table
    ref="sortTable"
    height="300"
    :data="sortList"
    row-key="id"
  >
    <el-table-column width="50" align="center" prop="orderNum" label="序号" />
    <el-table-column width="100" align="center" prop="username" label="用户名" />
    <el-table-column align="center" prop="code" label="编号" />
    <el-table-column align="center" prop="code" label="编号" />
    <el-table-column align="center" prop="code" label="编号" />
    <el-table-column align="center" prop="code" label="编号" />
    <el-table-column align="center" prop="code" label="编号" />
    <el-table-column align="center" prop="code" label="编号" />
    <el-table-column align="center" prop="code" label="编号" />
    <el-table-column width="100" align="center" prop="sex" label="性别" />
  </el-table>
</template>

<script>
import Sortable from 'sortablejs'

export default {
  data() {
    return {
      sortList: []
    }
  },
  mounted() {
    const list = []
    for (let i = 0; i < 10; i++) {
      list.push({
        orderNum: i + 1,
        id: i + 1,
        username: '用户名' + i,
        code: i,
        sex: i % 2 === 0 ? '男' : '女'
      })
    }
    this.sortList = list
    this.initDragTable()
  },
  methods: {
    // 初始化拖拽功能
    initDragTable() {
      const table = this.$refs.sortTable.$el.querySelectorAll('.el-table__body-wrapper > table > tbody')[0]
      const that = this
      Sortable.create(table, {
        animation: 1000,
        onEnd({ newIndex, oldIndex }) {
          // 拖拽排序数据
          that.sortList.splice(newIndex, 0, that.sortList.splice(oldIndex, 1)[0])
          const newArray = that.sortList.slice(0)
          that.sortList = [] // 必须有此步骤，不然拖拽后回弹
          that.$nextTick(function() {
            that.sortList = newArray // 重新赋值，用新数据来刷新视图
            this.updateOrderNum(that.sortList)// 更改列表中的序号，使序号1.2.3.4.....显示，不然就是行拖拽后乱序显示如:3.2.4.1...
          })
        }
      })
    },
    updateOrderNum(data) {
      data.forEach((item, index) => {
        item.orderNum = index + 1
      })
    }
  }
}
</script>
