package com.xxl.hex.handler;

import com.xxl.hex.handler.annotation.HexHandlerMapping;
import com.xxl.hex.handler.response.HexResponse;
import com.xxl.hex.remote.client.HexClient;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by xuxueli on 16/9/14.
 */
public class HexHandlerFactory implements ApplicationContextAware {

    private static ConcurrentHashMap<String, HexHandler> handlerMap = new ConcurrentHashMap<String, HexHandler>();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

        Map<String, Object> serviceMap = applicationContext.getBeansWithAnnotation(HexHandlerMapping.class);
        if (serviceMap!=null && serviceMap.size()>0) {
            for (Object serviceBean : serviceMap.values()) {
                if (serviceBean instanceof HexHandler) {
                    // valid annotation
                    HexHandlerMapping annotation = serviceBean.getClass().getAnnotation(HexHandlerMapping.class);
                    if (annotation!=null && annotation.value()!=null && annotation.value().trim().length()>0 && annotation.requestClass()!=null) {
                        handlerMap.put(annotation.value(), (HexHandler) serviceBean);
                    }
                }

            }
        }
    }

    public static String dispatchHandler(String mapping, String request_hex){
        // valid param
        if (mapping==null || mapping.trim().length()==0) {
            return HexClient.formatObj2JsonHex(new HexResponse.SimpleHexResponse("必要参数缺失[mapping]"));
        }
        if (request_hex==null || request_hex.trim().length()==0) {
            return HexClient.formatObj2JsonHex(new HexResponse.SimpleHexResponse("必要参数缺失[request_hex]"));
        }

        // handler
        HexHandler handler = handlerMap.get(mapping);
        if (handler == null) {
            return HexClient.formatObj2JsonHex(new HexResponse.SimpleHexResponse("handler不存在"));
        }

        // ex requeset
        Class requestClass = handler.getClass().getAnnotation(HexHandlerMapping.class).requestClass();
        Object requeset = HexClient.parseJsonHex2Obj(request_hex, requestClass);

        // do invoke
        HexResponse hexResponse = handler.handle(requeset);
        String response_hex = HexClient.formatObj2JsonHex(hexResponse);

        return response_hex;
    }

}
