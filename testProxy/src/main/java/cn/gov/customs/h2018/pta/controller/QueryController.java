package cn.gov.customs.h2018.pta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import cn.gov.customs.h2018.pta.pojo.QueryParam;
import cn.gov.customs.h2018.pta.pojo.QueryResult;
import cn.gov.customs.h2018.pta.service.PtaService;

@RestController
public class QueryController {

  @Autowired PtaService ptaService;

  @GetMapping("/track/getUserTrack-get")
  public QueryResult trackGet(
      @RequestParam(required = false) String userName,
      @RequestParam String userPhoneCode,
      @RequestParam String queryId) {
    return ptaService.queryCaict(queryId, userName, userPhoneCode);
  }

  @PostMapping("/track/getUserTrack")
  public QueryResult trackPost(@RequestBody QueryParam params) {
    return ptaService.queryCaict(
        params.getQueryId(), params.getUserName(), params.getUserPhoneCode());
  }
}
