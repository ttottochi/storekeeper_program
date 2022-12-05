package app.view;

import persistence.dto.OptionDTO;

import java.util.List;

public class OptionView {

    public List<OptionDTO> printMenuAllOption(List<OptionDTO> dtos) //이거 그냥 printAllOption으로 바꿔도 될듯
    {
        System.out.println("=================옵션 목록===================");
        if(dtos.size() == 0)
            System.out.println("해당 메뉴에 옵션이 없습니다.");

        else
        {
            int i = 0;
            for(OptionDTO dto: dtos) {
                System.out.println((i + 1) + ". " + dto.getOption_name() + " | " + dto.getOption_price() + "원");
                i++;
            }
        }
        System.out.println("============================================");
        return dtos;
    }

}
