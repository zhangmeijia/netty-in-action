package nia.chapter2.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author ZMJ
 * @date 2021/4/30
 */
public class ProxyFactory {

    private ProxyFactory(){}
    // 饿汉模式的单例
    private static ProxyFactory proxyFactory = new ProxyFactory();
    public static ProxyFactory getInstance() {
        return proxyFactory;
    }

    /**
     * cglib动态代理
     * 注意：无需实现接口
     * 但是需要引入第三方jar包，该功能是第三方实现的
     <!-- https://mvnrepository.com/artifact/cglib/cglib -->
     <dependency>
         <groupId>cglib</groupId>
         <artifactId>cglib</artifactId>
         <version>3.3.0</version>
     </dependency>
     */
    public Object getCglibProxy(IRentingHouse rentingHouse) {
        return Enhancer.create(rentingHouse.getClass(), new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                Object result = null;
                System.out.println("中介（代理）收取服务费3000元");
                result = method.invoke(rentingHouse, objects); // 调用原有业务逻辑
                System.out.println("客户信息卖了3毛钱......");
                return result;
            }
        });
    }


    /**
     * JDK动态代理
     * 注意：
     * 使用JDK动态代理 委托对象需要实现接口！！
     */
    public Object getJDKProxy(IRentingHouse rentingHouse) {
        return Proxy.newProxyInstance(rentingHouse.getClass().getClassLoader(),
                rentingHouse.getClass().getInterfaces(),
                new InvocationHandler() {
                    /**
                     * @param proxy 代理对象
                     * @param method  调用的方法
                     * @param args 调用方法的参数
                     * @return
                     * @throws Throwable
                     */
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        Object result = null;
                        // 写增强逻辑
                        System.out.println("中介（代理）收取服务费3000元");
                        result = method.invoke(rentingHouse, args); // 调用原有业务逻辑
                        System.out.println("客户信息卖了3毛钱......");
                        return result;
                    }
                });
    }

}
