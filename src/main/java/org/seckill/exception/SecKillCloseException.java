package org.seckill.exception;

/**
 * 秒杀关闭异常
 * Created by thRShy on 2017/4/6.
 */
public class SecKillCloseException extends SeckillException{
    public SecKillCloseException(String message) {
        super(message);
    }

    public SecKillCloseException(String message, Throwable cause) {
        super(message, cause);
    }
}
