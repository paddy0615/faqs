var myapp = angular.module("myapp",[]);

// index
myapp.controller("indexController",["$scope","$http",function ($scope, $http) {
    // 设置默认,langId==6语言，英文;catId = 0默认选第二个
    $scope.langId = GetUrlParam("langId")==""?6:GetUrlParam("langId");
    $scope.catId =  GetUrlParam("catId")==""?0:GetUrlParam("catId");
    var url = "/json/getIndex";
    $scope.isGetUrl = false;
    $scope.cat_title = "";
    //indexShow 显示
    $scope.indexShow = true;
    $scope.searchShow = false;
    // 初始化
    into($scope.langId,$scope.catId);
    function into(langID,catId){
        $http({
            method : 'post',
            url : url,
            params:{"langId": langID,"catId" : catId}
        }).success(function (data) {
            $scope.isGetUrl = true;
            if(data){
                /* 成功*/
                $scope.result = data.result;
                $scope.langId = data.result.langId;
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

    // 语言事件
    $scope.clickLanguage = function() {
        if($scope.isGetUrl){
            var url = "/faqs/index?langId="+$scope.langId+"&catId="+0;
            clicked(url);
        }
        // 强制更新  $scope.apply();
    }

    // 类别事件,idnex当前下标，id:类别ID
    $scope.clickCategory = function (idnex,cat) {
        if(idnex==0){
            clicked("https://www.hkexpress.com/");
        }else{
            if($scope.isGetUrl){
                var url = "/faqs/index?langId="+cat.langId+"&catId="+cat.id;
                clicked(url);
            }
        }
    }

    // 详情事件
    $scope.getDetailed = function (dlId) {
        var url = "/faqs/indexDetailed?dlId="+dlId;
        clicked(url);
    }

    /* 搜索框  */
    $scope.searchTest = "";
    $scope.getSearch = function (){
        if($scope.searchTest == ""){
            return;
        };
        $http({
            method : "post",
            url : "/json/getSearch",
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
       /* if(e && e.keyCode==27){ // 按 Esc
            //要做的事情
            getSearch(s);
        }
        if(e && e.keyCode==113){ // 按 F2
            //要做的事情
            getSearch(s);
        }
        if(e && e.keyCode==13){ // enter 键
            getSearch(s);
        }*/
        $scope.getSearch();
    }

}]);





// indexDetailed
myapp.controller("indexDetailedController",["$scope","$http","$sce",function ($scope, $http, $sce) {
    // 设置默认,langId==6语言，英文;catId = 0默认选第二个
    $scope.dlId = GetUrlParam("dlId");
    $scope.langId = 6;
    $scope.catId = 0;
    var url = "/json/getByDetailed";
    $scope.isGetUrl = false;
    $scope.detailed ={};
    // 初始化
    into($scope.dlId);
    function into(dlId){
        $http({
            method : 'post',
            url : url,
            params:{"dlId": dlId}
        }).success(function (data) {
            $scope.isGetUrl = true;
            if(data){
                /* 成功*/
                $scope.result = data.result;
                $scope.langId = data.result.langId;
                $scope.detailed = data.result.detailed;
                // 显示内容
                $scope.content = $sce.trustAsHtml($scope.detailed.content);
                angular.forEach($scope.result.languages,function (each) {
                    if($scope.langId == each.id){
                        $scope.problem = each.problem;
                        return;
                    }
                })
            }else{
                /* 失败*/
                layer.alert( 'Abnormal error.', {
                    skin: 'layui-layer-lan'
                    ,closeBtn: 0
                });
            }
        })
    }

    // 类别事件,idnex当前下标，id:类别ID
    $scope.clickCategory = function (idnex,cat) {
        if(idnex==0){
            clicked("https://www.hkexpress.com/");
        }else{
            if($scope.isGetUrl){
                var url = "/faqs/index?langId="+cat.langId+"&catId="+cat.id;
                clicked(url);
            }
        }
    }

}]);