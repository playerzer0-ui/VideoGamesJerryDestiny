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

    /**
     * match an order from the list, a buy order will match the smaller price, a sell order will match the higher price
     * @param order the Order class
     * @return the order/null if not found better match
     */
    public Order matchOrder(Order order) {
        for (Order o : orders) {
            if (o.getTitle().equals(order.getTitle()) &&
                    o.getMode().equals(order.getMode())) {

                if(order.getMode().equalsIgnoreCase("B")){
                    if (o.getPrice() < order.getPrice()) {
                        return o;
                    }
                }
                else{
                    if (o.getPrice() > order.getPrice()) {
                        return o;
                    }
                }
            }
        }
        return null;
    }

    /**
     * this can be used to view orders
     * @return the list of orders
     */
    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        for (Order order : orders) {
            output.append(order.getMode())
                    .append(TCProtocol.DELIMITER)
                    .append(order.getTitle())
                    .append(TCProtocol.DELIMITER)
                    .append(order.getPrice())
                    .append(TCProtocol.SPLITTER);
        }

        // Remove the last "~~" if there are any orders
        if (!output.isEmpty()) {
            output.setLength(output.length() - 2);
        }

        return output.toString();
    }
}
