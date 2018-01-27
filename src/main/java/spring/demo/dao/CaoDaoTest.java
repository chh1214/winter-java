package spring.demo.dao;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import spring.demo.entity.Car;
import spring.demo.test.BaseTest;

public class CaoDaoTest extends BaseTest {

	@Autowired
	private RedisTemplate redisTemplate;
	
	@Autowired
	private CarDao carDao;

	@SuppressWarnings("unchecked")
	@Test
	public void tessssst() throws Exception {
		redisTemplate.opsForValue().set("test", "chh123");
		String str = (String) redisTemplate.opsForValue().get("test");
		System.out.println(str);
	}

}
