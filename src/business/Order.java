package business;

import java.util.Objects;

public class Order {
    private User user;
    private String title;
    private double price;
    private String mode;

    public Order(User user, String mode, String title, double price) {
        this.user = user;
        this.title = title;
        this.price = price;
        this.mode = mode;
    }

    public Order() {}

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;
        return Double.compare(price, order.price) == 0 && user.equals(order.user) && title.equals(order.title) && mode.equals(order.mode);
    }

    @Override
    public int hashCode() {
        int result = user.hashCode();
        result = 31 * result + title.hashCode();
        result = 31 * result + Double.hashCode(price);
        result = 31 * result + mode.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return mode + TCProtocol.DELIMITER + title + TCProtocol.DELIMITER + price + TCProtocol.DELIMITER + user.getUsername();
    }
}
