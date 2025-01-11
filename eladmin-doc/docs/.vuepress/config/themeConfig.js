const nav = require("./nav.js");
const htmlModules = require("./htmlModules.js");

// Theme Config
module.exports = {
  nav,
  sidebarDepth: 2,
  logo: "/logo/small.png",
  searchMaxSuggestions: 10,
  lastUpdated: "上次更新",

  docsRepo: "elunez/eladmin-doc",
  docsDir: "docs",
  docsBranch: "master",
  editLinks: true,
  editLinkText: "帮助我们改善此页面！",

  // Vdoing Theme Config
  sidebar: { mode: "structuring", collapsable: false },

  updateBar: {
    showToArticle: false
  },

  category: false,
  tag: false,
  archive: true,

  author: {
    name: "ZhengJie"
  },

  social: {
    icons: [
      {
        iconClass: "icon-github",
        title: "GitHub",
        link: "https://github.com/elunez"
      },
      {
        iconClass: "icon-gitee",
        title: "Gitee",
        link: "https://gitee.com/elunez"
      },
      {
        iconClass: "icon-youjian",
        title: "发邮件",
        link: "mailto:zj7321@qq.com"
      }
    ]
  },
  footer: {
    createYear: 2018,
    copyrightInfo: [
      ' <a href="https://izlzl.com" target=_blank>知了小站</a>'
    ].join('')
  },

  htmlModules
};
