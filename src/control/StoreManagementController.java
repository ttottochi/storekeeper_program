package control;


import protocol.Header;
import protocol.RequestSender;
import protocol.ResponseReceiver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class StoreManagementController {
    Scanner sc;

    MenuController menuController;
    StoreController storeController;
    LoginController loginController;
    OrderAcceptController orderAcceptController;
    ReviewController reviewController;
    StatisticsController statisticsController;


    public static final int MENU_ADD_APPLY = 1;
    public static final int STORE_TIME_UPDATE = 2;
    public static final int ORDER_ACCEPT_OR_REFUSE = 3;
    public static final int REVIEW_LOOKUP_REPLY = 4;
    public static final int STATISTICS = 5;
    public static final int QUIT = 0;

    public StoreManagementController() throws IOException {
        sc = new Scanner(System.in);
        menuController = new MenuController();
        storeController = new StoreController();
        loginController = new LoginController();
        orderAcceptController = new OrderAcceptController();
        reviewController = new ReviewController();
        statisticsController = new StatisticsController();
    }

    public boolean handleCommand(int command, Scanner sc, DataInputStream inputStream, DataOutputStream outputStream, int store_id) throws IOException {

        int Store_id = store_id;

        switch(command) {
            case MENU_ADD_APPLY:
                menuController.handleMenuApply(sc,Store_id, inputStream, outputStream);
                break;

            case STORE_TIME_UPDATE:
                storeController.handleStoreTimeUpdate(sc, Store_id, inputStream, outputStream);
                break;

            case ORDER_ACCEPT_OR_REFUSE:
                orderAcceptController.handleOrderAccept(sc,Store_id, inputStream,outputStream);
                break;

            case REVIEW_LOOKUP_REPLY:
                reviewController.handleReview(sc ,Store_id, inputStream , outputStream);
                break;

            case STATISTICS:
                statisticsController.handleStatistics( sc, Store_id, inputStream , outputStream);
                break;

            case QUIT:
                System.out.println("이전 화면으로 돌아갑니다.");
                return false;

        }

        return true;
    }
}
