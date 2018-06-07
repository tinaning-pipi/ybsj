//专项字段添加弹出框
var addcolLayer;
//专项修改弹出层
var updateLayer;
//专项字段修改弹框
var editcoLayer;
$(function () {
    layui.use("element", function () {
        var element = layui.element;
    });
    var flag = 0;
//对输入值 进行监听
    layui.use("form", function () {
        //对输入的功能id进行监听
        $("#atypeId").bind("input propertychange", function () {
            var atypeId = $("#atypeId").val();
            if (!isNaN(atypeId)) {
                flag = 1;
                $.ajax({
                    url: ctx + '/config/mantainconfig_checkfId',
                    type: "POST",
                    data: {
                        'cId': $("#atypeId").val()
                    },
                    success: function (data) {
                        if ($("#atypeId").val() == null || $("#atypeId").val() == "") {
                            $("#checkfId").html("");
                            flag = 3;
                        }
                        else {
                            if (data.message == "1") {
                                $("#checkfId").html("类型id可用");
                                $("#checkfId").attr("style", "color:green");
                                flag = 1;
                            }
                            else if (data.message = "0") {
                                $("#checkfId").html("类型id不可用");
                                $("#checkfId").attr("style", "color:red");
                                flag = 2;
                            }
                        }
                    }
                });

            }

            else {
                $("#checkfId").html("请输入 数字");
                $("#checkfId").attr("style", "color:red");
                flag = 2;
            }

            return false;
        });
    });

//对新增功能表单form监听
    layui.use('form', function () {
        var atypeId = $("#atypeId").val();
        var asurveyType = $("#asurveyType").val();
        checkLogin();
        var form = layui.form;
        // if(atypeId==null||atypeId===""||asurveyType===null||asurveyType===""||ashowNum==null||ashowNum===""){
        //     flag=0;
        // }

        form.on('submit(btn)', function (data) {
            var ashowNum = $("#ashowNum").val();
            if (!isNaN(ashowNum)) {
                if (flag === 1) {
                    $.ajax({
                        url: ctx + "/config/mantainconfig_add",
                        type: "POST",
                        data: data.field,
                        data_type: "json",
                        beforeSend: function (xhr) {
                            $("#btn").attr('disabled', "true");
                        },
                        success: function (data) {
                            layer.msg(data.message);
                            $("#checkfId").html("");
                            $('#funcform')[0].reset();


                        },
                        error: function () {
                            layer.alert("连接服务器失败");
                        },
                        complete: function () {
                            $("#btn").removeAttr("disabled");
                        }

                    });

                }
            }
            else if (flag === 2) {
                layer.msg("类型id不可用请重新填写");
            }
            else {
                layer.msg("随机条数是非数字，请填写为数字");
                $("#ashowNum").val("");
            }
            return false;
        });
    });
    //对字段修改form监听
    layui.use('form', function () {
        checkLogin();
        var form = layui.form;
        //修改
        form.on('submit(ebtn)', function (data) {
            var hecol=$("#hecol").val();
            var dary=data.field;
            dary.hecol=hecol;
            colupdate(dary);
            return false;
        });
    });
    $("#uCan").click(function () {
        layer.closeAll();
    });
    $("#ecel").click(function () {
        layer.close(editcoLayer);
    });

    //对新增专项字段表单form监听
    layui.use('form', function () {
        checkLogin();
        var form = layui.form;
        form.on('submit(addcolbtn)', function (data) {
            $.ajax({
                url: ctx + "/config/simplecol_add",
                type: "POST",
                data: data.field,
                data_type: "json",
                beforeSend: function (xhr) {
                    $("#addcolbtn").attr('disabled', "true");
                },
                success: function (data) {
                    layer.msg(data.message);
                    layer.close(addcolLayer);
                    var colum = $("#colNum").val();
                    $("#colNum").val(parseInt(colum) + 1);
                    getcollist($("#stypeId").val());
                     return true;
                },
                error: function () {
                    layer.alert("连接服务器失败");
                },
                complete: function () {
                    $("#addcolbtn").removeAttr("disabled");
                }

            });
            return false;
        });
    });
    $(function () {
        //对功能信息修改表单form监听
        layui.use('form', function () {
            checkLogin();
            var form = layui.form;
            //修改
            form.on('submit(uBtn)', function (data) {
                update(data.field);
                // location.reload();
                return false;
                // location.reload();
            });
        });

    });
    //点击功能列表
    $("#funclistmenu").click(function () {
        location.reload();
    });
    //点击添加功能重置 checkId
    $("#addfunc").click(function () {
        $('#funcform')[0].reset();
        $("#checkfId").html("");
    });
    //打开页面时加载一次数据
    init();

});
//初始化方法
function init() {
    //初始化专项列表
    layui.use('table', function () {
        var table = layui.table;
        var tableOptions = {
            url: ctx + '/config/mantainconfig_findAll',
            method: 'POST',
            id: 'listReload',
            page: true,
            request: {pageName: 'current', limitName: 'currentTotal'},
            response: {
                statusName: 'error',
                statusCode: 0,
                msgName: 'message',
                countName: 'total',
                dataName: 'surveytypelist',
            }
        };
        table.init('mtable', tableOptions);
        //监听主表格工具条
        table.on('tool(mtable)', function (obj) {
            var data = obj.data;//obj代表所选中的行
            if (obj.event === 'del') {
                layer.confirm('将删除专项的所有数据', {
                    btn: ['确定','取消'] //按钮
                    ,cancel: function(index, layero){
                    }
                }, function(){
//是
                    $.ajax({
                        url: ctx + "/config/mantainconfig_delete",
                        type: "POST",
                        data: {
                            'surveyTypeEntity.typeId': (data.typeId).toString()
                        },
                        dataType: "json",
                        success: function (data) {
                            var error = parseInt(data.error);
                            if (error === 0) {
                                obj.del();
                                layer.msg(data.message);
                                init();
                            }

                        },
                        error: function () {
                            layer.alert("连接服务器失败");
                        },
                        complete: function () {
                            layer.close("操作问题");
                        }
                    });
                }, function(){
//否
                });


            } else if (obj.event === 'edit') {
                //获取信息
                $.ajax({
                    url: ctx + "/config/mantainconfig_findOne",
                    type: "POST",
                    data: {
                        'surveyTypeEntity.typeId': data.typeId
                    },
                    dataType: "json",
                    success: function (data) {
                        if (data.error === 0) {
                            //设置值
                            var typeId = data.surveyTypeEntity.typeId;
                            var surveyType = data.surveyTypeEntity.surveyType;
                            var showNum = data.surveyTypeEntity.showNum;
                            var openlnla = data.surveyTypeEntity.openlnla;
                            var colNum = data.surveyTypeEntity.colNum;
                            $("#typeId").val(typeId);
                            $("#surveyType").val(surveyType);
                            $("#showNum").val(showNum);
                            if(openlnla===1||openlnla==='1'){
                                $("#openlnla1").attr("checked","checked");
                            }
                            else {
                                $("#openlnla2").attr("checked","checked");
                            }
                            $("#colNum").val(colNum);
                            //重新渲染
                            layui.use('form', function () {
                                var form = layui.form;
                                form.render();//更新全部 ；
                            });
                            updateLayer = layer.open({
                                type: 1,
                                shade: 0.5,
                                area: ['820px', '520px'],
                                content: $('#update'),
                                offset: 'auto',
                                zIndex:19891014,
                                success:function(layero){
                                    var mask = $(".layui-layer-shade");
                                    mask.appendTo(layero.parent());
                                },
                                // cancel: function(){
                                //     var data={
                                //         'surveyTypeEntity.typeId':$("#typeId").val(),
                                //         'surveyTypeEntity.surveyType':$("#surveyType").val(),
                                //         'surveyTypeEntity.showNum':$("#showNum").val(),
                                //         'surveyTypeEntity.openlnla':$("#openlnla").val(),
                                //         'surveyTypeEntity.colNum':$("#colNum").val()
                                //     };
                                //     update(data);
                                // },
                                closeBtn:0,
                                end: function () {
                                    //关闭的时候重新初始化主表格，以防获取数据异常，因为layui点击某一行后，进入另外一个表格，主表格行信息会被修改或遗失，导致关闭弹窗后，点击另外行出现获取数据异常。
                                    // location.reload();
                                    init();
                                }
                            });
                            getcollist(typeId);
                        } else {
                            layer.alert(data.message);
                        }
                    },
                    error: function () {
                        layer.alert("连接服务器失败");
                    }
                });
            }
        });

    });
    //新增专项字段按钮弹出框
    $("#addcol").click(function () {
        $("#stypeId").val($("#typeId").val());
        $.ajax({
            url:ctx + '/config/simplecol_getMaxcol',
            type:"POST",
            data: {
                'tSimpleColEntity.stypeId': $("#typeId").val()
            },
            dataType: "json",
            success: function (data) {
                if (data.error === 0) {
                    $("#col").val("COL"+data.maxcol);
                } else {
                    layer.alert(data.message);
                }
            },
            error: function () {
                layer.alert("连接服务器失败");
            }

        });
        addcolLayer = layer.open({
            type: 1,
            shade: 0.3,
            zIndex:19891015,
            area: ['800px', '540px'],
            content: $('#addsurveycol'),
            success:function(layero){
                var mask = $(".layui-layer-shade");
                layero.appendTo(mask.parent());
                //其中：layero是弹层的DOM对象
            }
        });
        return false;

    });

}
//监听
function getcollist(typeId) {
    //初始化专项字段列表
    layui.use('table', function () {
        var table1 = layui.table;
        //初始化专项字段表格
        var tableOptions1 = {
            height: 150,
            url: ctx + '/config/simplecol_findBytypeId',
            method: 'POST',
            id: 'listReload',
            page: false,
            request: {pageName: 'current', limitName: 'currentTotal'},
            where: {
                'stypeId': typeId
            },
            response: {
                statusName: 'error',
                statusCode: 0,
                msgName: 'message',
                dataName: 'simpleColEntityList',
            }
        };
        table1.init('table1', tableOptions1);
        //监听副表格工具条
        table1.on('tool(table1)', function (obj) {
            var data = obj.data;//obj代表所选中的行
            if (obj.event === 'del') {
                $.ajax({
                    url: ctx + "/config/simplecol_delete",
                    type: "POST",
                    data: {
                        'tSimpleColEntity.stypeId': data.stypeId,
                        'tSimpleColEntity.col': data.col
                    },
                    dataType: "json",
                    success: function (data) {
                        var error = parseInt(data.error);
                        if (error === 0) {
                            obj.del();
                            layer.msg(data.message);
                            var colum = $("#colNum").val();
                            $("#colNum").val(parseInt(colum) - 1);
                            getcollist($("#typeId").val());
                        }

                    },
                    error: function () {
                        layer.alert("连接服务器失败");
                    },
                    complete: function () {
                        layer.close(index);
                    }
                });
            }
            else if (obj.event === 'edit') {
                //获取信息
                $.ajax({
                    url: ctx + "/config/simplecol_findOne",
                    type: "POST",
                    data: {
                        'tSimpleColEntity.col': data.col
                    },
                    dataType: "json",
                    success: function (data) {
                        if (data.error === 0) {
                            //设置值
                            var estypeId = data.tSimpleColEntity.stypeId;
                            var ecol = data.tSimpleColEntity.col;
                            var ecolname = data.tSimpleColEntity.colname;
                            var eisprov = data.tSimpleColEntity.isprov;
                            var eiscity = data.tSimpleColEntity.iscity;
                            var eiscusttype = data.tSimpleColEntity.iscusttype;
                            var eisorder = data.tSimpleColEntity.isorder;
                            var eisaddr = data.tSimpleColEntity.isaddr;
                            $("#estypeId").val(estypeId);
                            $("#ecol").val(ecol);
                            $("#ecolname").val(ecolname);
                            if(eisprov===1||eisprov==='1'){
                                $("#eisprov1").attr("checked","checked");
                                // $("#eisprov2").attr("checked","true");
                            }
                            else if(eisprov===0||eisprov==='0'){
                                // $("#eisprov1").removeAttr("checked");
                                // $("#eisprov1").attr("checked","false");
                                $("#eisprov2").attr("checked","checked");
                            }
                            else if(eiscity===1||eiscity==='1'){
                                $("#eisprov1").attr("checked","false");
                                $("#eiscity1").attr("checked","checked");
                                // $("#eiscity2").removeAttr("checked");
                            }
                            else if(eiscity===0||eiscity==='0'){
                                $("#eiscity2").attr("checked","checked");
                            }
                            else if(eiscusttype===1||eiscusttype==='1'){
                                $("#eiscusttype1").attr("checked","checked");
                                // $("#eiscusttype2").attr("checked","false");
                            }
                            else if(eiscusttype===0||eiscusttype==='0'){
                                // $("#eiscusttype1").attr("checked","false");
                                $("#eiscusttype2").attr("checked","checked");
                            }
                            else if(eisorder===1||eisorder==='1'){
                                $("#eisorder1").attr("checked","true");
                                // $("#eisorder2").attr("checked","false");
                            }
                            else if(eisorder===0||eisorder==='0'){
                                // $("#eisorder1").attr("checked","false");
                                $("#eisorder2").attr("checked","checked");
                            }
                            else if(eisaddr===1||eisaddr==='1'){
                                $("#eisaddr1").attr("checked","checked");
                                // $("#eisaddr2").attr("checked","false");
                            }
                            else if(eisaddr===0||eisaddr==='0'){
                                // $("#eisaddr1").attr("checked","false");
                                $("#eisaddr2").attr("checked","checked");
                            }
                            //将原有列名放入隐藏域，以方便对列名的修改
                            $("#hecol").val(ecol);
                            //重新渲染
                            layui.use('form', function () {
                                var form = layui.form;
                                form.render();//更新全部 ；
                            });
                            editcoLayer = layer.open({
                                type: 1,
                                shade: 0.3,
                                area: ['800px', '540px'],
                                content: $('#editsurveycol'),
                                zIndex:19891016,
                                success:function(layero){
                                    var mask = $(".layui-layer-shade");
                                    mask.appendTo(layero.parent());
                                },
                                end:function () {
                                    // $("#editcolform")[0].reset();
                                }
                            });
                        } else {
                            layer.alert(data.message);
                        }
                    },
                    error: function () {
                        layer.alert("连接服务器失败");
                    }
                });
            }
        });


    });
}

//执行修改调查类型的方法
function update(data) {
    $.ajax({
        url: ctx + "/config/mantainconfig_update",
        type: "POST",
        data: data,
        dataType: "json",
        success: function (data) {
            var error = parseInt(data.error);
            if (error === 0) {
                //刷新当前页数据################################
                $(".layui-laypage-btn").click();
                //关闭弹窗
                layer.close(updateLayer);
            }
            layer.msg(data.message);
        },
        error: function () {
            layer.alert("连接服务器失败");
        }
    })
}
//执行修改字段专项的方法
function colupdate(data) {
    $.ajax({
        url: ctx + "/config/simplecol_update",
        type: "POST",
        data: data,
        dataType: "json",
        success: function (data) {
                layer.msg(data.message);
                layer.close(editcoLayer);
                getcollist($("#typeId").val());
        },
        error: function () {
            layer.alert("连接服务器失败");
        }
    })
}