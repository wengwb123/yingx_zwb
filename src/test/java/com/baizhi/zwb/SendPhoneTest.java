package com.baizhi.zwb;

import com.baizhi.zwb.util.AliyunUtil;
import org.junit.Test;

public class SendPhoneTest {

    @Test
    public void test1() throws Exception{
        AliyunUtil.sendPhone("15156219793");
    }
}
