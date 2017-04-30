package org.seckill.entity;

/**
 * Created by thRShy on 2017/4/17.
 */
public class Seller {

    private int sellerId;

    private String sellerName;

    private String sellerPassword;

    private long sellerPhone;

    private String sellerAddr;

    public int getSellerId() {
        return sellerId;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getSellerPassword() {
        return sellerPassword;
    }

    public void setSellerPassword(String sellerPassword) {
        this.sellerPassword = sellerPassword;
    }

    public long getSellerPhone() {
        return sellerPhone;
    }

    public void setSellerPhone(long sellerPhone) {
        this.sellerPhone = sellerPhone;
    }

    public String getSellerAddr() {
        return sellerAddr;
    }

    public void setSellerAddr(String sellerAddr) {
        this.sellerAddr = sellerAddr;
    }
}
