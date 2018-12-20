// 初始化样式
$(function () {
    $(".faqOnePage").addClass("active");
})
// admin/feedback
myapp.controller("faqOneController",["$scope","$http",function ($scope, $http) {

    $http({
        method : 'post',
        url : ctx + "appJson/admin/getLibrabrys",
        params :{}
    }).success(function (data) {
        if(data){
            /* 成功*/
            $scope.librabries = data.result.librabries;
        }
    })

    // 查看
    $scope.getSet = function(id){
        clicked(ctx + "appPage/admin/feedbackSet?dfId="+id);
    }


    // 退出
    $scope.goCancel = function(url){
        clicked(url); // 跳url
    }
}]);
