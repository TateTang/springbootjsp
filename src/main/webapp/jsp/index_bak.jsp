<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Insert title here</title>


    <style>
        .container{
            position:absolute;
            bottom:60px;
        }
        #footer {
            position: absolute;
            bottom: 0;
            width: 100%;
            height: 60px;/*脚部的高度*/
            background: #6cf;
            clear:both;
        }
    </style>
</head>
<body>
<div class="container" style="width: 100%">

    <iframe src="http://more.free.frpboot.com/user/index2" id="external-frame" width="100%" frameborder=”no” border=”0″
            marginwidth=”0″ marginheight=”0″ scrolling=”no” allowtransparency=”yes”></iframe>
</div>
<div id="footer" style="background: aquamarine">

</div>
</body>
<script language="JavaScript">
    // if (window != top) {
    //     top.location.href = location.href;
    // }

    function setIframeHeight(iframe) {
        if (iframe) {
            var iframeWin = iframe.contentWindow || iframe.contentDocument.parentWindow;
            if (iframeWin.document.body) {
                iframe.height = iframeWin.document.documentElement.scrollHeight || iframeWin.document.body.scrollHeight;
            }
        }
    };

    window.onload = function () {
        setIframeHeight(document.getElementById('external-frame'));
    }
    var iframe = document.getElementById('external-frame');
    var h = document.documentElement.clientHeight+60;
    console.log(h)
    iframe.style.height = h+"px";

    //var downFrame = document.getElementById("external-frame");
    /*downFrame.onload=function(e){
        console.log("======onload")
        var _iframe = document.getElementById('external-frame').contentWindow;
        var _div =_iframe.document.getElementById('test3');
        if(_div){
            console.log(_div.value)
            document.getElementById('footer').style.display = "none";
            document.getElementById("container").style.bottom = "0px";
        }
    };*/
</script>


</html>