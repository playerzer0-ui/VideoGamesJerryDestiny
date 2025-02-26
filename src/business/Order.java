package business;

import java.util.Objects;

public class Order {
    private User user;
    private Game game;

    public Order(User user, Game game) {
        this.user = user;
        this.game = game;
    }

    public Order() {}

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(user, order.user) && Objects.equals(game, order.game);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, game);
    }

    @Override
    public String toString() {
        return "Order{" +
                "user=" + user +
                ", game=" + game +
                '}';
    }
}
