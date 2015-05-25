'use strict';

angular.module('atTableApp')
  .controller('BusinessOrdersCtrl', function ($scope, $http, $routeParams, socket) {

    var currentId = $routeParams.id;
    $scope.business = {};
    $scope.orders = [];
       
    // TODO: Should put this to factory
    $http.get('/api/business/' + currentId).success(function (business) {
      $scope.business = business;
    });
    
    // Getting orders and sync them afterwards
    $http.get('/api/order/business/' + currentId + '/active').success(function(orders) {
      $scope.orders = orders;
      socket.syncUpdates('order', $scope.orders);
    });

    $scope.$on('$destroy', function () {
      socket.unsyncUpdates('order');
    });
    
    // Increment an order status
    $scope.incStatus = function(order){
      if (order.status < 2)
        order.status += 1;
    };
    
    // Pays all orders of a table
    $scope.payOrders = function(table){
      
    };
});
