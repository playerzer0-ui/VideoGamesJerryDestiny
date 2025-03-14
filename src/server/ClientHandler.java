package server;
import business.Order;
import business.OrderList;
import business.TCProtocol;
import business.User;
import business.UserList;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;

class ClientHandler implements Runnable {
    private final Socket socket;
    private final UserList userList;
    private final OrderList orderList;
    private User currentUser = null;

    public ClientHandler(Socket socket, UserList userList, OrderList orderList) {
        this.socket = socket;
        this.userList = userList;
        this.orderList = orderList;
    }

    @Override
    public void run() {
        try (Scanner input = new Scanner(socket.getInputStream());
             PrintWriter output = new PrintWriter(socket.getOutputStream(), true)) {

            boolean validSession = true;
            while (validSession && input.hasNextLine()) {
                String request = input.nextLine();
                String[] components = request.split(TCProtocol.DELIMITER);
                System.out.println(Arrays.toString(components));
                String response = TCProtocol.ERROR;

                switch (components[0]) {
                    case TCProtocol.USER:
                        if (components.length > 1) {
                            String username = components[1];
                            currentUser = new User(username);
                            boolean connected = userList.connectUser(username);
                            response = connected ? TCProtocol.CONNECTED : TCProtocol.NOT_LOGGED_IN;
                        }
                        break;

                    case TCProtocol.ORDER:
                        if (currentUser == null) {
                            response = TCProtocol.NOT_LOGGED_IN;
                        } else if (components.length == 4) {
                            String mode = components[1];
                            String title = components[2];
                            double price;
                            try {
                                price = Double.parseDouble(components[3]);
                                Order newOrder = new Order(currentUser, mode, title, price);
                                Order matchedOrder = orderList.matchOrder(newOrder);
                                if (matchedOrder != null) {
                                    response = TCProtocol.MATCH + TCProtocol.DELIMITER + matchedOrder;
                                    orderList.cancelOrder(matchedOrder); 
                                } else {
                                    orderList.addOrder(new Order(currentUser, mode, title, price));
                                    response = orderList.toString();
                                }
                            } catch (NumberFormatException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        break;

                    case TCProtocol.CANCEL:
                        if (currentUser == null) {
                            response = TCProtocol.NOT_LOGGED_IN;
                        } else if (components.length == 4) {
                            String mode = components[1];
                            String title = components[2];
                            double price;
                            try {
                                price = Double.parseDouble(components[3]);
                                Order orderToRemove = new Order(currentUser, mode, title, price);
                                boolean cancelled = orderList.cancelOrder(orderToRemove);
                                response = cancelled ? TCProtocol.CANCELLED : TCProtocol.NOT_FOUND;
                            } catch (NumberFormatException e) {
                                response = TCProtocol.ERROR;
                            }
                        }
                        break;

                    case TCProtocol.VIEW:
                        response = orderList.toString();
                        break;

                    case TCProtocol.END:
                        response = TCProtocol.ENDED;
                        validSession = false;
                        break;
                }
                output.println(response);
            }
        } catch (IOException e) {
            System.out.println("Client handler error: " + e.getMessage());
        }
    }
}
