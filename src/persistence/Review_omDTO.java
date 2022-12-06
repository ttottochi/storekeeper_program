package persistence;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import protocol.MySerializableClass;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

@Getter
@Setter
@ToString
public class Review_omDTO implements MySerializableClass {
    private int review_id;
    private int order_id;
    private int store_id;
    private String menu_name;
    private String review_content;
    private int review_rate;

    private int review_comment;

    public Review_omDTO(int review_id, int order_id, int store_id, String menu_name, String review_content, int review_rate, int review_comment) {
        this.review_id = review_id;
        this.order_id = order_id;
        this.store_id = store_id;
        this.menu_name = menu_name;
        this.review_content = review_content;
        this.review_rate = review_rate;
        this.review_comment = review_comment;
    }


    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getStore_id() {
        return store_id;
    }

    public void setStore_id(int store_id) {
        this.store_id = store_id;
    }

    public String getMenu_name() {
        return menu_name;
    }

    public void setMenu_name(String menu_name) {
        this.menu_name = menu_name;
    }

    public String getReview_content() {
        return review_content;
    }

    public void setReview_content(String review_content) {
        this.review_content = review_content;
    }

    public int getReview_rate() {
        return review_rate;
    }

    public void setReview_rate(int review_rate) {
        this.review_rate = review_rate;
    }

    public int getReview_comment() {return review_comment;}
    public void setReview_comment(int review_comment) { this.review_comment = review_comment;};

    @Override
    public byte[] getBytes() throws IOException {
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(buf);

        dos.writeInt(review_id);
        dos.writeInt(order_id);
        dos.writeInt(store_id);
        dos.writeUTF(menu_name);
        dos.writeUTF(review_content);
        dos.writeInt(review_rate);
        dos.writeInt(review_comment);
        return buf.toByteArray();
    }


}