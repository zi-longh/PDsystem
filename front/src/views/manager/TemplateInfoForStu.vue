<template>
  <div>
    <div class="card" style="margin-bottom: 10px">
      <p style="margin: 5px;">
        <span style="font-size: 24px; font-weight: bold">查看模板要求详情 </span>
      </p>
      <p style="margin-left: 5px">
        <el-select
            v-model="value"
            clearable
            placeholder="Select"
            style="width: 240px"
        >
          <el-option
              v-for="item in options.templates"
              :key="item.templateId"
              :label="item.templateName"
              :value="item.templateId"
          />
        </el-select>

        <el-button type="primary" style="margin-left: 10px" @click="load">查看</el-button>
      </p>

    </div>

    <div class="card" style="margin-bottom: 10px">
      <el-scrollbar height="650px">

      </el-scrollbar>

    </div>

  </div>
</template>

<style scoped>

</style>


<script lang="ts" setup>
import {reactive, ref} from "vue";
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
const value = ref('')

const options = reactive({
  templates: []
})




const load = () => {
  request.get('/getTemplateListForStudent').then(res => {
    options.templates = res.data.filter((template: Template) => template.status === 1);
  });
};

load();  // 页面加载时加载数据

</script>