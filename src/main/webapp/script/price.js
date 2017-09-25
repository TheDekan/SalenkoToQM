var app = angular.module('price', ['ngResource', 'ngGrid', 'ui.bootstrap']);

app.controller('productsListController', function ($scope, $rootScope, productService) {
    
    $scope.sortInfo = {fields: ['id'], directions: ['asc']};
    $scope.price = {currentPage: 1};

    $scope.gridOptions = {
        data: 'price.list',
        useExternalSorting: true,

        columnDefs: [
            { field: 'name', displayName: 'Product Name' },
            { field: 'price', displayName: 'Price' },
            { field: 'calculationType', displayName: 'calcType' },
            { field: 'actionValid', displayName: 'Action valid?' },
            { field: 'actionCount', displayName: 'AC' },
            { field: 'actionPrice', displayName: 'AP' },
            { field: 'gift', displayName: 'Gift?' },
            { field: 'giftName', displayName: 'GN' },
            { field: 'giftCount', displayName: 'GC' },
            { field: '', width: 30, cellTemplate: '<span class="glyphicon glyphicon-remove remove" ng-click="deleteRow(row)"></span>' }          
        ],

        multiSelect: false,
        selectedItems: [],
        
        afterSelectionChange: function (rowItem) {
            if (rowItem.selected) {
                $rootScope.$broadcast('productSelected', $scope.gridOptions.selectedItems[0].id);
            }
        }
    };

    $scope.refreshGrid = function () {
        var listProductsArgs = {
            page: $scope.price.currentPage,
            sortFields: $scope.sortInfo.fields[0],
            sortDirections: $scope.sortInfo.directions[0]
        };

        productService.get(listProductsArgs, function (data) {
            $scope.price = data;
        })
    };

    $scope.deleteRow = function (row) {
        $rootScope.$broadcast('deleteProduct', row.entity.id);
    };

    $scope.$watch('sortInfo', function () {
        $scope.price = {currentPage: 1};
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

app.controller('productFormController', function ($scope, $rootScope, productService) {
	
	  $scope.productList = [];
	  
	  productService.get(function (data) {
		  $scope.productList = data.productList; 
	  })
	 
    	$scope.clearForm = function () {
        $scope.product = null;
        $scope.productForm.$setPristine();
        $rootScope.$broadcast('clear');
    };

        $scope.updateProd = function () {
        productService.save($scope.product).$promise.then(
            function () {
                $rootScope.$broadcast('refreshGrid');
                $rootScope.$broadcast('productSaved');
                $scope.clearForm();
            },
            function () {
                $rootScope.$broadcast('error');
            });
    };

    	$scope.$on('productSelected', function (event, id) {
        $scope.product = productService.get({id: id});
    });

        $scope.$on('deleteProduct', function (event, id) {
        productService.delete({id: id}).$promise.then(
            function () {
                $rootScope.$broadcast('refreshGrid');
                $rootScope.$broadcast('productDeleted');
                $scope.clearForm();
            },
            function () {
                $rootScope.$broadcast('error');
            });
    });
});

app.controller('alertMessagesController', function ($scope) {
    	$scope.$on('productSaved', function () {
        $scope.alerts = [
            { type: 'success', msg: 'Product saved successfully!' }
        ];
    });

        $scope.$on('productDeleted', function () {
        $scope.alerts = [
            { type: 'success', msg: 'Product deleted successfully!' }
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

app.factory('productService', function ($resource) {
    return $resource('resources/price/:id');
});
