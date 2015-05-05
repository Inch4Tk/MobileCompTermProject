'use strict';

angular.module('atTableApp')
  .controller('DashboardCtrl', function ($scope, $http, Auth, User) {
    $scope.businessFilter = '';
    $scope.businesses = [];
    
    // TODO: Should put this to factory
    $http.get('/api/business/mybusiness').success(function(businesses) {
      $scope.businesses = businesses;
    });
    
  });
