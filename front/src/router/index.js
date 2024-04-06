import {createRouter, createWebHistory} from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'Manager',
      component: () => import('@/views/Manager.vue'),
      redirect: '/home',
      children: [
        { path: 'home', name: 'Home', component: () => import('@/views/manager/Home.vue')},
        { path: 'personInfoForStu', name: 'PersonInfoForStu', component: () => import('@/views/manager/PersonInfoForStu.vue')},
        { path: 'personInfoForTea', name: 'PersonInfoForTea', component: () => import('@/views/manager/PersonInfoForTea.vue')},
        { path: 'templateListForStu', name: 'TemplateListForStu', component: () => import('@/views/manager/TemplateListForStu.vue')},
        { path: 'templateListForTea', name: 'TemplateListForTea', component: () => import('@/views/manager/TemplateListForTea.vue')},
        {path: 'templateInfoForStu', name: 'TemplateInfoForStu', component: () => import('@/views/manager/TemplateInfoForStu.vue')},
        {path: 'templateInfoForTea', name: 'TemplateInfoForTea', component: () => import('@/views/manager/TemplateInfoForTea.vue')},
        {path: 'paperDetection', name: 'PaperDetection', component: () => import('@/views/manager/PaperDetection.vue')},
        {path:'history',name:'History',component:()=>import('@/views/manager/History.vue')},
        {path:'papersOfStu', name:'PapersOfStu', component:()=>import('@/views/manager/PapersOfStu.vue')},
        {path:'createTemplate', name:'CreateTemplate', component:()=>import('@/views/manager/CreateTemplate.vue')},
        {path:'modifyTemplate', name:'ModifyTemplate', component:()=>import('@/views/manager/ModifyTemplate.vue')},
      ]
    },
    { path: '/login', name: 'Login', component: () => import('@/views/Login.vue'),},
    { path: '/register', name: 'Register', component: () => import('@/views/Register.vue'),},
  ]
})

export default router
