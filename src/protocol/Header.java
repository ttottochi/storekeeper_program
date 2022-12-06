package protocol;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Header implements MySerializableClass {

    public final static byte UNUSED = 0;

    /* TYPE */
    public final static byte TYPE_START = 0x00;
    public final static byte TYPE_REQ = 0x01;
    public final static byte TYPE_ANS = 0x02;
    public final static byte TYPE_RES = 0x03;
    public final static byte TYPE_QUIT = 0x04;

    /* CODE */
    //TYPE : 0x00 (START) 일 때
    public final static byte CODE_SIGN_UP = 0x01;
    public final static byte CODE_LOG_IN = 0x02;
    public final static byte CODE_INFO_AND_PW_FIX= 0x03;
    public final static byte CODE_STORE_LOOKUP = 0x04;
    public final static byte CODE_FOOD_ORDER = 0x05;
    public final static byte CODE_ORDER_CANCEL = 0x06;
    public final static byte CODE_ORDER_LIST_LOOKUP = 0x07;
    public final static byte CODE_WRITE_REVIEW = 0x08;

    public final static byte CODE_ORDER_ACCEPT = 0x30;

    //TYPE : 0x01(REQUEST) or 0x02(ANSWER) 일 때
    public final static byte CODE_USER_ID = 0x01;
    public final static byte CODE_USER_PW = 0x02;public final static byte CODE_USER_NAME = 0x03;
    public final static byte CODE_USER_ADDRESS = 0x04;
    public final static byte CODE_USER_CATEGORY = 0x05; public final static byte CODE_CUSTOMER_PHONE = 0x06;
    public final static byte CODE_STORE_ID = 0x08; public final static byte CODE_STORE_ADDRESS = 0x0A;
    public final static byte CODE_STORE_PHONE = 0x0B; public final static byte CODE_STORE_CATEGORY = 0x0C;
    public final static byte CODE_STORE_STATE = 0x0D; public final static byte CODE_STORE_OPEN = 0x0E;
    public final static byte CODE_STORE_CLOSE = 0x0F; public final static byte CODE_STORE_INTRODUCE = 0x10;
    public final static byte CODE_MENU_LIST = 0x11; public final static byte CODE_MENU_ID = 0x12;
    public final static byte CODE_MENU_NAME = 0x13; public final static byte CODE_MENU_PRICE = 0x14;
    public final static byte CODE_MENU_STOCK = 0x15; public final static byte CODE_MENU_CATEGORY = 0x16;
    public final static byte CODE_OPTION_REQ = 0x17; public final static byte CODE_OPTION_LIST = 0x18;
    public final static byte CODE_OPTION_ID = 0x19; public final static byte CODE_OPTION_PRICE = 0x32;
    public final static byte CODE_ORDER_LIST = 0x1A; public final static byte CODE_ORDER_ID = 0x1B;
    public final static byte CODE_ORDER_STATE = 0x1D; public final static byte CODE_ORDER_MENU_LIST = 0x1E;
    public final static byte CODE_ORDER_PRICE = 0x1F; public final static byte CODE_GRADE = 0x20;
    public final static byte CODE_REVIEW_CONTENT = 0x21; public final static byte CODE_REVIEW_LIST = 0x22;
    public final static byte CODE_REVIEW_ID = 0x23; public final static byte CODE_REVIEW_ANS = 0x24;
    public final static byte CODE_APPROVAL = 0x25; public final static byte CODE_DISCOUNT_RATE = 0x26;
    public final static byte CODE_MODIFY_LIST =0x27 ;   public final static byte CODE_MODIFY_INFO = 0x28;
    public final static byte CODE_CHANGE_LIST = 0x29; public final static byte CODE_CATEGORY_INFO = 0x2A;
    public final static byte CODE_SELECTED_CATEGORY = 0x2B ;   public final static byte CODE_KEEPER_SALES = 0x2C;
    public final static byte CODE_STORE_NUMBER_OF_SALE = 0x2D;   public final static byte CODE_STORE_SALES = 0x2E;
    public final static byte CODE_STORE_OPENING_HOURS = 0x2F ;   public final static byte CODE_STATISTICS_LIST = 0x30;
    public final static byte CODE_STORE_LIST = 0x40;
    public final static byte CODE_FIXED_ORDER_DTO = 0x41;
    public final static byte CODE_USER_DTO = 0x50;
    public final static byte CODE_USER_INFO = 0x51;
    public final static byte CODE_STORE_INFO = 0x52;
    public final static byte CODE_STORE_TIME = 0x53;
    public final static byte CODE_INSERT_ORDER = 0x54;
    public final static byte CODE_UPDATE_MENU_QUANTITY = 0x55;
    public final static byte CODE_INSERT_ORDER_MENU = 0x56;
    public final static byte CODE_INSERT_ORDER_OPTION = 0x57;


    //TYPE : 0x03(RESULT) 일 때
    public final static byte CODE_SUCCESS = 0x01;
    public final static byte CODE_FAIL = 0x02;


    public byte type;
    public byte code;
    public int length;

    public Header(byte type, byte code, int length) {
        this.type = type;
        this.code = code;
        this.length = length;
    }

    public static Header readHeader(DataInputStream dis) throws IOException {

        byte type = dis.readByte();
        byte code = dis.readByte();
        int length = dis.readInt();

        return new Header(type, code, length);

    }


    @Override
    public byte[] getBytes() throws IOException {

        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(buf);

        dos.writeByte(type);
        dos.writeByte(code);
        dos.writeInt(length);

        return buf.toByteArray();


    }
}
