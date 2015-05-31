'use strict';

angular.module('atTableApp')
  .controller('BusinessOrdersPlaceCtrl', function ($scope, $http) {

    $scope.tableId = '';
    $scope.menu = [];
    $scope.showPlace = false;
    
    // Button function for fetching menu
    $scope.fetchMenu = function(){
      $http.get('/api/business/menu/' + $scope.tableId).success(function (menu) {
        $scope.menu = menu;
        if (menu)
        {
          for (var i=0; i < $scope.menu.length; ++i) {
            $scope.menu[i].amount = 0;
          }
          $scope.showPlace = true;
        }
      });
    };
    
    // Button function for submitting order, resets all values
    $scope.submitOrder = function(){
      var order = {items:[]};
      // Build order json
      var totalAmount = 0;
      for (var i=0; i < $scope.menu.length; ++i){
        if ($scope.menu[i].amount <= 0) {
          continue;
        }
        order.items.push({});
        order.items[order.items.length-1].name = $scope.menu[i].name;
        order.items[order.items.length-1].amount = $scope.menu[i].amount;
        totalAmount += $scope.menu[i].amount;
      }
      // Submit
      if (totalAmount > 0)
      {
        $http.post('/api/order/' + $scope.tableId, order).success(function () {
          $scope.tableId = '';
          $scope.menu = [];
          $scope.showPlace = false;
        });
      }
    };
    
});
