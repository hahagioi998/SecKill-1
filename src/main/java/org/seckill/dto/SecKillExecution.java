package org.seckill.dto;

import org.seckill.entity.SuccessKill;
import org.seckill.enums.SeckillStateEnum;

/**
 *
 * 封装秒杀结束后的结果
 * Created by thRShy on 2017/4/6.
 */
public class SecKillExecution {

    private long seckillId;

    //状态
    private int state;
    //状态标识
    private String stateInfo;

    //秒杀成功对象
    private SuccessKill successKill;

    //成功后的对象赋值
    public SecKillExecution(long seckillId, SeckillStateEnum stateEnum, SuccessKill successKill) {
        this.seckillId = seckillId;
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.successKill = successKill;
    }

    //秒杀失败对象的赋值
    public SecKillExecution(long seckillId, SeckillStateEnum stateEnum) {
        this.seckillId = seckillId;
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }

    public long getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(long seckillId) {
        this.seckillId = seckillId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public SuccessKill getSuccessKill() {
        return successKill;
    }

    public void setSuccessKill(SuccessKill successKill) {
        this.successKill = successKill;
    }

    @Override
    public String toString() {
        return "SecKillExecution{" +
                "seckillId=" + seckillId +
                ", state=" + state +
                ", stateInfo='" + stateInfo + '\'' +
                ", successKill=" + successKill +
                '}';
    }
}
