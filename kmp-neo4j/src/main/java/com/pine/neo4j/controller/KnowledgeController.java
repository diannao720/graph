package com.pine.neo4j.controller;


import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pine.neo4j.service.KnowledgeInter;


@Controller
@RequestMapping("/knowledge")
public class KnowledgeController{
	@Autowired
	private KnowledgeInter knowledgeService;
	
	@RequestMapping("selectNode")
    public void selectNode(String name) throws IOException {
		
        Map params = new HashMap();
        params.put("name", name);
        
        List<Map<String, Object>>  result = knowledgeService.selectNode(params);

    }
	
	@RequestMapping("selectTable")
    public void selectTable(int pagenum) throws IOException {
		
        Map params = new HashMap();
        params.put("pagenum", pagenum);
        
        List<Map<String, Object>>  result = knowledgeService.selectTable(params);

    }
	
	@RequestMapping("selectTableByNode")
    public void selectTableByNode(String name) throws IOException {
        
        Map params = new HashMap();
        params.put("name", name);
        List<Map<String, Object>>  result = knowledgeService.selectTableByNode(params);
    }
	
	@RequestMapping("selectTableByCount")
    public void selectTableByCount() throws IOException {
		
        List<Map<String, Object>>  result = knowledgeService.selectTableByCount();
        
    }
	
}
