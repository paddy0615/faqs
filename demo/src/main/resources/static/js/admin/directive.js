var myapp = angular.module("myapp",[]);
// 左导航  <left-Directive></left-Directive>
myapp.directive('leftDirective', function() {
    var templateHtml = " <div class=\"col-sm-3 col-md-2 sidebar\">" +
        "                <ul class=\"nav nav-sidebar\">\n" +
        "                <li class=\"languagePage\"><a href=\"javascript:void(0);\" ng-click=\"goCancel('/hkexpress/admin/language')\">Language Maintenance</a></li>\n" +
        "                <li class=\"categoryPage\"><a href=\"javascript:void(0);\" ng-click=\"goCancel('/hkexpress/admin/category')\">Category Maintenance</a></li>\n" +
        "                <li class=\"detailedPage\"><a href=\"javascript:void(0);\" ng-click=\"goCancel('/hkexpress/admin/detailed')\">FAQ</a></li>\n" +
        "            </ul></div>";
    return {
        restrict: 'E',
        template: templateHtml
    }
});

// 头部
myapp.directive('topDirective', function() {
    var templateHtml = "<nav class=\"navbar navbar-inverse navbar-fixed-top\">\n" +
        "    <div class=\"container-fluid\">\n" +
        "        <div class=\"navbar-header\">\n" +
        "            <button type=\"button\" class=\"navbar-toggle collapsed\" data-toggle=\"collapse\" data-target=\"#navbar\" aria-expanded=\"false\" aria-controls=\"navbar\">\n" +
        "                <span class=\"sr-only\">Toggle navigation</span>\n" +
        "                <span class=\"icon-bar\"></span>\n" +
        "                <span class=\"icon-bar\"></span>\n" +
        "                <span class=\"icon-bar\"></span>\n" +
        "            </button>\n" +
        "            <a class=\"navbar-brand\" href=\"#\">FAQs</a>\n" +
        "        </div>\n" +
        "        <div id=\"navbar\" class=\"navbar-collapse collapse\">\n" +
        "            <ul id=\"topTest-hide\" class=\"nav navbar-nav navbar-right\">\n" +
        "                <li class=\"languagePage\"><a href=\"javascript:void(0);\" ng-click=\"goCancel('/hkexpress/admin/language')\">Language Maintenance</a></li>\n" +
        "                <li class=\"categoryPage\"><a href=\"javascript:void(0);\" ng-click=\"goCancel('/hkexpress/admin/category')\">Category Maintenance</a></li>\n" +
        "                <li class=\"detailedPage\"><a href=\"javascript:void(0);\" ng-click=\"goCancel('/hkexpress/admin/detailed')\">FAQ</a></li>\n" +
        "            </ul>\n" +
        "        </div>\n" +
        "    </div>\n" +
        "</nav>";
    return {
        restrict: 'E',
        template: templateHtml
    }
});
