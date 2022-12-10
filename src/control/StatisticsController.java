package control;

import persistence.StatisticalInfoDTO;
import protocol.BodyMaker;
import protocol.Header;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StatisticsController {

    public void handleStatistics(Scanner sc,int Store_id, DataInputStream inputStream, DataOutputStream outputStream) throws IOException {

        //시작 신호 보내기
        BodyMaker bodyMaker_start = new BodyMaker();
        bodyMaker_start.addIntBytes(Store_id);
        byte[] start_Body = bodyMaker_start.getBody();
        Header startHeader = new Header(
                Header.TYPE_START,
                Header.CODE_STATISTICS,
                start_Body.length);
        outputStream.write(startHeader.getBytes());
        outputStream.write(start_Body);

        // statisticalinfo DTO LIST 받기

        Header list_header = Header.readHeader(inputStream);
        int listSize = inputStream.readInt();

        List<StatisticalInfoDTO> list  = new ArrayList<StatisticalInfoDTO>();
        for(int i = 0 ; i <listSize ; i ++)
        {
            list.add(StatisticalInfoDTO.read(inputStream));
        }

        System.out.println("<메뉴 별 통계>");
        for(int i = 0 ; i < list.size() ; i ++)
        {
            StatisticalInfoDTO statisticInfo = list.get(i);
            System.out.println(statisticInfo.getMenu_name()+" | 총 매출 :"+statisticInfo.getSum_order_price()+
                    " | 주문 수 : " + statisticInfo.getCount_order());
        }




    }
}
