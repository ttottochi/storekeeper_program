package persistence;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
//@Alias("orderMenuList")

public class OrderMenuDTO implements Serializable {
    private String orderMenu_id;
    private String menu_name;
    private String order_num;

    public OrderMenuDTO(String orderMenu_id, String order_num, String menu_name)
    {
        this.orderMenu_id = orderMenu_id;
        this.order_num = order_num;
        this.menu_name = menu_name;
    }

    public String getOrder_num() {
        return order_num;
    }

    public void setOrder_num(String order_num) {
        this.order_num = order_num;
    }

    public void setMenu_name(String menu_name) {
        this.menu_name = menu_name;
    }

    public String getOrderMenu_id() {
        return orderMenu_id;
    }

    public void setOrderMenu_id(String orderMenu_id) {
        this.orderMenu_id = orderMenu_id;
    }

    public String getMenu_name() {
        return menu_name;
    }
}
