package org.example.utils.httpClient;

import org.apache.log4j.Logger;
import org.example.keyword.commonTool.BaseRequest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

//基于页面返回的变量
public class HtmlBaseVariable extends HttpClientVariable{
    protected static final Logger logger = Logger.getLogger(BaseRequest.class);

    public HtmlBaseVariable(String name,String left,String right) {
        this.name = name;
        this.startStr = left;
        this.endStr = right;
    }

    String startStr;
    String endStr;

    public String getStartStr() {
        return startStr;
    }

    public void setStartStr(String startStr) {
        this.startStr = startStr;
    }

    public String getEndStr() {
        return endStr;
    }

    public void setEndStr(String endStr) {
        this.endStr = endStr;
    }

    //从response的页面内容中获取目标参数
    public boolean parse(Response response){
        String text =response.getLast().getResultHtml();
        String result = parseByFix(startStr,endStr,text);
        super.setValue(result);
        return Boolean.TRUE;
    }

    private String parseByFix(String left, String right,String text){
        String result = "";
        String holderString = "(.*?)";
        String regExp = Pattern.quote(left) + holderString + Pattern.quote(right);
        Pattern pattern = Pattern.compile(regExp,Pattern.DOTALL);
        Matcher matcher = pattern.matcher(text);
        while(matcher.find()){
            if(matcher.groupCount() > 1){
                logger.info("StingProvider.getResultList error.the groupCount>1.please");
            }
            result = matcher.group(1);
            break;
        }
        return result;
    }


}
