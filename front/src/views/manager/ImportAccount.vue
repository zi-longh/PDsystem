<template>
  <div>
    <div class="card" style="margin-bottom: 10px">
      <p style="margin: 5px;">
        <span style="font-size: 24px; font-weight: bold">手动创建账号 </span>
      </p>
    <!-- 输入账号名、账号密码、选择账号类别、姓名、学号创建账号 -->
      <p style="margin-left: 4px">
        <span style="margin-left: 5px">输入账号名称：</span>
        <el-input v-model="inputData.inputForChinese" style="width: 335px" placeholder="请输入账号名称" clearable/>
      </p>
      <p style="margin-left: 4px">
        <span style="margin-left: 5px">输入账号密码：</span>
        <el-input v-model="inputData.inputForEnglish" style="width: 335px" placeholder="请输入账号密码" clearable/>
      </p>
      <p style="margin-left: 4px">
        <span style="margin-left: 5px">选择账号类别：</span>
        <el-select v-model="inputData.selectedRole" style="width: 335px" placeholder="请选择账号类别">
          <el-option value="STUDENT" label="学生账号"></el-option>
          <el-option value="TEACHER" label="教师账号"></el-option>
        </el-select>
      </p>
      <p style="margin-left: 4px">
        <span style="margin-left: 5px">输入账号姓名：</span>
        <el-input v-model="inputData.inputForName" style="width: 335px" placeholder="请输入姓名" clearable/>
      </p>
      <p style="margin-left: 4px">
        <span style="margin-left: 5px">学号或者工号：</span>
        <el-input v-model="inputData.inputForId" style="width: 335px" placeholder="学号或者工号" clearable/>
      </p>
      <p style="margin-left: 4px">
        <el-button type="primary" style="margin-left: 120px" @click="createAccount">创建账号</el-button>
        <el-button type="info" style="margin-left: 155px" @click="reset">重置输入</el-button>
      </p>
    </div>

    <div class="card" style="margin-bottom: 10px">
      <p style="margin: 5px;">
        <span style="font-size: 24px; font-weight: bold">从文件导入账号 </span>
      </p>
      <p style="margin: 5px;">请导入如下格式的excel文件，格式不正确的文件系统将无法识别！</p>
      <!-- 插入图片，比例缩小 -->
      <img src="@/assets/imgs/excel.png" alt="excel" style="width: 70%; height: 70%">
      <p style="margin-left: 4px">
        <el-upload
            ref="uploadFile"
            v-model:file-list="fileList"
            class="upload-demo"
            action="http://localhost:9090/files/upload"
            :limit="1"
            :on-exceed="handleExceed"
            :auto-upload="false"
            :on-success="handleSuccess"
        >
          <template #trigger>
            <el-button type="primary">点击上传文件</el-button>
          </template>
          <template #tip>
            <div class="el-upload__tip text-red">
              请上传符合格式要求的excel文件，支持扩展名：.xls .xlsx
            </div>
          </template>

          <el-button class="ml-3" type="primary" color="#626aef" @click="submitAndDetect(inputData)"
                     style="margin-left: 20px; height: 33px; inline-size: auto">
            开始导入账号
          </el-button>

        </el-upload>
      </p>

    </div>
  </div>
</template>

<script lang="ts" setup>
import {reactive} from "vue";
import request from "@/utils/request";
import router from "@/router";
import {ElMessageBox, ElMessage} from "element-plus";
import {Search} from "@element-plus/icons-vue";

const tempData  = reactive(
    [
      {
        username: 'student1',
        role: '学生账号',
        name: '黄子龙',
        id: '2020101634'
      },
      {
        username: 'student2',
        role: '学生账号',
        name: '张三',
        id: '2020101635'
      },
      {
        username: 'student3',
        role: '学生账号',
        name: '李四',
        id: '2020101636'
      },
      {
        username: 'student4',
        role: '学生账号',
        name: '王五',
        id: '2020101637'
      },
      {
        username: 'student5',
        role: '学生账号',
        name: '赵六',
        id: '2020101638'
      },
      {
        username: 'student6',
        role: '学生账号',
        name: '孙七',
        id: '2020101639'
      },
      {
        username: 'student7',
        role: '学生账号',
        name: '周八',
        id: '2020101640'
      },
      {
        username: 'student8',
        role: '学生账号',
        name: '吴九',
        id: '2020101641'
      },
      {
        username: 'student9',
        role: '学生账号',
        name: '郑十',
        id: '2020101642'
      },
      {
        username: 'teacher1',
        role: '教师账号',
        name: '张三',
        id: '201123'
      },
      {
        username: 'teacher2',
        role: '教师账号',
        name: '李四',
        id: '201124'
      },
      {
        username: 'teacher3',
        role: '教师账号',
        name: '王五',
        id: '201125'
      },
      {
        username: 'teacher4',
        role: '教师账号',
        name: '赵六',
        id: '201126'
      },
      {
        username: 'teacher5',
        role: '教师账号',
        name: '孙七',
        id: '201127'
      },
      {
        username: 'teacher6',
        role: '教师账号',
        name: '周八',
        id: '201128'
      },
      {
        username: 'teacher7',
        role: '教师账号',
        name: '吴九',
        id: '201129'
      },
      {
        username: 'teacher8',
        role: '教师账号',
        name: '郑十',
        id: '201130'
      },

    ]
);


const data = reactive({
  selectedRole: 'unlimited',
  inputUsername: '',
  info: []

});

const inputData = reactive({
  inputForChinese: '',
  inputForEnglish: '',
  selectedRole: '',
  inputForName: '',
  inputForId: ''
});

const load = () => {


};
load();  // 页面加载时加载数据

const reset = () => {
  data.selectedRole = 'unlimited';
  data.inputUsername = '';
  load();
};



</script>