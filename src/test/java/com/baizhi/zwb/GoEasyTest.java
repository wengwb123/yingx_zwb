package com.baizhi.zwb;

import io.goeasy.GoEasy;
import org.junit.Test;

public class GoEasyTest {

    @Test
    public void test1(){
        for(int i=0;i<10;i++) {
            GoEasy goEasy = new GoEasy("http://rest-hangzhou.goeasy.io", "BC-081bbf854e0e4dc4bcbf8ad1fa773068");
            goEasy.publish("我的channel", "Hello, GoEasy!"+i);
        }
    }
}
