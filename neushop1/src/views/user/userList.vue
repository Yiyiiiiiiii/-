<template>
  <div class="userlist">
    <!--用户名模糊搜索-->
    用户名：<el-input v-model="username" placeholder="请输入用户名"></el-input>

    <!--手机号输入-->
    手机号：<el-input v-model="phone" placeholder="请输入手机号"></el-input>
    <!-- 邮箱 -->
    邮箱：<el-input v-model="email" placeholder="请输入邮箱"></el-input>

    <!--按钮-->
    <el-button type="primary" @click="handleQuery">查询</el-button>
    <el-button type="danger" @click="handleDelete">删除</el-button>

    <!--表单-->
    <el-table
      v-loading="!iPage.records"
      :data="iPage.records"
      style="width: 100%"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection"> </el-table-column>
      <el-table-column prop="username" label="用户名"> </el-table-column>
      <el-table-column prop="password" label="密码"> </el-table-column>
      <el-table-column prop="phone" label="手机号"> </el-table-column>
      <el-table-column prop="email" label="邮箱"> </el-table-column>
      <el-table-column prop="role.statusMsg" label="角色"> </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button type="warning" @click="handleUpdateView(scope.row)"
            >修改</el-button
          >
        </template>
      </el-table-column>
    </el-table>

    <!--页码-->
    <el-pagination
      background
      layout="prev, pager, next"
      :total="iPage.total"
      :page-size="iPage.size"
      @current-change="handleCurrentChange"
    >
    </el-pagination>
  </div>
</template>

<script>
import qs from "querystring";
export default {
  name: "userList",
  data() {
    return {
      iPage: {},
      username: "",
      phone: "",
      email: "",
      selectionId: [],
    };
  },
  created() {
    //会在组件加载时被调用
    this._initUserList();
  },
  methods: {
    _initUserList(pageNum = 1, pageSize = 10) {
      //在每一次请求数据之前iPage对象赋值一个空对象，目的是让表格进入加载状态
      this.iPage.records = null;
      const param = {
        pageNum,
        pageSize,
        username: this.username,
        phone: this.phone,
        email: this.email,
      };
      //发送ajax获取商品数据
      //get()方法发送get请求，传字符串形式的地址
      this.$axios
        .get("/api/user/userlistByDynamicPageDesc?" + qs.stringify(param))
        .then((res) => {
          //res是axios把服务器返回的数据又包装了一层
          //不想要包装这么写：
          res = res.data;
          if (res.code == 0) {
            this.iPage = res.data;
          } else {
            this.$message({
              type: "error",
              message: res.message,
            });
          }
        });
    },
    handleCurrentChange(pageNum) {
      //这个函数会在页码改变时被调用，并且传当前最新的页码，pageNum就是
      //能拿到页码，重新请求数据
      this._initUserList(pageNum);
    },
    handleQuery() {
      this._initUserList();
    },
    handleDelete() {
      //校验是否有选择
      if (this.selectionId.length == 0) {
        //提示未选中任何数据
        // this.$message集成了ElementUI
        this.$message({
          type: "error",
          message: "未选中任何数据,不能删除",
        });
        return; //这行代码意思是打断当前方法执行
      }

      //为了防止误操作 所以要给确认提示
      this.$confirm("此操作将永久删除数据, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          //这个函数在点击确定时被调用
          //执行删除逻辑,发送ajax把id带给后台
          //参数怎么弄
          this.$axios
            .get(
              "/api/user/userdeleteByIds?" +
                qs.stringify({ id: this.selectionId })
            )
            .then((res) => {
              res = res.data;
              if (res.code == 0) {
                //删除成功  刷新页面数据
                this._initUserList();
              } else {
                this.$message({
                  type: "error",
                  message: res.message,
                });
              }
            });
        })
        .catch(() => {
          //这个函数在点击取消时被调用
          //catch不能删
        });
    },
    handleSelectionChange(selection) {
      //selection是被选中的数据的数组
      //数组里放的是所有原始数据的对象
      //将原始数组里的对象中的id取出来放一个新数组存到data中
      //这样delete就能访问数据
      let selectionId = [];
      for (let user of selection) {
        selectionId.push(user.id);
      }
      this.selectionId = selectionId;
    },
    handleUpdateView(user) {
      //知道用户id
      this.$router.push(`/user/userUpdate/${user.id}`); //params   query
    },
  },
};
</script>

<style>
.userlist {
  padding: 20px;
}
.el-input {
  width: 200px;
  margin-right: 10px;
}
</style>