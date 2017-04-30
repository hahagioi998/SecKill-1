package org.seckill.entity;

/**
 * Created by thRShy on 2017/4/17.
 */
public class Seckill_Seller {

    private SecKill secKill;

    private Seller seller;

    private String seckillDes;

    public SecKill getSecKill() {
        return secKill;
    }

    public void setSecKill(SecKill secKill) {
        this.secKill = secKill;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public String getSeckillDes() {
        return seckillDes;
    }

    public void setSeckillDes(String seckillDes) {
        this.seckillDes = seckillDes;
    }
}
