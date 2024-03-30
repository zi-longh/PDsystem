<template>
  <div>
    <div class="card" style="padding: 40px; margin: 10px" >
      <el-form :model="data.form" ref="formRef" label-width="100px" label-position="right"
               style="padding-right: 40px">
        <el-form-item label="学生账号" prop="username" >
          <el-input v-model="data.form.username" autocomplete="off" disabled style="width: 300px" />
        </el-form-item>
        <el-form-item label="学生密码" prop="password">
          <el-input show-password v-model="data.form.password" autocomplete="off" style="width: 300px"/>
        </el-form-item>
        <el-form-item label="学生名称">
          <el-input v-model="data.form.name" autocomplete="off" style="width: 300px"/>
        </el-form-item>
        <el-form-item label="学生学号">
          <el-input v-model="data.form.studentId" autocomplete="off" style="width: 300px"/>
        </el-form-item>
        <el-form-item label="年级">
          <el-input v-model="data.form.grade" autocomplete="off" style="width: 300px"/>
        </el-form-item>
        <el-form-item label="专业">
          <el-input v-model="data.form.grade" autocomplete="off" style="width: 300px"/>
        </el-form-item>
        <el-form-item label="从属院系">
          <el-input v-model="data.form.grade" autocomplete="off" style="width: 300px"/>
        </el-form-item>

        <el-form-item label="论文指导老师">
          <el-autocomplete
              v-model="state1"
              :fetch-suggestions="querySearch"
              clearable
              class="inline-input w-50"
              placeholder="选择您的论文指导老师"
              @select="handleSelect"
              style="width: 300px"
          />
        </el-form-item>



        <el-form-item>
          <el-button type="primary" @click="update">保 存</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script setup>
import {reactive} from "vue";
import request from "@/utils/request";
import {ElMessage} from "element-plus";
import router from "@/router";
import {Plus} from "@element-plus/icons-vue"

const data = reactive({
  form: {
    username: '',
    password: '',
    name:'',
    studentId: '',
    grade: '',
    major: '',
    department: '',
    instructor: '',
  }
})



function init() {
  let accountData = JSON.parse(localStorage.getItem('account-user') || "{}")
  data.form.username = accountData.username
  data.form.password = accountData.password
}
init()
const handleImgUploadSuccess = (res) => {
  data.form.avatar = res.data
}

const update = () => {
  request.put('/student/update', data.form).then(res => {
    if (res.code === '200') {
      ElMessage.success("操作成功")
      router.push('/login')
    } else {
      ElMessage.error(res.msg)
    }
  })
}

</script>




<style>
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 100px;
  height: 100px;
  line-height: 100px;
  text-align: center;
}
.avatar {
  width: 100px;
  height: 100px;
  display: block;
}
</style>