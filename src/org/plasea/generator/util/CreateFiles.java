package org.plasea.generator.util;

import java.io.File;

public class CreateFiles {


	/**
	 * �����ļ���
	 * @param destDirName
	 * @return
	 * author 90
	 * 2018��1��18��
	 */
	public static boolean createDir(String destDirName) {  
        File dir = new File(destDirName);  
        if (dir.exists()) {  
            System.out.println("����Ŀ¼" + destDirName + "ʧ�ܣ�Ŀ��Ŀ¼�Ѿ�����");  
            return false;  
        }  
        if (!destDirName.endsWith(File.separator)) {  
            destDirName = destDirName + File.separator;  
        }  
        //����Ŀ¼  
        if (dir.mkdirs()) {  
            System.out.println("����Ŀ¼" + destDirName + "�ɹ���");  
            return true;  
        } else {  
            System.out.println("����Ŀ¼" + destDirName + "ʧ�ܣ�");  
            return false;  
        }  
    } 
	
	
	public static void main(String[] args) {  
        //����Ŀ¼  
        String dirName = "D:/test/temp/temp0/temp1";  
        CreateFiles.createDir(dirName);  
        //�����ļ�  
       
    }  
}
