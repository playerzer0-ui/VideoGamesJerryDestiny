package client;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        String serverIP = "localhost";
        int serverPort = 13838;
        String message = "";

        try (DatagramSocket clientSocket = new DatagramSocket();
             Scanner scanner = new Scanner(System.in)) {

            while (true) {
                // Read user input
                System.out.println("1. USER connect (USER%%username)");
                System.out.println("2. ORDER game (ORDER%%title%%price%%mode)");
                System.out.println("3. CANCEL order (CANCEL%%title%%price%%mode)");
                System.out.println("4. VIEW all orders (VIEW)");
                System.out.println("5. END");
                System.out.print("> ");
                message = scanner.nextLine();

                // Send message to server
                byte[] sendData = message.getBytes();
                InetAddress serverAddress = InetAddress.getByName(serverIP);
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, serverPort);
                clientSocket.send(sendPacket);

                // Receive echoed message
                byte[] receiveBuffer = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                clientSocket.receive(receivePacket);

                // Display response
                String response = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("received: " + response);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
