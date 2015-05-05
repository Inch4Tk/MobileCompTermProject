
'use strict';

angular.module('atTableApp')
  .controller('BusinessDetailCtrl', function ($scope, $http, $location, $routeParams) {

    var currentId = $routeParams.id;
    $scope.business = {};
    $scope.qrData = [];
    $scope.showQR = false;
    
    function formatQRData(business){
      var data = [];
      for (var i=0; i<business.tables.length; ++i)
      {
        if (i%2==0) {
          data.push([]);
        }
        data[data.length-1].push({id:business.tables[i]._id, name:business.tables[i].name});
      }
      return data;
    };
    
    // TODO: Should put this to factory
    $http.get('/api/business/' + currentId).success(function(business) {
      $scope.business = business;
      $scope.qrData = formatQRData(business);
    });
    
    $scope.toggleQR = function(){
      $scope.showQR = !$scope.showQR;
    };
    
  });
