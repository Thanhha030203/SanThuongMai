    var app = angular.module('myApp', ['ngRoute']);
    app.config(function ($locationProvider) {
    $locationProvider.hashPrefix('!');
});
app.config(function ($routeProvider) {
    $routeProvider
        .when('/', {
            templateUrl: '/Ibook/seller/homePageSeller',
            controller: 'indexController'
        })
        .when('/orderManagement/sales', {
            templateUrl: '/Ibook/seller/orderManagement/sales',
            controller: 'salesController'
        })
        .when('/orderManagement/transport', {
            templateUrl: '/Ibook/seller/orderManagement/transport',
            controller: 'transportController'
        })
        .when('/orderManagement/createProduct', {
            templateUrl: 'templates/SellerChannel/CreateProduct.html',
            controller: 'createProductController'
        })
        .when('/orderManagement/voucher', {
            templateUrl: 'templates/SellerChannel/Voucher.html',
            controller: 'voucherController'
        })
        .when('/orderManagement/createVoucher', {
            templateUrl: 'templates/SellerChannel/CreateVoucher.html',
            controller: 'createVoucherController'
        })
        .when('/orderManagement/reviews', {
            templateUrl: 'templates/SellerChannel/ShopReview.html',
            controller: 'reviewController'
        })
        .when('/shipping/bulkDelivery', {
            templateUrl: 'templates/SellerChannel/BulkDelivery.html',
            controller: 'bulkDeliveryController'
        })
        .when('/shop/shopProfile', {
            templateUrl: '/Ibook/seller/shop/shopProfile',
            controller: 'shopProfileController'
        })
        .when('/shop/shopProfile/change', {
            templateUrl: '/Ibook/seller/shop/shopProfile/change',
            controller: 'changeProfileController'
        })
        .otherwise({
            redirectTo: '/seller'
        });
});
app.controller("changeProfileController", function ($scope, $routeParams, $route, $http, $rootScope) {
    let host = "http://localhost:8080/rest/shop/";
    $scope.shop = [];
    $scope.logo = null;
    $scope.getProfileShop = function () {
        $scope.shop = [];
        let url = `${host}detail`;
        $http.get(url).then(resp => {
            $scope.shop = resp.data;
            console.log("shop: ", resp.data)
        }).catch(error => {
            console.log("Error", error)
        });
        //set Image if image == null
        if ($scope.shop.logo == null) {
            document.getElementById("logo").src = "http://bootdey.com/img/Content/avatar/avatar1.png";
        } else {
            document.getElementById("logo").src = "/SellerChannel/images/" + $scope.shop.logo;
        }
    }
    $scope.changeProfileShop = function () {
        $scope.saveImage($scope.logo, $scope.shop.shopid)
    }
    $scope.changeImage = function (e) {
        var reader = new FileReader();
        reader.onload = function (e) {

            document.getElementById("logo").src = e.target.result;
            $scope.$apply();
        };
        reader.readAsDataURL(e.target.files[0])
        $scope.logo = e.target.files[0];
    }
    $scope.saveImage = function (file, shopid) {
        let formData;
        formData = new FormData();
        formData.append('fileImage', file);
        formData.append('shopId', shopid)
        formData.append("reportProgress", true);
        const headers = {
            'Content-Type': undefined ,
            transformRequest: angular.identity
        };
        let url = `${host}save/image`;
        $http.post(url, formData,{headers:headers}).then(resp => {
            console.log("formData: ", formData.toString())
        }).catch(error => {
            console.log("Upload fail", error)
        });
    }
    $scope.getProfileShop();
});
