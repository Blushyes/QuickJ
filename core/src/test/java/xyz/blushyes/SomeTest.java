package xyz.blushyes;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.Resource;
import xyz.blushyes.http.ChainRequests;
import xyz.blushyes.http.Requests;

@SpringBootTest
public class SomeTest {
    @Resource
    private Requests requests;

    @Test
    public void httpTest() {
        String res = requests.get("https://www.baidu.com", null);
        System.out.println(res);
    }

    @Test
    public void mapperTest() {
        var objectMapper = new ObjectMapper();
        var payloads = Map.of("hello", "world", "ni", "hao", "key", "value");
        String res;
        try {
            res = objectMapper.writeValueAsString(payloads);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        System.out.println(res);
    }

    @Test
    public void requestChainTest() {
        String res = ChainRequests.build().url("https://www.baidu.com").get();
        System.out.println(res);
    }
}
