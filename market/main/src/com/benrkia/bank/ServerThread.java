package com.benrkia.bank;

import com.benrkia.bank.dao.AccountDao;
import com.benrkia.bank.data.Card;
import com.benrkia.bank.manager.AccountManager;
import com.benrkia.bank.manager.CardManager;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerThread extends Thread {

    private Socket serverEndSocket;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    AccountManager accountManager = new AccountManager();
    CardManager cardManager = new CardManager();

    public ServerThread(Socket serverEndSocket) {
        try {
            this.serverEndSocket = serverEndSocket;
            this.input = new ObjectInputStream(serverEndSocket.getInputStream());
            this.output = new ObjectOutputStream(serverEndSocket.getOutputStream());
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            Response response;
            while (true){

                response = (Response) input.readObject();
                if(!response.isReq()){
                    // user response with operation done
                    break;
                }else {
                    String cardInfo = (String) response.getReq();
                    String cardInfos[] = cardInfo.split(" ");
                    try {

                        String number = cardInfos[0];
                        String cvv = cardInfos[1];
                        String expirationDate[] = cardInfos[2].split("/");

                        Card card = cardManager.getCard(number, cvv, expirationDate[0], expirationDate[1]);
                        if(card == null){
                            output.writeObject(Response.createResponse(null, "wrong card data", true));
                            output.flush();
                        }
                        else {
                            output.writeObject(Response.createResponse(null, "account exist", false));
                            output.flush();

                            double cost = input.readDouble();
                            boolean answer = accountManager.debit(card, cost);
                            if(!answer){
                                output.writeObject(Response.createResponse(null, "no enough money", true));
                                output.flush();
                            }else {
                                output.writeObject(Response.createResponse(null, "operation done successfully", false));
                                output.flush();
                            }
                        }

                    }catch (IndexOutOfBoundsException e){
                        output.writeObject(Response.createResponse(null, "wrong format", true));
                        output.flush();
                    }
                }
            }
        }catch (Exception e){}
        finally {
            try {
                this.serverEndSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
