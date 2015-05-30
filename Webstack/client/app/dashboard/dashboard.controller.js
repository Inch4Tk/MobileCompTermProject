'use strict';

angular.module('atTableApp')
  .controller('DashboardCtrl', function ($scope, $http, Auth, User) {
    $scope.businessFilter = '';
    $scope.businesses = [];
    
    $http.get('/api/business/mybusiness').success(function(businesses) {
      $scope.businesses = businesses;
    });
    
  });
