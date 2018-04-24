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
 * �������������࣬���
 * @author Lenovo
 *
 */
public class GenerateMain {

	//���뻷��
	public static String environment ;
	//��Ŀ����
	public static String projectName;
	
	//ʵ�����
	public static String modelPackage;
	
	//����
	public static String tableName;
	
	//���ݿ���
	public static String dataBaseName;
	
	//��Ŀ��·��
	public static String projectPath;
	
	//basedao����
	public static String baseDaoPackage;
	
	//dao��ӿڰ���
	public static String daoBasePackage;
	
	//dao��ӿ�ʵ�ְ���
	public static String daoImpPackage;
	
	//ҵ���ӿڰ���
	public static String serviceBasePackage;
	
	//ҵ���ӿ�ʵ�ְ���
	public static String serviceImpPackage;
	//���Ʋ����
	public static String controllerPackage;
	//classPath
	public static String classPath;
	
	/**
	 * ��ʼ��ϵͳ����
	 */
	public  void init(){
		PropUtil.getInstatance();
		environment = PropUtil.getValue("environment");
		projectName = PropUtil.getValue("projectName");
		tableName = PropUtil.getValue("tableName");
		dataBaseName = PropUtil.getValue("dataBaseName");
		
		String thisProjectPath= PropUtil.getValue("projectPath")+"/src/main/";
		modelPackage = PropUtil.getValue("modelPackage");

		//����������Ӧ·��
		CreateFiles.createDir(thisProjectPath+"/"+modelPackage.replace(".", "/"));
		
		baseDaoPackage = PropUtil.getValue("baseDaoPackage");
		//����������Ӧ·��
	    CreateFiles.createDir(thisProjectPath+"/"+baseDaoPackage.replace(".", "/"));
	    
		daoBasePackage = PropUtil.getValue("daoBasePackage");
		//����������Ӧ·��
		CreateFiles.createDir(thisProjectPath+"/"+daoBasePackage.replace(".", "/"));
		
		daoImpPackage = PropUtil.getValue("daoImpPackage");
		//����������Ӧ·��
	    CreateFiles.createDir(thisProjectPath+"/"+daoImpPackage.replace(".", "/"));
	    
		serviceBasePackage = PropUtil.getValue("serviceBasePackage");
		//����������Ӧ·��
	    CreateFiles.createDir(thisProjectPath+"/"+serviceBasePackage.replace(".", "/"));
		serviceImpPackage = PropUtil.getValue("serviceImpPackage");
		//����������Ӧ·��
	    CreateFiles.createDir(thisProjectPath+"/"+serviceImpPackage.replace(".", "/"));
	    
		controllerPackage = PropUtil.getValue("controllerPackage");
		//����������Ӧ·��
		CreateFiles.createDir(thisProjectPath+"/"+controllerPackage.replace(".", "/"));
		
		classPath = PropUtil.getValue("classPath");
		
		String temp = PropUtil.getValue("projectPath");
		
		if(temp != null && !temp.equals("")){//�����������Ŀ����·��
			projectPath = temp + classPath;
		}else{//��ȡ��Ŀ·��
			String rootPath = getClass().getResource("/").getFile().toString();
			rootPath = rootPath.substring(1);
			System.out.println("rootPath:"+rootPath);
		    String[] pathes = rootPath.split("/");
		    String newPath = "";
		    for(int i=0;i<pathes.length-2;i++){//ʡȥbin����Ŀ·��
		    	newPath+= pathes[i]+"/";
		    }
		    System.out.println("newPath:"+newPath);
		    newPath += projectName+classPath;
		    projectPath = newPath;
		    System.out.println("��Ŀ·����"+projectPath);
		}
		
		
	}
	/**
	 * ���ɸ����ļ�
	 */
	public static void generate(){
		String tables[]=null;//һǧ�������ˡ�
		//��ȡ�����ļ�����ʼ������
		new GenerateMain().init();
		//�����жϣ��Ǹ������ݿ������Ǹ��ݵ�������������
		if(dataBaseName!=null&&!dataBaseName.equals("")){//�������ݿ��������ɵ�
			List tableList=TableFactory.getTablesByDataBase(dataBaseName);
			if(tableList!=null&&tableList.size()>0){
				tables=new String[tableList.size()];
				for (int i = 0; i < tableList.size(); i++) {
					tables[i]=(String) tableList.get(i);
				}
			}
			
		}else{
			//��ȡ��Ӧ������javaʵ��
			tables = tableName.split(",");
		}
		
		Table table = null;
		GenerateInfo generateInfo = null;
		//���������
		for(int i=0;i<tables.length;i++){
			table= TableFactory.generateTable(tables[i].trim());
			
			generateInfo = new GenerateInfo();
			//���ɰ������һ��
			String tablePackageName = table.getTableName().toLowerCase();
			
			generateInfo.setProjectPath(projectPath);
			generateInfo.setBaseDaoPackage(baseDaoPackage);
			/*generateInfo.setDaoBasePackage(daoBasePackage+"."+tablePackageName);
			generateInfo.setDaoImpPackage(daoImpPackage+"."+tablePackageName);
			generateInfo.setServiceBasePackage(serviceBasePackage+"."+tablePackageName);
			generateInfo.setServiceImpPackage(serviceImpPackage+"."+tablePackageName);
			generateInfo.setModelPackage(modelPackage+"."+tablePackageName);
			generateInfo.setControllerPackage(controllerPackage+"."+tablePackageName);*/
			
			//�޸�by 90 on 2018-1   ȥ�����ɰ��������һ��
			generateInfo.setDaoBasePackage(daoBasePackage );
			generateInfo.setDaoImpPackage(daoImpPackage );
			generateInfo.setServiceBasePackage(serviceBasePackage );
			generateInfo.setServiceImpPackage(serviceImpPackage );
			generateInfo.setModelPackage(modelPackage );
			generateInfo.setControllerPackage(controllerPackage);
			
			generateInfo.setUrlPrefix(tablePackageName);
			
			//���ɸ����ļ�
			ModelGenerator.generateModel( generateInfo,table);
			DaoBaseGenerator.generateBaseDao( generateInfo,table);
			DaoImpGenerator.generateBaseDao(generateInfo, table);
			ServiceBaseGenerator.generateServiceBase(generateInfo, table);
			ServiceImpGenerator.generateServiceBase(generateInfo, table);
			ControllerGenerator.generateController(generateInfo, table);
		}
		
		
	}

}
