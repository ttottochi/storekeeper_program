package app.service;

import app.view.OptionView;
import persistence.dao.MyMenuDAO;
import persistence.dao.MyMenuOptionDAO;
import persistence.dao.MyOptionDAO;
import persistence.dao.MyStoreDAO;
import persistence.dto.MenuDTO;
import persistence.dto.MenuOptionDTO;
import persistence.dto.OptionDTO;
import persistence.dto.StoreDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MenuService
{
    private final MyMenuDAO myMenuDAO;
    private final MyStoreDAO myStoreDAO;

    public MenuService()
    {
        myMenuDAO = new MyMenuDAO();
        myStoreDAO = new MyStoreDAO();
    }

    public List<MenuDTO> findAll()
    {
        List<MenuDTO> all = myMenuDAO.selectAll();
        return all;
    }

    public List<MenuDTO> selectStoreMenu(int store_id)
    {
        List<MenuDTO> menu = myMenuDAO.selectStoreMenu(store_id);
        return menu;
    }

    public int getMenuId(List<MenuDTO> dtos, int selectNum)
    {
        if(dtos.size() < selectNum || selectNum < 1)
        {
            System.out.println("잘못된 메뉴번호 선택입니다.");
            System.out.println();
            return -1;
        }

        if(dtos.get(selectNum - 1).getMenu_quantity() == 0)
        {
            System.out.println("선택하신 메뉴의 수량이 소진되어 주문이 불가합니다.");
            System.out.println();
            return -1;
        }
        return dtos.get(selectNum - 1).getMenu_id();
    }

    public int getStoreMenuNum(int store_id)
    {
        return myMenuDAO.selectStoreMenuNum(store_id);
    }

    public void updateMenuQuantity(int menu_id)
    {
        myMenuDAO.updateMenuQuantity(menu_id);
    }

    public MenuDTO selectMenuById(int menu_id)
    {
        return myMenuDAO.selectMenuById(menu_id);
    }

    public List<MenuDTO> selectMenuCategoryList(int store_id)
    {
        List<MenuDTO> menuCategory = myMenuDAO.selectMenuCategoryList(store_id);
        return menuCategory;
    }

    public long getMenuPrice(List<MenuDTO> dtos, int selectMenuNum)
    {
        return dtos.get(selectMenuNum - 1).getMenu_price();
    }

    public String getMenuName(List<MenuDTO> dtos, int selectMenuNum)
    {
        return dtos.get(selectMenuNum - 1).getMenu_name();
    }

    public void menuAdd(int store_id)
    {
        Scanner sc = new Scanner(System.in);
        MenuDTO addMenuDTO = new MenuDTO();
        int menu_id;

        System.out.println("================메뉴 추가================");

        int checked_Store_Id = checkStore_id(store_id);

        String menu_name = inputMenu_name_add(sc);
        while(menu_name == "")
            menu_name = inputMenu_name_add(sc);

        long menu_price = inputMenu_price(sc);
        while(menu_price == -1)
            menu_price = inputMenu_price(sc);

        int menu_quantity = inputMenu_quantity(sc);
        while(menu_quantity == -1)
            menu_quantity = inputMenu_quantity(sc);

        String menu_category = inputMenu_category(sc);
        while(menu_category == "")
            menu_category = inputMenu_category(sc);

        System.out.println("========================================");


        addMenuDTO.setStore_id(checked_Store_Id);
            addMenuDTO.setMenu_name(menu_name);
            addMenuDTO.setMenu_price(menu_price);
            addMenuDTO.setMenu_quantity(menu_quantity);
            addMenuDTO.setMenu_category(menu_category);

        menu_id = myMenuDAO.menuAdd(addMenuDTO);

        addMenuOption(menu_id, store_id);

    }

    public void menuUpdate(int menu_id, int store_id)
    {
        Scanner sc = new Scanner(System.in);
        MenuDTO uptMenuDTO;
        uptMenuDTO = new MenuDTO();

        int optionUpdateInput;

        System.out.println("================메뉴 수정================");
        System.out.println("(변경하지 않는다면 enter)");

        String menu_name = inputMenu_name_mod(sc);
        long menu_price = inputMenu_price(sc);
        int menu_quantity = inputMenu_quantity(sc);
        String menu_category = inputMenu_category(sc);

        System.out.println("========================================");

        uptMenuDTO.setStore_id(store_id);
        uptMenuDTO.setMenu_id(checkMenu_id(menu_id, store_id));
        if(menu_name != "")
            uptMenuDTO.setMenu_name(menu_name);
        if(menu_price != -1)
            uptMenuDTO.setMenu_price(menu_price);
        if(menu_quantity != -1)
            uptMenuDTO.setMenu_quantity(menu_quantity);
        if(menu_category != "")
            uptMenuDTO.setMenu_category(menu_category);

        myMenuDAO.menuUpdate(uptMenuDTO);

        System.out.println("==============메뉴 옵션 수정==============");

        while(true)
        {
            System.out.println("해당 메뉴 옵션을 변경하시겠습니까?");
            System.out.println("0.아니오");
            System.out.println("1.옵션 추가");
            System.out.println("2.옵션 삭제");
            System.out.print(" : ");
            optionUpdateInput = sc.nextInt();

            if(optionUpdateInput == 0)
                break;

            switch (optionUpdateInput) {
                case 1:
                    addMenuOption(menu_id, store_id);
                    break;
                case 2:
                    deleteOption(menu_id, store_id);
                    break;
                default:
                    System.out.println("입력 형식이 올바르지 않습니다.");
                    break;
            }
        }

        System.out.println("========================================");

    }

    public void addMenuOption(int menu_id, int store_id) {
        String[] inputNums;
        String input;
        Scanner sc = new Scanner(System.in);
        MyOptionDAO myOptionDAO = new MyOptionDAO();
        MyMenuOptionDAO myMenuOptionDAO = new MyMenuOptionDAO();
        OptionView ov = new OptionView();
        List<OptionDTO> optionDTOS = myOptionDAO.selectAllByStoreId(store_id);
        List<MenuOptionDTO> menuOptionDTOS = new ArrayList<>();
        MenuOptionDTO addMODTO = new MenuOptionDTO();
        System.out.println("==============메뉴 옵션 추가==============");

        ov.printMenuAllOption(optionDTOS);

        System.out.print("추가할 옵션을 고르세요.(공백으로 구분, 중복 불가, 복수 선택 가능) : ");
        input = sc.nextLine();
        System.out.println();

        if(input != "") {
            inputNums = input.split(" ");

            for (String optionNum : inputNums) {
                addMODTO = new MenuOptionDTO();
                if (isdigit(optionNum)) {
                    addMODTO.setOption_id(optionDTOS.get(Integer.parseInt(optionNum) - 1).getOption_id());
                    addMODTO.setMenu_id(menu_id);
                    menuOptionDTOS.add(addMODTO);
                }
            }
        }


        myMenuOptionDAO.menuOptionAdd(menuOptionDupCheck(menuOptionDTOS, menu_id));

        System.out.println("========================================");
    }

    public void deleteOption(int menu_id, int store_id)
    {
        String[] inputNums;
        String input;
        Scanner sc = new Scanner(System.in);
        OptionView ov = new OptionView();
        OptionService os = new OptionService();
        MyOptionDAO myOptionDAO = new MyOptionDAO();
        List<MenuOptionDTO> menuOptionDTOS = new ArrayList<>();
        List<OptionDTO> optionDTOS = os.selectMenuOption(menu_id);
        MenuOptionDTO delMODTO = new MenuOptionDTO();

        System.out.println("==============메뉴 옵션 삭제==============");

        ov.printMenuAllOption(optionDTOS);

        System.out.print("삭제할 옵션을 고르세요.(공백으로 구분, 중복 불가, 복수 선택 가능) : ");
        input = sc.nextLine();
        System.out.println();


        if(input != "") {
            inputNums = input.split(" ");
            for (String optionNum : inputNums)
            {
                delMODTO = new MenuOptionDTO();
                if(isdigit(optionNum))
                {
                    delMODTO.setOption_id(optionDTOS.get(Integer.parseInt(optionNum)- 1).getOption_id());
                    delMODTO.setMenu_id(menu_id);
                    menuOptionDTOS.add(delMODTO);
                }
            }
        }

        os.deleteMenuOption(menuOptionDTOS);

        System.out.println("========================================");

    }

    private int checkStore_id(int store_id)
    {
        List<StoreDTO> storeDTOS = myStoreDAO.selectAllStoreId();
        for(StoreDTO storeDTO: storeDTOS) {
            if (store_id == storeDTO.getStore_id())
                return store_id;
        }

        System.out.println("매장이 존재하지 않음.");
        return store_id;
    }

    private int checkMenu_id(int menu_id, int store_id)
    {
        List<MenuDTO> menuDTOS = myMenuDAO.selectStoreMenu(store_id);
        for(MenuDTO menuDTO: menuDTOS) {
            if (menu_id == menuDTO.getMenu_id())
                return menu_id;
        }

        System.out.println("해당 가게에 다음과 같은 메뉴가 존재하지 않음.");
        return menu_id;
    }

    private String inputMenu_name_add(Scanner sc)
    {
        String input;

        while(true)
        {
            System.out.print("메뉴 이름을 입력해주세요.: ");
            input = sc.nextLine();

            if(nameDupCheck(input))
                return input;
        }
    }

    private String inputMenu_name_mod(Scanner sc)
    {
        String input;

        System.out.print("메뉴 이름을 입력해주세요.: ");
        input = sc.nextLine();

        return input;
    }

    private long inputMenu_price(Scanner sc)
    {
        String input;

        while(true)
        {
            System.out.print("메뉴 가격을 입력해주세요.: ");
            input = sc.nextLine();

            if(input.equals(""))
                return -1;

            if(isdigit(input))
            {
                return Long.parseLong(input);
            }

            else
                System.out.println("입력 형식에 맞지 않습니다. ");
        }

    }

    private int inputMenu_quantity(Scanner sc)
    {
        String input;

        while(true)
        {
            System.out.print("메뉴 재고를 입력해주세요.: ");
            input = sc.nextLine();

            if(input.equals(""))
                return -1;

            if(isdigit(input))
            {
                return Integer.parseInt(input);
            }

            else
                System.out.println("입력 형식에 맞지 않습니다. ");
        }
    }

    private String inputMenu_category(Scanner sc)
    {
        String input;
        System.out.print("메뉴 카테고리를 입력해주세요.: ");
        input = sc.nextLine();
        return input;
    }


    private boolean isdigit(String input)
    {
        char tmp;

        for(int i = 0; i<input.length(); i++)
        {
            tmp = input.charAt(i);
            if(!('0' <= tmp && tmp <= '9'))
                return false;
        }

        return true;
    }

    private boolean nameDupCheck(String input)
    {
        List<MenuDTO> menuDTOS = myMenuDAO.selectAll();

        for (MenuDTO menuDTO : menuDTOS) {
            if (input.equals(menuDTO.getMenu_name())) {
                return false;
            }
        }

        return true;
    }

    private List<MenuOptionDTO> menuOptionDupCheck(List<MenuOptionDTO> menuOptionDTOS, int menu_id)
    {
        OptionService os = new OptionService();
        List<OptionDTO> optionDTOS = os.selectMenuOption(menu_id);

        for(MenuOptionDTO menuOptionDTO : menuOptionDTOS)
        {
            for(OptionDTO optionDTO : optionDTOS)
            {
                if(menuOptionDTO.getOption_id() == optionDTO.getOption_id())
                    menuOptionDTOS.remove(menuOptionDTO);
            }
        }

        return menuOptionDTOS;
    }

}
