package control;

import persistence.StoreDTO;
import protocol.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StoreSelectController {
    private ResponseSender responseSender;
    private ResponseReceiver responseReceiver;
    private RequestSender requestSender;
    private RequestReceiver requestReceiver;

    public StoreSelectController() {
        this.responseSender = new ResponseSender();
        this.responseReceiver = new ResponseReceiver();
        this.requestSender = new RequestSender();
        this.requestReceiver = new RequestReceiver();
    }

    public List<String> handleStoreSelect ( Scanner sc, String user_id, DataInputStream inputStream, DataOutputStream outputStream) throws IOException {
        List<StoreDTO> loginedStores = new ArrayList<StoreDTO>();
        List<String> storeidAndName = new ArrayList<String>();
        //시작 신호 보내기
        BodyMaker bodyMaker = new BodyMaker();
        bodyMaker.addStringBytes(user_id);
        byte[] body = bodyMaker.getBody();
        Header start_Header = new Header(
                Header.TYPE_START,
                Header.CODE_SELECT_STORE,
                body.length);
        outputStream.write(start_Header.getBytes());
        outputStream.write(body);

        if(requestReceiver.receiveUserIDReq(inputStream))
        {
            responseSender.sendLoginedUserIDAns(user_id, outputStream);
        }

        if(requestReceiver.receiveStoreSelectResultReq(inputStream))
        {
            int userStoreNum = inputStream.readInt();
            System.out.println(userStoreNum);
            for(int i = 0; i < userStoreNum; i++)
            {
                loginedStores.add(StoreDTO.read(inputStream));
            }

            int index = 1;

            printAllStore(loginedStores);

            System.out.print("관리하실 가게 번호 선택해주세요. : ");
            int selectNum = sc.nextInt();

            System.out.println("--------------------------------------");

            storeidAndName.add("" + (loginedStores.get(selectNum - 1).getStore_id()));
            storeidAndName.add(loginedStores.get(selectNum - 1).getStore_name());
            return storeidAndName;
        }
        else {
            System.out.println("소유하신 가게가 없습니다.");
            return null;
        }

    }

    public List<StoreDTO> printAllStore(List<StoreDTO> dtos)
    {
        int i = 0;
        System.out.println("-------------------------------------------------------");
        for(StoreDTO dto: dtos) {
            System.out.println((i + 1) + ". " + dto.getStore_name() + "  \"" + dto.getStore_info() + " \"" +
                    "\n | 영업시간 : " + dto.getStore_time() + " \t| " + ((dto.getStore_state()) ? "(영업중)" : "(영업종료)") +
                    "\n | 주소 : " + dto.getStore_address() + "\t| 매장 전화번호 : " + dto.getStore_phone());
            System.out.println("-------------------------------------------------------");
            i++;
        }
        return dtos;
    }
}
