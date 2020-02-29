package cn.gov.customs.h2018.pta.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import cn.gov.customs.h2018.pta.pojo.QueryResult;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PtaService {

  @Value("${remote.server.url}")
  private String url;

  @Autowired RestTemplate restTemplate;

  /**
   * 查询信通院接口，返回旅客信息 （caict--中国信息通讯研究院）
   *
   * @param queryId guid,唯一标识一次查询
   * @param userName 用户姓名
   * @param userPhoneCode 用户手机号
   * @return
   */
  public QueryResult queryCaict(String queryId, String userName, String userPhoneCode) {

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    Map<String, Object> map = new HashMap<>();
    map.put("queryId", queryId);
    map.put("userName", userName);
    map.put("userPhoneCode", userPhoneCode);

    HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map, headers);

    LocalDateTime begin = LocalDateTime.now();
    ResponseEntity<QueryResult> response =
        restTemplate.postForEntity(url, entity, QueryResult.class);

    LocalDateTime end = LocalDateTime.now();

    log.debug(
        "query begin-end:{}-{}={}ms, queryId:{}, userName:{}, phone:{}, ",
        begin,
        end,
        Duration.between(begin, end).toMillis(),
        queryId,
        userName,
        userPhoneCode);

    return response.getBody();
  }
}
