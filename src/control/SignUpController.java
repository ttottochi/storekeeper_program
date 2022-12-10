package control;

import persistence.UserDTO;
import protocol.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class SignUpController {
    private Socket so;
    private ResponseSender responseSender;
    private ResponseReceiver responseReceiver = new ResponseReceiver();
    private RequestSender requestSender = new RequestSender();
    private RequestReceiver requestReceiver = new RequestReceiver();

    public SignUpController()
    {
        this.responseSender = new ResponseSender();
        this.responseReceiver = new ResponseReceiver();
        this.requestSender = new RequestSender();
        this.requestReceiver = new RequestReceiver();
    }

    public void handleSignUp(Scanner sc, DataInputStream inputStream, DataOutputStream outputStream) throws IOException {
        UserDTO signUpUser = new UserDTO();
        String user_id;

        //시작 신호 보내기
        Header startHeader = new Header(
                Header.TYPE_START,
                Header.CODE_SIGN_UP,
                0);
        outputStream.write(startHeader.getBytes());

        if(requestReceiver.receiveUserIDReq(inputStream)) //user_id 요청 받기
        {
            responseSender.sendUserIDAns(sc, outputStream);//user_id 보내기
        }

        while(true)
        {
            if((user_id = requestReceiver.receiveUserIDResult_SignUpReq(inputStream)) != "")
                break;

            System.out.println("아이디가 중복됩니다.");
            responseSender.sendUserIDAns(sc, outputStream);//user_id 보내기
        }
        responseSender.sendUserInfoAns(sc,user_id, outputStream);

        System.out.println("--------------------------------------");

        if((signUpUser = requestReceiver.receiveSignUpResult(inputStream)) != null)
            System.out.println(signUpUser.getUser_name() + "(" + signUpUser.getUser_id() + ")점주 님 회원가입 되셨습니다.");
        else
            System.out.println("회원가입 실패.");



    }

}
