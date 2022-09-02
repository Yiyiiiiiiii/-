// import router from './router'
// import store from './store'
// import { Message } from 'element-ui'
// import NProgress from 'nprogress' // progress bar
// import 'nprogress/nprogress.css' // progress bar style
// import { getToken } from '@/utils/auth' // get token from cookie
// import getPageTitle from '@/utils/get-page-title'
//
// NProgress.configure({ showSpinner: false }) // NProgress Configuration
//
// const whiteList = ['/login'] // no redirect whitelist
//
// router.beforeEach(async(to, from, next) => {
//   // start progress bar
//   NProgress.start()
//
//   // set page title
//   document.title = getPageTitle(to.meta.title)
//
//   // determine whether the user has logged in
//   const hasToken = getToken()
//
//   if (hasToken) {
//     if (to.path === '/login') {
//       // if is logged in, redirect to the home page
//       next({ path: '/' })
//       NProgress.done()
//     } else {
//       const hasGetUserInfo = store.getters.name
//       if (hasGetUserInfo) {
//         next()
//       } else {
//         try {
//           // get user info
//           await store.dispatch('user/getInfo')
//
//           next()
//         } catch (error) {
//           // remove token and go to login page to re-login
//           await store.dispatch('user/resetToken')
//           Message.error(error || 'Has Error')
//           next(`/login?redirect=${to.path}`)
//           NProgress.done()
//         }
//       }
//     }
//   } else {
//     /* has no token*/
//
//     if (whiteList.indexOf(to.path) !== -1) {
//       // in the free login whitelist, go directly
//       next()
//     } else {
//       // other pages that do not have permission to access are redirected to the login page.
//       next(`/login?redirect=${to.path}`)
//       NProgress.done()
//     }
//   }
// })
//
// router.afterEach(() => {
//   // finish progress bar
//   NProgress.done()
// })

import router from './router'

//const whiteList = ['/user'] // no redirect whitelist
//用集合存储白名单 （不需要登录即可访问的页面）
const whiteSet=new Set();
whiteSet.add('/login');

//这个函数会在没有路由的时候被调用
//可以在里面控制是否允许他跳转
//to是一个对象 表示要访问的界面
//from从哪里来
//next是一个方法 两种用法 next（），不传参数表示放行  就是访问to指向的界面
// next（’、product、list‘） 里面给一个地址 表示访问固定界面
//前端有些界面是不需要登录就能访问的  比如登录页 注册页
router.beforeEach(async(to, from, next) => {
  //判断目标页面是否在白名单中 是就放行
  if(whiteSet.has(to.fullPath)){
    next();
  }else{
    //去localStroage中找有没有user
    if(localStorage.getItem('user')){
      next();
    }else{
      next('/login');
    }
  }
})
