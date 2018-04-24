package org.plasea.generator.main;

import org.plasea.generator.util.PropUtil;

public class MainCreateor {

	/**
	 * 标识数据库的类型：oracle或者mysql
	 */
	public static String dataBaseType ;
	public  void init(){
		dataBaseType = PropUtil.getValue("dataBaseType");
	}
	
	/**
	 * 生成代码函数
	 * 
	 * author 90
	 */
	public static void creater(){
		new MainCreateor().init();
		if("oracle".equals(dataBaseType)){//oracle数据库类型
			GenerateOracleMain.generate();
		}else{
			GenerateMain.generate();
		}
	}
	
	public static void main(String[] args) {
		creater();
	}
}
