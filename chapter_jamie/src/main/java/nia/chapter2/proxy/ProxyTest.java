package nia.chapter2.proxy;

/**
 * @author ZMJ
 * @date 2021/4/30
 */
public class ProxyTest {

    public static void main(String[] args) {
        IRentingHouse rentingHouse = new RentingHouseImpl();
        // 自己要租用一个一室一厅的房子
//        rentingHouse.rentHosue();

        // 中介（代理）帮我租了一个一室一厅的房子
        RentingHouseProxy proxy = new RentingHouseProxy(rentingHouse);
        proxy.rentHosue();
    }
}
