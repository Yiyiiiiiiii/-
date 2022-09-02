<template>
  <div>
    <el-table
      :data="categoryList"
      style="width: 100%; margin-bottom: 20px"
      row-key="id"
      border
      :tree-props="{ children: 'sons' }"
    >
      <el-table-column prop="id" label="ID" width="250"> </el-table-column>
      <el-table-column prop="name" label="类别名称" width="400">
      </el-table-column>
      <el-table-column prop="status" label="状态" width="250">
        <template slot-scope="scope">
          {{ scope.row.status == 1 ? "正常" : "废弃" }}
        </template>
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button type="warning" @click="handleUpdate(scope.row)"
            >修改</el-button
          >
          <el-button type="danger" @click="handleDelete(scope.row)"
            >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>
    <el-dialog title="类别" :visible.sync="dialogFormVisible" width="600px">
      <el-form :model="form">
        <el-form-item label="类别名称" :label-width="formLabelWidth">
          <el-input
            v-model="form.name"
            autocomplete="off"
            style="width: 206px"
          ></el-input>
        </el-form-item>
        <el-form-item label="状态" :label-width="formLabelWidth">
          <el-select v-model="form.status" placeholder="请选择是否使用">
            <el-option label="启用" :value="1"></el-option>
            <el-option label="废弃" :value="2"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="handleStatus">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      categoryList: [],
      categoryId: "",
      dialogFormVisible: false,
      form: {
        name: "",
        status: "",
      },
      formLabelWidth: "200px",
    };
  },
  created() {
    this._initCategoryList();
  },
  methods: {
    handleUpdate(row) {
      this.form = row;
      this.dialogFormVisible = true;
    },
    _initCategoryList() {
      this.$axios.get("/api/category/listByStruct").then((res) => {
        res = res.data;
        if (res.code == 0) {
          this.categoryList = res.data;
        }
      });
    },
    handleStatus() {
      this.dialogFormVisible = false;
      this.$axios
        .post("/api/category/categoryupdateByIdAllArgs", this.form)
        .then((res) => {
          res = res.data;
          if (res.code == 0) {
            console.log(res);
          } else {
            this.$message({
              type: "error",
              message: res.message,
            });
          }
        });
    },
    handleDelete(row) {
      this.$confirm("此操作将永久删除数据, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(
        this.$axios
          .get(`/api/category/categorydeleteByIds?id=${row.id}`)
          .then((res) => {
            res = res.data;
            if (res.code == 0) {
              //删除成功  刷新页面数据
              this._initCategoryList();
            } else {
              this.$message({
                type: "error",
                message: res.message,
              });
            }
          })
      );
    },
  },
};
</script>

<style>
</style>