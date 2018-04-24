package org.plasea.generator.database.mysql;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.plasea.generator.util.NameUtil;

import com.mysql.jdbc.Connection;
/**
 * ��ȡ���ݿ��������Ӧ��java����
 * @author Lenovo
 *
 */
public class TableFactory {

	/**
	 * �����ݿ���ȡ��java����
	 * @param tableName
	 * @return
	 */
	public static Table generateTable(String tableName) {
		// ʵ����Configuration�����д���Ĭ�ϼ���hibernate.cfg.xml�ļ�,����ļ�û����src��·���£�������·����Ϊ��������
		Configuration conf = new AnnotationConfiguration().configure();
		// ��Configuration����SessionFactory
		SessionFactory sf = conf.buildSessionFactory();
		// ʵ����Session
		Session session = sf.openSession();
		// ��ʼ����
		Transaction tx = session.beginTransaction();

		String sql = "show table status where name='" + tableName + "'";

		List<Table> tables = session.createSQLQuery(sql).addEntity(Table.class)
				.list();

		if ((tables==null&&tables.size()==0)||tables.size() > 1) {
			System.out.println("��ѯ�������쳣����������");
			return null;
		}

		Table table = tables.get(0);

		System.out.println(table.getTableName() + " : " + table.getComment());

		// ��ѯ���ֶ�
		String sql2 = "show full fields from " + tableName;

		List<TableField> fields = session.createSQLQuery(sql2)
				.addEntity(TableField.class).list();

		// �ύ����
		tx.commit();
		// �ر�Session
		session.close();
		table.setTableName(NameUtil.dealName(table.getTableName()));// �Ա������д������java�����ʽ
		System.out.println("�������" + table.getTableName() + " : "
				+ table.getComment());

		for (TableField filed : fields) {
			NameUtil.dealTable(filed);

			System.out.println("�ֶ����ƣ�" + filed.getField() + " ���ͣ�"
					+ filed.getType() + " ע��:" + filed.getComment() + "�Ƿ�������"
					+ filed.getKey() + " ���ֶ����ƣ�" + filed.getColumnName()
					+ " �Ƿ������" + filed.getExtra());
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
	 * 2018��1��
	 */
	public static List getTablesByDataBase(String dataBaseName) {
		Connection conn = null;
		// ʵ����Configuration�����д���Ĭ�ϼ���hibernate.cfg.xml�ļ�,����ļ�û����src��·���£�������·����Ϊ��������

		Configuration conf = new AnnotationConfiguration().configure();
		// ��Configuration����SessionFactory
		SessionFactory sf = conf.buildSessionFactory();
		String sql = " select table_name from information_schema.tables where table_schema='"
				+ dataBaseName + "' ";
		// ʵ����Session
		Session session = sf.openSession();
		List tables = session.createSQLQuery(sql).list();
		// �ر�Session
		session.close();
		return tables;
	}

	public static void main(String[] args) {
		// generateTable("news_table");
		getTablesByDataBase("ejiaoyan_studio");
	}
	
}
