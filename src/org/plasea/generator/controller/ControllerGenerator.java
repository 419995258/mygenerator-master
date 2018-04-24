package org.plasea.generator.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.plasea.generator.database.mysql.Table;
import org.plasea.generator.database.oracle.OracleTable;
import org.plasea.generator.model.ModelGenerator;
import org.plasea.generator.util.GenerateInfo;
import org.plasea.generator.util.PathUtil;
import org.plasea.generator.util.UpperFirstCharacter;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 控制层生成类
 * @author Lenovo
 *
 */
public class ControllerGenerator {

	/**
	 * 生成控制层
	 * @param generateInfo
	 * @param table
	 */
	public static void generateController(GenerateInfo generateInfo,Table table){
		String packagePath = PathUtil.getPackagePath(generateInfo.getProjectPath(), generateInfo.getControllerPackage());
		
		Configuration cfg = new Configuration();  
        try {  
            cfg.setClassForTemplateLoading(ControllerGenerator.class, "/org/plasea/generator/controller");//指定模板所在的classpath目录  
            cfg.setSharedVariable("upperFC", new UpperFirstCharacter());//添加一个"宏"共享变量用来将属性名首字母大写  
            Template t = cfg.getTemplate("controller.html");//指定模板  
            
            String className= table.getTableName().substring(0, 1).toUpperCase()+table.getTableName().substring(1);
            String fileName = className+"Controller.java";
            System.out.println("文件名称："+fileName);
            File file = new File(packagePath);
            if(!file.exists()){
           	 file.mkdir();
            }
            FileOutputStream fos = new FileOutputStream(new File(packagePath+fileName));//java文件的生成目录  
              
            //数据源  
            Map data = new HashMap();  
            data.put("generateInfo", generateInfo);//生成信息类
            data.put("className", className); //类名
            data.put("table", table);//表对象
            data.put("generateTime", new Date());//生成时间
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
	 * 生成控制层
	 * @param generateInfo
	 * @param table
	 */
	public static void generateController(GenerateInfo generateInfo,OracleTable table){
		String packagePath = PathUtil.getPackagePath(generateInfo.getProjectPath(), generateInfo.getControllerPackage());
		
		Configuration cfg = new Configuration();  
        try {  
            cfg.setClassForTemplateLoading(ControllerGenerator.class, "/org/plasea/generator/controller");//指定模板所在的classpath目录  
            cfg.setSharedVariable("upperFC", new UpperFirstCharacter());//添加一个"宏"共享变量用来将属性名首字母大写  
            Template t = cfg.getTemplate("controller.html");//指定模板  
            
            String className= table.getTableName().substring(0, 1).toUpperCase()+table.getTableName().substring(1);
            String fileName = className+"Controller.java";
            System.out.println("文件名称："+fileName);
            File file = new File(packagePath);
            if(!file.exists()){
           	 file.mkdir();
            }
            FileOutputStream fos = new FileOutputStream(new File(packagePath+fileName));//java文件的生成目录  
              
            //数据源  
            Map data = new HashMap();  
            data.put("generateInfo", generateInfo);//生成信息类
            data.put("className", className); //类名
            data.put("table", table);//表对象
            data.put("generateTime", new Date());//生成时间
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
