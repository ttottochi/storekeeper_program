package control;

import persistence.MenuDTO;
import protocol.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class MenuController {
    private ResponseSender responseSender;
    private ResponseReceiver responseReceiver;
    private RequestSender requestSender;
    private RequestReceiver requestReceiver;

    public MenuController()
    {
        this.responseSender = new ResponseSender();
        this.responseReceiver = new ResponseReceiver();
        this.requestSender = new RequestSender();
        this.requestReceiver = new RequestReceiver();
    }
    public void handleMenuApply(Scanner sc,int store_id, DataInputStream inputStream, DataOutputStream outputStream) throws IOException {
        //시작 신호 보내기
        Header startHeader = new Header(
                Header.TYPE_START,
                Header.CODE_INSERT_MENU,
                0);
        outputStream.write(startHeader.getBytes());

        MenuDTO addMenuDTO = new MenuDTO();

        if(requestReceiver.receiveMenuInfoReq(inputStream)) //menu_info요청 받기
        {
            System.out.println("메뉴 정보 요청 받음");
            responseSender.sendAddMenuInfoAns(store_id, outputStream);//menu_info보내기
        }



        if((addMenuDTO = requestReceiver.receiveMenuAddResult(inputStream)) != null) {
            System.out.println("메뉴가 추가 되었습니다.");
            System.out.println("카테고리 : " + addMenuDTO.getMenu_category() + "| " + addMenuDTO.getMenu_name() + " | 가격 : " + addMenuDTO.getMenu_price() + " | 재고 : " + addMenuDTO.getMenu_quantity());
        }
        else
            System.out.println("메뉴 추가 실패.");



    }
}
