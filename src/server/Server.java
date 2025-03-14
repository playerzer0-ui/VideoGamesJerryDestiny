package server;

import business.OrderList;
import business.TCProtocol;
import business.User;
import business.UserList;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Server {
    public static void main(String[] args) {
        System.out.println("Server is online");
        List<User> users = new ArrayList<>();
        users.add(new User("jerry"));
        users.add(new User("bobby"));
        users.add(new User("destiny"));

        UserList userList = new UserList(users);
        OrderList orderList = new OrderList();

        try (ServerSocket serverSocket = new ServerSocket(TCProtocol.PORT)) {
            while (true) {
                try {
                    Socket socket = serverSocket.accept(); // Accept a new client connection
                    System.out.println("New client connected: " + socket.getInetAddress());

                    // Create a new ClientHandler instance and run it in a separate thread
                    Thread clientThread = new Thread(new ClientHandler(socket, userList, orderList));
                    clientThread.start();
                } catch (IOException e) {
                    System.out.println("Error handling client connection: " + e.getMessage());
                }
            }
        } catch (BindException e) {
            System.out.println("BindException occurred when attempting to bind to port " + TCProtocol.PORT);
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println("IOException occurred on server socket");
            System.out.println(e.getMessage());
        }
    }
}
