package com.app.dictionary.demo;

import com.app.dictionary.handler.manager.AbstractRepertory;
import com.app.dictionary.handler.manager.OracleRepertory;
import com.bobo.dbconnection.ConnectionHolderDefault;
import com.mybatis.pojo.Dbs;

import java.util.List;
import java.util.Map;

public class CherryxOracle {





    public static void main(String[] args) {
        test1();

        AbstractRepertory repertory =  new OracleRepertory();
        repertory.setFilter(new CherryxFilter());

    }
    public static void test1(){
        Dbs dbs = new Dbs("oracle.jdbc.OracleDriver", "jdbc:oracle:thin:@10.14.122.60:1521:portaldb", "CHERRYX", "CHERRYX");

        for(int i =0; i <=89;i++){
            String sql =	"select A.*, B.SORT, B.ORG_ID, B.TYPE as WORK_TYPE, C.ORG_CODE, C.SNAME as ORG_NAME, C.ID_PATH FROM " +
                    "(	select ID ,EMPNO ,ACCT ,PWD ,CNAME ,ENAME ,SEX ,MOBILE ,IDC ,TEL ,EMAIL ,STATUS ,REMARK ,TYPE ,AVATAR_URL ," +
                    "GROUP_IMAGE_URL,ICHAT_NO FROM CUC_USER_"+"000".substring(0,3-String.valueOf(i).length())+i+" WHERE STATUS != '-1' ) A left join " +
                    "(select * from  CUC_ORG_USER_"+"000".substring(0,3-String.valueOf(i).length())+i+" where TYPE=1 )B on B.USER_ID = A.ID " +
                    "left join CUC_ORG_"+"000".substring(0,3-String.valueOf(i).length())+i+" C on C.ID = B.ORG_ID";
            ConnectionHolderDefault holderDefault = new ConnectionHolderDefault();
            List<Map<String, Object>> list = holderDefault.query(dbs, sql);
            for(Map<String, Object> map : list){
                if ("7779224378753548288".equals(map.get("ORG_CODE"))||"7779224378753548288".equals(map.get("ORG_ID"))){
                    System.out.println(map);
                }
            }
        }

    }
}
