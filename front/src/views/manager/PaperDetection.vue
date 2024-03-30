<template>
  <div>
    <div class="card" style="margin-bottom: 10px">
      <p style="margin: 5px;">
        <span style="font-size: 24px; font-weight: bold">上传论文进行格式检测 </span>
      </p>
      <p style="margin-left: 4px">
        <span style="margin-left: 5px">选择检测模板：</span>
        <el-select v-model="value" clearable placeholder="请选择您要使用的检测模板" style="width: 400px;">
          <el-option
              v-for="item in options.templates"
              :key="item.templateId"
              :label="item.templateName"
              :value="item.templateName"
          />
        </el-select>
      </p>
      <p style="margin-left: 4px">
        <span style="margin-left: 5px">输入您的论文中文标题：</span>
        <el-input v-model="inputForChinese" style="width: 335px" placeholder="请输入论文中文标题" clearable/>
      </p>
      <p style="margin-left: 4px">
        <span style="margin-left: 5px">输入您的论文英文标题：</span>
        <el-input v-model="inputForEnglish" style="width: 335px" placeholder="Please input paper name" clearable/>
      </p>
      <p style="margin-left: 4px">
        <span style="margin-left: 5px">若检测通过则提交到指导老师：</span>
        <el-switch v-model="sendToTeacher"/>
      </p>
      <p style="margin-left: 4px">
        <el-upload
            ref="upload"
            class="upload-demo"
            action="https://run.mocky.io/v3/9d059bf9-4660-45f2-925d-ce80ad6c4d15"
            :limit="1"
            :on-exceed="handleExceed"
            :auto-upload="false"
        >
          <template #trigger>
            <el-button type="primary">选择上传论文</el-button>
          </template>
          <template #tip>
            <div class="el-upload__tip text-red">
              请上传您的论文文件，支持扩展名：.docx, .doc; 开始检测前请确保您已经选择了检测模板并输入了论文标题。
            </div>
          </template>

          <el-button class="ml-3" type="primary" color="#626aef" @click="submitUpload"
                     style="margin-left: 20px; height: 33px; inline-size: auto">
            开始论文格式检测
          </el-button>

        </el-upload>
      </p>
    </div>

    <div class="card" style="height: 400px"> <!--v-if="data.isHaveNewData"-->

      <el-descriptions class="margin-top" :column="5" :size="size" border>
        <template #title>
          <span style="font-size: 24px; font-weight: bold; margin-left: 5px">论文检测结果 </span>
        </template>
        <template #extra>
          <el-button type="primary" @click="moveToHistory">查看更多检测记录</el-button>
        </template>
        <el-descriptions-item>
          <template #label>
            <div class="cell-item">
              <el-icon :style="iconStyle">
                <user/>
              </el-icon>
              Username
            </div>
          </template>
          kooriookami
        </el-descriptions-item>
        <el-descriptions-item>
          <template #label>
            <div class="cell-item">
              <el-icon :style="iconStyle">
                <Timer/>
              </el-icon>
              检测时间
            </div>
          </template>
          2024年3月30日14:33:09
        </el-descriptions-item>
        <el-descriptions-item>
          <template #label>
            <div class="cell-item">
              <el-icon :style="iconStyle">
                <Tools/>
              </el-icon>
              选择的模板
            </div>
          </template>
          202020-模板
        </el-descriptions-item>
        <el-descriptions-item>
          <template #label>
            <div class="cell-item">
              <el-icon :style="iconStyle">
                <Notification/>
              </el-icon>
              检测结果
            </div>
          </template>
          <el-tag size="small">检测通过</el-tag>
        </el-descriptions-item>
        <el-descriptions-item>
          <template #label>
            <div class="cell-item">
              <el-icon :style="iconStyle">
                <Promotion/>
              </el-icon>
              发送导师
            </div>
          </template>
          <el-tag size="small">否</el-tag>
        </el-descriptions-item>
        <el-descriptions-item>
          <template #label>
            <div class="cell-item">
              <el-icon :style="iconStyle">
                <tickets/>
              </el-icon>
              论文中文名称
            </div>
          </template>
          论文中文名称
        </el-descriptions-item>
        <el-descriptions-item>
          <template #label>
            <div class="cell-item">
              <el-icon :style="iconStyle">
                <tickets/>
              </el-icon>
              论文英文名称
            </div>
          </template>
          论文英文名称
        </el-descriptions-item>

        <el-descriptions-item>
          <template #label>
            <div class="cell-item">
              <el-icon :style="iconStyle">
                <Download/>
              </el-icon>

              下载检测报告
            </div>
          </template>
          检测结果下载，pdf下载
        </el-descriptions-item>
      </el-descriptions>
      <p>抱歉，您的论文并未通过检测，请下载检测报告，根据批注提示修改论文格式。</p>
    </div>


  </div>
</template>

<style scoped>
.el-descriptions {
  margin-top: 20px;
}

.cell-item {
  display: flex;
  align-items: center;
}

.margin-top {
  margin-top: 20px;
}
</style>


<script setup lang="ts">
import {computed, reactive, ref} from "vue";
import request from "@/utils/request";
import type {UploadInstance, UploadProps, UploadRawFile} from 'element-plus'
import {genFileId} from 'element-plus'
import router from "@/router";

interface Template {
  templateId: string;
  templateName: string;
  creator: string;
  createTime: string;
  updateTime: string;
  status: number;
  description: string;
}

const value = ref("") // 设置论文模板选择框默认值
const inputForChinese = ref("") // 设置论文标题输入框默认值
const inputForEnglish = ref("") // 设置论文标题输入框默认值
const sendToTeacher = ref(false) // 设置是否提交到指导老师默认值
const data = reactive({
  isHaveNewData: false,
  newDetectData: {
    recordId: '',
    username: '',
    templateId: '',
    detectTime: '',
    status: '',
    paperName: '',
    paperEnglishName: '',
    resultFileName: '',
    resultPDF: '',
    isSentToTeacher: ''
  }
})
const size = ref('default')
const iconStyle = computed(() => {
  const marginMap = {
    large: '8px',
    default: '6px',
    small: '4px',
    margin: '20px',
  }
  return {
    marginRight: marginMap[size.value] || marginMap.default,
  }
})


const options = reactive({
  templates: []
})
// let templateData:{ TemplateInfo: string } = {
// };


const load = () => {
  request.get('/getTemplateListForStudent').then(res => {
    options.templates = res.data.filter((template: Template) => template.status === 1);
  });
};


const init = () => {
  load();  // 页面加载时加载数据
  data.isHaveNewData = false
}
init()

const upload = ref<UploadInstance>()
const handleExceed: UploadProps['onExceed'] = (files) => {
  upload.value!.clearFiles()
  const file = files[0] as UploadRawFile
  file.uid = genFileId()
  upload.value!.handleStart(file)
}

const submitUpload = () => {
  upload.value!.submit()
}

const moveToHistory = () => {
  /* 跳转到历史记录 */
  router.push('/History')
}

</script>