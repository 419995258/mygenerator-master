package org.plasea.generator.main;

import java.util.List;

import org.plasea.generator.controller.ControllerGenerator;
import org.plasea.generator.dao.hibernate.dao.DaoBaseGenerator;
import org.plasea.generator.dao.hibernate.imp.DaoImpGenerator;
import org.plasea.generator.database.mysql.Table;
import org.plasea.generator.database.mysql.TableFactory;
import org.plasea.generator.model.ModelGenerator;
import org.plasea.generator.service.imp.ServiceImpGenerator;
import org.plasea.generator.service.service.ServiceBaseGenerator;
import org.plasea.generator.util.CreateFiles;
import org.plasea.generator.util.GenerateInfo;
import org.plasea.generator.util.PropUtil;

/**
 * 代码生成器主类，入口
 * @author Lenovo
 *
 */
public class GenerateMain {

	//代码环境
	public static String environment ;
	//项目名称
	public static String projectName;
	
	//实体包名
	public static String modelPackage;
	
	//表名
	public static String tableName;
	
	//数据库名
	public static String dataBaseName;
	
	//项目根路径
	public static String projectPath;
	
	//basedao包名
	public static String baseDaoPackage;
	
	//dao层接口包名
	public static String daoBasePackage;
	
	//dao层接口实现包名
	public static String daoImpPackage;
	
	//业务层接口包名
	public static String serviceBasePackage;
	
	//业务层接口实现包名
	public static String serviceImpPackage;
	//控制层包名
	public static String controllerPackage;
	//classPath
	public static String classPath;
	
	/**
	 * 初始化系统变量
	 */
	public  void init(){
		PropUtil.getInstatance();
		environment = PropUtil.getValue("environment");
		projectName = PropUtil.getValue("projectName");
		tableName = PropUtil.getValue("tableName");
		dataBaseName = PropUtil.getValue("dataBaseName");
		
		String thisProjectPath= PropUtil.getValue("projectPath")+"/src/main/";
		modelPackage = PropUtil.getValue("modelPackage");

		//创建包名对应路径
		CreateFiles.createDir(thisProjectPath+"/"+modelPackage.replace(".", "/"));
		
		baseDaoPackage = PropUtil.getValue("baseDaoPackage");
		//创建包名对应路径
	    CreateFiles.createDir(thisProjectPath+"/"+baseDaoPackage.replace(".", "/"));
	    
		daoBasePackage = PropUtil.getValue("daoBasePackage");
		//创建包名对应路径
		CreateFiles.createDir(thisProjectPath+"/"+daoBasePackage.replace(".", "/"));
		
		daoImpPackage = PropUtil.getValue("daoImpPackage");
		//创建包名对应路径
	    CreateFiles.createDir(thisProjectPath+"/"+daoImpPackage.replace(".", "/"));
	    
		serviceBasePackage = PropUtil.getValue("serviceBasePackage");
		//创建包名对应路径
	    CreateFiles.createDir(thisProjectPath+"/"+serviceBasePackage.replace(".", "/"));
		serviceImpPackage = PropUtil.getValue("serviceImpPackage");
		//创建包名对应路径
	    CreateFiles.createDir(thisProjectPath+"/"+serviceImpPackage.replace(".", "/"));
	    
		controllerPackage = PropUtil.getValue("controllerPackage");
		//创建包名对应路径
		CreateFiles.createDir(thisProjectPath+"/"+controllerPackage.replace(".", "/"));
		
		classPath = PropUtil.getValue("classPath");
		
		String temp = PropUtil.getValue("projectPath");
		
		if(temp != null && !temp.equals("")){//如果配置了项目绝对路径
			projectPath = temp + classPath;
		}else{//获取项目路径
			String rootPath = getClass().getResource("/").getFile().toString();
			rootPath = rootPath.substring(1);
			System.out.println("rootPath:"+rootPath);
		    String[] pathes = rootPath.split("/");
		    String newPath = "";
		    for(int i=0;i<pathes.length-2;i++){//省去bin和项目路径
		    	newPath+= pathes[i]+"/";
		    }
		    System.out.println("newPath:"+newPath);
		    newPath += projectName+classPath;
		    projectPath = newPath;
		    System.out.println("项目路径："+projectPath);
		}
		
		
	}
	/**
	 * 生成各种文件
	 */
	public static void generate(){
		String tables[]=null;//一千个表，够了。
		//读取配置文件，初始化变量
		new GenerateMain().init();
		//首先判断，是根据数据库名还是根据单个表名来生成
		if(dataBaseName!=null&&!dataBaseName.equals("")){//根究数据库名来生成的
			List tableList=TableFactory.getTablesByDataBase(dataBaseName);
			if(tableList!=null&&tableList.size()>0){
				tables=new String[tableList.size()];
				for (int i = 0; i < tableList.size(); i++) {
					tables[i]=(String) tableList.get(i);
				}
			}
			
		}else{
			//读取响应表，生成java实体
			tables = tableName.split(",");
		}
		
		Table table = null;
		GenerateInfo generateInfo = null;
		//多个表生成
		for(int i=0;i<tables.length;i++){
			table= TableFactory.generateTable(tables[i].trim());
			
			generateInfo = new GenerateInfo();
			//生成包名最后一层
			String tablePackageName = table.getTableName().toLowerCase();
			
			generateInfo.setProjectPath(projectPath);
			generateInfo.setBaseDaoPackage(baseDaoPackage);
			/*generateInfo.setDaoBasePackage(daoBasePackage+"."+tablePackageName);
			generateInfo.setDaoImpPackage(daoImpPackage+"."+tablePackageName);
			generateInfo.setServiceBasePackage(serviceBasePackage+"."+tablePackageName);
			generateInfo.setServiceImpPackage(serviceImpPackage+"."+tablePackageName);
			generateInfo.setModelPackage(modelPackage+"."+tablePackageName);
			generateInfo.setControllerPackage(controllerPackage+"."+tablePackageName);*/
			
			//修改by 90 on 2018-1   去掉生成包名的最后一层
			generateInfo.setDaoBasePackage(daoBasePackage );
			generateInfo.setDaoImpPackage(daoImpPackage );
			generateInfo.setServiceBasePackage(serviceBasePackage );
			generateInfo.setServiceImpPackage(serviceImpPackage );
			generateInfo.setModelPackage(modelPackage );
			generateInfo.setControllerPackage(controllerPackage);
			
			generateInfo.setUrlPrefix(tablePackageName);
			
			//生成各种文件
			ModelGenerator.generateModel( generateInfo,table);
			DaoBaseGenerator.generateBaseDao( generateInfo,table);
			DaoImpGenerator.generateBaseDao(generateInfo, table);
			ServiceBaseGenerator.generateServiceBase(generateInfo, table);
			ServiceImpGenerator.generateServiceBase(generateInfo, table);
			ControllerGenerator.generateController(generateInfo, table);
		}
		
		
	}

}
