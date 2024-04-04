<template>
  <div>
    <div class="card" style="margin-bottom: 10px;">
      <p style="margin: 5px;">
        <span style="font-size: 24px; font-weight: bold">创建新模板 </span>
        <span style="margin-left: 15px; font-style: italic; color: #888; font-size: small"> 说明：下方填写模板基本信息，并设定格式规范，最后点击右侧“创建”按钮创建您的论文模板。 </span>
      </p>

      <p style="margin-left: 5px; margin-bottom: 1px">
        <el-form :model="data.baseData" :inline="true" :rules="rules" label-width="auto">
          <el-form-item prop="templateName" label="模板名称" style="width: 400px">
            <el-input v-model="data.baseData.templateName"/>
          </el-form-item>
          <el-form-item label="创建者" style="width: 300px">
            <el-input v-model="data.teacherData.name" :disabled="true"/>
          </el-form-item>
          <el-form-item label="是否上线本模板" style="margin-left: 65px">
            <el-switch v-model="data.baseData.status"/>
          </el-form-item>
          <el-form-item label="描述信息" style="width: 800px">
            <el-input v-model="data.baseData.description" type="textarea"/>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" style="margin-right: 10px;" @click="createTemplate">创建新模板</el-button>
          </el-form-item>
        </el-form>
      </p>
    </div>

    <div class="card" style="margin-bottom: 10px; height: 600px">
      <el-scrollbar>
        <p style="margin: 5px;">
          <span style="font-size: 24px; font-weight: bold">设置具体格式规范 </span>
          <span style="margin-left: 15px; font-style: italic; color: #888; font-size: small"> 说明：设定格式规范。 </span>
        </p>
        <el-form :model="data.templateData" :inline="true" :rules="rules" label-width="left" style="margin-left: 5px">
          <p><span style="font-size: 15px; font-weight: bold">页面页眉页脚设置 </span></p>
          <el-form-item prop="pagesettingHeadercontent1" label="正文的前页眉内容" style="width: 470px">
            <el-input v-model="data.templateData.pagesettingHeadercontent1" />
          </el-form-item>

          <el-form-item label="页上边距">
            <el-select v-model="data.templateData.pagesettingTopmargin" placeholder="please select"
                       style="width: 100px">
              <el-option label="1.0厘米" value="567"/>
              <el-option label="1.5厘米" value="850"/>
              <el-option label="2.0厘米" value="1134"/>
              <el-option label="2.5厘米" value="1417"/>
              <el-option label="3.0厘米" value="1701"/>
              <el-option label="3.5厘米" value="1984"/>
              <el-option label="4.0厘米" value="2268"/>
            </el-select>
          </el-form-item>
          <el-form-item label="页下边距">
            <el-select v-model="data.templateData.pagesettingBottommargin" placeholder="please select"
                       style="width: 100px">
              <el-option
                  v-for="item in pageMargin"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="页左边距">
            <el-select v-model="data.templateData.pagesettingLeftmargin" placeholder="please select"
                       style="width: 100px">
              <el-option
                  v-for="item in pageMargin"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="页右边距">
            <el-select v-model="data.templateData.pagesettingRightmargin" placeholder="please select"
                       style="width: 100px">
              <el-option
                  v-for="item in pageMargin"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item prop="pagesettingHeadercontent2" label="正文和正文后的页眉内容" style="width: 470px">
            <el-input v-model="data.templateData.pagesettingHeadercontent2" />
          </el-form-item>
          <el-form-item label="页眉边距">
            <el-select v-model="data.templateData.pagesettingHeadermargin" placeholder="please select"
                       style="width: 100px">
              <el-option
                  v-for="item in pageMargin"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="页脚边距">
            <el-select v-model="data.templateData.pagesettingFootermargin" placeholder="please select"
                       style="width: 100px">
              <el-option
                  v-for="item in pageMargin"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="是否区分奇偶页插入页码">
            <el-select v-model="data.templateData.pagesettingOddevenpage" placeholder="please select"
                       style="width: 100px">
              <el-option label="是" value='1'/>
              <el-option label="否" value='0'/>
            </el-select>
          </el-form-item>

          <p><span style="font-size: 15px; font-weight: bold">图注和表注 </span></p>
          <el-form-item label="图注表注中文字体" style="width: 280px">
            <el-select v-model="data.templateData.captionFontname" placeholder="please select" >
              <el-option
                  v-for="item in fontType"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="图注表注英文字体" style="width: 280px">
            <el-select v-model="data.templateData.captionFontenglishname" placeholder="please select">
              <el-option
                  v-for="item in fontEnglishType"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="图注表注字号" style="width: 280px">
            <el-select v-model="data.templateData.captionFontsize" placeholder="please select" >
              <el-option
                  v-for="item in fontSize"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item prop="captionPicszwidthmin" label="推荐图片最小宽度(单位cm)" style="width: 280px">
            <el-input v-model="data.templateData.captionPicszwidthmin" />
          </el-form-item>
          <el-form-item prop="captionPicszwidthmax" label="推荐图片最小宽度(单位cm)" style="width: 280px">
            <el-input v-model="data.templateData.captionPicszwidthmax" />
          </el-form-item>
          <el-form-item prop="captionPicszheightmin" label="推荐图片最小宽度(单位cm)" style="width: 280px">
            <el-input v-model="data.templateData.captionPicszheightmin" />
          </el-form-item>
          <el-form-item prop="captionPicszheightmax" label="推荐图片最小宽度(单位cm)" style="width: 280px">
            <el-input v-model="data.templateData.captionPicszheightmax" />
          </el-form-item>
          <el-form-item prop="captionRecommendnum" label="推荐图表数量" style="width: 280px">
            <el-input v-model="data.templateData.captionRecommendnum" />
          </el-form-item>

          <p><span style="font-size: 15px; font-weight: bold">诚信说明</span></p>
          <el-form-item label="标题中文字体" style="width: 280px">
            <el-select v-model="data.templateData.sohHFontType" placeholder="please select">
              <el-option
                  v-for="item in fontType"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="标题英文字体" style="width: 280px">
            <el-select v-model="data.templateData.sohHFontEnglishType" placeholder="please select">
              <el-option
                  v-for="item in fontEnglishType"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="标题字体大小" style="width: 280px">
            <el-select v-model="data.templateData.sohHFontSz" placeholder="please select">
              <el-option
                  v-for="item in fontSize"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="标题对齐方式" style="width: 280px">
            <el-select v-model="data.templateData.sohHJc" placeholder="please select">
              <el-option
                  v-for="item in jc"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="标题段间距" style="width: 280px">
            <el-select v-model="data.templateData.sohHLine" placeholder="please select">
              <el-option
                  v-for="item in line"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="标题段前距" style="width: 280px">
            <el-select v-model="data.templateData.sohHBeforeline" placeholder="please select" >
              <el-option
                  v-for="item in beforeAfterline"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="标题段后距" style="width: 280px">
            <el-select v-model="data.templateData.sohHAfterline" placeholder="please select" >
              <el-option
                  v-for="item in beforeAfterline"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="标题加粗" style="width: 280px">
            <el-select v-model="data.templateData.sohHBold" placeholder="please select">
              <el-option label="是" value='1'/>
              <el-option label="否" value='0'/>
            </el-select>
          </el-form-item>
          <br/>
          <el-form-item label="正文段落中文字体" style="width: 280px">
            <el-select v-model="data.templateData.sohPFontType" placeholder="please select" >
              <el-option
                  v-for="item in fontType"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="正文段落英文字体" style="width: 280px">
            <el-select v-model="data.templateData.sohPFontTypeEnglish" placeholder="please select" >
              <el-option
                  v-for="item in fontEnglishType"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="正文字体大小" style="width: 280px">
            <el-select v-model="data.templateData.sohPFontSz" placeholder="please select" >
              <el-option
                  v-for="item in fontSize"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="段落首行缩进" style="width: 280px">
            <el-select v-model="data.templateData.sohPInd" placeholder="please select" >
              <el-option
                  v-for="item in indent"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="段落段间距" style="width: 280px">
            <el-select v-model="data.templateData.sohPLine" placeholder="please select" >
              <el-option
                  v-for="item in line"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="正文加粗" style="width: 280px">
            <el-select v-model="data.templateData.sohPBold" placeholder="please select" >
              <el-option label="是" value='1'/>
              <el-option label="否" value='0'/>
            </el-select>
          </el-form-item>
          <br/>
          <el-form-item label="诚信说明正文内容" style="width: 900px;">
            <el-input v-model="data.templateData.sohContent" type="textarea"  :autosize="{ minRows: 4, maxRows: 10 }"/>
          </el-form-item>





          <p><span style="font-size: 15px; font-weight: bold">中文摘要</span></p>
          <el-form-item label="前置词（即“摘要”、“关键字”）的字体" style="width: 480px">
            <el-select v-model="data.templateData.aocPrefixfont" placeholder="please select" >
              <el-option
                  v-for="item in fontType"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="摘要内容推荐的最大字数" style="width: 380px">
            <el-input v-model="data.templateData.aocRecommendedmaxcontentlength" placeholder="please select"/>
          </el-form-item>
          <br/>
          <el-form-item label="摘要内容推荐的最小字数" style="width: 280px">
            <el-input v-model="data.templateData.aocRecommendedmincontentlength" placeholder="please select"/>
          </el-form-item>
          <el-form-item label="关键词推荐最大数量" style="width: 280px">
            <el-input v-model="data.templateData.aocRecommendedmaxkeywordscount" placeholder="please select"/>
          </el-form-item>
          <el-form-item label="关键词推荐最小数量" style="width: 280px">
            <el-input v-model="data.templateData.aocRecommendedminkeywordscount" placeholder="please select"/>
          </el-form-item>
          <el-form-item label="前置词是否加粗" style="width: 280px">
            <el-select v-model="data.templateData.aocIsprefixbold" placeholder="please select">
              <el-option label="是" value='1'/>
              <el-option label="否" value='0'/>
            </el-select>
          </el-form-item>

          <br/>
          <el-form-item label="论文标题中文字体" style="width: 280px">
            <el-select v-model="data.templateData.aocHFontType" placeholder="please select">
              <el-option
                  v-for="item in fontType"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="论文标题英文字体" style="width: 280px">
            <el-select v-model="data.templateData.aocHFontEnglishType" placeholder="please select">
              <el-option
                  v-for="item in fontEnglishType"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="标题字体大小" style="width: 280px">
            <el-select v-model="data.templateData.aocHFontSz" placeholder="please select">
              <el-option
                  v-for="item in fontSize"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="标题对齐方式" style="width: 280px">
            <el-select v-model="data.templateData.aocHJc" placeholder="please select">
              <el-option
                  v-for="item in jc"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="标题段间距" style="width: 280px">
            <el-select v-model="data.templateData.aocHLine" placeholder="please select">
              <el-option
                  v-for="item in line"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="标题段前距" style="width: 280px">
            <el-select v-model="data.templateData.aocHBeforeline" placeholder="please select" >
              <el-option
                  v-for="item in beforeAfterline"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="标题段后距" style="width: 280px">
            <el-select v-model="data.templateData.aocHAfterline" placeholder="please select" >
              <el-option
                  v-for="item in beforeAfterline"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="标题加粗" style="width: 280px">
            <el-select v-model="data.templateData.aocHBold" placeholder="please select">
              <el-option label="是" value='1'/>
              <el-option label="否" value='0'/>
            </el-select>
          </el-form-item>
          <br/>
          <el-form-item label="正文段落中文字体" style="width: 280px">
            <el-select v-model="data.templateData.aocPFontType" placeholder="please select" >
              <el-option
                  v-for="item in fontType"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="正文段落英文字体" style="width: 280px">
            <el-select v-model="data.templateData.aocPFontTypeEnglish" placeholder="please select" >
              <el-option
                  v-for="item in fontEnglishType"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="正文字体大小" style="width: 280px">
            <el-select v-model="data.templateData.aocPFontSz" placeholder="please select" >
              <el-option
                  v-for="item in fontSize"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="段落首行缩进" style="width: 280px">
            <el-select v-model="data.templateData.aocPInd" placeholder="please select" >
              <el-option
                  v-for="item in indent"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="段落段间距" style="width: 280px">
            <el-select v-model="data.templateData.aocPLine" placeholder="please select" >
              <el-option
                  v-for="item in line"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="正文加粗" style="width: 280px">
            <el-select v-model="data.templateData.aocPBold" placeholder="please select" >
              <el-option label="是" value='1'/>
              <el-option label="否" value='0'/>
            </el-select>
          </el-form-item>
          <br/>





          <p><span style="font-size: 15px; font-weight: bold">英文摘要</span></p>
          <el-form-item label="前置词（即“Abstract”、“Keywords”）的字体" style="width: 480px">
            <el-select v-model="data.templateData.aoePrefixfont" placeholder="please select" >
              <el-option
                  v-for="item in fontType"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="摘要内容推荐的最大字数" style="width: 380px">
            <el-input v-model="data.templateData.aoeRecommendedmaxcontentlength" placeholder="please select"/>
          </el-form-item>
          <br/>
          <el-form-item label="摘要内容推荐的最小字数" style="width: 280px">
            <el-input v-model="data.templateData.aoeRecommendedmincontentlength" placeholder="please select"/>
          </el-form-item>
          <el-form-item label="关键词推荐最大数量" style="width: 280px">
            <el-input v-model="data.templateData.aoeRecommendedmaxkeywordscount" placeholder="please select"/>
          </el-form-item>
          <el-form-item label="关键词推荐最小数量" style="width: 280px">
            <el-input v-model="data.templateData.aoeRecommendedminkeywordscount" placeholder="please select"/>
          </el-form-item>
          <el-form-item label="前置词是否加粗" style="width: 280px">
            <el-select v-model="data.templateData.aoeIsprefixbold" placeholder="please select">
              <el-option label="是" value='1'/>
              <el-option label="否" value='0'/>
            </el-select>
          </el-form-item>

          <br/>
          <el-form-item label="论文标题中文字体" style="width: 280px">
            <el-select v-model="data.templateData.aoeHFontType" placeholder="please select">
              <el-option
                  v-for="item in fontType"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="论文标题英文字体" style="width: 280px">
            <el-select v-model="data.templateData.aoeHFontEnglishType" placeholder="please select">
              <el-option
                  v-for="item in fontEnglishType"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="标题字体大小" style="width: 280px">
            <el-select v-model="data.templateData.aoeHFontSz" placeholder="please select">
              <el-option
                  v-for="item in fontSize"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="标题对齐方式" style="width: 280px">
            <el-select v-model="data.templateData.aoeHJc" placeholder="please select">
              <el-option
                  v-for="item in jc"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="标题段间距" style="width: 280px">
            <el-select v-model="data.templateData.aoeHLine" placeholder="please select">
              <el-option
                  v-for="item in line"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="标题段前距" style="width: 280px">
            <el-select v-model="data.templateData.aoeHBeforeline" placeholder="please select" >
              <el-option
                  v-for="item in beforeAfterline"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="标题段后距" style="width: 280px">
            <el-select v-model="data.templateData.aoeHAfterline" placeholder="please select" >
              <el-option
                  v-for="item in beforeAfterline"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="标题加粗" style="width: 280px">
            <el-select v-model="data.templateData.aoeHBold" placeholder="please select">
              <el-option label="是" value='1'/>
              <el-option label="否" value='0'/>
            </el-select>
          </el-form-item>
          <br/>
          <el-form-item label="正文段落中文字体" style="width: 280px">
            <el-select v-model="data.templateData.aoePFontType" placeholder="please select" >
              <el-option
                  v-for="item in fontType"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="正文段落英文字体" style="width: 280px">
            <el-select v-model="data.templateData.aoePFontTypeEnglish" placeholder="please select" >
              <el-option
                  v-for="item in fontEnglishType"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="正文字体大小" style="width: 280px">
            <el-select v-model="data.templateData.aoePFontSz" placeholder="please select" >
              <el-option
                  v-for="item in fontSize"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="段落首行缩进" style="width: 280px">
            <el-select v-model="data.templateData.aoePInd" placeholder="please select" >
              <el-option
                  v-for="item in indent"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="段落段间距" style="width: 280px">
            <el-select v-model="data.templateData.aoePLine" placeholder="please select" >
              <el-option
                  v-for="item in line"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="正文加粗" style="width: 280px">
            <el-select v-model="data.templateData.aoePBold" placeholder="please select" >
              <el-option label="是" value='1'/>
              <el-option label="否" value='0'/>
            </el-select>
          </el-form-item>
          <br/>





          <p><span style="font-size: 15px; font-weight: bold">目录</span></p>
          <el-form-item label="标题中文字体" style="width: 280px">
            <el-select v-model="data.templateData.catalogueHFontType" placeholder="please select">
              <el-option
                  v-for="item in fontType"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="标题英文字体" style="width: 280px">
            <el-select v-model="data.templateData.catalogueHFontEnglishType" placeholder="please select">
              <el-option
                  v-for="item in fontEnglishType"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="标题字体大小" style="width: 280px">
            <el-select v-model="data.templateData.catalogueHFontSz" placeholder="please select">
              <el-option
                  v-for="item in fontSize"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="标题对齐方式" style="width: 280px">
            <el-select v-model="data.templateData.catalogueHJc" placeholder="please select">
              <el-option
                  v-for="item in jc"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="标题段间距" style="width: 280px">
            <el-select v-model="data.templateData.catalogueHLine" placeholder="please select">
              <el-option
                  v-for="item in line"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="标题段前距" style="width: 280px">
            <el-select v-model="data.templateData.catalogueHBeforeline" placeholder="please select" >
              <el-option
                  v-for="item in beforeAfterline"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="标题段后距" style="width: 280px">
            <el-select v-model="data.templateData.catalogueHAfterline" placeholder="please select" >
              <el-option
                  v-for="item in beforeAfterline"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="标题加粗" style="width: 280px">
            <el-select v-model="data.templateData.catalogueHBold" placeholder="please select">
              <el-option label="是" value='1'/>
              <el-option label="否" value='0'/>
            </el-select>
          </el-form-item>
          <br/>







          <p><span style="font-size: 15px; font-weight: bold">绪论和正文</span></p>
          <el-form-item label="一级标题中文字体" style="width: 280px">
            <el-select v-model="data.templateData.mainbodyH1FontType" placeholder="please select">
              <el-option
                  v-for="item in fontType"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="一级标题英文字体" style="width: 280px">
            <el-select v-model="data.templateData.mainbodyH1FontEnglishType" placeholder="please select">
              <el-option
                  v-for="item in fontEnglishType"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="一级标题字体大小" style="width: 280px">
            <el-select v-model="data.templateData.mainbodyH1FontSz" placeholder="please select">
              <el-option
                  v-for="item in fontSize"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="一级标题对齐方式" style="width: 280px">
            <el-select v-model="data.templateData.mainbodyH1Jc" placeholder="please select">
              <el-option
                  v-for="item in jc"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="一级标题段间距" style="width: 280px">
            <el-select v-model="data.templateData.mainbodyH1Line" placeholder="please select">
              <el-option
                  v-for="item in line"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="一级标题段前距" style="width: 280px">
            <el-select v-model="data.templateData.mainbodyH1Beforeline" placeholder="please select" >
              <el-option
                  v-for="item in beforeAfterline"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="一级标题段后距" style="width: 280px">
            <el-select v-model="data.templateData.mainbodyH1Afterline" placeholder="please select" >
              <el-option
                  v-for="item in beforeAfterline"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="一级标题加粗" style="width: 280px">
            <el-select v-model="data.templateData.mainbodyH1Bold" placeholder="please select">
              <el-option label="是" value='1'/>
              <el-option label="否" value='0'/>
            </el-select>
          </el-form-item>
          <br/>

          <el-form-item label="二级标题中文字体" style="width: 280px">
            <el-select v-model="data.templateData.mainbodyH2FontType" placeholder="please select">
              <el-option
                  v-for="item in fontType"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="二级标题英文字体" style="width: 280px">
            <el-select v-model="data.templateData.mainbodyH2FontEnglishType" placeholder="please select">
              <el-option
                  v-for="item in fontEnglishType"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="二级标题字体大小" style="width: 280px">
            <el-select v-model="data.templateData.mainbodyH2FontSz" placeholder="please select">
              <el-option
                  v-for="item in fontSize"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="二级标题对齐方式" style="width: 280px">
            <el-select v-model="data.templateData.mainbodyH2Jc" placeholder="please select">
              <el-option
                  v-for="item in jc"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="二级标题段间距" style="width: 280px">
            <el-select v-model="data.templateData.mainbodyH2Line" placeholder="please select">
              <el-option
                  v-for="item in line"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="二级标题段前距" style="width: 280px">
            <el-select v-model="data.templateData.mainbodyH2Beforeline" placeholder="please select" >
              <el-option
                  v-for="item in beforeAfterline"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="二级标题段后距" style="width: 280px">
            <el-select v-model="data.templateData.mainbodyH2Afterline" placeholder="please select" >
              <el-option
                  v-for="item in beforeAfterline"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="二级标题加粗" style="width: 280px">
            <el-select v-model="data.templateData.mainbodyH2Bold" placeholder="please select">
              <el-option label="是" value='1'/>
              <el-option label="否" value='0'/>
            </el-select>
          </el-form-item>
          <br/>

          <el-form-item label="三级标题中文字体" style="width: 280px">
            <el-select v-model="data.templateData.mainbodyH3FontType" placeholder="please select">
              <el-option
                  v-for="item in fontType"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="三级标题英文字体" style="width: 280px">
            <el-select v-model="data.templateData.mainbodyH3FontEnglishType" placeholder="please select">
              <el-option
                  v-for="item in fontEnglishType"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="三级标题字体大小" style="width: 280px">
            <el-select v-model="data.templateData.mainbodyH3FontSz" placeholder="please select">
              <el-option
                  v-for="item in fontSize"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="三级标题对齐方式" style="width: 280px">
            <el-select v-model="data.templateData.mainbodyH3Jc" placeholder="please select">
              <el-option
                  v-for="item in jc"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="三级标题段间距" style="width: 280px">
            <el-select v-model="data.templateData.mainbodyH3Line" placeholder="please select">
              <el-option
                  v-for="item in line"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="三级标题段前距" style="width: 280px">
            <el-select v-model="data.templateData.mainbodyH3Beforeline" placeholder="please select" >
              <el-option
                  v-for="item in beforeAfterline"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="三级标题段后距" style="width: 280px">
            <el-select v-model="data.templateData.mainbodyH3Afterline" placeholder="please select" >
              <el-option
                  v-for="item in beforeAfterline"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="三级标题加粗" style="width: 280px">
            <el-select v-model="data.templateData.mainbodyH3Bold" placeholder="please select">
              <el-option label="是" value='1'/>
              <el-option label="否" value='0'/>
            </el-select>
          </el-form-item>
          <br/>

          <el-form-item label="正文段落中文字体" style="width: 280px">
            <el-select v-model="data.templateData.mainbodyPFontType" placeholder="please select" >
              <el-option
                  v-for="item in fontType"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="正文段落英文字体" style="width: 280px">
            <el-select v-model="data.templateData.mainbodyPFontTypeEnglish" placeholder="please select" >
              <el-option
                  v-for="item in fontEnglishType"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="正文字体大小" style="width: 280px">
            <el-select v-model="data.templateData.mainbodyPFontSz" placeholder="please select" >
              <el-option
                  v-for="item in fontSize"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="段落首行缩进" style="width: 280px">
            <el-select v-model="data.templateData.mainbodyPInd" placeholder="please select" >
              <el-option
                  v-for="item in indent"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="段落段间距" style="width: 280px">
            <el-select v-model="data.templateData.mainbodyPLine" placeholder="please select" >
              <el-option
                  v-for="item in line"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="正文加粗" style="width: 280px">
            <el-select v-model="data.templateData.mainbodyPBold" placeholder="please select" >
              <el-option label="是" value='1'/>
              <el-option label="否" value='0'/>
            </el-select>
          </el-form-item>
          <br/>



          <p><span style="font-size: 15px; font-weight: bold">结论</span></p>
          <el-form-item label="标题中文字体" style="width: 280px">
            <el-select v-model="data.templateData.conclusionHFontType" placeholder="please select">
              <el-option
                  v-for="item in fontType"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="标题英文字体" style="width: 280px">
            <el-select v-model="data.templateData.conclusionHFontEnglishType" placeholder="please select">
              <el-option
                  v-for="item in fontEnglishType"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="标题字体大小" style="width: 280px">
            <el-select v-model="data.templateData.conclusionHFontSz" placeholder="please select">
              <el-option
                  v-for="item in fontSize"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="标题对齐方式" style="width: 280px">
            <el-select v-model="data.templateData.conclusionHJc" placeholder="please select">
              <el-option
                  v-for="item in jc"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="标题段间距" style="width: 280px">
            <el-select v-model="data.templateData.conclusionHLine" placeholder="please select">
              <el-option
                  v-for="item in line"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="标题段前距" style="width: 280px">
            <el-select v-model="data.templateData.conclusionHBeforeline" placeholder="please select" >
              <el-option
                  v-for="item in beforeAfterline"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="标题段后距" style="width: 280px">
            <el-select v-model="data.templateData.conclusionHAfterline" placeholder="please select" >
              <el-option
                  v-for="item in beforeAfterline"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="标题加粗" style="width: 280px">
            <el-select v-model="data.templateData.conclusionHBold" placeholder="please select">
              <el-option label="是" value='1'/>
              <el-option label="否" value='0'/>
            </el-select>
          </el-form-item>
          <br/>
          <el-form-item label="正文段落中文字体" style="width: 280px">
            <el-select v-model="data.templateData.conclusionPFontType" placeholder="please select" >
              <el-option
                  v-for="item in fontType"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="正文段落英文字体" style="width: 280px">
            <el-select v-model="data.templateData.conclusionPFontTypeEnglish" placeholder="please select" >
              <el-option
                  v-for="item in fontEnglishType"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="正文字体大小" style="width: 280px">
            <el-select v-model="data.templateData.conclusionPFontSz" placeholder="please select" >
              <el-option
                  v-for="item in fontSize"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="段落首行缩进" style="width: 280px">
            <el-select v-model="data.templateData.conclusionPInd" placeholder="please select" >
              <el-option
                  v-for="item in indent"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="段落段间距" style="width: 280px">
            <el-select v-model="data.templateData.conclusionPLine" placeholder="please select" >
              <el-option
                  v-for="item in line"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="正文加粗" style="width: 280px">
            <el-select v-model="data.templateData.conclusionPBold" placeholder="please select" >
              <el-option label="是" value='1'/>
              <el-option label="否" value='0'/>
            </el-select>
          </el-form-item>
          <br/>




          <p><span style="font-size: 15px; font-weight: bold">致谢</span></p>
          <el-form-item label="标题中文字体" style="width: 280px">
            <el-select v-model="data.templateData.thanksHFontType" placeholder="please select">
              <el-option
                  v-for="item in fontType"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="标题英文字体" style="width: 280px">
            <el-select v-model="data.templateData.thanksHFontEnglishType" placeholder="please select">
              <el-option
                  v-for="item in fontEnglishType"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="标题字体大小" style="width: 280px">
            <el-select v-model="data.templateData.thanksHFontSz" placeholder="please select">
              <el-option
                  v-for="item in fontSize"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="标题对齐方式" style="width: 280px">
            <el-select v-model="data.templateData.thanksHJc" placeholder="please select">
              <el-option
                  v-for="item in jc"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="标题段间距" style="width: 280px">
            <el-select v-model="data.templateData.thanksHLine" placeholder="please select">
              <el-option
                  v-for="item in line"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="标题段前距" style="width: 280px">
            <el-select v-model="data.templateData.thanksHBeforeline" placeholder="please select" >
              <el-option
                  v-for="item in beforeAfterline"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="标题段后距" style="width: 280px">
            <el-select v-model="data.templateData.thanksHAfterline" placeholder="please select" >
              <el-option
                  v-for="item in beforeAfterline"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="标题加粗" style="width: 280px">
            <el-select v-model="data.templateData.thanksHBold" placeholder="please select">
              <el-option label="是" value='1'/>
              <el-option label="否" value='0'/>
            </el-select>
          </el-form-item>
          <br/>
          <el-form-item label="正文段落中文字体" style="width: 280px">
            <el-select v-model="data.templateData.thanksPFontType" placeholder="please select" >
              <el-option
                  v-for="item in fontType"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="正文段落英文字体" style="width: 280px">
            <el-select v-model="data.templateData.thanksPFontTypeEnglish" placeholder="please select" >
              <el-option
                  v-for="item in fontEnglishType"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="正文字体大小" style="width: 280px">
            <el-select v-model="data.templateData.thanksPFontSz" placeholder="please select" >
              <el-option
                  v-for="item in fontSize"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="段落首行缩进" style="width: 280px">
            <el-select v-model="data.templateData.thanksPInd" placeholder="please select" >
              <el-option
                  v-for="item in indent"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="段落段间距" style="width: 280px">
            <el-select v-model="data.templateData.thanksPLine" placeholder="please select" >
              <el-option
                  v-for="item in line"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="正文加粗" style="width: 280px">
            <el-select v-model="data.templateData.thanksPBold" placeholder="please select" >
              <el-option label="是" value='1'/>
              <el-option label="否" value='0'/>
            </el-select>
          </el-form-item>
          <br/>



          <p><span style="font-size: 15px; font-weight: bold">参考文献</span></p>
          <el-form-item label="标题中文字体" style="width: 280px">
            <el-select v-model="data.templateData.referencesHFontType" placeholder="please select">
              <el-option
                  v-for="item in fontType"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="标题英文字体" style="width: 280px">
            <el-select v-model="data.templateData.referencesHFontEnglishType" placeholder="please select">
              <el-option
                  v-for="item in fontEnglishType"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="标题字体大小" style="width: 280px">
            <el-select v-model="data.templateData.referencesHFontSz" placeholder="please select">
              <el-option
                  v-for="item in fontSize"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="标题对齐方式" style="width: 280px">
            <el-select v-model="data.templateData.referencesHJc" placeholder="please select">
              <el-option
                  v-for="item in jc"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="标题段间距" style="width: 280px">
            <el-select v-model="data.templateData.referencesHLine" placeholder="please select">
              <el-option
                  v-for="item in line"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="标题段前距" style="width: 280px">
            <el-select v-model="data.templateData.referencesHBeforeline" placeholder="please select" >
              <el-option
                  v-for="item in beforeAfterline"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="标题段后距" style="width: 280px">
            <el-select v-model="data.templateData.referencesHAfterline" placeholder="please select" >
              <el-option
                  v-for="item in beforeAfterline"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="标题加粗" style="width: 280px">
            <el-select v-model="data.templateData.referencesHBold" placeholder="please select">
              <el-option label="是" value='1'/>
              <el-option label="否" value='0'/>
            </el-select>
          </el-form-item>
          <br/>
          <el-form-item label="正文段落中文字体" style="width: 280px">
            <el-select v-model="data.templateData.referencesPFontType" placeholder="please select" >
              <el-option
                  v-for="item in fontType"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="正文段落英文字体" style="width: 280px">
            <el-select v-model="data.templateData.referencesPFontTypeEnglish" placeholder="please select" >
              <el-option
                  v-for="item in fontEnglishType"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="正文字体大小" style="width: 280px">
            <el-select v-model="data.templateData.referencesPFontSz" placeholder="please select" >
              <el-option
                  v-for="item in fontSize"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="段落首行缩进" style="width: 280px">
            <el-select v-model="data.templateData.referencesPInd" placeholder="please select" >
              <el-option
                  v-for="item in indent"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="段落段间距" style="width: 280px">
            <el-select v-model="data.templateData.referencesPLine" placeholder="please select" >
              <el-option
                  v-for="item in line"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="正文加粗" style="width: 280px">
            <el-select v-model="data.templateData.referencesPBold" placeholder="please select" >
              <el-option label="是" value='1'/>
              <el-option label="否" value='0'/>
            </el-select>
          </el-form-item>

          <el-form-item label="参考文献引用格式" >
            <el-select v-model="defaultRefReq" placeholder="please select" style="width: 470px">
              <el-option label="国标GB/T 7714-2015" value='国标GB/T 7714-2015'/>

            </el-select>
          </el-form-item>
          <br/>

        </el-form>
      </el-scrollbar>
    </div>


  </div>
</template>

<script setup>
import {reactive} from "vue";
import request from "@/utils/request";
import {ElMessage} from "element-plus";

const defaultRefReq = '国标GB/T 7714-2015'

const user = JSON.parse(localStorage.getItem('account-user') || '{}')
const data = reactive({
  baseData: {
    templateName: '',
    description: '',
    status: false,
    creator: user.username
  },
  teacherData: {
    username: '',
    name: '',
    teacherId: '',
    department: '',
    description: ''
  },
  templateData: {
    templateId: '',
    sohFlag: '1',
    sohSeq: '1',
    sohContent: '我声明，所呈交的毕业论文是本人在老师指导下进行的研究工作及取得的研究成果。据我查证，除了文中特别加以标注和致谢的地方外，论文中不包含其他人已经发表或撰写过的研究成果，也不包含为获得其他教育机构的学位或证书而使用过的材料。我承诺，论文中的所有内容均真实、可信。',
    sohHFontType: '楷体_GB2312',
    sohHFontEnglishType: 'Times New Roman',
    sohHFontSz: '44',
    sohHJc: 'center',
    sohHBold: '1',
    sohHAfterline: '200',
    sohHBeforeline: '100',
    sohHLine: '240',
    sohPFontType: '楷体_GB2312',
    sohPFontTypeEnglish: 'Times New Roman',
    sohPFontSz: '30',
    sohPInd: '200',
    sohPLine: '360',
    sohPBold: '0',
    aocFlag: '1',
    aocSeq: '2',
    aocPrefixfont: '宋体',
    aocIsprefixbold: '1',
    aocRecommendedmaxcontentlength: '300',
    aocRecommendedmincontentlength: '1',
    aocRecommendedmaxkeywordscount: '5',
    aocRecommendedminkeywordscount: '1',
    aocHFontType: '宋体',
    aocHFontEnglishType: 'Times New Roman',
    aocHFontSz: '36',
    aocHJc: 'center',
    aocHBold: '240',
    aocHAfterline: '200',
    aocHBeforeline: '100',
    aocHLine: '240',
    aocPFontType: '宋体',
    aocPFontTypeEnglish: 'Times New Roman',
    aocPFontSz: '28',
    aocPInd: '200',
    aocPLine: '240',
    aocPBold: '0',
    aoeFlag: '1',
    aoeSeq: '3',
    aoePrefixfont: '宋体',
    aoeIsprefixbold: '1',
    aoeRecommendedmaxcontentlength: '300',
    aoeRecommendedmincontentlength: '1',
    aoeRecommendedmaxkeywordscount: '5',
    aoeRecommendedminkeywordscount: '1',
    aoeHFontType: '宋体',
    aoeHFontEnglishType: 'Times New Roman',
    aoeHFontSz: '36',
    aoeHJc: 'center',
    aoeHBold: '1',
    aoeHAfterline: '200',
    aoeHBeforeline: '100',
    aoeHLine: '240',
    aoePFontType: '宋体',
    aoePFontTypeEnglish: 'Times New Roman',
    aoePFontSz: '28',
    aoePInd: '200',
    aoePLine: '360',
    aoePBold: '0',
    catalogueFlag: '1',
    catalogueSeq: '4',
    catalogueHFontType: '宋体',
    catalogueHFontEnglishType: 'Times New Roman',
    catalogueHFontSz: '36',
    catalogueHJc: 'center',
    catalogueHBold: '1',
    catalogueHAfterline: '50',
    catalogueHBeforeline: '50',
    catalogueHLine: '480',
    mainbodyFlag: '1',
    mainbodySeq: '5',
    mainbodyH1FontType: '宋体',
    mainbodyH1FontEnglishType: 'Times New Roman',
    mainbodyH1FontSz: '36',
    mainbodyH1Jc: 'left',
    mainbodyH1Bold: '1',
    mainbodyH1Afterline: '50',
    mainbodyH1Beforeline: '50',
    mainbodyH1Line: '480',
    mainbodyH2FontType: '宋体',
    mainbodyH2FontEnglishType: 'Times New Roman',
    mainbodyH2FontSz: '28',
    mainbodyH2Jc: 'left',
    mainbodyH2Bold: '1',
    mainbodyH2Afterline: '30',
    mainbodyH2Beforeline: '30',
    mainbodyH2Line: '360',
    mainbodyH3FontType: '宋体',
    mainbodyH3FontEnglishType: 'Times New Roman',
    mainbodyH3FontSz: '28',
    mainbodyH3Jc: 'left',
    mainbodyH3Bold: '1',
    mainbodyH3Afterline: '0',
    mainbodyH3Beforeline: '0',
    mainbodyH3Line: '240',
    mainbodyPFontType: '宋体',
    mainbodyPFontTypeEnglish: 'Times New Roman',
    mainbodyPFontSz: '28',
    mainbodyPInd: '200',
    mainbodyPLine: '240',
    mainbodyPBold: '0',
    conclusionFlag: '1',
    conclusionSeq: '6',
    conclusionRecommendedmaxcontentlength: '630',
    conclusionHFontType: '宋体',
    conclusionHFontEnglishType: 'Times New Roman',
    conclusionHFontSz: '36',
    conclusionHJc: 'left',
    conclusionHBold: '1',
    conclusionHAfterline: '50',
    conclusionHBeforeline: '50',
    conclusionHLine: '480',
    conclusionPFontType: '宋体',
    conclusionPFontTypeEnglish: 'Times New Roman',
    conclusionPFontSz: '28',
    conclusionPInd: '200',
    conclusionPLine: '240',
    conclusionPBold: '0',
    thanksFlag: '1',
    thanksSeq: '7',
    thanksRecommendedmaxcontentlength: '630',
    thanksHFontType: '宋体',
    thanksHFontEnglishType: 'Times New Roman',
    thanksHFontSz: '36',
    thanksHJc: 'center',
    thanksHBold: '1',
    thanksHAfterline: '50',
    thanksHBeforeline: '50',
    thanksHLine: '480',
    thanksPFontType: '宋体',
    thanksPFontTypeEnglish: 'Times New Roman',
    thanksPFontSz: '28',
    thanksPInd: '200',
    thanksPLine: '240',
    thanksPBold: '0',
    referencesFlag: '1',
    referencesSeq: '8',
    referencesRecommendedmincount: '8',
    referencesHFontType: '宋体',
    referencesHFontEnglishType: 'Times New Roman',
    referencesHFontSz: '36',
    referencesHJc: 'center',
    referencesHBold: '1',
    referencesHAfterline: '50',
    referencesHBeforeline: '50',
    referencesHLine: '480',
    referencesPFontType: '宋体',
    referencesPFontTypeEnglish: 'Times New Roman',
    referencesPFontSz: '28',
    referencesPInd: '0',
    referencesPLine: '360',
    referencesPBold: '0',
    captionFontname: '宋体',
    captionFontenglishname: 'Times New Roman',
    captionFontsize: '21',
    captionPicszwidthmin: '1.0',
    captionPicszwidthmax: '17.0',
    captionPicszheightmin: '1.0',
    captionPicszheightmax: '26',
    captionRecommendnum: '2',
    pagesettingHeadercontent1: '暨南大学本科毕业设计（论文）',
    pagesettingHeadercontent2: 'defaultPaperName',
    pagesettingTopmargin: '1418',
    pagesettingBottommargin: '1418',
    pagesettingLeftmargin: '1701',
    pagesettingRightmargin: '1134',
    pagesettingHeadermargin: '851',
    pagesettingFootermargin: '1418',
    pagesettingOddevenpage: '1',

  }
});
const rules = reactive({
  templateName: [
    {required: true, message: '请输入模板名称', trigger: 'blur'},
  ],
  pagesettingHeadercontent1: [
    {required: true, message: '请输入正文前页眉内容', trigger: 'blur'},
  ],
  pagesettingHeadercontent2: [
    {required: true, message: '请输入正文和正文后的页眉内容', trigger: 'blur'},
  ],
  captionRecommendnum: [
    {required: true, message: '请输入推荐图表数量', trigger: 'blur'},
  ],
});
const fontType = [
  {label: '楷体_GB2312', value: '楷体_GB2312'},
  {label: '宋体', value: '宋体'},
  {label: '黑体', value: '黑体'},
  {label: '仿宋', value: '仿宋'},
  {label: '楷体', value: '楷体'},
  {label: '微软雅黑', value: '微软雅黑'},
];

const fontEnglishType = [
  {label: 'Times New Roman', value: 'Times New Roman'},
  {label: 'Arial', value: 'Arial'},
  {label: 'Calibri', value: 'Calibri'},
  {label: 'Cambria', value: 'Cambria'},
  {label: 'Georgia', value: 'Georgia'},
  {label: 'Verdana', value: 'Verdana'},
];

const fontSize = [
  {label: '初号', value: '84'},
  {label: '小初', value: '72'},
  {label: '一号', value: '52'},
  {label: '小一', value: '48'},
  {label: '二号', value: '44'},
  {label: '小二', value: '36'},
  {label: '三号', value: '32'},
  {label: '小三', value: '30'},
  {label: '四号', value: '28'},
  {label: '小四', value: '24'},
  {label: '五号', value: '21'},
  {label: '小五', value: '18'},
  {label: '六号', value: '15'},
  {label: '小六', value: '13'},
  {label: '七号', value: '11'},
  {label: '八号', value: '10'},
]

const jc = [
  {label: '居中', value: 'center'},
  {label: '居左', value: 'left'},
  {label: '居右', value: 'right'},
]

const line = [
  {label: '1.0倍行距', value: '240'},
  {label: '1.5倍行距', value: '360'},
  {label: '2.0倍行距', value: '480'},
  {label: '2.5倍行距', value: '600'},
  {label: '3.0倍行距', value: '720'},
]

const beforeAfterline = [
  {label: '0.0行', value: '0'},
  {label:'0.5行', value: '50'},
  {label:'1.0行', value: '100'},
  {label:'1.5行', value: '150'},
  {label:'2.0行', value: '200'},
  {label:'2.5行', value: '250'},
  {label:'3.0行', value: '300'},
]

const indent = [
  {label: '首行缩进0字符', value: '0'},
  {label: '首行缩进1字符', value: '100'},
  {label: '首行缩进2字符', value: '200'},
  {label: '首行缩进3字符', value: '300'},
  {label: '首行缩进4字符', value: '400'},
]

const pageMargin = [
  {label: '1.0厘米', value: '567'},
  {label: '1.25厘米', value: '709'},
  {label: '1.5厘米', value: '851'},
  {label: '1.75厘米', value: '992'},
  {label: '2.0厘米', value: '1134'},
  {label: '2.25厘米', value: '1276'},
  {label: '2.5厘米', value: '1418'},
  {label: '2.75厘米', value: '1560'},
  {label: '3.0厘米', value: '1701'},
  {label: '3.25厘米', value: '1843'},
  {label: '3.5厘米', value: '1985'},
  {label: '3.75厘米', value: '2127'},
  {label: '4.0厘米', value: '2268'},
]
const load = () => {
  request.post('/getTeacherInfoByUserName', {
        username: user.username
      }
  ).then(res => {
    if (res.code === '200') {
      data.teacherData.name = res.data.name
      data.teacherData.teacherId = res.data.teacherId
      data.teacherData.department = res.data.department
      data.teacherData.description = res.data.description
    } else {
      ElMessage.error(res.msg)
    }
  })


};
load();  // 页面加载时加载数据

const createTemplate = () => {
  request.post('/addTemplate', {
    templateName: data.baseData.templateName,
    description: data.baseData.description,
    creator: data.baseData.creator,
    status: data.baseData.status ? '1' : '0'
  }).then(res => {
    if (res.code === '200') {

      data.templateData.templateId = res.data.templateId
      request.post('/addTemplateInfo', data.templateData).then(res => {
        if (res.code === '200') {
          ElMessage.success('创建成功!新模板id为' + data.templateData.templateId)
        } else {
          ElMessage.error(res.msg)
        }
      })
    } else {
      ElMessage.error(res.msg)
    }
  })

}


</script>