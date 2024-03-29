<template>
  <div>
    <div class="card" style="margin-bottom: 10px">
      <p style="margin: 5px;">
        <span style="font-size: 24px; font-weight: bold">查看模板要求详情 </span>
      </p>
      <p style="margin-left: 4px">
        <el-select
            v-model="value"
            clearable
            placeholder="选择您要查看的模板"
            style="width: 400px;"
        >
          <el-option
              v-for="item in options.templates"
              :key="item.templateId"
              :label="item.templateName"
              :value="item.templateName"
          />
        </el-select>
        <el-button type="primary" style="margin-left: 10px" @click="load">查看</el-button>

        <span style="margin-left: 15px">当前查看的模板名称：{{value}}</span>
      </p>
    </div>

    <div class="card" style="margin-bottom: 10px">
      <el-scrollbar height="650px" >
        <div class="demo-collapse">
          <el-collapse v-model="activeNames" @change="handleChange" style="margin: 12px">
            <el-collapse-item name="9">
              <template #title>
                <h2>页面页眉页脚设置</h2>
              </template>
              <el-descriptions direction="vertical" column="9" border >
                <el-descriptions-item label="正文前的页眉内容" span="3">暨南大学本科毕业设计（论文）</el-descriptions-item>
                <el-descriptions-item label="正文和正文后的页眉内容" span="3">[你的论文标题]</el-descriptions-item>
                <el-descriptions-item label="页眉页脚字体类别" span="1">宋体</el-descriptions-item>
                <el-descriptions-item label="页眉页脚字体大小" span="1">小五</el-descriptions-item>
                <el-descriptions-item label="页眉内容位置" span="1">居中</el-descriptions-item>
                <el-descriptions-item label="页上边距">2.5cm</el-descriptions-item>
                <el-descriptions-item label="页下边距">2.5cm</el-descriptions-item>
                <el-descriptions-item label="页左边距">2.5cm</el-descriptions-item>
                <el-descriptions-item label="页右边距">2.5cm</el-descriptions-item>
                <el-descriptions-item label="页眉边距">2.5cm</el-descriptions-item>
                <el-descriptions-item label="页脚边距">2.5cm</el-descriptions-item>
                <el-descriptions-item label="是否区分奇偶页插入页脚"><el-tag size="small">是</el-tag></el-descriptions-item>
              </el-descriptions>
            </el-collapse-item>
            <el-collapse-item name="10">
              <template #title>
                <h2>图表和图注表注</h2>
              </template>
              <el-descriptions direction="vertical" column="8" border >
                <el-descriptions-item label="图注表注中文字体" >宋体</el-descriptions-item>
                <el-descriptions-item label="图注表注英文字体" >Times New Roman</el-descriptions-item>
                <el-descriptions-item label="图注表注字体大小" >五号</el-descriptions-item>
                <el-descriptions-item label="推荐的图片最小宽度" >大于1cm</el-descriptions-item>
                <el-descriptions-item label="推荐的图片最大宽度" >小于17cm</el-descriptions-item>
                <el-descriptions-item label="推荐的图片最小长度">大于1cm</el-descriptions-item>
                <el-descriptions-item label="推荐的图片最大长度">小于17cm</el-descriptions-item>
                <el-descriptions-item label="推荐图表数量">不少于2个</el-descriptions-item>
                <el-descriptions-item label="图注表注说明" span="8">论文中的每个图片都应该有图注，图注位置应该在图片的正下方；论文中的每个表格都应该有表注，表注的位置应该在表格的正上方。</el-descriptions-item>
             </el-descriptions>
            </el-collapse-item>

            <el-collapse-item name="1">
              <template #title>
                <h2>诚信说明</h2>
              </template>
              <el-descriptions direction="vertical" column="9" border>
                <el-descriptions-item label="标题要求" width="300px" >即“诚信说明”标题格式</el-descriptions-item>
                <el-descriptions-item label="中文字体">宋体</el-descriptions-item>
                <el-descriptions-item label="英文字体">Times New Roman</el-descriptions-item>
                <el-descriptions-item label="字体大小">36</el-descriptions-item>
                <el-descriptions-item label="对齐方式">左对齐</el-descriptions-item>
                <el-descriptions-item label="段间距">2倍行距</el-descriptions-item>
                <el-descriptions-item label="段前距">0.5行</el-descriptions-item>
                <el-descriptions-item label="段后距">0.5行</el-descriptions-item>
                <el-descriptions-item label="是否加粗"><el-tag size="small">是</el-tag></el-descriptions-item>

                <el-descriptions-item label="正文段落要求" >即“诚信说明”正文内容格式</el-descriptions-item>
                <el-descriptions-item label="中文字体">宋体</el-descriptions-item>
                <el-descriptions-item label="英文字体">Times New Roman</el-descriptions-item>
                <el-descriptions-item label="字体大小">36</el-descriptions-item>
                <el-descriptions-item label="段落首行缩进">两个字符</el-descriptions-item>
                <el-descriptions-item label="段间距">2倍行距</el-descriptions-item>
                <el-descriptions-item label="是否加粗"><el-tag size="small">是</el-tag></el-descriptions-item>
              </el-descriptions>
            </el-collapse-item>
            <el-collapse-item name="2">
              <template #title>
                <h2>中文摘要</h2>
              </template>
              <el-descriptions direction="vertical" column="9" border>
                <el-descriptions-item label="前置词（即“摘要”、“关键字”）的字体" width="300px" span="1">宋体</el-descriptions-item>
                <el-descriptions-item label="摘要内容推荐的最大字数"  span="1">最好不超过500字</el-descriptions-item>
                <el-descriptions-item label="摘要内容推荐的最小字数" span="1">最好不少于100字（未实现）</el-descriptions-item>
                <el-descriptions-item label="关键词推荐最大数量" span="1">不超过6个</el-descriptions-item>
                <el-descriptions-item label="关键词推荐最小数量" span="1">不少于3个</el-descriptions-item>
                <el-descriptions-item label="前置词是否加粗"  span="4"><el-tag size="small">是</el-tag></el-descriptions-item>

                <el-descriptions-item label="标题要求" width="300px">即“中文摘要”上方的论文标题格式要求</el-descriptions-item>
                <el-descriptions-item label="中文字体">宋体</el-descriptions-item>
                <el-descriptions-item label="英文字体">Times New Roman</el-descriptions-item>
                <el-descriptions-item label="字体大小">36</el-descriptions-item>
                <el-descriptions-item label="对齐方式">左对齐</el-descriptions-item>
                <el-descriptions-item label="段间距">2倍行距</el-descriptions-item>
                <el-descriptions-item label="段前距">0.5行</el-descriptions-item>
                <el-descriptions-item label="段后距">0.5行</el-descriptions-item>
                <el-descriptions-item label="是否加粗"><el-tag size="small">是</el-tag></el-descriptions-item>

                <el-descriptions-item label="正文段落要求" >即“中文摘要”正文内容格式要求</el-descriptions-item>
                <el-descriptions-item label="中文字体">宋体</el-descriptions-item>
                <el-descriptions-item label="英文字体">Times New Roman</el-descriptions-item>
                <el-descriptions-item label="字体大小">36</el-descriptions-item>
                <el-descriptions-item label="段落首行缩进">两个字符</el-descriptions-item>
                <el-descriptions-item label="段间距">2倍行距</el-descriptions-item>
                <el-descriptions-item label="是否加粗"><el-tag size="small">是</el-tag></el-descriptions-item>
              </el-descriptions>

            </el-collapse-item>
            <el-collapse-item name="3">
              <template #title>
                <h2>英文摘要</h2>
              </template>
              <el-descriptions direction="vertical" column="9" border>
                <el-descriptions-item label="前置词（即“Abstract”、“Keywords”）的字体" width="300px" span="1">宋体</el-descriptions-item>
                <el-descriptions-item label="摘要内容推荐的最大字数"  span="1">最好不超过500个单词</el-descriptions-item>
                <el-descriptions-item label="摘要内容推荐的最小字数" span="1">最好不少于100个单词（未实现）</el-descriptions-item>
                <el-descriptions-item label="关键词推荐最大数量" span="1">不超过6个</el-descriptions-item>
                <el-descriptions-item label="关键词推荐最小数量" span="1">不少于3个</el-descriptions-item>
                <el-descriptions-item label="前置词是否加粗"  span="4"><el-tag size="small">是</el-tag></el-descriptions-item>

                <el-descriptions-item label="标题要求" width="300px">即“英文摘要”上方的英文论文标题格式要求</el-descriptions-item>
                <el-descriptions-item label="中文字体">宋体</el-descriptions-item>
                <el-descriptions-item label="英文字体">Times New Roman</el-descriptions-item>
                <el-descriptions-item label="字体大小">36</el-descriptions-item>
                <el-descriptions-item label="对齐方式">左对齐</el-descriptions-item>
                <el-descriptions-item label="段间距">2倍行距</el-descriptions-item>
                <el-descriptions-item label="段前距">0.5行</el-descriptions-item>
                <el-descriptions-item label="段后距">0.5行</el-descriptions-item>
                <el-descriptions-item label="是否加粗"><el-tag size="small">是</el-tag></el-descriptions-item>

                <el-descriptions-item label="正文段落要求" >即“英文摘要”正文内容格式要求</el-descriptions-item>
                <el-descriptions-item label="中文字体">宋体</el-descriptions-item>
                <el-descriptions-item label="英文字体">Times New Roman</el-descriptions-item>
                <el-descriptions-item label="字体大小">36</el-descriptions-item>
                <el-descriptions-item label="段落首行缩进">两个字符</el-descriptions-item>
                <el-descriptions-item label="段间距">2倍行距</el-descriptions-item>
                <el-descriptions-item label="是否加粗"><el-tag size="small">是</el-tag></el-descriptions-item>
              </el-descriptions>
            </el-collapse-item>
            <el-collapse-item name="4">
              <template #title>
                <h2>目录</h2>
              </template>
              <el-descriptions direction="vertical" column="9" border >

                <el-descriptions-item label="相关说明" span="9" >目录正文会强制检查是否有内容缺失，目录最小应包含到3级标题，若有缺失则检测会不通过！</el-descriptions-item>
                <el-descriptions-item label="标题要求" width="300px">即“目录”二字的格式要求</el-descriptions-item>
                <el-descriptions-item label="中文字体">宋体</el-descriptions-item>
                <el-descriptions-item label="英文字体">Times New Roman</el-descriptions-item>
                <el-descriptions-item label="字体大小">36</el-descriptions-item>
                <el-descriptions-item label="对齐方式">左对齐</el-descriptions-item>
                <el-descriptions-item label="段间距">2倍行距</el-descriptions-item>
                <el-descriptions-item label="段前距">0.5行</el-descriptions-item>
                <el-descriptions-item label="段后距">0.5行</el-descriptions-item>
                <el-descriptions-item label="是否加粗"><el-tag size="small">是</el-tag></el-descriptions-item>

              </el-descriptions>
            </el-collapse-item>
            <el-collapse-item name="5">
              <template #title>
                <h2>绪论和正文</h2>
              </template>
              <el-descriptions direction="vertical" column="9" border>
                <el-descriptions-item label="一级标题要求" width="300px" >即正文中的一级标题格式（包括绪论）</el-descriptions-item>
                <el-descriptions-item label="中文字体">宋体</el-descriptions-item>
                <el-descriptions-item label="英文字体">Times New Roman</el-descriptions-item>
                <el-descriptions-item label="字体大小">36</el-descriptions-item>
                <el-descriptions-item label="对齐方式">左对齐</el-descriptions-item>
                <el-descriptions-item label="段间距">2倍行距</el-descriptions-item>
                <el-descriptions-item label="段前距">0.5行</el-descriptions-item>
                <el-descriptions-item label="段后距">0.5行</el-descriptions-item>
                <el-descriptions-item label="是否加粗"><el-tag size="small">是</el-tag></el-descriptions-item>

                <el-descriptions-item label="二级标题要求" width="300px" >即正文中的二级标题格式</el-descriptions-item>
                <el-descriptions-item label="中文字体">宋体</el-descriptions-item>
                <el-descriptions-item label="英文字体">Times New Roman</el-descriptions-item>
                <el-descriptions-item label="字体大小">36</el-descriptions-item>
                <el-descriptions-item label="对齐方式">左对齐</el-descriptions-item>
                <el-descriptions-item label="段间距">2倍行距</el-descriptions-item>
                <el-descriptions-item label="段前距">0.5行</el-descriptions-item>
                <el-descriptions-item label="段后距">0.5行</el-descriptions-item>
                <el-descriptions-item label="是否加粗"><el-tag size="small">是</el-tag></el-descriptions-item>

                <el-descriptions-item label="三级标题要求" width="300px" >即正文中的三级标题格式</el-descriptions-item>
                <el-descriptions-item label="中文字体">宋体</el-descriptions-item>
                <el-descriptions-item label="英文字体">Times New Roman</el-descriptions-item>
                <el-descriptions-item label="字体大小">36</el-descriptions-item>
                <el-descriptions-item label="对齐方式">左对齐</el-descriptions-item>
                <el-descriptions-item label="段间距">2倍行距</el-descriptions-item>
                <el-descriptions-item label="段前距">0.5行</el-descriptions-item>
                <el-descriptions-item label="段后距">0.5行</el-descriptions-item>
                <el-descriptions-item label="是否加粗"><el-tag size="small">是</el-tag></el-descriptions-item>

                <el-descriptions-item label="正文段落要求" >即正文内容格式</el-descriptions-item>
                <el-descriptions-item label="中文字体">宋体</el-descriptions-item>
                <el-descriptions-item label="英文字体">Times New Roman</el-descriptions-item>
                <el-descriptions-item label="字体大小">36</el-descriptions-item>
                <el-descriptions-item label="段落首行缩进">两个字符</el-descriptions-item>
                <el-descriptions-item label="段间距">2倍行距</el-descriptions-item>
                <el-descriptions-item label="是否加粗"><el-tag size="small">是</el-tag></el-descriptions-item>
              </el-descriptions>
            </el-collapse-item>
            <el-collapse-item name="6">
              <template #title>
                <h2>结论</h2>
              </template>
              <el-descriptions direction="vertical" column="9" border>
                <el-descriptions-item label="标题要求" width="300px" >即“结论”标题格式</el-descriptions-item>
                <el-descriptions-item label="中文字体">宋体</el-descriptions-item>
                <el-descriptions-item label="英文字体">Times New Roman</el-descriptions-item>
                <el-descriptions-item label="字体大小">36</el-descriptions-item>
                <el-descriptions-item label="对齐方式">左对齐</el-descriptions-item>
                <el-descriptions-item label="段间距">2倍行距</el-descriptions-item>
                <el-descriptions-item label="段前距">0.5行</el-descriptions-item>
                <el-descriptions-item label="段后距">0.5行</el-descriptions-item>
                <el-descriptions-item label="是否加粗"><el-tag size="small">是</el-tag></el-descriptions-item>

                <el-descriptions-item label="正文段落要求" >即“结论”正文内容格式</el-descriptions-item>
                <el-descriptions-item label="中文字体">宋体</el-descriptions-item>
                <el-descriptions-item label="英文字体">Times New Roman</el-descriptions-item>
                <el-descriptions-item label="字体大小">36</el-descriptions-item>
                <el-descriptions-item label="段落首行缩进">两个字符</el-descriptions-item>
                <el-descriptions-item label="段间距">2倍行距</el-descriptions-item>
                <el-descriptions-item label="是否加粗"><el-tag size="small">是</el-tag></el-descriptions-item>
              </el-descriptions>
            </el-collapse-item>

            <el-collapse-item name="7">
              <template #title>
                <h2>致谢</h2>
              </template>
              <el-descriptions direction="vertical" column="9" border>
                <el-descriptions-item label="标题要求" width="300px" >即“致谢”标题格式</el-descriptions-item>
                <el-descriptions-item label="中文字体">宋体</el-descriptions-item>
                <el-descriptions-item label="英文字体">Times New Roman</el-descriptions-item>
                <el-descriptions-item label="字体大小">36</el-descriptions-item>
                <el-descriptions-item label="对齐方式">左对齐</el-descriptions-item>
                <el-descriptions-item label="段间距">2倍行距</el-descriptions-item>
                <el-descriptions-item label="段前距">0.5行</el-descriptions-item>
                <el-descriptions-item label="段后距">0.5行</el-descriptions-item>
                <el-descriptions-item label="是否加粗"><el-tag size="small">是</el-tag></el-descriptions-item>

                <el-descriptions-item label="正文段落要求" >即“致谢”正文内容格式</el-descriptions-item>
                <el-descriptions-item label="中文字体">宋体</el-descriptions-item>
                <el-descriptions-item label="英文字体">Times New Roman</el-descriptions-item>
                <el-descriptions-item label="字体大小">36</el-descriptions-item>
                <el-descriptions-item label="段落首行缩进">两个字符</el-descriptions-item>
                <el-descriptions-item label="段间距">2倍行距</el-descriptions-item>
                <el-descriptions-item label="是否加粗"><el-tag size="small">是</el-tag></el-descriptions-item>
              </el-descriptions>
            </el-collapse-item>

            <el-collapse-item name="8">
              <template #title>
                <h2>参考文献</h2>
              </template>
              <el-descriptions direction="vertical" column="9" border>

                <el-descriptions-item label="参考文献说明" span="9" >参考文献引用格式：国标GB/T 7714-2015；检测会从正文中检测参考文献的应用是否正确，出现无效引用、引用格式错误、未应用的参考文献时会进行提醒。</el-descriptions-item>

                <el-descriptions-item label="标题要求" width="300px" >即“参考文献”标题格式</el-descriptions-item>
                <el-descriptions-item label="中文字体">宋体</el-descriptions-item>
                <el-descriptions-item label="英文字体">Times New Roman</el-descriptions-item>
                <el-descriptions-item label="字体大小">36</el-descriptions-item>
                <el-descriptions-item label="对齐方式">左对齐</el-descriptions-item>
                <el-descriptions-item label="段间距">2倍行距</el-descriptions-item>
                <el-descriptions-item label="段前距">0.5行</el-descriptions-item>
                <el-descriptions-item label="段后距">0.5行</el-descriptions-item>
                <el-descriptions-item label="是否加粗"><el-tag size="small">是</el-tag></el-descriptions-item>

                <el-descriptions-item label="正文段落要求" >即“结论”正文内容格式</el-descriptions-item>
                <el-descriptions-item label="中文字体">宋体</el-descriptions-item>
                <el-descriptions-item label="英文字体">Times New Roman</el-descriptions-item>
                <el-descriptions-item label="字体大小">36</el-descriptions-item>
                <el-descriptions-item label="段落首行缩进">无缩进</el-descriptions-item>
                <el-descriptions-item label="段间距">2倍行距</el-descriptions-item>
                <el-descriptions-item label="是否加粗"><el-tag size="small">否</el-tag></el-descriptions-item>
              </el-descriptions>
            </el-collapse-item>
          </el-collapse>
        </div>

      </el-scrollbar>

    </div>

  </div>
</template>

<style scoped>

</style>


<script setup lang="ts">
import {computed, reactive, ref} from "vue";
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

const value = ref('') // 设置默认值
const options = reactive({
  templates: []
})
const templateData = reactive([]) // 模板数据


const activeNames = ref(['9', '10', '1','2','3','4','5']) // 控制展示框的默认展开项
const handleChange = (val: string[]) => {
  console.log(val)
}

const load = () => {
  request.get('/getTemplateListForStudent').then(res => {
    options.templates = res.data.filter((template: Template) => template.status === 1);
  });
};

const loadTemplateData = () => {
  request.get('/getTemplateDataById').then(res => {
    templateData = res.data;
  });
};

load();  // 页面加载时加载数据

</script>