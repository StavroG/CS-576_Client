package client;

import java.util.Scanner;

public class Main
{
    private static final int MAX_CHAR_COUNT = 256;
    private static final String ADDRESS = "127.0.0.1";
    private static final int PORT_NUMBER = 5000;

    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        ClientConnection clientConnection = new ClientConnection();

        try
        {
            clientConnection.connectToServer(ADDRESS, PORT_NUMBER);
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }

        while(true)
        {
            try
            {
                System.out.println();
                System.out.println("Type a message to send to the server or type \"quit\" to exit: ");
                String input = scanner.nextLine();

                if(input.length() > MAX_CHAR_COUNT)
                {
                    System.out.println("Input messages must be less than 256 characters");
                    continue;
                }

                clientConnection.sendMessage(input);

                if(input.equals("quit"))
                {
                    clientConnection.disconnectFromServer();
                    break;
                }

                System.out.println("Server responded with: ");
                String serverResponse = clientConnection.listenToServer();
                System.out.println(serverResponse);
            }
            catch(Exception e)
            {
                System.out.println("An error occurred, disconnecting from the server");
                System.out.println("Error message: " + e.getMessage());
                clientConnection.disconnectFromServer();
                break;
            }
        }
    }
}