package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientConnection
{
    private Socket serverSocket;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;

    /**
     * Initialize the client with a given address of the network a server is on and
     * the port that the server is listening to
     *
     * @param address    the network address the server is on
     * @param portNumber the port that the server is listening to
     */
    public void connectToServer(String address, int portNumber)
    {
        try
        {
            System.out.println("Attempting to connect to server...");

            serverSocket = new Socket(address, portNumber);

            System.out.println("Connected to server at address: " + address + ":" + portNumber);

            dataInputStream = new DataInputStream(serverSocket.getInputStream());
            dataOutputStream = new DataOutputStream(serverSocket.getOutputStream());
        }
        catch(IOException e)
        {
            throw new RuntimeException("Could not connect to server");
        }
    }

    /**
     * Listen to a response from the server
     *
     * @return a message from the server
     * @throws IllegalStateException throw exception if the client is not connected to a server
     */
    public String listenToServer() throws IllegalStateException
    {
        if(serverSocket == null)
        {
            throw new IllegalStateException("Not connected to a server");
        }

        try
        {
            return dataInputStream.readUTF();
        }
        catch(IOException e)
        {
            throw new RuntimeException("Can not listen to server");
        }
    }

    /**
     * Send a message to the server
     *
     * @param input the message to send
     * @throws IllegalStateException throw exception if not connected to a server
     */
    public void sendMessage(String input) throws IllegalStateException
    {
        if(serverSocket == null)
        {
            throw new IllegalStateException("Not connected to server");
        }

        try
        {
            dataOutputStream.writeUTF(input);
        }
        catch(IOException e)
        {
            throw new RuntimeException("Could not write to server");
        }
    }

    /**
     * Disconnect from the server and close the socket
     */
    public void disconnectFromServer()
    {
        System.out.println("Disconnecting from server...");

        try
        {
            dataOutputStream.close();
            serverSocket.close();
        }
        catch(IOException e)
        {
            throw new RuntimeException("Problem disconnecting from server");
        }
    }
}
