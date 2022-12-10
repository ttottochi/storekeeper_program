package control;

import protocol.Header;
import protocol.RequestSender;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

public class StoreKeeperController {
    Scanner sc;
    String user_id;
    DataInputStream is;
    DataOutputStream os;
    int store_id = 0;
    StoreManagementController storeManagementController;
    StoreController storeController;
    StoreSelectController storeSelectController;

    public static final int STORE_ADD_APPLY = 1;
    public static final int STORE_SELECT = 2;
    public static final int QUIT = 0;

    public StoreKeeperController() throws IOException {
        sc = new Scanner(System.in);
        storeManagementController = new StoreManagementController();
        storeController = new StoreController();
        storeSelectController = new StoreSelectController();
    }

    public boolean handleCommand(int command, Scanner sc, DataInputStream inputStream, DataOutputStream outputStream, String input_user_id) throws IOException {
        this.user_id = input_user_id;
        is = inputStream;
        os = outputStream;

        switch(command) {

            case STORE_ADD_APPLY:
                storeController.handleStoreApply(sc, user_id, inputStream, outputStream);
                break;

            case STORE_SELECT:
                List<String> store_id_name = storeSelectController.handleStoreSelect(sc, user_id, inputStream, outputStream);
                if(store_id_name == null)
                    return true;
                else {
                    store_id = Integer.parseInt(store_id_name.get(0));
                    String store_name = store_id_name.get(1);

                    storeManagementCommand(store_id, store_name);
                }
                break;

            case QUIT:
                System.out.println("이전 화면으로 돌아갑니다.");
                return false;
        }

        return true;
    }


    private void storeManagementCommand(int store_id, String store_name) throws IOException
    {
        boolean isContinue = true;
        while(isContinue) {
            int command;
            System.out.println("==============" + store_name + " 관리=============");
            System.out.println("1. 메뉴 추가");
            System.out.println("2. 영업시간 변경");
            System.out.println("3. 주문 접수 승인/거절");
            System.out.println("4. 리뷰 조회/답글");
            System.out.println("5. 통계정보 (메뉴별 주문건수, 매출)");
            System.out.println("0. 돌아가기");
            System.out.println("======================================");
            System.out.print("메뉴를 선택하세요 : ");
            command = sc.nextInt();
            System.out.println("--------------------------------------");

            isContinue = storeManagementController.handleCommand(command, sc, is, os, store_id);
            System.out.println("======================================");
        }
    }
}
