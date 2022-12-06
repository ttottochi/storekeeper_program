import control.Controller;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {

        Socket socket = new Socket("127.0.0.1", 3000);

        System.out.println("connected.");

        DataInputStream is = new DataInputStream(socket.getInputStream());
        DataOutputStream os = new DataOutputStream(socket.getOutputStream());

        Scanner sc = new Scanner(System.in);

        System.out.println("======================================");
        System.out.println("=============점주 프로그램==============");
        System.out.println("======================================");

        boolean isContinue = true;

        Controller controller = new Controller();

        while(isContinue) {

            int command;

            System.out.println("1. 회원가입");
            System.out.println("2. 로그인");
            System.out.println("3. 메뉴 추가");
            System.out.println("4. 영업시간 변경");
            System.out.println("5. 기억이 안나");
            System.out.println("6. 주문 접수 승인/거절");
            System.out.println("7. 리뷰 조회/답글");
            System.out.println("8. 통계정보 (메뉴별 주문건수, 매출)");
            System.out.println("9. 종료");
            System.out.println("======================================");
            System.out.print("메뉴를 선택하세요 : ");
            command = sc.nextInt();

            isContinue = controller.handleCommand(command, sc, is, os);

            System.out.println("======================================");


        } // end of while


    } // end of main
}
