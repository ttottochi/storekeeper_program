package app.service;

import persistence.dao.MyStoreDAO;
import persistence.dao.MyUserDAO;
import persistence.dto.StoreDTO;
import persistence.dto.UserDTO;

import java.util.List;
import java.util.Scanner;

public class StoreService
{
    private final MyStoreDAO myStoreDAO;
    private final MyUserDAO myUserDAO;

    public StoreService()
    {
        myStoreDAO = new MyStoreDAO();
        myUserDAO = new MyUserDAO();
    }

    public List<StoreDTO> findAll()
    {
        List<StoreDTO> all = myStoreDAO.selectAll();
        return all;
    }

    public List<StoreDTO> selectAllStoreNameAndId()
    {
        List<StoreDTO> nameAid = myStoreDAO.selectAllStoreNameAndId();
        return nameAid;
    }

    public void storeAdd(String keeper_id)
    {
        Scanner sc = new Scanner(System.in);
        StoreDTO addStoreDTO = new StoreDTO();

        System.out.println("================가게 요청================");

        String user_id = checkUser_id(sc, keeper_id);
        String store_name = inputStore_Name(sc);
        String store_address = inputStore_Address(sc);
        String store_phone = inputStore_Phone(sc);
        int store_category = inputStore_Category(sc);
        String store_time = inputStore_Time(sc);
        String store_info = inputStore_info(sc);

        System.out.println("=======================================");

        addStoreDTO.setUser_id(user_id);
        addStoreDTO.setStore_name(store_name);
        addStoreDTO.setStore_address(store_address);
        addStoreDTO.setStore_phone(store_phone);
        addStoreDTO.setStore_category(store_category);
        addStoreDTO.setStore_time(store_time);
        addStoreDTO.setStore_info(store_info);

        myStoreDAO.storeAdd(addStoreDTO);
    }

    private String checkUser_id(Scanner sc, String keeper_id)
    {
        UserService us = new UserService();
        List<UserDTO> userDTOS = myUserDAO.selectAllUserid();
        String input;

        while(true)
        {
            System.out.print("사장님 아이디를 입력해주세요 : ");
            input = sc.nextLine();
            System.out.println();

            if(us.idCheck(input) && input.equals(keeper_id))
                return input;

            System.out.println("아이디가 유효하지 않습니다.");

        }
    }
    private String inputStore_Phone(Scanner sc)
    {
        String input = new String();

        while(true)
        {
            System.out.print("가게 전화번호를 입력해주세요.(형식 : 01012345678) : ");
            input = sc.nextLine();
            System.out.println();
            if(isdigit(input))
                return input;
            else
                System.out.println("입력 형식에 맞지 않습니다.");
        }
    }

    private String inputStore_Name(Scanner sc)
    {
        String input;
        while(true)
        {
            System.out.print("가게 이름를 입력해주세요 : ");
            input = sc.nextLine();

            if (!input.equals(""))
                return input;
        }
    }

    private String inputStore_Address(Scanner sc)
        {
            String input;

            while(true)
            {
                System.out.print("가게 주소를 입력해주세요 : ");
                input = sc.nextLine();

                if (!input.equals(""))
                    return input;
            }
        }
    private int inputStore_Category(Scanner sc)
        {
            String input;
            while(true)
            {
                System.out.print("가게 카테고리를 입력해주세요. : ");
                input = sc.nextLine();

                if (isdigit(input))
                    return Integer.parseInt(input);
                else
                    System.out.print("입력 값이 형식에 맞지 않습니다.");
            }
        }
    private String inputStore_Time(Scanner sc)
        {
            String store_time = "",open_input, close_input;
            while(true)
            {
                System.out.print("가게 오픈시간을 입력해주세요(00:00): ");
                open_input = sc.nextLine();

                if (isTime(open_input)) {
                    while(true)
                    {
                        System.out.print("가게 마감시간을 입력해주세요(00:00): ");
                        close_input = sc.nextLine();
                        if(isTime(close_input) && compareTime(open_input, close_input)) {
                            store_time += open_input + "~" + close_input;
                            return store_time;
                        }
                        else {
                            System.out.println("입력 값이 형식에 맞지 않습니다. (00:00) ");
                            break;
                        }

                    }

                }
                else
                    System.out.println("입력 값이 형식에 맞지 않습니다. (00:00) ");

            }
        }
    private String inputStore_info(Scanner sc)
        {
            System.out.print("가게 한 줄 소개를 입력해주세요 : ");
            return sc.nextLine();
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

    private boolean isTime(String input)
    {
        String[] temp = input.split(":");
        for(int i = 0; i < temp.length; i++)
        {
            if(!(isdigit(temp[i]) && temp[i].length() == 2))
                return false;
        }
        if(0 <= Integer.parseInt(temp[0]) && Integer.parseInt(temp[0]) <= 24)
            if(0 <= Integer.parseInt(temp[1]) && Integer.parseInt(temp[1]) < 60)
                return true;

        return false;
    }

    private boolean compareTime(String open, String close)
    {
        String[] open_temp = open.split(":");
        String[] close_temp = close.split(":");

        if(Integer.parseInt(open_temp[0]) < Integer.parseInt(close_temp[0]))
            return true;
        else if(Integer.parseInt(open_temp[0]) == Integer.parseInt(close_temp[0]))
        {
            if(Integer.parseInt(open_temp[1]) <= Integer.parseInt(close_temp[1]))
                return true;
        }

        return false;
    }
    public List<StoreDTO> selectStore_WaitingAccept()
    {
        List<StoreDTO> list = myStoreDAO.selectStore_WaitingAccept();
        return list;
    }

    public List<StoreDTO> selectStore_Accepted()
    {
        List<StoreDTO> list = myStoreDAO.selectStore_Accepted();
        return list;
    }

    public int updateStore_Accept(int store_id)
    {
        int result =myStoreDAO.updateStore_Accept(store_id);
        return result;
    }

    public int deleteStore(int store_id)
    {
        int result =myStoreDAO.deleteStore(store_id);
        return result;
    }
}
