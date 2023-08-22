package org.example.api.service.impl;

import com.xiaoju.uemc.tinyid.client.utils.TinyId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class TinyIdTest {
    @Test
    public void test(){
    /*    Long id = TinyId.nextId("test");
*/
// 根据业务类型 批量获取10个ID
        List<Long> ids = TinyId.nextId("test", 10);
        System.out.println(ids);
    }
}
