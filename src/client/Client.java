package client;

import business.TCProtocol;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("client service");
        //setup connection
        try(Socket socket = new Socket(TCProtocol.HOST, TCProtocol.PORT)){
            try(Scanner input = new Scanner(socket.getInputStream()); PrintWriter output = new PrintWriter(socket.getOutputStream())){
                boolean validSession = true;
                String msg = "";

                while(validSession){
                    System.out.println("1. LOGIN (username): ");
                    System.out.println("2. Place order: ");
                    System.out.println("3. Cancel order: ");
                    System.out.println("4. View orders made by you ");
                    System.out.println("5. EXIT");
                    String choice = sc.nextLine();

                    switch(choice){
                        case "1":
                            System.out.print("Enter username: ");
                            msg = TCProtocol.USER + TCProtocol.DELIMITER + sc.nextLine();
                            break;
                        case "2":
                            System.out.print("(B)uy or (S)ell? ");
                            String type = sc.nextLine().toUpperCase();
                            System.out.print("Enter game title: ");
                            String title = sc.nextLine();
                            System.out.print("Enter price: ");
                            String price = sc.nextLine();
                            msg = TCProtocol.ORDER + TCProtocol.DELIMITER + type + TCProtocol.DELIMITER + title + TCProtocol.DELIMITER + price;
                            break;
                        case "3":
                            System.out.print("Enter order details to cancel (B/S title price): ");
                            msg = TCProtocol.CANCEL + TCProtocol.DELIMITER + sc.nextLine().replace(" ", TCProtocol.DELIMITER);
                            break;
                        case "4":
                            msg = TCProtocol.VIEW;
                            break;
                        case "5":
                            msg = TCProtocol.END;
                            validSession = false;
                            break;
                    }

                    output.println(msg);
                    output.flush();

                    String response = input.nextLine();
                    System.out.println("message recieved: " + response);

                    if(response.equals(TCProtocol.ENDED)){
                        validSession = false;
                    }
                }
            }
        }
        catch(UnknownHostException e){
            System.out.println("Host cannot be found at this moment. Try again later");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
