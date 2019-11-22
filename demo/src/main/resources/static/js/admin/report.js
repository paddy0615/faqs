// 初始化样式
$(function () {
    $(".reportPage").addClass("active");
})
// admin/report
myapp.controller("reportController",["$scope","$http",function ($scope, $http) {

    /**
     * Start
     * all
     */
    $http({
        method : 'post',
        url : ctx + "appJson/admin/excel/allPage",
    }).success(function (data) {
        if(data){
            /* 成功*/
            $scope.allPage = data.result.allPage;
            $scope.allPage1 = data.result.allPage1;
            $scope.allPage2 = data.result.allPage2;
        }
    })
    /**
     * End
     * all
     */

    /**
     * Start
     * Question
     */
    // Question-分页
    $scope.languages_Question = {}
    $scope.langId_Question = 0;
    $scope.PageCount_Question = 0; // 总数
    $scope.CurrentPage_Question = 1; // 当前页
    $scope.PageSize_Question = 10; // 显示页数
    // Question-初始化
    $scope.into_Question = function(CurrentPage,PageSize){
        var start = $("#ladate1").val();
        var end = $("#ladate2").val();
        if(end != ""){
            end = end +" 23:59:59";
        }
        var dataMap = {
            langId : $scope.langId_Question,
            startTime : start,
            endTime : end,
            CurrentPage : CurrentPage,
            PageSize : PageSize
        }
        $http({
            method : 'post',
            url : ctx + "appJson/admin/excel/monitorQuestionPage",
            data : JSON.stringify(dataMap)
        }).success(function (data) {
            if(data){
                /* 成功*/
                $scope.eforms_Question = data.result.eforms;
                $scope.languages_Question = data.result.languages;
                $scope.languages_Question.unshift({'id':0,'title':'All'});

                $scope.PageCount_Question = data.result.PageCount;
                if($scope.PageCount_Question > 0){
                   $scope.Paginator_Question($scope.PageCount_Question,CurrentPage,PageSize);
                }else{
                   // 没有数据时不显示
                   $('#pagination_Question').jqPaginator('destroy');
                }
            }
        })
    }
    $scope.into_Question($scope.CurrentPage_Question,$scope.PageSize_Question);
    // Question-type下拉
    $scope.clickLanguage_Question = function () {
        $scope.CurrentPage_Question = 1; // 当前页
        $scope.into_Question($scope.CurrentPage_Question,$scope.PageSize_Question);
    }
    // Question-分页
    $scope.Paginator_Question = function(PageCount,CurrentPage,PageSize){
        if(PageCount == 0){
            return;
        }
        var myPageCount = PageCount;
        var myPageSize = PageSize;
        var countindex = myPageCount % myPageSize > 0 ? (myPageCount / myPageSize) + 1 : (myPageCount / myPageSize);
        $.jqPaginator('#pagination_Question', {
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
                    $scope.CurrentPage_Question = num;
                    $scope.into_Question($scope.CurrentPage_Question,$scope.PageSize_Question);
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
    // Question-search
    $scope.search_Question = function(){
        $scope.into_Question($scope.CurrentPage_Question,$scope.PageSize_Question);
    }
    // Question-download
    $scope.submitDownload_Question = function () {
        var start = $("#ladate1").val();
        var end = $("#ladate2").val();
        if(end != ""){
            end = end +" 23:59:59";
        }
        var form  = document.getElementById("question_from");
        form.action = ctx + "appJson/admin/excel/monitorQuestionReport?langId="+$scope.langId_Question+"&startTime="+start+"&endTime="+end;
        form.submit();
    }
    /**
     * End
     * Question
     */



    /**
     * Start
     * Eform
     */
    // Eform-分页
    $scope.languages_Eform = {}
    $scope.langId_Eform = 0;
    // Eform-初始化
    $scope.into_Eform = function(){
        var start = $("#ladate3").val();
        var end = $("#ladate4").val();
        if(end != ""){
            end = end +" 23:59:59";
        }
        var dataMap = {
            langId : $scope.langId_Eform,
            startTime : start,
            endTime : end
        }
        $http({
            method : 'post',
            url : ctx + "appJson/admin/excel/monitorEformPage",
            data : JSON.stringify(dataMap)
        }).success(function (data) {
            if(data){
                /* 成功*/
                $scope.eforms_Eform = data.result.eforms;
                $scope.languages_Eform = data.result.languages;
                $scope.languages_Eform.unshift({'id':0,'title':'All'});
            }
        })
    }
    $scope.into_Eform();
    // Eform-type下拉
    $scope.clickLanguage_Eform = function () {
        $scope.into_Eform();
    }
    // laydate国际版
    laydate.render({
        elem: '#ladate3'
        ,lang: 'en'
    });
    laydate.render({
        elem: '#ladate4'
        ,lang: 'en'
    });
    // Eform-search
    $scope.search_Eform = function(){
        $scope.into_Eform();
    }
    // Eform-download
    $scope.submitDownload_Eform = function () {
        var start = $("#ladate3").val();
        var end = $("#ladate4").val();
        if(end != ""){
            end = end +" 23:59:59";
        }
        var form  = document.getElementById("efrom_form");
        form.action = ctx + "appJson/admin/excel/monitorEformReport?langId="+$scope.langId_Eform+"&startTime="+start+"&endTime="+end;
        form.submit();
    }
    /**
     * End
     * Eform
     */

    /**
     * Start
     * Detailed
     */
    // Detailed-download
    $scope.submitDownload_Detailed = function () {
        var form  = document.getElementById("detailed_form");
        form.action = ctx + "appJson/admin/excel/faq";
        form.submit();
    }
    /**
     * End
     * Detailed
     */


    // 退出
    $scope.goCancel = function(url){
        clicked(url); // 跳url
    }

}]);
