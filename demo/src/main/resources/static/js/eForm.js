var myapp = angular.module("myapp",['pascalprecht.translate']);

myapp.config(['$translateProvider',function($translateProvider){
    var lang = window.localStorage.lang || '6';
    $translateProvider
        .preferredLanguage(lang)
        .useStaticFilesLoader({
            prefix: ctx + 'js/i18n/',
            suffix: '.json'
        })
        //sanitize escape escapeParameters
        .useSanitizeValueStrategy('escapeParameters');
}])

myapp.factory('T', ['$translate', function($translate) {
    var T = {
        T:function(key) {
            if(key){
                return $translate.instant(key);
            }
            return key;
        }
    }
    return T;
}])

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
                //SonicTeleservices.setTitle("聯絡我們!"); // for the pre-chat form
                //SonicTeleservicesChat.setTitle("透過線上客服與我們聯絡"); // for the chat-form
                //SonicTeleservices.setProactiveAutocloseDelay(0.5);// delay proactive auto close after 30 sec
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
                //SonicTeleservices.setTitle("联络我们!"); // for the pre-chat form
                //SonicTeleservicesChat.setTitle("透过在线客服与我们联络"); // for the chat-form
                //SonicTeleservices.setProactiveAutocloseDelay(0.5);// delay proactive auto close after 30 sec
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
                //SonicTeleservices.setTitle("聯絡我們!"); // for the pre-chat form
                //SonicTeleservicesChat.setTitle("透過線上客服與我們聯絡"); // for the chat-form
                //SonicTeleservices.setProactiveAutocloseDelay(0.5);// delay proactive auto close after 30 sec
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
                //SonicTeleservices.setTitle("連絡先情報!"); // for the pre-chat form
                //SonicTeleservicesChat.setTitle("私たちとチャットしましょう"); // for the chat-form
                //SonicTeleservices.setProactiveAutocloseDelay(0.5);// delay proactive auto close after 30 sec
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
               // SonicTeleservices.setTitle("연락처!"); // for the pre-chat form
                //SonicTeleservicesChat.setTitle("연락해 주십시오."); // for the chat-form
                //SonicTeleservices.setProactiveAutocloseDelay(0.5);// delay proactive auto close after 30 sec
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
                //SonicTeleservices.setTitle("Contact us!"); // for the pre-chat form
                //SonicTeleservicesChat.setTitle("Chat with us!"); // for the chat-form
                //SonicTeleservices.setProactiveAutocloseDelay(0.5);// delay proactive auto close after 30 sec
            }
        };
        var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(se, s);
    }
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
myapp.controller("eForm1Controller",["$scope","$http","$location","$translate","T",function ($scope, $http,$location,$translate,T) {
    // 设置默认,langId==6语言，英文;
    $scope.langId = parseInt(GetUrlParam("langId")==""?6:GetUrlParam("langId"));
    gaixialatu($scope.langId);
    function into(langID){
        $http({
            method : 'post',
            url : ctx + "appJson/getLanguageAll",
            params:{"langId": langID}
        }).success(function (data) {
            $scope.isGetUrl = true;
            $scope.languages = data;
        })

        $http({
            method : 'post',
            url : ctx + "appJson/E/getAreaNames"
        }).success(function (data) {
            $scope.e_area_names = data;
        })
    }
    // 初始化
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
            gaixialatu($scope.langId);
        }
        // 强制更新  $scope.apply();
    }

    // laydate国际版
    laydate.render({
        elem: '#ladate1'
        ,lang: 'en'
        ,done:function(value,dates,edate){
            $('#ladate1').val(value);
            $('.eForm-div1').data('bootstrapValidator')
                .updateStatus('ladate1', 'NOT_VALIDATED',null)
                .validateField('ladate1');
        }
    });
    laydate.render({
        elem: '#ladate2'
        ,lang: 'en'
        ,done:function(value,dates,edate){
            $('#ladate2').val(value);
            $('.eForm-div1').data('bootstrapValidator')
                .updateStatus('ladate2', 'NOT_VALIDATED',null)
                .validateField('ladate2');
        }
    });
    laydate.render({
        elem: '#ladate3'
        ,lang: 'en'
        ,done:function(value,dates,edate){
            $('#ladate3').val(value);
            $('.eForm-div1').data('bootstrapValidator')
                .updateStatus('ladate3', 'NOT_VALIDATED',null)
                .validateField('ladate3');
        }
    });
    laydate.render({
        elem: '#ladate4'
        ,lang: 'en'
        ,done:function(value,dates,edate){
            $('#ladate4').val(value);
            $('.eForm-div1').data('bootstrapValidator')
                .updateStatus('ladate4', 'NOT_VALIDATED',null)
                .validateField('ladate4');
        }
    });
    laydate.render({
        elem: '#ladate5'
        ,lang: 'en'
        ,done:function(value,dates,edate){
            $('#ladate5').val(value);
            $('.eForm-div1').data('bootstrapValidator')
                .updateStatus('ladate5', 'NOT_VALIDATED',null)
                .validateField('ladate5');
        }
    });


    // 样式初始化
    $scope.myTabContent = false;
    $scope.PNR = true
    $scope.checkPNR = function(){
        if(!$scope.PNR){
            $('.pnr-div').hide();
            $scope.myTabContent = true;
        }else{
            $('.pnr-div').show();
            $scope.myTabContent = false;
        }
    }
    $scope.eFormContent = true;
    $scope.eFormError = false;
    $scope.eFormSuccess = false;
    $scope.e = {};
    $scope.e.type = "1";
    // 获取那个dlId进入
    $scope.e.dlId = GetUrlParam("dlId")==""?0:GetUrlParam("dlId");
    var crm_uid = $location.search().crm_uid;
    if(crm_uid == undefined){
        crm_uid = "";
    }
    /**
     * 提交
     */
    var lock1 = false; //默认未锁定
    $scope.eFormContentSubmit = function () {
        $scope.e.langId =  $scope.langId;
        if(!$scope.PNR){
            $scope.pnr = "";
            $scope.e.triptype = parseInt($(".nav-tabs .active").attr("data"));
            if($scope.e.triptype == 1){
                $scope.e.departing =  $scope.tab1_departing;
                $scope.e.going =  $scope.tab1_going;
                $scope.e.departingdate =   $("#ladate1").val();
                $scope.e.goingdate =   $("#ladate2").val();
                $scope.e.departingnew =  "";
                $scope.e.goingnew =  "";
            }else if($scope.e.triptype == 2){
                $scope.e.departing =  $scope.tab2_departing;
                $scope.e.going =  $scope.tab2_going;
                $scope.e.departingdate =  $("#ladate3").val();
                $scope.e.goingdate = "";
                $scope.e.departingnew =  "";
                $scope.e.goingnew =  "";
            }else if($scope.e.triptype == 3){
                $scope.e.departing =  $scope.e_area_names[0].key;
                $scope.e.going =  $scope.tab3_going;
                $scope.e.departingdate =  $("#ladate4").val();
                $scope.e.goingdate = $("#ladate5").val();
                $scope.e.departingnew =  $scope.tab3_departingnew;
                $scope.e.goingnew =  $scope.e_area_names[0].key;
            }
        }
        var bootstrapValidator = $(".eForm-div1").data('bootstrapValidator');
        bootstrapValidator.validate();
        if(bootstrapValidator.isValid()){
            // 验证成功
            var index =  layer.load(0, {shade: false});
            if(!lock1) {
                lock1 = true; // 锁定
                $http({
                    method : 'post',
                    url : ctx + 'appJson/E/add',
                    data : {"eform":$scope.e,"crmuid":crm_uid},
                }).then(function(resp){
                    $scope.data = resp.data;
                    $("#booking-steps-li2").removeClass().addClass('active');
                    $("#booking-steps-li1").removeClass();
                    $scope.eFormContent = false;
                    if( $scope.data.code == 200 ){
                        $scope.eFormSuccess = true;
                    }else{
                        $scope.eFormError = true;
                    }
                    layer.close(index);
                });
            }
        }else{
            return;
        }
    }

    /**
     * error返回
     */
    $scope.getEformContent = function () {
        $("#booking-steps-li1").removeClass().addClass('active');
        $("#booking-steps-li2").removeClass();
        $scope.eFormError = false;
        $scope.eFormSuccess = false;
        $scope.eFormContent = true;
        lock1 = false; //解锁
    }

    /**
     * success返回
     */
    $scope.getEformContentDone = function () {
        location.reload();
    }

    /**
     * 内容验证
     */
    $(function () {
        $translate.use($scope.langId.toString());
        window.onload = function(){
            $('.eForm-div1').bootstrapValidator({
                message: 'This value is not valid',
                feedbackIcons: {
                    valid: 'glyphicon glyphicon-ok',
                    invalid: 'glyphicon glyphicon-remove',
                    validating: 'glyphicon glyphicon-refresh'
                },
                fields: {
                    firstName: {
                        validators: {
                            notEmpty: {
                                message: T.T('error2')
                            }
                        }
                    },
                    lastName: {
                        validators: {
                            notEmpty: {
                                message: T.T('error2')
                            }
                        }
                    },
                    pnr : {
                        validators: {
                            notEmpty: {
                                message: T.T('error2')
                            }
                        }
                    },
                    email: {
                        validators: {
                            notEmpty: {
                                message: T.T('error2')
                            },
                            emailAddress: {
                                message: T.T('error3')
                            }
                        }
                    },
                    tab1_departing: {
                        validators: {
                            notEmpty: {
                                message: T.T('error4')
                            },
                            callback :{
                                message: T.T('error4'),
                                callback: function(value, validator) {
                                    if (value == "?") {
                                        return false;
                                    } else {
                                        return true;
                                    }
                                }
                            }
                        }
                    },
                    tab1_going: {
                        validators: {
                            notEmpty: {
                                message: T.T('error4')
                            },
                            callback :{
                                message: T.T('error4'),
                                callback: function(value, validator) {
                                    if (value == "?") {
                                        return false;
                                    } else {
                                        return true;
                                    }
                                }
                            }
                        }
                    },
                    ladate1: {
                        validators: {
                            notEmpty: {
                                message: T.T('error2')
                            }
                        }
                    },
                    ladate2: {
                        validators: {
                            notEmpty: {
                                message: T.T('error2')
                            }
                        }
                    },
                    ladate3: {
                        validators: {
                            notEmpty: {
                                message: T.T('error2')
                            }
                        }
                    },
                    ladate4: {
                        validators: {
                            notEmpty: {
                                message: T.T('error2')
                            }
                        }
                    },
                    ladate5: {
                        validators: {
                            notEmpty: {
                                message: T.T('error2')
                            }
                        }
                    },
                    tab2_departing: {
                        validators: {
                            notEmpty: {
                                message: T.T('error4')
                            },
                            callback :{
                                message: T.T('error4'),
                                callback: function(value, validator) {
                                    if (value == "?") {
                                        return false;
                                    } else {
                                        return true;
                                    }
                                }
                            }
                        }
                    },
                    tab2_going: {
                        validators: {
                            notEmpty: {
                                message: T.T('error4')
                            },
                            callback :{
                                message: T.T('error4'),
                                callback: function(value, validator) {
                                    if (value == "?") {
                                        return false;
                                    } else {
                                        return true;
                                    }
                                }
                            }
                        }
                    },
                    tab3_going: {
                        validators: {
                            notEmpty: {
                                message: T.T('error4')
                            },
                            callback :{
                                message: T.T('error4'),
                                callback: function(value, validator) {
                                    if (value == "?") {
                                        return false;
                                    } else {
                                        return true;
                                    }
                                }
                            }
                        }
                    },
                    tab3_departingnew: {
                        validators: {
                            notEmpty: {
                                message: T.T('error4')
                            },
                            callback :{
                                message: T.T('error4'),
                                callback: function(value, validator) {
                                    if (value == "?") {
                                        return false;
                                    } else {
                                        return true;
                                    }
                                }
                            }
                        }
                    }



                }
            });
        }
    });

}]);


// eForm2Controller
myapp.controller("eForm2Controller",["$scope","$http","$location","$translate",function ($scope, $http,$location,$translate) {
    // 设置默认,langId==6语言，英文;
    $scope.langId = parseInt(GetUrlParam("langId")==""?6:GetUrlParam("langId"));
    gaixialatu($scope.langId);
    function into(langID){
        $http({
            method : 'post',
            url : ctx + "appJson/getLanguageAll",
            params:{"langId": langID}
        }).success(function (data) {
            $scope.languages = data;
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
        $translate.use($scope.langId.toString());
        onlineChat($scope.langId);
        // 强制更新  $scope.apply();
    }

    $scope.eFormContent1 = true;
    $("#progressbarLi2").removeClass().addClass('progressbarLi');

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
        $("#progressbarLi1").removeClass().addClass('progressbarLi');
        $("#progressbarLi2").removeClass();
    }

    /**
     * 返回上一页
     */
    $scope.getEformContent1 = function () {
        $scope.eFormContent1 = true;
        $scope.eFormContent2 = false;
    }

    /**
     * 返回
     */
    $scope.getEformContent = function () {
        $("#progressbarLi2").removeClass().addClass('progressbarLi');
        $("#progressbarLi1").removeClass();
        $scope.eFormContent1 = true;
        $scope.eFormError = false;
        $scope.eFormSuccess = false;
    }

}]);


// eForm3Controller
myapp.controller("eForm3Controller",["$scope","$http","$location","$translate","T",function ($scope, $http,$location,$translate,T) {
    // 设置默认,langId==6语言，英文;
    $scope.langId = parseInt(GetUrlParam("langId")==""?6:GetUrlParam("langId"));
    gaixialatu($scope.langId);
    function into(){
        $http({
            method : 'post',
            url : ctx + "appJson/E/getEform3"
        }).success(function (data) {
            $scope.languages = data.result.languages;
            $scope.e_area_names = data.result.e_area_names;
            $scope.e_certificates = data.result.e_certificates;
        })
    }
    // 初始化
    $translate.use($scope.langId.toString());
    into();
    onlineChat($scope.langId);
    // 返HKE官网
    $scope.clickCategory = function (idnex) {
        getHKE($scope.langId);
    }
    // 语言事件
    $scope.clickLanguage = function() {
        $translate.use($scope.langId.toString());
        onlineChat($scope.langId);
        gaixialatu($scope.langId);
        // 强制更新  $scope.apply();
    }

    $scope.e = {};
    $scope.e.type = "3";
    // 获取那个dlId进入
    $scope.e.dlId = GetUrlParam("dlId")==""?0:GetUrlParam("dlId");
    // 默认航班编号为: 航班延误证明
    $scope.e.ecertificatetype = 1;
    $scope.eFormContent = true;
    var crm_uid = $location.search().crm_uid;
    if(crm_uid == undefined){
        crm_uid = "";
    }
    /**
     * GuestName 功能
     * @type {string[]}
     */
    $scope.postBack = [{}];
    $scope.addGuestName = function(idx){
        var postBack={};
        $scope.postBack.push(postBack);

    };
    $scope.deleteGuestName = function(idx){
        $scope.postBack.splice(idx,1);
    };

    // laydate国际版
    laydate.render({
        elem: '#ladate1'
        ,lang: 'en'
        ,done:function(value,dates,edate){
            $('#ladate1').val(value);
            $('.eForm-div1').data('bootstrapValidator')
                .updateStatus('ladate1', 'NOT_VALIDATED',null)
                .validateField('ladate1');
        }
    });

    /**
     * 提交
     */
    var lock1 = false; //默认未锁定
    $scope.eFormContentSubmit = function () {
        $scope.e.langId =  $scope.langId;
        $scope.e.firstname = "";
        $scope.e.lastname = "";
        angular.forEach($scope.postBack, function (each,id) {
            var first = 'firstname'+id;
            var last = 'lastname'+id;
            $('.eForm-div1').bootstrapValidator('addField', first, {
                validators: {
                    notEmpty: {
                        message: T.T('error2')
                    }
                }
            });
            $('.eForm-div1').bootstrapValidator('addField', last, {
                validators: {
                    notEmpty: {
                        message: T.T('error2')
                    }
                }
            });
             $scope.e.firstname = $scope.e.firstname + "," + $("input[name="+first+"]").val().trim();
             $scope.e.lastname = $scope.e.lastname + "," + $("input[name="+last+"]").val().trim();
        });
        $scope.e.firstname = $scope.e.firstname.substring(1);
        $scope.e.lastname = $scope.e.lastname.substring(1);
        $scope.e.departuredate = $("#ladate1").val();
        var bootstrapValidator = $(".eForm-div1").data('bootstrapValidator');
        bootstrapValidator.validate();
        if(bootstrapValidator.isValid()) {
            // 验证成功
            var index =  layer.load(0, {shade: false});
            if(!lock1) {
                lock1 = true; // 锁定
                $http({
                    method : 'post',
                    url : ctx + 'appJson/E/add3',
                    data : {"eform":$scope.e,"crmuid":crm_uid},
                }).then(function(resp){
                    $scope.data = resp.data;
                    $("#booking-steps-li2").removeClass().addClass('active');
                    $("#booking-steps-li1").removeClass();
                    $scope.eFormContent = false;
                    if( $scope.data.code == 200 ){
                        $scope.eFormSuccess = true;
                    }else{
                        $scope.eFormError = true;
                    }
                    layer.close(index);
                });
            }

        }else{
            return;
        }
    }

    /**
     * error返回
     */
    $scope.getEformContent = function () {
        $("#booking-steps-li1").removeClass().addClass('active');
        $("#booking-steps-li2").removeClass();
        $scope.eFormError = false;
        $scope.eFormSuccess = false;
        $scope.eFormContent = true;
        lock1 = false; //解锁
    }

    /**
     * success返回
     */
    $scope.getEformContentDone = function () {
        location.reload();
    }

    /**
     * 内容验证
     */
    $(function () {
        $translate.use($scope.langId.toString());
        window.onload = function(){
            $('.eForm-div1').bootstrapValidator({
                message: 'This value is not valid',
                feedbackIcons: {
                    valid: 'glyphicon glyphicon-ok',
                    invalid: 'glyphicon glyphicon-remove',
                    validating: 'glyphicon glyphicon-refresh'
                },
                fields: {
                    flightno: {
                        validators: {
                            notEmpty: {
                                message: T.T('error2')
                            }
                        }
                    },
                    pnr : {
                        validators: {
                            notEmpty: {
                                message: T.T('error2')
                            }
                        }
                    },
                    email: {
                        validators: {
                            notEmpty: {
                                message: T.T('error2')
                            },
                            emailAddress: {
                                message: T.T('error3')
                            }
                        }
                    },
                    departing: {
                        validators: {
                            notEmpty: {
                                message: T.T('error4')
                            },
                            callback :{
                                message: T.T('error4'),
                                callback: function(value, validator) {
                                    if (value == "?") {
                                        return false;
                                    } else {
                                        return true;
                                    }
                                }
                            }
                        }
                    },
                    going: {
                        validators: {
                            notEmpty: {
                                message: T.T('error4')
                            },
                            callback :{
                                message: T.T('error4'),
                                callback: function(value, validator) {
                                    if (value == "?") {
                                        return false;
                                    } else {
                                        return true;
                                    }
                                }
                            }
                        }
                    },
                    ladate1: {
                        validators: {
                            notEmpty: {
                                message: T.T('error2')
                            }
                        }
                    }



                }
            });
        }
    });

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
    $("#progressbarLi2").removeClass().addClass('progressbarLi');

    /**
     * 提交
     */
    $scope.eFormContentSubmit = function () {
        $("#progressbarLi1").removeClass().addClass('progressbarLi');
        $("#progressbarLi2").removeClass();
        $scope.eFormContent = false;
        $scope.eFormError = true;
        $scope.eFormSuccess = true;
    }

    /**
     * 返回
     */
    $scope.getEformContent = function () {
        $("#progressbarLi2").removeClass().addClass('progressbarLi');
        $("#progressbarLi1").removeClass();
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
        $http({
            method : 'post',
            url : ctx + "appJson/E/getAreaNames"
        }).success(function (data) {
            $scope.e_area_names = data;
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
    $("#progressbarLi2").removeClass().addClass('progressbarLi');
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
        $("#progressbarLi1").removeClass().addClass('progressbarLi');
        $("#progressbarLi2").removeClass();
        $scope.eFormContentPay = false;
        $scope.eFormError = true;
        $scope.eFormSuccess = true;
    }
    /**
     * 完成,返回
     */
    $scope.getEformContent = function () {
        $("#progressbarLi2").removeClass().addClass('progressbarLi');
        $("#progressbarLi1").removeClass();
        $scope.eFormError = false;
        $scope.eFormSuccess = false;
        $scope.eFormContent = true;
    }
}]);


// eForm6Controller
myapp.controller("eForm6Controller",["$scope","$http","$location","$translate","T",function ($scope, $http,$location,$translate,T) {
    // 设置默认,langId==6语言，英文;
    $scope.langId = parseInt(GetUrlParam("langId")==""?6:GetUrlParam("langId"));
    gaixialatu($scope.langId);
    function into(langID){
        $http({
            method : 'post',
            url : ctx + "appJson/getLanguageAll",
            params:{"langId": langID}
        }).success(function (data) {
            $scope.isGetUrl = true;
            $scope.languages = data;
        })

        $http({
            method : 'post',
            url : ctx + "appJson/E/getAreaNames"
        }).success(function (data) {
            $scope.e_area_names = data;
        })
    }

    // 初始化
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
            gaixialatu($scope.langId);
        }
        // 强制更新  $scope.apply();
    }

    // laydate国际版
    laydate.render({
        elem: '#ladate1'
        ,lang: 'en'
        ,done:function(value,dates,edate){
            $('#ladate1').val(value);
            $('.eForm-div1').data('bootstrapValidator')
                .updateStatus('ladate1', 'NOT_VALIDATED',null)
                .validateField('ladate1');
        }
    });
    laydate.render({
        elem: '#ladate2'
        ,lang: 'en'
        ,done:function(value,dates,edate){
            $('#ladate2').val(value);
            $('.eForm-div1').data('bootstrapValidator')
                .updateStatus('ladate2', 'NOT_VALIDATED',null)
                .validateField('ladate2');
        }
    });
    laydate.render({
        elem: '#ladate3'
        ,lang: 'en'
        ,done:function(value,dates,edate){
            $('#ladate3').val(value);
            $('.eForm-div1').data('bootstrapValidator')
                .updateStatus('ladate3', 'NOT_VALIDATED',null)
                .validateField('ladate3');
        }
    });
    laydate.render({
        elem: '#ladate4'
        ,lang: 'en'
        ,done:function(value,dates,edate){
            $('#ladate4').val(value);
            $('.eForm-div1').data('bootstrapValidator')
                .updateStatus('ladate4', 'NOT_VALIDATED',null)
                .validateField('ladate4');
        }
    });
    laydate.render({
        elem: '#ladate5'
        ,lang: 'en'
        ,done:function(value,dates,edate){
            $('#ladate5').val(value);
            $('.eForm-div1').data('bootstrapValidator')
                .updateStatus('ladate5', 'NOT_VALIDATED',null)
                .validateField('ladate5');
        }
    });


    // 样式初始化
    $scope.myTabContent = false;
    $scope.PNR = true
    $scope.checkPNR = function(){
        if(!$scope.PNR){
            $('.pnr-div').hide();
            $scope.myTabContent = true;
        }else{
            $('.pnr-div').show();
            $scope.myTabContent = false;
        }
    }
    $scope.eFormContent = true;
    $scope.eFormError = false;
    $scope.eFormSuccess = false;
    $scope.e = {};
    $scope.e.type = "6";
    // 获取那个dlId进入
    $scope.e.dlId = GetUrlParam("dlId")==""?0:GetUrlParam("dlId");
    var crm_uid = $location.search().crm_uid;
    if(crm_uid == undefined){
        crm_uid = "";
    }

    /**
     * 提交
     */
    var lock1 = false; //默认未锁定
    $scope.eFormContentSubmit = function () {
        $scope.e.langId =  $scope.langId;
        if(!$scope.PNR){
            $scope.pnr = "";
            $scope.e.triptype = parseInt($(".nav-tabs .active").attr("data"));
            if($scope.e.triptype == 1){
                $scope.e.departing =  $scope.tab1_departing;
                $scope.e.going =  $scope.tab1_going;
                $scope.e.departingdate =   $("#ladate1").val();
                $scope.e.goingdate =   $("#ladate2").val();
                $scope.e.departingnew =  "";
                $scope.e.goingnew =  "";
            }else if($scope.e.triptype == 2){
                $scope.e.departing =  $scope.tab2_departing;
                $scope.e.going =  $scope.tab2_going;
                $scope.e.departingdate =  $("#ladate3").val();
                $scope.e.goingdate = "";
                $scope.e.departingnew =  "";
                $scope.e.goingnew =  "";
            }else if($scope.e.triptype == 3){
                $scope.e.departing =  $scope.e_area_names[0].key;
                $scope.e.going =  $scope.tab3_going;
                $scope.e.departingdate =  $("#ladate4").val();
                $scope.e.goingdate = $("#ladate5").val();
                $scope.e.departingnew =  $scope.tab3_departingnew;
                $scope.e.goingnew =  $scope.e_area_names[0].key;
            }
        }
        var bootstrapValidator = $(".eForm-div1").data('bootstrapValidator');
        bootstrapValidator.validate();
        if(bootstrapValidator.isValid()){
            // 验证成功
            var index =  layer.load(0, {shade: false});
            if(!lock1) {
                lock1 = true; // 锁定
                $http({
                    method : 'post',
                    url : ctx + 'appJson/E/add',
                    data : {"eform":$scope.e,"crmuid":crm_uid},
                }).then(function(resp){
                    $scope.data = resp.data;
                    $("#booking-steps-li2").removeClass().addClass('active');
                    $("#booking-steps-li1").removeClass();
                    $scope.eFormContent = false;
                    if( $scope.data.code == 200 ){
                        $scope.eFormSuccess = true;
                    }else{
                        $scope.eFormError = true;
                    }
                    layer.close(index);
                });
            }
        }else{
            return;
        }
    }

    /**
     * error返回
     */
    $scope.getEformContent = function () {
        $("#booking-steps-li1").removeClass().addClass('active');
        $("#booking-steps-li2").removeClass();
        $scope.eFormError = false;
        $scope.eFormSuccess = false;
        $scope.eFormContent = true;
        lock1 = false; //解锁
    }

    /**
     * success返回
     */
    $scope.getEformContentDone = function () {
        location.reload();
    }

    /**
     * 内容验证
     */
    $(function () {
        $translate.use($scope.langId.toString());
        window.onload = function(){
              $('.eForm-div1').bootstrapValidator({
                  message: 'This value is not valid',
                  feedbackIcons: {
                      valid: 'glyphicon glyphicon-ok',
                      invalid: 'glyphicon glyphicon-remove',
                      validating: 'glyphicon glyphicon-refresh'
                  },
                  fields: {
                      firstName: {
                          validators: {
                              notEmpty: {
                                  message: T.T('error2')
                              }
                          }
                      },
                      lastName: {
                          validators: {
                              notEmpty: {
                                  message: T.T('error2')
                              }
                          }
                      },
                      pnr : {
                          validators: {
                              notEmpty: {
                                  message: T.T('error2')
                              }
                          }
                      },
                      email: {
                          validators: {
                              notEmpty: {
                                  message: T.T('error2')
                              },
                              emailAddress: {
                                  message: T.T('error3')
                              }
                          }
                      },
                      tab1_departing: {
                          validators: {
                              notEmpty: {
                                  message: T.T('error4')
                              },
                              callback :{
                                  message: T.T('error4'),
                                  callback: function(value, validator) {
                                      if (value == "?") {
                                          return false;
                                      } else {
                                          return true;
                                      }
                                  }
                              }
                          }
                      },
                      tab1_going: {
                          validators: {
                              notEmpty: {
                                  message: T.T('error4')
                              },
                              callback :{
                                  message: T.T('error4'),
                                  callback: function(value, validator) {
                                      if (value == "?") {
                                          return false;
                                      } else {
                                          return true;
                                      }
                                  }
                              }
                          }
                      },
                      ladate1: {
                          validators: {
                              notEmpty: {
                                  message: T.T('error2')
                              }
                          }
                      },
                      ladate2: {
                          validators: {
                              notEmpty: {
                                  message: T.T('error2')
                              }
                          }
                      },
                      ladate3: {
                          validators: {
                              notEmpty: {
                                  message: T.T('error2')
                              }
                          }
                      },
                      ladate4: {
                          validators: {
                              notEmpty: {
                                  message: T.T('error2')
                              }
                          }
                      },
                      ladate5: {
                          validators: {
                              notEmpty: {
                                  message: T.T('error2')
                              }
                          }
                      },
                      tab2_departing: {
                          validators: {
                              notEmpty: {
                                  message: T.T('error4')
                              },
                              callback :{
                                  message: T.T('error4'),
                                  callback: function(value, validator) {
                                      if (value == "?") {
                                          return false;
                                      } else {
                                          return true;
                                      }
                                  }
                              }
                          }
                      },
                      tab2_going: {
                          validators: {
                              notEmpty: {
                                  message: T.T('error4')
                              },
                              callback :{
                                  message: T.T('error4'),
                                  callback: function(value, validator) {
                                      if (value == "?") {
                                          return false;
                                      } else {
                                          return true;
                                      }
                                  }
                              }
                          }
                      },
                      tab3_going: {
                          validators: {
                              notEmpty: {
                                  message: T.T('error4')
                              },
                              callback :{
                                  message: T.T('error4'),
                                  callback: function(value, validator) {
                                      if (value == "?") {
                                          return false;
                                      } else {
                                          return true;
                                      }
                                  }
                              }
                          }
                      },
                      tab3_departingnew: {
                          validators: {
                              notEmpty: {
                                  message: T.T('error4')
                              },
                              callback :{
                                  message: T.T('error4'),
                                  callback: function(value, validator) {
                                      if (value == "?") {
                                          return false;
                                      } else {
                                          return true;
                                      }
                                  }
                              }
                          }
                      }



                  }
              });
        }
    });


}]);


// eForm7Controller
myapp.controller("eForm7Controller",["$scope","$http","$location","$translate","T",function ($scope, $http,$location,$translate,T) {
    // 设置默认,langId==6语言，英文;
    $scope.langId = parseInt(GetUrlParam("langId")==""?6:GetUrlParam("langId"));
    gaixialatu($scope.langId);
    function into(langID){
        $http({
            method : 'post',
            url : ctx + "appJson/getLanguageAll",
            params:{"langId": langID}
        }).success(function (data) {
            $scope.isGetUrl = true;
            $scope.languages = data;
        })

        $http({
            method : 'post',
            url : ctx + "appJson/E/getAreaNames"
        }).success(function (data) {
            $scope.e_area_names = data;
        })
    }
    // 初始化
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
            gaixialatu($scope.langId);
        }
        // 强制更新  $scope.apply();
    }

    var map = new Map();
    map.set("2020-4-29",true);
    map.set("2020-4-30",true);
    map.set("2020-5-1",true);
    map.set("2020-5-2",true);
    map.set("2020-5-3",true);
    map.set("2020-5-4",true);
    map.set("2020-6-25",true);
    map.set("2020-6-26",true);
    map.set("2020-7-1",true);
    map.set("2020-7-20",true);
    map.set("2020-7-21",true);
    map.set("2020-7-22",true);
    map.set("2020-7-23",true);
    map.set("2020-7-24",true);
    map.set("2020-7-25",true);
    map.set("2020-7-26",true);
    map.set("2020-7-27",true);
    map.set("2020-7-28",true);
    map.set("2020-7-29",true);
    map.set("2020-7-30",true);
    map.set("2020-7-31",true);
    map.set("2020-8-1",true);
    map.set("2020-8-2",true);
    map.set("2020-8-3",true);
    map.set("2020-8-4",true);
    map.set("2020-8-5",true);
    map.set("2020-8-6",true);
    map.set("2020-8-7",true);
    map.set("2020-8-8",true);
    map.set("2020-8-9",true);
    map.set("2020-8-10",true);
    map.set("2020-8-11",true);
    map.set("2020-8-12",true);
    map.set("2020-8-13",true);
    map.set("2020-8-14",true);
    map.set("2020-8-15",true);
    map.set("2020-8-16",true);
    map.set("2020-8-17",true);
    map.set("2020-8-18",true);
    map.set("2020-8-19",true);
    map.set("2020-8-20",true);
    map.set("2020-8-21",true);
    map.set("2020-8-22",true);
    map.set("2020-8-23",true);
    map.set("2020-8-24",true);
    map.set("2020-8-25",true);
    map.set("2020-8-26",true);
    map.set("2020-8-27",true);
    map.set("2020-8-28",true);
    map.set("2020-8-29",true);
    map.set("2020-8-30",true);
    map.set("2020-9-26",true);
    map.set("2020-9-27",true);
    map.set("2020-9-28",true);
    map.set("2020-9-29",true);
    map.set("2020-9-30",true);
    map.set("2020-10-1",true);
    map.set("2020-10-2",true);
    map.set("2020-10-3",true);
    map.set("2020-10-4",true);
    map.set("2020-10-5",true);
    map.set("2020-10-6",true);
    map.set("2020-10-7",true);
    map.set("2020-10-8",true);
    map.set("2020-10-9",true);
    map.set("2020-10-10",true);
    map.set("2020-10-11",true);
    map.set("2020-10-23",true);
    map.set("2020-10-24",true);
    map.set("2020-10-25",true);
    map.set("2020-10-26",true);
    map.set("2020-10-27",true);
    map.set("2020-12-18",true);
    map.set("2020-12-19",true);
    map.set("2020-12-20",true);
    map.set("2020-12-21",true);
    map.set("2020-12-22",true);
    map.set("2020-12-23",true);
    map.set("2020-12-24",true);
    map.set("2020-12-25",true);
    map.set("2020-12-26",true);
    map.set("2020-12-27",true);
    map.set("2020-12-28",true);
    map.set("2020-12-29",true);
    map.set("2020-12-30",true);
    map.set("2020-12-31",true);



    function disabledLaydateInfo() {
        var elem = $(".layui-laydate-content");//获取table对象
        angular.forEach(elem.find('td'),function(hero1,index1,objs1){
           var s = hero1.getAttribute('lay-ymd');
           if(map.get(s)){
               hero1.className += ' laydate-disabled';
           }

       });
    }


    var day3 = new Date();
    var s3 = (day3.getFullYear())+"-12-31";
    // laydate国际版
    laydate.render({
        elem: '#ladate1'
        ,lang: 'en'
        ,min : 2
        ,max : s3
        ,btns: ['clear']
        ,change: function(value, date, endDate){
            disabledLaydateInfo();
        }
        ,ready: function() {
            disabledLaydateInfo();
        }
        ,done: function(value, date, endDate){
            // 回调函数
            $scope.checkPNR2();
        }
    });
    laydate.render({
        elem: '#ladate2'
        ,lang: 'en'
        ,min : 2
        ,max : s3
        ,btns: ['clear']
        ,change: function(value, date, endDate){
            disabledLaydateInfo();
        }
        ,ready: function() {
            disabledLaydateInfo();
        }
        ,done: function(value, date, endDate){
            // 回调函数
            $scope.checkPNR2();
        }
    });
    laydate.render({
        elem: '#ladate3'
        ,lang: 'en'
        ,min : 2
        ,max : s3
        ,btns: ['clear']
        ,change: function(value, date, endDate){
            disabledLaydateInfo();
        }
        ,ready: function() {
            disabledLaydateInfo();
        }
        ,done: function(value, date, endDate){
            // 回调函数
            $scope.checkPNR2();
        }
    });
    laydate.render({
        elem: '#ladate4'
        ,lang: 'en'
        ,min : 2
        ,max : s3
        ,btns: ['clear']
        ,change: function(value, date, endDate){
            disabledLaydateInfo();
        }
        ,ready: function() {
            disabledLaydateInfo();
        }
        ,done: function(value, date, endDate){
            // 回调函数
            $scope.checkPNR2();
        }
    });
    laydate.render({
        elem: '#ladate5'
        ,lang: 'en'
        ,min : 2
        ,max : s3
        ,btns: ['clear']
        ,change: function(value, date, endDate){
            disabledLaydateInfo();
        }
        ,ready: function() {
            disabledLaydateInfo();
        }
        ,done: function(value, date, endDate){
            // 回调函数
            $scope.checkPNR2();
        }
    });
    laydate.render({
        elem: '#ladate6'
        ,lang: 'en'
        ,min : 2
        ,max : s3
        ,btns: ['clear']
        ,change: function(value, date, endDate){
            disabledLaydateInfo();
        }
        ,ready: function() {
            disabledLaydateInfo();
        }
        ,done: function(value, date, endDate){
            // 回调函数
            $scope.checkPNR2();
        }
    });
    laydate.render({
        elem: '#ladate7'
        ,lang: 'en'
        ,min : 2
        ,max : s3
        ,btns: ['clear']
        ,change: function(value, date, endDate){
            disabledLaydateInfo();
        }
        ,ready: function() {
            disabledLaydateInfo();
        }
        ,done: function(value, date, endDate){
            // 回调函数
            $scope.checkPNR2();
        }
    });
    laydate.render({
        elem: '#ladate8'
        ,lang: 'en'
        ,min : 2
        ,max : s3
        ,btns: ['clear']
        ,change: function(value, date, endDate){
            disabledLaydateInfo();
        }
        ,ready: function() {
            disabledLaydateInfo();
        }
        ,done: function(value, date, endDate){
            // 回调函数
            $scope.checkPNR2();
        }
    });
    laydate.render({
        elem: '#ladate9'
        ,lang: 'en'
        ,min : 2
        ,max : s3
        ,btns: ['clear']
        ,change: function(value, date, endDate){
            disabledLaydateInfo();
        }
        ,ready: function() {
            disabledLaydateInfo();
        }
        ,done: function(value, date, endDate){
            // 回调函数
            $scope.checkPNR2();
        }
    });


    // 样式初始化
    $scope.OneWay = false;
    $scope.RoundTrip = false;
    $scope.checkPNR = function(txt){
        $scope.OneWay = false;
        $scope.RoundTrip = false;
        if(txt == 'OneWay'){
            $scope.OneWay = true;
            $scope.er.triptype = 1;
        }else{
            $scope.RoundTrip = true;
            $scope.er.triptype = 2;
        }
        $scope.checkPNR2();
    }

    $scope.checkPNR1 = function(txt){
        if(txt == 1){
            if($('#checkbox1').is(':checked')) {
                $('#checkbox2').attr("disabled",true);
                $('#ladate4').attr("disabled",true);
                $('#ladate5').attr("disabled",true);
                $('#ladate6').attr("disabled",true);
                $("#ladate4").val("");
                $("#ladate5").val("");
                $("#ladate6").val("");
            }else{
                $('#checkbox2').attr("disabled",false);
                $('#ladate4').attr("disabled",false);
                $('#ladate5').attr("disabled",false);
                $('#ladate6').attr("disabled",false);
            }
        }else{
            if($('#checkbox2').is(':checked')) {
                $('#checkbox1').attr("disabled",true);
                $('#ladate7').attr("disabled",true);
                $('#ladate8').attr("disabled",true);
                $('#ladate9').attr("disabled",true);
                $("#ladate7").val("");
                $("#ladate8").val("");
                $("#ladate9").val("");
            }else{
                $('#checkbox1').attr("disabled",false);
                $('#ladate7').attr("disabled",false);
                $('#ladate8').attr("disabled",false);
                $('#ladate9').attr("disabled",false);
            }
        }
        $scope.checkPNR2();
    }


    // 是否可以提交
    $scope.checkPNR2 = function(){
        if($scope.OneWay){
            if($("#ladate1").val() == "" && $("#ladate2").val() == "" && $("#ladate3").val() == ""){
                $(".eFormContent-submit").attr("disabled",true);
            }else{
                $(".eFormContent-submit").attr("disabled",false);
            }
        }else{
            if($('#checkbox1').is(':checked')) {
                if($("#ladate7").val() == "" && $("#ladate8").val() == "" && $("#ladate9").val() == ""){
                    $(".eFormContent-submit").attr("disabled",true);
                }else{
                    $(".eFormContent-submit").attr("disabled",false);
                }
            }else if($('#checkbox2').is(':checked')){
                if($("#ladate4").val() == "" && $("#ladate5").val() == "" && $("#ladate6").val() == ""){
                    $(".eFormContent-submit").attr("disabled",true);
                }else{
                    $(".eFormContent-submit").attr("disabled",false);
                }
            }else if(!$('#checkbox1').is(':checked') && !$('#checkbox2').is(':checked')){
                var falg1 = true;
                var falg2 = true;
                if($("#ladate4").val() == "" && $("#ladate5").val() == "" && $("#ladate6").val() == ""){
                    falg1 = false;
                }else{
                    falg1 = true;
                }
                if($("#ladate7").val() == "" && $("#ladate8").val() == "" && $("#ladate9").val() == ""){
                    falg2 = false;
                }else{
                    falg2 = true;
                }
                if(falg1 && falg2){
                    $(".eFormContent-submit").attr("disabled",false);
                }else{
                    $(".eFormContent-submit").attr("disabled",true);
                }
            }
        }
    }


    $scope.eFormContent = true;
    $scope.eFormError = false;
    $scope.eFormSuccess = false;
    $scope.e = {};
    $scope.e.type = "7";
    $scope.er = {};
    $scope.er.triptype = 0;
    $scope.er.outboundunchanged = 0;
    $scope.er.inboundunchanged = 0;

    // 获取那个dlId进入
    $scope.e.dlId = GetUrlParam("dlId")==""?0:GetUrlParam("dlId");
    var crm_uid = $location.search().crm_uid;
    if(crm_uid == undefined){
        crm_uid = "";
    }
    /**
     * 提交
     */
    var lock1 = false; //默认未锁定
    $scope.eFormContentSubmit = function () {
        $scope.e.langId =  $scope.langId;
        if($scope.er.triptype ==1){
            $scope.er.outboundone = $("#ladate1").val();
            $scope.er.outboundtwo = $("#ladate2").val();
            $scope.er.outboundthree = $("#ladate3").val();
        }else{
            $scope.er.outboundone = $("#ladate4").val();
            $scope.er.outboundtwo = $("#ladate5").val();
            $scope.er.outboundthree = $("#ladate6").val();
            $scope.er.inboundone = $("#ladate7").val();
            $scope.er.inboundtwo = $("#ladate8").val();
            $scope.er.inboundthree = $("#ladate9").val();
            if($('#checkbox1').is(':checked')) {
                $scope.er.outboundunchanged = 1;
                $scope.er.outboundone = "";
                $scope.er.outboundtwo = "";
                $scope.er.outboundthree = "";
            }
            if($('#checkbox2').is(':checked')) {
                $scope.er.inboundunchanged = 1;
                $scope.er.inboundone = "";
                $scope.er.inboundtwo = "";
                $scope.er.inboundthree = "";
            }

        }
        var bootstrapValidator = $(".eForm-div1").data('bootstrapValidator');
        bootstrapValidator.validate();
        if(bootstrapValidator.isValid()){
            // 验证成功
            var index =  layer.load(0, {shade: false});
            if(!lock1) {
                lock1 = true; // 锁定
                $http({
                    method : 'post',
                    url : ctx + 'appJson/E/add',
                    data : {"eform":$scope.e,"crmuid":crm_uid,"relation":$scope.er},
                }).then(function(resp){
                    $scope.data = resp.data;
                    $("#booking-steps-li2").removeClass().addClass('active');
                    $("#booking-steps-li1").removeClass();
                    $scope.eFormContent = false;
                    if( $scope.data.code == 200 ){
                        $scope.eFormSuccess = true;
                    }else{
                        $scope.eFormError = true;
                    }
                    layer.close(index);
                });
            }
        }else{
            return;
        }
    }

    /**
     * error返回
     */
    $scope.getEformContent = function () {
        $("#booking-steps-li1").removeClass().addClass('active');
        $("#booking-steps-li2").removeClass();
        $scope.eFormError = false;
        $scope.eFormSuccess = false;
        $scope.eFormContent = true;
        lock1 = false; //解锁
    }

    /**
     * success返回
     */
    $scope.getEformContentDone = function () {
        location.reload();
    }

    /**
     * 内容验证
     */
    $(function () {
        $translate.use($scope.langId.toString());
        window.onload = function(){
            $('.eForm-div1').bootstrapValidator({
                message: 'This value is not valid',
                feedbackIcons: {
                    valid: 'glyphicon glyphicon-ok',
                    invalid: 'glyphicon glyphicon-remove',
                    validating: 'glyphicon glyphicon-refresh'
                },
                fields: {
                    firstName: {
                        validators: {
                            notEmpty: {
                                message: T.T('error2')
                            }
                        }
                    },
                    lastName: {
                        validators: {
                            notEmpty: {
                                message: T.T('error2')
                            }
                        }
                    },
                    pnr : {
                        validators: {
                            notEmpty: {
                                message: T.T('error2')
                            }
                        }
                    },
                    email: {
                        validators: {
                            notEmpty: {
                                message: T.T('error2')
                            },
                            emailAddress: {
                                message: T.T('error3')
                            }
                        }
                    }

                }
            });
        }
    });

}]);



// eForm8Controller
myapp.controller("eForm8Controller",["$scope","$http","$location","$translate","T",function ($scope, $http,$location,$translate,T) {
    // 设置默认,langId==6语言，英文;
    $scope.langId = parseInt(GetUrlParam("langId")==""?6:GetUrlParam("langId"));
    gaixialatu($scope.langId);
    function into(langID){
        $http({
            method : 'post',
            url : ctx + "appJson/getLanguageAll",
            params:{"langId": langID}
        }).success(function (data) {
            $scope.isGetUrl = true;
            $scope.languages = data;
        })

        $http({
            method : 'post',
            url : ctx + "appJson/E/getAreaNames"
        }).success(function (data) {
            $scope.e_area_names = data;
        })
    }
    // 初始化
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
            gaixialatu($scope.langId);
            $scope.getUrlRefundIn();
        }
        // 强制更新  $scope.apply();
    }

    // laydate国际版
    laydate.render({
        elem: '#ladate1'
        ,lang: 'en'
        ,done:function(value,dates,edate){
            $('#ladate1').val(value);
            $('.eForm-div1').data('bootstrapValidator')
                .updateStatus('ladate1', 'NOT_VALIDATED',null)
                .validateField('ladate1');
        }
    });
    laydate.render({
        elem: '#ladate2'
        ,lang: 'en'
        ,done:function(value,dates,edate){
            $('#ladate2').val(value);
            $('.eForm-div1').data('bootstrapValidator')
                .updateStatus('ladate2', 'NOT_VALIDATED',null)
                .validateField('ladate2');
        }
    });
    laydate.render({
        elem: '#ladate3'
        ,lang: 'en'
        ,done:function(value,dates,edate){
            $('#ladate3').val(value);
            $('.eForm-div1').data('bootstrapValidator')
                .updateStatus('ladate3', 'NOT_VALIDATED',null)
                .validateField('ladate3');
        }
    });
    laydate.render({
        elem: '#ladate4'
        ,lang: 'en'
        ,done:function(value,dates,edate){
            $('#ladate4').val(value);
            $('.eForm-div1').data('bootstrapValidator')
                .updateStatus('ladate4', 'NOT_VALIDATED',null)
                .validateField('ladate4');
        }
    });
    laydate.render({
        elem: '#ladate5'
        ,lang: 'en'
        ,done:function(value,dates,edate){
            $('#ladate5').val(value);
            $('.eForm-div1').data('bootstrapValidator')
                .updateStatus('ladate5', 'NOT_VALIDATED',null)
                .validateField('ladate5');
        }
    });

    // 样式初始化
    $scope.myTabContent = false;
    $scope.PNR = true
    $scope.checkPNR = function(){
        if(!$scope.PNR){
            $('.pnr-div').hide();
            $scope.myTabContent = true;
        }else{
            $('.pnr-div').show();
            $scope.myTabContent = false;
        }
    }
    $scope.eFormContent = true;
    $scope.eFormError = false;
    $scope.eFormSuccess = false;
    $scope.e = {};
    $scope.e.type = "8";
    // 获取那个dlId进入
    $scope.e.dlId = GetUrlParam("dlId")==""?0:GetUrlParam("dlId");
    var crm_uid = $location.search().crm_uid;
    if(crm_uid == undefined){
        crm_uid = "";
    }

    /**
     * 提交
     */
    var lock1 = false; //默认未锁定
    $scope.eFormContentSubmit = function () {
        $scope.e.langId =  $scope.langId;
        if(!$scope.PNR){
            $scope.pnr = "";
            $scope.e.triptype = parseInt($(".nav-tabs .active").attr("data"));
            if($scope.e.triptype == 1){
                $scope.e.departing =  $scope.tab1_departing;
                $scope.e.going =  $scope.tab1_going;
                $scope.e.departingdate =   $("#ladate1").val();
                $scope.e.goingdate =   $("#ladate2").val();
                $scope.e.departingnew =  "";
                $scope.e.goingnew =  "";
            }else if($scope.e.triptype == 2){
                $scope.e.departing =  $scope.tab2_departing;
                $scope.e.going =  $scope.tab2_going;
                $scope.e.departingdate =  $("#ladate3").val();
                $scope.e.goingdate = "";
                $scope.e.departingnew =  "";
                $scope.e.goingnew =  "";
            }else if($scope.e.triptype == 3){
                $scope.e.departing =  $scope.e_area_names[0].key;
                $scope.e.going =  $scope.tab3_going;
                $scope.e.departingdate =  $("#ladate4").val();
                $scope.e.goingdate = $("#ladate5").val();
                $scope.e.departingnew =  $scope.tab3_departingnew;
                $scope.e.goingnew =  $scope.e_area_names[0].key;
            }
        }
        var bootstrapValidator = $(".eForm-div1").data('bootstrapValidator');
        bootstrapValidator.validate();
        if(bootstrapValidator.isValid()){
            // 验证成功
            var index =  layer.load(0, {shade: false});
            if(!lock1) {
                lock1 = true; // 锁定
                $http({
                    method : 'post',
                    url : ctx + 'appJson/E/add',
                    data : {"eform":$scope.e,"crmuid":crm_uid},
                }).then(function(resp){
                    $scope.data = resp.data;
                    $("#booking-steps-li2").removeClass().addClass('active');
                    $("#booking-steps-li1").removeClass();
                    $scope.eFormContent = false;
                    if( $scope.data.code == 200 ){
                        $scope.eFormSuccess = true;
                    }else{
                        $scope.eFormError = true;
                    }
                    layer.close(index);
                });
            }
        }else{
            return;
        }
    }

    /**
     * error返回
     */
    $scope.getEformContent = function () {
        $("#booking-steps-li1").removeClass().addClass('active');
        $("#booking-steps-li2").removeClass();
        $scope.eFormError = false;
        $scope.eFormSuccess = false;
        $scope.eFormContent = true;
        lock1 = false; //解锁
    }

    /**
     * success返回
     */
    $scope.getEformContentDone = function () {
        location.reload();
    }


    /**
     * 跳转e-refund
     */
    $scope.getUrlRefundIn = function () {
        var u = "https://securesettlement.net/hkexpress/e-refund/";
        if($scope.langId==1){
            u += "zh_hk";
        }else if($scope.langId==2){
            u += "zh_cn";
        }else if($scope.langId==4){
            u += "ja";
        }else if($scope.langId==5){
            u += "ko";
        }else if($scope.langId==6){
            u += "en";
        }else{
            u += "en";
        }
        $scope.getUrlRefund = u;
    }
    $scope.getUrlRefundIn();

    /**
     * 内容验证
     */
    $(function () {
        $translate.use($scope.langId.toString());
        window.onload = function(){
            $('.eForm-div1').bootstrapValidator({
                message: 'This value is not valid',
                feedbackIcons: {
                    valid: 'glyphicon glyphicon-ok',
                    invalid: 'glyphicon glyphicon-remove',
                    validating: 'glyphicon glyphicon-refresh'
                },
                fields: {
                    firstName: {
                        validators: {
                            notEmpty: {
                                message: T.T('error2')
                            }
                        }
                    },
                    lastName: {
                        validators: {
                            notEmpty: {
                                message: T.T('error2')
                            }
                        }
                    },
                    pnr : {
                        validators: {
                            notEmpty: {
                                message: T.T('error2')
                            }
                        }
                    },
                    pnrnew : {
                        validators: {
                            notEmpty: {
                                message: T.T('error2')
                            }
                        }
                    },
                    email: {
                        validators: {
                            notEmpty: {
                                message: T.T('error2')
                            },
                            emailAddress: {
                                message: T.T('error3')
                            }
                        }
                    },
                    tab1_departing: {
                        validators: {
                            notEmpty: {
                                message: T.T('error4')
                            },
                            callback :{
                                message: T.T('error4'),
                                callback: function(value, validator) {
                                    if (value == "?") {
                                        return false;
                                    } else {
                                        return true;
                                    }
                                }
                            }
                        }
                    },
                    tab1_going: {
                        validators: {
                            notEmpty: {
                                message: T.T('error4')
                            },
                            callback :{
                                message: T.T('error4'),
                                callback: function(value, validator) {
                                    if (value == "?") {
                                        return false;
                                    } else {
                                        return true;
                                    }
                                }
                            }
                        }
                    },
                    ladate1: {
                        validators: {
                            notEmpty: {
                                message: T.T('error2')
                            }
                        }
                    },
                    ladate2: {
                        validators: {
                            notEmpty: {
                                message: T.T('error2')
                            }
                        }
                    },
                    ladate3: {
                        validators: {
                            notEmpty: {
                                message: T.T('error2')
                            }
                        }
                    },
                    ladate4: {
                        validators: {
                            notEmpty: {
                                message: T.T('error2')
                            }
                        }
                    },
                    ladate5: {
                        validators: {
                            notEmpty: {
                                message: T.T('error2')
                            }
                        }
                    },
                    tab2_departing: {
                        validators: {
                            notEmpty: {
                                message: T.T('error4')
                            },
                            callback :{
                                message: T.T('error4'),
                                callback: function(value, validator) {
                                    if (value == "?") {
                                        return false;
                                    } else {
                                        return true;
                                    }
                                }
                            }
                        }
                    },
                    tab2_going: {
                        validators: {
                            notEmpty: {
                                message: T.T('error4')
                            },
                            callback :{
                                message: T.T('error4'),
                                callback: function(value, validator) {
                                    if (value == "?") {
                                        return false;
                                    } else {
                                        return true;
                                    }
                                }
                            }
                        }
                    },
                    tab3_going: {
                        validators: {
                            notEmpty: {
                                message: T.T('error4')
                            },
                            callback :{
                                message: T.T('error4'),
                                callback: function(value, validator) {
                                    if (value == "?") {
                                        return false;
                                    } else {
                                        return true;
                                    }
                                }
                            }
                        }
                    },
                    tab3_departingnew: {
                        validators: {
                            notEmpty: {
                                message: T.T('error4')
                            },
                            callback :{
                                message: T.T('error4'),
                                callback: function(value, validator) {
                                    if (value == "?") {
                                        return false;
                                    } else {
                                        return true;
                                    }
                                }
                            }
                        }
                    }



                }
            });
        }
    });

}]);


// eForm9Controller
myapp.controller("eForm9Controller",["$scope","$http","$location","$translate","T",function ($scope, $http,$location,$translate,T) {
    // 设置默认,langId==6语言，英文;
    $scope.langId = parseInt(GetUrlParam("langId")==""?6:GetUrlParam("langId"));
    gaixialatu($scope.langId);
    function into(langID){
        $http({
            method : 'post',
            url : ctx + "appJson/getLanguageAll",
            params:{"langId": langID}
        }).success(function (data) {
            $scope.isGetUrl = true;
            $scope.languages = data;
        })

    }
    // 初始化
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
            gaixialatu($scope.langId);
        }
        // 强制更新  $scope.apply();
    }

    // laydate国际版
    laydate.render({
        elem: '#ladate1'
        ,lang: 'en'
        ,done:function(value,dates,edate){
            $('#ladate1').val(value);
            $('.eForm-div1').data('bootstrapValidator')
                .updateStatus('ladate1', 'NOT_VALIDATED',null)
                .validateField('ladate1');
        }
    });


    // 样式初始化
    $scope.PNR = true
    $scope.eFormContent = true;
    $scope.eFormError = false;
    $scope.eFormSuccess = false;
    $scope.e = {};
    $scope.e.type = "9";
    // 获取那个dlId进入
    $scope.e.dlId = GetUrlParam("dlId")==""?0:GetUrlParam("dlId");

    /**
     * 提交
     */
    var lock1 = false; //默认未锁定
    $scope.eFormContentSubmit = function () {
        $scope.e.langId =  $scope.langId;
        var bootstrapValidator = $(".eForm-div1").data('bootstrapValidator');
        bootstrapValidator.validate();
        if(bootstrapValidator.isValid()){
            // 验证成功
            var index =  layer.load(0, {shade: false});
            if(!lock1) {
                $scope.e.departuredate = $("#ladate1").val();
                lock1 = true; // 锁定
                $http({
                    method : 'post',
                    url : ctx + 'appJson/E/searchFlightIRR',
                    data : $scope.e
                }).then(function(resp){
                    $scope.data = resp.data;
                    $("#booking-steps-li2").removeClass().addClass('active');
                    $("#booking-steps-li1").removeClass();
                    $scope.eFormContent = false;
                    if( $scope.data.code == 200 ){
                        $scope.map = resp.data.result.map;
                        $scope.map_no_date = resp.data.result.map_no_date;
                        $scope.eFormSuccess = true;
                    }else{
                        $scope.eFormError = true;
                    }
                    layer.close(index);
                });
            }
        }else{
            return;
        }
    }

    /**
     * error返回
     */
    $scope.getEformContent = function () {
        $("#booking-steps-li1").removeClass().addClass('active');
        $("#booking-steps-li2").removeClass();
        $scope.eFormError = false;
        $scope.eFormSuccess = false;
        $scope.eFormContent = true;
        lock1 = false; //解锁
    }

    /**
     * success返回
     */
    $scope.getEformContentDone = function () {
        location.reload();
    }

    /**
     * 内容验证
     */
    $(function () {
        $translate.use($scope.langId.toString());
        window.onload = function(){
            $('.eForm-div1').bootstrapValidator({
                message: 'This value is not valid',
                feedbackIcons: {
                    valid: 'glyphicon glyphicon-ok',
                    invalid: 'glyphicon glyphicon-remove',
                    validating: 'glyphicon glyphicon-refresh'
                },
                fields: {
                    pnr : {
                        validators: {
                            notEmpty: {
                                message: T.T('error2')
                            }
                        }
                    },
                    flightno: {
                        validators: {
                            notEmpty: {
                                message: T.T('error2')
                            }
                        }
                    },
                    ladate1: {
                        validators: {
                            notEmpty: {
                                message: T.T('error2')
                            }
                        }
                    }



                }
            });
        }
    });

}]);


// eForm11Controller
myapp.controller("eForm11Controller",["$scope","$http","$location","$translate","T",function ($scope, $http,$location,$translate,T) {
    // 设置默认,langId==6语言，英文;
    $scope.langId = parseInt(GetUrlParam("langId")==""?6:GetUrlParam("langId"));
    gaixialatu($scope.langId);
    function into(langID){
        $http({
            method : 'post',
            url : ctx + "appJson/getLanguageAll",
            params:{"langId": langID}
        }).success(function (data) {
            $scope.isGetUrl = true;
            $scope.languages = data;
        })

    }
    // 初始化
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
            gaixialatu($scope.langId);
        }
        // 强制更新  $scope.apply();
    }


    // 样式初始化
    $scope.myTabContent = false;
    $scope.PNR = true
    $scope.checkPNR = function(){
        if(!$scope.PNR){
            $('.pnr-div').hide();
            $scope.myTabContent = true;
        }else{
            $('.pnr-div').show();
            $scope.myTabContent = false;
        }
    }
    $scope.eFormContent = true;
    $scope.eFormError = false;
    $scope.eFormSuccess = false;
    $scope.TermsandConditions = false;

    $scope.e = {};
    $scope.e.type = "11";
    $scope.er = {};
    $scope.er.elevenstyle = "";
    $scope.checkElevenstyle = function(txt){
        if(txt == 1){
            $scope.er.elevenstyle = "Issue the gift certificate according to the refund passenger list in the applied refund order"
        }else{
            $scope.er.elevenstyle = "Only one gift certificate will be sent to the above passenger"
        }
    }


    // 获取那个dlId进入
    $scope.e.dlId = GetUrlParam("dlId")==""?0:GetUrlParam("dlId");
    var crm_uid = $location.search().crm_uid;
    if(crm_uid == undefined){
        crm_uid = "";
    }
    /**
     * 提交
     */
    var lock1 = false; //默认未锁定
    $scope.eFormContentSubmit = function () {
        $scope.e.langId =  $scope.langId;
        // 验证成功
        var index =  layer.load(0, {shade: false});
        if(!lock1) {
            lock1 = true; // 锁定
            $http({
                method : 'post',
                url : ctx + 'appJson/E/add',
                data : {"eform":$scope.e,"crmuid":crm_uid,"relation":$scope.er},
            }).then(function(resp){
                $scope.data = resp.data;
                $("#booking-steps-li1").removeClass().addClass('active');
                $("#booking-steps-li1-1").removeClass().addClass('active');
                $("#booking-steps-li2").removeClass().addClass('active');
                $scope.TermsandConditions = false;
                $scope.eFormContent = false;
                if( $scope.data.code == 200 ){
                    $scope.eFormSuccess = true;
                }else{
                    $scope.eFormError = true;
                }
                layer.close(index);
            });
        }
    }


    $scope.getTongyi = function (){
        if($('#checkbox').is(':checked')){
            $(".eFormError-submit-xia").attr("disabled",false);
        }else{
            $(".eFormError-submit-xia").attr("disabled",true);
        }
    }

    /**
     * 下一层
     */
    $scope.getXiaTermsandConditions = function () {

        var bootstrapValidator = $(".eForm-div1").data('bootstrapValidator');
        bootstrapValidator.validate();
        if(bootstrapValidator.isValid()){
            if($scope.er.elevenstyle == ""){
                alert(T.T("lab130"));
                return;
            }
            $("#booking-steps-li1").removeClass().addClass('active');
            $("#booking-steps-li1-1").removeClass().addClass('active');
            $("#booking-steps-li2").removeClass();
            $scope.eFormError = false;
            $scope.eFormSuccess = false;
            $scope.eFormContent = false;
            $scope.TermsandConditions = true;
        }else{
            return;
        }
    }

    /**
     * 上一层
     */
    $scope.getShangTermsandConditions = function () {
        $("#booking-steps-li1").removeClass().addClass('active');
        $("#booking-steps-li1-1").removeClass();
        $("#booking-steps-li2").removeClass();
        $scope.eFormError = false;
        $scope.eFormSuccess = false;
        $scope.TermsandConditions = false;
        $scope.eFormContent = true;
        lock1 = false; //解锁
    }


    /**
     * error返回
     */
    $scope.getEformContent = function () {
        $("#booking-steps-li1").removeClass().addClass('active');
        $("#booking-steps-li2").removeClass();
        $scope.eFormError = false;
        $scope.eFormSuccess = false;
        $scope.eFormContent = true;
        lock1 = false; //解锁
    }

    /**
     * success返回
     */
    $scope.getEformContentDone = function () {
        location.reload();
    }

    /**
     * 内容验证
     */
    $(function () {
        $translate.use($scope.langId.toString());
        window.onload = function(){
            $('.eForm-div1').bootstrapValidator({
                message: 'This value is not valid',
                feedbackIcons: {
                    valid: 'glyphicon glyphicon-ok',
                    invalid: 'glyphicon glyphicon-remove',
                    validating: 'glyphicon glyphicon-refresh'
                },
                fields: {
                    firstName: {
                        validators: {
                            notEmpty: {
                                message: T.T('error2')
                            }
                        }
                    },
                    lastName: {
                        validators: {
                            notEmpty: {
                                message: T.T('error2')
                            }
                        }
                    },
                    pnr : {
                        validators: {
                            notEmpty: {
                                message: T.T('error2')
                            }
                        }
                    },
                    email: {
                        validators: {
                            notEmpty: {
                                message: T.T('error2')
                            },
                            emailAddress: {
                                message: T.T('error3')
                            }
                        }
                    }

                }
            });
        }
    });

}]);
