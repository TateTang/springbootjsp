package com;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class Test {

    public static void main1(String[] args) {
        String inHosReg = null;
        StringBuilder sb = new StringBuilder();
        sb.append("http://www.baidu.com?inHosReg=").append(inHosReg==null?"":inHosReg);
        sb.append("&aaaaaaaaaaaa");
        log.info("url={}",sb);
    }

    public static void main(String[] args) {
        //String str="{attach=  sfsd  , sub_mch_id=10000100, time_end=20140903131540, openid=oUpF8uMEb4qRXf22hE3X68TekukE, bank_type=CFT, return_code=SUCCESS}";
        String str="{ISOpenYibaoAdvancePay=1, pop_tips_social_pay_text=, social_icon=https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1571045723161&di=04b9e2deccd9521930fa6522dacda24e&imgtype=0&src=http%3A%2F%2Fbpic.588ku.com%2Felement_origin_min_pic%2F01%2F40%2F32%2F98573cf75c3bf04.jpg\t, trueName=钟丽娜, yuyueMustIDAgeLimit=, pop_tips_zifei_pay_text=, memberType=1, social_text=医保结算, jianhangAggregate=0, isMustPay=1,  curselectYiBaoFlag=1, isHideChoosePaytypeTip=1,  must_pay_status=true, notpayOrderInstruction=非强制支付北大----预约、当天 payOrderInstruction=强制支付北大----预约、当天 yibaoGuideHref=https://www.baidu.com, diffTime=600, memberNumberLimit=12, beginTime=1606727355409, socialType=, payway=14, selectYiBaoFlag=1,  unitCardValue=1, checkCodeFlag=1, yibaoGuideBtn=医保绑定指导, yuyueUserType=0, memberId=50784257, jianhang=0, channel=weixin, isAllowPay=1}";
        Map<String,String> map=mapStringToMap(str);
        System.out.println(map);
    }

    public static Map<String,String> mapStringToMap(String str){
        str=str.substring(1, str.length()-1);
        String[] strs=str.split(",");
        Map<String,String> map = new HashMap<String, String>();
        for (String string : strs) {
            String key=string.split("=")[0];
            String value=string.split("=")[1];
            map.put(key.trim(), value);
        }
        return map;
    }
}
