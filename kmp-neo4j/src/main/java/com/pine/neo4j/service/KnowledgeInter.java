
package com.pine.neo4j.service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pine.neo4j.entity.KnowleggeEntity;
import com.pine.neo4j.entity.RelationBean;

/**
 * @author AgileLARUS
 * @since 3.0.2
 */
public interface KnowledgeInter{	
	
	public int insertNode(KnowleggeEntity params);

	public List<Map<String, Object>> selectNode(Map params);

	public int insertNodeAndRelation(Map params);

	public List<Map<String, Object>> selectTable(Map params);

	public List<Map<String, Object>> selectTableByNode(Map params);

	public List<Map<String, Object>> selectTableByCount();

	public List<Map<String, Object>> selectNodeByDoc(String docid);

	public List<Map<String, Object>> findRelation(Map params);
}
