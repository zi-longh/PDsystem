<template>
  <div>
    <div class="card" style="padding: 40px; margin: 10px">
      <el-form :model="data.form" ref="formRef" label-width="100px" label-position="right"
               style="padding-right: 40px">
        <el-form-item label="学生账号" prop="username">
          <el-input v-model="data.form.username" autocomplete="off" disabled style="width: 300px"/>
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
          <el-input v-model="data.form.major" autocomplete="off" style="width: 300px"/>
        </el-form-item>
        <el-form-item label="从属院系">
          <el-input v-model="data.form.grade" autocomplete="off" style="width: 300px"/>
        </el-form-item>

        <el-form-item label="论文指导老师">
          <el-autocomplete
              v-model="data.form.instructor"
              :fetch-suggestions="querySearch"
              clearable
              class="inline-input w-50"
              placeholder="选择您的论文指导老师"
              @select="handleSelect"
              style="width: 300px"
          />
        </el-form-item>

        <el-form-item>
          <el-button type="info" @click="reset">恢复默认</el-button>
          <el-button type="primary" @click="update" style="margin-left: 80px;">保存并重新登录</el-button>
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
  // teacherName: '',
  form: {
    username: '',
    password: '',
    name: '',
    studentId: '',
    grade: '',
    major: '',
    department: '',
    instructor: '',
  },
  teacherData: []
})

const querySearch = (queryString, cb) => {
  const instructors = data.teacherData.map(teacher => {
    return {
      value: teacher.name + '（' + teacher.teacherId + '）',
    }
  })

  const results = queryString ? instructors.filter(instructor => instructor.value.toLowerCase().indexOf(queryString.toLowerCase()) > -1) : instructors;
  cb(results);
}

const handleSelect = (item) => {
  data.form.instructor = item.value
}

const loadInfo = () => {
  request.post('/getStudentInfoByUsername', {
        username: data.form.username
      }
  ).then(res => {
    if (res.code === '200') {
      ElMessage.success("已成功加载学生数据！")
      data.form.name = res.data.name
      data.form.studentId = res.data.studentId
      data.form.grade = res.data.grade
      data.form.major = res.data.major
      data.form.department = res.data.department
      data.form.instructor = res.data.instructor

      // 根据当前学生的指导老师instructor，在teacherData中找到对应的老师名字
      data.teacherData.forEach(teacher => {
        if (teacher.username === data.form.instructor) {
          data.form.instructor = teacher.name + '（' + teacher.teacherId + '）'
        }
      })

    } else {
      ElMessage.error("没有找到该学生信息！")
    }
  })





}

const reset = () => {
  loadInfo()
}

function init() {
  let accountData = JSON.parse(localStorage.getItem('account-user') || "{}")
  data.form.username = accountData.username
  data.form.password = accountData.password

  request.get('getAllTeacher').then(res => {
    if (res.code !== '200') {
      ElMessage.error(res.msg)
      return
    }
    data.teacherData = res.data
    ElMessage.success("已成功加载指导老师数据！")
  })

  loadInfo()
}

init()


const update = () => {
  request.post('/updateStudent', data.form).then(res => {
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