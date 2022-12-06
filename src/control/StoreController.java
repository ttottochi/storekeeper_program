package control;

import protocol.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class StoreController {
    private ResponseSender responseSender;
    private ResponseReceiver responseReceiver;
    private RequestSender requestSender;
    private RequestReceiver requestReceiver;

    public StoreController()
    {
        this.responseSender = new ResponseSender();
        this.responseReceiver = new ResponseReceiver();
        this.requestSender = new RequestSender();
        this.requestReceiver = new RequestReceiver();
    }
    public void handleStoreTimeUpdate(Scanner sc, int store_id, DataInputStream inputStream, DataOutputStream outputStream) throws IOException {
        //시작 신호 보내기
        Header startHeader = new Header(
                Header.TYPE_START,
                Header.CODE_UPDATE_STORE_TIME,
                0);
        outputStream.write(startHeader.getBytes());

        System.out.println("");
        if(requestReceiver.receiveStoreTimeReq(inputStream)) //id 요청 받기
            responseSender.sendStoreTimeAns(outputStream); //id 요청 받고 id 보내기
    }
}
