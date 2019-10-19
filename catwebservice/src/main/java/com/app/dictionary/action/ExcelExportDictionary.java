package com.app.dictionary.action;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFHyperlink;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;

import com.app.dictionary.handler.manager.AbstractRepertory;
import com.app.dictionary.handler.manager.ExportDictionary;
import com.app.dictionary.templet.model.Form;
import com.app.dictionary.templet.model.StoreRoom;
import com.app.dictionary.templet.model.Table;
import com.bobo.dbconnection.DBType;

public class ExcelExportDictionary extends ExportDictionary {
    @Override
    public void doExport(AbstractRepertory<DBType> repertory, DBType dbs, OutputStream outputStream) throws IOException {

        StoreRoom storeRoom = repertory.createDBInfOBean(dbs, "基础");

        String[] names = {"表   名","说   明","操作"};
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("测试Excel");
        workbook.setSheetName(0, "目录");
        int index = 0, sheetNum = 1;
        Row row1 = sheet.createRow(index++);
        sheet.addMergedRegion(new CellRangeAddress(0,0,0,2));

        sheet.setColumnWidth(0, 40*256);
        sheet.setColumnWidth(1, 40*256);
        sheet.setColumnWidth(2, 40*256);

        short height = 800;
        row1.setHeight(height);

        HSSFRichTextString text = new HSSFRichTextString("手机管理平台数据库设计V5.4");
        HSSFCellStyle style =  getStyle(workbook);
        getStyleBorder(workbook);
        HSSFCellStyle mainstyle =  getSimpleStyle(workbook);
        Cell cell = row1.createCell(0);
        cell.setCellStyle(style);
        cell.setCellValue(text.toString());;

        Row row2 = sheet.createRow(index);

        createRowHeader( workbook,row2, names);

        for(Table table : storeRoom.getTables()){
            index ++;
            Row row = sheet.createRow(index);
            Cell cell1 = row.createCell(0);
            Cell cell2 = row.createCell(1);
            Cell cell3 = row.createCell(2);

            cell1.setCellValue(table.getTableName());

            String cname = getSimpleName(table.getTableName());

            Hyperlink hyperlink = new HSSFHyperlink(Hyperlink.LINK_DOCUMENT);
            hyperlink.setAddress("#"+cname+"!A1");
            cell1.setHyperlink(hyperlink);
            if (cname.indexOf("（未被使用）") > -1){
                cname.indexOf("（未被使用）");
                cname = cname.substring(0,cname.indexOf("（未被使用）"));
                cell3.setCellValue("（未被使用）");
            }else{
                cell3.setCellValue("");
            }
            cell2.setCellValue(cname);

            HSSFCellStyle linkStyle = getLinkStyle(workbook);
            cell1.setCellStyle(linkStyle);
            cell2.setCellStyle(mainstyle);
            cell3.setCellStyle(mainstyle);
        }


        for(Table table : storeRoom.getTables()){
            String clazzName = getClassName(table.getTableName());
            String[] headers = {"表名", table.getTableName(),"实体类名",clazzName,"实体中文名","",""};
            String simpleName = getSimpleName(table.getTableName());

            if (simpleName.indexOf("（未被使用）") > -1){
                simpleName.indexOf("（未被使用）");
                simpleName = simpleName.substring(0,simpleName.indexOf("（未被使用）"));
            }
            createMoreSheet( workbook, sheetNum,simpleName, headers,table.getForms(),outputStream);
            sheetNum ++;
        }
        workbook.write(outputStream);
    }



    private void createRowHeader(HSSFWorkbook workbook,Row row,String[] names){
        for(int len = 0; len < names.length; len++){
            HSSFRichTextString text = new HSSFRichTextString(names[len]);
            HSSFCellStyle style =  getStyle(workbook);
            Cell cell = row.createCell(len);
            cell.setCellStyle(style);
            cell.setCellValue(text.toString());;
        }
    }

    private String getSimpleName(String tableName){
        Map<String,String> chinese = getTableNames();
        return chinese.get(tableName);
    }


    private void createMoreSheet(HSSFWorkbook workbook, int sheetNum, String sheetTitle, String[] headers, List<Form> rows, OutputStream out){

        HSSFSheet sheet = workbook.createSheet();
        sheet.addMergedRegion(new CellRangeAddress(0,0,0,6));

        workbook.setSheetName(sheetNum, sheetTitle);
        HSSFCellStyle style =  getStyle(workbook);
        HSSFCellStyle mainstyle =  getSimpleStyle(workbook);

        HSSFRow row0 = sheet.createRow(0);

        Hyperlink hyperlink = new HSSFHyperlink(Hyperlink.LINK_DOCUMENT);
        hyperlink.setAddress("#目录!A"+(sheetNum+2));
        Cell backcell = row0.createCell(0);
        backcell.setHyperlink(hyperlink);
        HSSFRichTextString backtext = new HSSFRichTextString("返回目录");
        backcell.setCellValue(backtext.toString());


        HSSFRow row1 = sheet.createRow(1);
        for (int i = 0; i < headers.length; i++) {
            sheet.setColumnWidth(i, 30*256);
            HSSFCell cell =  row1.createCell(i);
            cell.setCellStyle(style);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text.toString());
        }
        HSSFRow row2 = sheet.createRow(2);
        String[] columns = {"物理字段","显示名称","字段类型","字段长度","是否主键","是否必填项","字段说明"};
        createRowHeader( workbook,row2,columns);

        int k = 3;
        for (Form form : rows) {
            //创建数据行
            HSSFRow row = sheet.createRow(k);
            //设置对应单元格的值
            HSSFCell cell1 = row.createCell(0);
            HSSFCell cell2 = row.createCell(1);
            HSSFCell cell3 = row.createCell(2);
            HSSFCell cell4 = row.createCell(3);
            HSSFCell cell5 = row.createCell(4);
            HSSFCell cell6 = row.createCell(5);
            HSSFCell cell7 = row.createCell(6);

            cell1.setCellStyle(mainstyle);
            cell2.setCellStyle(mainstyle);
            cell3.setCellStyle(mainstyle);
            cell4.setCellStyle(mainstyle);
            cell5.setCellStyle(mainstyle);
            cell6.setCellStyle(mainstyle);
            cell7.setCellStyle(mainstyle);

            String columnDetail = form.getColumnDetail();
            HSSFRichTextString text1 = new HSSFRichTextString(form.getColumnName());
            HSSFRichTextString text2 = null;
            HSSFRichTextString text3 = new HSSFRichTextString(form.getColumnType());
            HSSFRichTextString text4 = new HSSFRichTextString(form.getLength());
            HSSFRichTextString text5 = new HSSFRichTextString(form.getColumnRemark());
            HSSFRichTextString text6 = new HSSFRichTextString(form.getNon());
            HSSFRichTextString text7 = null;

            if (columnDetail.indexOf("&") > -1){
                text2 = new HSSFRichTextString(columnDetail.split("&")[0]);
                text7 = new HSSFRichTextString(columnDetail.split("&")[1]);
            }else{
                text2 = new HSSFRichTextString(form.getColumnDetail());
                text7 = new HSSFRichTextString("");
            }

            cell1.setCellValue(text1.toString());
            cell2.setCellValue(text2.toString());
            cell3.setCellValue(text3.toString());
            cell4.setCellValue(text4.toString());
            cell5.setCellValue(text5.toString());
            cell6.setCellValue(text6.toString());
            cell7.setCellValue(text7.toString());

            k ++;
        }

    }
    private HSSFCellStyle getStyle(HSSFWorkbook workbook){
        HSSFCellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index );

        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        HSSFFont font = workbook.createFont();
        font.setColor(HSSFColor.BLACK.index);
        font.setFontHeightInPoints((short) 12);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        style.setFont(font);
        style.setWrapText(true);
        return style;
    }


    private HSSFCellStyle getSimpleStyle(HSSFWorkbook workbook){
        HSSFCellStyle style = workbook.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
        HSSFFont font = workbook.createFont();
        font.setFontHeightInPoints((short) 12);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        style.setFont(font);
        return style;
    }

    private HSSFCellStyle getStyleBorder(HSSFWorkbook workbook){
        HSSFCellStyle style = workbook.createCellStyle();
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
        HSSFFont font = workbook.createFont();
        font.setFontHeightInPoints((short) 14);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        style.setFont(font);
        return style;
    }

    private HSSFCellStyle getLinkStyle(HSSFWorkbook workbook){
        HSSFCellStyle style = workbook.createCellStyle();
        HSSFFont cellFont= workbook.createFont();
        cellFont.setUnderline((byte) 1);
        cellFont.setColor(HSSFColor.BLUE.index);
        style.setFont(cellFont);
        return style;
    }


    private Map<String,String> getTableNames(){

        Map<String,String> names = new HashMap<>();
        names.put("CAMC_APP","App应用表");
        names.put("CAMC_APPINFO_CERT_REL","应用证书关系表");
        names.put("CAMC_APPINFO_SUBAPP_REL","版本与小程序关联表");
        names.put("CAMC_APP_ACCESS_INFO","权限设置(黑白灰设置)表");
        names.put("CAMC_APP_AUTHINFO_REL","App与认证信息关联表");
        names.put("CAMC_APP_AUTH_RECORD","App认证记录表");
        names.put("CAMC_APP_BUSINESS_REL","App与业务系统关联表");
        names.put("CAMC_APP_CERT","证书表");
        names.put("CAMC_APP_CERT_REL","App与证书关联表");
        names.put("CAMC_APP_CESHI","应用测试（未被使用）");
        names.put("CAMC_APP_CLIENTINFO_REL","App与设备关联表");
        names.put("CAMC_APP_DATASOURCE_REL","App与数据源关联表");
        names.put("CAMC_APP_H5_RESOURCE_REL","App与H5资源关联表");
        names.put("CAMC_APP_INFO","App版本表");
        names.put("CAMC_APP_INFO_MAINFEST","App版本发布清单表");
        names.put("CAMC_APP_INFO_PUBLISH","App版本发布信息表");
        names.put("CAMC_APP_MSG_PUSH_ACCOUNT","App消息推送帐号关联表");
        names.put("CAMC_APP_PATCH","热修复表");
        names.put("CAMC_APP_PATCH_HISTORY","热修复历史表");
        names.put("CAMC_APP_PUSH_REL","App与推送设备关联表");
        names.put("CAMC_APP_RESOURCE_REL","APP应用资源关系表");
        names.put("CAMC_APP_ROLE_GROUP_REL","应用和角色组关系表");
        names.put("CAMC_APP_SPG_REL","APP应用和组关系表");
        names.put("CAMC_APP_URL_MANAGER","开关配置表");
        names.put("CAMC_APP_USER_REL","APP应用和用户关系表");
        names.put("CAMC_AUTH_INFO","认证信息表");
        names.put("CAMC_AUTH_MODE","认证模式表");
        names.put("CAMC_BAS_DICT","字典表");
        names.put("CAMC_BAS_DICT_ITEM","字典项表");
        names.put("CAMC_CLIENT_INFO","设备信息表");
        names.put("CAMC_CONFIG_INFO","系统配置表");
        names.put("CAMC_GATEWAY","网关配置表");
        names.put("CAMC_H5_RESOURCE","H5资源表");
        names.put("CAMC_INF_OPERATE_RECORD","接口操作记录表");
        names.put("CAMC_ON_OFF","开关表");
        names.put("CAMC_OPERATE_RECORD","操作记录表");
        names.put("CAMC_PUBLISH_STRATEGY","发布策略表");
        names.put("CAMC_PUBLISH_STRATEGY_REL","App版本与发布策略关联表");
        names.put("CAMC_RESOURCE","资源表");
        names.put("CAMC_SCROLL_PIC","滚动图片配置表");
        names.put("CAMC_SCROLL_PIC_GROUP","滚动图片组表");
        names.put("CAMC_SPG_APPINFO_REL","滚动图片和APP版本关联表");
        names.put("CAMC_SPG_AUTH_REL","滚动图片组访问权限关联表");
        names.put("CAMC_USER_SUB_APP","用户已选小程序表");
        names.put("CBDC_APP_BAFFLE","挡板表");
        names.put("CBDC_APP_BAFFLE_CONFIG","挡板规则表");
        names.put("CBDC_APP_BAFFLE_LOG","挡板日志表");
        names.put("CBDC_APP_SERVICE","接口表");
        names.put("CBDC_APP_SERVICE_MAPPING","接口参数映射表");
        names.put("CBDC_BAFFLE_GRAY_USER","挡板灰度用户表");
        names.put("CBDC_BUSINESS_INF_REL","业务系统与接口关联表");
        names.put("CBDC_BUSINESS_SYSTEM","业务系统表");
        names.put("CBDC_GRAY_USER","组织用户关系表（未被使用）");
        names.put("CDC_FILE_RECORD","文件记录表（未被使用）");
        names.put("CDMC_APP_DEVICE_BOUND_CONFIG","绑定配置表");
        names.put("CDMC_APP_USER_DEVICE_BOUND","设备绑定表");
        names.put("CDMC_USER_BOUND_CONFIG","用户绑定配置表");
        names.put("CDMC_USER_DEVICE_APPLY","用户设备绑定申请表");
        names.put("CNC_MSG_PUSH_ACCOUNT","消息推送帐号表");
        names.put("CNC_NOTIFY_MESSAGE","消息推送表");
        names.put("COC_APP_INFO_POINT","APP版本与埋点关联表");
        names.put("COC_APP_POINT","平台和埋点的关联表");
        names.put("COC_LOG_POINT_CONFIG","埋点配置表");
        names.put("COC_LOG_POINT_INFO","埋点信息表");
        names.put("COC_POINT_TITLE","埋点标签表");
        names.put("CUC_DATASOURCE","数据源表");
        names.put("CUC_DS_RG_REL","数据源和权限组(角色组)的关系表");
        names.put("CUC_ORG_000","动态机构表");
        names.put("CUC_ORG_AUTH_000","动态用户分级授权机构表");
        names.put("CUC_ORG_USER_000","动态机构用户关联表");
        names.put("CUC_RESOURCE_000","动态资源表");
        names.put("CUC_RESOURCE_DEFAULT","角色默认关联资源表");
        names.put("CUC_ROLE_000","动态角色表");
        names.put("CUC_ROLE_GROUP_000","动态角色组表");
        names.put("CUC_ROLE_ORG_000","动态角色机构关联表");
        names.put("CUC_ROLE_RESOURCE_000","动态角色(或用户)资源关联表");
        names.put("CUC_ROLE_USER_000","动态角色用户关联表");
        names.put("CUC_USER_000","动态用户表");
        names.put("CAMC_WHITE_STRATEGY","白名单策略表");
        names.put("CCSC_OPTIONAL_CONFIG","可选配置表");
        names.put("CNC_PUSH_SERVICE_CONFIG","推送服务配置表");
        names.put("CNC_MSG_PUSH_INFO_OBJS","推送对象表");
        names.put("CNC_MSG_PUSH_INFO","消息推送信息表");
        names.put("CNC_MSG_PUSH_INFO_STATUS","推送状态表");
        names.put("CCSC_PROPERTIES","配置中心配置文件表");
        names.put("CNC_MSG_PUSH_TEMPLATE","消息推送模版表");
        names.put("schema_version","Flyway程序版本信息表");
        return names;
    }



}
