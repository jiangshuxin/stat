package com.handpay.arch;

import com.handpay.rache.core.spring.StringRedisTemplateX;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

/**
 * Created by fczheng on 2016/11/14.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTester {

    @Autowired
    private StringRedisTemplateX stringRedisTemplateX;

    @Test
    public void testRedisZSet() {
        Set<String> s1 = stringRedisTemplateX.boundZSetOps("dubboRT|GroupKeySet").range(0, -1);
        Set<String> s2 = stringRedisTemplateX.boundZSetOps("dubboQPS|GroupKeySet").range(0, -1);
    }
}
