package spring.demo.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.redis.core.RedisTemplate;

import spring.demo.utils.SerializeUtils;

public class MySessionDao extends AbstractSessionDAO {

	@Autowired
	private RedisTemplate redisTemplate;

	private static int TIME_OUT = 30;

	private static String SESSION_PREFIX = "shiro_redis_session:";

	private static Logger logger = LoggerFactory.getLogger(MySessionDao.class);

	public void delete(Session session) {
		logger.debug("删除seesion,id=[{}]", session.getId().toString());
		if (session == null || session.getId() == null) {
			logger.error("session or session id is null");
			return;
		}
		redisTemplate.delete(SESSION_PREFIX + session.getId().toString());
	}

	public Collection<Session> getActiveSessions() {
		logger.info("获取存活的session");
		return Collections.emptySet();
	}

	public void update(Session session) throws UnknownSessionException {
		logger.debug("更新seesion,id=[{}]", session.getId().toString());
		if (session == null || session.getId() == null) {
			logger.error("session or session id is null");
			return;
		}
		try {
			redisTemplate.opsForValue().set(SESSION_PREFIX + session.getId().toString(),
					SerializeUtils.serialize(session), TIME_OUT, TimeUnit.MINUTES);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

	}

	@Override
	protected Serializable doCreate(Session session) throws UnknownSessionException {
		logger.debug("创建seesion,id=[{}]", session.getId().toString());
		Serializable sessionId = generateSessionId(session);
		assignSessionId(session, sessionId);
		try {
			redisTemplate.opsForValue().set(SESSION_PREFIX + session.getId().toString(),
					SerializeUtils.serialize(session), TIME_OUT, TimeUnit.MINUTES);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return sessionId;
	}

	@Override
	protected Session doReadSession(Serializable sessionId) {
		logger.debug("获取seesion,id=[{}]", sessionId.toString());
		Session readSession = null;
		try {
			readSession = (Session) SerializeUtils
					.deserialize((byte[]) redisTemplate.opsForValue().get(SESSION_PREFIX + sessionId.toString()));
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return readSession;
	}

}
