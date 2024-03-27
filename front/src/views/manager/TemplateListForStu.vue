<template>
  <div>
    <div class="card" style="margin-bottom: 10px">
      <h2>
        论文模板信息
        <el-button type="primary" style="margin-left: 10px" @click="load">刷新</el-button>
      </h2>
      <p>
        学生请仔细阅读论文模板信息，选择正确的模板进行论文格式检测，模板详细信息请见模板详情页。
      </p>
    </div>

    <div class="card" style="margin-bottom: 10px">
      <div>
        <el-table
            :data="data.templates"
            :default-sort="{ prop: 'update_time', order: 'descending' }"
            style="width: 100%" height="500">
          <el-table-column prop="templateId" label="模板编号" sortable width="130"/>
          <el-table-column prop="templateName" label="模板名称" sortable/>
          <el-table-column prop="creator" label="创建者" sortable/>
          <el-table-column prop="createTime" label="创建时间" sortable/>
          <el-table-column prop="updateTime" label="最近更新时间" sortable/>
          <el-table-column prop="description" label="模板描述"/>
        </el-table>
      </div>

    </div>

  </div>
</template>

<script lang="ts" setup>
import {reactive} from "vue";
import request from "@/utils/request";

interface Template {
  templateId: string;
  templateName: string;
  creator: string;
  createTime: string;
  updateTime: string;
  status: number;
  description: string;
}

const data = reactive({
  templates: []
});

const load = () => {
  request.get('/getTemplateList').then(res => {
    data.templates = res.data.filter((template: Template) => template.status === 1);
  });
};

load();  // 页面加载时加载数据

</script>