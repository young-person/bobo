$(document).keypress(function (e) {
    // 回车键事件
    if (e.which == 13) {
        $('input[type="button"]').click();
    }
});
//粒子背景特效
$('body').particleground({
    dotColor: '#E8DFE8',
    lineColor: '#133b88'
});
$('input[name="password"]').focus(function () {
    $(this).attr('type', 'password');
});
$('input[type="text"]').focus(function () {
    $(this).prev().animate({ 'opacity': '1' }, 200);
});
$('input[type="text"],input[type="password"]').blur(function () {
    $(this).prev().animate({ 'opacity': '.5' }, 200);
});
$('input[name="username"],input[name="password"]').keyup(function () {
    var Len = $(this).val().length;
    if (!$(this).val() == '' && Len >= 5) {
        $(this).next().animate({
            'opacity': '1',
            'right': '30'
        }, 200);
    } else {
        $(this).next().animate({
            'opacity': '0',
            'right': '20'
        }, 200);
    }
});
var open = 0;
layui.use('layer', function () {
    layer.style('login', {
        color: '#777'
    });
    //非空验证
    $('input[type="button"]').click(function () {
        var username = $('input[name="username"]').val();
        var password = $('input[name="password"]').val();
        if (username == '') {
            ErroAlert('请输入您的账号');
        } else if (password == '') {
            ErroAlert('请输入密码');
        }else {
            //认证中..
            //fullscreen();
            $('.login').addClass('test'); //倾斜特效
            setTimeout(function () {
                $('.login').addClass('testtwo'); //平移特效
            }, 300);
            setTimeout(function () {
                $('.authent').show().animate({ right: -320 }, {
                    easing: 'easeOutQuint',
                    duration: 600,
                    queue: false
                });
                $('.authent').animate({ opacity: 1 }, {
                    duration: 200,
                    queue: false
                }).addClass('visible');
            }, 500);

            //登录
            var user = {'username':username, 'password': password};
            //此处做为ajax内部判断
            var url = BASEPATH + "/system/login";
            AjaxPost(url, JSON.stringify(user),
                            function () {
                                //ajax加载中
                            },
                            function (data) {
                                setTimeout(function () {
                                    $('.authent').show().animate({ right: 90 }, {
                                        easing: 'easeOutQuint',
                                        duration: 600,
                                        queue: false
                                    });
                                    $('.authent').animate({ opacity: 0 }, {
                                        duration: 200,
                                        queue: false
                                    }).addClass('visible');
                                    $('.login').removeClass('testtwo'); //平移特效
                                }, 2000);
                                setTimeout(function () {
                                    $('.authent').hide();
                                    $('.login').removeClass('test');
                                    if (data.message == 'ok') {
                                        //登录成功
                                        $('.login div').fadeOut(100);
                                        $('.success').fadeIn(1000);
                                        $('.success').html(data.data);
                                        //跳转操作
                                        location.href = BASEPATH+'/home';
                                    } else {
                                        AjaxErro(data);
                                    }
                                }, 2400);
                            })
        }
    })
});

//Ajax提交
function AjaxPost(Url,JsonData,LodingFun,ReturnFun) {
    $.ajax({
        type: "post",
        url: Url,
        data: JsonData,
        contentType: "application/json;charset=utf-8",
        dataType: 'json',
        async: 'false',
        beforeSend: LodingFun,
        error: function () { AjaxErro();},
        success: ReturnFun
    });
}
//弹出
function ErroAlert(e) {
    var index = layer.alert(e, { icon: 5, time: 2000, offset: 't', closeBtn: 0, title: '错误信息', btn: [], anim: 2, shade: 0 });
    layer.style(index, {
        color: '#777'
    });
}

//Ajax 错误返回处理
function AjaxErro(data) {
    if (!data.message) {
        ErroAlert("错误 : 服务请求失败！");
    } else {
        layer.msg((data.message || "未知错误！"));
    }
}
