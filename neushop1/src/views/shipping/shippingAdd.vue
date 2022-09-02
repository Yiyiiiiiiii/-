<template>
  <div class="shippingAdd">
    <el-form ref="form" :model="shipping" label-width="80px">
      <el-form-item label="用户名">
        {{ username }}
      </el-form-item>
      <el-form-item label="用户ID" hidden>
        <el-input v-model="shipping.userId"></el-input>
      </el-form-item>
      <el-form-item label="收件名">
        <el-input v-model="shipping.receiverName"></el-input>
      </el-form-item>

      <el-form-item label="电话">
        <el-input v-model="shipping.receiverPhone"></el-input>
      </el-form-item>
      <el-form-item label="省份">
        <el-input v-model="shipping.receiverProvince"></el-input>
      </el-form-item>

      <el-form-item label="市区">
        <el-input v-model="shipping.receiverCity"></el-input>
      </el-form-item>

      <el-form-item label="区/县">
        <el-input v-model="shipping.receiverDistrict"></el-input>
      </el-form-item>
      <el-form-item label="详细地址">
        <el-input v-model="shipping.receiverAddress"></el-input>
      </el-form-item>
      <el-form-item label="邮编">
        <el-input v-model="shipping.receiverZip"></el-input>
      </el-form-item>
    </el-form>
    <el-button type="primary" @click="handleAdd" style="margin-left: 200px"
      >添加</el-button
    >
  </div>
</template>

<script>
export default {
  name: "shippingAdd",
  data() {
    return {
      shipping: {
        userId: "",
        receiverName: "",
        receiverPhone: "",
        receiverProvince: "",
        receiverCity: "",
        receiverDistrict: "",
        receiverAddress: "",
        receiverZip: "",
      },
      userdata: "",
    };
  },
  created() {
    this.userdata = JSON.parse(localStorage.getItem("user") || "{}");
    this.userId = this.userdata.id;
    this.username = this.userdata.username;
    this.shipping.userId = this.userId;
  },
  methods: {
    handleAdd() {
      //将数据提交到后端
      this.$axios
        .post("/api/shipping/shippingadd", this.shipping)
        .then((res) => {
          res = res.data;
          if (res.code == 0) {
            this.$message({
              type: "success",
              message: "添加成功",
            });
            //跳转列表页
            this.$router.push("/shipping/shippingList");
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

<style scoped>
.avatar-uploader >>> .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader >>> .el-upload:hover {
  border-color: #409eff;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}
.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
.el-input >>> .el-input__inner {
  width: 400px;
}
</style>