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