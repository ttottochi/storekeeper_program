package app.view;

import app.service.MenuService;
import persistence.dto.MenuDTO;

import java.util.List;

public class MenuView {

    public List<MenuDTO> printStoreAllMenu(List<MenuDTO> dtos, int store_id)
    {
        MenuService ms = new MenuService();
        List<MenuDTO> menuCategory = ms.selectMenuCategoryList(store_id);
        int i = 0;

        System.out.println("================메뉴 목록================");

        for(MenuDTO menuCat : menuCategory)
        {

            System.out.println(menuCat.getMenu_category());
            System.out.println("---------------------------------------");
            for(MenuDTO dto: dtos) {
                if(dto.getMenu_category().equals(menuCat.getMenu_category())) {
                    System.out.println((i + 1) + ". " + dto.getMenu_name() + " | " + dto.getMenu_price() + "원");
                    i++;
                }
            }
            System.out.println("---------------------------------------\n");

        }

        System.out.println("========================================");

        return dtos;
    }

    public List<MenuDTO> printStoreAllMenu_Keeper(List<MenuDTO> dtos, int store_id)
    {
        MenuService ms = new MenuService();
        List<MenuDTO> menuCategory = ms.selectMenuCategoryList(store_id);
        int i = 0;

        System.out.println("================메뉴 목록================");

        for(MenuDTO menuCat : menuCategory)
        {
            System.out.println(menuCat.getMenu_category());
            System.out.println("---------------------------------------");
            for(MenuDTO dto: dtos) {
                if(dto.getMenu_category().equals(menuCat.getMenu_category())) {
                    System.out.println((i + 1) + ". " + dto.getMenu_name() + " | " + dto.getMenu_price() + "원" + " | 재고 : " + dto.getMenu_quantity());
                    i++;
                }
            }
            System.out.println("---------------------------------------\n");
        }

        System.out.println("========================================");

        return dtos;
    }

    public void printOneMenu(MenuDTO menuDTO)
    {
        System.out.println("==============수정 메뉴 확인=============");

        System.out.println(" | 카테고리 : " + menuDTO.getMenu_category() + " | " + menuDTO.getMenu_name() + " | " + menuDTO.getMenu_price() + "원" + " | 재고 : " + menuDTO.getMenu_quantity());

        System.out.println("=======================================");

    }
}
