package control;

import persistence.StoreDTO;
import persistence.UserDTO;
import protocol.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LoginController {
    private ResponseSender responseSender;
    private ResponseReceiver responseReceiver;
    private RequestSender requestSender;
    private RequestReceiver requestReceiver;

    public LoginController()
    {
        this.responseSender = new ResponseSender();
        this.responseReceiver = new ResponseReceiver();
        this.requestSender = new RequestSender();
        this.requestReceiver = new RequestReceiver();
    }

    public String handleLogin(Scanner sc, DataInputStream inputStream, DataOutputStream outputStream) throws IOException {
        UserDTO loginUser;
        String user_id = "";

        //시작 신호 보내기
        Header start_Header = new Header(
                Header.TYPE_START,
                Header.CODE_LOG_IN,
                0);
        outputStream.write(start_Header.getBytes());

        if(requestReceiver.receiveUserIDReq(inputStream)) //id 요청 받기
            responseSender.sendUserIDAns(sc, outputStream); //id 요청 받고 id 보내기

        while(true)
        {
            if((user_id = requestReceiver.receiveUserIDResult_LogInReq(inputStream)) != "") {
                break;
            }

            System.out.println("아이디가 잘못 되었습니다.");
            responseSender.sendUserIDAns(sc, outputStream);//user_id 보내기
        }

        responseSender.sendUserPWAns(sc,user_id, outputStream);

        int dataLength = 0;

        while(true)
        {
            if(requestReceiver.receiveUserPWResult_LogInReq(inputStream)) {
                break;
            }

            System.out.println("비밀번호가 잘못 되었습니다.");
            responseSender.sendUserPWAns(sc,user_id, outputStream);
        }

        System.out.println("--------------------------------------");

        loginUser = UserDTO.read(inputStream);

        if(loginUser.getUser_category() != 1)
        {
            System.out.println("해당 회원은 점주가 아닙니다. 다시 로그인해주세요.");
            return null;
        }

        int userStoreNum = inputStream.readInt();

        List<StoreDTO> userStores = new ArrayList<StoreDTO>();

        int i = 0;

        for(; i < userStoreNum; i++)
        {
            userStores.add(StoreDTO.read(inputStream));
        }



        for(StoreDTO userstore : userStores)
        {
            System.out.print(" " + userstore.getStore_name() + " ");
        }
        System.out.println("가게 점주");
        System.out.println(loginUser.getUser_name() + "(" + loginUser.getUser_id() + ")" + "님 로그인 되셨습니다.");
        return user_id;
    }
}
