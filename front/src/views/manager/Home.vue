<template>
  <div>
    <div class="card" style="text-align: center; padding: 20px; margin: 20px">
      <img src="@/assets/imgs/home_startwork.png" alt="Homepage Image" style="max-width: 100%; height: auto;">
      <h2>欢迎使用“论文格式检测与修订系统”</h2>
      <!--根据用户角色显示不同的欢迎信息-->
      <p v-if="user.role === 'STUDENT'">您好 <span style="color:dodgerblue">{{ user.username }}</span> 同学！</p>
      <p v-else-if="user.role === 'TEACHER'">您好 <span style="color:dodgerblue">{{ user.username }}</span> 老师！</p>
      <p v-else>您好 <span style="color:dodgerblue">{{ user.username }}</span> 管理员！</p>
      <p>请点击左侧菜单开始工作。</p>
    </div>



  </div>
</template>

<style>
h2 {
  color: #333;
  font-size: 24px;
  margin-bottom: 15px;
}

p {
  color: #666;
  font-size: 16px;
  margin-bottom: 10px;
}


</style>


<script setup>

const user = JSON.parse(localStorage.getItem('account-user') || '{}')  // 获取用户信息
</script>