import Vue from "vue";
import Router from "vue-router";

Vue.use(Router);

/* Layout */
import Layout from "@/layout";
let userdata = JSON.parse(localStorage.getItem("user") || "{}");
let role = userdata.role;
/*
 * hidden:true表示不显示在菜单中
 * 没有hidden属性并且提供了meta元信息的都是显示在菜单中的
 * meta中的title是菜单文字，icon是图标名*/
export const constantRoutes = [
  {
    path: "/login",
    component: () => import("@/views/user/login"),
    hidden: true,
  },
  {
    path: "/register",
    name: "Register",
    component: () => import("@/views/user/register"),
  },

  {
    path: "/404",
    component: () => import("@/views/404"),
    hidden: true,
  },

  {
    path: "/",
    component: Layout,
    redirect: "/login",
    hidden: true,
    children: [
      {
        path: "login",
        name: "login",
        component: () => import("@/views/login/index"),
        meta: { title: "login", icon: "login" },
      },
    ],
  },

  {
    path: "/product",
    component: Layout,
    redirect: "/product/list",
    name: "Product",
    meta: { title: "商品管理", icon: "el-icon-shopping-bag-1" },
    children: [
      {
        path: "list",
        name: "List",
        component: () => import("@/views/product/list"),
        meta: { title: "商品列表", icon: "el-icon-shopping-bag-2" },
      },
      {
        path: "add",
        name: "Add",
        component: () => import("@/views/product/add"),
        meta: { title: "添加商品", icon: "el-icon-sold-out" },
        hidden: role == 1,
      },
      {
        path: "update/:id",
        name: "Update",
        component: () => import("@/views/product/update"),
        hidden: role == 1,
      },
    ],
  },
  {
    path: "/user",
    component: Layout,
    redirect: "/user/userList",
    name: "User",
    meta: { title: "用户管理", icon: "el-icon-s-custom" },
    children: [
      {
        path: "userList",
        name: "UserList",
        component: () => import("@/views/user/userList"),
        meta: { title: "用户列表", icon: "el-icon-user" },
        hidden: role == 1,
      },
      {
        path: "userUpdate/:id",
        name: "UserUpdate",
        component: () => import("@/views/user/userUpdate"),
        meta: { title: "修改用户", icon: "el-icon-user-solid" },
        hidden: role == 0,
      },
    ],
  },

  {
    path: "/cart",
    component: Layout,
    meta: { title: "购物车", icon: "el-icon-shopping-cart-full" },
    hidden: role == 0,
    children: [
      {
        path: "car",
        name: "Car",
        component: () => import("@/views/cart/car"),
        meta: { title: "购物车", icon: "el-icon-s-goods" },
      },
    ],
  },
  {
    path: "/shipping",
    component: Layout,
    redirect: "/shipping/shippingList",
    name: "shipping",
    meta: { title: "收货地址管理", icon: "el-icon-truck" },
    children: [
      {
        path: "shippingList",
        name: "shippingList",
        component: () => import("@/views/shipping/shippingList"),
        meta: { title: "收货地址", icon: "el-icon-location" },
      },
      {
        path: "shippingAdd",
        name: "shippingAdd",
        component: () => import("@/views/shipping/shippingAdd"),
        meta: { title: "增加收货地址", icon: "el-icon-add-location" },
        hidden: role == 0,
      },
      {
        path: "shippingUpdate/:id",
        name: "shippingUpdate",
        component: () => import("@/views/shipping/shippingUpdate"),
        hidden: true,
      },
    ],
  },

  {
    path: "/category",
    component: Layout,
    redirect: "/category/category",
    name: "category",
    hidden: role == 1,
    children: [
      {
        path: "categoryinfo",
        name: "categoryinfo",
        component: () => import("@/views/category/category"),
        meta: { title: "类别管理", icon: "el-icon-s-order" },
      },
    ],
  },

  // 404 page must be placed at the end !!!
  { path: "*", redirect: "/404", hidden: true },
];

const createRouter = () =>
  new Router({
    // mode: 'history', // require service support
    scrollBehavior: () => ({ y: 0 }),
    routes: constantRoutes,
  });

const router = createRouter();

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
  const newRouter = createRouter();
  router.matcher = newRouter.matcher;
}

export default router;
