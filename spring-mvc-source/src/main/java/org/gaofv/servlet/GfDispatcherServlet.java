package org.gaofv.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.*;

/**
 * @Description：
 * @Author：gaofei32419
 * @Date：2021/9/4 15:33
 */
public class GfDispatcherServlet extends HttpServlet {

    /**
     * properties代替springmvc.xml文件
     */
    private static Properties properties = new Properties();

    private static final String CONTEXT_CONFIG_LOCATION = "contextConfigLocation";

    /**
     * 定义IOC容器，用于存储bean
     */
    private static Map<Object, Object> beanMap = new HashMap<>(16);

    @Override
    public void init() throws ServletException {
        // 1. 读取配置文件
        doLoadConfig();
        // 2. 初始化IOC容器
        doCreateInstance();
        // 3. 依赖注入
        doInjection();
    }


    /**
     * 读取配置文件
     */
    private void doLoadConfig() {
        String value = getServletConfig().getInitParameter(CONTEXT_CONFIG_LOCATION);
        String path = "";
        if (value.startsWith("classpath")) {
            String[] split = value.split(":");
            if (split.length >= 2) {
                path = split[1];
            }
        }
        InputStream is = null;
        try {
            is = GfDispatcherServlet.class.getResourceAsStream("/" + path);
            if (is != null) {
                properties.load(is);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 初始化IOC容器，创建bean实例
     */
    private void doCreateInstance() {
        Set<Object> beanNames = properties.keySet();
        for (Object beanName : beanNames) {
            String value = properties.getProperty((String) beanName);
            beanMap.put(beanName, getObject(value));
        }
    }

    private Object getObject(String clazz) {
        if (clazz == null || clazz.length() == 0) {
            return null;
        }
        Object obj = null;
        try {
            Class<?> aClass = Class.forName(clazz);
            obj = aClass.newInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return obj;
    }


    private void doInjection() {
        Set<Object> keys = beanMap.keySet();
        Object instance;
        Class<?> aClass;
        Field[] fields;
        for (Object key : keys) {
            instance = beanMap.get(key);
            if (instance == null) {
                continue;
            }
            fields = instance.getClass().getDeclaredFields();
            for (Field field : fields) {
                try {
                    field.setAccessible(true);
                    Object value = field.get(instance);
                    if (value == null) {
                        Object o = beanMap.get(field.getName());
                        if (o == null) {
                            throw new RuntimeException("autowired failed");
                        }
                        field.set(instance, o);
                    }

                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }


    }
}
