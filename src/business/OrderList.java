package business;

import java.util.ArrayList;
import java.util.List;

public class OrderList {
    private List<Order> orders;

    public OrderList(List<Order> orders) {
        this.orders = orders;
    }

    public OrderList() {
        orders = new ArrayList<>();
    }

    public List<Order> viewOrders(){
        return orders;
    }

    public boolean addOrder(Order order){
        return orders.add(order);
    }

    public boolean cancelOrder(Order order){
        return orders.remove(order);
    }
}
