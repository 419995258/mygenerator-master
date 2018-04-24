package org.plasea.generator.database.oracle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.hibernate.transform.Transformers;
import org.plasea.generator.database.mysql.Table;
import org.plasea.generator.database.mysql.TableField;
import org.plasea.generator.util.NameUtil;

import com.mysql.jdbc.Connection;
/**
 * 读取数据库表生成相应的java对象
 * @author Lenovo
 *
 */
public class OracleTableFactory {

	/**
	 * 把数据库表读取成java对象
	 * @param tableName
	 * @return
	 */
	public static OracleTable generateTable(String tableName) {
		// 实例化Configuration，这行代码默认加载hibernate.cfg.xml文件,如果文件没有在src根路径下，可以用路径作为参数传递
		Configuration conf = new AnnotationConfiguration().configure();
		// 以Configuration创建SessionFactory
		SessionFactory sf = conf.buildSessionFactory();
		// 实例化Session
		Session session = sf.openSession();
		// 开始事务
		Transaction tx = session.beginTransaction();
		tableName=tableName.toUpperCase();
		//String sql = "show table status where name='" + tableName + "'";
        //String sql=" SELECT T.TABLE_NAME AS NAME FROM user_tab_cols T WHERE T.TABLE_NAME='" + tableName + "'";
		String sql="SELECT  TABLE_NAME as NAME,COMMENTS   FROM  USER_TAB_COMMENTS  WHERE  TABLE_NAME ='" + tableName + "'";// 'BASE_APPLICATIONS'";
		List<Map<String,Object>>  tableList =   session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		OracleTable table=new OracleTable();
		if(tableList!=null&&tableList.size()>0){
			Map<String,Object> thisTable=tableList.get(0);
			table.setComment(thisTable.get("COMMENTS")==null?"":thisTable.get("COMMENTS").toString());
			table.setRealTableName(thisTable.get("NAME").toString());
			table.setTableName(thisTable.get("NAME").toString());
		}
		
		/*List<OracleTable> tables = session.createSQLQuery(sql).addEntity(OracleTable.class).
				list();
        //List<Table> tables = session.createSQLQuery(sql).addEntity(Table.class).list();
		if ((tables==null&&tables.size()==0)) {
			System.out.println("查询表数据异常！！！！！");
			return null;
		}*/
		 
		System.out.println(table.getTableName() + " : " );
		
		
		List<OracleTableField> fields=new ArrayList<OracleTableField>();
		// 查询表字段
		String sql2 = "select COLUMN_NAME AS field,DATA_TYPE AS type,NULLABLE  , DATA_LENGTH     from user_tab_columns     where table_name ='"+tableName+"'  ";
		List<Map<String,Object>>  fieldsList =   session.createSQLQuery(sql2).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		// 提交事务
		
		String keySql="select col.column_name     from user_constraints con,  user_cons_columns col     where con.constraint_name = col.constraint_name     and con.constraint_type='P'     and col.table_name ='"+tableName+"'  ";
		List<Map<String,Object>>  keyList= session.createSQLQuery(keySql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		
		String keyColumn="select *   from user_col_comments   where Table_Name ='"+tableName+"'  ";
		List<Map<String,Object>>  keyColumnList= session.createSQLQuery(keyColumn).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		Map<String,String> keyColumnMap=new HashMap<String,String>();
		if(keyColumnList!=null&&keyColumnList.size()>0){
			for (int i = 0; i < keyColumnList.size(); i++) {
				Map<String,Object> thisMap=keyColumnList.get(i);
				keyColumnMap.put(thisMap.get("COLUMN_NAME").toString(), thisMap.get("COMMENTS")==null?"":thisMap.get("COMMENTS").toString());
			}
		}
		String keyStr="";
		if(keyList!=null&&keyList.size()>0){
			keyStr=keyList.get(0).get("COLUMN_NAME")==null?"":keyList.get(0).get("COLUMN_NAME").toString();
		}
		
		tx.commit();
		// 关闭Session
		session.close();
		table.setTableName(NameUtil.dealName(table.getTableName()));// 对表名进行处理，变成java对象格式
		System.out.println("类表名：" + table.getTableName() + " : " );
		for (Map<String,Object> filedMap: fieldsList) {
			OracleTableField  filed= new OracleTableField();
			filed.setField(filedMap.get("FIELD").toString());
			filed.setType(filedMap.get("TYPE").toString());
			filed.setKey(filedMap.get("FIELD").toString().equals(keyStr)?"PRI":"");
			filed.setComment(keyColumnMap.get(filedMap.get("FIELD").toString()));
			filed.setColumnName(filedMap.get("FIELD").toString());
			filed.setExtra("");
			NameUtil.dealOracleTable(filed);
			fields.add(filed);
			System.out.println("字段名称：" + filed.getField() + " 类型："
					+ filed.getType() + " 注释:" + filed.getComment() + "是否主键："
					+ filed.getKey() + " 表字段名称：" + filed.getColumnName()
					+ " 是否递增：" + filed.getExtra());
		}
		
		table.setFields(fields);
		table.setRealTableName(tableName);
		return table;
	}

	/**
	 * 
	 * @param dataBaseName
	 * @return
	 * author 90
	 * 2018年1月
	 */
	public static List getTablesByDataBase(String dataBaseName) {
		Connection conn = null;
		// 实例化Configuration，这行代码默认加载hibernate.cfg.xml文件,如果文件没有在src根路径下，可以用路径作为参数传递

		Configuration conf = new AnnotationConfiguration().configure();
		// 以Configuration创建SessionFactory
		SessionFactory sf = conf.buildSessionFactory();
		String sql = " select * from user_tables  ";
				 
		// 实例化Session
		Session session = sf.openSession();
		List tables = session.createSQLQuery(sql).list();
		// 关闭Session
		session.close();
		return tables;
	}

	public static void main(String[] args) {
		// generateTable("news_table");
		getTablesByDataBase("BASE_APPLICATIONS");
	}
	
}
