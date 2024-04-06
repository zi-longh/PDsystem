<template>
  <div>
    <div class="card" style="margin-bottom: 10px">
      <p style="margin: 5px;">
        <span style="font-size: 24px; font-weight: bold">模板列表 </span>
        <el-button type="primary" style="margin-left: 10px; margin-bottom: 10px" @click="load">刷新</el-button>
        <span style="margin-left: 15px; font-style: italic; color: #888; font-size: small"> 说明：处于发布状态的论文模板才能用于检测；用户只能操控自己创建的模板。 </span>
      </p>
      <p style="margin: 5px;">
        <el-checkbox v-model="checkOnLine" label="过滤未上线的模板" size="large" @change="load"/>
        <el-checkbox v-model="checkMyTemp" label="只看我的模板" size="large" @change="load"/>
      </p>
    </div>

    <div class="card" style="margin-bottom: 10px">
      <div>
        <el-table
            :data="data.templates"
            :default-sort="{ prop: 'update_time', order: 'descending' }"
            style="width: 100%" height="610">
          <el-table-column prop="templateId" label="模板编号" sortable width="130"/>
          <el-table-column prop="templateName" label="模板名称" sortable/>
          <el-table-column label="创建者" sortable>
            <template #default="scope">
              {{ data.teachers.find(teacher => teacher.username === scope.row.creator).name }}
              {{ "-" + data.teachers.find(teacher => teacher.username === scope.row.creator).teacherId }}
            </template>
          </el-table-column>
          <el-table-column prop="createTime" label="创建时间" sortable/>
          <el-table-column label="状态" width="80px" sortable>
            <template #default="scope">
              {{ scope.row.status === 1 ? "已上线" : "未上线" }}
            </template>
          </el-table-column>
          <el-table-column prop="updateTime" label="最近更新时间" sortable/>
          <el-table-column prop="description" label="模板描述"/>
          <el-table-column label="操作" width="200">
            <template #default="scope">
              <el-button type="primary" @click="selectTemplate(scope)">查看详情</el-button>
              <el-button type="primary" @click="modifyTemplate(scope)" v-if="scope.row.creator === user.username">修改
              </el-button>
              <i class="el-icon-info" title="您不能修改他人的模板" style="margin-left: 12px">
                <el-button type="primary" disabled v-if="scope.row.creator != user.username"
                >修改
                </el-button>
              </i>
            </template>
          </el-table-column>
          <el-table-column label="删除">
            <template #default="scope">
              <el-button type="danger" @click="openDialogVisible(scope)" v-if="scope.row.creator === user.username"
              >删除
              </el-button>


              <i class="el-icon-info" title="您不能删除他人的模板">
                <el-button type="danger" disabled v-if="scope.row.creator != user.username"
                >删除
                </el-button>
              </i>
            </template>
          </el-table-column>

        </el-table>
      </div>

    </div>

    <el-dialog v-model="dialogVisible" title="您确定要删除吗？" width="500" :before-close="handleClose">
      <span>删除后内容无法恢复！</span>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="deleteTemplate(data.scope)">
            确定
          </el-button>
        </div>
      </template>
    </el-dialog>


  </div>
</template>

<script lang="ts" setup>
import {reactive, ref} from "vue";
import request from "@/utils/request";
import router from "@/router";
import {ElMessage} from "element-plus";

interface Template {
  templateId: string;
  templateName: string;
  creator: string;
  createTime: string;
  updateTime: string;
  status: number;
  description: string;
}

const checkOnLine = ref(false)
const checkMyTemp = ref(false)
const user = JSON.parse(localStorage.getItem('account-user') || '{}')
const data = reactive({
  templates: [],
  teachers: [],
  scope: {}
});
const dialogVisible = ref(false);
const handleClose = (done: () => void) => {
  done()
}

const load = () => {
  request.get('/getTemplateList').then(res => {
    data.templates = res.data;
    if (checkOnLine.value) {
      data.templates = data.templates.filter((template: Template) => template.status === 1);
    }
    if (checkMyTemp.value) {
      data.templates = data.templates.filter((template: Template) => template.creator === user.username);
    }
  });
  request.get('/getAllTeacher').then(res => {
    data.teachers = res.data;
  })
};

load();  // 页面加载时加载数据

const selectTemplate = (scope) => {
  // 保存选中的模板id和模板名称
  localStorage.setItem('templateId', scope.row.templateId);
  localStorage.setItem('templateName', scope.row.templateName + '-' + "已上线");
  // 跳转到论文检测页面
  router.push('/templateInfoForTea')
};

const modifyTemplate = (scope) => {
  // 保存选中的模板id和模板名称
  localStorage.setItem('templateId-my', scope.row.templateId);
  localStorage.setItem('templateName-my', scope.row.templateName + '-' + "已上线");
  // 跳转到论文检测页面
  router.push('/modifyTemplate')
};


const openDialogVisible = (scope) => {
  data.scope = scope;
  dialogVisible.value = true;
};

const deleteTemplate = (scope) => {
  dialogVisible.value = false;
  request.post('/deleteTemplate', {templateId: scope.row.templateId}).then(res => {
    if (res.code === '200') {
      ElMessage.success('删除成功');
      load();
    } else {
      ElMessage.error('删除失败');
    }
  })
};


</script>