/**
 * @param context 上下文，例如 this
 * @param wsUri websocket地址
 * @constructor
 */
function EasyWebSocket(context, wsUri) {
  this.context = context
  this.wsUri = wsUri
  this.webSocket = null
  /**
   * 初始化WebSocket连接
   * @param handleError -> function (e) { e是异常本身 }
   * @param handleMessage -> function (e) { e.data是message }
   */
  this.connect = function(handleError, handleMessage) {
    this.webSocket = new WebSocket(this.wsUri)
    // 连接发生错误
    this.webSocket.onerror = handleError
    // 收到消息
    this.webSocket.onmessage = handleMessage
    return this
  }
  /**
   * 发送客户端数据
   * @param data 客户端数据
   */
  this.sendData = function(data) {
    this.webSocket.send(JSON.stringify(data))
  }
  /**
   * 用于关闭ws连接
   */
  this.close = function() {
    console.info('关闭连接')
    if (this.webSocket.readyState === 1) {
      this.sendData([{}])
      this.webSocket.close()
    }
  }
}

export default EasyWebSocket
