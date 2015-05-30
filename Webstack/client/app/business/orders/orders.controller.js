'use strict';

angular.module('atTableApp')
  .controller('BusinessOrdersCtrl', function ($scope, $http, $routeParams, socket) {

    var currentId = $routeParams.id;
    $scope.business = {};
    $scope.orders = [];
       
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
    
    // Gets the bill amount for a table
    $scope.getBill = function(table) {
      var amt = 0;
      for (var i=0; i < $scope.orders.length; ++i){
        var order = $scope.orders[i];
        if (order.table == table._id && order.status < 3) {
          for (var j=0; j < order.items.length; ++j)
            amt += order.items[j].amount * order.items[j].price;
        }
      }
      return amt;
    };
    
    // Filter: Check the status of an order
    $scope.checkStatus = function(){
      return function(order) {return order.status < 3; };
    };
    
    // Increment an order status
    $scope.incStatus = function(order){
      if (order.status < 2){
        var newStatus = order.status + 1;
        $http.post('/api/order/' + order._id + '/status', {status: newStatus});
      }     
    };
    
    // Pays all orders of a table (Also sets their state to delivered)
    $scope.payOrders = function(table){
      for (var i=0; i < $scope.orders.length; ++i){
        var order = $scope.orders[i];
        if (order.table == table._id)
        {
          var newStatus = 3;
          $http.post('/api/order/' + order._id + '/status', {status: newStatus});
        }
      }
    };
});
