(function(w){
    w.clicked = function(url){
        w.location.href = url;
    }

    w.goBack = function () {
        w.history.back(-1);
    }

    w.reloadRoute = function () {
        w.location.reload();
    };

})(window);

// 对字符串;转义,解义escape 和 unescape; encodeURI 和 decodeURI

// 获取url参数
function GetUrlParam(paraName) {
    var url = document.location.toString();
    var arrObj = url.split("?");
    if (arrObj.length > 1) {
        var arrPara = arrObj[1].split("&");
        var arr;
        for (var i = 0; i < arrPara.length; i++) {
            arr = arrPara[i].split("=");
            if (arr != null && arr[0] == paraName) {
                return arr[1];
            }
        }
        return "";
    }
    else {
        return "";
    }
}

// 编辑页，判断是否有修改
function comGoCancel(url) {
    var myconfirm = layer.confirm("Has the content been modified, is it determined to leave?", {
        title:'Information',
        btn: ['OK','Cancel'] //按钮
    }, function(){
        if("" != url){
            clicked(url); // 跳url
        }else{
            goBack(); // 返回上一页
        }
        layer.close(myconfirm);
    }, function(){
        layer.close(myconfirm);
    });
}

// 搜索框文字
function selectTest(langId){
    var t = "Please enter keyword(s) to search separated by space";
    if(langId == 1){
        t = "請輸入關鍵字搜索，如多於一個關鍵字，請用空格鍵隔開";
    }else if(langId == 2){
        t = "请输入关键字搜索，如多于一个关键字，请用空格键隔开";
    }else if(langId == 3){
        t = "請輸入關鍵字搜索，如多於一個關鍵字，請用空格鍵隔開";
    }else if(langId == 4){
        t = "キーワードを入力し、検索くださいませ、もし一つ以上あれば、スペイスで分けてくださいませ";
    }else if(langId == 5){
        t = "키 워드를 입력하시고 검색하시기 바랍니다.  만약 키워드는 한 글자 이상되면 중간에서  스페이스 바로 입력하시기 바랍니다";
    }
    return t+".";
}
function selectTest1(langId){
    var t = "Search";
    if(langId == 1){
        t = "搜索";
    }else if(langId == 2){
        t = "搜索";
    }else if(langId == 3){
        t = "搜索";
    }else if(langId == 4){
        t = "検索";
    }else if(langId == 5){
        t = "검색";
    }
    return t+"...";
}
function selectTestUnll(langId){
    var t = "Sorry for not finding relevant results, please try to enter other keywords to search or try to use the blank key to separate keywords, such as query change flight, try to search change flight";
    if(langId == 1){
        t = "抱歉未能找到相關結果，請試試輸入其他關鍵字再進行搜索或嘗試用空白鍵隔開關鍵字，例如查詢更改航班，試試搜索更改 航班";
    }else if(langId == 2){
        t = "抱歉未能找到相关结果，请试试输入其他关键词再进行搜索或尝试用空格键隔开关键词，例如查询更改航班，试试搜索更改 航班";
    }else if(langId == 3){
        t = "抱歉未能找到相關結果，請試試輸入其他關鍵字再進行搜索或嘗試用空白鍵隔開關鍵字，例如查詢更改航班，試試搜索更改 航班";
    }else if(langId == 4){
        t = "申し訳ございません、これに关しての検査结果がございません、もし良ければ他のキーワードで検索し、もしくはスペース键でご検索くださいませ、例えば、航空便を変更する时には、“変更、便”で検索をお试しくださいませ。";
    }else if(langId == 5){
        t = "죄송합니다. 검색 결과가 없습니다 .일반적인 검색어로 다시 검색해 보세요.";
    }
    return t+".";
}

// 热点laber
function hotspotTest(langId){
    var t = "Frequently Asked Questions";
    if(langId == 1){
        t = "常見問題";
    }else if(langId == 2){
        t = "常见问题";
    }else if(langId == 3){
        t = "常見問題";
    }else if(langId == 4){
        t = "よくあるご質問";
    }else if(langId == 5){
        t = "자주묻는 질문";
    }
    return t;
}

// 反馈laber
function feedbackTest(langId){
    var t = "Does it help?";
    if(langId == 1){
        t = "這有幫助嗎?";
    }else if(langId == 2){
        t = "这有帮助吗?";
    }else if(langId == 3){
        t = "這有幫助嗎?";
    }else if(langId == 4){
        t = "お力になれましたか。";
    }else if(langId == 5){
        t = "이것이 도움이 될겁니까";
    }
    return t;
}

// laber1 提交
function commonLabel1(langId) {
    var t = "submit";
    if(langId == 1){
        t = "提交";
    }else if(langId == 2){
        t = "提交";
    }else if(langId == 3){
        t = "提交";
    }else if(langId == 4){
        t = "確認";
    }else if(langId == 5){
        t = "제출";
    }
    return t;
}
