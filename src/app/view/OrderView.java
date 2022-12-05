package app.view;

import persistence.dto.OrderDTO;
import persistence.dto.OrderMenuDTO;
import persistence.dto.OrderOptionDTO;

import java.util.List;

public class OrderView {

    public void printAll(List<OrderDTO> dtos){
        for(OrderDTO dto: dtos){
            System.out.println("dto.toString() = " + dto.toString());
        }
    }

    public void printOrder(List<OrderDTO> od){
        for(OrderDTO ods: od){
            List<OrderMenuDTO> orderMenuList = ods.getOrderMenuList();
            List<OrderOptionDTO> orderOptionList = ods.getOrderOptionList();

            System.out.print("회원: " + ods.getUser_id() );
            for(OrderMenuDTO odms : orderMenuList)
                System.out.print(", " + odms.getMenu_name());

            for(OrderOptionDTO  odos : orderOptionList)
                System.out.print(", " + odos.getOption_name());

            System.out.println(", "+ods.getOrder_price() + ", " + ods.getOrder_state());
        }
    }

    public void printOrderWithNumber(List<OrderDTO> od){
        int i= 1;
        for(OrderDTO ods: od){
            List<OrderMenuDTO> orderMenuList = ods.getOrderMenuList();
            List<OrderOptionDTO> orderOptionList = ods.getOrderOptionList();

            System.out.print(i+" | 회원 :" + ods.getUser_id() );
            for(OrderMenuDTO odms : orderMenuList)
            {
                System.out.print(", " + odms.getMenu_name());
            }
            for(OrderOptionDTO  odos : orderOptionList)
            {
                System.out.print(", " + odos.getOption_name());
            }
            System.out.println(", "+ods.getOrder_price() + ", " + ods.getOrder_state());
            i++;
        }
    }
}
