package server;

import java.net.BindException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.List;

public class Server {
    public static void main(String[] args) {
        int port = 13838; // Echo service port
        String msg = "null";

        try (DatagramSocket serverSocket = new DatagramSocket(port)) {
            System.out.println("Echo Server is running on port " + port);

            byte[] buffer = new byte[1024];

            while (true) {
                // Receive packet
                DatagramPacket receivedPacket = new DatagramPacket(buffer, buffer.length);
                serverSocket.receive(receivedPacket);

                // Extract message and sender's details
                String receivedMessage = new String(receivedPacket.getData(), 0, receivedPacket.getLength());
                InetAddress senderAddress = receivedPacket.getAddress();
                int senderPort = receivedPacket.getPort();

                System.out.println("Received from " + senderAddress + ":" + senderPort + " -> " + receivedMessage);
                String[] msgArr = receivedMessage.split("%%");

//                switch(msgArr[0]){
//                    case "ADD":
//                        break;
//                    case "REMOVE":
//                        break;
//                    case "SEARCH":
//                        break;
//                    case "DISPLAY":
//                        break;
//                }

                // Echo message back to sender
                byte[] responseData = msg.getBytes();
                DatagramPacket responsePacket = new DatagramPacket(responseData, responseData.length, senderAddress, senderPort);
                serverSocket.send(responsePacket);
            }
        } catch (BindException e){
            System.out.println("port number error");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
