package app.service;

import persistence.dao.MyMenuOptionDAO;
import persistence.dao.MyOptionDAO;
import persistence.dao.MyStoreDAO;
import persistence.dto.MenuOptionDTO;
import persistence.dto.OptionDTO;
import persistence.dto.StoreDTO;

import java.util.List;
import java.util.Scanner;

public class OptionService {
    private final MyOptionDAO myOptionDAO;
    private final MyMenuOptionDAO myMenuOptionDAO;

    public OptionService()
    {
        this.myOptionDAO = new MyOptionDAO();
        this.myMenuOptionDAO = new MyMenuOptionDAO();
    }

    public List<OptionDTO> selectMenuOption(int menu_id)
    {
        List<OptionDTO> dtos = myOptionDAO.selectMenuOption(myMenuOptionDAO.selectMenuOptionId(menu_id));
        return dtos;
    }

    public String[] getOptionNames(List<OptionDTO> dtos, int[] options, int size)
    {
        String[] optionIds = new String[size];
        for(int i = 0; i < size; i++)
            optionIds[i] = dtos.get(options[i] - 1).getOption_name();
        return optionIds;
    }

    public long getOptionPrice(List<OptionDTO> dtos, int[] options, int size)
    {
        long resultOptionPrice = 0;
        for(int i = 0; i < size; i++)
            resultOptionPrice = resultOptionPrice + dtos.get(options[i] - 1).getOption_price();
        return resultOptionPrice;
    }

    public void optionAdd(int store_id)
    {
        Scanner sc = new Scanner(System.in);
        OptionDTO addOptionDTO = new OptionDTO();

        int checked_Store_Id = checkStore_id(store_id);
        String option_name = inputOption_name(sc);
        long option_price = inputOption_price(sc);

        addOptionDTO.setStore_id(checked_Store_Id);
        addOptionDTO.setOption_name(option_name);
        addOptionDTO.setOption_price(option_price);

        myOptionDAO.optionAdd(addOptionDTO);
    }
    public void deleteMenuOption(List<MenuOptionDTO> menuOptionDTOs)
    {
        for(MenuOptionDTO delMenuOptionDTO : menuOptionDTOs) {
            myMenuOptionDAO.deleteMenuOption(delMenuOptionDTO);
        }
    }

    private int checkStore_id(int store_id)
    {
        MyStoreDAO myStoreDAO = new MyStoreDAO();

        List<StoreDTO> storeDTOS = myStoreDAO.selectAllStoreId();
        for(StoreDTO storeDTO: storeDTOS) {
            if (store_id == storeDTO.getStore_id())
                return store_id;
        }

        System.out.println("매장이 존재하지 않음.");
        return -1;
    }

    private String inputOption_name(Scanner sc)
    {
        String input;
        System.out.print("옵션 이름을 입력해주세요.: ");
        input = sc.nextLine();
        return input;
    }
    private long inputOption_price(Scanner sc)
    {
        String input;

        while(true)
        {
            System.out.print("옵션 가격을 입력해주세요.: ");
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
}
