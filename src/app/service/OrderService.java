package app.service;

import persistence.dao.MyOrderDAO;
import persistence.dao.MyOrderMenuDAO;
import persistence.dto.OrderDTO;
import persistence.dto.OrderMenuDTO;
import persistence.dto.OrderOptionDTO;

import java.time.LocalDateTime;
import java.util.List;

public class OrderService {
    private final MyOrderDAO orderDAO;
    private final MyOrderMenuDAO orderMenuDAO;

    public OrderService()
    {
        orderDAO = new MyOrderDAO();
        orderMenuDAO = new MyOrderMenuDAO();
    }

    public List<OrderDTO> selectAll()
    {
        List<OrderDTO> orderDTOS = orderDAO.selectAll();
        return orderDTOS;
    }

    public void insertOrder(String user_id, int store_id, long order_price, LocalDateTime order_orderTime, int menu_id, String order_num)
    {
        MenuService menuS = new MenuService();
        OrderDTO od = new OrderDTO(user_id, store_id, order_price, order_orderTime, order_num);

        orderDAO.insertOrder(od); //오더테이블에 주문 넣기
        menuS.updateMenuQuantity(menu_id); //메뉴 개수 수정
    }

    public void insertOrderMenuAndOption(String order_num, String menu_name, int i, String[] optionNames)
    {
        String orderMenu_id = order_num + "-" + i;
        OrderMenuDTO omd = new OrderMenuDTO(orderMenu_id, order_num, menu_name);

        orderMenuDAO.insertOrderMenu(omd); //오더메뉴 테이블에 메뉴명 넣기
        for(int j = 0; j < optionNames.length; j++)
            insertOrderOption(orderMenu_id, optionNames[j]);
    }

    public void insertOrderOption(String orderMenu_id, String option_name)
    {
        OrderOptionDTO ood = new OrderOptionDTO(orderMenu_id, option_name);
        orderMenuDAO.insertOrderOption(ood);
    }

    public List<OrderDTO> selectOrder_store(int store_id)
    {
        List<OrderDTO> orderDTOS = orderDAO.selectOrder_store(store_id);

        return orderDTOS;
    }

    public List<OrderDTO> selectOrder_store_Waiting(int store_id)
    {
        List<OrderDTO> orderDTOS = orderDAO.selectOrder_store_Waiting(store_id);

        return orderDTOS;
    }

    public List<OrderDTO> selectOrder_store_Delivery(int store_id)
    {
        List<OrderDTO> orderDTOS = orderDAO.selectOrder_store_Delivery(store_id);

        return orderDTOS;
    }

    public List<OrderDTO> selectOrder_customer(String user_id)
    {
        List<OrderDTO> orderDTOS = orderDAO.selectOrder_customer(user_id);

        return orderDTOS;
    }

    public List<OrderDTO> selectAllOrder_customer(String user_id)
    {
        List<OrderDTO> orderDTOS = orderDAO.selectAllOrder_customer(user_id);

        return orderDTOS;
    }

    public int updateOrderState_Complete(int order_id)
    {
        OrderDTO orderDTO = new OrderDTO(order_id);
        int result =orderDAO.updateOrderState_Complete(order_id);
        return result;
    }

    public int updateOrderState_Cancle(int order_id)
    {
        OrderDTO orderDTO = new OrderDTO(order_id);
        int result =orderDAO.updateOrderState_Cancle(order_id);
        return result;
    }

    public int updateOrderState_Delivery(int order_id)
    {
        OrderDTO orderDTO = new OrderDTO(order_id);
        int result =orderDAO.updateOrderState_Delivery(order_id);
        return result;
    }




}
