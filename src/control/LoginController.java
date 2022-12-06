package control;

import persistence.StoreDTO;
import persistence.UserDTO;
import protocol.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
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
        Header start_Header = new Header( Header.TYPE_START, Header.CODE_LOG_IN, 0);
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

        while(true)
        {
            if(requestReceiver.receiveUserPWResult_LogInReq(inputStream)) {
                break;
            }

            System.out.println("비밀번호가 잘못 되었습니다.");
            responseSender.sendUserPWAns(sc,user_id, outputStream);
        }

        loginUser = UserDTO.read(inputStream);
        int userStoreNum = inputStream.readInt();

        List<StoreDTO> userStores = new ArrayList<StoreDTO>();
        for(int i = 0; i < userStoreNum; i++)
        {
            userStores.add(StoreDTO.read(inputStream));
        }

        System.out.println(loginUser.getUser_name() + "(" + loginUser.getUser_id() + ")" + "님 로그인 되셨습니다.");
        return loginUser.getUser_id();
    }
}
