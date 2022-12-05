package app.Function;

import persistence.dto.MenuDTO;
import persistence.dto.OrderDTO;
import service.MenuService;
import service.OrderService;
import service.StoreService;
import view.MenuView;
import view.OrderView;

import java.util.List;
import java.util.Scanner;

public class StorekeeperFunction {
    private Scanner sc ;
    private OrderService orderService;
    private OrderView orderView;
    private int store_id;

    public StorekeeperFunction() {
        this.sc = new Scanner(System.in);
        this.orderService = new OrderService();
        this.orderView = new OrderView();
    }

    public void selectOrder_store(int store_id)
    {
        orderView.printOrder(orderService.selectOrder_store(store_id));
    }

    public void deliveryFinish(int store_id)
    {
        List<OrderDTO> od= orderService.selectOrder_store_Delivery(store_id);
        orderView.printOrderWithNumber(od);
        if(od.size()==0)
        {
            System.out.println("배달중인 주문이 없습니다.");
        }
        else {
            System.out.println("==배달중인 주문 목록==");
            System.out.print("배달완료할 주문번호를 선택해 주세요 : ");
            int inputNum = sc.nextInt();
            int order_id =od.get(inputNum-1).getOrder_id();
            int result = orderService.updateOrderState_Complete(order_id);
            if( result == 1) System.out.println("배달 완료");
            else System.out.println("배달완료 실패");
        }
    }

    public void acceptOrder(int store_id)
    {
        List<OrderDTO> od= orderService.selectOrder_store_Waiting(store_id);
        System.out.println("==접수 대기중인 주문 목록==");
        orderView.printOrderWithNumber(od);
        System.out.print("접수할 주문번호를 선택해 주세요 : ");
        int inputNum = sc.nextInt();
        int order_id =od.get(inputNum-1).getOrder_id();
        System.out.print("주문 접수(1) , 거절(2) : ");
        int inputSelect = sc.nextInt();
        if( inputSelect ==1)
        {
            int result = orderService.updateOrderState_Delivery(order_id);
            if( result == 1) System.out.println("주문 접수 완료");
            else System.out.println("주문 접수 실패");
        }
        else if (inputSelect ==2)
        {
            int result = orderService.updateOrderState_Cancle(order_id);
            if( result == 1) System.out.println("주문 거절 완료");
            else System.out.println("주문 거절 실패");
        }
    }

    public void requestStoreAdd(String keeper_id)
    {
        StoreService ss = new StoreService();
        ss.storeAdd(keeper_id);
    }

    public void viewStoreAllMenu(int store_id)
    {
        MenuService ms = new MenuService();
        MenuView mv = new MenuView();
        List<MenuDTO> menuDTOS = ms.selectStoreMenu(store_id);
        mv.printStoreAllMenu_Keeper(menuDTOS, store_id);
    }


    public void menuUpdate(int store_id)
    {
        MenuService ms = new MenuService();
        MenuView mv = new MenuView();
        List<MenuDTO> menuDTOS = ms.selectStoreMenu(store_id);
        int inputNum, menu_id;

            mv.printStoreAllMenu_Keeper(menuDTOS, store_id);
            System.out.print("수정 할 메뉴를 선택해 주세요. (종료 시 0): ");
            inputNum = sc.nextInt();

            if(inputNum <= 0 || inputNum > ms.getStoreMenuNum(store_id))
                return;
            else {
                menu_id = menuDTOS.get(inputNum - 1).getMenu_id();
                System.out.println(menu_id);
                ms.menuUpdate(menu_id, store_id);

                mv.printOneMenu(ms.selectMenuById(menu_id));
            }
    }

    public void menuAdd(int store_id)
    {
        MenuService ms = new MenuService();
        MenuView mv = new MenuView();
        List<MenuDTO> menuDTOS = ms.selectStoreMenu(store_id);

        mv.printStoreAllMenu_Keeper(menuDTOS, store_id);
        ms.menuAdd(store_id);
    }
}