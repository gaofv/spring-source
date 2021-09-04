package org.gaofv;

import org.gaofv.servlet.GfDispatcherServlet;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description：
 * @Author：gaofei32419
 * @Date：2021/9/4 21:09
 */
public class Test {
    private static List<String> classNames = new ArrayList<>();

    public static void main(String[] args) {
        doScanner("org.gaofv");
    }

    public static void doScanner(String packageName) {


        Class<?> aClass = null;
        try {
            aClass = Class.forName("org/gaofv/controller/UserController");
            System.out.println(aClass.newInstance());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        // 获取指定包下的所有文件
//        URL url = Test.class.getClassLoader().getResource(packageName.replaceAll("\\.", "/"));
//        File file = new File(url.getFile());
//        for (File f : file.listFiles()) {
//            if (f.isDirectory()) {
//                doScanner(packageName + "/" + f.getName());
//            } else {
//                String className = f.getName().replaceAll(".class", "");
//                classNames.add(className);
//            }
//        }

    }

}
