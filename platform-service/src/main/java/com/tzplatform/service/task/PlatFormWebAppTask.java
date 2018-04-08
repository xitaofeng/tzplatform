package com.tzplatform.service.task;

import com.tzplatform.dao.webapp.PlatFormAppAccessCountDao;
import com.tzplatform.entity.common.CommonEnum;
import com.tzplatform.service.common.RedisClusterHelper;
import com.tzplatform.service.webapp.PlatFormAppAccessCountService;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Set;

@Component
@Lazy(value = false)
public class PlatFormWebAppTask {

    @Resource
    private RedisClusterHelper redisClusterHelper;

    @Resource
    private PlatFormAppAccessCountDao platFormAppAccessCountDao;

    private Logger logger = Logger.getLogger(PlatFormWebAppTask.class);

    @Scheduled(cron = "0 */60 * * * ?")//1小时处理一次
    public void updateUseCount() {
        logger.info("update usecount task begin");
        Set<String> appidSet = redisClusterHelper.getSet(CommonEnum.APP_USECOUNT.应用名称.getValue());
        if (appidSet != null) {
            for (String appid : appidSet) {
                Long usecount = Long.valueOf(redisClusterHelper.getCount(appid));
                //如果点击数大于零才更新
                if (usecount > 0) {
                    //更新使用人数表
                    Integer count = platFormAppAccessCountDao.updateUseCount(appid, usecount);
                    //更新完成后将计数器置零
                    if (1 == count) {
                        redisClusterHelper.setZero(appid);
                    }
                }
            }
            logger.info("update usecount task end");
        }
    }
}
