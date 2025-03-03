package server;

import business.OrderList;
import business.TCProtocol;
import business.UserList;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    public static void main(String[] args) {
        System.out.println("server is online");
        try(ServerSocket serverSocket = new ServerSocket(TCProtocol.PORT)){

            while(true){
                Socket socket = serverSocket.accept();
                boolean validSession = true;
                UserList userList = new UserList();
                OrderList orderList = new OrderList();

                try(Scanner input = new Scanner(socket.getInputStream()); PrintWriter output = new PrintWriter(socket.getOutputStream())){

                    while(validSession){
                        String request = input.nextLine();
                        String[] components = request.split(TCProtocol.DELIMITER);
                        String response = "";

                        switch (components[0]){
                            case TCProtocol.USER:

                                break;
                            case TCProtocol.ORDER:

                                break;
                            case TCProtocol.CANCEL:

                                break;
                            case TCProtocol.VIEW:
                                break;
                            case TCProtocol.END:
                                break;
                        }

                        output.println(response);
                        output.flush();
                    }
                }
            }
        }
        catch (BindException e) {
            System.out.println("BindException occurred when attempting to bind to port " + 12321);
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println("IOException occurred on server socket");
            System.out.println(e.getMessage());
        }
    }
}
