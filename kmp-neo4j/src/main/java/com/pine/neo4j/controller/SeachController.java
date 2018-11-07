package com.pine.neo4j.controller;

import java.util.ArrayList;
import java.util.Arrays;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import sun.util.logging.resources.logging;

import com.pine.neo4j.service.SearchService;
import com.pine.neo4j.util.ResponseUtil;

/** 图谱查询 */
@Controller
@RequestMapping("/SearchController")
public class SeachController {
	@Resource
	private SearchService service;

	/** 查询 */
	@RequestMapping("/PubQuery")
	public String listAll(@RequestParam(value = "university", required = false) String university,
			 HttpServletResponse response) throws Exception {
		// 封装查询条件
		ArrayList<String> list = new ArrayList<String>();
		//此集合顺序决定查询顺序
		if (university != null && university != "" ) {
			list.add("Univ~~~" + university);
		}
		JSONObject result = service.find(list, null);
		ResponseUtil.write(response, result);
		return null;
	}

	/** 初始化下拉框 */
	@RequestMapping("/select")
	public String selectAll(HttpServletResponse response) throws Exception {
		JSONObject result = service.findLables();
		ResponseUtil.write(response, result);
		return null;
	}
}
