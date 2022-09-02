<template>
  <div class="list">
    <!--类别下拉选择框-->
    <el-select v-model="categoryId" placeholder="请选择">
      <el-option-group
        v-for="parent in categoryList"
        :label="parent.name"
        :key="parent.id"
      >
        <el-option
          v-for="son in parent.sons"
          :label="son.name"
          :value="son.id"
          :key="son.id"
        >
        </el-option>
      </el-option-group>
    </el-select>

    <!--商品名模糊搜索-->
    商品名：<el-input v-model="name" placeholder="请输入商品名"></el-input>

    <!--价格输入-->
    价格：<el-input v-model="price" placeholder="请输入价格"></el-input>

    <!--按钮-->
    <el-button type="primary" @click="handleQuery">查询</el-button>
    <el-button type="danger" @click="handleDelete" v-show="role == 0"
      >删除</el-button
    >

    <!--表单-->
    <el-table
      v-loading="!iPage.records"
      :data="iPage.records"
      style="width: 100%"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" v-if="role == 0"> </el-table-column>
      <el-table-column prop="name" label="商品名"> </el-table-column>
      <el-table-column prop="subtitle" label="简介"> </el-table-column>
      <el-table-column prop="price" label="价格"> </el-table-column>
      <el-table-column prop="stock" label="库存"> </el-table-column>
      <el-table-column prop="status.statusMsg" label="状态"> </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button
            type="warning"
            @click="handleUpdateView(scope.row)"
            v-if="role == 0"
            >修改</el-button
          >
          <el-button
            type="warning"
            @click="handleCar(scope.row)"
            v-show="role == 1"
            >添加至购物车</el-button
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
  name: "list",
  data() {
    return {
      iPage: {},
      categoryList: [],
      categoryId: "",
      name: "",
      price: "",
      selectionId: [],
      role: "",
      userdata: "",
    };
  },
  created() {
    //会在组件加载时被调用
    this._initProductList();
    this._initCategoryList();
    this.userdata = JSON.parse(localStorage.getItem("user") || "{}");
    this.role = this.userdata.role;
  },
  methods: {
    _initProductList(pageNum = 1, pageSize = 10) {
      //在每一次请求数据之前iPage对象赋值一个空对象，目的是让表格进入加载状态
      this.iPage.records = null;
      const param = {
        pageNum,
        pageSize,
        categoryId: this.categoryId,
        name: this.name,
        price: this.price,
      };
      //发送ajax获取商品数据
      //get()方法发送get请求，传字符串形式的地址
      this.$axios
        .get("/api/product/listByDynamicPageDesc?" + qs.stringify(param))
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
    _initCategoryList() {
      this.$axios.get("/api/category/listByStruct").then((res) => {
        res = res.data;
        if (res.code == 0) {
          this.categoryList = res.data;
        }
      });
    },
    handleCurrentChange(pageNum) {
      //这个函数会在页码改变时被调用，并且传当前最新的页码，pageNum就是
      //能拿到页码，重新请求数据
      this._initProductList(pageNum);
    },
    handleQuery() {
      this._initProductList();
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
              "/api/product/deleteByIds?" +
                qs.stringify({ id: this.selectionId })
            )
            .then((res) => {
              res = res.data;
              if (res.code == 0) {
                //删除成功  刷新页面数据
                this._initProductList();
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
    handleCar(row) {
      let cartAddInfo = {
        productId: row.id,
        userId: this.userdata.id,
        quantity: 1,
        checked: 0,
      };
      //校验是否有选择
      this.$axios.post("/api/cart/cartadd", cartAddInfo).then((res) => {
        res = res.data;
        if (res.code == 0) {
          this.$message({
            type: "success",
            message: "添加成功",
          });
        } else {
          this.$message({
            type: "error",
            message: res.message,
          });
        }
      });
    },
    handleSelectionChange(selection) {
      //selection是被选中的数据的数组
      //数组里放的是所有原始数据的对象
      //将原始数组里的对象中的id取出来放一个新数组存到data中
      //这样delete就能访问数据
      let selectionId = [];
      for (let product of selection) {
        selectionId.push(product.id);
      }
      this.selectionId = selectionId;
    },
    handleUpdateView(product) {
      //知道商品id
      this.$router.push(`/product/update/${product.id}`);
    },
  },
};
</script>

<style scoped>
.list {
  padding: 20px;
}
.el-input {
  width: 200px;
  margin-right: 10px;
}
.el-select {
  margin-right: 10px;
}
</style>
