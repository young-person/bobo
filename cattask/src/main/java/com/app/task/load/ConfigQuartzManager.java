package com.app.task.load;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import com.app.pojo.ScheduleJobStatus;
import com.app.pojo.TaskObject;
import org.apache.commons.io.IOUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.quartz.CronScheduleBuilder;

public class ConfigQuartzManager extends QuartzManagerAbstract{
    private static String configLocation="/QuartzManager.xml";
    @Override
    public void init() {
        SAXReader reader = new SAXReader();
        reader.setEncoding("UTF-8");
        //获得文档对象模型
        InputStream inputStream = null;
        try {
            inputStream  = QuartzManagerAbstract.class.getResourceAsStream(configLocation);
            Document doc = reader.read(new InputStreamReader(inputStream,"UTF-8"));
            //获得根元素
            Element root = doc.getRootElement();
            List<Element> elements = root.elements("group");
            if(elements!=null){
                for (Element nodes: elements) {
                    String name =nodes.attributeValue("name");
                    List<Element> tasks =  nodes.elements("task_object");
                    if(tasks!=null){
                        for (Element task: tasks) {
                            boolean task_disable =  Boolean.parseBoolean(task.attributeValue("disable","false"));
                            String task_id = task.attributeValue("id");//任务id
                            String task_name = task.attributeValue("name");//任务名
                            int task_status =Integer.parseInt(task.attributeValue("status"));//运行状态
                            String task_expression = task.attributeValue("expression");//表达式
                            CronScheduleBuilder.cronSchedule(task_expression);
                            Element node =  task.element("json_params");//参数
                            Element explain = task.element("detailed_description");//详情
                            TaskObject taskObject= new TaskObject();
                            taskObject.setDisable(task_disable);
                            taskObject.setGroupId(name);
                            taskObject.setId(task_id);
                            taskObject.setName(task_name);
                            ScheduleJobStatus status = ScheduleJobStatus.getEnumByKey(task_status);
                            taskObject.setStatus(status);
                            taskObject.setExpression(task_expression);
                            taskObject.setClassPath(task.attributeValue("class"));
                            if(node!=null){
                                taskObject.setParams(node.getTextTrim());
                            }
                            if(explain!=null){
                                taskObject.setInstruction(explain.getTextTrim());
                            }
                            TASK_GROUP_MAP.put(task_id, taskObject);
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error("初始化配置失败",e);
        }finally {
            IOUtils.closeQuietly(inputStream);
        }

        if(logger.isDebugEnabled()){
            logger.debug("初始化调度配置文件信息完毕.{}",TASK_GROUP_MAP);
        }
    }
}
