package cn.yer;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * 出差申报流程测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestCcsb {
    Logger logger= LoggerFactory.getLogger(this.getClass());

    @Autowired
    RuntimeService runtimeService;
    @Autowired
    TaskService taskService;

    private String processKey="hmccsb";

    private String mjNow="民警1";
    private String dwfzrNow="单位负责人1";
    private String fgjldNow="分管局领导2";
    private String fgcwjldNow="分管财务局领导1";
    private String cwkNow="财务科2";
    private String jwbzsNow="警务保障室2";

    private String dwfzrGroup="单位负责人1,单位负责人2";
    private String fgjldGroup="分管局领导1,分管局领导2";
    private String fgcwjldGroup="分管财务局领导1,分管财务局领导2";
    private String cwkGroup="财务科1,财务科2";
    private String jwbzsGroup="警务保障室1,警务保障室2";


    /**
     * 第一步：启动出差申报流程
     */
    @Test
    public void startProcess(){
        //启动流程，第一个变量是processKey  第二个是一个map集合，可存放流程全局变量
        Map<String,Object> map=new HashMap<>();
        map.put("jtgj",3);
        map.put("mj",mjNow);
        map.put("dwfzr",dwfzrNow);
        map.put("fgjld",fgjldNow);
        map.put("fgcwjld",fgcwjldNow);
        map.put("cwk",cwkNow);
        map.put("jwbzs",jwbzsNow);
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processKey, map);
        logger.info("流程开始："+processInstance.toString());

    }

    /**
     * 只有一个完成者的情况
     * 民警要完成的工作
     * 第二步：当前民警填写出差申报单
     */
    @Test
    public void mjDo(){
        //先查询自己的任务列表
        Task task= taskService.createTaskQuery().processDefinitionKey(processKey).taskAssignee(mjNow).singleResult();
        if(task!=null){
            logger.info(mjNow+"待完成的任务是："+task.toString());
            //存在任务就把任务完成
            taskService.complete(task.getId());;
            logger.info(mjNow+"完成任务的是："+task.toString());
        }
    }

    /**
     * 多个候选人的情况
     * 单位负责人等要完成的工作
     * 第三步：单位负责人拾取任务后，从任务的候选人变成任务的执行人，并执行任务
     */
    @Test
    public void shspDo(){
//        String assignee=dwfzrNow;//单位负责人
//        String assignee=fgjldNow;//分管局领导
        String assignee=fgcwjldNow;//分管财务领导
//        String assignee=cwkNow;//财务科
//        String assignee=jwbzsNow;//警务保障室
        //先查询自己的任务列表
        Task task= taskService.createTaskQuery().processDefinitionKey(processKey).taskCandidateUser(assignee).singleResult();
        if(task!=null){
            //如果自己是候选人，则先拾取任务变成执行人，然后执行任务
            //拾取任务  第一个参数是
            taskService.claim(task.getId(),assignee);
            logger.info(assignee+"拾取的任务是："+task.toString());
            //存在任务就把任务完成
            taskService.complete(task.getId());
            logger.info(assignee+"完成任务的是："+task.toString());
        }
    }


}
