package control;

import protocol.RequestSender;
import protocol.ResponseReceiver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class Controller {

    public static final int SIGN_UP =1;
    public static final int LOG_IN = 2;
//    public static final int FIND_PLAYER_BY_NAME = 2;
//    public static final int FIND_ALL_PLAYER = 3;
//    public static final int FIND_ALL_TEAM = 4;
    public static final int ORDER_ACCEPT_OR_REFUSE = 6;
    public static final int REVIEW_LOOKUP_REPLY = 7;
    public static final int STATISTICS = 8;
    public static final int QUIT = 9;



    public boolean handleCommand(int command, Scanner s, DataInputStream inputStream, DataOutputStream outputStream) throws IOException {

        ResponseReceiver resReceiver = new ResponseReceiver();
        RequestSender reqSender = new RequestSender();

        switch(command) {

            case SIGN_UP:
                //
                break;

            case LOG_IN:
                reqSender.sendCustomerID(s, outputStream);
                reqSender.sendCustomerPW(s, outputStream);
                resReceiver.receiveLogInResult(inputStream);
                break;

            case ORDER_ACCEPT_OR_REFUSE:
                break;

            case REVIEW_LOOKUP_REPLY:
                break;

            case STATISTICS:
                break;

            case QUIT:
                /*reqSender.sendQuitReq(outputStream);*/
                return false;

        }

        return true;
    }




}
