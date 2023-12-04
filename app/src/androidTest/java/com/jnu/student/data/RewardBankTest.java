package com.jnu.student.data;

import static org.junit.Assert.*;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class RewardBankTest {
    List<RewardItem> origin_data;
    Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
    @Before
    public void setUp() throws Exception {
        origin_data = RewardBank.loadRewardItems(targetContext);
    }

    @After
    public void tearDown() throws Exception {
        RewardBank.saveRewardItems(targetContext, origin_data);
    }

    @Test
    public void saveAndLoadRewardItems(){
        List<RewardItem> test_data = new ArrayList<>();
        test_data.add(new RewardItem("看小说", 20));
        test_data.add(new RewardItem("逛手机", 25));
        test_data.add(new RewardItem("看电影", 30));
        RewardBank.saveRewardItems(targetContext, test_data);

        List<RewardItem> loaded_data = RewardBank.loadRewardItems(targetContext);
        assertNotSame(test_data, loaded_data);
        assertEquals(test_data.size(), loaded_data.size());
        for(int index = 0; index < test_data.size(); ++index){
            assertNotSame(test_data.get(index), loaded_data.get(index));
            assertEquals(test_data.get(index).getRewardName(), loaded_data.get(index).getRewardName());
            assertEquals(test_data.get(index).getRewardCost(), loaded_data.get(index).getRewardCost());
        }
    }
}