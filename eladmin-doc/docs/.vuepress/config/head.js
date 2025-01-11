// Head Config
module.exports = [
  ["link", { rel: "icon", href: "/logo/small.png" }],
  ["meta", { name: "keywords", content: "eladmin,el-admin,eladmin官网,eladmin在线文档,eladmin学习" }],
  ["meta", { name: "theme-color", content: "#11a8cd" }],
  ["meta", { "http-equiv": "Content-Type", content: "text/html;charset=gb2312" }],
  ["meta", { name: "baidu-site-verification", content: "code-kdORRzs7wW" }],
  ["meta", { name: "sogou_site_verification", content: "M7FZGnsiqa" }],
  ["meta", { name: "360-site-verification", content: "f6ae0ca805b4ad57430f50784d798c2e" }],
  [
    "script",
    {}, `
            var _hmt = _hmt || [];
            (function() {
            let aHtml = document.createElement('a');
              var hm = document.createElement("script");
              hm.src = "//hm.baidu.com/hm.js?a8cc029036ab1e31acc5e8c8a519fe45";
              var s = document.getElementsByTagName("script")[0]; 
              s.parentNode.insertBefore(hm, s);
            })();`
  ]
]
