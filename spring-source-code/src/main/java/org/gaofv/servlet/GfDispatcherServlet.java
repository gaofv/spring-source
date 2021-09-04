package org.gaofv.servlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.*;

/**
 * @Description：
 * @Author：gaofei32419
 * @Date：2021/9/4 20:15
 */
public class GfDispatcherServlet extends HttpServlet {
    /**
     * 读取配置信息
     */
    private Properties contextConfig = new Properties();
    /**
     * 读取指定包下的所有的类名
     */
    List<String> classNames = new ArrayList<>();
    /**
     * ioc容器，key为bean的唯一标识，value为对象
     */
    Map<String, Object> iocMap = new HashMap<>();

    @Override
    public void init() throws ServletException {
        // 1. 加载配置文件
        doLoadConfig();
        // 2. 组件扫描
        doScanner(contextConfig.getProperty("baseScanPackage"));
        // 3. 初始化IOC容器
        doInitIoc();
        // 4. 依赖注入
        doAutoWired();
        // 5. 初始化handlerMapping
        doHandlerMapping();
    }

    /**
     * 加载配置文件
     */
    private void doLoadConfig() {
        String paramValue = getServletConfig().getInitParameter("contextConfigLocation");
        if (paramValue.startsWith("classpath:") || paramValue.startsWith("classpath*:")) {
            paramValue = paramValue.substring(paramValue.indexOf(":") + 1);
        }
        if (paramValue.startsWith("/")) {
            paramValue = paramValue.substring(1);
        }
        InputStream is = GfDispatcherServlet.class.getClassLoader().getResourceAsStream(paramValue);
        try {
            if (is != null) {
                contextConfig.load(is);
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
     * 包扫描
     */
    private void doScanner(String packageName) {
        // 获取指定包下的所有文件
        URL url = GfDispatcherServlet.class.getClassLoader().getResource(packageName.replaceAll("\\.", "/"));
        File file = new File(url.getFile());
        if (file == null) {
            return;
        }
        for (File f : file.listFiles()) {
            if (f.isDirectory()) {
                doScanner(packageName + "." + f.getName());
            } else {
                if (!f.getName().endsWith(".class")) continue;
                classNames.add(packageName + "." + f.getName().replaceAll(".class", ""));
            }
        }
    }

    /**
     * 初始化IOC容器
     */
    private void doInitIoc() {
        if (classNames.isEmpty()) {
            return;
        }
        try {
            for (String className : classNames) {
                Class<?> clazz = Class.forName(className);
                if (clazz.isAnnotationPresent(Controller.class)
                        || clazz.isAnnotationPresent(Service.class)
                        || clazz.isAnnotationPresent(Repository.class)) {
                    Object instance = clazz.newInstance();
                    iocMap.put(toLowerFirst(clazz.getSimpleName()), instance);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 依赖注入
     */
    private void doAutoWired() {
        if (iocMap.isEmpty()) return;
        for (Map.Entry<String, Object> entry : iocMap.entrySet()) {
            Class<?> clazz = entry.getValue().getClass();
            for (Field field : clazz.getDeclaredFields()) {
                // 判断是否有Autowired注解
                if (!field.isAnnotationPresent(Autowired.class)) continue;
                // 注入
                field.setAccessible(true);
                String key = field.getType().getSimpleName();
                try {
                    field.set(entry.getValue(), iocMap.get(toLowerFirst(key)));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    private void doHandlerMapping() {
    }


    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }

    public String toLowerFirst(String value) {
        if (value == null || value.length() == 0) {
            return value;
        }
        char[] charArray = value.toCharArray();
        charArray[0] += 32;
        return String.valueOf(charArray);
    }

}
