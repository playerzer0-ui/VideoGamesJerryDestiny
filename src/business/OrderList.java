package business;

import java.util.ArrayList;
import java.util.List;

public class OrderList {
    private List<Order> buy;
    private List<Order> sell;

    public OrderList() {
        buy = new ArrayList<>();
        sell = new ArrayList<>();
    }

    /**
     * add an order to the list
     * @param order the Order class
     * @return true/false
     */
    public boolean addOrder(Order order){
        if(order.getMode().equalsIgnoreCase("B")){
            return buy.add(order);
        }
        else{
            return sell.add(order);
        }
    }


    /**
     * remove an order from the list
     * @param order the Order class
     * @return true/false
     */
    public boolean cancelOrder(Order order){
        if(order.getMode().equalsIgnoreCase("B")){
            return buy.remove(order);
        }
        else{
            return sell.remove(order);
        }
    }

    /**
     * match an order from the list, gets you the better deal when possible
     * @param order the Order class
     * @return the order/null if not found better match
     */
    public Order matchOrder(Order order) {
        if(order.getMode().equalsIgnoreCase("B")){
            for (Order value : sell) {
                if (order.getPrice() > value.getPrice()) {
                    return value;
                }
            }
        } else if (order.getMode().equalsIgnoreCase("S")) {
            for (Order value : buy) {
                if (order.getPrice() < value.getPrice()) {
                    return value;
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
        for (Order order : buy) {
            output.append(order.getMode())
                    .append(TCProtocol.DELIMITER)
                    .append(order.getTitle())
                    .append(TCProtocol.DELIMITER)
                    .append(order.getPrice())
                    .append(TCProtocol.SPLITTER);
        }
        for (Order order : sell) {
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
