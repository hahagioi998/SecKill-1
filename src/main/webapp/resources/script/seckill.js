/**
 * Created by thRShy on 2017/4/7.
 */
//存放主要的交互逻辑 js 代码
//JavaScript    模块化 - 分包
var seckill={
    //封装秒杀相关ajax的ＵＲＬ
    URL : {
        now :function () {
            return '/seckill/time/now'
        },
        exposer:function (seckillId) {
            return '/seckill/'+seckillId+'/exposer';
        },
        excution:function (seckillId,md5) {
            return '/seckill/'+seckillId+'/'+md5+'/excution';

        }
    },
    //验证手机号
    varidatePhone:function (phone) {
        if(phone && phone.length==11 && !isNaN(phone)){
            return true;
        }else{
            return false;
        }
    },
    handleSeckillKill:function (seckillId,node) {
        //处理秒杀逻辑
        node.hide().html('<button class="btn btn-primary btn-lg" id="killBtn">开始秒杀</button>');
        $.post(seckill.URL.exposer(seckillId),{},function (result) {
            //在回调函数中，执行交互流程
            if(result && result['success']){
                var exposer=result['data'];
                if(exposer['exposed']){
                    //获取秒杀地址
                    var md5=exposer['md5'];
                    var killUrl=seckill.URL.excution(seckillId,md5);
                    console.log("Url="+killUrl);
                    //绑定一次点击事件
                    $('#killBtn').one('click',function () {
                        //执行秒杀请求的操作
                        //1、禁用按钮
                        $(this).addClass('disabled');
                        //2、发送秒杀请求
                        $.post(killUrl,{},function (result) {
                            if(result&&result['success']){
                                var killResult=result['data'];
                                var state=killResult['state'];
                                var stateInfo=killResult['stateInfo'];
                                var retur="  返回首页";
                                //显示秒杀结果
                                node.html('<a class="btn btn-info btn-lg" href="/seckill/list">'+stateInfo+retur+'</a>');
                            }
                        });
                    });
                    node.show();
                }else{
                    //未开启
                    //PC运行过久之后，时间会有偏差，所以时间和服务器端可能会有区别
                    var now=exposer['now'];
                    var start=exposer['start'];
                    var end=exposer['end'];
                    seckill.countdown(seckillId,now,start,end);
                }
            }else{
                console.log("result= "+result);
            }
        });
    },
    //时间判断
    countdown:function (seckillId,nowTime,startTime,endTime) {
        var seckillBox=$('#seckill-box');
        //时间判断
        if(nowTime>endTime){
            seckillBox.html('秒杀结束');
        }else if(nowTime<startTime){
            //seckillBox.html('秒杀未开始');
            //计时 - 事件绑定
            var killTime=new Date(startTime + 1000);

            //event 回调函数
            seckillBox.countdown(killTime,function (event) {
                //时间格式
                var format=event.strftime('秒杀倒计时：%D天 %H时 %M分 %S秒');
                seckillBox.html(format);
            }).on('finish.countdown',function () {
                //获取秒杀地址，控制逻辑，执行秒杀
                seckill.handleSeckillKill(seckillId,seckillBox);
            });
            var goIndex=$('#go-index');
            $('#go-index').hide().html('<a class="btn btn-info btn-lg" href="/seckill/list">去看看其他秒杀？</a>').show(1500);
        }else{
            //seckillBox.html('秒杀进行中');
            seckill.handleSeckillKill(seckillId,seckillBox);
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
            //计时交互
            $.get(seckill.URL.now(),{},function (result) {
                //获取时间
                if(result&&result['success']){
                    var nowTime=result['data'];
                    //时间判断
                    seckill.countdown(seckillId,nowTime,startTime,endTime);
                }else{
                    console.log("time="+result);
                }
            })

        }
    }
}