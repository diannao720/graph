package com.pine.neo4j.entity;

import java.io.Serializable;

import javax.persistence.Entity;

/** 图谱展示实体类 */
@Entity(name = "KnowleggeEntity")
public class KnowleggeEntity implements Serializable {
	
	/**
	"baseInfoValueList": "菊糖",
	  "image": "http://a0.att.hudong.com/72/85/20200000013920144736851207227_s.jpg",
	  "detail": "[药理作用] 诊断试剂 人体内不含菊糖，静注后，不被机体分解、结合、利用和破坏，经肾小球滤过，通过测定血中和尿中的菊糖含量，可以准确计算肾小球的滤过率。菊糖广泛存在于植物组织中,约有3.6万种植物中含有菊糖,尤其是菊芋、菊苣块根中含有丰富的菊糖[6,8]。菊芋(Jerusalem artichoke)又名洋姜,多年生草本植物,在我国栽种广泛,其适应性广、耐贫瘠、产量高、易种植,一般亩产菊芋块茎为2 000～4 000 kg,菊芋块茎除水分外,还含有15%～20%的菊糖,是加工生产菊糖及其制品的良好原料。",
	  "title": "菊糖",
	  "baseInfoKeyList": "中文名：",
	  "url": "http://www.baike.com/wiki/菊糖",
	  "openTypeList": "健康科学##分子生物学##化学品##有机物##科学##自然科学##药品##药学名词##药物中文名称列表"
     */
	
	private static final long serialVersionUID = 1L;
	private String id;// 内部id
	private String baseInfoValueList;// 基本信息值
	private String image;// 图片
	private String detail;// 描述
	private String title;// 标题
	private String baseInfoKeyList;// 基本信息key
	private String openTypeList;// 类型
	private String docid;// 类型
	
	public KnowleggeEntity() {
    }
	
	public KnowleggeEntity(String baseInfoValueList, String image, String detail, 
			String title,String baseInfoKeyList, String openTypeList, String docid) {
		this.baseInfoValueList = baseInfoValueList;
		this.image = image;
		this.detail = detail;
		this.title = title;
		this.baseInfoKeyList = baseInfoKeyList;
		this.openTypeList = openTypeList;
		this.docid = docid;
    }
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBaseInfoValueList() {
		return baseInfoValueList;
	}
	public void setBaseInfoValueList(String baseInfoValueList) {
		this.baseInfoValueList = baseInfoValueList;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getBaseInfoKeyList() {
		return baseInfoKeyList;
	}
	public void setBaseInfoKeyList(String baseInfoKeyList) {
		this.baseInfoKeyList = baseInfoKeyList;
	}
	public String getOpenTypeList() {
		return openTypeList;
	}
	public void setOpenTypeList(String openTypeList) {
		this.openTypeList = openTypeList;
	}

	public String getDocid() {
		return docid;
	}

	public void setDocid(String docid) {
		this.docid = docid;
	}
	

}
