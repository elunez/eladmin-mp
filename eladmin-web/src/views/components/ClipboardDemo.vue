<template>
  <div>
    <el-divider>clipboard版</el-divider>
    <span>{{ msg }}</span>
    <div id="btn" @click="copy">复制</div>
    <el-divider>OneKeyCopy组件版</el-divider>
    <one-key-copy style="margin-left: 10pt" :content.sync="msg" />
  </div>
</template>
<script>
import Clipboard from 'clipboard'
import { Message } from 'element-ui'
import OneKeyCopy from '@/views/components/OneKeyCopy'
export default {
  components: { OneKeyCopy },
  data() {
    return {
      msg: '来复制我吧'
    }
  },
  methods: {
    // 复制方法
    copy() {
      const that = this
      const clipboard = new Clipboard('#btn', {
        text(trigger) {
          console.log(trigger)
          // 返回字符串
          return that.msg
        }
      })
      // 复制成功
      clipboard.on('success', (e) => {
        Message.success('复制成功')
        console.log(e, '复制成功')
        clipboard.destroy()
      })
      // //复制失败
      clipboard.on('error', (e) => {
        Message.error('复制失败')
        console.log(e, '复制失败')
        clipboard.destroy()
      })
    }
  }
}
</script>
<style scoped>
#btn {
  display: inline-block;
  padding: 3px 12px;
  border-radius: 6px;
  background-color: #eee;
  color: #333;
  cursor: pointer;
}
#btn:active {
  background-color: #ddd;
  color: #666;
}
</style>
