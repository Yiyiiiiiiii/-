<template>
  <div class="add">
    <el-form ref="form" :model="user" label-width="80px" :rules="rules">
      <el-form-item label="角色">
        <el-radio v-model="user.role" :label="0">管理员</el-radio>
        <el-radio v-model="user.role" :label="1">普通用户</el-radio>
      </el-form-item>
      <el-form-item label="用户名" prop="username">
        <el-input v-model="user.username" placeholder="请输入用户名"></el-input>
      </el-form-item>

      <el-form-item label="密码" prop="password" style="width: 380px">
        <el-input v-model="user.password" placeholder="请输入密码" />
      </el-form-item>

      <el-form-item label="手机号">
        <el-input
          v-model.number="user.phone"
          placeholder="请输入手机号"
        ></el-input>
      </el-form-item>

      <el-form-item label="邮箱">
        <el-input v-model="user.email" placeholder="请输入邮箱"></el-input>
      </el-form-item>
    </el-form>
    <el-button type="primary" @click="handleRegister" style="margin-left: 200px"
      >确定</el-button
    >
  </div>
</template>

<script>
export default {
  name: "register",
  data() {
    return {
      user: {
        role: 0,
        username: "",
        password: "",
        email: "",
        phone: "",
      },
      rules: {
        name: [{ required: true, message: "请输入用户名", trigger: "blur" }],
      },
      radio: "0",
      form: {
        name: "",
        region: "",
        date1: "",
        date2: "",
        delivery: false,
        type: [],
        resource: "",
        desc: "",
      },
    };
  },
  methods: {
    handleRegister() {
      this.$axios.post("/api/user/useradd", this.user).then((res) => {
        res = res.data;
        if (res.code == 0) {
          this.$message({
            type: "success",
            message: "注册成功",
          });
          this.$router.push("/login");
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
.add {
  padding: 20px;
}
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
  width: 300px;
}
.svg-container {
  padding: 6px 5px 6px 15px;
  vertical-align: middle;
  width: 30px;
  display: inline-block;
}
</style>
