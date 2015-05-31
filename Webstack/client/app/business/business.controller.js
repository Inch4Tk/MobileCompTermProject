'use strict';

angular.module('atTableApp')
  .controller('BusinessCtrl', function ($scope, $http, Auth) {

    $scope.newBusinessName = 'New Business';
    $scope.businesses = [];
    // Make http request to update businesses

    function fetchBusinesses() {
      $http.get('/api/business').success(function(businesses) {
        $scope.businesses = businesses;
      });
    }
    fetchBusinesses();

    $scope.createBusiness = function() {
      $http.post('/api/business', {name: $scope.newBusinessName, locations: [], users: [Auth.getCurrentUser()._id]}).success(fetchBusinesses);
    };

  });
