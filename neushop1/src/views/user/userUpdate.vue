<template>
  <div class="Update">
    <el-form ref="form" :model="user" label-width="80px">
      <el-form-item label="角色">
        <el-radio :disabled="userData.role == 1" v-model="user.role" :label="0"
          >管理员</el-radio
        >
        <el-radio :disabled="userData.role == 1" v-model="user.role" :label="1"
          >普通用户</el-radio
        >
      </el-form-item>
      <el-form-item label="头像">
        <el-upload
          class="avatar-uploader"
          action="/api/upload/imgUpload"
          :show-file-list="false"
          :on-success="handleHeadImg"
        >
          <img v-if="user.headImg" :src="user.headImg" class="avatar" />
          <i v-else class="el-icon-plus avatar-uploader-icon"></i>
        </el-upload>
      </el-form-item>

      <el-form-item label="用户名" prop="username">
        <el-input v-model="user.username"></el-input>
      </el-form-item>

      <el-form-item label="密码" prop="password">
        <el-input v-model="user.password"></el-input>
      </el-form-item>

      <el-form-item label="手机号">
        <el-input v-model="user.phone"></el-input>
      </el-form-item>

      <el-form-item label="邮箱">
        <el-input v-model="user.email"></el-input>
      </el-form-item>
    </el-form>
    <el-button type="primary" @click="handleUpdate">确定</el-button>
  </div>
</template>

<script>
export default {
  name: "userupdate",
  data() {
    return {
      id: "",
      user: {
        role: "",
        username: "",
        password: "",
        phone: "",
        email: "",
      },
      userData: "",
    };
  },
  created() {
    this.userData = JSON.parse(localStorage.getItem("user") || "{}");
    if (this.userData.role == 1) {
      this.id = this.userData.id;
    } else {
      this.id = this.$route.params.id;
    }
    this._initUser();
  },
  methods: {
    handleHeadImg(res) {
      if (res.code == 0) {
        this.user.headImg = res.data;
      } else {
        this.$message({
          type: "error",
          message: res.message,
        });
      }
    },
    _initUser() {
      if (this.user.role == 0) {
        this.$axios.get(`/api/user/usergetById?id=${this.id}`).then((res) => {
          res = res.data;
          console.log(res);
          if (res.code == 0) {
            res.data.role = res.data.role.statusCode;
            this.user = res.data;
          } else {
            this.$message({
              type: "error",
              message: res.message,
            });
          }
        });
      }
    },
    handleUpdate() {
      //将数据提交到后端
      this.$axios
        .post("/api/user/userupdateByIdAllArgs", this.user)
        .then((res) => {
          res = res.data;
          if (res.code == 0) {
            this.$message({
              type: "success",
              message: "修改成功",
            });
            //跳转列表页
            if (this.user.role == 0) {
              this.$router.push("/user/userList");
            }
            this._initUser();
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
</style>
