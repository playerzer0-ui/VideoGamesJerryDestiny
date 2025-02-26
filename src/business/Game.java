package business;

import java.util.Objects;

public class Game {
    private String name;
    private double price;
    private String mode;

    public Game(String mode, double price, String name) {
        this.mode = mode;
        this.price = price;
        this.name = name;
    }

    public Game() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        Game game = (Game) o;
        return Double.compare(price, game.price) == 0 && Objects.equals(name, game.name) && Objects.equals(mode, game.mode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price, mode);
    }

    @Override
    public String toString() {
        return "Game{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", mode='" + mode + '\'' +
                '}';
    }
}
