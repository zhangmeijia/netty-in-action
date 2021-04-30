package nia.chapter2.proxy;

/**
 * @author ZMJ
 * @date 2021/4/30
 */

/**
 * 代理类
 */
public class RentingHouseProxy implements IRentingHouse {

    private IRentingHouse rentingHouse;
    public RentingHouseProxy(IRentingHouse rentingHouse) {
        this.rentingHouse = rentingHouse;
    }

    // 代理增强方法
    @Override
    public void rentHosue() {
        System.out.println("中介（代理）收取服务费3000元");
        rentingHouse.rentHosue();
        System.out.println("客户信息卖了3毛钱......");
    }
}


