package com.jnu.student.data;

import static org.junit.Assert.*;
import android.content.Context;
import androidx.test.platform.app.InstrumentationRegistry;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

public class TaskBankTest {

    List<TaskItem> origin_data;
    Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
    @Before
    public void setUp() throws Exception {
        origin_data = TaskBank.loadTaskItems(targetContext, 0);
    }

    @After
    public void tearDown() throws Exception {
        TaskBank.saveTaskItems(targetContext, origin_data, 0);
    }
    @Test
    public void saveAndLoadTaskItems(){
        List<TaskItem> test_data = new ArrayList<>();
        test_data.add(new TaskItem("完成作业", 2, 10));
        test_data.add(new TaskItem("晨读", 3, 15));
        test_data.add(new TaskItem("背单词", 5, 5));
        TaskBank.saveTaskItems(targetContext, test_data, 0);

        List<TaskItem> loaded_data = TaskBank.loadTaskItems(targetContext, 0);
        assertNotSame(test_data, loaded_data);
        assertEquals(test_data.size(), loaded_data.size());
        for(int index = 0; index < test_data.size(); ++index){
            assertNotSame(test_data.get(index), loaded_data.get(index));
            assertEquals(test_data.get(index).getTaskName(), loaded_data.get(index).getTaskName());
            assertEquals(test_data.get(index).getTaskReward(), loaded_data.get(index).getTaskReward());
            assertEquals(test_data.get(index).getTaskNeedTimes(), loaded_data.get(index).getTaskNeedTimes());
            assertEquals(test_data.get(index).getTaskCntTimes(), loaded_data.get(index).getTaskCntTimes());
            assertEquals(test_data.get(index).getTaskType(), loaded_data.get(index).getTaskType());
        }
    }
}