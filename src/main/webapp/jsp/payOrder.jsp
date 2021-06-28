<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-touch-fullscreen" content="yes">
    <meta name="format-detection" content="telephone=no">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <title>订单支付</title>
</head>
<body>
<form id="payForm" method="post"
      action="${ctx }/act/order/payOrder.do?r=<%=System.currentTimeMillis() %>&unitId=${map.UNIT_ID }&orderNo=${map.ORDER_NO }&orderType=1&branchId=${map.BRANCH_ID}">
    <input type="hidden" value="14" name="payWay" id="input_pay_method"/>
    <input type="hidden" value="0" name="pay_balance" id="input_pay_balance"/>
    <input type="hidden" name="memberId" value="${map.MEMBER_ID}" id="input_memberId"/>
    <%--支付截止时间 --%>
    <input type="hidden" value="curDep" id="orderDeadLine"/>
    <input type="hidden" id="socialType" name="socialType" value="${socialType}"/>
    <c:if test="${quhaoTag=='1'}">
        <%--#25157 将取号需要的参数传递至后台--%>
        <input type="hidden" name="quhaoTag" value="${quhaoTag}" id="quhaoTag"/>
    </c:if>
    <%--是否开通挂号医保提前支付--%>
    <input type="hidden" value="${ISOpenYibaoAdvancePay}" id="ISOpenYibaoAdvancePay">

    <div class="order-result tc fts18 co-5 mb10">
        <%--ISOpenYibaoAdvancePay  #59536开通提前付显示倒计时--%>
        <c:if test="${confirmYibaoPayBySchFlag!='true' || ISOpenYibaoAdvancePay == '1'}">
            订单提交成功！请您尽快支付！
            <c:if test="${isMustPay!=null && isMustPay==1}">
                <div class="fts14">
                    <!--data-time为倒计秒数-->
                    <span class="co-6 order-pay-time" data-time="${diffTime}">00:00</span>
                    <span class="co-3">内未完成支付的订单将自动取消</span>
                </div>
            </c:if>
        </c:if>
        <c:if test="${confirmYibaoPayBySchFlag=='true'}">
            <c:if test="${ISOpenYibaoAdvancePay!='1'}">
                选择医保支付的用户请于就诊当天支付挂号费用，如需自费支付，请取消订单重新下单。
            </c:if>
            <c:if test="${ISOpenYibaoAdvancePay=='1'}">
                订单提交成功！请您尽快支付！
            </c:if>
        </c:if>
    </div>
    <div class="bgf padl10 padr10 padt10 padb10 mb10">
        <div class="fts16 co-5">
            <span class="mr5">订单编号</span>${map.ORDER_NO }
        </div>
        <div class="fts16 co-5">支付金额</div>
        <div class="co-6 tr">
            <span class="fts20" id="amt"></span>元
        </div>
    </div>

    <c:if test="${confirmYibaoPayBySchFlag!='true'}">
        <div class="bgf padl10 padr10 padt10 padb10 mb10 clr">
            <div>
            <span class="mr5 fts16 co-3">网上支付</span>
            <span id="iconyibao" class="co-4 order-pay-tips fts12 padr10 lh_def inblo">
                <!-- 是否不隐藏选择支付类型的提示配置不为1时显示，或者开通了强制支付时也显示文案 -->
                <c:if test="${(fn:length(socialMethodsList) > 0 && isHideChoosePaytypeTip != '1') || isMustPay == '1'}">
                    <i class="iconfont mr5">&#xf10e;</i>
                    <i id="yibao">选择自费支付, 后续的就诊费用将不可使用医保支付</i>
                </c:if>
            </span>
        </div>

        <ul class="payway">
            <!--中行支付，只有当在中行APP里才会显示-->
            <c:if test='${sessionScope.nychannel=="ny_zhonghang_android" || sessionScope.nychannel=="ny_zhonghang_ios"}'>
                <li class="yibao_change_payway">
                    <a class="" href="javascript:void(0)" data-method="58" onclick="zfMoney()">
                        <i class="iconfont icon-zhongguoyinhang gonghang-orange"></i><br>
                        中行支付
                    </a>
                </li>
            </c:if>
             <%--is_channel_bill_pay为1代表渠道请求--%>
            <c:if test='${is_channel_bill_pay!="1" && (sessionScope.nychannel != "ny_zhonghang_android" && sessionScope.nychannel !="ny_zhonghang_ios")}'>

                <c:if test="${yuyueUserType!='1'}">
                    <c:if test='${sessionScope.nychannel=="alipay" && gonghangAggregate!="1"}'>
                        <li id="zf">
                            <a class="paybor" href="javascript:void(0);" data-method="13" onclick="zfMoney()">
                                <i class="iconfont icon-zhifubao alipay-blue"></i><br>
                                自费支付
                            </a>
                        </li>
                    </c:if>
                    <c:if test='${(sessionScope.nychannel=="" || sessionScope.nychannel=="weixin" || sessionScope.nychannel==null) && gonghangAggregate!="1" && jianhangAggregate!="1"}'>
                        <li>
                            <a class="paybor" href="javascript:void(0);" data-method="14" onclick="zfMoney()">
                                <i class="iconfont icon-weixin2 weixin-green"></i><br>
                                自费支付
                            </a>
                        </li>
                    </c:if>
                    <c:if test='${zhonghang=="1"}'>
                        <li class="">
                            <a class="" href="javascript:void(0);" data-method="27" onclick="zfMoney()">
                                <i class="iconfont icon-zhongguoyinhang zhonghang-orange"></i><br>
                                中国银行支付
                            </a>
                        </li>
                    </c:if>
                    <c:if test='${jianhang=="1"}'>
                        <li class="yibao_change_payway">
                            <a class="" href="javascript:void(0)" data-method="29" onclick="zfMoney()">
                                <i class="iconfont icon-jiansheyinhang jianhang-blue"></i><br>
                                中国建设银行
                            </a>
                        </li>
                    </c:if>
                    <c:if test='${gonghangAggregate=="1"}'>
                        <li class="yibao_change_payway">
                            <a class="" href="javascript:void(0)" data-method="56" onclick="zfMoney()">
                                <i class="iconfont icon-gongshangyinhang gonghang-orange"></i><br>
                                工行聚合支付
                            </a>
                        </li>
                    </c:if>
                    <c:if test='${jianhangAggregate=="1"}'>
                        <li class="yibao_change_payway">
                            <a class="" href="javascript:void(0)" data-method="60" onclick="zfMoney()">
                                <i class="iconfont icon-jiansheyinhang jianhang-blue"></i><br>
                                建行聚合支付
                            </a>
                        </li>
                    </c:if>
                    <c:if test='${nonghangPay=="1" && sessionScope.nychannel!="alipay"}'>
                        <li class="yibao_change_payway">
                            <a class="" href="javascript:void(0)" data-method="61" onclick="zfMoney()">
                                <i class="iconfont icon-nongyeyinhang nonghang-green"></i><br>
                                农行网上支付
                            </a>
                        </li>
                    </c:if>
                    <c:if test="${selectYiBaoFlag!=1}">
                        <%-- 循环渲染医保类型 --%>
                        <c:forEach items="${socialMethodsList}" var="socialTypeMethod" varStatus="status">
                            <c:if test="${ISOpenYibaoAdvancePay=='1'}">
                                <c:if test="${socialTypeMethod.ALIPAY_ADULT_SOCIAL == '1' && sessionScope.nychannel=='alipay'}">
                                    <li id="yb">
                                        <a class="" href="javascript:void(0);" data-method="22" onclick="ybMoney()">
                                            <c:if test='${social_icon == ""}'>
                                                <i class="iconfont icon-chengrenyibaox chengren-blue"></i><br>
                                            </c:if>
                                            <c:if test='${social_icon != ""}'>
                                                <img src='${social_icon}' style="width: 40px; height: 40px;"><br>
                                            </c:if>
                                            ${social_text}
                                        </a>
                                    </li>
                                </c:if>
                                <c:if test="${socialTypeMethod.ALIPAY_CHILD_SOCIAL == '1' && sessionScope.nychannel=='alipay'}">
                                    <li id="yb">
                                        <a class="" href="javascript:void(0);" data-method="221" onclick="ybMoney()">
                                            <i class="iconfont icon-shaoeryibaox shaoer-green"></i><br>
                                            少儿医保
                                        </a>
                                    </li>
                                </c:if>
                                <c:if test="${socialTypeMethod.WE_CHAT_ADULT_SOCIAL == '1' && (sessionScope.nychannel==''||sessionScope.nychannel=='weixin'||sessionScope.nychannel==null)}">
                                    <li id="yb">
                                        <a class="" href="javascript:void(0);" data-method="25" onclick="ybMoney()">
                                            <c:if test='${social_icon == ""}'>
                                                <i class="iconfont icon-chengrenyibaox chengren-blue"></i><br>
                                            </c:if>
                                            <c:if test='${social_icon != ""}'>
                                                <img src='${social_icon}' style="width: 40px; height: 40px;"><br>
                                            </c:if>
                                            ${social_text}
                                        </a>
                                    </li>
                                </c:if>
                                <c:if test="${socialTypeMethod.WE_CHAT_CHILD_SOCIAL == '1' && (sessionScope.nychannel==''||sessionScope.nychannel=='weixin'||sessionScope.nychannel==null)}">
                                    <li id="yb">
                                        <a class="" href="javascript:void(0);" data-method="251" onclick="ybMoney()">
                                            <i class="iconfont icon-shaoeryibaox shaoer-green"></i><br>
                                            少儿医保
                                        </a>
                                    </li>
                                </c:if>
                            </c:if>
                            <c:if test="${ISOpenYibaoAdvancePay!='1'}">
                                <c:if test="${map.TO_DATE_IS_CUR_DAY_PAY == '1'}">
                                    <c:if test="${socialTypeMethod.ALIPAY_ADULT_SOCIAL == '1' && sessionScope.nychannel=='alipay'}">
                                        <li id="yb">
                                            <a class="" href="javascript:void(0);" data-method="22" onclick="ybMoney()">
                                                <c:if test='${social_icon == ""}'>
                                                    <i class="iconfont icon-chengrenyibaox chengren-blue"></i><br>
                                                </c:if>
                                                <c:if test='${social_icon != ""}'>
                                                    <img src='${social_icon}' style="width: 40px; height: 40px;"><br>
                                                </c:if>
                                                ${social_text}
                                            </a>
                                        </li>
                                    </c:if>
                                    <c:if test="${socialTypeMethod.ALIPAY_CHILD_SOCIAL == '1' && sessionScope.nychannel=='alipay'}">
                                        <li id="yb">
                                            <a class="" href="javascript:void(0);" data-method="221" onclick="ybMoney()">
                                                <i class="iconfont icon-shaoeryibaox shaoer-green"></i><br>
                                                少儿医保
                                            </a>
                                        </li>
                                    </c:if>
                                    <c:if test="${socialTypeMethod.WE_CHAT_ADULT_SOCIAL == '1' && (sessionScope.nychannel==''||sessionScope.nychannel=='weixin'||sessionScope.nychannel==null)}">
                                        <li id="yb">
                                            <a class="" href="javascript:void(0);" data-method="25" onclick="ybMoney()">
                                                <c:if test='${social_icon == ""}'>
                                                    <i class="iconfont icon-chengrenyibaox chengren-blue"></i><br>
                                                </c:if>
                                                <c:if test='${social_icon != ""}'>
                                                    <img src='${social_icon}' style="width: 40px; height: 40px;"><br>
                                                </c:if>
                                                ${social_text}
                                            </a>
                                        </li>
                                    </c:if>
                                    <c:if test="${socialTypeMethod.WE_CHAT_CHILD_SOCIAL == '1' && (sessionScope.nychannel==''||sessionScope.nychannel=='weixin'||sessionScope.nychannel==null)}">
                                        <li id="yb">
                                            <a class="" href="javascript:void(0);" data-method="251" onclick="ybMoney()">
                                                <i class="iconfont icon-shaoeryibaox shaoer-green"></i><br>
                                                少儿医保
                                            </a>
                                        </li>
                                    </c:if>
                                </c:if>

                                <c:if test="${map.TO_DATE_IS_CUR_DAY_PAY != '1' }">
                                    <c:if test="${socialTypeMethod.ALIPAY_ADULT_SOCIAL == '1' && sessionScope.nychannel=='alipay'}">
                                        <li>
                                            <a href="javascript:void(0)" class="">
                                                成人医保
                                                <b style="line-height:18px" class="fts12 block">请于就诊日当天<br/>选择医保支付</b>
                                            </a>
                                        </li>
                                    </c:if>
                                    <c:if test="${socialTypeMethod.ALIPAY_CHILD_SOCIAL == '1' && sessionScope.nychannel=='alipay'}">
                                        <li>
                                            <a href="javascript:void(0)" class="">
                                                少儿医保
                                                <b style="line-height:18px" class="fts12 block">请于就诊日当天<br/>选择医保支付</b>
                                            </a>
                                        </li>
                                    </c:if>
                                    <c:if test="${socialTypeMethod.WE_CHAT_ADULT_SOCIAL == '1' && (sessionScope.nychannel==''||sessionScope.nychannel=='weixin'||sessionScope.nychannel==null)}">
                                        <li>
                                            <a href="javascript:void(0)" class="">
                                                成人医保
                                                <b style="line-height:18px" class="fts12 block">请于就诊日当天<br/>选择医保支付</b>
                                            </a>
                                        </li>
                                    </c:if>
                                    <c:if test="${socialTypeMethod.WE_CHAT_CHILD_SOCIAL == '1' && (sessionScope.nychannel==''||sessionScope.nychannel=='weixin'||sessionScope.nychannel==null)}">
                                        <li>
                                            <a href="javascript:void(0)" class="">
                                                少儿医保
                                                <b style="line-height:18px" class="fts12 block">请于就诊日当天<br/>选择医保支付</b>
                                            </a>
                                        </li>
                                    </c:if>
                                </c:if>
                            </c:if>
                        </c:forEach>
                    </c:if>
                </c:if>
            </c:if>

              <%--is_channel_bill_pay为1代表渠道请求, 由于公司app请求的诊前并未嵌套我们业务, 所以在此只会是医院app请求--%>
             <c:if test='${is_channel_bill_pay=="1" && (sessionScope.nychannel != "ny_zhonghang_android" && sessionScope.nychannel !="ny_zhonghang_ios")}'>
                 <%--hosAppOrderCanWeixinZfPay为1代表app允许微信支付, hosAppOrderCanAlipayZfPay 为1代表app允许支付宝支付--%>
                 <c:if test="${hosAppOrderCanWeixinZfPay=='1'}">
                     <li id="zf">
                         <a class="paybor" href="javascript:void(0);" data-method="15" onclick="zfMoney()">
                             <i class="iconfont icon-weixin2 weixin-green"></i><br>
                             自费支付
                         </a>
                     </li>
                </c:if>
                 <c:if test="${hosAppOrderCanAlipayZfPay=='1'}">
                     <li id="zf">
                         <a class="" href="javascript:void(0);" data-method="13" onclick="zfMoney()">
                             <i class="iconfont icon-zhifubao alipay-blue"></i><br>
                             自费支付
                         </a>
                     </li>
                 </c:if>
             </c:if>
        </ul>

            <div class="yibao hide">
            <span class="co-4 fts12 padr10 lh_def inblo">
                 <c:if test="${self_pay_tip!=null }">${self_pay_tip }</c:if>
                 <c:if test="${self_pay_tip==null }">目前仅开放一档（综合医保）普通门诊缴费。门诊特检、特病、生育医保及二、三档参保人请到人工窗口缴费，否则手机支付的医疗费用不予医保报销。</c:if>
            </span>
        </div>
        </div>
    </c:if>
    <input class="mt10 fts16 tc order-pay-pay block fullwidth bg-1 co-1 lh_large" type="button" id="btnSubmit"
           value="立即支付"/>
    <c:if test="${isMustPay!=null && isMustPay==1}">
        <input class="mt10 fts16 tc order-pay-pay block fullwidth bgf co-4 lh_large" type="button" id="subtn"
               value="取消订单"/>
        <!-- <span class="fts16 btn_ground btn_sround padl15 padr15 fr ml5 oitem-link" style="margin-top:10px;margin-right:10px;"
        id="subtn">&nbsp;&nbsp;取消&nbsp;&nbsp;</span> -->
    </c:if>
    <div class="padl10 padr10 padt10 padb10 fts14">
        <div class="typo_dgray lh_def">为什么有些订单会出现支付倒计时？</div>
        <div class="typo_gray lh_nor mb10">答：部分医院要求预约时必须支付挂号费，所以您需要在相应时间内完成支付，否则预约订单将会自动取消。</div>
    </div>
    <input type="hidden" name="method" id="method" value="14"/>
</form>
<form action="${ctx }/act/order/cancelOrder.do?r=<%=System.currentTimeMillis() %>" method="post" id="cancel_form">
    <input type="hidden" name="unitId" value="${map.UNIT_ID}" id="unitId"/>
    <input type="hidden" name="orderNo" value="${map.ORDER_NO}" id="orderNo"/>
    <input type="hidden" name="yuyueId" value="${map.YUYUE_ID}" id="yuyueId"/>
    <input type="hidden" name="to_date" value="${map.TO_DATE}" id="to_date"/>
    <input type="hidden" value="${map.MEMBER_ID}" id="memberid"/>

</form>
<div class="pshade prompt fullheight fullwidth">
    <div class="prompt-box bgf tc">
        <div class="prompt-main padt10 padb10 padl10 padr10">
            <div class="prompt-title fts16 typo_dgray"></div>
            <div class="prompt-msg fts14 typo_gray"></div>
        </div>
        <div class="prompt-bbox clr fullwidth">
            <a class="prompt-btn prompt-cancel block co-1 fts18" href="javascript:void(0);"></a>
            <a class="prompt-btn prompt-sure block co-1 fts18" href="javascript:void(0);"></a>
        </div>
    </div>
</div>

<!-- 弹层 -->
<div id="id-mask" class="mobile-mask">
    <div class="address-pop">
        <a class="iconfont icon-close" id="pop-close">&#xe606;</a>
        <div class="pop-line"></div>
        <div class="fl-body">
            <h2><b>就诊人资料补充</b></h2>
            <p class="ps">医保支付采取实名制，请先补齐就诊人资料。</p>
            <dl><dt>姓名</dt><dd><input type="text" id="member_name" value=""  disabled="disabled"/> </dd></dl>
            <dl><dt>身份证信息</dt><dd><input type="text" id="member_idcard" maxlength="18"/> </dd></dl>
            <input type="hidden" id="member_birthday"/>
            <input type="hidden" id="member_sex"/>
            <p class="co-4"><b>身份证信息保存后不可修改,请仔细核对!</b></p>
            <input type="submit" value="提交" class="submit" id="member_info_submit"/>
            <div class="clearfix"></div>
        </div>
    </div>
</div>

<div id="OverDeadLineWindow" class="mask hide take01">
    <div class="takenumber" >
        <h2>系统提示</h2>
        <p>已超过订单支付截止时间，请到医院现场支付。</p>
        <div class="takebtn">
            <a href="javascript:void(0);" style="width: 100%;" id="OverDeadLineButton">我知道了</a>
        </div>
    </div>
</div>

<script>
    var sendUrl = "";
	var is_channel_bill_pay='${is_channel_bill_pay}';
	var hosAppOrderCanWeixinZfPay='${hosAppOrderCanWeixinZfPay}';
	var hosAppOrderCanAlipayZfPay='${hosAppOrderCanAlipayZfPay}';
		
    var balance = parseFloat('0');//账户余额
    var gh_amt =  parseFloat('${map.GUAHAO_AMT}');//自费总额
    var social_sercurity_toal = parseFloat('${map.SOCIAL_SECURITY_TOTAL}');//社保总额
    var sub = balance - gh_amt;
    var tips1 = '对不起，您的余额不足，请选择第三方支付平台支付！';
    var tips2 = '您的余额为0.00元，请使用第三方支付方式支付！';
    var confirmYibaoPayBySchFlag = '${confirmYibaoPayBySchFlag}';//下单选择医保标识
    var channel = '${channel}';

    $("#amt").text(gh_amt);//默认自费总额

    var currentObj = $("#payForm li:eq(0)");
    changePayway(currentObj);

    //点击医保支付
    function ybMoney() {
        $("#amt").empty();
        $("#amt").text(social_sercurity_toal);
    }

    //点击自费总额
    function zfMoney() {
        $("#amt").empty();
        $("#amt").text(gh_amt);
    }

    //切换支付方式
    function changePayway(obj) {
        obj.find("a").addClass("paybor");
        obj.siblings("li").find("a").removeClass("paybor");
    }

    (function () {
        var trueName='${trueName}';
        var is_must_pay = '${isMustPay}';
        var unitId = '${map.UNIT_ID}';
        var orderNo = '${map.ORDER_NO }';
        var yuyue_id = '${map.YUYUE_ID}';
        var istoday = '${map.TO_DATE_IS_CUR_DAY_PAY}';//是否是当天 1当天 -1非当天
        var isCanPay = '${map.canPayFlag}';//是否可支付
        var isCheckIdCard ='${map.isCheckIdCard2Rule}';//支付前是否需要验证身份证号
        var cardYuyue ='${map.CARD}';//预约下单时存在预约里的身份证号

        //只有回调才会显示
        var bindMedicalIsSuccess = "${childBindMedicalFlage}";
        var bindMedicalIsSuccessUrl = "${bindMedicalIsSuccessUrl}"
        if(bindMedicalIsSuccess == '1'){
            pop_tips_show("温馨提示","绑定社保卡成功");
            //三秒自动隐藏
            setTimeout(close_pop_tips_show,3000);
        }else if(bindMedicalIsSuccess == '-1'){
            var content = "社保卡绑定失败，请重试。";
            pop_two_button_tips_show("温馨提示",content,oksubmit,"重试",close_two_button_tips,"我知道了");

        }else if(bindMedicalIsSuccess == '2'){
            sendUrl = bindMedicalIsSuccessUrl;
            window.location = sendUrl;
        }else if(bindMedicalIsSuccess == '4'){
            pop_tips_social_pay_no_first_adult('${pop_tips_social_pay_text_no_first_adult}');

        }else if(bindMedicalIsSuccess == '5'){
            pop_tips_social_pay_name_no_match_adult('${pop_tips_social_pay_text_name_no_match_adult}');

        }else if(bindMedicalIsSuccess == '6'){
            sendUrl = bindMedicalIsSuccessUrl;
            pop_tips_social_pay_name_no_match_child('${pop_tips_social_pay_text_name_no_match_child}');
        }
        <%-- 不强制支付，则为预约挂号，查询支付截止时间 --%>
        if(is_must_pay!="1"){
            $.ajax({
                url: '${webroot}/ajax/queryOrderDeadLine.do?r=<%=System.currentTimeMillis() %>',
                type: 'post',
                data: {'unit_id': unitId,
                    'dep_id':'${map.DEP_ID}',
                    'doc_id':'${map.DOCTOR_ID}',
                    'sch_date':'${map.TO_DATE}',
                    'begin_time':'${map.BEGIN_TIME}',
                    'end_time':'${map.END_TIME}'
                },
                dataType: 'json',
                success : function(data) {
                    if(data!=""){
                        $("#orderDeadLine").val(data);
                    }
                }
            });
        }
        if (is_must_pay == '1') {//强制支付
            $('#yb a').removeClass('paybor');
    		//此处新加判断 is_channel_bill_pay!='1'，因为app进来的请求页面会出现两个支付图标都选中的情况。(原代码 无判断，直接$('#zf a').addClass('paybor')   )start....
        	if(is_channel_bill_pay!='1'){
            	$('#zf a').addClass('paybor');       		
        	}
    		//end....
            if (is_must_pay == '1') {
                $("#yibao").text("预约此医生应立刻付款，因此无法使用医保");
            }
            var countDown = function () {
                var ele = $(".order-pay-time");
                var min, sec, s, str;
                var s = parseInt(ele.attr("data-time")) - 1;
                if (s >= 0) {
                    ele.attr("data-time", s);
                    min = Math.floor(s / 60);
                    sec = s - min * 60;
                    str = formatNum(min) + ":" + formatNum(sec);
                    if (str == '00:00') {//超时，取消订单，进入我的挂号主页
                        window.location.href = '${ctx}/act/order/cancelOrder.do?r=<%=System.currentTimeMillis() %>&unitId=' + unitId + '&orderNo=' + orderNo + '&yuyueId=' + yuyue_id + '&cancel_status=1';
                        return;
                    }
                    $(ele).text(str);
                    setTimeout(function () {
                        countDown();
                    }, 1000);
                }
            };

            function formatNum(num) {
                if (num.toString().length <= 1) {
                    num = "0" + num;
                }
                return num;
            }

            countDown();
        }

        if(confirmYibaoPayBySchFlag=="true" && channel=="weixin"){
            $("#input_pay_method").val("25");
        }else if(confirmYibaoPayBySchFlag=="true" && channel=="alipay"){
            $("#input_pay_method").val("22");
        }


        var aob = $(".payway li").find(".paybor");
        $("#input_pay_method").val(parseInt(aob.attr("data-method")));
        $(".payway li").bind("click", function () {
            var obj = $(this);
            <%--支付宝开通社保，预约挂号非当天支付方式选中的事件撤销--%>
            if(typeof(obj.find("a").attr("data-method"))=='undefined'){
                return ;
            }
            changePayway(obj);
            $("#method").val(parseInt(obj.find("a").attr("data-method"))).removeClass("disabled").trigger("change");
            $("#input_pay_method").val(parseInt(obj.find("a").attr("data-method")));
            var val = $("#input_pay_method").val();
            if (val == 22 || val == 25) {
                if (is_must_pay != "1") {
                    $("#yibao").text("选择医保支付，后续的就诊费用可使用医保支付");
                }
                $("#iconyibao").removeClass("co-4").addClass("co-6");
                $(".yibao").show();
                //#22659 医保支付须知弹窗, 支持配置 唐莉 2017/02/16
                var pop_tips_social_pay_text = "${pop_tips_social_pay_text}";
                if (null == pop_tips_social_pay_text || "" == pop_tips_social_pay_text || "null" == pop_tips_social_pay_text) {
                    pop_tips_social_pay_text = "1.提交订单后请尽快完成支付，逾时号源作废<br><br>2.实名认证的姓名与就诊人姓名一致才能使用医保支付<br><br>3.目前仅支持一档医保（综合医保）的在线挂号及诊间支付，二三档医保请到人工窗口交费";
                }
                pop_tips_show_with_function("医保支付须知",pop_tips_social_pay_text);
            }else if (val == 13 || val == 14 || val ==27 || val == 15 || val == 29){
                if (is_must_pay != "1") {
                    $("#yibao").text("选择自费支付, 后续的就诊费用将不可使用医保支付");
                }
                $("#iconyibao").removeClass("co-6").addClass("co-4");
                $(".yibao").hide()
            }else if(val==221||val==251){
                if (is_must_pay != "1") {
                    $("#yibao").text("选择医保支付，后续的就诊费用可使用医保支付");
                }
                $("#iconyibao").removeClass("co-4").addClass("co-6");
                //少儿医保
                var yuyueChildSocialTips = "${yuyueChildSocialTips}";
                if (null == yuyueChildSocialTips || "" == yuyueChildSocialTips || "null" == yuyueChildSocialTips) {
                    yuyueChildSocialTips = "1.提交订单后请尽快完成支付，逾时号源作废<br><br>2.实名认证的姓名与就诊人姓名一致才能使用医保支付<br>";
                }
                $("#input_pay_method").val(val)
                var yibaoGuideBtn = ${not empty yibaoGuideBtn};
                if(yibaoGuideBtn && val==251){
                    pop_two_button_tips_show("医保支付须知",yuyueChildSocialTips,"close_two_button_tips","我知道了","yibaoGuide","${yibaoGuideBtn}");
                }else{
                    pop_tips_show_with_function("医保支付须知",yuyueChildSocialTips);
                }

            }
        });

        $("#btnSubmit").bind('click', function (e) {

        	//如果是渠道过来的支付请求且 未开通微信且未开通支付宝支付 则拒绝支付
        	if(is_channel_bill_pay=='1'&&hosAppOrderCanWeixinZfPay!='1'&&hosAppOrderCanAlipayZfPay!='1' && ${(sessionScope.nychannel != "ny_zhonghang_android" && sessionScope.nychannel !="ny_zhonghang_ios")}){
        		alert("未开通支付功能，暂不支持支付！")
        		return false;
        	}
            if(confirmYibaoPayBySchFlag != "true" ) {
                var payw = $("#input_pay_method").val();
                if (payw == 221) {
                    $("#socialType").val("childSocial")
                    $("#input_pay_method").val("22")
                } else {
                    $("#socialType").val("")
                }
                if (payw == 251) {
                    $("#socialType").val("childSocial")
                    $("#input_pay_method").val("25")
                }
            }
            if(confirmYibaoPayBySchFlag=="true"&&$("#socialType").val()=='childSocial'){
                if(channel=="weixin"){
                    $("#input_pay_method").val("25")
                }else if (channel=="alipay") {
                    $("#input_pay_method").val("22")
                }
            }
            //预约挂号下单确认选择医保支付
            if(confirmYibaoPayBySchFlag == "true"&&$("#socialType").val()!='childSocial'){

			   if(channel=="weixin"){
                    $("#input_pay_method").val("25")
               }else if (channel=="alipay") {
                   $("#input_pay_method").val("22")
               }

               if(istoday=="1" || $("#ISOpenYibaoAdvancePay").val()=='1'){
                   $("#payForm").submit();
                   //按钮置灰，并且文案变为处理中
                   btnSubmitLoading();
               }else{
                   pop_tips_show("医保挂号须知","选择医保支付的用户请于就诊当天支付挂号费用，如需自费支付，请取消订单重新下单。");
               }
                return false;
            }

            var payway = $("#input_pay_method").val();
            if (payway == 0) {
                nytips('请选择支付方式！');
                return false;
            }
            //状态22为支付宝社保支付, 25微信社保
            if (payway == 22 || payway == 25) {
                //验证是否为当天挂号
                if (istoday == '1' || ${ISOpenYibaoAdvancePay=='1'}) {
                    //检查是否超过支付截止时间
                    if(!checkDeadLine()){
                        return false;
                    }
                    //医保支付强制绑定身份证
                    checkIdCardIsExist();
                } else {
                    alert("请于就诊日当天支付");
                }
            }else {
                if(!checkDeadLine()){
                    return false;
                }
                if(!isCanPay4CheckIdCard()){
                    return false;
                }

                $("#payForm").submit();
                //按钮置灰，并且文案变为处理中
                btnSubmitLoading();
            }
        });
        function  isday(){
            var curdatestr = new Date().toDateString();
            var curdate = new Date(curdatestr);
            //如果为医保支付校验日期是否是就诊日当天
            var to_data_str = $("#to_date").val()+ " 00:00:00";
            to_data_str = to_data_str.replace(/-/g,"/");
            var to_date = new Date( to_data_str);
            if (to_date.getTime() == curdate.getTime()) {
                return true;
            } else {
                return false;
            }
        }

        //检查是否绑定医保卡
        function checkIsMedicalCard() {
            var memberid = $('#memberid').val();
            var issubmit = false;
            var socialType=$("#socialType").val();
            if(socialType!='childSocial'){
                socialType='adultSocial';
            }
            var resData='';
            var method;//当天挂号、预约挂号
            if(${'1' == map.REALTIME}){
                method='cursch'
            }else{
                method='sch'
            }
            if("childSocial"==socialType&&$("#input_pay_method").val() == '25'){
                resData={'memberId': memberid,
                    'orderNo':'${map.ORDER_NO}',
                    'medical_state':'order_pay',
                    'socialType':socialType,
                    'patient_name':trueName,
                    'payway':$("#input_pay_method").val(),
                    'pay_balance':$("#input_pay_balance").val(),
                    'memberId':$("#input_memberId").val(),
                    'socialType':$("#socialType").val(),
                    'unitId':'${map.UNIT_ID}',
                    'orderType':'1',
                    'method':method,
                    'dep_id':'${map.DEP_ID}',
                    'doc_id':'${map.DOCTOR_ID}',
                    'branchId':'${map.BRANCH_ID}'
                };
            }else{
                resData={'memberId': memberid,'orderNo':'${map.ORDER_NO}','medical_state':'order_pay','socialType':socialType,'patient_name':trueName,'unitId':'${map.UNIT_ID}','method':method,'dep_id':'${map.DEP_ID}','doc_id':'${map.DOCTOR_ID}'};
            }
            //医保支付必须绑医保卡
            $.ajax({
                url: ctx + "/ismedicalcard.do",
                async: false,
                type: "post",
                data:resData,
                success: function (data) {
                    if (data.success) {
                        issubmit = true;
                    } else {
                        if (data.object.code == '1'||data.object.code == '2' || data.object.code == '3') {
                            //状态为3表示已从社保局查出社保卡号, 但是未在用户中心绑定,暂不处理
                            window.location = data.object.url;
                        }else if(data.object.code=='4'){
                            pop_tips_social_pay_no_first_adult('${pop_tips_social_pay_text_no_first_adult}');
                            return;
                        }else if (data.object.code == '5') {  //状态为5表示成人就诊人姓名不匹配
                            pop_tips_social_pay_name_no_match_adult('${pop_tips_social_pay_text_name_no_match_adult}');
                            return;
                        }else if (data.object.code == '6') {   //状态为6表示少儿就诊人姓名不匹配
                            sendUrl = data.object.url;
                            pop_tips_social_pay_name_no_match_child('${pop_tips_social_pay_text_name_no_match_child}');
                            return;
                        }else {
                            pop_tips_show_with_function("医保支付须知",data.msg);
                        }
                    }
                    if(issubmit){
                        //再次验证是否是当天支付
                        if (istoday=='1' || ${ISOpenYibaoAdvancePay=='1'}) {
                            $("#payForm").submit();
                            //按钮置灰，并且文案变为处理中
                            btnSubmitLoading();
                        } else {
                            pop_tips_show_with_function("医保支付须知","请于就诊日当天支付");
                            return;
                        }
                    }
                }
            });
        }

        function btnSubmitLoading(){
            $("#btnSubmit").val("处理中...");
            $("#btnSubmit").attr("disabled",true);
            $("#btnSubmit").css("background","#f5f5f5");
            $("#btnSubmit").css("color","black");
        }

        //医保支付强制绑定身份证
        function checkIdCardIsExist() {
            var memberId = $('#memberid').val();
            $.ajax({
                url: '${webroot}/act/order/checkIdCardIsExist.do?r=<%=System.currentTimeMillis() %>',
                type: 'post',
                data: {'memberId': memberId,'checkMedicalCard':'1','socialType':$("#socialType").val()},
                dataType: 'json',
                success : function(data) {
                    $("#member_name").val("");
                    $("#member_idcard").val("");
                    $("#member_birthday").val("");
                    $("#member_sex").val("");
                    if(data.code=="checkMedicalCard_fail"){
                        pop_tips_show("医保支付须知","选择付费类型为医保支付, 必须绑定就诊卡!");
                        return false;
                    }else if(data.code=="true"){
                        //检查就诊人信息符合规范
                        $(this).attr('disabled', true);
                        $(this).addClass('is-disabled');
                        //检查是否绑定医保卡
                        checkIsMedicalCard()
                    }else{
                        //检查就诊人信息不符合规范，显示补全资料的窗口
                        $("#member_idcard").removeAttr("disabled");
                        if(data.data.name != ''){
                            $("#member_name").val(data.data.name);
                        }
                        if(data.data.idCard != ''){
                            $("#member_idcard").val(data.data.idCard);
                            $("#member_idcard").attr("disabled","disabled");
                        }
                        if(data.data.birthday != ''){
                            $("#member_birthday").val(data.data.birthday);
                        }
                        $('#id-mask').show();
                        return false;
                    }
                }
            });
        };

        //身份证输入完成后，自动填充出生日期和性别
        $("#member_idcard").on('blur',function(){
            var memeberIdCard = x2X($.trim($("#member_idcard").val()));
            var memberBirthday = $.trim($("#member_birthday").val());

            if(checkCertificate(${ctx},"1",memeberIdCard)){
                if(memeberIdCard.length==18){
                    //出生日期 ---用户已有出生日期的，不可修改，不需要自动填充
                    if (memberBirthday == '' || memberBirthday == null || memberBirthday == 'null') {
                        var member_birthday = memeberIdCard.substr(6,4)+"-"+memeberIdCard.substr(10,2)+"-"+memeberIdCard.substr(12,2);
                        $("#member_birthday").val(member_birthday);
                    }
                    //性别
                    var member_sex = memeberIdCard.substr(16,1)%2==0 ? 1:0;
                    if(member_sex  == 0){
                        $("#member_sex").val(member_sex);
                    }else if(member_sex  == 1){
                        $("#member_sex").attr(member_sex);
                    }
                }
            }
        });

        //补全资料的提交
        $("#member_info_submit").on('click',function(e){
            var memberId =$('#memberid').val();
            var memeberIdCard = x2X($("#member_idcard").val());
            var memberBirthday = $("#member_birthday").val();
            var memberSex = $("#member_sex").val();
            if(memberSex!=0 && memberSex!=1){
                alert("身份证号码有误，请仔细检查!");
                return;
            }else if(memberBirthday==''){
                alert("身份证号码有误，请仔细检查!");
                return;
            }else if(memeberIdCard==''){
                alert("医保支付, 身份证号不可为空！");
                return;
            }else {
                memberBirthday = memberBirthday.replace(/-/g, '/');
                if(!checkCertificate(${ctx},"1",memeberIdCard)){
                    alert("身份证号码有误，请仔细检查!");
                    $("#member_idcard").focus();
                    return;
                }
                //保存用户信息
                $.ajax({
                    url: '${webroot}/act/order/saveMember.do?r=<%=System.currentTimeMillis() %>',
                    type: 'post',
                    data: {
                        'memberId': memberId,
                        'memeberIdCard': memeberIdCard,
                        'memberBirthday': memberBirthday,
                        'memberSex': memberSex
                    },
                    dataType: 'json',
                    success : function(data) {
                        if(data.code=="true"){
                            //就诊人补全信息成功
                            $(this).attr('disabled', true);
                            $(this).addClass('is-disabled');
                            $('#id-mask').hide();
                            //检查是否绑定医保卡
                            checkIsMedicalCard();
                        }else{
                            alert(data.data);
                            return;
                        }
                    }
                });
            }
        });

        $('#pop-close').on('click',function(){
            $('#id-mask').hide();
        });

        $("#subtn").click(function () {
            showPrompt("取消订单", "确认取消订单？", "sync", [{"name": "否"}, {
                "name": "是", "events": function () {
                    $("#cancel_form").submit();
                }
            }]);
        });

        <%-- 检查是否为截止时间 --%>
        function checkDeadLine(){
            var orderDeadLine=$("#orderDeadLine").val();
            //不为curDep，说明有值，不为当天挂号，是预约挂号
            if(orderDeadLine!="curDep" && isCanPay==1){
                //当前时间已超过截止时间，弹出窗口，提示用户
                $("#OverDeadLineWindow").show();
                return false;
            }
            return true;
        }
        <%-- 检查是否需要验证身份证号, 如未绑定身份证表示不可支付 1需要验证身份证号 0不需要验证--%>
        function isCanPay4CheckIdCard(){
            if(isCheckIdCard != 1){
                return true;
            }

            //验证下单时预约表是否存身份证
            if(isBindIdCard()){
                return true;
            }

            //未绑定身份证号弹框提示 如果从取号列表进来的订单 弹窗后跳转至取号列表
            popTipsNonPay();
            return false;
        }


        //验证下单时order表中是否有身份证
        function isBindIdCard() {
            return !!cardYuyue!=null && cardYuyue!='' && cardYuyue!='null';
        }
        //未绑定身份证弹框提示. 点击我知道了, 取号入口进来的订单回到取号列表 我的挂号进来的订单回到我的挂号列表
        function popTipsNonPay() {
            var url = '';
            if(${quhaoTag=='1'}){
                url = "${webroot}/act/weixin_jzqh.do?r=<%=System.currentTimeMillis() %>&unit_id="+unitId+"&unitId="+unitId;
            }else{
                url = "${webroot}/act/order/orderList.do?r=<%=System.currentTimeMillis() %>&unit_id="+unitId+"&unitId="+unitId;
            }
            pop_tips_url_notitle("您在预约挂号时未填写身份证信息，不能在线支付挂号费及在线取号，请到医院窗口支付挂号费", url);
        }

        //点击"我知道了",关闭窗口
        $("#OverDeadLineButton").click(function () {
            $("#OverDeadLineWindow").hide();
        });

    })();

    function yibaoGuide() {
        window.location.href = "${yibaoGuideHref}";
    }

</script>
<!-- 版权标识 -->
<jsp:include page="/inc/copyright.jsp"/>
<!-- 百度统计 -->
<jsp:include page="/inc/footer.jsp"/>
</body>
</html>
