    var app = angular.module('Admin', ['ngRoute']);
    app.config(function ($locationProvider) {
    $locationProvider.hashPrefix('!');
});

app.config(function ($routeProvider) {
    $routeProvider
        .when('/', {
            templateUrl: '/admin/findOrderUser',
            controller: 'indexController'
        })
        .when('/admin/findtop5', {
            templateUrl: '/admin/findtop5',
            controller: ''
        })
        .when('/api/payment/callpayment', {
            templateUrl: '/api/payment/callpayment',
            controller: ''
        })
        .when('/admin/confirm', {
            templateUrl: '/admin/confirm',
            controller: ''
        })
        .when('/admin/delivering', {
            templateUrl: '/admin/delivering',
            controller: ''
        })
        .when('/admin/unpaid', {
            templateUrl: '/admin/unpaid',
            controller: ''
        })
        .when('/admin/paid', {
            templateUrl: '/admin/paid',
            controller: ''
        })
        .when('/admin/processed', {
            templateUrl: '/admin/processed',
            controller: ''
        })
        .when('/admin/refund', {
            templateUrl: '/admin/refund',
            controller: ''
        })
        .when('/admin/cancel', {
            templateUrl: '/admin/cancel',
            controller: ''
        })
        .when('/admin/createvoucher', {
            templateUrl: '/admin/createvoucher',
            controller: 'createVoucherController'
        })
        .when('/shop/TranportChannel/CreateVoucher', {
            templateUrl: '/Ibook/seller/shop/TranportChannel/CreateVoucher',
            controller: 'createVoucherController'
        })
        .when('/shop/TranportChannel/ShopRewiew', {
            templateUrl: '/Ibook/seller/shop/TranportChannel/ShopRewiew',
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
        .when('/shop/setting/address', {
            templateUrl: '/Ibook/seller/shop/setting/address',
            controller: 'addressSettingController'
        })
        .when('/shop/setting/shipping', {
            templateUrl: '/Ibook/seller/shop/setting/shipping',
            controller: 'shippingSettingController'
        })
        .when('/shop/setting/account', {
            templateUrl: '/Ibook/seller/shop/setting/account',
            controller: 'accountSettingController'
        })
        .when('/sales', {
            templateUrl: '/Ibook/seller/shop/sales',
            controller: 'salesController'
        })
        .when('/finance/revenue', {
            templateUrl: '/Ibook/seller/shop/finance/revenue',
            controller: 'revenueFinanceController'
        })
        .when('/finance/accountBalance',{
            templateUrl: '/Ibook/seller/shop/finance/bankAccountBalance',
            controller: 'bankAccountController'
        })
        .when('/finance/createBankAccount',{
            templateUrl: '/Ibook/seller/shop/finance/createBankAccount',
            controller: 'createBankAccountController'
        })
        .otherwise({
            redirectTo: '/seller'
        });
});

app.controller('createVoucherController', function($scope, $http) {
    $scope.pageSize = 5; // Number of items per page
    $scope.currentPage = 1; // Current page
    $scope.totalPages = 1
    $scope.initInfoProduct = function () {
        $scope.books = [];
        $http.get('/rest/books')
            .then(function(response) {
                $scope.books = response.data;
                $scope.totalPages = Math.ceil($scope.books.length / $scope.pageSize);
                $scope.setPage(1); // Set initial page
                console.log('Evaluates:', $scope.books);
            })
            .catch(function(error) {
                console.error('Error fetching data:', error);
            });

    }
    $scope.initInfoProduct();
    $scope.setPage = function (page) {
        console.log('Current Page:', $scope.currentPage);
        console.log('Total Pages:', $scope.totalPages);
        if (page < 1 || page > $scope.totalPages) {
            return;
        }
        $scope.currentPage = page;
        var startIndex = (page - 1) * $scope.pageSize;
        var endIndex = startIndex + $scope.pageSize;
        $scope.paginatedBooks = $scope.books.slice(startIndex, endIndex);
        console.log('setPage called with page:', page);
        // ... (rest of the code)

        console.log('currentPage:', $scope.currentPage);
        console.log('paginatedBooks:', $scope.paginatedBooks);
    };

    $scope.getPages = function () {
        return new Array($scope.totalPages).fill().map((_, index) => index + 1);
    };
    // Disable button
    $scope.selectedOption = '';//Default value
    $scope.isButtonDisabled = true;

    $scope.updateButtonStatus = function () {

        $scope.isButtonDisabled = $scope.selectedOption === 'Books';
    };
    $scope.createSale = function() {
        // Gửi dữ liệu form đến API
        $http.post('http://localhost:8080/rest/sale/create', $scope.sale)
            .then(function(response) {

                alert(response.data.message);
            })
            .catch(function(error) {

                console.error('Error:', error);
                alert('An error occurred while processing the request.');
            });
    };

});