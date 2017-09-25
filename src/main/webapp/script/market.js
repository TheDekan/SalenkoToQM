var app = angular.module('checkout', ['ngResource', 'ngGrid', 'ui.bootstrap']);

app.controller('dealsListController', function ($scope, $rootScope, dealService, checkService) {
    
	$scope.checkList = checkService.get();
    $scope.sortInfo = {fields: ['id'], directions: ['asc']};
    $scope.checkout = {currentPage: 1};

    $scope.gridOptions = {
        data: 'checkout.list',
        useExternalSorting: true,

        columnDefs: [
            { field: 'product.name', displayName: 'name' },
            { field: 'productCount', displayName: 'count' },   
            { field: '', width: 30, cellTemplate: '<span class="glyphicon glyphicon-remove remove" ng-click="deleteRow(row)"></span>' }          
        ],

        multiSelect: false,
        selectedItems: [],
        
        afterSelectionChange: function (rowItem) {
            if (rowItem.selected) {
                $rootScope.$broadcast('dealSelected', $scope.gridOptions.selectedItems[0].id);
            }
        }
    };

    
	
    
        $scope.refreshGrid = function () {
        var listDealsArgs = {
            page: $scope.checkout.currentPage,
            sortFields: $scope.sortInfo.fields[0],
            sortDirections: $scope.sortInfo.directions[0]
        };
        $scope.checkList=checkService.get();
        dealService.get(listDealsArgs, function (data) {
            $scope.checkout = data;
        })
    };

        $scope.deleteRow = function (row) {
        $rootScope.$broadcast('deleteDeal', row.entity.id);
    };

        $scope.$watch('sortInfo', function () {
        $scope.checkout = {currentPage: 1};
        $scope.refreshGrid();
    }, true);

        $scope.$on('ngGridEventSorted', function (event, sortInfo) {
        $scope.sortInfo = sortInfo;
    });

        $scope.$on('refreshGrid', function () {
        $scope.refreshGrid();
    });

        $scope.$on('clear', function () {
        $scope.gridOptions.selectAll(false);
    });
});

app.controller('dealsFormController', function ($scope, $rootScope, dealService, productService) {
    
	$scope.productList = [];
	
	productService.get(function (data) {
   	 	$scope.productList = data.productList;
    })
	
    $scope.o = "each";
	
	$scope.countPattern = /^[0-9]{1,2}?$/;
	
    	$scope.clearForm = function () {
        $scope.deal = null;
        $scope.dealForm.$setPristine();
        $rootScope.$broadcast('clear');
    };

        $scope.updateDeal = function () {
        dealService.save($scope.deal).$promise.then(
            function () {
                $rootScope.$broadcast('refreshGrid');
                $rootScope.$broadcast('dealSaved');
                $scope.clearForm();
            },
            function () {
                $rootScope.$broadcast('error');
            });
    };

    	$scope.$on('dealSelected', function (event, id) {
        $scope.deal = dealService.get({id: id});
    });

        $scope.$on('deleteDeal', function (event, id) {
        dealService.delete({id: id}).$promise.then(
            function () {
                $rootScope.$broadcast('refreshGrid');
                $rootScope.$broadcast('dealDeleted');
                $scope.clearForm();
            },
            function () {
                $rootScope.$broadcast('error');
            });
    });
});

app.controller('alertMessagesController', function ($scope) {
    	$scope.$on('dealSaved', function () {
        $scope.alerts = [
            { type: 'success', msg: 'Deal added successfully!' }
        ];
    });

        $scope.$on('dealDeleted', function () {
        $scope.alerts = [
            { type: 'success', msg: 'Deal removed successfully!' }
        ];
    });

        $scope.$on('error', function () {
        $scope.alerts = [
            { type: 'danger', msg: 'There was a problem in the server!' }
        ];
    });

    $scope.closeAlert = function (index) {
        $scope.alerts.splice(index, 1);
    };
});

app.factory('checkService', function ($resource) {
    return $resource('resources/ch');
});

app.factory('dealService', function ($resource) {
    return $resource('resources/checkout/:id');
});

app.factory('productService', function ($resource) {
    return $resource('resources/price/:id');
});
