<template>
  <div>
    <el-table :data="tableData" style="width: 100%">
      <el-table-column prop="userId" label="用户" width="150">
        {{ username }}
      </el-table-column>
      <el-table-column label="配送信息">
        <el-table-column prop="receiverName" label="收件姓名" width="120">
        </el-table-column>
        <el-table-column label="地址">
          <el-table-column prop="receiverProvince" label="省份" width="120">
          </el-table-column>
          <el-table-column prop="receiverCity" label="市区" width="120">
          </el-table-column>
          <el-table-column prop="receiverDistrict" label="区/县" width="120">
          </el-table-column>
          <el-table-column prop="receiverAddress" label="详细地址" width="300">
          </el-table-column>
          <el-table-column prop="receiverZip" label="邮编" width="120">
          </el-table-column>
        </el-table-column>
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button type="primary" @click="handleUpdate(scope.row)"
            >修改</el-button
          >
          <el-button type="danger" @click="handleDelete(scope.row)"
            >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
export default {
  data() {
    return {
      id: "",
      tableData: [],
      userdata: "",
    };
  },
  created() {
    this.userdata = JSON.parse(localStorage.getItem("user") || "{}");
    this.role = this.userdata.role;
    this.username = this.userdata.username;
    this._initShipping();
  },
  methods: {
    _initShipping() {
      if (this.role == 0) {
        this.$axios
          .get("/api/shipping/shippinglistByDynamicPageDesc")
          .then((res) => {
            //res是axios把服务器返回的数据又包装了一层
            //不想要包装这么写：
            res = res.data;
            if (res.code == 0) {
              this.tableData = res.data.records;
            } else {
              this.$message({
                type: "error",
                message: res.message,
              });
            }
          });
      } else {
        this.tableData = [];
        this.$axios
          .get(
            `/api/shipping/shippinglistByDynamicPageDesc?userId=${this.userdata.id}`
          )
          .then((res) => {
            //res是axios把服务器返回的数据又包装了一层
            //不想要包装这么写：
            res = res.data;
            if (res.code == 0) {
              this.tableData = res.data.records;
            } else {
              this.$message({
                type: "error",
                message: res.message,
              });
            }
          });
      }
    },
    handleUpdate(shipping) {
      this.$router.push(`/shipping/shippingUpdate/${shipping.id}`);
    },
    handleDelete(row) {
      this.$axios
        .get(`/api/shipping/shippingdeleteByIds?id=${row.id}`)
        .then((res) => {
          res = res.data;
          if (res.code == 0) {
            //删除成功  刷新页面数据
            this._initShipping();
          } else {
            this.$message({
              type: "error",
              message: res.message,
            });
          }
        });
    },
  },
};
</script>

<style>
</style>