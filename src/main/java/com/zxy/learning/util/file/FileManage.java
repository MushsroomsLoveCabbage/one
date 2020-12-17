package com.zxy.learning.util.file;

import com.sun.deploy.net.HttpResponse;

import java.io.File;
import java.io.FileInputStream;

public class FileManage {

    public static void main(String[] args) {
        File file = new File("G:\\Common\\Video");
        readFile(file);
    }

    public static void test(String fileName){
        File file = new File(fileName);
        if(file.isDirectory()){
            File[] files = file.listFiles();
            for(File single:files){
                test(single.getName());
            }
        } else {
            readFileInfo(file.getName());
        }

    }


    public static void readFile(File file){
        if(file.isDirectory()){
            File[] files = file.listFiles();
            for(File single:files){
                readFile(single);
            }
        } else {
            System.out.println("fileName: " + file.getName() + " fileSize: " + file.length() + " filePath: " +file.getAbsolutePath() );
        }
    }

    public static void readFileData(File file) throws Exception{
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            fileInputStream.available();
            HttpResponse httpResponse= null;
            byte[] data = new byte[1024*1024*16];
            while(fileInputStream.read(data)!= -1){

            }
        }catch (Exception e){

        }finally {
            try {
                fileInputStream.close();
            }catch (Exception e){
                e.printStackTrace();
            }

        }

    }
    public static void readFileInfo(String fileName){
        File file = new File(fileName);
        System.out.println(file.getName() + " " + file.length() + " " +file.getAbsolutePath() );
    }
}
