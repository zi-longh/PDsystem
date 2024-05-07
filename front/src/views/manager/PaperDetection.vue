<template>
  <div>
    <div class="card" style="margin-bottom: 10px">
      <p style="margin: 5px;">
        <span style="font-size: 24px; font-weight: bold">上传论文进行格式检测 </span>
      </p>
      <p style="margin-left: 4px">
        <span style="margin-left: 5px">选择检测模板：</span>
        <el-select v-model="inputData.templateValue" clearable placeholder="请选择您要使用的检测模板"
                   style="width: 400px;">
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
        <el-input v-model="inputData.inputForChinese" style="width: 335px" placeholder="请输入论文中文标题" clearable/>
      </p>
      <p style="margin-left: 4px">
        <span style="margin-left: 5px">输入您的论文英文标题：</span>
        <el-input v-model="inputData.inputForEnglish" style="width: 335px" placeholder="Please input paper name"
                  clearable/>
      </p>
      <p style="margin-left: 4px" v-if = "user.role === 'STUDENT'">
        <span style="margin-left: 5px">若检测通过则提交到指导老师：</span>
        <el-switch v-model="inputData.sendToTeacher"/>
          <span style="margin-left: 15px; font-style: italic; color: #888; font-size: small" > 提示：若没有绑定指导老师，请先到个人资料里绑定指导老师 </span>
      </p>
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
            <el-button type="primary">选择上传论文</el-button>
          </template>
          <template #tip>
            <div class="el-upload__tip text-red">
              请上传您的论文文件，支持扩展名：.docx, .doc; 开始检测前请确保您已经选择了检测模板并输入了论文标题。
            </div>
          </template>

          <el-button class="ml-3" type="primary" color="#626aef" @click="submitAndDetect(inputData)"
                     style="margin-left: 20px; height: 33px; inline-size: auto">
            开始论文格式检测
          </el-button>

        </el-upload>
      </p>
    </div>

    <div class="card" style="height: 400px" v-if="data.isHaveNewData">

      <el-descriptions class="margin-top" :column="5" :size="size" border>
        <template #title>
          <span style="font-size: 24px; font-weight: bold; margin-left: 5px">论文检测结果 </span>
        </template>
        <template #extra>
          <el-button type="primary" @click="moveToHistory">查看更多检测记录</el-button>
        </template>

        <el-descriptions-item width="150px">
          <template #label>
            <div class="cell-item">
              <el-icon :style="iconStyle">
                <User/>
              </el-icon>
              Username
            </div>
          </template>
          {{ data.newDetectData.username }}
        </el-descriptions-item>

        <el-descriptions-item span="2" width="180px">
          <template #label>
            <div class="cell-item">
              <el-icon :style="iconStyle">
                <tickets/>
              </el-icon>
              论文中文名称
            </div>
          </template>
          {{ data.newDetectData.paperName }}
        </el-descriptions-item>

        <el-descriptions-item span="2" width="180px">
          <template #label>
            <div class="cell-item">
              <el-icon :style="iconStyle">
                <tickets/>
              </el-icon>
              论文英文名称
            </div>
          </template>
          {{ data.newDetectData.paperEnglishName }}
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
          {{ data.newDetectData.detectTime }}
        </el-descriptions-item>

        <el-descriptions-item width="150px" span="2">
          <template #label>
            <div class="cell-item">
              <el-icon :style="iconStyle">
                <Tools/>
              </el-icon>
              选择的模板
            </div>
          </template>
          {{ data.newDetectData.templateId }}
        </el-descriptions-item>

        <el-descriptions-item width="75px" span="2">
          <template #label>
            <div class="cell-item">
              <el-icon :style="iconStyle">
                <Notification/>
              </el-icon>
              检测结果
            </div>
          </template>
          <el-tag size="small">{{ data.newDetectData.status }}</el-tag>
        </el-descriptions-item>

        <el-descriptions-item v-if = "user.role === 'STUDENT'">
          <template #label>
            <div class="cell-item">
              <el-icon :style="iconStyle">
                <Promotion/>
              </el-icon>
              发送导师
            </div>
          </template>
          <el-tag size="small">{{ data.newDetectData.isSendToTeacher }}</el-tag>
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
          <el-button type="primary" @click="downloadDocx" v-if="data.newDetectData.status != '未知状态'">下载检测报告
          </el-button>
          <el-button type="primary" @click="downloadPDF"
                     v-if="data.newDetectData.status === '检测通过(可修改)' || data.newDetectData.status === '检测通过'">
            下载PDF
          </el-button>
        </el-descriptions-item>
      </el-descriptions>
      <p v-if="data.newDetectData.status === '检测通过'">
        恭喜你，您的论文已通过检测！
      </p>
      <p v-if="data.newDetectData.status === '检测通过(可修改)'">
        您的论文已通过检测，但仍存在可以优化的地方，建议您下载检测报告，根据批注提示修改论文格式。
      </p>
      <p v-if="data.newDetectData.status === '不通过'">
        抱歉，您的论文并未通过检测，请下载检测报告，根据批注提示修改论文格式。
      </p>
      <p v-if="data.newDetectData.status === '未知状态'">
        论文检测状态未知，请联系管理员。
      </p>
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
import {ElMessage, genFileId, UploadInstance, UploadProps, UploadRawFile, UploadUserFile} from 'element-plus'
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
const inputData = reactive({
  templateValue: "",
  inputForChinese: "",
  inputForEnglish: "",
  sendToTeacher: false
})
const data = reactive({
  isHaveTeacher: false,
  teacherUsername: '',
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
    isSendToTeacher: '',
    teacherUsername: ''
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
const uploadFile = ref<UploadInstance>()
const fileList = ref<UploadUserFile[]>([])
const user = JSON.parse(localStorage.getItem('account-user') || "{}")

const load = () => {
  request.get('/getTemplateListForStudent').then(res => {
    options.templates = res.data.filter((template: Template) => template.status === 1);
  });

  if (user.role == 'STUDENT') {
    request.post('/getStudentInfoByUsername', {
          username: user.username
        }
    ).then(res => {
      if (res.data.instructor !== null && res.data.instructor !== '') {
        data.isHaveTeacher = true
        data.teacherUsername = res.data.instructor
      }
    })

  }

};

const init = () => {
  load();  // 页面加载时加载数据
  data.isHaveNewData = false
}
init()


const handleExceed: UploadProps['onExceed'] = (files) => {
  ElMessage.error('最多只能上传一个文件！')
  // 清空已上传的文件

  uploadFile.value!.clearFiles()
  const file = files[0] as UploadRawFile
  file.uid = genFileId()
  uploadFile.value!.handleStart(file)

}
const submitAndDetect = (inputData) => {
  // 若有未填写的信息，则提示用户
  if (!inputData.templateValue) {
    ElMessage.error("请选择检测模板！")
    return
  }
  if (!inputData.inputForChinese) {
    ElMessage.error("请输入论文中文标题！")
    return
  }
  if (!inputData.inputForEnglish) {
    ElMessage.error("请输入论文英文标题！")
    return
  }
  // 如果勾选了发送给指导老师，但是没有绑定指导老师，则提示用户
  if (inputData.sendToTeacher && !data.isHaveTeacher) {
    ElMessage.error("您还没有绑定指导老师，请先到个人资料里绑定指导老师！")
    return
  }
  // 如果没有选择文件，则提示用户
  if (fileList.value.length == 0) {
    ElMessage.error("请上传您的论文文件！")
    return
  }
  /* 提交并检测论文 */
  uploadFile.value.submit()
  // 这里不能过快删除文件
}

const handleSuccess = (res, file) => {
  let filePath = ref('')
  let accountData = JSON.parse(localStorage.getItem('account-user') || "{}")
  if (res.code === '200') {
    ElMessage.success("文件上传成功！")
    filePath = res.data.filePath
    request.post('/files/detect', {
      username: accountData.username,
      templateId: inputData.templateValue.split('-')[0],
      paperName: inputData.inputForChinese,
      paperEnglishName: inputData.inputForEnglish,
      docFilePath: filePath,
      isSendToTeacher: inputData.sendToTeacher? '1': '0',
      teacherUsername: data.teacherUsername
    }).then(res => {
      if (res.code === '200') {
        ElMessage.success("文件检测成功！")
        data.isHaveNewData = true
        data.newDetectData = res.data
      } else {
        ElMessage.error(res.msg)
      }
    })
  } else {
    ElMessage.error(res.msg)
  }
  uploadFile.value!.clearFiles()
}

const downloadDocx = () => {
  // 下载docx文件
  // 在新窗口打开下载链接
  window.open('http://localhost:9090/files/download?fileName=' + data.newDetectData.resultFileName)
  // window.open('http://localhost:9090/files/download?fileName=(待修改)wps测试论文2_2024-04-01T17-33-30.863999.docx')
}

const downloadPDF = () => {
  // 下载pdf文件
  // 在新窗口打开下载链接
  window.open('http://localhost:9090/files/downloadPDF?fileName=' + data.newDetectData.resultPDF)
}

const moveToHistory = () => {
  /* 跳转到历史记录 */
  router.push('/History')
}

</script>