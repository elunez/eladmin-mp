// Plugin Config
module.exports = [
  // 搜索框第三方搜索
  [
    "thirdparty-search", {
      thirdparty: [
        {
          title: "在GitHub中搜索",
          frontUrl: "https://github.com/search?q=",
          behindUrl: ""
        },
        {
          title: "在Google中搜索",
          frontUrl: "https://www.google.com/search?q="
        },
        {
          title: "在Baidu中搜索",
          frontUrl: "https://www.baidu.com/s?wd="
        }
      ]
    }
  ],
  // 代码块复制
  [
    "one-click-copy", {
      copySelector: [
        'div[class*="language-"] pre',
        'div[class*="aside-code"] aside'
      ],
      copyMessage: "复制成功",
      duration: 1000,
      showInMobile: false
    }
  ],
  // "上次更新"时间格式
  [
    "@vuepress/last-updated", {
      transformer: (timestamp, lang) => {
        const dayjs = require("dayjs"); // https://day.js.org/
        return dayjs(timestamp).format("YYYY/MM/DD, HH:mm:ss");
      }
    }
  ],
  [
  'sitemap', {
      hostname: 'https://eladmin.vip' // 网站域名
    }
  ]
];
