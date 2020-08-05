package cn.yer;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * 指定任务执行人
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class test02 {
    Logger logger= LoggerFactory.getLogger(this.getClass());

    private String processKey="myProcess02";

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


}
