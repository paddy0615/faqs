// 初始化样式
$(function () {
    $(".feedbackPage").addClass("active");
})
// admin/feedback
myapp.controller("feedbackController",["$scope","$http",function ($scope, $http) {
    $scope.df_types = [
        {id : 0, name : "All"},
        {id : 1, name : "Useful(+1)"},
        {id : 2, name : "Not Useful(-1)"}
    ];
    $scope.df_type = 0;
    $scope.feedbacks = {};
    // 分页
    $scope.PageCount = 0; // 总数
    $scope.CurrentPage = 1; // 当前页
    $scope.PageSize = 5; // 显示页数

    // 初始化
    $scope.into = function(type,CurrentPage,PageSize){
        var start = $("#ladate1").val();
        var end = $("#ladate2").val();
        if(end != ""){
            end = end +" 23:59:59";
        }
        $http({
            method : 'post',
            url : ctx + "appJson/admin/getFeedbackPage",
            params :{"df_type": type,"CurrentPage": CurrentPage,"PageSize": PageSize,"startTime":start,"endTime":end}
        }).success(function (data) {
            if(data){
                /* 成功*/
                $scope.feedbacks = data.result.feedbacks;
                /*   $scope.PageCount = data.result.PageCount;
                   if($scope.PageCount > 0){
                       $scope.Paginator($scope.PageCount,CurrentPage,PageSize);
                   }else{
                       // 没有数据时不显示
                       $('#pagination').jqPaginator('destroy');
                   }*/
            }
        })
    }
    $scope.into($scope.df_type,$scope.CurrentPage,$scope.PageSize);

    // type下拉
    $scope.clickLanguage = function () {
        $scope.CurrentPage = 1; // 当前页
        $scope.into($scope.df_type,$scope.CurrentPage,$scope.PageSize);
    }

    // 查看
    $scope.getSet = function(id){
        clicked(ctx + "appPage/admin/feedbackSet?dfId="+id);
    }

    // 分页
    $scope.Paginator = function(PageCount,CurrentPage,PageSize){
        if(PageCount == 0){
            return;
        }
        var myPageCount = PageCount;
        var myPageSize = PageSize;
        var countindex = myPageCount % myPageSize > 0 ? (myPageCount / myPageSize) + 1 : (myPageCount / myPageSize);
        $.jqPaginator('#pagination', {
            totalPages: parseInt(countindex),
            visiblePages: 7, //显示分页数
            currentPage: CurrentPage,
            first: '<li class="first"><a href="javascript:;">First</a></li>',
            prev: '<li class="prev"><a href="javascript:;"><i class="arrow arrow2"></i>Prev</a></li>',
            next: '<li class="next"><a href="javascript:;">Next<i class="arrow arrow3"></i></a></li>',
            last: '<li class="last"><a href="javascript:;">Last</a></li>',
            page: '<li class="page"><a href="javascript:;">{{page}}</a></li>',
            onPageChange: function (num, type) {
                $('#text').html('当前第' + num + '页');
                if (type == "change") {
                    $scope.into($scope.df_type,num,$scope.PageSize);
                }
            }
        });
    }

    // laydate国际版
    laydate.render({
        elem: '#ladate1'
        ,lang: 'en'
    });
    laydate.render({
        elem: '#ladate2'
        ,lang: 'en'
    });
    $scope.getLayDate = function(){
        $scope.into($scope.df_type,$scope.CurrentPage,$scope.PageSize);
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