package nia.chapter2.proxy;

/**
 * @author ZMJ
 * @date 2021/4/30
 */

/**
 * 委托类
 */
public class RentingHouseImpl implements IRentingHouse {

    @Override
    public void rentHosue() {
        System.out.println("我要租用一室一厅的房子");
    }
}
