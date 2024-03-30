<template>
  <div>
    <div class="card" style="margin-bottom: 10px">
      <p style="margin: 5px;">
        <span style="font-size: 24px; font-weight: bold">论文检测历史 </span>
        <el-button type="primary" style="margin-left: 10px; margin-bottom: 10px" @click="load">刷新</el-button>
      </p>
      <p style="margin: 5px;">以下是您的论文检测记录，可在操作栏中下载当时的检测报告，或删除记录。</p>
    </div>

    <div class="card" style="margin-bottom: 10px">
      <div>
        <el-table
            :data="data.records"
            :default-sort="{ prop: 'update_time', order: 'descending' }"
            style="width: 100%" height="620">
          <el-table-column prop="paperName" label="论文中文标题" sortable />
          <el-table-column prop="paperEnglishName" label="论文英文标题" sortable/>
          <el-table-column prop="templateId" label="检测模板" sortable/>
          <el-table-column prop="detectTime" label="检测时间" sortable/>
          <el-table-column prop="status" label="检测结果" sortable/>
          <el-table-column prop="isSentToTeacher" label="发送导师"/>
          <el-table-column label="删除记录">
            <template #default="scope">
              <el-button type="primary" @click="selectTemplate(scope)">删除</el-button>
            </template>
          </el-table-column>
          <el-table-column label="下载检测报告">
            <template #default="scope">
              <el-button type="primary" @click="selectTemplate(scope)">下载docx</el-button>
              <el-button type="primary" @click="selectTemplate(scope)">下载pdf</el-button>
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
import router from "@/router";

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
  isSentToTeacher: string

}

const data = reactive({
  records: []
});

const load = () => {
  request.get('/getHistory').then(res => {

  });
};

load();  // 页面加载时加载数据




</script>