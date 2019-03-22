var myapp = angular.module("myapp",['pascalprecht.translate']);

myapp.config(['$translateProvider',function($translateProvider){
    var lang = window.localStorage['lang'] || '6';
    $translateProvider
        .preferredLanguage(lang)
        .useStaticFilesLoader({
            prefix: ctx + 'js/i18n/',
            suffix: '.json'
        })
        //sanitize escape escapeParameters
        .useSanitizeValueStrategy('escapeParameters');
}])

/*myapp.filter("L", ['$translate', function($translate){
    return function(key){
        if(key){
            return $translate.instant(key);
        }
    };
}])*/


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

// 路由
myapp.config(['$locationProvider', function($locationProvider) {
    // $locationProvider.html5Mode(true);
    $locationProvider.html5Mode({
        enabled: true,
        requireBase: false
    });
}]);

// eForm1Controller
myapp.controller("eForm1Controller",["$scope","$http","$location","$translate",function ($scope, $http,$location,$translate) {
    // 设置默认,langId==6语言，英文;
    $scope.langId = parseInt(GetUrlParam("langId")==""?6:GetUrlParam("langId"));
    function into(langID){
        $http({
            method : 'post',
            url : ctx + "appJson/getLanguageAll",
            params:{"langId": langID}
        }).success(function (data) {
            $scope.isGetUrl = true;
            if(data){
                /* 成功*/
                $scope.languages = data;
            }else{
                /* 失败*/
                layer.alert( 'Abnormal error, please contact the administrator.', {
                    skin: 'layui-layer-lan'
                    ,closeBtn: 0
                });
            }
        })
    }
    // 初始化
    $translate.use($scope.langId.toString());
    into($scope.langId);
    onlineChat($scope.langId);
    // 返HKE官网
    $scope.clickCategory = function (idnex) {
        getHKE($scope.langId);
    }
    // 语言事件
    $scope.clickLanguage = function() {
        if($scope.isGetUrl){
            $translate.use($scope.langId.toString());
            onlineChat($scope.langId);
        }
        // 强制更新  $scope.apply();
    }

    // laydate国际版
    laydate.render({
        elem: '#ladate1'
        ,lang: 'en'
    });
    laydate.render({
        elem: '#ladate2'
    });
    laydate.render({
        elem: '#ladate3'
    });
    laydate.render({
        elem: '#ladate4'
    });
    laydate.render({
        elem: '#ladate5'
    });

    $scope.myTabContent = false;
    $scope.checkPNR = function(){
        if(!$scope.PNR){
            $('input[name="PNRtext"]').hide();
            $scope.myTabContent = true;
        }else{
            $('input[name="PNRtext"]').show();
            $scope.myTabContent = false;
        }
    }

    $scope.eFormContent = true;
    $scope.eFormError = false;
    $scope.eFormSuccess = false;

    $scope.eFormContentSubmit = function () {
        $scope.eFormContent = false;
        $scope.eFormError = true;
        $scope.eFormSuccess = true;
    }

    $scope.getEformContent = function () {
        $scope.eFormError = false;
        $scope.eFormSuccess = false;
        $scope.eFormContent = true;
    }

}]);


// eForm2Controller
myapp.controller("eForm2Controller",["$scope","$http","$location","$translate",function ($scope, $http,$location,$translate) {
    // 设置默认,langId==6语言，英文;
    $scope.langId = parseInt(GetUrlParam("langId")==""?6:GetUrlParam("langId"));
    function into(langID){
        $http({
            method : 'post',
            url : ctx + "appJson/getLanguageAll",
            params:{"langId": langID}
        }).success(function (data) {
            $scope.isGetUrl = true;
            if(data){
                /* 成功*/
                $scope.languages = data;
            }else{
                /* 失败*/
                layer.alert( 'Abnormal error, please contact the administrator.', {
                    skin: 'layui-layer-lan'
                    ,closeBtn: 0
                });
            }
        })
    }
    // 初始化
    $translate.use($scope.langId.toString());
    into($scope.langId);
    onlineChat($scope.langId);
    // 返HKE官网
    $scope.clickCategory = function (idnex) {
        getHKE($scope.langId);
    }
    // 语言事件
    $scope.clickLanguage = function() {
        if($scope.isGetUrl){
            $translate.use($scope.langId.toString());
            onlineChat($scope.langId);
        }
        // 强制更新  $scope.apply();
    }

    $scope.eFormContent1 = true;

    /**
     * 第一次提交
     */
    $scope.eFormContentSubmit1 = function () {
        $scope.eFormContent1 = false;
        $scope.eFormContent2 = true;
    }

    /**
     * 第二次提交
     */
    $scope.eFormContentSubmit2 = function () {
        $scope.eFormContent1 = false;
        $scope.eFormContent2 = false;
        $scope.eFormError = true;
        $scope.eFormSuccess = true;
    }

    /**
     * 返回
     */
    $scope.getEformContent = function () {
        $scope.eFormContent1 = true;
        $scope.eFormError = false;
        $scope.eFormSuccess = false;
    }

}]);


// eForm3Controller
myapp.controller("eForm3Controller",["$scope","$http","$location","$translate",function ($scope, $http,$location,$translate) {
    // 设置默认,langId==6语言，英文;
    $scope.langId = parseInt(GetUrlParam("langId")==""?6:GetUrlParam("langId"));
    function into(langID){
        $http({
            method : 'post',
            url : ctx + "appJson/getLanguageAll",
            params:{"langId": langID}
        }).success(function (data) {
            $scope.isGetUrl = true;
            if(data){
                /* 成功*/
                $scope.languages = data;
            }else{
                /* 失败*/
                layer.alert( 'Abnormal error, please contact the administrator.', {
                    skin: 'layui-layer-lan'
                    ,closeBtn: 0
                });
            }
        })
    }
    // 初始化
    $translate.use($scope.langId.toString());
    into($scope.langId);
    onlineChat($scope.langId);
    // 返HKE官网
    $scope.clickCategory = function (idnex) {
        getHKE($scope.langId);
    }
    // 语言事件
    $scope.clickLanguage = function() {
        if($scope.isGetUrl){
            $translate.use($scope.langId.toString());
            onlineChat($scope.langId);
        }
        // 强制更新  $scope.apply();
    }
    // 默认航班编号为: 航班延誤證明
    $scope.UO_number = '4';
    $scope.eFormContent = true;
    /**
     * 提交
     */
    $scope.eFormContentSubmit = function () {
        $scope.eFormContent = false;
        $scope.eFormError = true;
        $scope.eFormSuccess = true;
    }
    /**
     * 返回
     */
    $scope.getEformContent = function () {
        $scope.eFormContent = true;
        $scope.eFormError = false;
        $scope.eFormSuccess = false;
    }

}]);


// eForm4Controller
myapp.controller("eForm4Controller",["$scope","$http","$location","$translate",function ($scope, $http,$location,$translate) {
    // 设置默认,langId==6语言，英文;
    $scope.langId = parseInt(GetUrlParam("langId")==""?6:GetUrlParam("langId"));
    function into(langID){
        $http({
            method : 'post',
            url : ctx + "appJson/getLanguageAll",
            params:{"langId": langID}
        }).success(function (data) {
            $scope.isGetUrl = true;
            if(data){
                /* 成功*/
                $scope.languages = data;
            }else{
                /* 失败*/
                layer.alert( 'Abnormal error, please contact the administrator.', {
                    skin: 'layui-layer-lan'
                    ,closeBtn: 0
                });
            }
        })
    }
    // 初始化
    $translate.use($scope.langId.toString());
    into($scope.langId);
    onlineChat($scope.langId);
    // 返HKE官网
    $scope.clickCategory = function (idnex) {
        getHKE($scope.langId);
    }
    // 语言事件
    $scope.clickLanguage = function() {
        if($scope.isGetUrl){
            $translate.use($scope.langId.toString());
            onlineChat($scope.langId);
        }
        // 强制更新  $scope.apply();
    }

    $scope.eFormContent = true;

    /**
     * 提交
     */
    $scope.eFormContentSubmit = function () {
        $scope.eFormContent = false;
        $scope.eFormError = true;
        $scope.eFormSuccess = true;
    }

    /**
     * 返回
     */
    $scope.getEformContent = function () {
        $scope.eFormContent = true;
        $scope.eFormError = false;
        $scope.eFormSuccess = false;
    }

}]);


// eForm5Controller
myapp.controller("eForm5Controller",["$scope","$http","$location","$translate",function ($scope, $http,$location,$translate) {
    // 设置默认,langId==6语言，英文;
    $scope.langId = parseInt(GetUrlParam("langId")==""?6:GetUrlParam("langId"));
    function into(langID){
        $http({
            method : 'post',
            url : ctx + "appJson/getLanguageAll",
            params:{"langId": langID}
        }).success(function (data) {
            $scope.isGetUrl = true;
            if(data){
                /* 成功*/
                $scope.languages = data;
            }else{
                /* 失败*/
                layer.alert( 'Abnormal error, please contact the administrator.', {
                    skin: 'layui-layer-lan'
                    ,closeBtn: 0
                });
            }
        })
    }
    // 初始化
    $translate.use($scope.langId.toString());
    into($scope.langId);
    onlineChat($scope.langId);

    // 语言事件
    $scope.clickLanguage = function() {
        if($scope.isGetUrl){
            $translate.use($scope.langId.toString());
            onlineChat($scope.langId);
        }
        // 强制更新  $scope.apply();
    }

    // 类别事件,idnex当前下标，id:类别ID
    $scope.clickCategory = function (idnex) {
        getHKE($scope.langId);
    }

    // laydate国际版
    laydate.render({
        elem: '#ladate1'
        ,lang: 'en'
    });
    laydate.render({
        elem: '#ladate2'
    });
    laydate.render({
        elem: '#ladate3'
    });
    laydate.render({
        elem: '#ladate4'
    });
    laydate.render({
        elem: '#ladate5'
    });

    $scope.eFormContent = true;

    /**
     * 第一次提交
     */
    $scope.eFormContentSubmit = function () {
        $scope.eFormContent = false;
        $scope.eFormContentPay = true;
    }
    /**
     * 第二次提交
     */
    $scope.eFormContentPaySubmit = function () {
        $scope.eFormContentPay = false;
        $scope.eFormError = true;
        $scope.eFormSuccess = true;
    }
    /**
     * 完成,返回
     */
    $scope.getEformContent = function () {
        $scope.eFormError = false;
        $scope.eFormSuccess = false;
        $scope.eFormContent = true;
    }
}]);


// eForm6Controller
myapp.controller("eForm6Controller",["$scope","$http","$location","$translate",function ($scope, $http,$location,$translate) {
    // 设置默认,langId==6语言，英文;
    $scope.langId = parseInt(GetUrlParam("langId")==""?6:GetUrlParam("langId"));
    function into(langID){
        $http({
            method : 'post',
            url : ctx + "appJson/getLanguageAll",
            params:{"langId": langID}
        }).success(function (data) {
            $scope.isGetUrl = true;
            if(data){
                /* 成功*/
                $scope.languages = data;
            }else{
                /* 失败*/
                layer.alert( 'Abnormal error, please contact the administrator.', {
                    skin: 'layui-layer-lan'
                    ,closeBtn: 0
                });
            }
        })
    }
    // 初始化
    $translate.use($scope.langId.toString());
    into($scope.langId);
    onlineChat($scope.langId);
    // 返HKE官网
    $scope.clickCategory = function (idnex) {
        getHKE($scope.langId);
    }
    // 语言事件
    $scope.clickLanguage = function() {
        if($scope.isGetUrl){
            $translate.use($scope.langId.toString());
            onlineChat($scope.langId);
        }
        // 强制更新  $scope.apply();
    }

    // laydate国际版
    laydate.render({
        elem: '#ladate1'
        ,lang: 'en'
    });
    laydate.render({
        elem: '#ladate2'
    });
    laydate.render({
        elem: '#ladate3'
    });
    laydate.render({
        elem: '#ladate4'
    });
    laydate.render({
        elem: '#ladate5'
    });

    $scope.myTabContent = false;
    $scope.checkPNR = function(){
        if(!$scope.PNR){
            $('input[name="PNRtext"]').hide();
            $scope.myTabContent = true;
        }else{
            $('input[name="PNRtext"]').show();
            $scope.myTabContent = false;
        }
    }

    $scope.eFormContent = true;
    $scope.eFormError = false;
    $scope.eFormSuccess = false;

    $scope.eFormContentSubmit = function () {
        $scope.eFormContent = false;
        $scope.eFormError = true;
        $scope.eFormSuccess = true;
    }

    $scope.getEformContent = function () {
        $scope.eFormError = false;
        $scope.eFormSuccess = false;
        $scope.eFormContent = true;
    }

}]);
