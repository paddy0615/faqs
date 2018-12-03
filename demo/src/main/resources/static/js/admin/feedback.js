// 初始化样式
$(function () {
    $(".feedbackPage").addClass("active");
})
// admin/feedback
myapp.controller("feedbackController",["$scope","$http",function ($scope, $http) {
    $scope.df_types = [
        {id : 0, name : "All"},
        {id : 1, name : "Support(+1)"},
        {id : 2, name : "Not support(-1)"}
    ];
    $scope.df_type = 0;
    $scope.feedbacks = {};

    // 初始化
    $scope.into = function(type){
        $http({
            method : 'post',
            url : ctx + "appJson/admin/getFeedbackPage",
            params :{"df_type": type}
        }).success(function (data) {
            if(data){
                /* 成功*/
                $scope.feedbacks = data.result.feedbacks;
            }
        })
    }
    $scope.into($scope.df_type);

    // type下来
    $scope.clickLanguage = function () {
        $scope.into($scope.df_type);
    }

    // 查看
    $scope.getSet = function(id){
        clicked(ctx + "appPage/admin/feedbackSet?dfId="+id);
    }


    // 退出
    $scope.goCancel = function(url){
        clicked(url); // 跳url
    }
}]);

// admin/feedbackSet
myapp.controller("feedbackSetController",["$scope","$http",function ($scope, $http) {
    $scope.dfId = GetUrlParam("dfId")==""?0:GetUrlParam("dfId");
    if($scope.dfId == 0){
        alert("Error");
        return;
    }
    $scope.feedback = "";

    // 初始化
    $scope.into = function(id){
        $http({
            method : 'post',
            url : ctx + "appJson/admin/getFeedbackById",
            params :{"df_id": id}
        }).success(function (data) {
            if(data){
                /* 成功*/
                $scope.feedback = data.result.feedback;
            }
        })
    }
    $scope.into($scope.dfId);

    // 退出
    $scope.goCancel = function(url){
        clicked(url); // 跳url
    }
}]);