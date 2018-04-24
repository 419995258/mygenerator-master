package org.plasea.generator.dao.hibernate.imp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.plasea.generator.database.mysql.Table;
import org.plasea.generator.database.oracle.OracleTable;
import org.plasea.generator.main.GenerateMain;
import org.plasea.generator.model.ModelGenerator;
import org.plasea.generator.util.GenerateInfo;
import org.plasea.generator.util.PathUtil;
import org.plasea.generator.util.UpperFirstCharacter;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
/**
 * dao��ʵ��������
 * @author Lenovo
 *
 */
public class DaoImpGenerator {

	/**
	 * ����dao��ʵ��
	 * @param projectPath
	 * @param baseDaoPackage
	 * @param table
	 */
	public static void generateBaseDao(GenerateInfo generateInfo,Table table){
		String packagePath = PathUtil.getPackagePath(generateInfo.getProjectPath(), generateInfo.getDaoImpPackage());
		
		Configuration cfg = new Configuration();  
        try {  
            cfg.setClassForTemplateLoading(DaoImpGenerator.class, "/org/plasea/generator/dao/hibernate/imp");//ָ��ģ�����ڵ�classpathĿ¼  
            cfg.setSharedVariable("upperFC", new UpperFirstCharacter());//����һ��"��"������������������������ĸ��д  
            Template t = cfg.getTemplate("dao-imp.html");//ָ��ģ��  
            String className= table.getTableName().substring(0, 1).toUpperCase()+table.getTableName().substring(1);
            String fileName = className+"DaoImp.java";
            System.out.println("�ļ����ƣ�"+fileName);
            File file = new File(packagePath);
            if(!file.exists()){
           	 file.mkdir();
            }
            FileOutputStream fos = new FileOutputStream(new File(packagePath+fileName));//java�ļ�������Ŀ¼  
              
            //����Դ  
            Map data = new HashMap();  
            data.put("generateInfo", generateInfo);//������Ϣ��
            data.put("className", className); //����
            data.put("table", table);//������
            data.put("generateTime", new Date());//����ʱ��
            t.process(data, new OutputStreamWriter(fos,"utf-8"));//  
            fos.flush();  
            fos.close();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } catch (TemplateException e) {  
            e.printStackTrace();  
        }  
	}
	
	/**
	 * ����dao��ʵ��
	 * @param projectPath
	 * @param baseDaoPackage
	 * @param table
	 */
	public static void generateBaseDao(GenerateInfo generateInfo,OracleTable table){
		String packagePath = PathUtil.getPackagePath(generateInfo.getProjectPath(), generateInfo.getDaoImpPackage());
		
		Configuration cfg = new Configuration();  
        try {  
            cfg.setClassForTemplateLoading(DaoImpGenerator.class, "/org/plasea/generator/dao/hibernate/imp");//ָ��ģ�����ڵ�classpathĿ¼  
            cfg.setSharedVariable("upperFC", new UpperFirstCharacter());//����һ��"��"������������������������ĸ��д  
            Template t = cfg.getTemplate("dao-imp.html");//ָ��ģ��  
            String className= table.getTableName().substring(0, 1).toUpperCase()+table.getTableName().substring(1);
            String fileName = className+"DaoImp.java";
            System.out.println("�ļ����ƣ�"+fileName);
            File file = new File(packagePath);
            if(!file.exists()){
           	 file.mkdir();
            }
            FileOutputStream fos = new FileOutputStream(new File(packagePath+fileName));//java�ļ�������Ŀ¼  
              
            //����Դ  
            Map data = new HashMap();  
            data.put("generateInfo", generateInfo);//������Ϣ��
            data.put("className", className); //����
            data.put("table", table);//������
            data.put("generateTime", new Date());//����ʱ��
            t.process(data, new OutputStreamWriter(fos,"utf-8"));//  
            fos.flush();  
            fos.close();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } catch (TemplateException e) {  
            e.printStackTrace();  
        }  
	}
}