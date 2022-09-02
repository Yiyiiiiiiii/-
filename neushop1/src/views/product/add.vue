<template>
  <div class="add">
    <el-form ref="form" :model="product" label-width="80px" :rules="rules">
      <el-form-item label="类别">
        <el-select v-model="product.categoryId" placeholder="请选择">
          <el-option-group
            v-for="(parent, index) in categoryList"
            :key="index"
            :label="parent.name"
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
      </el-form-item>

      <el-form-item label="商品名" prop="name">
        <el-input v-model="product.name"></el-input>
      </el-form-item>

      <el-form-item label="简介">
        <el-input v-model="product.subtitle"></el-input>
      </el-form-item>

      <el-form-item label="主图">
        <el-upload
          class="avatar-uploader"
          action="/api/upload/imgUpload"
          :show-file-list="false"
          :on-success="handleMainImageSuccess"
        >
          <img
            v-if="product.mainImage"
            :src="product.mainImage"
            class="avatar"
          />
          <i v-else class="el-icon-plus avatar-uploader-icon"></i>
        </el-upload>
      </el-form-item>

      <el-form-item label="详情图">
        <el-upload
          action="/api/upload/imgUpload"
          list-type="picture-card"
          ref="subImages"
          :on-success="handleSubImagesSuccess"
        >
          <i slot="default" class="el-icon-plus"></i>
          <div slot="file" slot-scope="{ file }">
            <img
              class="el-upload-list__item-thumbnail"
              :src="file.url"
              alt=""
            />
            <span class="el-upload-list__item-actions">
              <span
                class="el-upload-list__item-preview"
                @click="handlePictureCardPreview(file)"
              >
                <i class="el-icon-zoom-in"></i>
              </span>

              <span
                class="el-upload-list__item-delete"
                @click="handleRemove(file)"
              >
                <i class="el-icon-delete"></i>
              </span>
            </span>
          </div>
        </el-upload>
        <el-dialog :visible.sync="dialogVisible">
          <img width="100%" :src="dialogImageUrl" alt="" />
        </el-dialog>
      </el-form-item>

      <el-form-item label="价格">
        <el-input v-model.number="product.price"></el-input>
      </el-form-item>

      <el-form-item label="库存">
        <el-input v-model.number="product.stock"></el-input>
      </el-form-item>

      <el-form-item label="状态">
        <el-radio v-model="product.status" :label="1">在售</el-radio>
        <el-radio v-model="product.status" :label="2">下架</el-radio>
      </el-form-item>

      <el-form-item label="详情">
        <div id="editor"></div>
      </el-form-item>
    </el-form>
    <el-button type="primary" @click="handleAdd">确定</el-button>
  </div>
</template>

<script>
import E from "wangeditor";
export default {
  name: "add",
  data() {
    return {
      editor: {},
      dialogVisible: false,
      dialogImageUrl: "",
      product: {
        mainImage: "",
        categoryId: "",
        status: 1,
        subImages: "",
      },
      categoryList: [],
      rules: {
        name: [{ required: true, message: "请输入商品名称", trigger: "blur" }],
      },
      radio: "1",
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
    onSubmit() {
      console.log("submit!");
    },
    _initCategoryList() {
      this.$axios.get("/api/category/listByStruct").then((res) => {
        res = res.data;
        if (res.code == 0) {
          this.categoryList = res.data;
        }
      });
    },
    handleMainImageSuccess(res, file) {
      if (res.code == 0) {
        this.product.mainImage = res.data;
      } else {
        this.$message({
          type: "error",
          message: res.message,
        });
      }
    },
    handlePictureCardPreview(file) {
      this.dialogImageUrl = file.url;
      this.dialogVisible = true;
    },
    handleRemove(file) {
      console.log(file);
      console.log(this.$refs.subImages.uploadFiles);
      for (let i = 0; i < this.$refs.subImages.uploadFiles.length; i++) {
        if (this.$refs.subImages.uploadFiles[i].uid == file.uid) {
          this.$refs.subImages.uploadFiles.splice(i, 1);
          //product对象里subImages属性上还记录着这个图片地址
          //要做字符串裁剪
          //拼接数组之前需要清理逗号
          this.product.subImages = this.product.subImages.substring(
            0,
            this.product.subImages.length - 1
          );
          //将subInages用split方法切成数组，把数组的指定位置删掉
          //删完之后在把数组拼接成字符串  设置回subImages上
          let subImagesArr = this.product.subImages.split(",");
          subImagesArr.splice(i, 1);
          this.product.subImages = "";
          for (let sub of subImagesArr) {
            this.product.subImages = this.product.subImages + sub + ",";
          }
          return;
        }
      }
    },
    _initEditor() {
      this.editor = new E("#editor");
      //进行设置
      this.editor.config.height = 600;
      //给富文本编辑器设置上传功能
      this.editor.config.customUploadImg = (resultFiles, insertImgFn) => {
        // resultFiles 是 input 中选中的文件列表
        // insertImgFn 是获取图片 url 后，插入到编辑器的方法

        //最好一个请求传多个文件
        for (let file of resultFiles) {
          let formData = new FormData();
          formData.append("file", file);
          this.$axios.post("/api/upload/imgUpload", formData).then((res) => {
            res = res.data;
            console.log(res);
            // 上传图片，返回结果，将图片插入到编辑器中
            insertImgFn(res.data);
          });
        }
      };
      this.editor.create();
    },
    handleAdd() {
      console.log("添加点击");
      //富文本编辑器需要自己手动获取
      this.product.detail = this.editor.txt.html();
      //裁剪掉subimages属性的最后一个逗号
      this.product.subImages = this.product.subImages.substring(
        0,
        this.product.subImages.length - 1
      );
      //前端校验
      //用if写判断
      if (!this.product.detail.trim()) {
        this.$message({
          type: "error",
          message: "详情不能为空",
        });
        return;
      }
      this.$axios.post("/api/product/add", this.product).then((res) => {
        res = res.data;
        if (res.code == 0) {
          //跳转到界面
          this.$router.push("/product/list");
        } else {
          this.$message({
            type: "error",
            message: res.message,
          });
        }
      });
    },
    handleSubImagesSuccess(res, file) {
      this.product.subImages = this.product.subImages + res.data + ",";
    },
  },
  created() {
    this._initCategoryList();
  },
  mounted() {
    this._initEditor();
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
</style>
