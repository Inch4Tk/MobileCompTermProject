
'use strict';

angular.module('atTableApp')
  .controller('BusinessCreateCtrl', function ($scope, $http, $location, Auth) {

    $scope.newBusinessName = '';
    $scope.tables = [];
    $scope.nTableCount = 1;
    
    $scope.reset = function() {
      $scope.newBusinessName = '';
      $scope.tables = [];
      $scope.nTableCount = 1;
    };
    
    $scope.addTables = function() {
      if ($scope.nTableCount < 1)
        return;
      for (var i=0; i < $scope.nTableCount; ++i)
      {
        var tname = 'Table ' + $scope.tables.length.toString();
        $scope.tables.push({name:tname});
      }
    };
    
    $scope.createBusiness = function() {
      if ($scope.newBusinessName === '')
        return;
      var newBusiness = {
        name: $scope.newBusinessName, 
        user: Auth.getCurrentUser()._id,
        tables: $scope.tables,
        };
      $http.post('/api/business', newBusiness).success( function(){
        $location.path('/dashboard');
      });
    };

  });
