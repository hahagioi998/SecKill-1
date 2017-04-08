/**
 * Created by thRShy on 2017/4/7.
 */
//存放主要的交互逻辑 js 代码
//JavaScript    模块化 - 分包
var seckill={
    //封装秒杀相关ajax的ＵＲＬ
    URL : {

    },
    //验证手机号
    varidatePhone:function (phone) {
        if(phone && phone.length==11 && !isNaN(phone)){
            return true;
        }else{
            return false;
        }
    },
    //详情页秒杀逻辑
    detail : {
        //详情页初始化
        init : function (params) {
            //手机验证和登录 , 计时交互
            //规划我们的交互流程
            //cookie中的数据  在cookie中查找
            var killPhone = $.cookie('killPhone');
            var startTime=params['startTime'];
            var endTime=params['endTime'];
            var seckillId=params['seckillId'];
            if(!seckill.varidatePhone(killPhone)){
                //如果没有登录
                //绑定手机号
                //控制输出
                console.log("No phone");//TODO
                var killPhoneModal= $('#killPhoneModal');
                //显示弹出层
                killPhoneModal.modal({
                    show:true,//显示弹窗
                    backdrop:'static',//禁止位置关闭
                    keyboard:false
                });
                console.log("弹窗");//TODO
                $('#killPhoneBtn').click(function () {
                    console.log("点击了按钮");//TODO
                    var inputPhone = $('#killPhoneKey').val();
                    console.log('inputPhone='+inputPhone);//TODO
                    if(seckill.varidatePhone(inputPhone)){
                        //写入cookie
                        $.cookie('killPhone',inputPhone,{expires:7,path:'/seckill'});
                        //刷新页面
                        window.location.reload();
                    }else{
                        console.log("Phone error");//TODO
                        $('#killPhoneMessage').hide().html('<label class="label label-danger">手机号错误！</label>').show(300);
                    }
                });
            }
            //已经登录
        }
    }
}