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
    public static final int MENU_ADD_APPLY = 3;
    public static final int ORDER_ACCEPT_OR_REFUSE = 6;
    public static final int REVIEW_LOOKUP_REPLY = 7;
    public static final int STATISTICS = 8;
    public static final int QUIT = 9;



    public boolean handleCommand(int command, Scanner sc, DataInputStream inputStream, DataOutputStream outputStream) throws IOException {
        MenuController menuController = new MenuController();
        LoginController loginController = new LoginController();
        OrderAcceptController orderAcceptController = new OrderAcceptController();
        ReviewController reviewController = new ReviewController();
        StatisticsController statisticsController = new StatisticsController();
        ResponseReceiver resReceiver = new ResponseReceiver();
        RequestSender reqSender = new RequestSender();

        int store_id = 1;//임시

        switch(command) {

            case SIGN_UP:
                //
                break;

            case LOG_IN:
                loginController.handleLogin(sc,inputStream, outputStream);
                break;

            case MENU_ADD_APPLY:
                menuController.handleMenuApply(sc,store_id, inputStream, outputStream);

            case ORDER_ACCEPT_OR_REFUSE:
                orderAcceptController.handleOrderAccept(sc,inputStream,outputStream);
                break;

            case REVIEW_LOOKUP_REPLY:
                reviewController.handleReview(sc , inputStream , outputStream);
                break;

            case STATISTICS:
                statisticsController.handleStatistics( sc, inputStream , outputStream);
                break;

            case QUIT:
                /*reqSender.sendQuitReq(outputStream);*/
                return false;

        }

        return true;
    }




}
