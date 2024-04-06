package jnu.service.xmlprocessor;

import cn.hutool.extra.spring.SpringUtil;
import com.zilong.fdbackend.mapper.TemplateInfoMapper;
import com.zilong.fdbackend.pojo.TemplateInfoPojo;
import jnu.template.*;
import lombok.Getter;
import lombok.Setter;

/**
 * 从数据库中获取模板信息。
 * */
@Getter
@Setter
public class TemplateInfo {

    private PageSetting pageSetting;
    private Caption caption;
    private StatementOfHonesty statementOfHonesty;
    private AbstractOfChinese abstractOfChinese;
    private AbstractOfEnglish abstractOfEnglish;
    private Catalogue catalogue;
    private MainBody mainBody;
    private Conclusion conclusion;
    private References references;
    private Thanks thanks;

    private TemplateInfoPojo templateInfoPojo;
    private boolean isTemplateValid = true;

    public TemplateInfo(String templateId, String paperName) {
        TemplateInfoMapper templateInfoMapper = SpringUtil.getBean(TemplateInfoMapper.class);
        TemplateInfoPojo infoPojo = templateInfoMapper.selectById(templateId);
        this.templateInfoPojo = infoPojo;
        if (infoPojo == null) {
            System.out.println("模板不存在！自动使用默认模板。");
            isTemplateValid = false;
            pageSetting = PageSetting.getDefaultInstance();
            pageSetting.setHeaderContent2(paperName);
            caption = Caption.getDefaultInstance();
            statementOfHonesty = StatementOfHonesty.getDefaultInstance();
            abstractOfChinese = AbstractOfChinese.getDefaultInstance();
            abstractOfEnglish = AbstractOfEnglish.getDefaultInstance();
            catalogue = Catalogue.getDefaultInstance();
            mainBody = MainBody.getDefaultInstance();
            conclusion = Conclusion.getDefaultInstance();
            references = References.getDefaultInstance();
            thanks = Thanks.getDefaultInstance();
            return;
        }

        pageSetting = new PageSetting(
                infoPojo.getPagesettingHeadercontent1(),
                infoPojo.getPagesettingHeadercontent2(),
                infoPojo.getPagesettingTopmargin(),
                infoPojo.getPagesettingBottommargin(),
                infoPojo.getPagesettingLeftmargin(),
                infoPojo.getPagesettingRightmargin(),
                infoPojo.getPagesettingOddevenpage(),
                infoPojo.getPagesettingHeadermargin(),
                infoPojo.getPagesettingFootermargin()
        );
        if(pageSetting.getHeaderContent2().equals("defaultPaperName")) {
            pageSetting.setHeaderContent2(paperName);
        }

        caption = new Caption(
                infoPojo.getCaptionFontname(),
                infoPojo.getCaptionFontenglishname(),
                infoPojo.getCaptionFontsize(),
                infoPojo.getCaptionPicszwidthmin(),
                infoPojo.getCaptionPicszwidthmax(),
                infoPojo.getCaptionPicszheightmin(),
                infoPojo.getCaptionPicszheightmax(),
                infoPojo.getCaptionRecommendnum()
        );


        statementOfHonesty = new StatementOfHonesty(
                infoPojo.getSohSeq(),
                infoPojo.getSohContent(),
                new HeadingRep(
                        infoPojo.getSohHFontType(),
                        infoPojo.getSohHFontEnglishType(),
                        infoPojo.getSohHFontSz(),
                        infoPojo.getSohHJc(),
                        infoPojo.getSohHLine(),
                        infoPojo.getSohHBeforeline(),
                        infoPojo.getSohHAfterline(),
                        infoPojo.getSohHBold()
                ),
                new ParagraphRep(
                        infoPojo.getSohPFontType(),
                        infoPojo.getSohPFontTypeEnglish(),
                        infoPojo.getSohPFontSz(),
                        infoPojo.getSohPInd(),
                        infoPojo.getSohPLine(),
                        infoPojo.getSohPBold()
                )
        );

        abstractOfChinese = new AbstractOfChinese(
                infoPojo.getAocPrefixfont(),
                infoPojo.getAocHBold(),
                infoPojo.getAocRecommendedmaxcontentlength(),
                infoPojo.getAocRecommendedmaxkeywordscount(),
                infoPojo.getAocRecommendedmincontentlength(),
                infoPojo.getAocRecommendedminkeywordscount(),
                new HeadingRep(
                        infoPojo.getAocHFontType(),
                        infoPojo.getAocHFontEnglishType(),
                        infoPojo.getAocHFontSz(),
                        infoPojo.getAocHJc(),
                        infoPojo.getAocHLine(),
                        infoPojo.getAocHBeforeline(),
                        infoPojo.getAocHAfterline(),
                        infoPojo.getAocHBold()
                ),
                new ParagraphRep(
                        infoPojo.getAocPFontType(),
                        infoPojo.getAocPFontTypeEnglish(),
                        infoPojo.getAocPFontSz(),
                        infoPojo.getAocPInd(),
                        infoPojo.getAocPLine(),
                        infoPojo.getAocPBold()
                )
        );

        abstractOfEnglish = new AbstractOfEnglish(
                infoPojo.getAoePrefixfont(),
                infoPojo.getAoeHBold(),
                infoPojo.getAoeRecommendedmaxcontentlength(),
                infoPojo.getAoeRecommendedmaxkeywordscount(),
                infoPojo.getAoeRecommendedmincontentlength(),
                infoPojo.getAoeRecommendedminkeywordscount(),
                new HeadingRep(
                        infoPojo.getAoeHFontType(),
                        infoPojo.getAoeHFontEnglishType(),
                        infoPojo.getAoeHFontSz(),
                        infoPojo.getAoeHJc(),
                        infoPojo.getAoeHLine(),
                        infoPojo.getAoeHBeforeline(),
                        infoPojo.getAoeHAfterline(),
                        infoPojo.getAoeHBold()
                ),
                new ParagraphRep(
                        infoPojo.getAoePFontType(),
                        infoPojo.getAoePFontTypeEnglish(),
                        infoPojo.getAoePFontSz(),
                        infoPojo.getAoePInd(),
                        infoPojo.getAoePLine(),
                        infoPojo.getAoePBold()
                )
        );

        catalogue = new Catalogue(
                new HeadingRep(
                        infoPojo.getCatalogueHFontType(),
                        infoPojo.getCatalogueHFontEnglishType(),
                        infoPojo.getCatalogueHFontSz(),
                        infoPojo.getCatalogueHJc(),
                        infoPojo.getCatalogueHLine(),
                        infoPojo.getCatalogueHBeforeline(),
                        infoPojo.getCatalogueHAfterline(),
                        infoPojo.getCatalogueHBold()
                )
        );

        mainBody = new MainBody(
                new HeadingRep(
                        infoPojo.getMainbodyH1FontType(),
                        infoPojo.getMainbodyH1FontEnglishType(),
                        infoPojo.getMainbodyH1FontSz(),
                        infoPojo.getMainbodyH1Jc(),
                        infoPojo.getMainbodyH1Line(),
                        infoPojo.getMainbodyH1Beforeline(),
                        infoPojo.getMainbodyH1Afterline(),
                        infoPojo.getMainbodyH1Bold()
                ),
                new HeadingRep(
                        infoPojo.getMainbodyH2FontType(),
                        infoPojo.getMainbodyH2FontEnglishType(),
                        infoPojo.getMainbodyH2FontSz(),
                        infoPojo.getMainbodyH2Jc(),
                        infoPojo.getMainbodyH2Line(),
                        infoPojo.getMainbodyH2Beforeline(),
                        infoPojo.getMainbodyH2Afterline(),
                        infoPojo.getMainbodyH2Bold()
                ),
                new HeadingRep(
                        infoPojo.getMainbodyH3FontType(),
                        infoPojo.getMainbodyH3FontEnglishType(),
                        infoPojo.getMainbodyH3FontSz(),
                        infoPojo.getMainbodyH3Jc(),
                        infoPojo.getMainbodyH3Line(),
                        infoPojo.getMainbodyH3Beforeline(),
                        infoPojo.getMainbodyH3Afterline(),
                        infoPojo.getMainbodyH3Bold()
                ),
                new ParagraphRep(
                        infoPojo.getMainbodyPFontType(),
                        infoPojo.getMainbodyPFontTypeEnglish(),
                        infoPojo.getMainbodyPFontSz(),
                        infoPojo.getMainbodyPInd(),
                        infoPojo.getMainbodyPLine(),
                        infoPojo.getMainbodyPBold()
                )
        );

        conclusion = new Conclusion(
                infoPojo.getConclusionRecommendedmaxcontentlength(),
                new HeadingRep(
                        infoPojo.getConclusionHFontType(),
                        infoPojo.getConclusionHFontEnglishType(),
                        infoPojo.getConclusionHFontSz(),
                        infoPojo.getConclusionHJc(),
                        infoPojo.getConclusionHLine(),
                        infoPojo.getConclusionHBeforeline(),
                        infoPojo.getConclusionHAfterline(),
                        infoPojo.getConclusionHBold()
                ),
                new ParagraphRep(
                        infoPojo.getConclusionPFontType(),
                        infoPojo.getConclusionPFontTypeEnglish(),
                        infoPojo.getConclusionPFontSz(),
                        infoPojo.getConclusionPInd(),
                        infoPojo.getConclusionPLine(),
                        infoPojo.getConclusionPBold()
                )
        );

        references = new References(
                infoPojo.getReferencesRecommendedmincount(),
                new HeadingRep(
                        infoPojo.getReferencesHFontType(),
                        infoPojo.getReferencesHFontEnglishType(),
                        infoPojo.getReferencesHFontSz(),
                        infoPojo.getReferencesHJc(),
                        infoPojo.getReferencesHLine(),
                        infoPojo.getReferencesHBeforeline(),
                        infoPojo.getReferencesHAfterline(),
                        infoPojo.getReferencesHBold()
                ),
                new ParagraphRep(
                        infoPojo.getReferencesPFontType(),
                        infoPojo.getReferencesPFontTypeEnglish(),
                        infoPojo.getReferencesPFontSz(),
                        infoPojo.getReferencesPInd(),
                        infoPojo.getReferencesPLine(),
                        infoPojo.getReferencesPBold()
                )
        );

        thanks = new Thanks(
                infoPojo.getThanksRecommendedmaxcontentlength(),
                new HeadingRep(
                        infoPojo.getThanksHFontType(),
                        infoPojo.getThanksHFontEnglishType(),
                        infoPojo.getThanksHFontSz(),
                        infoPojo.getThanksHJc(),
                        infoPojo.getThanksHLine(),
                        infoPojo.getThanksHBeforeline(),
                        infoPojo.getThanksHAfterline(),
                        infoPojo.getThanksHBold()
                ),
                new ParagraphRep(
                        infoPojo.getThanksPFontType(),
                        infoPojo.getThanksPFontTypeEnglish(),
                        infoPojo.getThanksPFontSz(),
                        infoPojo.getThanksPInd(),
                        infoPojo.getThanksPLine(),
                        infoPojo.getThanksPBold()
                )
        );
    }


}
