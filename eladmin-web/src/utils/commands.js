import Vue from 'vue'

/**
 * 防止重复提交指令<br/>
 * 例子: <el-button v-prevent-re-click >提交</el-button>
 */
const preventReClick = Vue.directive('preventReClick', {
  inserted(el, binding) {
    el.addEventListener('click', () => {
      if (!el.disabled) {
        el.disabled = true
        setTimeout(() => {
          el.disabled = false
        }, binding.value || 3000)
      }
    })
  }
})

export { preventReClick }
