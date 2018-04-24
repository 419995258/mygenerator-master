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
	 * ����ʵ�����
	 */
	@Column(name="name")
	@Id
	private String tableName;
	

	
	/**
	 * ��ע��
	 */
	@Column(name="comment")
	private String comment;
	
	/**
	 * ��ʵ�ı��� ���磺t_user_info
	 */
	@Transient
	private String realTableName;
	
	//����Ҫ�־û����ֶ�
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
