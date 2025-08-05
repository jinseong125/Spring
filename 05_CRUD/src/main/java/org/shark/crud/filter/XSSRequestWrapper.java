package org.shark.crud.filter;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class XSSRequestWrapper extends HttpServletRequestWrapper {

  public XSSRequestWrapper(HttpServletRequest request) {
    super(request);
  }

  //----- 요청 파라미터를 cleanXSS() 메소드 결과로 바꿔서 반환합니다.
  @Override
  public String getParameter(String name) {
    String value = super.getParameter(name);
    if (value == null) {
      return null;
    }
    return cleanXSS(value);
  }
  @Override
  public String[] getParameterValues(String name) {
    String[] values = super.getParameterValues(name);
    if (values == null)
      return null;
    for (int i = 0; i < values.length; i++) {
      values[i] = cleanXSS(values[i]);
    }
    return values;
  }
  @Override
  public Map<String, String[]> getParameterMap() {
    Map<String, String[]> paramMap = super.getParameterMap();
    Map<String, String[]> result = new HashMap<>();
    for (String key : paramMap.keySet()) {
      String[] rawValues = paramMap.get(key);
      String[] filteredValues = new String[rawValues.length];
      for (int i = 0; i < rawValues.length; i++) {
        filteredValues[i] = cleanXSS(rawValues[i]);
      }
      result.put(key, filteredValues);
    }
    return result;
  }
  
  //----- 스크립트 코드가 입력되면 이를 엔티티 코드로 변환하여 스크립트 코드의 동작을 무력화 합니다.
  private String cleanXSS(String param) {
    param = param.replaceAll("<", "&lt;")
                 .replaceAll(">", "&gt;")
                 .replaceAll("'", "&#39;")
                 .replaceAll("\\(", "&#40;")
                 .replaceAll("\\)", "&#41;")
                 .replaceAll("eval\\((.*)\\)", "");
    return param;
  }
  
}
