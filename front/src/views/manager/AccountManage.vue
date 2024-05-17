<template>
  <div>
    <div class="card" style="margin-bottom: 10px">
      <p style="margin: 5px;">
        <span style="font-size: 24px; font-weight: bold">账号管理 </span>
      </p>
      <p style="margin:5px; margin-top: 10px">
        <el-input style="width: 260px; margin-right: 10px" v-model="data.inputUsername" placeholder="输入账号名查询"
                  :prefix-icon="Search"/>
        <el-select style="width: 260px" v-model="data.selectedRole">
          <el-option value="STUDENT" label="学生账号"></el-option>
          <el-option value="TEACHER" label="教师账号"></el-option>
          <el-option value="unlimited" label="不限制账号类型"></el-option>
        </el-select>

        <el-button type="primary" style="margin-left: 10px" @click="load">查询</el-button>
        <el-button type="info" @click="reset">重置</el-button>
      </p>
    </div>

    <div class="card" style="margin-bottom: 10px">
      <div>
        <el-table
            :data="data.info"
            :default-sort="{ prop: 'username', order: 'descending' }"
            style="width: 100%" height="640">
          <el-table-column prop="username" label="账号名" sortable/>
          <el-table-column prop="role" label="账号类型" sortable/>
          <el-table-column prop="name" label="姓名" sortable/>
          <el-table-column prop="id" label="学号/工号" sortable/>

          <el-table-column label="操作" width="400">
            <template #default="scope">
              <el-button type="primary" @click="selectTemplate(scope)">修改账号</el-button>
              <el-button type="primary" @click="resetPassword">修改密码</el-button>
              <el-button type="danger" @click="deleteAccount">删除账号</el-button>
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

const load = () => {
  if (data.selectedRole === 'unlimited') {
    data.info = tempData;
    if (data.inputUsername !== ''){ // 输入账号名搜索，前端匹配
      data.info = tempData.filter(item => item.username.includes(data.inputUsername));
    }
    return;
  }

  if (data.selectedRole === 'STUDENT') {
    data.info = tempData.filter(item => item.role === '学生账号');
    if (data.inputUsername !== ''){ // 输入账号名搜索，前端匹配
      data.info = data.info.filter(item => item.username.includes(data.inputUsername));
    }
    return;
  }

  if (data.selectedRole === 'TEACHER') {
    data.info = tempData.filter(item => item.role === '教师账号');
    if (data.inputUsername !== ''){ // 输入账号名搜索，前端匹配
      data.info = data.info.filter(item => item.username.includes(data.inputUsername));
    }
    return;
  }

};
load();  // 页面加载时加载数据

const reset = () => {
  data.selectedRole = 'unlimited';
  data.inputUsername = '';
  load();
};


const resetPassword = () => {
  ElMessageBox.prompt('请输入新的密码', '密码修改')
      .then(({ value }) => {
        ElMessage({
          type: 'success',
          message: `密码修改成功！`,
        })
      })
      .catch(() => {
        ElMessage({
          type: 'info',
          message: '取消修改',
        })
      })
}


const deleteAccount = () => {
  ElMessageBox.confirm(
      '你确定要删除该账号吗？',
      '删除账号',
      {
        confirmButtonText: '删除',
        cancelButtonText: '取消',
        type: 'warning',
      }
  )
      .then(() => {
        ElMessage({
          type: 'success',
          message: '删除成功！',
        })
      })
      .catch(() => {
        ElMessage({
          type: 'info',
          message: '删除取消',
        })
      })
}

</script>