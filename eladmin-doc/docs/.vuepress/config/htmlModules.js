/** 插入自定义html模块 (可用于插入广告模块等)
 * {
 *   homeSidebarB: htmlString, 首页侧边栏底部
 *
 *   sidebarT: htmlString, 全局左侧边栏顶部
 *   sidebarB: htmlString, 全局左侧边栏底部
 *
 *   pageT: htmlString, 全局页面顶部
 *   pageB: htmlString, 全局页面底部
 *   pageTshowMode: string, 页面顶部-显示方式：未配置默认全局；'article' => 仅文章页①； 'custom' => 仅自定义页①
 *   pageBshowMode: string, 页面底部-显示方式：未配置默认全局；'article' => 仅文章页①； 'custom' => 仅自定义页①
 *
 *   windowLB: htmlString, 全局左下角②
 *   windowRB: htmlString, 全局右下角②
 * }
 *
 * ①注：在.md文件front matter配置`article: false`的页面是自定义页，未配置的默认是文章页（首页除外）。
 * ②注：windowLB 和 windowRB：1.展示区块宽高最大是200*200px。2.请给自定义元素定一个不超过200px的固定宽高。3.在屏宽小于960px时无论如何都不会显示。
 */
module.exports = {
  sidebarT: `
      <a href="https://bwh81.net/aff.php?aff=70876" target="_blank" style="margin-top: -8px;">
        <img src="/images/banner/bwg.jpeg" alt="帮瓦工" style="width:246px;height: 72px;">
      </a>
      <a href="/pages/040101" style="text-align: center;margin-top: 2px;margin-bottom:0;display:block;color: #999;font-size: 12px;">🌈 无需备案，境外服务器推荐</a>
  `,
  windowRB: `
    <div style="margin-bottom: -3px">
        <a href="https://bwh81.net/aff.php?aff=70876" target="_blank">
        <img src="https://izlzl.com/usr/uploads/2024/08/3103712587.png" alt="帮瓦工" style="height: 200px;width: 165px;border-top-left-radius: 8px;border-top-right-radius:8px">
      </a>
    </div>
  `
};
