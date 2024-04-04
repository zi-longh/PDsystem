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
        <el-button type="primary" style="margin-left: 10px" @click="loadTemplateData">查看</el-button>

        <span style="margin-left: 15px">当前查看的模板名称：{{ newValue }}</span>
      </p>
    </div>

    <div class="card" style="margin-bottom: 10px">
      <el-scrollbar height="650px">
        <div class="demo-collapse">
          <el-collapse v-model="activeNames" @change="handleChange" style="margin: 12px">
            <el-collapse-item name="9">
              <template #title>
                <h2>页面页眉页脚设置</h2>
              </template>
              <el-descriptions direction="vertical" column="9" border>
                <el-descriptions-item label="正文前的页眉内容" span="3">{{
                    data.templateData.pagesettingHeadercontent1
                  }}
                </el-descriptions-item>
                <el-descriptions-item label="正文和正文后的页眉内容" span="3">{{
                    data.templateData.pagesettingHeadercontent2
                  }}
                </el-descriptions-item>
                <el-descriptions-item label="页眉页脚字体类别" span="1">宋体</el-descriptions-item>
                <el-descriptions-item label="页眉页脚字体大小" span="1">“小五”</el-descriptions-item>
                <el-descriptions-item label="页眉内容位置" span="1">居中对齐</el-descriptions-item>
                <el-descriptions-item label="页上边距">{{ data.templateData.pagesettingTopmargin }}</el-descriptions-item>
                <el-descriptions-item label="页下边距">{{ data.templateData.pagesettingBottommargin }}</el-descriptions-item>
                <el-descriptions-item label="页左边距">{{ data.templateData.pagesettingLeftmargin }}</el-descriptions-item>
                <el-descriptions-item label="页右边距">{{ data.templateData.pagesettingRightmargin }}</el-descriptions-item>
                <el-descriptions-item label="页眉边距">{{ data.templateData.pagesettingHeadermargin }}</el-descriptions-item>
                <el-descriptions-item label="页脚边距">{{ data.templateData.pagesettingBottommargin }}</el-descriptions-item>
                <el-descriptions-item label="是否区分奇偶页插入页脚">
                  <el-tag size="small">是</el-tag>
                </el-descriptions-item>
              </el-descriptions>
            </el-collapse-item>
            <el-collapse-item name="10">
              <template #title>
                <h2>图表和图注表注</h2>
              </template>
              <el-descriptions direction="vertical" column="8" border>
                <el-descriptions-item label="图注表注中文字体">{{ data.templateData.captionFontname }}</el-descriptions-item>
                <el-descriptions-item label="图注表注英文字体">{{ data.templateData.captionFontenglishname }}
                </el-descriptions-item>
                <el-descriptions-item label="图注表注字体大小">{{ data.templateData.captionFontsize }}</el-descriptions-item>
                <el-descriptions-item label="推荐的图片最小宽度">{{ data.templateData.captionPicszheightmin }}
                </el-descriptions-item>
                <el-descriptions-item label="推荐的图片最大宽度">{{ data.templateData.captionPicszwidthmax }}
                </el-descriptions-item>
                <el-descriptions-item label="推荐的图片最小长度">{{ data.templateData.captionPicszheightmin }}
                </el-descriptions-item>
                <el-descriptions-item label="推荐的图片最大长度">{{ data.templateData.captionPicszheightmax }}
                </el-descriptions-item>
                <el-descriptions-item label="推荐图表数量">{{ data.templateData.captionRecommendnum }}</el-descriptions-item>
                <el-descriptions-item label="图注表注说明" span="8">
                  论文中的每个图片都应该有图注，图注位置应该在图片的正下方；论文中的每个表格都应该有表注，表注的位置应该在表格的正上方。
                </el-descriptions-item>
              </el-descriptions>
            </el-collapse-item>

            <el-collapse-item name="1">
              <template #title>
                <h2>诚信说明</h2>
              </template>
              <el-descriptions direction="vertical" column="9" border>
                <el-descriptions-item label="诚信说明正文内容" span="9">{{ data.templateData.sohContent }}
                </el-descriptions-item>

                <el-descriptions-item label="标题要求" width="300px">即“诚信说明”标题格式</el-descriptions-item>
                <el-descriptions-item label="中文字体">{{ data.templateData.sohHFontType }}</el-descriptions-item>
                <el-descriptions-item label="英文字体">{{ data.templateData.sohHFontEnglishType }}</el-descriptions-item>
                <el-descriptions-item label="字体大小">{{ data.templateData.sohHFontSz }}</el-descriptions-item>
                <el-descriptions-item label="对齐方式">{{ data.templateData.sohHJc }}</el-descriptions-item>
                <el-descriptions-item label="段间距">{{ data.templateData.sohHLine }}</el-descriptions-item>
                <el-descriptions-item label="段前距">{{ data.templateData.sohHBeforeline }}</el-descriptions-item>
                <el-descriptions-item label="段后距">{{ data.templateData.sohHAfterline }}</el-descriptions-item>
                <el-descriptions-item label="是否加粗">
                  <el-tag size="small">{{ data.templateData.sohHBold }}</el-tag>
                </el-descriptions-item>

                <el-descriptions-item label="正文段落要求">即“诚信说明”正文要求</el-descriptions-item>
                <el-descriptions-item label="中文字体">{{ data.templateData.sohPFontType }}</el-descriptions-item>
                <el-descriptions-item label="英文字体">{{ data.templateData.sohPFontTypeEnglish }}</el-descriptions-item>
                <el-descriptions-item label="字体大小">{{ data.templateData.sohPFontSz }}</el-descriptions-item>
                <el-descriptions-item label="段落首行缩进">{{ data.templateData.sohPInd }}</el-descriptions-item>
                <el-descriptions-item label="段间距">{{ data.templateData.sohPLine }}</el-descriptions-item>
                <el-descriptions-item label="是否加粗">
                  <el-tag size="small">{{ data.templateData.sohPBold }}</el-tag>
                </el-descriptions-item>
              </el-descriptions>
            </el-collapse-item>
            <el-collapse-item name="2">
              <template #title>
                <h2>中文摘要</h2>
              </template>
              <el-descriptions direction="vertical" column="9" border>
                <el-descriptions-item label="前置词（即“摘要”、“关键字”）的字体" width="300px" span="1">
                  {{ data.templateData.aocPrefixfont }}
                </el-descriptions-item>
                <el-descriptions-item label="摘要内容推荐的最大字数" span="1">
                  {{ data.templateData.aocRecommendedmaxcontentlength }}
                </el-descriptions-item>
                <el-descriptions-item label="摘要内容推荐的最小字数" span="1">
                  {{ data.templateData.aocRecommendedmincontentlength }}
                </el-descriptions-item>
                <el-descriptions-item label="关键词推荐最大数量" span="1">
                  {{ data.templateData.aocRecommendedmaxkeywordscount }}
                </el-descriptions-item>
                <el-descriptions-item label="关键词推荐最小数量" span="1">
                  {{ data.templateData.aocRecommendedminkeywordscount }}
                </el-descriptions-item>
                <el-descriptions-item label="前置词是否加粗" span="4">
                  <el-tag size="small">{{ data.templateData.aocPBold }}</el-tag>
                </el-descriptions-item>

                <el-descriptions-item label="标题要求" width="300px">即“中文摘要”上方的论文标题的格式
                </el-descriptions-item>
                <el-descriptions-item label="中文字体">{{ data.templateData.aocHFontType }}</el-descriptions-item>
                <el-descriptions-item label="英文字体">{{ data.templateData.aocHFontEnglishType }}</el-descriptions-item>
                <el-descriptions-item label="字体大小">{{ data.templateData.aocHFontSz }}</el-descriptions-item>
                <el-descriptions-item label="对齐方式">{{ data.templateData.aocHJc }}</el-descriptions-item>
                <el-descriptions-item label="段间距">{{ data.templateData.aocHLine }}</el-descriptions-item>
                <el-descriptions-item label="段前距">{{ data.templateData.aocHBeforeline }}</el-descriptions-item>
                <el-descriptions-item label="段后距">{{ data.templateData.aocHAfterline }}</el-descriptions-item>
                <el-descriptions-item label="是否加粗">
                  <el-tag size="small">{{ data.templateData.aocHBold }}</el-tag>
                </el-descriptions-item>

                <el-descriptions-item label="正文段落要求">即“中文摘要”正文的格式要求</el-descriptions-item>
                <el-descriptions-item label="中文字体">{{ data.templateData.aocPFontType }}</el-descriptions-item>
                <el-descriptions-item label="英文字体">{{ data.templateData.aocPFontTypeEnglish }}</el-descriptions-item>
                <el-descriptions-item label="字体大小">{{ data.templateData.aocPFontSz }}</el-descriptions-item>
                <el-descriptions-item label="段落首行缩进">{{ data.templateData.aocPInd }}</el-descriptions-item>
                <el-descriptions-item label="段间距">{{ data.templateData.aocPLine }}</el-descriptions-item>
                <el-descriptions-item label="是否加粗">
                  <el-tag size="small">{{ data.templateData.aocPBold }}</el-tag>
                </el-descriptions-item>

              </el-descriptions>

            </el-collapse-item>
            <el-collapse-item name="3">
              <template #title>
                <h2>英文摘要</h2>
              </template>
              <el-descriptions direction="vertical" column="9" border>
                <el-descriptions-item label="前置词（即“Abstract”、“Keywords”）的字体" width="300px" span="1">
                  {{ data.templateData.aoePrefixfont }}
                </el-descriptions-item>
                <el-descriptions-item label="摘要内容推荐的最大字数" span="1">
                  {{ data.templateData.aoeRecommendedmaxcontentlength }}
                </el-descriptions-item>
                <el-descriptions-item label="摘要内容推荐的最小字数" span="1">
                  {{ data.templateData.aoeRecommendedmincontentlength }}
                </el-descriptions-item>
                <el-descriptions-item label="关键词推荐最大数量" span="1">
                  {{ data.templateData.aoeRecommendedmaxkeywordscount }}
                </el-descriptions-item>
                <el-descriptions-item label="关键词推荐最小数量" span="1">
                  {{ data.templateData.aoeRecommendedminkeywordscount }}
                </el-descriptions-item>
                <el-descriptions-item label="前置词是否加粗" span="4">
                  <el-tag size="small">{{ data.templateData.aoePBold }}</el-tag>
                </el-descriptions-item>

                <el-descriptions-item label="标题要求" width="300px">即“英文摘要”上方的论文英文标题格式
                </el-descriptions-item>
                <el-descriptions-item label="中文字体">{{ data.templateData.aoeHFontType }}</el-descriptions-item>
                <el-descriptions-item label="英文字体">{{ data.templateData.aoeHFontEnglishType }}</el-descriptions-item>
                <el-descriptions-item label="字体大小">{{ data.templateData.aoeHFontSz }}</el-descriptions-item>
                <el-descriptions-item label="对齐方式">{{ data.templateData.aoeHJc }}</el-descriptions-item>
                <el-descriptions-item label="段间距">{{ data.templateData.aoeHLine }}</el-descriptions-item>
                <el-descriptions-item label="段前距">{{ data.templateData.aoeHBeforeline }}</el-descriptions-item>
                <el-descriptions-item label="段后距">{{ data.templateData.aoeHAfterline }}</el-descriptions-item>
                <el-descriptions-item label="是否加粗">
                  <el-tag size="small">{{ data.templateData.aoeHBold }}</el-tag>
                </el-descriptions-item>

                <el-descriptions-item label="正文段落要求">即“英文摘要”正文要求</el-descriptions-item>
                <el-descriptions-item label="中文字体">{{ data.templateData.aoePFontType }}</el-descriptions-item>
                <el-descriptions-item label="英文字体">{{ data.templateData.aoePFontTypeEnglish }}</el-descriptions-item>
                <el-descriptions-item label="字体大小">{{ data.templateData.aoePFontSz }}</el-descriptions-item>
                <el-descriptions-item label="段落首行缩进">{{ data.templateData.aoePInd }}</el-descriptions-item>
                <el-descriptions-item label="段间距">{{ data.templateData.aoePLine }}</el-descriptions-item>
                <el-descriptions-item label="是否加粗">
                  <el-tag size="small">{{ data.templateData.aoePBold }}</el-tag>
                </el-descriptions-item>

              </el-descriptions>
            </el-collapse-item>
            <el-collapse-item name="4">
              <template #title>
                <h2>目录</h2>
              </template>
              <el-descriptions direction="vertical" column="9" border>

                <el-descriptions-item label="相关说明" span="9">
                  目录正文会强制检查是否有内容缺失，目录最小应包含到3级标题，若有缺失则检测会不通过！
                </el-descriptions-item>
                <el-descriptions-item label="标题要求" width="300px">即标题“目录”二字的格式</el-descriptions-item>
                <el-descriptions-item label="中文字体">{{ data.templateData.catalogueHFontType }}</el-descriptions-item>
                <el-descriptions-item label="英文字体">{{ data.templateData.catalogueHFontEnglishType }}
                </el-descriptions-item>
                <el-descriptions-item label="字体大小">{{ data.templateData.catalogueHFontSz }}</el-descriptions-item>
                <el-descriptions-item label="对齐方式">{{ data.templateData.catalogueHJc }}</el-descriptions-item>
                <el-descriptions-item label="段间距">{{ data.templateData.catalogueHLine }}</el-descriptions-item>
                <el-descriptions-item label="段前距">{{ data.templateData.catalogueHBeforeline }}</el-descriptions-item>
                <el-descriptions-item label="段后距">{{ data.templateData.catalogueHAfterline }}</el-descriptions-item>
                <el-descriptions-item label="是否加粗">
                  <el-tag size="small">{{ data.templateData.catalogueHBold }}</el-tag>
                </el-descriptions-item>

              </el-descriptions>
            </el-collapse-item>
            <el-collapse-item name="5">
              <template #title>
                <h2>绪论和正文</h2>
              </template>
              <el-descriptions direction="vertical" column="9" border>
                <el-descriptions-item label="一级标题要求" width="300px">一级标题的格式要求</el-descriptions-item>
                <el-descriptions-item label="中文字体">{{ data.templateData.mainbodyH1FontType }}</el-descriptions-item>
                <el-descriptions-item label="英文字体">{{ data.templateData.mainbodyH1FontEnglishType }}
                </el-descriptions-item>
                <el-descriptions-item label="字体大小">{{ data.templateData.mainbodyH1FontSz }}</el-descriptions-item>
                <el-descriptions-item label="对齐方式">{{ data.templateData.mainbodyH1Jc }}</el-descriptions-item>
                <el-descriptions-item label="段间距">{{ data.templateData.mainbodyH1Line }}</el-descriptions-item>
                <el-descriptions-item label="段前距">{{ data.templateData.mainbodyH1Beforeline }}</el-descriptions-item>
                <el-descriptions-item label="段后距">{{ data.templateData.mainbodyH1Afterline }}</el-descriptions-item>
                <el-descriptions-item label="是否加粗">
                  <el-tag size="small">{{ data.templateData.mainbodyH1Bold }}</el-tag>
                </el-descriptions-item>


                <el-descriptions-item label="二级标题要求" width="300px">二级标题的格式要求</el-descriptions-item>
                <el-descriptions-item label="中文字体">{{ data.templateData.mainbodyH2FontType }}</el-descriptions-item>
                <el-descriptions-item label="英文字体">{{ data.templateData.mainbodyH2FontEnglishType }}
                </el-descriptions-item>
                <el-descriptions-item label="字体大小">{{ data.templateData.mainbodyH2FontSz }}</el-descriptions-item>
                <el-descriptions-item label="对齐方式">{{ data.templateData.mainbodyH2Jc }}</el-descriptions-item>
                <el-descriptions-item label="段间距">{{ data.templateData.mainbodyH2Line }}</el-descriptions-item>
                <el-descriptions-item label="段前距">{{ data.templateData.mainbodyH2Beforeline }}</el-descriptions-item>
                <el-descriptions-item label="段后距">{{ data.templateData.mainbodyH2Afterline }}</el-descriptions-item>
                <el-descriptions-item label="是否加粗">
                  <el-tag size="small">{{ data.templateData.mainbodyH2Bold }}</el-tag>
                </el-descriptions-item>

                <el-descriptions-item label="三级标题要求" width="300px">三级标题的格式要求</el-descriptions-item>
                <el-descriptions-item label="中文字体">{{ data.templateData.mainbodyH3FontType }}</el-descriptions-item>
                <el-descriptions-item label="英文字体">{{ data.templateData.mainbodyH3FontEnglishType }}
                </el-descriptions-item>
                <el-descriptions-item label="字体大小">{{ data.templateData.mainbodyH3FontSz }}</el-descriptions-item>
                <el-descriptions-item label="对齐方式">{{ data.templateData.mainbodyH3Jc }}</el-descriptions-item>
                <el-descriptions-item label="段间距">{{ data.templateData.mainbodyH3Line }}</el-descriptions-item>
                <el-descriptions-item label="段前距">{{ data.templateData.mainbodyH3Beforeline }}</el-descriptions-item>
                <el-descriptions-item label="段后距">{{ data.templateData.mainbodyH3Afterline }}</el-descriptions-item>
                <el-descriptions-item label="是否加粗">
                  <el-tag size="small">{{ data.templateData.mainbodyH3Bold }}</el-tag>
                </el-descriptions-item>

                <el-descriptions-item label="正文段落要求">即绪论和正文的格式要求</el-descriptions-item>
                <el-descriptions-item label="中文字体">{{ data.templateData.mainbodyPFontType }}</el-descriptions-item>
                <el-descriptions-item label="英文字体">{{ data.templateData.mainbodyPFontTypeEnglish }}
                </el-descriptions-item>
                <el-descriptions-item label="字体大小">{{ data.templateData.mainbodyPFontSz }}</el-descriptions-item>
                <el-descriptions-item label="段落首行缩进">{{ data.templateData.mainbodyPInd }}</el-descriptions-item>
                <el-descriptions-item label="段间距">{{ data.templateData.mainbodyPLine }}</el-descriptions-item>
                <el-descriptions-item label="是否加粗">
                  <el-tag size="small">{{ data.templateData.mainbodyPBold }}</el-tag>
                </el-descriptions-item>


              </el-descriptions>
            </el-collapse-item>
            <el-collapse-item name="6">
              <template #title>
                <h2>结论</h2>
              </template>
              <el-descriptions direction="vertical" column="9" border>
                <el-descriptions-item label="标题要求" width="300px">即“结论”标题格式</el-descriptions-item>
                <el-descriptions-item label="中文字体">{{ data.templateData.conclusionHFontType }}</el-descriptions-item>
                <el-descriptions-item label="英文字体">{{ data.templateData.conclusionHFontEnglishType }}
                </el-descriptions-item>
                <el-descriptions-item label="字体大小">{{ data.templateData.conclusionHFontSz }}</el-descriptions-item>
                <el-descriptions-item label="对齐方式">{{ data.templateData.conclusionHJc }}</el-descriptions-item>
                <el-descriptions-item label="段间距">{{ data.templateData.conclusionHLine }}</el-descriptions-item>
                <el-descriptions-item label="段前距">{{ data.templateData.conclusionHBeforeline }}</el-descriptions-item>
                <el-descriptions-item label="段后距">{{ data.templateData.conclusionHAfterline }}</el-descriptions-item>
                <el-descriptions-item label="是否加粗">
                  <el-tag size="small">{{ data.templateData.conclusionHBold }}</el-tag>
                </el-descriptions-item>

                <el-descriptions-item label="正文段落要求">即“结论”正文要求</el-descriptions-item>
                <el-descriptions-item label="中文字体">{{ data.templateData.conclusionPFontType }}</el-descriptions-item>
                <el-descriptions-item label="英文字体">{{ data.templateData.conclusionPFontTypeEnglish }}
                </el-descriptions-item>
                <el-descriptions-item label="字体大小">{{ data.templateData.conclusionPFontSz }}</el-descriptions-item>
                <el-descriptions-item label="段落首行缩进">{{ data.templateData.conclusionPInd }}</el-descriptions-item>
                <el-descriptions-item label="段间距">{{ data.templateData.conclusionPLine }}</el-descriptions-item>
                <el-descriptions-item label="是否加粗">
                  <el-tag size="small">{{ data.templateData.conclusionPBold }}</el-tag>
                </el-descriptions-item>

              </el-descriptions>
            </el-collapse-item>

            <el-collapse-item name="7">
              <template #title>
                <h2>致谢</h2>
              </template>
              <el-descriptions direction="vertical" column="9" border>
                <el-descriptions-item label="标题要求" width="300px">即“致谢”标题格式</el-descriptions-item>
                <el-descriptions-item label="中文字体">{{ data.templateData.thanksHFontType }}</el-descriptions-item>
                <el-descriptions-item label="英文字体">{{ data.templateData.thanksHFontEnglishType }}</el-descriptions-item>
                <el-descriptions-item label="字体大小">{{ data.templateData.thanksHFontSz }}</el-descriptions-item>
                <el-descriptions-item label="对齐方式">{{ data.templateData.thanksHJc }}</el-descriptions-item>
                <el-descriptions-item label="段间距">{{ data.templateData.thanksHLine }}</el-descriptions-item>
                <el-descriptions-item label="段前距">{{ data.templateData.thanksHBeforeline }}</el-descriptions-item>
                <el-descriptions-item label="段后距">{{ data.templateData.thanksHAfterline }}</el-descriptions-item>
                <el-descriptions-item label="是否加粗">
                  <el-tag size="small">{{ data.templateData.thanksHBold }}</el-tag>
                </el-descriptions-item>

                <el-descriptions-item label="正文段落要求">即“致谢”正文要求</el-descriptions-item>
                <el-descriptions-item label="中文字体">{{ data.templateData.thanksPFontType }}</el-descriptions-item>
                <el-descriptions-item label="英文字体">{{ data.templateData.thanksPFontTypeEnglish }}</el-descriptions-item>
                <el-descriptions-item label="字体大小">{{ data.templateData.thanksPFontSz }}</el-descriptions-item>
                <el-descriptions-item label="段落首行缩进">{{ data.templateData.thanksPInd }}</el-descriptions-item>
                <el-descriptions-item label="段间距">{{ data.templateData.thanksPLine }}</el-descriptions-item>
                <el-descriptions-item label="是否加粗">
                  <el-tag size="small">{{ data.templateData.thanksPBold }}</el-tag>
                </el-descriptions-item>

              </el-descriptions>
            </el-collapse-item>

            <el-collapse-item name="8">
              <template #title>
                <h2>参考文献</h2>
              </template>
              <el-descriptions direction="vertical" column="9" border>

                <el-descriptions-item label="参考文献说明" span="9">参考文献引用格式：国标GB/T
                  7714-2015；检测会从正文中检测参考文献的应用是否正确，出现无效引用、引用格式错误、未应用的参考文献时会进行提醒。
                </el-descriptions-item>

                <el-descriptions-item label="标题要求" width="300px">即“参考文献”标题格式</el-descriptions-item>
                <el-descriptions-item label="中文字体">{{ data.templateData.referencesHFontType }}</el-descriptions-item>
                <el-descriptions-item label="英文字体">{{ data.templateData.referencesHFontEnglishType }}
                </el-descriptions-item>
                <el-descriptions-item label="字体大小">{{ data.templateData.referencesHFontSz }}</el-descriptions-item>
                <el-descriptions-item label="对齐方式">{{ data.templateData.referencesHJc }}</el-descriptions-item>
                <el-descriptions-item label="段间距">{{ data.templateData.referencesHLine }}</el-descriptions-item>
                <el-descriptions-item label="段前距">{{ data.templateData.referencesHBeforeline }}</el-descriptions-item>
                <el-descriptions-item label="段后距">{{ data.templateData.referencesHAfterline }}</el-descriptions-item>
                <el-descriptions-item label="是否加粗">
                  <el-tag size="small">{{ data.templateData.referencesHBold }}</el-tag>
                </el-descriptions-item>

                <el-descriptions-item label="正文段落要求">即“参考文献”正文要求</el-descriptions-item>
                <el-descriptions-item label="中文字体">{{ data.templateData.referencesPFontType }}</el-descriptions-item>
                <el-descriptions-item label="英文字体">{{ data.templateData.referencesPFontTypeEnglish }}
                </el-descriptions-item>
                <el-descriptions-item label="字体大小">{{ data.templateData.referencesPFontSz }}</el-descriptions-item>
                <el-descriptions-item label="段落首行缩进">{{ data.templateData.referencesPInd }}</el-descriptions-item>
                <el-descriptions-item label="段间距">{{ data.templateData.referencesPLine }}</el-descriptions-item>
                <el-descriptions-item label="是否加粗">
                  <el-tag size="small">{{ data.templateData.referencesPBold }}</el-tag>
                </el-descriptions-item>

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

interface TemplateInfo {
  templateId: string;
  sohFlag: string;
  sohSeq: string;
  sohContent: string;
  sohHFontType: string;
  sohHFontEnglishType: string;
  sohHFontSz: string;
  sohHJc: string;
  sohHBold: string;
  sohHAfterline: string;
  sohHBeforeline: string;
  sohHLine: string;
  sohPFontType: string;
  sohPFontTypeEnglish: string;
  sohPFontSz: string;
  sohPInd: string;
  sohPLine: string;
  sohPBold: string;
  aocFlag: string;
  aocSeq: string;
  aocPrefixfont: string;
  aocIsprefixbold: string;
  aocRecommendedmaxcontentlength: string;
  aocRecommendedmincontentlength: string;
  aocRecommendedmaxkeywordscount: string;
  aocRecommendedminkeywordscount: string;
  aocHFontType: string;
  aocHFontEnglishType: string;
  aocHFontSz: string;
  aocHJc: string;
  aocHBold: string;
  aocHAfterline: string;
  aocHBeforeline: string;
  aocHLine: string;
  aocPFontType: string;
  aocPFontTypeEnglish: string;
  aocPFontSz: string;
  aocPInd: string;
  aocPLine: string;
  aocPBold: string;
  aoeFlag: string;
  aoeSeq: string;
  aoePrefixfont: string;
  aoeIsprefixbold: string;
  aoeRecommendedmaxcontentlength: string;
  aoeRecommendedmincontentlength: string;
  aoeRecommendedmaxkeywordscount: string;
  aoeRecommendedminkeywordscount: string;
  aoeHFontType: string;
  aoeHFontEnglishType: string;
  aoeHFontSz: string;
  aoeHJc: string;
  aoeHBold: string;
  aoeHAfterline: string;
  aoeHBeforeline: string;
  aoeHLine: string;
  aoePFontType: string;
  aoePFontTypeEnglish: string;
  aoePFontSz: string;
  aoePInd: string;
  aoePLine: string;
  aoePBold: string;
  catalogueFlag: string;
  catalogueSeq: string;
  catalogueHFontType: string;
  catalogueHFontEnglishType: string;
  catalogueHFontSz: string;
  catalogueHJc: string;
  catalogueHBold: string;
  catalogueHAfterline: string;
  catalogueHBeforeline: string;
  catalogueHLine: string;
  mainbodyFlag: string;
  mainbodySeq: string;
  mainbodyH1FontType: string;
  mainbodyH1FontEnglishType: string;
  mainbodyH1FontSz: string;
  mainbodyH1Jc: string;
  mainbodyH1Bold: string;
  mainbodyH1Afterline: string;
  mainbodyH1Beforeline: string;
  mainbodyH1Line: string;
  mainbodyH2FontType: string;
  mainbodyH2FontEnglishType: string;
  mainbodyH2FontSz: string;
  mainbodyH2Jc: string;
  mainbodyH2Bold: string;
  mainbodyH2Afterline: string;
  mainbodyH2Beforeline: string;
  mainbodyH2Line: string;
  mainbodyH3FontType: string;
  mainbodyH3FontEnglishType: string;
  mainbodyH3FontSz: string;
  mainbodyH3Jc: string;
  mainbodyH3Bold: string;
  mainbodyH3Afterline: string;
  mainbodyH3Beforeline: string;
  mainbodyH3Line: string;
  mainbodyPFontType: string;
  mainbodyPFontTypeEnglish: string;
  mainbodyPFontSz: string;
  mainbodyPInd: string;
  mainbodyPLine: string;
  mainbodyPBold: string;
  conclusionFlag: string;
  conclusionSeq: string;
  conclusionRecommendedmaxcontentlength: string;
  conclusionHFontType: string;
  conclusionHFontEnglishType: string;
  conclusionHFontSz: string;
  conclusionHJc: string;
  conclusionHBold: string;
  conclusionHAfterline: string;
  conclusionHBeforeline: string;
  conclusionHLine: string;
  conclusionPFontType: string;
  conclusionPFontTypeEnglish: string;
  conclusionPFontSz: string;
  conclusionPInd: string;
  conclusionPLine: string;
  conclusionPBold: string;
  thanksFlag: string;
  thanksSeq: string;
  thanksRecommendedmaxcontentlength: string;
  thanksHFontType: string;
  thanksHFontEnglishType: string;
  thanksHFontSz: string;
  thanksHJc: string;
  thanksHBold: string;
  thanksHAfterline: string;
  thanksHBeforeline: string;
  thanksHLine: string;
  thanksPFontType: string;
  thanksPFontTypeEnglish: string;
  thanksPFontSz: string;
  thanksPInd: string;
  thanksPLine: string;
  thanksPBold: string;
  referencesFlag: string;
  referencesSeq: string;
  referencesRecommendedmincount: string;
  referencesHFontType: string;
  referencesHFontEnglishType: string;
  referencesHFontSz: string;
  referencesHJc: string;
  referencesHBold: string;
  referencesHAfterline: string;
  referencesHBeforeline: string;
  referencesHLine: string;
  referencesPFontType: string;
  referencesPFontTypeEnglish: string;
  referencesPFontSz: string;
  referencesPInd: string;
  referencesPLine: string;
  referencesPBold: string;
  captionFontname: string;
  captionFontenglishname: string;
  captionFontsize: string;
  captionPicszwidthmin: string;
  captionPicszwidthmax: string;
  captionPicszheightmin: string;
  captionPicszheightmax: string;
  captionRecommendnum: string;
  pagesettingHeadercontent1: string
  pagesettingHeadercontent2: string
  pagesettingTopmargin: string
  pagesettingBottommargin: string
  pagesettingLeftmargin: string
  pagesettingRightmargin: string
  pagesettingHeadermargin: string
  pagesettingFootermargin: string
  pagesettingOddevenpage: string
}

const value = ref("") // 设置默认值
const newValue = ref("") // 当前查看的论文模板
const options = reactive({
  templates: []
})
// let templateData:{ TemplateInfo: string } = {
// };

const data = reactive({
  templateData: []
})


const activeNames = ref([]) // 控制展示框的默认展开项
const handleChange = (val: string[]) => {
  console.log(val)
}

const load = () => {
  request.get('/getTemplateListForTeacher').then(res => {
    options.templates = res.data;
  });

};

load();  // 页面加载时加载数据

const loadTemplateData = () => {
  let x = value.value.split("-")[0].toString();
  request.get('/getTemplateInfoById', {
    params: {
      template_id: x
    }
  }).then(res => {
    data.templateData = res.data;
  });
  activeNames.value = ['9', '10', '1', '2', '3', '4', '5', '6', '7', '8'];
  newValue.value = value.value;
};

const init = () => {
  // 读取存储的模板id
  let templateId = localStorage.getItem("templateId");
  let templateName = localStorage.getItem("templateName");
  if (templateId) { // 如果有值
    value.value = templateId + "-" + templateName;
    loadTemplateData();
  }

};
init();


</script>