package slive.jedis.client.session;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import slive.jedis.client.util.JedisUtils;

import java.util.Date;

/**
 * 描述：<br>
 *
 * @author slive
 * @date 2020/1/8
 */
public class BaseSessionCacheTest {

    /** logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseSessionCacheTest.class);

    private SessionCache<TestSession> baseSessionCache;

    private Jedis jedis;

    @BeforeClass
    public void setUp() throws Exception {
        try {
            jedis = new Jedis("192.168.235.201", 6379);
            jedis.connect();
            JedisUtils.init(jedis);

            baseSessionCache = new BaseSessionCache<TestSession>("test", 2, TestSession.class);
        }
        catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("error:", e);
        }
    }

    @Test
    public void testCache() {
        TestSession ts = new TestSession();
        ts.setKey("124");
        ts.setNumber(1000);
        ts.setValue("v23424324");
        ts.setDate(new Date());
        baseSessionCache.put(ts.getKey(), ts);
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        String ret = baseSessionCache.get(ts.getKey());
        LOGGER.info("ret:{}", ret);

    }

    @org.junit.After
    public void tearDown() throws Exception {
    }
}