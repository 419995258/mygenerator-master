package org.plasea.generator.main;

import org.plasea.generator.util.PropUtil;

public class MainCreateor {

	/**
	 * ��ʶ���ݿ�����ͣ�oracle����mysql
	 */
	public static String dataBaseType ;
	public  void init(){
		dataBaseType = PropUtil.getValue("dataBaseType");
	}
	
	/**
	 * ���ɴ��뺯��
	 * 
	 * author 90
	 */
	public static void creater(){
		new MainCreateor().init();
		if("oracle".equals(dataBaseType)){//oracle���ݿ�����
			GenerateOracleMain.generate();
		}else{
			GenerateMain.generate();
		}
	}
	
	public static void main(String[] args) {
		creater();
	}
}
