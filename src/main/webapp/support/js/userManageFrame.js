var layer;
var editpwdLayer;
var changepwd;
function logon() {
    $.ajax({
        url: ctx + "/user/user_logon",
        type: "POST",
        data: {
            'userEntity.userid': $("#logon").attr("alt")
        },
        dataType: "json",
        success: function (data) {
            checkLogon(data);
            var error = parseInt(data.error);
            if (error === 0) {
                $("#logon").attr("alt", "");
                layer.open({
                    time: 3000,
                    content: data.message + "，即将关闭该页面。"
                    , btn: ['确定']
                    , yes: function (index, layero) {
                        layer.close(index);
                        window.location.href = ctx + '/index.jsp';
                    }
                    , cancel: function () {
                        window.location.href = ctx + '/index.jsp';
                    }, end: function () {
                        window.location.href = ctx + '/index.jsp';
                    }
                });
            } else {
                layer.msg(data.message);
            }
        },
        error: function () {
            layer.msg("连接服务器失败");
        }
    });
}

layui.use(['element', 'layer'], function () {
    var element = layui.element;
    layer = layui.layer;
    $(function () {
        $("#urls").find("a").each(function (i) {
            var $obj = $(this);
            $(this).click(function () {
                $.ajax({
                    url: ctx + "/user/user_checkLogin",
                    type: "POST",
                    data: {},
                    dataType: "json",
                    success: function (data) {
                        if (data.isLogin === true) {
                            $("#show_page").prop("src", $obj.attr("alt"));
                        } else {
                            window.location.href = ctx + '/index.jsp';
                        }
                    }
                });
            });
            if (i === 0) {
                $(this).click();
            }
        });
        $("#logon").click(function () {
            logon();
        });
    });
});

window.onbeforeunload = function () {
    if ($("#logon").attr("alt") === "") {
        return;
    } else {
        logon();
        return;
    }
};
$(function () {
    changepwd=0;
    //点击弹出弹框
    $("#edituser").click(function () {
        // $("#userid").val($("#showuid").val());
        editpwdLayer=layer.open({
            title: '密码修改',
            type: 1,
            shade: 0.5,
            // area: ['100px', '100px'],
            content: $('#changepassword'),
            offset: 'auto',
            zIndex: 19891014,
            success: function (layero) {
                var mask = $(".layui-layer-shade");
                mask.appendTo(layero.parent());
            }
        });
    });
    //对密码修改表单form监听
    layui.use('form', function () {
        checkLogin();
        var form = layui.form;
        //修改
        form.on('submit(uBtn)', function (data) {
            var newupwd=$("#newupwd").val();
            var confirmupwd=$("#confirmupwd").val();
            if(changepwd==='1'){
                layer.msg("原密码不正确，请重新输入");
                return false;
            }
            else if(newupwd!=confirmupwd){
                layer.msg("确认密码和新密码不一致，请重新输入");
                return false;
            }
            else{
                $.ajax({
                    url: ctx + "/user/user_updatepwd",
                    type: "POST",
                    data: data.field,
                    dataType: "json",
                    success: function (data) {
                        var error = parseInt(data.error);
                        layer.msg(data.message);
                        if (error === 0) {
                            layer.close(editpwdLayer);
                        }
                    },
                    error: function () {
                        layer.msg("连接服务器失败");
                    }
                });
            }
            return false;
        });

    });
    $("#uCan").click(function () {
        layer.close(editpwdLayer);
    });
    //判断原密码输入框密码是否和原密码相同
    $("#oldupwd").blur(function(){
        var oldupwd=$("#oldupwd").val();
        $.ajax({
            url: ctx + "/user/user_checkpwd",
            type: "POST",
            data:{
              "userEntity.upwd":oldupwd
            },
            dataType: "json",
            success: function (data) {
                checkLogon(data);
                var msg = parseInt(data.msg);
                if (msg === 0) {
                    //1指的是和原密码不相同
                    changepwd='1';
                    // layer.msg("原密码不正确，请重新输入");
                }
                else{
                    changepwd='0';
                }
            }
        });
    });
});