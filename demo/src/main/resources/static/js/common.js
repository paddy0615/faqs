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