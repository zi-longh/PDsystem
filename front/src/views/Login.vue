<template>
  <div>
    <div class="login-container">
      <div style="width: 350px" class="login-box">
        <div style="font-weight: bold; font-size: 24px; text-align: center; margin-bottom: 30px">请登录</div>
        <el-form :model="data.form" ref="formRef" :rules="rules">
          <el-form-item prop="username">
            <el-input prefix-icon="User" v-model="data.form.username" placeholder="请输入账号" />
          </el-form-item>
          <el-form-item prop="password">
            <el-input show-password prefix-icon="Lock" v-model="data.form.password"  placeholder="请输入密码" />
          </el-form-item>
          <el-form-item prop="role">
            <el-select style="width: 100%" v-model="data.form.role">
              <el-option value="STUDENT" label="学生"></el-option>
              <el-option value="TEACHER" label="教师"></el-option>
              <el-option value="ADMIN" label="管理员"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" style="width: 100%" @click="login">登 录</el-button>
          </el-form-item>
        </el-form>
        <!--跳转到注册页面-->
        <div style="margin-top: 30px; font-size: small; text-align: center">
          还没有账号？请 <a href="/register">注册</a>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import {reactive, ref} from "vue"
import request from "@/utils/request";
import {ElMessage} from "element-plus";
import router from "@/router";


const data = reactive({
  form: {
    role: 'STUDENT'
  } // 默认角色为学生
})

const rules = reactive({
  username: [
    { required: true, message: '请输入账号', trigger: 'blur' },
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
  ],
})

const formRef = ref()

const login = () => {
  formRef.value.validate((valid) => {
    if (valid) {
      request.post('/login', data.form).then(res => {
        if (res.code === '200') {
          localStorage.setItem('account-user', JSON.stringify(res.data))  // 保存用户信息
          // 清空内容存储的templateId内容
          localStorage.removeItem('templateId')
          ElMessage.success('登录成功')
          console.log(res.data)
          router.push('/home') // 跳转到主页
        } else {
          ElMessage.error(res.msg)
        }
      })
    }
  })
}
</script>

<style scoped>
.login-container {
  min-height: 100vh;
  overflow: hidden;
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: #f0f2f5;   /*背景图案，设置背景为灰色;*/
  //background-image: url("@/assets/imgs/bg.png");
  //background-size: cover;
}
.login-box {
  background-color: #fff;
  box-shadow: 0 0 10px rgba(0,0,0,0.1);
  padding: 30px;
  border-radius: 20px;
}
</style>