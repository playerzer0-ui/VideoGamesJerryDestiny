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

    /**
     * view orders in the list
     * @return list of orders
     */
    public List<Order> viewOrders(){
        return orders;
    }

    /**
     * add an order to the list
     * @param order the Order class
     * @return true/false
     */
    public boolean addOrder(Order order){
        return orders.add(order);
    }


    /**
     * remove an order from the list
     * @param order the Order class
     * @return true/false
     */
    public boolean cancelOrder(Order order){
        return orders.remove(order);
    }
}
