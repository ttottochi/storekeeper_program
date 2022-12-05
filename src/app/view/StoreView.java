package app.view;

import app.service.UserService;
import persistence.dto.StoreDTO;

import java.util.List;

public class StoreView {

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

    public void printAllStore_manager(List<StoreDTO> dtos)
    {
        UserService us = new UserService();
        int i = 0;
        System.out.println("-------------------------------------------------------");
        for(StoreDTO dto: dtos) {
            System.out.println((i + 1) + ". " + dto.getStore_name() + "  \"" + dto.getStore_info() + " \"" +
                    "\n | 영업시간 : " + dto.getStore_time() + " \t| " + ((dto.getStore_state()) ? "(영업중)" : "(영업종료)") +
                    "\n | 주소 : " + dto.getStore_address() + "\t| 매장 전화번호 : " + dto.getStore_phone() +
                    "\n| 점주 : " + us.findUser(dto.getUser_id()).getUser_name() + " \t\t| 점주 전화번호 : " + dto.getUser_id());
            System.out.println("-------------------------------------------------------");
            i++;
        }

    }

    public int selectStore(List<StoreDTO> dtos, int selectNum)
    {
        if(dtos.size() < selectNum || selectNum < 1)
        {
            System.out.println("잘못된 가게번호입니다.");
            return -1;
        }
        else
            return dtos.get(selectNum - 1).getStore_id();
    }

    public void printStoreWithNumber(List<StoreDTO> store){
        int i= 1;
        System.out.println("====================================");
        for(StoreDTO stores: store){
            System.out.println("("+i+")");
            System.out.println("가게명 : "+stores.getStore_name() );
            System.out.println("가게주 : "+stores.getUser_id() );
            System.out.println("휴대폰번호 : "+stores.getStore_phone() );
            System.out.println("주소 : "+stores.getStore_address() );
            System.out.println("영업시간 : "+stores.getStore_time() );
            System.out.println("코멘트 : "+stores.getStore_info() );
            System.out.println("====================================");
            i++;
        }
    }

}
