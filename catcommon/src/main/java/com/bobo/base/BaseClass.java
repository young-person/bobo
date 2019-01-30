package com.bobo.base;

import com.fasterxml.jackson.databind.ObjectMapper;

public class BaseClass {
    protected static ObjectMapper objectMapper = new ObjectMapper();


    /**
     * 将 图片插入到现有的Excel里面
     * @throws Exception
     */
//    public static void write()throws Exception{
//        WritableWorkbook wwb=Workbook.createWorkbook(new File("c:/1.xls"));
//        WritableSheet ws=wwb.createSheet("Test Sheet 1",0);
//        File file=new File("C://jbPRoject//PVS//WebRoot//weekhit//1109496996281.png");
//
//        WritableImage image=new WritableImage(1, 4, 6, 18,file);
//        ws.addImage(image);
//
//        wwb.write();
//        wwb.close();
//    }
}
