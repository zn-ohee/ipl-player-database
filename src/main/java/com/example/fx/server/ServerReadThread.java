package com.example.fx.server;

import com.example.fx.utils.SocketWrapper;
import com.example.fx.models.Request;

public class ServerReadThread implements Runnable {

    private SocketWrapper clientSocketWrapper;
    private Server server;

    public ServerReadThread(SocketWrapper clientSocketWrapper, Server server) {
        this.clientSocketWrapper = clientSocketWrapper;
        this.server = server;
        new Thread(this).start();
    }

    @Override
    public void run() {
        try{
            while(true){
                Request request = (Request) clientSocketWrapper.read();
                if(request.getType() == 1){
                    server.handleLoginRequest(request, clientSocketWrapper);
                }
                else if(request.getType() == 3){
                    server.handleSellRequest(request);
                }
                else if(request.getType() == 4){
                    server.handleBuyRequest(request);
                }
                else if(request.getType() == 10){
                    server.detachClient(request.getSenderClubName());
                    break;
                }
                else if(request.getType() == 11){
                    break;
                }
                else if(request.getType() == 13){
                    server.handleChangePassRequest(request, clientSocketWrapper);
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally {
            try {
                clientSocketWrapper.closeConnection();
                System.out.println("One client disconnected");
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
