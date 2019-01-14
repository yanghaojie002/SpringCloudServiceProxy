package com.example.demo.controller;

import com.zxj.cloud_service_proxy_core.util.LocalServiceAccessUtil;
import com.example.demo_service_interface.config.RemoteMicroServiceName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import rx.Single;

import javax.annotation.Resource;
import java.io.InputStream;

/**
 * 调用服务层核心控制器，请勿修改此处代码！！！
 */
@Controller
public class ServiceInvokeCoreController {

    private static Logger logger = LoggerFactory.getLogger(ServiceInvokeCoreController.class);


    private static LocalServiceAccessUtil.Logger controllerLogger = new LocalServiceAccessUtil.Logger() {
        @Override
        public void info(String info) {
            logger.info(info);
        }

        @Override
        public void error(String error) {
            logger.error(error);
        }

        @Override
        public void error(String info, Exception e) {
            logger.error(info, e);
        }
    };

    @Resource
    private ApplicationContext applicationContext;

    /**
     * 使用RXJava的类似观察者模式的机制处理异步任务
     *
     * @return
     */
    @RequestMapping("/" + RemoteMicroServiceName.SERVICE_EVEYY_THING)
    public Single<byte[]> responseWithObservable(InputStream inputStream) throws Throwable {
        return LocalServiceAccessUtil.asyncAccess(applicationContext, inputStream, controllerLogger);
    }
}
