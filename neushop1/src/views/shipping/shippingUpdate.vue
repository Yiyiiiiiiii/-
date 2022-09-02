<template>
  <div class="userUpdate">
    <el-form ref="form" :model="shipping" label-width="80px">
      <el-form-item label="收件名">
        <el-input v-model="shipping.receiverName"></el-input>
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
    <el-button type="primary" @click="handleUpdate" style="margin-left: 200px"
      >确定</el-button
    >
  </div>
</template>

<script>
export default {
  name: "userUpdate",
  data() {
    return {
      id: "",
      userId: "",
      shipping: {
        receiverName: "",
        receiverProvince: "",
        receiverCity: "",
        receiverDistrict: "",
        receiverAddress: "",
        receiverZip: "",
      },
      oUser: "",
    };
  },
  created() {
    this.id = this.$route.params.id;
    this._initShipping();
  },
  methods: {
    _initShipping() {
      this.$axios
        .get(`/api/shipping/shippinggetById?id=${this.id}`)
        .then((res) => {
          res = res.data;
          console.log(res);
          if (res.code == 0) {
            this.shipping = res.data;
          } else {
            this.$message({
              type: "error",
              message: res.message,
            });
          }
        });
    },
    handleUpdate() {
      //将数据提交到后端
      this.$axios
        .post("/api/shipping/shippingupdateByIdAllArgs", this.shipping)
        .then((res) => {
          res = res.data;
          if (res.code == 0) {
            this.$message({
              type: "success",
              message: "修改成功",
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