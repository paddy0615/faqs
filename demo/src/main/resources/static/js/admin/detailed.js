var myapp = angular.module("myapp",[]);

// admin/detailed
myapp.controller("detailedController",["$scope","$http",function ($scope, $http) {
    // 设置默认,langId==1语言，第一个
    $scope.langId = GetUrlParam("langId")==""?1:GetUrlParam("langId");
    $scope.catId = GetUrlParam("catId")==""?0:GetUrlParam("catId");
    $scope.isGetUrl = false;
    $scope.detaileds = {};
    $scope.detailedsTemp = {};
    var url = "/json/admin/getDetailedPage";
    // 初始化
    into($scope.langId);
    function into(langID){
        $http({
            method : 'post',
            url : url,
            params:{"langId": langID}
        }).success(function (data) {
            if(data){
                /* 成功*/
                $scope.result = data.result;
                $scope.detaileds = data.result.detaileds;
                $scope.detailedsTemp = data.result.detaileds;
                $scope.isGetUrl =true;
                $scope.catId = data.result.selectCatId;
            }
        })
    }

    // 语言事件
    $scope.clickLanguage = function() {
        if($scope.isGetUrl){
            $scope.result.detaileds = null;
            $scope.result.categories = null;
            into($scope.langId);
        }
    }

    // 类别事件
    $scope.clickCategory = function() {
        if($scope.isGetUrl){
            $scope.detaileds = null;
            var url = "/json/admin/getDetaileds";
            $http({
                method : 'post',
                url : url,
                params:{"langId": $scope.langId,"catId": $scope.catId}
            }).success(function (data) {
                if(data){
                    /* 成功*/
                    $scope.detaileds = data.result.detaileds;
                    $scope.isGetUrl =true;
                }
            })
        }
    }

    // 编辑url
    $scope.getEdit = function(dlId){
        clicked("/faqs/admin/detailedEdit?dlId="+dlId);
    }

    // 删除
    $scope.getDelete = function(id){
        var lock = false; //默认未锁定
        var myconfirm = layer.confirm("Are you sure you want to delete it?", {
            title:'Information',
            btn: ['OK','Cancel'] //按钮
        }, function(){
            if(!lock) {
                lock = true; // 锁定
                Candelete(id);
            }
            layer.close(myconfirm);
        }, function(){
            layer.close(myconfirm);
        });

    }
    // 能删除
    function  Candelete(id) {
        $http({
            method : 'post',
            url : "/json/admin/detailed/delete",
            params:{"dlId": id}
        }).success(function (data) {
            reloadRoute();
        })
    }

    /* 搜索框  */
    $scope.searchTest = "";
    $scope.getSearchTitle = function () {
        if($scope.searchTest == ""){
            $scope.detaileds = $scope.detailedsTemp;
            return;
        };
        $http({
            method : 'post',
            url : "/json/admin/detailed/getSearchTitle",
            params:{"serach": $scope.searchTest}
        }).success(function (data) {
            /* 成功*/
            $scope.detaileds = data.result.detaileds;
        })
    }
    $scope.onKeyup = function(event){
        var e = event || window.event || arguments.callee.caller.arguments[0];
        var s = $scope.searchText;
        if(e && e.keyCode==13){ // enter 键
            $scope.getSearchTitle();
        }
    }


}]);



// admin/detailedEdit
myapp.controller("detailedEditController",["$scope","$http",function ($scope, $http) {
    $scope.dlId = GetUrlParam("dlId")==""?0:GetUrlParam("dlId");
    $scope.catId = GetUrlParam("catId")==""?0:GetUrlParam("catId");
    $scope.langId = GetUrlParam("langId")==""?1:GetUrlParam("langId");
    $scope.editType = "";
    $scope.detailed = {};
    $scope.categories = {};
    $scope.language = {};
    $scope.languages = {};

    // 初始化
    if($scope.dlId != 0){
        $scope.addType = false;
        var url = "/json/admin/getDetailedUpdate";
        $http({
            method : 'post',
            url : url,
            params:{"dlId": $scope.dlId}
        }).success(function (data) {
            if(data){
                /* 成功*/
                $scope.detailed = data.result.detailed;
                $scope.language = data.result.language;
                $scope.categories = data.result.categories;
                $scope.editType ="< Edit < " + data.result.category.title+" < " + $scope.detailed.title;
                into2();
            }
        })
    }else{
        $scope.addType =true;
        $scope.detailed.title = "";
        var url = "/json/admin/getDetailedAdd";
        $http({
            method : 'post',
            url : url,
        }).success(function (data) {
            if(data){
                /* 成功*/
                $scope.languages = data.result.languages;
                $scope.categories = data.result.categories;
                $scope.catId = data.result.selectCatId;
                $scope.editType ="< Add";
                into2();
            }
        })
    }

    function into2() {
        // 实例化富文本
        if($scope.addType){
            var ue = UE.getEditor('editorAdd');
        }else{
            var editor = UE.getEditor('editorUpdate',{initialFrameWidth: null});
            editor.ready(function() {
                if($scope.detailed.content){
                    editor.setContent($scope.detailed.content);
                }
            })
        }
    }

    // 语言事件
    $scope.clickLanguage = function() {
        var url = "/json/admin/getCategoryByLangId";
        $scope.categories ={};
            $http({
            method : 'post',
            url : url,
            params:{"removeIndex": 1,"langId":$scope.langId}
        }).success(function (data) {
            if(data){
                /* 成功*/
                $scope.categories = data.result.categories;
                $scope.catId = data.result.selectCatId;
            }
        })
    }

    // update
    var lock1 = false; //默认未锁定
    $scope.submitUpdate = function () {
        //判断
        if(!chekFrom()){
            return;
        };
        if(!lock1) {
            lock1 = true; // 锁定
            $scope.detailed.content = UE.getEditor('editorUpdate').getContent();
            $scope.detailed.contentTxt = UE.getEditor('editorUpdate').getContentTxt();
            $http({
                method : 'post',
                url : '/json/admin/detailed/update',
                data : $scope.detailed
            }).then(function(resp){
                layer.alert( 'Success', {
                    title:'Information',
                    skin: 'layui-layer-lan'
                    ,closeBtn: 0
                },function () {
                    var url = "/faqs/admin/detailed";
                    clicked(url);
                });
            },function(resp){
                layer.alert( 'Abnormal error, please contact the administrator or refresh page', {
                    title:'Information',
                    skin: 'layui-layer-lan'
                    ,closeBtn: 0
                },function () {
                    location.reload();
                });
            });
        }

    }

    // add
    var lock = false; //默认未锁定
    $scope.submitAdd = function () {
        //判断
        if(!chekFrom()){
            return;
        };
        if(!lock) {
            lock = true; // 锁定
            $scope.detailed.catId = $scope.catId;
            $scope.detailed.langId = $scope.langId;
            $scope.detailed.content = UE.getEditor('editorAdd').getContent();
            $scope.detailed.contentTxt = UE.getEditor('editorAdd').getContentTxt();
            $http({
                method : 'post',
                url : '/json/admin/detailed/add',
                data : $scope.detailed
            }).then(function(resp){
                layer.alert( 'Success', {
                    title:'Information',
                    skin: 'layui-layer-lan'
                    ,closeBtn: 0
                },function () {
                    var url = "/faqs/admin/detailed";
                    clicked(url);
                });
            },function(resp){
                layer.alert( 'Abnormal error, please contact the administrator or refresh page', {
                    title:'Information',
                    skin: 'layui-layer-lan'
                    ,closeBtn: 0
                },function () {
                    location.reload();
                });
            });
        }

    }

    
    function chekFrom() {
       if($scope.detailed.title == ""){
           layer.alert( 'The title should not be empty.', {
               title:'Information',
               skin: 'layui-layer-lan'
               ,closeBtn: 0
           })
            return false;
       }
        return true;
    }



}]);