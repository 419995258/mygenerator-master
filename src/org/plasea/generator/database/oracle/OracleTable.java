package org.plasea.generator.database.oracle;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class OracleTable {

	/**
	 * 对象实体表名
	 */
	@Column(name="name")
	@Id
	private String tableName;
	

	
	/**
	 * 表注释
	 */
	@Column(name="comment")
	private String comment;
	
	/**
	 * 真实的表名 例如：t_user_info
	 */
	@Transient
	private String realTableName;
	
	//不需要持久化的字段
	@Transient
	private List<OracleTableField> fields;
	
	
	
	

	public String getRealTableName() {
		return realTableName;
	}

	public void setRealTableName(String realTableName) {
		this.realTableName = realTableName;
	}

	public List<OracleTableField> getFields() {
		return fields;
	}

	public void setFields(List<OracleTableField> fields) {
		this.fields = fields;
	}

	@Column(name="name")
	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}


	@Column(name="comment")
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	

	
	
	
	
}
