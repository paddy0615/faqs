var myapp = angular.module("myapp",[]);

myapp.controller("serachController",["$scope","$http",function ($scope, $http) {

    /* 搜索框  */
    $scope.searchText = "";
    $scope.onKeyup = function(event){
        var e = event || window.event || arguments.callee.caller.arguments[0];
        var s = $scope.searchText;
      /*  if(e && e.keyCode==27){ // 按 Esc
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
    $scope.getSearch = function () {
        if($scope.searchText == "") return;
        $http({
            method : 'post',
            url : "/json/getSerDetaileds",
            params:{"serach" : $scope.searchText}
        }).success(function (data) {
            if(data){
                /* 成功*/
                $scope.result = data.result;
            }
        })
    }

    // 初始化
    info();
    function info(){
        $http({
            method : 'post',
            url : "/json/getHotspot"
        }).success(function (data) {
            if(data){
                $scope.hotspots = data.result.detaileds;
            }
        })
    }

    // 详情事件
    var lock = false; //默认未锁定
    $scope.getDetailed = function (dlId) {
        if(!lock) {
            lock = true; // 锁定
            $http({
                method : 'post',
                url : "/json/addHotspot",
                params:{"dlId" : dlId}
            }).success(function (data) {
                if(data){
                    var url = "/faqs/indexDetailed?dlId="+dlId+"&serch=true";
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


}]);