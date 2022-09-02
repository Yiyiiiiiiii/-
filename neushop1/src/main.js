import Vue from "vue";

import "normalize.css/normalize.css";

import ElementUI from "element-ui";
import "element-ui/lib/theme-chalk/index.css";

import "@/styles/index.scss";

import App from "./App";
import store from "./store";
import router from "./router";

import "@/icons";
import "@/permission";

import axios from "axios";

//设置axios携带cookie
axios.defaults.withCredentials = true;

// 添加响应拦截器,axios发出的请求 响应时都会执行
axios.interceptors.response.use(
  function (response) {
    //不管在哪个页面 只要后端响应未登录就跳转登录页面
    if (response.data.code == 3003) {
      router.push("/login");
    }
    // 对响应数据做点什么
    return response;
  },
  function (error) {
    // 对响应错误做点什么
    return Promise.reject(error);
  }
);

//将导入的axios对象配置给Vue对象
Vue.prototype.$axios = axios;

/**
 * If you don't want to use mock-server
 * you want to use MockJs for mock api
 * you can execute: mockXHR()
 *
 * Currently MockJs will be used in the production environment,
 * please remove it before going online ! ! !
 */
if (process.env.NODE_ENV === "production") {
  const { mockXHR } = require("../mock");
  mockXHR();
}

// set ElementUI lang to EN
Vue.use(ElementUI);
// 如果想要中文版 element-ui，按如下方式声明
// Vue.use(ElementUI)

Vue.config.productionTip = false;

new Vue({
  el: "#app",
  router,
  store,
  render: (h) => h(App),
});
