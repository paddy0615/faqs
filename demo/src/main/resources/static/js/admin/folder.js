// 初始化样式
$(function () {
    $(".folderPage").addClass("active");
})
// admin/folder
myapp.controller("folderController",["$scope","$http",function ($scope, $http) {
    // 默认语言ID
    $scope.langId = Number(GetUrlParam("langId")==""?6:GetUrlParam("langId"));
    // 文件夹等级
    $scope.level = Number(GetUrlParam("level")==""?1:GetUrlParam("level"));
    // 当前文件夹ID-key
    $scope.parenId = GetUrlParam("parenId")==""?1:GetUrlParam("parenId");

    function info(){
        // 数据
        var dataMap = {
            'level' : $scope.level,
            'parenId' : $scope.parenId,
            'langId' : $scope.langId
        }
        $http({
            method : 'post',
            url : ctx + "appJson/admin/getFolderPage",
            data : JSON.stringify(dataMap)
        }).success(function (data) {
            $scope.list = data.result.list;
            $scope.list_librabries = data.result.list_librabries;
            $scope.languages = data.result.languages;
            var template = JSON.stringify($scope.languages)
            $scope.languages_show = JSON.parse(template)
            $scope.languages_show.unshift({'id':0,'title':'All'});
            $scope.tableofContents =  data.result.tableofContents;
            $scope.librabries = data.result.librabries;

        })
    }
    info();

    //语言下拉
    $scope.clickLanguage = function () {
        info();
    }

    // 显示标签
    function showTags(demo,tags) {
        $('.folderTags'+demo).tagEditor({
            initialTags: tags,
            delimiter: ', ', /* space and comma */
            placeholder: 'Enter tags ...'
        });
    }

    // key_random
    $scope.key_random = 0;
    function checkShowTags(data,langId){
        var falg = "";
        for(var j = 0; j < data.length; j++) {
            if(data[j].langId == langId){
                falg = data[j].tags;
                $("input[name='folderTitle"+data[j].langId+"']").val(data[j].title);
                continue;
            }
        }
        return falg;
    }

    // add/update Folder
    $scope.alertSet = function(key){
        // 防止产生多个
        $(".tag-editor").remove();
        if(key > 0){
            $scope.eventStatus = 'Update';
            $scope.key_random = key;
            $http({
                method : 'get',
                url : ctx + "appJson/admin/editGetFolderAll",
                params:{"key_random" : $scope.key_random}
            }).success(function (data) {
                angular.forEach($scope.languages,function (each,index,objs) {
                   $("input[name='folderTitle"+each.id+"']").val("");
                   $(".folderTags"+each.id).val("");
                   showTags(each.id,checkShowTags(data,each.id));
                })

            })
        }else{
            $scope.eventStatus = 'Add';
            $scope.key_random = 0;
            angular.forEach($scope.languages,function (each,index,objs) {
                $("input[name='folderTitle"+each.id+"']").val("");
                $(".folderTags"+each.id).val("");
                showTags(each.id);
            })
        }

        $('#myModalAddFolder').modal();
    }


    // add/update Folder
    $scope.alertSet1 = function(key){
        $('#myModalAddLibray').modal();
    }


    function infoDataMap(l,t,s) {
        return  {
            'langId':l,
            'title' : t,
            'tags' : s
        }
    }

    // submit Folder
    var lock1 = false; //默认未锁定
    $scope.submitAddFolder = function(){
        if(!lock1) {
            lock1 = true; // 锁定
            var index =  layer.load(0, {shade: false});
            var dataMapArr = new Array();
            var flag = true;
            angular.forEach($scope.languages,function (each,index,objs) {
                var title = $("input[name='folderTitle"+each.id+"']").val();
                var tags = $(".folderTags"+each.id).tagEditor('getTags')[0].tags;
                if(title == ""){
                    if(tags != "" ){
                        alert(each.title+" Folder name, cannot be empty");
                        flag = false;
                    }
                    if(each.id == 1){
                        alert(each.title+" Folder name, cannot be empty");
                        flag = false;
                    }
                }else{
                    dataMapArr.push(infoDataMap(each.id,title,tags));
                }
            })
            if(!flag || dataMapArr.length == 0){
                layer.close(index);
                lock1 = false;
                return;
            }
            // 数据
            var dataMap = {
                'key_random': $scope.key_random,
                'level' : $scope.level,
                'parenId' : $scope.parenId,
                'dataMapArr' : dataMapArr
            }
            $http({
                method : 'post',
                url : ctx + "appJson/admin/addORupdateFolder",
                data : JSON.stringify(dataMap)
            }).success(function (data) {
                layer.close(index);
                window.location.reload();//页面刷新
            })

        }
    }

    $scope.selected = [];
    //  submit Library
    var lock2 = false; //默认未锁定
    $scope.submitAddLibrary = function(){
        if(!lock2) {
            lock2 = true; // 锁定
            var index = layer.load(0, {shade: false});
            $scope.selected = [];
            angular.forEach($("[name='checkboxClien']:checkbox"), function (each) {
                if(each.checked){
                    $scope.selected.push(Number(each.value));
                }
            })
            if($scope.selected.length == 0){
                layer.close(index);
                lock2 = false;
                return;
            }
            // 数据
            var dataMap = {
                'key_random': $scope.key_random,
                'level' : $scope.level,
                'parenId' : $scope.parenId,
                'libraryArr' : $scope.selected
            }
            $http({
                method : 'post',
                url : ctx + "appJson/admin/addORupdateLibrary",
                data : JSON.stringify(dataMap)
            }).success(function (data) {
                layer.close(index);
                window.location.reload();//页面刷新
            })

        }


    }


    // 点击下一个文件夹
    $scope.nextFolder = function (parenId,level,langId) {
        var url = ctx + "appPage/admin/folder?parenId="+parenId+"&level="+level+"&langId="+langId;
        clicked(url); // 跳url
    }


    // 退出
    $scope.goCancel = function(url){
        clicked(url); // 跳url
    }
}]);
