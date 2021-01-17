package org.example.keyword.commonTool;

import org.apache.commons.lang3.StringUtils;
import org.example.keyword.requestEntity.HttpPostRequestEntity;
import org.example.keyword.resultEntity.HttpPostResultEntity;
import org.example.utils.httpClient.BaseHttpResult;
import org.example.utils.httpClient.HttpClientVariable;
import org.w3c.dom.NodeList;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPMessage;
import java.io.ByteArrayInputStream;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

public class HttpPostRequest extends BaseRequest<HttpPostRequestEntity, HttpPostResultEntity> {

  public HttpPostResultEntity execute(HttpPostRequestEntity request){
      HttpPostResultEntity result = new HttpPostResultEntity();
      try{
          Map<String, String> headerMap = convert2Map(request.getHeaders());
          Map<String, String> paramMap = convert2Map(request.getParams());
          List<HttpClientVariable> variables = convert2ListOfHttpClientVariable(request.getExpectedVariable());
          if(StringUtils.isNotBlank(request.getPayLoadBody())){
              paramMap.put("##Payload##",request.getPayLoadBody());
          }

          BaseHttpResult baseHttpResult = postRequest(request.getTargetHost(),request.getRelativeUrl(),variables,paramMap,headerMap,result);
          result.setResponseContent(baseHttpResult.getResponse().getResultHtml());
          for(int i = 0; i < variables.size(); i++){
              String name = variables.get(i).getName();
              String value = baseHttpResult.getVariables().get(i);
              if(StringUtils.isNotBlank(value)){
                  result.getVariabels().put(name,value);
              }
          }


          String text = baseHttpResult.getResponse().getResultHtml();
          if(StringUtils.isNotBlank(text) && StringUtils.contains(text.toLowerCase(),"<soap:envelope xmlns:soap")){
              Map<String, String> soapResultMap = result.getSoapResultMap();
              MessageFactory factory = MessageFactory.newInstance();
              SOAPMessage message = factory.createMessage(new MimeHeaders(),new ByteArrayInputStream(text.getBytes(Charset.forName("UTF-8"))));

              SOAPBody body = message.getSOAPBody();
              NodeList nodeList1 = body.getElementsByTagName("ns1:out");
              NodeList nodeList2 = nodeList1.item(0).getChildNodes();

              for(int i = 0; i < nodeList2.getLength(); i++) {
                  soapResultMap.put(nodeList2.item(i).getNodeName(),nodeList2.item(i).getTextContent());
              }
          }
      } catch (Exception e){
          logger.info(e.getMessage(),e);
          result.setSuccess(false);
          result.setDetailMessage(e.toString());

      }
      return result;
  }



}
