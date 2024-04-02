<template>
  <div>
    <div class="card" style="margin-bottom: 10px">
      <p style="margin: 5px;">
        <span style="font-size: 24px; font-weight: bold">学生提交的论文 </span>
        <el-button type="primary" style="margin-left: 10px; margin-bottom: 10px" @click="load">刷新</el-button>
      </p>
      <p style="margin: 5px;">以下是学生提交的论文，可在操作栏中下载学生论文PDF版本和docx版本。</p>
    </div>

    <div class="card" style="margin-bottom: 10px">
      <div>
        <el-table
            :data="data.records"
            :default-sort="{ prop: 'update_time', order: 'descending' }"
            style="width: 100%" height="620">

          <el-table-column  label="论文标题" sortable>
          <template #default="scope">
            {{ scope.row.paperName}} <br/>
            {{ scope.row.paperEnglishName}}
          </template></el-table-column>
          <el-table-column  label="学生姓名" sortable width="120px">
            <template #default="scope">
              {{ data.students.find(item => item.username === scope.row.username).name}}
            </template>
          </el-table-column>
          <el-table-column  label="学号" sortable width="120px">
            <template #default="scope">
              {{ data.students.find(item => item.username === scope.row.username).studentId }}
            </template>
          </el-table-column>
          <el-table-column  label="年级专业" sortable width="140px">
            <template #default="scope">
              {{ data.students.find(item => item.username === scope.row.username).grade }}
              {{ data.students.find(item => item.username === scope.row.username).major }}
            </template>
          </el-table-column>
          <el-table-column prop="templateId" label="检测模板" sortable width="250px" />
          <el-table-column prop="detectTime" label="检测时间" sortable width="150px"/>
          <el-table-column prop="status" label="检测结果" sortable width="120px">
            <template #default="scope">
              <el-tag>{{ scope.row.status }}</el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="isSendToTeacher" label="发送导师" width="100px" v-if="user.role === 'STUDENT'">
            <template #default="scope">
              <el-tag>{{ scope.row.isSendToTeacher }}</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="下载检测报告" width="280">
            <template #default="scope">
              <el-button type="primary" @click="downloadDocx(scope)" v-if="scope.row.status != '未知状态'">
                下载检测报告
              </el-button>
              <el-button type="primary" @click="downloadPDF(scope)"
                         v-if="scope.row.status === '检测通过(可修改)' || scope.row.status === '检测通过'">下载PDF
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>

    </div>

  </div>
</template>

<script lang="ts" setup>
import {reactive} from "vue";
import request from "@/utils/request";
import {ElMessage} from "element-plus";

interface DetectRecord {
  recordId: string
  username: string
  templateId: string
  detectTime: string
  status: string
  paperName: string
  paperEnglishName: string
  resultFileName: string
  resultPDF: string
  isSendToTeacher: string
  teacherUsername: string
}

const data = reactive({
  records: [],
  students: []
});
const user = JSON.parse(localStorage.getItem('account-user') || '{}')
const load = () => {
  const username = user.username;
  request.post('/getPapersOfStu',{
    username: username
  }).then(res => {
    data.records = res.data;
  });

  request.get('/getAllStudentsInfo').then(res => {
    data.students = res.data;
  });
};
load();  // 页面加载时加载数据

const downloadDocx = (scope) => {
  // 下载docx文件原件
  // 在新窗口打开下载链接
  const filename = scope.row.resultPDF.split('.pdf')[0] + '.docx';
  window.open('http://localhost:9090/files/downloadPDF?fileName=' + filename)
}

const downloadPDF = (scope) => {
  // 下载pdf文件
  // 在新窗口打开下载链接
  window.open('http://localhost:9090/files/downloadPDF?fileName=' + scope.row.resultPDF)
}


</script>