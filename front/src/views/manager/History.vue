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
          <el-table-column prop="paperName" label="论文中文标题" sortable/>
          <el-table-column prop="paperEnglishName" label="论文英文标题" sortable/>
          <el-table-column prop="templateId" label="检测模板" sortable/>
          <el-table-column prop="detectTime" label="检测时间" sortable/>
          <el-table-column prop="status" label="检测结果" sortable>
            <template #default="scope">
              <el-tag >{{ scope.row.status }}</el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="isSendToTeacher" label="发送导师">
            <template #default="scope">
              <el-tag>{{ scope.row.isSendToTeacher }}</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="删除记录">
            <template #default="scope">
              <el-button type="primary" @click="deleteRecord(scope)">删除</el-button>
            </template>
          </el-table-column>
          <el-table-column label="下载检测报告" width="230">
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

}

const data = reactive({
  records: []
});

const load = () => {
  const account = localStorage.getItem('account-user');
  const username = JSON.parse(account).username;
  request.get('/getHistoryByUsername', {
    params: {
      username: username
    }
  }).then(res => {
    data.records = res.data;
  });

};
load();  // 页面加载时加载数据

const deleteRecord = (scope: any) => {
  request.post('/deleteRecordById', scope.row.recordId
  ).then(res => {
    if(res.code === '200') {
      // 删除成功
      console.log('删除成功');
      ElMessage.success('删除成功')
      load();
    }else{
      ElMessage.error('删除失败')
    }

  });
};


</script>