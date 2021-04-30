package nia.chapter2.proxy;

/**
 * @author ZMJ
 * @date 2021/4/30
 */

/**
 * JDK动态代理
 * 注意：
 * 使用JDK动态代理 委托对象需要实现接口！！
 */
public class ProxyFactoryTest {

    public static void main(String[] args) {
        IRentingHouse rentingHouse = new RentingHouseImpl();

        // 从代理对象工厂获取代理对象
        IRentingHouse jdkProxy = (IRentingHouse) ProxyFactory.getInstance().getJDKProxy(rentingHouse);
        jdkProxy.rentHosue();

        IRentingHouse cglibProxy = (IRentingHouse) ProxyFactory.getInstance().getCglibProxy(rentingHouse);
        cglibProxy.rentHosue();

    }
}


