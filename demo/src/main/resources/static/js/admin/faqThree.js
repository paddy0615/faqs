// 初始化样式
$(function () {
    $(".faqTwoPage").addClass("active");
})

// faqTwo
myapp.controller("faqTwoController",["$scope","$http",function ($scope, $http) {
    // 状态下拉
    $scope.dl_statuss = [
        {id : -1, name : "All"},
        {id : 1, name : "Show"},
        {id : 0, name : "Hide"}
    ];
    $scope.dl_status = -1;
    $scope.librabries = {}
    $scope.languages = {}
    // librabrie,ID
    $scope.fl_id = Number(GetUrlParam("fl_id")==""?0:GetUrlParam("fl_id"));
    // languages,ID
    $scope.langId = Number(GetUrlParam("selLangId")==""?0:GetUrlParam("selLangId"));

    // 显示下拉
    $scope.info = function(){
        $http({
            method : 'post',
            url : ctx + "appJson/admin/getLibrabryInfo",
            params:{"dlId": $scope.dlId}
        }).success(function (data) {
            /* 成功*/
            $scope.librabries = data.result.librabries;
            $scope.languages = data.result.languages;
            $scope.librabries.unshift({'id':0,'title':'All'})
            $scope.languages.unshift({'id':0,'title':'All'})
        })
    }
    $scope.info();

    // 初始化显示
    $scope.info1 = function(){
        $scope.selectTest = selectTest($scope.langId);
        $http({
            method : 'post',
            url : ctx + "appJson/admin/getLibrabryPage",
            params:{"fl_id": $scope.fl_id,"langId": $scope.langId,"dl_status": $scope.dl_status}
        }).success(function (data) {
            /* 成功*/
            $scope.detaileds = data.result.detaileds;
        })
    }
    $scope.info1();

    // 下拉事件
    $scope.clickLanguage = function() {
        $scope.selected = [];
        $("[name='checkboxAll']:checkbox").prop("checked", false);
        $scope.info1();
    }

    // 编辑url
    $scope.getEdit = function(dlId){
        var url = ctx + "appPage/admin/faqThree?dlId="+dlId+"&selFlId="+$scope.fl_id+"&selLangId="+$scope.langId;
        clicked(url);
    }


    // 复选框
    $scope.selected = [];
    $scope.updateSelection = function($event, id){
        var checkbox = $event.target;
        var action = (checkbox.checked?'add':'remove');
        if(action == 'add' && $scope.selected.indexOf(id) == -1){
            $scope.selected.push(id);
            var flag = true;
            angular.forEach($("[name='checkboxClien']:checkbox"), function (each) {
                if(!each.checked){
                    flag = false;
                }
            })
            $("[name='checkboxAll']:checkbox").prop("checked", flag);
        }
        if(action == 'remove' && $scope.selected.indexOf(id)!=-1){
            var idx = $scope.selected.indexOf(id);
            $scope.selected.splice(idx,1);
            $("[name='checkboxAll']:checkbox").prop("checked", false);
        }
    }
    $scope.updateSelectionAll = function($event){
        var checkbox = $event.target;
        var action = (checkbox.checked?'add':'remove');
        if(action == 'add'){
            $("[name='checkboxClien']:checkbox").prop("checked", true);
            angular.forEach($("[name='checkboxClien']:checkbox"), function (each) {
                $scope.selected.push(Number(each.value));
            })
        }
        if(action == 'remove'){
            $("[name='checkboxClien']:checkbox").prop("checked", false);
            $scope.selected = [];
        }
    }

    // 修改状态
    var lock2 = false; //默认未锁定
    $scope.editStatus = function (id,status,type) {
        if(!lock2){
            lock2 = true;  // 锁定
            var delId = id;
            if(id == 0){// 全部
                var txt = "Do you want to show all of the following?";
                if(type){
                    txt = "Do you want to hide all of the following?";
                }
                delId = $scope.selected.join("-");
                var i = 0;
                angular.forEach($("[name='checkboxClien']:checkbox"), function (each) {
                    if(each.checked){
                        i++;
                    }
                })
                if(i==0){
                    layer.alert("Please check out");
                    lock2 = false;
                    return;
                }
                var myconfirm = layer.confirm(txt, {
                    title:'Information',
                    btn: ['OK','Cancel'] //按钮
                }, function(){
                    CanEditStatus(delId,status);
                    layer.close(myconfirm);
                }, function(){
                    lock2 = false;
                    layer.close(myconfirm);
                });
            }else{ // 单个
                CanEditStatus(delId,status);
            }


        };
    }
    // 能编辑状态
    function CanEditStatus(dlIds,status) {
        $http({
            method : 'post',
            url : ctx + "appJson/admin/detailed/editStatus",
            params:{"dlIds": dlIds ,"status" : status}
        }).success(function (data) {
            /* 成功*/
            var index = layer.alert( 'Success', {
                title:'Information',
                skin: 'layui-layer-lan'
                ,closeBtn: 0
            },function () {
                lock2 = false;
                $scope.info1();
                $scope.selected = [];
                $("[name='checkboxAll']:checkbox").prop("checked", false);
                layer.close(index);
            });
        })
    }

    // 删除
    $scope.getDelete = function(id){
        var delId = id;
        var txt = "Are you sure you want to delete it?";
        if(id == 0){
            delId = $scope.selected.join("-");
            txt = "Do you want to delete the following checks?";
            var i = 0;
            angular.forEach($("[name='checkboxClien']:checkbox"), function (each) {
                if(each.checked){
                    i++;
                }
            })
            if(i==0){
                layer.alert("Please check out");
                return;
            }
        }
        var lock = false; //默认未锁定
        var myconfirm = layer.confirm(txt, {
            title:'Information',
            btn: ['OK','Cancel'] //按钮
        }, function(){
            if(!lock) {
                lock = true; // 锁定
                Candelete(delId);
            }
            layer.close(myconfirm);
        }, function(){
            layer.close(myconfirm);
        });

    }
    // 能删除
    function Candelete(dlIds) {
        $http({
            method : 'post',
            url : ctx + "appJson/admin/detailed/delete",
            params:{"dlIds": dlIds}
        }).success(function (data) {
            $scope.info1();
            $scope.selected = [];
            $("[name='checkboxAll']:checkbox").prop("checked", false);
        })
    }


    // 退出
    $scope.goCancel = function(url){
        clicked(url); // 跳url
    }
}]);

// admin/faqThree
myapp.controller("faqThreeController",["$scope","$http",function ($scope, $http) {
    $scope.dlId = GetUrlParam("dlId")==""?0:GetUrlParam("dlId");
    $scope.selFlId = Number(GetUrlParam("selFlId")==""?0:GetUrlParam("selFlId")); // 做跳转准备
    $scope.selLangId = Number(GetUrlParam("selLangId")==""?0:GetUrlParam("selLangId")); // 做跳转准备
    // 默认
    $scope.fl_id = 1;
    $scope.langId = 1;
    var person = "";
    var personTags = "";

    // 标签
    $scope.showTags = function(tags){
        $('.demo1').tagEditor({
            initialTags: tags,
            delimiter: ', ',
            placeholder: 'Enter tags ...'
        });
    }
    // 显示下拉:父级,语言
    $scope.info = function(){
        $http({
            method : 'post',
            url : ctx + "appJson/admin/getLibrabryInfo",
            params:{}
        }).success(function (data) {
            /* 成功*/
            $scope.librabries = data.result.librabries;
            $scope.languages = data.result.languages;
        })
    }
    // 初始化
    $scope.info1 = function(ind){
        $scope.detailed = {};
        $http({
            method : 'post',
            url : ctx + "appJson/admin/getFaqThreeEdit"+ind,
            params:{"dlId": $scope.dlId,"flId":$scope.fl_id,"langId":$scope.langId}
        }).success(function (data) {
            /* 成功*/
            var editor = UE.getEditor('editorEdit',{initialFrameWidth: null});
            $scope.detailed = data.result.detailed;
            person = JSON.stringify(data.result.detailed);
            if($scope.detailed){
                $scope.editType ="< Edit < " + $scope.detailed.flTitle+" < " + $scope.detailed.title;
                $scope.fl_id = $scope.detailed.flId;
                $scope.langId = $scope.detailed.langId;

                $scope.showTags(data.result.tags);
                editor.ready(function() {
                    if($scope.detailed.content){
                        editor.setContent($scope.detailed.content);
                    }
                })
            }else{
                $scope.editType = "< Add";
                $scope.showTags([]);
                editor.ready(function() {
                    editor.setContent("");
                })
            }
            personTags = $('.demo1').tagEditor('getTags')[0].tags;
        })
    }

    // 判断title是否为空
    function chekFrom() {
        if( "" == $("#inputTitle").val().trim()){
            layer.alert( 'The title should not be empty.', {
                title:'Information',
                skin: 'layui-layer-lan'
                ,closeBtn: 0
            })
            return false;
        }
        return true;
    }

    // 下拉事件
    $scope.clickLanguage = function() {
        $('.demo1').tagEditor('destroy');
        $('.demo1').val("");
        $scope.detailed = {};
        $scope.info1(2);
    }

    //   开始操作
    $scope.info();
    $scope.info1(1);

    // update
    var lock1 = false; //默认未锁定
    $scope.submitUpdate = function (ind) {
        //判断
        if(!chekFrom()){
            return;
        };
        if(!lock1) {
            var tags = $('.demo1').tagEditor('getTags')[0].tags;
            var index =  layer.load(0, {shade: false});
            lock1 = true; // 锁定
            $scope.detailed.flId = $scope.fl_id;
            $scope.detailed.langId = $scope.langId;
            $scope.detailed.content = UE.getEditor('editorEdit').getContent();
            $scope.detailed.contentTxt = UE.getEditor('editorEdit').getContentTxt();
            $http({
                method : 'post',
                url : ctx + 'appJson/admin/detailed/faqThreeUpdate',
                data : JSON.stringify({'detailed':$scope.detailed,'tagsArr':tags})
            }).success(function(resp){
                layer.alert( 'Success', {
                    title:'Information',
                    skin: 'layui-layer-lan'
                    ,closeBtn: 0
                },function () {
                    if(ind == 1){
                        var url = ctx + "appPage/admin/faqThree?dlId="+resp.result.dlId;
                        clicked(url);
                    }else{
                        var url = ctx + "appPage/admin/faqTwo?fl_id="+$scope.selFlId+"&selLangId="+$scope.selLangId;
                        clicked(url);
                    }
                });
                layer.close(index);
            });
        }

    }
    // 退出
    $scope.goCance2 = function(ind){
        if(ind > 0){
            var tags = $('.demo1').tagEditor('getTags')[0].tags;
            $scope.detailed.content = UE.getEditor('editorEdit').getContent();
            if(person != JSON.stringify($scope.detailed) || tags.toString() != personTags.toString()){
                var myconfirm = layer.confirm("Has the content been modified, is it determined to leave?", {
                    title:'Information',
                    btn: ['OK','Cancel'] //按钮
                }, function(){
                    var url = ctx + "appPage/admin/faqTwo?fl_id="+$scope.selFlId+"&selLangId="+$scope.selLangId;
                    clicked(url); // 跳url
                    layer.close(myconfirm);
                }, function(){
                    layer.close(myconfirm);
                });
            }else{
                var url = ctx + "appPage/admin/faqTwo?fl_id="+$scope.selFlId+"&selLangId="+$scope.selLangId;
                clicked(url); // 跳url
            }
        }else{
            var url = ctx + "appPage/admin/faqTwo?fl_id="+$scope.selFlId+"&selLangId="+$scope.selLangId;
            clicked(url); // 跳url
        }
    }

    $scope.goCancel = function(url){
        clicked(url); // 跳url
    }
}]);