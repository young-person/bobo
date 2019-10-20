package com.bobo.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseClass {
    protected static final Logger LOGGER = LoggerFactory.getLogger(BaseClass.class);

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
    
    
    /**
     * 删除以/开头的字符串
     *  @param url
     *  @return
     *  @Description: trimSplit
     *  @date 2019年6月29日 下午11:27:44
     */
    protected String trimSplit(String url) {
		
    	if (url != null && url.startsWith("/")) {
			return url.substring(1);
		}
    	
    	return url;
	}
}
