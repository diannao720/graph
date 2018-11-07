package com.pine.neo4j.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.pine.neo4j.service.SearchService;
import com.pine.neo4j.util.JdbcUtil;


@Service("tcgnicalSupervisionService")
public class SearchServiceImpl implements SearchService {

	private static final long serialVersionUID = -7377695482669420844L;

	/** 封装线 */
	private static JSONObject getPointLineJson(List<Map<String, Object>> result) {
		Map<Integer, Map<String, Object>> pointMap = new HashMap<Integer, Map<String, Object>>();
		Map<String, String> lineMap = new HashMap<String, String>();

		for (Map<String, Object> resultMap : result) {
			Set<String> keySet = resultMap.keySet();
			for (String key : keySet) {
				Map<String, Object> resulValueMap = (Map<String, Object>) resultMap.get(key);
				// 封装点map 根据ID去重
				// {66={id=66, category=1, name=福建, labels=Company,
				// symbolSize=40}, 68={id=68, category=1, name=湖北,
				// labels=Company, symbolSize=40}}
				if (key.startsWith("n")) {
					Integer id = Integer.parseInt(String.valueOf(resulValueMap.get("_id")));
					if (pointMap.get(id) == null) {
						Map<String, Object> map = new HashMap<String, Object>();
						// 定义标签ID
						map.put("id", id);
						String labels = ((List<String>) resulValueMap.get("_labels")).get(0);
						map.put("labels", labels);
						// 类别
						int category = 0;
						
						// 省
						if ("Province".equals(labels)) {
							map.put("name", resulValueMap.get("province_name"));
							category = 0;
							// 学校
						} else if ("Univ".equals(labels)) {
							map.put("name", resulValueMap.get("univ_name"));
							category = 1;
							// 专业
						} else if ("Major".equals(labels)) {
							map.put("name", resulValueMap.get("major_name"));
							category = 2;
						}
						
						// 类别
						map.put("category", category);
						pointMap.put(id, map);
					}

					// 封装线Map 根据startId-->endId去重
					// {30-->60=, 30-->84=}
				} else if (key.startsWith("r")) {
					lineMap.put(resulValueMap.get("_startId") + "-->" + resulValueMap.get("_endId"), "");
				} else {
					System.out.println("返回的结果集请以 'n' 或者 'r' 开头！！！");
				}
			}
		}

		// 转Json
		return mapToJson(lineMap, pointMap);
	}

	/** 转json */
	private static JSONObject mapToJson(Map<String, String> lineMap, Map<Integer, Map<String, Object>> pointMap) {
		JSONObject jsonObject = new JSONObject();
		JSONArray lineJsonArray = new JSONArray();

		for (String key : lineMap.keySet()) {
			String[] split = key.split("-->");
			JSONObject lineJsonObject = new JSONObject();
			lineJsonObject.put("source", split[0]);
			lineJsonObject.put("target", split[1]);
			lineJsonArray.add(lineJsonObject);
		}
		// 点集合
		// "data":[{"id":66,"category":1,"name":"福建","labels":"Company","symbolSize":40},{"id":68,"category":1,"name":"湖北","labels":"Company","symbolSize":40}]
		jsonObject.element("data", pointMap.values());
		// 线集合
		// "links":[{"source":'7',"target":'2'},{"source":'3',"target":'2'}]
		jsonObject.element("links", lineJsonArray);
		return jsonObject;
	}

	/** 根据查询条件查询数据转换为页面需要的JSON */
	@Override
	public JSONObject find(ArrayList<String> list, String isQuestion) {
		String cypher = findCypher(list, isQuestion);
		JdbcUtil jdbcUtil = new JdbcUtil();
		jdbcUtil.getConnection();
		JSONObject json = null;
		List<Map<String, Object>> result = null;
		try {
			// 查询结果集
			if (!"".equals(cypher)) {
				result = jdbcUtil.findList(cypher);
				// 查询结果集转为json
				json = getPointLineJson(result);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != jdbcUtil) {
				jdbcUtil.close();
			}
		}
		return json;
	}

	/** 将查询条件转换为cypher语句 */
	private String findCypher(ArrayList<String> list, String isQuestion) {
		String cypher = "";

		// 判断查询条件数量写不同cql语句
		if (list.size() == 1) {
			String key = list.get(0).split("~~~")[0];
			String value = list.get(0).split("~~~")[1];
			cypher = "match (na:Province)-[r1]-(nb:Univ)-[r2]-(nc:Major) where 1=1" ;
			if (!value.contains("all")) {
				cypher += " and nb.univ = '" + value +"'";
			}
			cypher += " return na,nb,nc,r1,r2";
		}

		return cypher;
	}

	/** 查询页面下拉框内容 */
	@Override
	public JSONObject findLables() {
		JdbcUtil jdbcUtil = new JdbcUtil();
		jdbcUtil.getConnection();
		JSONObject json = new JSONObject();
		List<Map<String, Object>> result = null;
		try {
			// 查询结果集
			result = jdbcUtil
					.findList("match (na) where labels(na) in ['Actor','Director','Movie'] return labels(na)[0] as label,na.name as name,id(na) as value ");

			HashMap<String, List<HashMap<String, String>>> labelsMap = new HashMap<String, List<HashMap<String, String>>>();
			for (Map<String, Object> map : result) {
				HashMap<String, String> hashMap = new HashMap<String, String>();
				hashMap.put("name", String.valueOf(map.get("name")));
				hashMap.put("value", String.valueOf(map.get("value")));

				// 标签第一次进集合，添加全部和第一条
				if (labelsMap.get(map.get("label")) == null) {
					ArrayList<HashMap<String, String>> arrayList = new ArrayList<HashMap<String, String>>();
					HashMap<String, String> hashAll = new HashMap<String, String>();
					hashAll.put("name", "全部");
					hashAll.put("value", "all");
					arrayList.add(hashAll);
					arrayList.add(hashMap);
					labelsMap.put(map.get("label").toString(), arrayList);
				} else {
					// 如果标签已经存在，则添加下拉框内容
					List<HashMap<String, String>> list = labelsMap.get(map.get("label"));
					list.add(hashMap);
				}
			}
			json.putAll(labelsMap);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != jdbcUtil) {
				jdbcUtil.close();
			}
		}
		return json;
	}
}
