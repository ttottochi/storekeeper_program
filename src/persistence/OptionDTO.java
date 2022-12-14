package persistence;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import protocol.MySerializableClass;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

@Getter
@Setter
@ToString
public class OptionDTO implements MySerializableClass{
    private int option_id;
    private long option_price;
    private String option_name;
    private int store_id;


    public OptionDTO()
    {

    }

    public OptionDTO(int option_id, long option_price, String option_name, int store_id) {
        this.option_id = option_id;
        this.option_price = option_price;
        this.option_name = option_name;
        this.store_id = store_id;
    }

    public int getOption_id() {
        return option_id;
    }

    public void setOption_id(int option_id) {
        this.option_id = option_id;
    }

    public long getOption_price() {
        return option_price;
    }

    public void setOption_price(long option_price) {
        this.option_price = option_price;
    }

    public String getOption_name() {
        return option_name;
    }

    public void setOption_name(String option_name) {
        this.option_name = option_name;
    }

    public static OptionDTO read(DataInputStream bodyReader) throws IOException
    {
        int option_id = bodyReader.readInt();
        long option_price = bodyReader.readLong();
        String option_name = bodyReader.readUTF();
        int store_id = bodyReader.readInt();

        return new OptionDTO(option_id, option_price, option_name, store_id);
    }

    @Override
    public byte[] getBytes() throws IOException {
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(buf);

        dos.writeInt(option_id);
        dos.writeLong(option_price);
        dos.writeUTF(option_name);
        dos.writeInt(store_id);
        return buf.toByteArray();
    }
}
