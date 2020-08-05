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

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestApplication {

    Logger logger= LoggerFactory.getLogger(this.getClass());

    private String processKey="myProcess01";

    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;


    /**
     * 启动一个流程实例
     */
    @Test
    public void startProcessInstants() {

        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processKey);
        logger.info("流程实例id:" + processInstance.getId());
        logger.info("流程实例id:" + processInstance.getProcessInstanceId());
        logger.info("流程实例id:" + processInstance.getProcessDefinitionId());
        logger.info("当前活动标识:" + processInstance.getActivityId());
    }

    /**
     * 查询流程实例
     */
    @Test
    public void queryProcessInstants() {
        List<ProcessInstance> list = runtimeService.createProcessInstanceQuery().processDefinitionKey(processKey).list();
        for (ProcessInstance processInstance : list) {
            logger.info("流程实例id:"+processInstance.getId());
            logger.info("流程实例id:"+processInstance.getProcessInstanceId());
            logger.info("流程实例id:"+processInstance.getProcessDefinitionId());
            logger.info("当前活动标识:"+processInstance.getActivityId());
        }
    }

    /**
     * 查询任务列表
     */
    @Test
    public void queryTask() {
//        String assignee="zhangsan";
//        String assignee="lisi";
        String assignee="wangwu";
        List<Task> taskList = taskService.createTaskQuery().processDefinitionKey(processKey).taskAssignee(assignee).list();
        for (Task task : taskList) {
            logger.info("获取"+assignee+"的任务列表:"+task.toString());
        }
    }

    @Test
    public void completeTask() {
        taskService.complete("10002");
        logger.info("完成任务！");
    }


}
