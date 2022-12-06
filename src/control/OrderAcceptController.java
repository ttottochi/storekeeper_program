package control;

import persistence.OrderDTO;
import protocol.BodyMaker;
import protocol.Header;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OrderAcceptController {

    public void handleOrderAccept(Scanner sc, DataInputStream inputStream, DataOutputStream outputStream) throws IOException {

        //시작 신호 보내기
        BodyMaker bodyMaker = new BodyMaker();

        Header startHeader = new Header(
                Header.TYPE_START,
                Header.CODE_ORDER_ACCEPT,
                0);
        outputStream.write(startHeader.getBytes());

        //서버에서 보낸 Order List 받기
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        Header list_header = Header.readHeader(inputStream);
        int listSize = inputStream.readInt();
        List<OrderDTO> list  = new ArrayList<OrderDTO>();
        for(int i = 0 ; i <listSize ; i ++)
        {
            int order_id = inputStream.readInt(); String user_id = inputStream.readUTF();
            int store_id = inputStream.readInt(); long order_price = inputStream.readLong();
            String order_state = inputStream.readUTF() ;
            LocalDateTime order_orderTime= LocalDateTime.parse(inputStream.readUTF(),formatter);
            String order_num = inputStream.readUTF();
            OrderDTO orderDTO = new OrderDTO(order_id , user_id , store_id , order_price , order_state , order_orderTime , order_num);
            list.add(orderDTO);
        }

        System.out.println("<접수 대기 중인 주문 목록>");
        for(int i = 0 ; i < listSize ; i ++)
        {
            OrderDTO order = list.get(i);
            System.out.println("("+(i+1)+")"+" 주문자 :"+order.getUser_id()+" 주문 가격 :"+order.getOrder_price()+
                    " 주문 상태 :"+order.getOrder_state()+" 주문시간 :"+order.getOrder_orderTime());
        }
        System.out.println("승인 혹은 거절할 주문의 번호를 입력하시오 : ");
        int select_Order = sc.nextInt();
        System.out.println("승인 (1) , 거절 (2) : ");
        int select_Accept = sc.nextInt();
        OrderDTO fixedOrderDTO = list.get(select_Order-1);
        if(select_Accept == 1)
            fixedOrderDTO.setOrder_state("배달중");
        else if(select_Accept == 2)
            fixedOrderDTO.setOrder_state("취소");
        else
            System.out.println("잘못된 입력입니다.");

        //수정된 OrderDTO 전송
        BodyMaker bodyMaker_fix = new BodyMaker();
        bodyMaker_fix.add(fixedOrderDTO);
        byte[] fix_Body = bodyMaker_fix.getBody();
        Header fix_Header = new Header(
                Header.TYPE_ANS,
                Header.CODE_FIXED_ORDER_DTO,
                fix_Body.length);
        outputStream.write(fix_Header.getBytes());
        outputStream.write(fix_Body);

        //결과 받기
        Header result_header = Header.readHeader(inputStream);
        if(result_header.code == Header.CODE_SUCCESS)
            System.out.println("주문을 처리하는데 성공하였습니다.");
        else
            System.out.println("주문을 처리하는데 실패하였습니다.");

    }
}
