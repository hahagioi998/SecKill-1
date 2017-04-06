package org.seckill.enums;

/**
 *
 * 使用枚举表示String常量字段
 * Created by thRShy on 2017/4/6.
 */
public enum SeckillStateEnum {

    SUCCESS(1,"秒杀成功"),
    END(0,"秒杀结束"),
    REPEAT_KILL(-1,"重复秒杀"),
    DATA_REWRITE(-3,"数据篡改"),
    INNER_ERROR(-2,"系统异常");

    private int state;
    private String stateInfo;

    SeckillStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public static SeckillStateEnum stateOf(int index){
        for(SeckillStateEnum state:values()){
            if(state.getState()==index)
                return state;
        }
        return null;
    }
}
