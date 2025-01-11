/**
 * 消息提示工具
 * @type {{success: MessageUtil.success, warning: MessageUtil.warning, error: MessageUtil.error, info: MessageUtil.info}}
 */
const MessageUtil = {
  info: function(context, content) {
    context.$message(content)
  },
  success: function(context, content) {
    context.$message({
      message: content,
      type: 'success'
    })
  },
  warning: function(context, content) {
    context.$message({
      message: content,
      type: 'warning'
    })
  },
  error: function(context, content) {
    context.$message.error(content)
  }
}
/**
 * 通知工具类
 * @type {{success: NotificationUtil.success, warning: NotificationUtil.warning, error: NotificationUtil.error, info: NotificationUtil.info}}
 */
const NotificationUtil = {
  info: function(context, content) {
    context.$notify({
      message: content
    })
  },
  success: function(context, content) {
    context.$notify({
      message: content,
      type: 'success'
    })
  },
  successDuration: function(context, content, durationTime) {
    context.$notify({
      message: content,
      type: 'success',
      duration: durationTime
    })
  },
  warning: function(context, content) {
    context.$notify({
      message: content,
      type: 'warning'
    })
  },
  error: function(context, content) {
    context.$notify({
      message: content,
      type: 'error'
    })
  },
  errorDuration: function(context, content, durationTime) {
    context.$notify({
      message: content,
      type: 'error',
      duration: durationTime
    })
  }
}

const MessageBoxUtil = {
  /**
   * 确认消息对话框<br/>
   * 提示用户确认其已经触发的动作, 并询问是否进行此操作时会用到此对话框
   * @param context 上下文
   * @param questionText 提示语
   * @param msgType 消息类型(w、i、e、s)
   * @param confirmCallBack 确认回调
   * @param cancelCallBack 取消回调
   */
  fullConfirm: function(context, questionText, msgType, confirmCallBack, cancelCallBack) {
    let currentType = 'info'
    if (msgType === 's') {
      currentType = 'success'
    } else if (msgType === 'e') {
      currentType = 'error'
    } else if (msgType === 'i') {
      currentType = 'info'
    } else if (msgType === 'w') {
      currentType = 'warning'
    }
    context.$confirm(questionText, '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: currentType
    }).then(() => {
      if (confirmCallBack != null) {
        confirmCallBack()
      }
    }).catch(() => {
      if (cancelCallBack != null) {
        cancelCallBack()
      }
    })
  },
  /**
   * 数据删除确认对话框
   * @param context 上下文
   * @param confirmCallBack 确认回调
   * @param cancelCallBack 取消回调
   */
  deleteConfirm: function(context, confirmCallBack, cancelCallBack) {
    this.fullConfirm(context, '确定删除本条数据吗？', 'w', confirmCallBack, cancelCallBack)
  },
  /**
   * 数据删除确认对话框(自定义提示内容)
   * @param context 上下文
   * @param message 提示内容
   * @param confirmCallBack 确认回调
   * @param cancelCallBack 取消回调
   */
  deleteMessageConfirm: function(context, message, confirmCallBack, cancelCallBack) {
    this.fullConfirm(context, message, 'w', confirmCallBack, cancelCallBack)
  },
  /**
   * 数据输入对话框
   *  @param context 上下文
   * @param title 标题提示
   * @param patternText 正则表达式
   * @param errorMsg 校验失败消息内容
   * @param confirmCallBack 校验通过回调
   * @param cancelCallBack 取消回调
   */
  textInputConfirm: function(context, title, patternText, errorMsg, confirmCallBack, cancelCallBack) {
    context.$prompt('', title, {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      inputPattern: patternText,
      inputErrorMessage: errorMsg
    }).then(({ value }) => {
      if (confirmCallBack != null) {
        confirmCallBack(value)
      }
    }).catch(() => {
      if (cancelCallBack != null) {
        cancelCallBack()
      }
    })
  },
  /**
   * 确认内容消息框
   * @param context 上下文
   * @param title 标题
   * @param content 内容
   */
  showConfirm: function(context, title, content) {
    context.$alert(content, title, {
      confirmButtonText: '关闭'
    })
  }
}

/**
 * 全屏加载动画<br/>
 * const loading = LoadingUtil.product()
 * loading.close()
 * @type {{product: (function(*): *)}}
 */
const LoadingUtil = {
  product: function(context) {
    return context.$loading({
      lock: true,
      text: '数据加载中',
      spinner: 'el-icon-loading',
      background: 'rgba(0, 0, 0, 0.7)'
    })
  }
}

export {
  MessageUtil,
  MessageBoxUtil,
  NotificationUtil,
  LoadingUtil
}
