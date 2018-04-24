package org.plasea.generator.util;

import java.io.File;

public class CreateFiles {


	/**
	 * 创建文件夹
	 * @param destDirName
	 * @return
	 * author 90
	 * 2018年1月18日
	 */
	public static boolean createDir(String destDirName) {  
        File dir = new File(destDirName);  
        if (dir.exists()) {  
            System.out.println("创建目录" + destDirName + "失败，目标目录已经存在");  
            return false;  
        }  
        if (!destDirName.endsWith(File.separator)) {  
            destDirName = destDirName + File.separator;  
        }  
        //创建目录  
        if (dir.mkdirs()) {  
            System.out.println("创建目录" + destDirName + "成功！");  
            return true;  
        } else {  
            System.out.println("创建目录" + destDirName + "失败！");  
            return false;  
        }  
    } 
	
	
	public static void main(String[] args) {  
        //创建目录  
        String dirName = "D:/test/temp/temp0/temp1";  
        CreateFiles.createDir(dirName);  
        //创建文件  
       
    }  
}
