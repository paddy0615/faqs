// 初始化样式
$(function () {
    $(".reportPage").addClass("active");
})
// admin/report
myapp.controller("reportController",["$scope","$http",function ($scope, $http) {
    $scope.languages = {}
    $scope.langId = 0;
    // 分页
    $scope.PageCount = 0; // 总数
    $scope.CurrentPage = 1; // 当前页
    $scope.PageSize = 10; // 显示页数

    // 初始化
    $scope.into = function(CurrentPage,PageSize){
        var start = $("#ladate1").val();
        var end = $("#ladate2").val();
        if(end != ""){
            end = end +" 23:59:59";
        }
        var dataMap = {
            langId : $scope.langId,
            startTime : start,
            endTime : end,
            CurrentPage : CurrentPage,
            PageSize : PageSize
        }
        $http({
            method : 'post',
            url : ctx + "appJson/admin/excel/monitorPage",
            data : JSON.stringify(dataMap)
        }).success(function (data) {
            if(data){
                /* 成功*/
                $scope.eforms = data.result.eforms;
                $scope.languages = data.result.languages;
                $scope.languages.unshift({'id':0,'title':'All'});

                $scope.PageCount = data.result.PageCount;
                if($scope.PageCount > 0){
                   $scope.Paginator($scope.PageCount,CurrentPage,PageSize);
                }else{
                   // 没有数据时不显示
                   $('#pagination').jqPaginator('destroy');
                }
            }
        })
    }
    $scope.into($scope.CurrentPage,$scope.PageSize);

    // type下拉
    $scope.clickLanguage = function () {
        $scope.CurrentPage = 1; // 当前页
        $scope.into($scope.CurrentPage,$scope.PageSize);
    }

    // 查看
    $scope.getSet = function(id){
        window.open(ctx + "appPage/admin/eFormSet?eId="+id);
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
                if (type == "change") {
                    $scope.CurrentPage = num;
                    $scope.into($scope.CurrentPage,$scope.PageSize);
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

    // search
    $scope.getLayDate = function(){
        $scope.into($scope.CurrentPage,$scope.PageSize);
    }


    // download
    $scope.submitUpdate = function () {
        var start = $("#ladate1").val();
        var end = $("#ladate2").val();
        if(end != ""){
            end = end +" 23:59:59";
        }
        var form  = document.getElementById("monitorform");
        form.action = ctx + "appJson/admin/excel/monitor?langId="+$scope.langId+"&startTime="+start+"&endTime="+end;
        form.submit();
    }

    // download1
    $scope.submitUpdate1 = function () {
        var form  = document.getElementById("monitorform");
        form.action = ctx + "appJson/admin/excel/faq";
        form.submit();
    }


    // 退出
    $scope.goCancel = function(url){
        clicked(url); // 跳url
    }
}]);
