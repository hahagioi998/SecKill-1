package org.seckill.web;

import org.seckill.dto.Exposer;
import org.seckill.dto.SecKillExecution;
import org.seckill.dto.SeckillResult;
import org.seckill.entity.SecKill;
import org.seckill.enums.SeckillStateEnum;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SecKillCloseException;
import org.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * Created by thRShy on 2017/4/6.
 */

@Controller //@Service @Component
@RequestMapping("/seckill") //url:/模块/资源/{id}/细分
public class SeckillController {

    private final Logger logger= LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillService seckillService;

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public String list(Model model){
        //list.jsp + model =ModelAndView
        //获取列表页
        List<SecKill> list=seckillService.getSeckillList();
        model.addAttribute("list",list);
        return "list";
    }

    @RequestMapping(value = "/{seckillId}/detail",method = RequestMethod.GET)
    public String detail(@PathVariable("seckillId") Long seckillId, Model model) {

        if(seckillId==null){
            return "redirect:/seckill/list";
        }
        SecKill secKill=seckillService.getById(seckillId);
        if(secKill==null){
            return "forward:/seckill/list";
            //return "redirect:/seckill/list";
        }
        model.addAttribute("seckill",secKill);
        return "detail";
    }

    //ajax 返回json
    @RequestMapping(value = "/{seckillId}/exposer",
            method = RequestMethod.POST,
            produces = {"application/json;charset=utf-8"})
    @ResponseBody //返回数据封装为json类型
    public SeckillResult<Exposer> exposer(@PathVariable Long seckillId){
        SeckillResult<Exposer> result;
        try{
            Exposer exposer=seckillService.exportSeckillUrl(seckillId);
            result=new SeckillResult<Exposer>(true,exposer);
        }catch(Exception e) {
            logger.error(e.getMessage(), e);
            result = new SeckillResult<Exposer>(false, e.getMessage());
        }
        return result;
    }

    //执行秒杀
    @RequestMapping(value = "/{seckillId}/{md5}/excution",
            method = RequestMethod.POST,
            produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public SeckillResult<SecKillExecution> execute(@PathVariable("seckillId") Long seckillId,
                                                    @PathVariable("md5") String md5,
                                                    @CookieValue(value = "killPhone",required = false) Long phone){
        //Springmvc valid
        if(phone==null){
            return new SeckillResult<SecKillExecution>(false,"未登录");
        }
        SeckillResult<SecKillExecution> result;
        try{
            SecKillExecution secKillExecution=seckillService.executeSeckillprocedure(seckillId,phone,md5);
            //SecKillExecution secKillExecution=seckillService.executeSeckill(seckillId,phone,md5);
            return new SeckillResult<SecKillExecution>(true,secKillExecution);
        }catch (RepeatKillException e) {
            SecKillExecution s=new SecKillExecution(seckillId, SeckillStateEnum.REPEAT_KILL);
            return new SeckillResult<SecKillExecution>(true,s);
        }catch (SecKillCloseException e){
            SecKillExecution s=new SecKillExecution(seckillId,SeckillStateEnum.END);
            return new SeckillResult<SecKillExecution>(true,s);
        }
        catch (Exception e){
            logger.error(e.getMessage(),e);
            SecKillExecution secKillExecution=new SecKillExecution(seckillId,SeckillStateEnum.INNER_ERROR);
            return new SeckillResult<SecKillExecution>(true,secKillExecution);
        }
    }

    @RequestMapping(value = "/time/now",method = RequestMethod.GET)
    @ResponseBody
    public SeckillResult<Long> time(){
        Date now =new Date();
        return new SeckillResult(true,now.getTime());

    }
}
