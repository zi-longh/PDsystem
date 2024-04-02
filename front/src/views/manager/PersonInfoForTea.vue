<template>
  <div>
    <div class="card" style="padding: 40px; margin: 10px">
      <el-form :model="data.form" ref="formRef" label-width="100px" label-position="right"
               style="padding-right: 40px">
        <el-form-item label="账号名称" prop="username">
          <el-input v-model="data.form.username" autocomplete="off" disabled style="width: 500px"/>
        </el-form-item>
        <el-form-item label="账号密码" prop="password">
          <el-input show-password v-model="data.form.password" autocomplete="off" style="width: 500px"/>
        </el-form-item>
        <el-form-item label="教师名称">
          <el-input v-model="data.form.name" autocomplete="off" style="width: 500px"/>
        </el-form-item>
        <el-form-item label="教师工号">
          <el-input v-model="data.form.teacherId" autocomplete="off" style="width: 500px"/>
        </el-form-item>
        <el-form-item label="从属院系">
          <el-input v-model="data.form.department" autocomplete="off" style="width: 500px"/>
        </el-form-item>
        <el-form-item label="描述信息">
<!--          <el-input v-model="data.form.description" autocomplete="off" style="width: 500px"/>-->
          <el-input
              v-model="data.form.description"
              style="width: 500px"
              :autosize="{ minRows: 2, maxRows: 10 }"
              type="textarea"
              placeholder="Please input"
          />
        </el-form-item>

        <el-form-item>
          <el-button type="info" @click="reset">恢复默认</el-button>
          <el-button type="primary" @click="update" style="margin-left: 280px;">保存并重新登录</el-button>
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
import {ref} from "vue";


const data = reactive({
  form: {
    username: '',
    password: '',
    name: '',
    teacherId: '',
    department: '',
    description: ''
  }
})

const loadInfo = () => {
  data.form.username = user.username
  data.form.password = user.password
  request.post('/getTeacherInfoByUserName', {
        username: data.form.username
      }
  ).then(res => {
    if (res.code === '200') {
      ElMessage.success("已成功加载数据！")
      data.form.name = res.data.name
      data.form.teacherId = res.data.teacherId
      data.form.department = res.data.department
      data.form.description = res.data.description
    } else {
      ElMessage.error(res.msg)
    }
  })
}

const reset = () => {
  loadInfo()
}

const user = JSON.parse(localStorage.getItem('account-user') || "{}")
function init() {
  loadInfo()
}

init()


const update = () => {
  request.post('/updateTeacher', data.form).then(res => {
    if (res.code === '200') {
      request.post('/updatePassword', {
        username: data.form.username,
        password: data.form.password
      }).then(res => {
        if (res.code === '200') {
          ElMessage.success("信息修改成功，请重新登录！")
          router.push('/login')
        } else {
          ElMessage.error(res.msg)
        }
      })
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