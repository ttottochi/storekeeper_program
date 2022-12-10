package MenuControl;

import control.StoreKeeperController;
import control.LoginController;
import control.SignUpController;

import protocol.Header;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

public class MainController {
    private Scanner sc;
    private DataInputStream is;
    private DataOutputStream os;
    private String user_id ="";
    private SignUpController signUpController;
    private LoginController loginController;
    private StoreKeeperController storeKeeperController;

    public static final int SIGN_UP = 1;
    public static final int LOG_IN = 2;
    public static final int QUIT = 0;

    public MainController(Socket socket) throws IOException {
        sc = new Scanner(System.in);
        signUpController = new SignUpController();
        loginController = new LoginController();
        storeKeeperController = new StoreKeeperController();

    }

    public boolean handleMainCommand(int command, Scanner sc, DataInputStream inputStream, DataOutputStream outputStream) throws IOException
    {
        is = inputStream;
        os = outputStream;

        switch(command)
        {
            case SIGN_UP:
                signUpController.handleSignUp(sc,inputStream, outputStream);
                break;

            case LOG_IN:
                user_id = loginController.handleLogin(sc,inputStream, outputStream);
                if(user_id != null)
                    storeKeeperCommand(user_id);
                else
                    return true;
                break;

            case QUIT:
                System.out.println("프로그램을 종료합니다.");
                return false;
        }
        return true;
    }



    private void storeKeeperCommand(String user_id) throws IOException
    {
        boolean isContinue = true;
        while(isContinue) {
            int command;
            System.out.println("==============점주 프로그램=============");
            System.out.println("1. 매장 추가");
            System.out.println("2. 매장 선택");
            System.out.println("0. 종료");
            System.out.println("======================================");
            System.out.print("메뉴를 선택하세요 : ");
            command = sc.nextInt();
            System.out.println("--------------------------------------");

            isContinue = storeKeeperController.handleCommand(command, sc, is, os, user_id);
            System.out.println("======================================");
        }
    }
}
