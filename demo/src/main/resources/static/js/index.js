var myapp = angular.module("myapp",[]);
myapp.directive('onFinishRenderFilters', ['$timeout', function ($timeout) {
    return {
        restrict: 'A',
        link: function(scope,element,attr) {
            if (scope.$last === true) {
                var finishFunc=scope.$parent[attr.onFinishRenderFilters];
                if(finishFunc)
                {
                    finishFunc();
                }
            }
        }
    };
}])
// online chat
function onlineChat(langId) {
    if(langId == 1){
        // 香港 (繁體)
        var se = document.createElement('script'); se.type = 'text/javascript'; se.async = true;
        se.src = 'https://storage.googleapis.com/sonic-teleservices/js/8168ca61-a381-422e-9803-44e04dabc878.js';
        var done = false;
        se.onload = se.onreadystatechange = function() {
            if (!done&&(!this.readyState||this.readyState==='loaded'||this.readyState==='complete')) {
                done = true;
                SonicTeleservices.setTitle("聯絡我們!"); // for the pre-chat form
                SonicTeleservicesChat.setTitle("透過線上客服與我們聯絡"); // for the chat-form
                SonicTeleservices.setProactiveAutocloseDelay(0.5);// delay proactive auto close after 30 sec
            }
        };
        var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(se, s);
    }else if(langId == 2){
        // 中国 简体
        var se = document.createElement('script'); se.type = 'text/javascript'; se.async = true;
        se.src = 'https://storage.googleapis.com/sonic-teleservices/js/cd66238e-805c-4fd2-b3e1-ccabddaf8a12.js';
        var done = false;
        se.onload = se.onreadystatechange = function() {
            if (!done&&(!this.readyState||this.readyState==='loaded'||this.readyState==='complete')) {
                done = true;
                SonicTeleservices.setTitle("联络我们!"); // for the pre-chat form
                SonicTeleservicesChat.setTitle("透过在线客服与我们联络"); // for the chat-form
                SonicTeleservices.setProactiveAutocloseDelay(0.5);// delay proactive auto close after 30 sec
            }
        };
        var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(se, s);
    }else if(langId == 3){
        // 台灣 (繁體)
        var se = document.createElement('script'); se.type = 'text/javascript'; se.async = true;
        se.src = 'https://storage.googleapis.com/sonic-teleservices/js/8168ca61-a381-422e-9803-44e04dabc878.js';
        var done = false;
        se.onload = se.onreadystatechange = function() {
            if (!done&&(!this.readyState||this.readyState==='loaded'||this.readyState==='complete')) {
                done = true;
                SonicTeleservices.setTitle("聯絡我們!"); // for the pre-chat form
                SonicTeleservicesChat.setTitle("透過線上客服與我們聯絡"); // for the chat-form
                SonicTeleservices.setProactiveAutocloseDelay(0.5);// delay proactive auto close after 30 sec
            }
        };
        var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(se, s);
    }else if(langId == 4){
        // 日本 (日本語))
        var se = document.createElement('script'); se.type = 'text/javascript'; se.async = true;
        se.src = 'https://storage.googleapis.com/sonic-teleservices/js/d1fa9911-1e41-4df1-a090-a1fd9454a030.js';
        var done = false;
        se.onload = se.onreadystatechange = function() {
            if (!done&&(!this.readyState||this.readyState==='loaded'||this.readyState==='complete')) {
                done = true;
                SonicTeleservices.setTitle("連絡先情報!"); // for the pre-chat form
                SonicTeleservicesChat.setTitle("私たちとチャットしましょう"); // for the chat-form
                SonicTeleservices.setProactiveAutocloseDelay(0.5);// delay proactive auto close after 30 sec
            }
        };
        var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(se, s);
    }else if(langId == 5){
        // 대한민국(한국어)
        var se = document.createElement('script'); se.type = 'text/javascript'; se.async = true;
        se.src = 'https://storage.googleapis.com/sonic-teleservices/js/ed84ba43-860a-43fc-87ac-29e1fb8ce6ab.js';
        var done = false;
        se.onload = se.onreadystatechange = function() {
            if (!done&&(!this.readyState||this.readyState==='loaded'||this.readyState==='complete')) {
                done = true;
                SonicTeleservices.setTitle("연락처!"); // for the pre-chat form
                SonicTeleservicesChat.setTitle("연락해 주십시오."); // for the chat-form
                SonicTeleservices.setProactiveAutocloseDelay(0.5);// delay proactive auto close after 30 sec
            }
        };
        var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(se, s);
    }else{
        // Hong Kong (EN)
        var se = document.createElement('script'); se.type = 'text/javascript'; se.async = true;
        se.src = 'https://storage.googleapis.com/sonic-teleservices/js/ff4b2b7a-dfbb-47d8-9e51-766c426eb3cb.js';
        var done = false;
        se.onload = se.onreadystatechange = function() {
            if (!done&&(!this.readyState||this.readyState==='loaded'||this.readyState==='complete')) {
                done = true;
                SonicTeleservices.setTitle("Contact us!"); // for the pre-chat form
                SonicTeleservicesChat.setTitle("Chat with us!"); // for the chat-form
                SonicTeleservices.setProactiveAutocloseDelay(0.5);// delay proactive auto close after 30 sec
            }
        };
        var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(se, s);
    }
}
// www.hkexpress.com
function getHKE(langId){
    var suffix = "";
    if(langId == 1){
        // 香港 (繁體)
        suffix = "zh-hk";
    }else if(langId == 2){
        // 中国 简体
        suffix = "zh-cn";
    }else if(langId == 3){
        // 台灣 (繁體)
        suffix = "zh-tw";
    }else if(langId == 4){
        // 日本 (日本語))
        suffix = "ja";
    }else if(langId == 5){
        // 대한민국(한국어)
        suffix = "ko";
    }else if(langId == 6){
        // Hong Kong (EN)
        suffix = "en-hk";
    }
    clicked("https://www.hkexpress.com/"+suffix);
}



// index
myapp.controller("indexController",["$scope","$http",function ($scope, $http) {
    // 设置默认,langId==6语言，英文;catId = 0默认选第二个
    $scope.langId = GetUrlParam("langId")==""?6:GetUrlParam("langId");
    $scope.catId =  GetUrlParam("catId")==""?0:GetUrlParam("catId");
    $scope.lang_cout = 5;
    $scope.isGetUrl = false;
    $scope.cat_title = "";
    //indexShow 显示
    $scope.indexShow = true;
    $scope.searchShow = false;
    // 初始化
    into($scope.langId,$scope.catId);
    onlineChat($scope.langId);
    function into(langID,catId){
        $http({
            method : 'post',
            url : ctx + "appJson/getIndex",
            params:{"langId": langID,"catId" : catId}
        }).success(function (data) {
            $scope.isGetUrl = true;
            if(data){
                /* 成功*/
                $scope.result = data.result;
                $scope.langId = data.result.langId;
                $scope.selectTest = selectTest($scope.langId);
                $scope.hotspotTest = hotspotTest($scope.langId);
                angular.forEach($scope.result.languages,function (each) {
                    if($scope.langId == each.id){
                        $scope.problem = each.problem;
                        return;
                    }
                })
                angular.forEach(data.result.categories,function (each) {
                    if(catId == each.id){
                        $scope.cat_title = each.title;
                        return;
                    }
                })
                $scope.lang_cout = data.result.categories.length;
                if($scope.cat_title == ""){
                    $scope.cat_title = data.result.categories[1].title;
                }
            }else{
                /* 失败*/
                layer.alert( 'Abnormal error, please contact the administrator.', {
                    skin: 'layui-layer-lan'
                    ,closeBtn: 0
                });
            }
        })
    }
    // 改导航宽
    $scope.completeRepeat= function(){
        if($scope.lang_cout > 5 ){
            $(".nav-li-text").width(Math.round(960/$scope.lang_cout)-12);
        }
    }
    // 语言事件
    $scope.clickLanguage = function() {
        if($scope.isGetUrl){
            var url = ctx + "appPage/index?langId="+$scope.langId+"&catId="+0;
            clicked(url);
        }
        // 强制更新  $scope.apply();
    }

    // 类别事件,idnex当前下标，id:类别ID
    $scope.clickCategory = function (idnex,cat) {
        if(idnex==0){
            getHKE($scope.langId);
        }else{
            if($scope.isGetUrl){
                $scope.addip(cat.id,0);
                var url = ctx + "appPage/index?langId="+cat.langId+"&catId="+cat.id;
                clicked(url);
            }
        }
    }

    // 详情事件
    var lock = false; //默认未锁定
    $scope.getDetailed = function (dlId) {
        if(!lock) {
            lock = true; // 锁定
            $http({
                method : 'post',
                url : ctx + "appJson/addHotspot",
                params:{"dlId" : dlId}
            }).success(function (data) {
                if(data){
                    $scope.addip(0,dlId);
                    var url = ctx + "appPage/indexDetailed?dlId="+dlId+"&serch=true";
                    clicked(url);
                }else{
                    layer.alert( 'Abnormal error, please contact the administrator or refresh page', {
                        title:'Information',
                        skin: 'layui-layer-lan'
                        ,closeBtn: 0
                    });
                }
            })
        }
    }

    // 添加流量数
    $scope.addip = function (catId,dlId) {
        $http({
            method : "post",
            url : ctx + "appJson/addip",
            params : {"catId":catId,"dlId": dlId}
        }).success(function (data) {

        })
    }

    /* 搜索框  */
    $scope.searchTest = "";
    $scope.getSearch = function (){
        if($scope.searchTest == ""){
            return;
        };
        $http({
            method : "post",
            url : ctx + "appJson/getSearchTags",
            params : {"search": $scope.searchTest,"langId" : $scope.langId}
        }).success(function (data) {
            $scope.searchShow = true;
            $scope.indexShow = false;
            $scope.detaileds =  data.result.detaileds;
        })
    }
    $scope.onKeyup = function(event){
       // $scope.arr1=$filter("filter")(arr,document.getElementById("wei").value);
        var e = event || window.event || arguments.callee.caller.arguments[0];
        if(e && e.keyCode==13){ // enter 键
            $scope.getSearch();
        }
    }



    function info1(){
        $http({
            method : 'post',
            url : ctx + "appJson/getHotspot"
        }).success(function (data) {
            if(data){
                $scope.hotspots = data.result.detaileds;
            }
        })
    }
    // 热点初始化
    info1();

}]);

// indexDetailed
myapp.controller("indexDetailedController",["$scope","$http","$sce",function ($scope, $http, $sce) {
    // 设置默认,langId==6语言，英文;catId = 0默认选第二个
    $scope.dlId = GetUrlParam("dlId");
    $scope.langId = 6;
    $scope.catId = 0;
    $scope.lang_cout = 5 ;
    $scope.isGetUrl = false;
    $scope.detailed ={};
    $scope.searchShow = false;
    // 初始化
    into($scope.dlId);
    function into(dlId){
        $http({
            method : 'post',
            url : ctx + "appJson/getByDetailed",
            params:{"dlId": dlId}
        }).success(function (data) {
            $scope.isGetUrl = true;
            if(data){
                /* 成功*/
                $scope.result = data.result;
                $scope.langId = data.result.langId;
                $scope.dfcount = data.result.dfcount;
                $scope.selectTest = selectTest($scope.langId);
                $scope.hotspotTest = hotspotTest($scope.langId);
                $scope.feedbackTest = feedbackTest($scope.langId);
                onlineChat($scope.langId);
                $scope.detailed = data.result.detailed;
                // 显示内容
                $scope.content = $sce.trustAsHtml($scope.detailed.content);
                angular.forEach($scope.result.languages,function (each) {
                    if($scope.langId == each.id){
                        $scope.problem = each.problem;
                        return;
                    }
                })
                $scope.lang_cout = data.result.categories.length;
            }else{
                /* 失败*/
                layer.alert( 'Abnormal error.', {
                    skin: 'layui-layer-lan'
                    ,closeBtn: 0
                });
            }
        })
    }

    // 改导航宽
    $scope.completeRepeat= function(){
        if($scope.lang_cout > 5 ){
            $(".nav-li-text").width(Math.round(960/$scope.lang_cout)-12);
        }
    }

    // 语言事件
    $scope.clickLanguage = function() {
        if($scope.isGetUrl){
            var url = ctx + "appPage/index?langId="+$scope.langId+"&catId="+0;
            clicked(url);
        }
        // 强制更新  $scope.apply();
    }

    // 类别事件,idnex当前下标，id:类别ID
    $scope.clickCategory = function (idnex,cat) {
        if(idnex==0){
            getHKE($scope.langId);
        }else{
            if($scope.isGetUrl){
                $scope.addip(cat.id,0);
                var url = ctx + "appPage/index?langId="+cat.langId+"&catId="+cat.id;
                clicked(url);
            }
        }
    }

    /* 搜索框  */
    $scope.searchTest = "";
    $scope.getSearch = function (){
        if($scope.searchTest == ""){
            return;
        };
        $http({
            method : "post",
            url : ctx + "appJson/getSearchTags",
            params : {"search": $scope.searchTest,"langId" : $scope.langId}
        }).success(function (data) {
            $scope.searchShow = true;
            $scope.detaileds =  data.result.detaileds;
        })
    }
    $scope.onKeyup = function(event){
        // $scope.arr1=$filter("filter")(arr,document.getElementById("wei").value);
        var e = event || window.event || arguments.callee.caller.arguments[0];
        if(e && e.keyCode==13){ // enter 键
            $scope.getSearch();
        }

    }
    // 详情事件
    var lock = false; //默认未锁定
    $scope.getDetailed = function (dlId) {
        if(!lock) {
            lock = true; // 锁定
            $http({
                method : 'post',
                url : ctx + "appJson/addHotspot",
                params:{"dlId" : dlId}
            }).success(function (data) {
                if(data){
                    $scope.addip(0,dlId);
                    var url = ctx + "appPage/indexDetailed?dlId="+dlId+"&serch=true";
                    clicked(url);
                }else{
                    layer.alert( 'Abnormal error, please contact the administrator or refresh page', {
                        title:'Information',
                        skin: 'layui-layer-lan'
                        ,closeBtn: 0
                    });
                }
            })
        }
    }

    function info1(){
        $http({
            method : 'post',
            url : ctx + "appJson/getHotspot"
        }).success(function (data) {
            if(data){
                $scope.hotspots = data.result.detaileds;
            }
        })
    }
    // 热点初始化
    info1();
    // 添加流量数
    $scope.addip = function (catId,dlId) {
        $http({
            method : "post",
            url : ctx + "appJson/addip",
            params : {"catId":catId,"dlId": dlId}
        }).success(function (data) {

        })
    }

    <!-- 反馈:支持-->
    $("#praise").click(function(){
        var praise_img = $("#praise-img");
        var text_box = $("#add-num");
        var praise_txt = $("#praise-txt");
        var num=parseInt(praise_txt.text());
        if(praise_img.attr("src") == (ctx + "img/zan1.png")){
            $(this).html("<img src='"+ctx + "img/hui1.png' id='praise-img' class='animation' />");
            praise_txt.removeClass("hover");
            text_box.show().html("<em class='add-animation'>-1</em>");
            $(".add-animation").removeClass("hover");
            num -=1;
            praise_txt.text(num);
            delFeedback($scope.dfId1);
            $scope.dfId1 = 0;
        }else{
            $(this).html("<img src='"+ctx + "img/zan1.png' id='praise-img' class='animation' />");
            praise_txt.addClass("hover");
            text_box.show().html("<em class='add-animation'>+1</em>");
            $(".add-animation").addClass("hover");
            num +=1;
            praise_txt.text(num)
            addFeedback(1,"");
        }
    });
    <!-- 反馈:反对-->
    $("#praiseNayTest").hide();
    $("#praise1").click(function(){
        var praise_img = $("#praise-img1");
        var text_box = $("#add-num1");
        var praise_txt = $("#praise-txt1");
        var num=parseInt(praise_txt.text());
        if(praise_img.attr("src") == (ctx + "img/zan2.png")){
            $(this).html("<img src='"+ctx + "img/hui2.png' id='praise-img1' class='animation' />");
            text_box.show().html("<em class='add-animation'>-1</em>");
            $(".add-animation").removeClass("hover");
            $("#praiseNayTest").hide();
            delFeedback($scope.dfId2);
            $scope.dfId2 = 0;
        }else{
            $(this).html("<img src='"+ctx + "img/zan2.png' id='praise-img1' class='animation' />");
            text_box.show().html("<em class='add-animation'>+1</em>");
            $(".add-animation").addClass("hover");
            $("#praiseNayTest").show();
            addFeedback(2,"");
        }
    });
    <!-- 反馈:add异步-->
    $scope.dfId1 = 0;
    $scope.dfId2 = 0;
    function addFeedback(type,content) {
        $http({
            method : "post",
            url : ctx + "appJson/addFeedback",
            data : {"type" : type,"dlId" : $scope.dlId,"content" : content}
        }).success(function (data) {
            if(data.result.type == 1){
                $scope.dfId1 = data.result.id;
            }else{
                $scope.dfId2 = data.result.id;
            }
        })
    }
    function delFeedback(dfId) {
        if(dfId == 0){
            return;
        }
        $http({
            method : "post",
            url : ctx + "appJson/delFeedback",
            data : {"id" : dfId}
        }).success(function (data) {
        })
    }
    $scope.dfContent = "";
    $scope.addDfContent = function () {
        var index = layer.load(0, {shade: false});
        $http({
            method : "post",
            url : ctx + "appJson/updateFeedback",
            data : {"id":$scope.dfId2,"type" : 2,"dlId" : $scope.dlId,"content" : $scope.dfContent}
        }).success(function (data) {
            layer.close(index);
            if(data.code == 200){
                layer.msg('OK', {icon: 1});
                $scope.dfContent = "";
                $scope.dfId2 = 0;
            }else{
                layer.msg("Error", {icon: 5});
            }
        })
    }

}]);