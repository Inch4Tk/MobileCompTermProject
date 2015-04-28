'use strict';

angular.module('atTableApp')
  .controller('BusinessCtrl', function ($scope, $http, Auth, User) {

    $scope.businesses = [];
    // Make http request to update businesses

    function fetchBusinesses() {
      $http.get('/api/business').success(function(businesses) {
        $scope.businesses = businesses;
      });
    }

    $scope.createBusiness() = function() {
      $http.post('/api/business', {name: "", locations: [], users: [Auth.currentUser._id]});
    };
  });
