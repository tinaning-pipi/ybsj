<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="/WEB-INF/jsp/common.jsp" %>
    <link rel="stylesheet" href="${ctx}/support/css/mantainconfig.css">
    <script src="${ctx}/support/js/checkFrame.js"></script>
    <script src="${ctx}/support/js/mantain_config.js"></script>
    <style type="text/css">
        .checkId {
            left: -12px;
            width: 100px;
        }
    </style>
</head>
<body style="height:100%;">
<div class="base">
    <div class="nva">
        <div class="layui-tab">
            <ul class="layui-tab-title">
                <li class="layui-this" id="funclistmenu">专项列表</li>
                <li id="addfunc">添加专项</li>
            </ul>
            <div class="layui-tab-content layui-body" style="top: 10%;left: 0%;overflow-x: hidden;overflow-y: hidden">
                <div class="layui-tab-item layui-show">
                    <div class="showData">
                        <table class="layui-table" lay-filter="mtable">
                            <thead>
                            <tr>
                                <th lay-data="{field:'typeId',fixed: true}">类型id</th>
                                <th lay-data="{field:'surveyType'}">调查数据类型</th>
                                <th lay-data="{field:'showNum'}">随机抽取数据条数</th>
                                <th lay-data="{field:'openlnla'}">经纬度查询状态</th>
                                <th lay-data="{field:'colNum'}">列数</th>
                                <th lay-data="{fixed: 'right', width:120, align:'center', toolbar: '#barTable'}">操作</th>
                            </tr>
                            </thead>
                        </table>
                        <script id="barTable" type="text/html">
                            <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
                            <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
                        </script>
                    </div>

                    <%--<label style="width: 200px;left: 20px">当前选中专项字段配置</label>--%>
                </div>

                <div class="layui-tab-item">
                    <form class="layui-form" id="funcform">
                        <div class="layui-form-item">
                            <label class="layui-form-label" style="width:10%;text-align: center">类型id</label>
                            <div class="layui-input-block">
                                <input type="text" id="atypeId" name="surveyTypeEntity.typeId" required
                                       lay-verify="required"
                                       placeholder="请输入类型id"
                                       autocomplete="off" class="layui-input" style="width: 96.5%">
                                <label id="checkfId" class="layui-form-label checkId">ddddddddddddddd</label>
                            </div>

                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label" style="width:10%;text-align: center">调查数据类型</label>
                            <div class="layui-input-block">
                                <input type="text" id="asurveyType" name="surveyTypeEntity.surveyType" required
                                       lay-verify="required"
                                       placeholder="请输入调查数据类型"
                                       autocomplete="off" class="layui-input" style="width: 96.5%">
                            </div>

                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label" style="width: fit-content;">随机抽取数据条数</label>
                            <div class="layui-input-block">
                                <input type="text" id="ashowNum" name="surveyTypeEntity.showNum" required
                                       lay-verify="required"
                                       placeholder="请输入随机抽取数据条数" autocomplete="off" class="layui-input"
                                       style="width: 96.5%;">
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label" style="width: fit-content;">是否开启坐标查询</label>
                            <input type="radio" name="surveyTypeEntity.openlnla" value="1" title="是"><input type="radio"
                                                                                            name="surveyTypeEntity.openlnla"
                                                                                            checked="checked"
                                                                                            value="0" title="否"/>

                        </div>

                        <div class="layui-form-item">
                            <div class="layui-input-block">
                                <button class="layui-btn" id="btn" lay-submit lay-filter="btn">确定新增</button>
                                <button type="reset" class="layui-btn layui-btn-primary">重置</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<%--更新面板--%>
<div id="update" hidden="hidden" style="font-size: 13px">
    <div class="uform" id="uform" style="width:800px;height:455px;">
        <form class="layui-form" lay-filter="uform" style="margin-top:0px;">
            <div class="layui-form-item" style="height: 14px;margin-top: 0px">
                <label class="layui-form-label" style="width:12%;text-align: center;">类型id</label>
                <div class="layui-input-block">
                    <input type="text" id="typeId" name="surveyTypeEntity.typeId" required lay-verify="required"
                           placeholder="类型id"
                           autocomplete="off" class="layui-input" disabled="disabled" style="width: 88%">
                </div>
            </div>
            <div class="layui-form-item" style="height: 14px">
                <label class="layui-form-label" style="width:12%;text-align: center">调查数据类型</label>
                <div class="layui-input-block">
                    <input type="text" id="surveyType" name="surveyTypeEntity.surveyType" required lay-verify="required"
                           placeholder="请输入专项名称"
                           autocomplete="off" class="layui-input" style="width: 88%">
                </div>
            </div>
            <div class="layui-form-item" style="height: 14px">
                <label class="layui-form-label" style="width:12%;text-align: center">随机抽取条数</label>
                <div class="layui-input-block">
                    <input type="text" id="showNum" name="surveyTypeEntity.showNum" required lay-verify="required"
                           placeholder="请输入抽取条数"
                           autocomplete="off" class="layui-input" style="width: 88%">

                </div>
            </div>
            <div class="layui-form-item" style="height: 14px">
                <label class="layui-form-label" style="width:12%;text-align: center">经纬度查询状态</label>
                <div class="layui-input-block">
                    <input type="radio" id="openlnla1" name="surveyTypeEntity.openlnla" value="1" title="是"> <input type="radio"
                                                                                                       id="openlnla2"
                                                                                                       name="surveyTypeEntity.openlnla"
                                                                                                       value="0" title="否"/>
                </div>

            </div>
            <div class="layui-form-item" style="height: 14px">
                <label class="layui-form-label" style="width:12%;text-align: center">列数</label>
                <div class="layui-input-block">
                    <input type="text" id="colNum" name="surveyTypeEntity.colNum" disabled="true"
                           autocomplete="off" class="layui-input" style="width: 88%">

                </div>
            </div>
            <div class="layui-form-item" style="height: 180px">
                <div class="showData">
                    <ul class="layui-tab-title">
                        <li class="layui-this" id="selectconfig">专项字段配置&nbsp;
                            <button id="addcol" class="layui-btn layui-btn-sm">
                                <i class="layui-icon">&#xe654;</i>
                            </button>
                        </li>
                    </ul>
                    <table class="layui-table" lay-filter="table1">
                        <thead>
                        <tr>
                            <th lay-data="{field:'stypeId',fixed: true}">类型</th>
                            <th lay-data="{field:'col'}">字段名</th>
                            <th lay-data="{field:'colname'}">列说明</th>
                            <th lay-data="{field:'ordernum'}">排序码</th>
                            <th lay-data="{field:'isprov'}">省字段</th>
                            <th lay-data="{field:'iscity'}">市字段</th>
                            <th lay-data="{field:'iscusttype'}">客户类型字段</th>
                            <th lay-data="{field:'isorder'}">数据排序字段</th>
                            <th lay-data="{field:'isaddr'}">地址字段</th>
                            <th lay-data="{width:130, align:'center', toolbar: '#barTable2'}">操作</th>
                        </tr>
                        </thead>
                    </table>
                    <script id="barTable2" type="text/html">
                        <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
                        <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
                    </script>
                </div>
            </div>

            <div class="layui-form-item">
                <div class="layui-input-block" style="margin-left:40%;margin-top: 5%">
                    <button class="layui-btn" id="uBtn" lay-submit lay-filter="uBtn">确定修改</button>
                    <button type="button" id="uCan" class="layui-btn layui-btn-primary">取消</button>
                </div>
            </div>
        </form>
    </div>
</div>
<%--专项字段添加面板--%>
<div id="addsurveycol" hidden="hidden" style="font-size: 12px">
    <form class="layui-form" id="colform" style="margin-top:1px">
        <div class="layui-form-item">
            <label class="layui-form-label" style="width:10%;text-align: center">类型id</label>
            <div class="layui-input-block" style="width:80%;">
                <input type="text" id="stypeId" name="tSimpleColEntity.stypeId" required
                       lay-verify="required"
                       autocomplete="off" class="layui-input" disabled="true">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label" style="width: 10%;text-align: center">字段名</label>
            <div class="layui-input-block" style="width:80%;">
                <input type="text" id="col" name="tSimpleColEntity.col" required
                       lay-verify="required"
                       placeholder="请输入列名"
                       autocomplete="off" class="layui-input" disabled="true">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label" style="width: 10%;text-align: center">列说明</label>
            <div class="layui-input-block" style="width:80%;">
                <input type="text" id="colname" name="tSimpleColEntity.colname" required
                       lay-verify="required"
                       placeholder="请输入列说明" autocomplete="off" class="layui-input"
                >
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label" style="width: 10%;text-align: center">省字段</label>
            <div class="layui-input-block" style="width:80%;">
                <input type="radio" name="tSimpleColEntity.isprov" value="1" title="是"><input type="radio"
                                                                                name="tSimpleColEntity.isprov"
                                                                                checked="checked" value="0" title="否"/>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label" style="width: 10%;text-align: center">市字段</label>
            <div class="layui-input-block" style="width:80%;">
                <input type="radio" name="tSimpleColEntity.iscity" value="1" title="是"><input type="radio"
                                                                                name="tSimpleColEntity.iscity"
                                                                                checked="checked" value="0" title="否"/>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label" style="width: 10%;text-align: center">客户类型字段</label>
            <div class="layui-input-block" style="width:80%;">
                <input type="radio" name="tSimpleColEntity.iscusttype" value="1" title="是"><input type="radio"
                                                                                name="tSimpleColEntity.iscusttype"
                                                                                checked="checked" value="0" title="否"/>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label" style="width: 10%;text-align: center">数据排序字段</label>
            <div class="layui-input-block" style="width:80%;">
                <input type="radio" name="tSimpleColEntity.isorder" value="1" title="是"><input type="radio"
                                                                                name="tSimpleColEntity.isorder"
                                                                                checked="checked" value="0" title="否"/>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label" style="width: 10%;text-align: center">地址字段</label>
            <div class="layui-input-block" style="width: 100%">
                <input type="radio" name="tSimpleColEntity.isaddr" value="1" title="是"> <input type="radio"
                                                                                name="tSimpleColEntity.isaddr"
                                                                                checked="checked" value="0" title="否"/>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-input-block">
                <button class="layui-btn" id="addcolbtn" lay-submit lay-filter="addcolbtn">确定新增</button>
                <button type="reset" class="layui-btn layui-btn-primary">重置</button>
            </div>
        </div>
    </form>
</div>
<%--专项字段编辑面板--%>
<div id="editsurveycol" hidden="hidden" style="font-size: 12px">
    <form class="layui-form" id="editcolform" style="margin-top:1px">
        <div class="layui-form-item">
            <label class="layui-form-label" style="width:10%;text-align: center">类型id</label>
            <div class="layui-input-block" style="width:80%;">
                <input type="text" id="estypeId" name="tSimpleColEntity.stypeId" required
                       lay-verify="required"
                       autocomplete="off" class="layui-input" disabled="true">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label" style="width: 10%;text-align: center">字段名</label>
            <div class="layui-input-block" style="width:80%;">
                <input type="text" id="ecol" name="tSimpleColEntity.col" required
                       lay-verify="required"
                       placeholder="请输入列名"
                       autocomplete="off" class="layui-input">
                <input type="hidden" id="hecol"/>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label" style="width: 10%;text-align: center">列说明</label>
            <div class="layui-input-block" style="width:80%;">
                <input type="text" id="ecolname" name="tSimpleColEntity.colname" required
                       lay-verify="required"
                       placeholder="请输入列说明" autocomplete="off" class="layui-input"
                >
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label" style="width: 10%;text-align: center">省字段</label>
            <div class="layui-input-block" style="width:80%;">
                <input type="radio" id="eisprov1" name="tSimpleColEntity.isprov" value="1" title="是"><input type="radio"  id="eisprov2"
                                                                                name="tSimpleColEntity.isprov"
                                                                                checked="checked" value="0" title="否"/>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label" style="width: 10%;text-align: center">市字段</label>
            <div class="layui-input-block" style="width:80%;">
                <input type="radio" id="eiscity1" name="tSimpleColEntity.iscity" value="1" title="是"><input type="radio"
                                                                                name="tSimpleColEntity.iscity" id="eiscity2"
                                                                                checked="checked" value="0" title="否"/>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label" style="width: 10%;text-align: center">客户类型字段</label>
            <div class="layui-input-block" style="width:80%;">
                <input type="radio" id="eiscusttype1" name="tSimpleColEntity.iscusttype" value="1" title="是"><input type="radio" id="eiscusttype2"
                                                                                name="tSimpleColEntity.iscusttype"
                                                                                checked="checked" value="0" title="否"/>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label" style="width: 10%;text-align: center">数据排序字段</label>
            <div class="layui-input-block" style="width:80%;">
                <input type="radio" id="eisorder1" name="tSimpleColEntity.isorder" value="1" title="是"><input type="radio"
                                                                                                              id="eisorder2" name="tSimpleColEntity.isorder"
                                                                                checked="checked" value="0" title="否"/>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label" style="width: 10%;text-align: center">地址字段</label>
            <div class="layui-input-block" style="width: 100%">
                <input type="radio"  id="eisaddr1" name="tSimpleColEntity.isaddr" value="1" title="是"><input type="radio"
                                                                                                             id="eisaddr2" name="tSimpleColEntity.isaddr"
                                                                                checked="checked" value="0" title="否"/>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-input-block">
                <button class="layui-btn" id="ebtn" lay-submit lay-filter="ebtn">确定修改</button>
                <button type="button" id="ecel" class="layui-btn layui-btn-primary">取消</button>
            </div>
        </div>
    </form>
</div>
</html>
