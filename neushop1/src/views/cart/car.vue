<template>
  <div>
    <el-descriptions class="margin-top" :column="3" :size="size" border>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-user"></i>
          用户名
        </template>
        {{ userInfo.username }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-mobile-phone"></i>
          手机号
        </template>
        {{ userInfo.phone }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-location-outline"></i>
          居住地
        </template>
        苏州市
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-tickets"></i>
          备注
        </template>
        <el-tag size="small">学校</el-tag>
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-office-building"></i>
          联系地址
        </template>
        江苏省苏州市吴中区吴中大道 1188 号
      </el-descriptions-item>
    </el-descriptions>

    <el-table
      :data="tableData"
      border
      @selection-change="handleSelectionChange"
      style="width: 100%; margin-top: 20px"
      v-loading="loading"
    >
      <el-table-column type="selection"> </el-table-column>
      <el-table-column prop="name" label="商品名"> </el-table-column>
      <el-table-column prop="price" label="单价（元）"> </el-table-column>
      <el-table-column prop="quantity" label="数量">
        <template slot-scope="scope">
          <div class="inputDeep">
            <el-input v-model.number="scope.row.quantity" type="text" />
          </div>
        </template>
      </el-table-column>
      <el-table-column prop="sum" label="总价（元）">
        <template slot-scope="scope">
          {{ scope.row.quantity * scope.row.price }}
        </template>
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button type="primary" @click="handleUpdate(scope.row)"
            >保存</el-button
          >
        </template>
      </el-table-column>
    </el-table>
    <el-button type="danger" @click="handleDelete" style="margin: 10px"
      >删除</el-button
    >
    <el-button type="warning" @click="handlePay" style="margin: 10px"
      >结算</el-button
    >
    <el-dialog title="请扫码支付" :visible.sync="dialogVisible" width="30%">
      <span><img src="../../image/二维码.jpg" style="width: 95%" /></span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="handleClose">确 定</el-button>
      </span>
    </el-dialog>

    <!--页码-->
    <el-pagination
      background
      layout="prev, pager, next"
      :total="iPage.total"
      :page-size="iPage.size"
      @current-change="handleCurrentChange"
      style="float: right; margin: 10px"
    >
    </el-pagination>
  </div>
</template>

<script>
import qs from "querystring";
export default {
  name: "car",
  data() {
    return {
      iPage: {},
      size: "",
      userInfo: "",
      name: "",
      price: 0,
      quantity: 0,
      Tprice: "",
      selectionId: [],
      tableData: [],
      dialogVisible: false,
      loading: false,
    };
  },
  created() {
    //会在组件加载时被调用
    this._initCartList();
    this.userInfo = JSON.parse(localStorage.getItem("user") || "{}");
  },
  methods: {
    _initCartList(pageNum = 1, pageSize = 10) {
      const param = {
        pageNum,
        pageSize,
      };
      this.loading = true;
      //发送ajax获取商品数据
      //get()方法发送get请求，传字符串形式的地址
      this.$axios
        .get("/api/cart/cartlistByDynamicPageDesc?" + qs.stringify(param))
        .then((res) => {
          //res是axios把服务器返回的数据又包装了一层
          //不想要包装这么写：
          res = res.data;
          if (res.code == 0) {
            this.iPage = res.data;
            let showList = res.data.records.map((item) => {
              this.$axios
                .get(`/api/product/getById?id=${item.productId}`)
                .then((res) => {
                  if (res.data.code == 0) {
                    item.name = res.data.data.name;
                    item.price = res.data.data.price;
                  }
                });
              return item;
            });
            setTimeout(() => {
              this.tableData = showList;
              this.loading = false;
            }, 1000);
          } else {
            this.$message({
              type: "error",
              message: res.message,
            });
            this.loading = false;
          }
        });
    },
    handleCurrentChange(pageNum) {
      //这个函数会在页码改变时被调用，并且传当前最新的页码，pageNum就是
      //能拿到页码，重新请求数据
      this._initUserList(pageNum);
    },
    handleUpdate(row) {
      let obj = { ...row, checked: 1 };
      this.$axios.post("/api/cart/cartupdateByIdAllArgs", obj).then((res) => {
        res = res.data;
        if (res.code == 0) {
          this._initCartList();
        } else {
          this.$message({
            type: "error",
            message: res.message,
          });
        }
      });
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
              "/api/cart/cartdeleteByIds?" +
                qs.stringify({ id: this.selectionId })
            )
            .then((res) => {
              res = res.data;
              if (res.code == 0) {
                //删除成功  刷新页面数据
                this._initCartList();
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
    handlePay() {
      if (this.selectionId.length == 0) {
        //提示未选中任何数据
        // this.$message集成了ElementUI
        this.$message({
          type: "error",
          message: "未选中任何数据,不能支付",
        });
        return; //这行代码意思是打断当前方法执行
      } else {
        this.dialogVisible = true;
      }
    },
    handleClose() {
      //这个函数在点击确定时被调用
      //执行删除逻辑,发送ajax把id带给后台
      //参数怎么弄
      this.$axios
        .get(
          "/api/cart/cartdeleteByIds?" + qs.stringify({ id: this.selectionId })
        )
        .then((res) => {
          res = res.data;
          if (res.code == 0) {
            //删除成功  刷新页面数据
            this._initCartList();
          } else {
            this.$message({
              type: "error",
              message: res.message,
            });
          }
        });
      this.dialogVisible = false;
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
  },
};
</script>

<style scoped>
/* 利用穿透，设置input边框隐藏 */
.inputDeep >>> .el-input__inner {
  border: 0;
}
</style>

