package client;

import business.TCProtocol;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Client {

    protected static boolean validSession;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("client service");
        //setup connection
        try(Socket socket = new Socket(TCProtocol.HOST, TCProtocol.PORT)){
            try(Scanner input = new Scanner(socket.getInputStream()); PrintWriter output = new PrintWriter(socket.getOutputStream())){
                validSession = true;
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
                            System.out.print("(B)uy or (S)ell? ");
                            String type1 = sc.nextLine().toUpperCase();
                            System.out.print("Enter game title: ");
                            String title1 = sc.nextLine();
                            System.out.print("Enter price: ");
                            String price1 = sc.nextLine();
                            msg = TCProtocol.CANCEL + TCProtocol.DELIMITER + type1 + TCProtocol.DELIMITER + title1 + TCProtocol.DELIMITER + price1;
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
//                    System.out.println("message recieved: " + response);
                    responseQuery(response);
//                    if(response.equals(TCProtocol.ENDED)){
//                        validSession = false;
//                    }
                }
            }
        }
        catch(UnknownHostException e){
            System.out.println("Host cannot be found at this moment. Try again later");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void responseQuery(String response){
        String[] component = response.split(TCProtocol.DELIMITER);

        switch (component[0]){
            case TCProtocol.CONNECTED:
                System.out.println("you are now connected");
                break;

            case TCProtocol.MATCH:
                System.out.println("match found with better price than yours!");
                System.out.println("-----------------------------------------");
                System.out.println("mode: " + component[1]);
                System.out.println("title: " + component[2]);
                System.out.println("price: " + component[3]);
                System.out.println("user: " + component[4]);
                System.out.println("-----------------------------------------");
                break;

            case TCProtocol.NOT_LOGGED_IN:
                System.out.println("you are not logged in");
                break;

            case TCProtocol.CANCELLED:
                System.out.println("order cancelled!");
                break;

            case TCProtocol.NOT_FOUND:
                System.out.println("order not found");
                break;

            case TCProtocol.ENDED:
                System.out.println("SHUT DOWN");
                validSession = false;
                break;

            case TCProtocol.ERROR:
                System.out.println("invalid command");
                break;

            default:
                String[] entries = response.split(TCProtocol.SPLITTER);
                if (entries.length == 0) {
                    break;
                }
                Map<String, String> buyItems = new HashMap<>();
                Map<String, String> sellItems = new HashMap<>();

                for (String entry : entries) {
                    // Skip empty or invalid entries
                    if (entry == null || entry.isEmpty()) {
                        continue;
                    }

                    String[] parts = entry.split(TCProtocol.DELIMITER);

                    // Validate that the split result has at least 3 parts
                    if (parts.length < 3) {
                        System.out.println("Invalid entry: " + entry);
                        continue; // Skip this entry
                    }

                    String action = parts[0]; // B or S
                    String item = parts[1];   // Name
                    String value = parts[2];  // Value

                    // Group items into "buy" or "sell"
                    if (action.equals("B")) {
                        buyItems.put(item, value);
                    } else if (action.equals("S")) {
                        sellItems.put(item, value);
                    }
                }

                System.out.println("buy");
                System.out.println("------------------------");
                for (Map.Entry<String, String> entry : buyItems.entrySet()) {
                    System.out.println(entry.getKey() + " = " + entry.getValue());
                }

                System.out.println("\nsell");
                System.out.println("------------------------");
                for (Map.Entry<String, String> entry : sellItems.entrySet()) {
                    System.out.println(entry.getKey() + " = " + entry.getValue());
                }
                break;

        }
    }
}
