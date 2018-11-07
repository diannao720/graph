
package com.pine.neo4j.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.pine.neo4j.entity.KnowleggeEntity;
import com.pine.neo4j.entity.RelationBean;
import com.pine.neo4j.service.KnowledgeInter;
import com.pine.neo4j.util.JdbcUtil;

/**
 * @author AgileLARUS
 * @since 3.0.2
 */
@Service
public class KnowledgeImpl implements KnowledgeInter{


	@Transactional
	@Override
	public List<Map<String, Object>> selectNode(Map params) {
		// TODO Auto-generated method stub
		String cypher = "MATCH f=(n:Knowledge{title:'"+params.get("name")+"'})-[]-()  RETURN f" ;	
		JdbcUtil jdbcUtil = new JdbcUtil();
		jdbcUtil.getConnection();
		JSONObject json = null;
		List<Map<String, Object>> result = null;
		try {
			// 查询结果集
			if (!"".equals(cypher)) {
				result = jdbcUtil.findList(cypher);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != jdbcUtil) {
				jdbcUtil.close();
			}
		}
		return result;
	}
	
	@Transactional
	@Override
	public int insertNode(KnowleggeEntity params) {
		
		String cypher = "Create (p:Knowledge {openTypeList:'"+params.getOpenTypeList()+"',"
				+ "title: '"+params.getTitle()+"',"
				+ "image: '"+params.getImage()+"',"
				+ "detail: '"+params.getDetail()+"',"
				+ "baseInfoKeyList: '"+params.getBaseInfoKeyList()+"',"
				+ "baseInfoValueList: '"+params.getBaseInfoValueList()+"',"
				+ "docid: '"+params.getDocid()+"'})" ;
		JdbcUtil jdbcUtil = new JdbcUtil();
		jdbcUtil.getConnection();
		JSONObject json = null;
		int result = 0;
		try {
			// 查询结果集
			if (!"".equals(cypher)) {
				result = jdbcUtil.update(cypher);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != jdbcUtil) {
				jdbcUtil.close();
			}
		}
		return result;
	}

	@Transactional
	@Override
	public int insertNodeAndRelation(Map params) {
		//String cypher = "MATCH (a:Knowledge),(b:Knowledge) WHERE a.name = '"+params.get("nameA")+"' AND b.name = '"+params.get("nameB")+"' CREATE (a)-[f:Relation{type:'"+params.get("relation")+"'}]->(b) RETURN (b)-[f]-(a)" ;
		// TODO Auto-generated method stub
		String cypher = "CREATE (a:Knowledge{name:'"+params.get("nameA")+"'})-[f:Relation{type:'"+params.get("relation")+"'}]->(b:Knowledge{name:'"+params.get("nameB")+"'})"; 
		JdbcUtil jdbcUtil = new JdbcUtil();
		jdbcUtil.getConnection();
		JSONObject json = null;
		int result = 0;
		try {
			// 查询结果集
			if (!"".equals(cypher)) {
				result = jdbcUtil.update(cypher);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != jdbcUtil) {
				jdbcUtil.close();
			}
		}
		return result;
	}

	@Transactional
	@Override
	public List<Map<String, Object>> selectTable(Map params) {
		// TODO Auto-generated method stub
		String cypher = "MATCH (n:HudongItem) RETURN n.title skip "+params.get("pagenum")+" limit 100" ;	
		JdbcUtil jdbcUtil = new JdbcUtil();
		jdbcUtil.getConnection();
		JSONObject json = null;
		List<Map<String, Object>> result = null;
		try {
			// 查询结果集
			if (!"".equals(cypher)) {
				result = jdbcUtil.findList(cypher);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != jdbcUtil) {
				jdbcUtil.close();
			}
		}
		return result;
	}

	@Transactional
	@Override
	public List<Map<String, Object>> selectTableByNode(Map params) {
		// TODO Auto-generated method stub
		String cypher = "MATCH (n:HudongItem{title:'"+params.get("name")+"'}) RETURN n" ;	
		JdbcUtil jdbcUtil = new JdbcUtil();
		jdbcUtil.getConnection();
		JSONObject json = null;
		List<Map<String, Object>> result = null;
		try {
			// 查询结果集
			if (!"".equals(cypher)) {
				result = jdbcUtil.findList(cypher);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != jdbcUtil) {
				jdbcUtil.close();
			}
		}
		return result;
	}

	@Transactional
	@Override
	public List<Map<String, Object>> selectTableByCount() {
		// TODO Auto-generated method stub
		String cypher = "MATCH (n:HudongItem) RETURN count(n)" ;	
		JdbcUtil jdbcUtil = new JdbcUtil();
		jdbcUtil.getConnection();
		JSONObject json = null;
		List<Map<String, Object>> result = null;
		try {
			// 查询结果集
			if (!"".equals(cypher)) {
				result = jdbcUtil.findList(cypher);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != jdbcUtil) {
				jdbcUtil.close();
			}
		}
		return result;
	}

	@Override
	public List<Map<String, Object>> selectNodeByDoc(String docid) {
		// TODO Auto-generated method stub
		String cypher = "MATCH f=(n:Knowledge{docid:'"+docid+"'})  RETURN f" ;	
		JdbcUtil jdbcUtil = new JdbcUtil();
		jdbcUtil.getConnection();
		JSONObject json = null;
		List<Map<String, Object>> result = null;
		try {
			// 查询结果集
			if (!"".equals(cypher)) {
				result = jdbcUtil.findList(cypher);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != jdbcUtil) {
				jdbcUtil.close();
			}
		}
		return result;
	}
	
	public List<Map<String, Object>> ShortestPathsByBeo4jBFS(String name1, String name2) {
		// TODO Auto-generated method stub
		String cypher = "MATCH (ms:Knowledge { title:'"+name1+"' }),(cs:Knowledge { title:'"+name2+"' }),"
                                + " p = ShortestPath((ms)-[*..4]-(cs)) " + "RETURN p;" ;	
		JdbcUtil jdbcUtil = new JdbcUtil();
		jdbcUtil.getConnection();
		JSONObject json = null;
		List<Map<String, Object>> result = null;
		try {
			// 查询结果集
			if (!"".equals(cypher)) {
				result = jdbcUtil.findList(cypher);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != jdbcUtil) {
				jdbcUtil.close();
			}
		}
		return result;
	}
	
	public List<Map<String, Object>> allPathsByBeo4j(String name1, String name2) {
		// TODO Auto-generated method stub
		String cypher = "MATCH (ms:Knowledge { title:'"+name1+"' }),(cs:Knowledge { title:'"+name2+"' }),"
                                + " p = allShortestPaths((ms)-[*..4]-(cs)) " + "RETURN p;" ;	
		JdbcUtil jdbcUtil = new JdbcUtil();
		jdbcUtil.getConnection();
		JSONObject json = null;
		List<Map<String, Object>> result = null;
		try {
			// 查询结果集
			if (!"".equals(cypher)) {
				result = jdbcUtil.findList(cypher);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != jdbcUtil) {
				jdbcUtil.close();
			}
		}
		return result;
	}

	@Override
	public List<Map<String, Object>> findRelation(Map params) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> result = null;
		Integer select = (int)params.get("algorithm");
		switch (select) {
        case 1:
            result = ShortestPathsByBeo4jBFS((String)params.get("name1"), (String)params.get("name2"));
            break;
        case 2:
            result = allPathsByBeo4j((String)params.get("name1"), (String)params.get("name2"));
            break;

        default:
            result = ShortestPathsByBeo4jBFS((String)params.get("name1"), (String)params.get("name2"));
            break;
		}
		return result;
	}


}
