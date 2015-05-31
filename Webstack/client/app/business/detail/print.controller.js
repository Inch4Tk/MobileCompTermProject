
'use strict';

angular.module('atTableApp')
  .controller('BusinessDetailPrintCtrl', function ($scope, $http, $routeParams) {

    var currentId = $routeParams.id;
    $scope.qrData = [];
    $scope.showQR = false;
    
    function formatQRData(business){
      var data = [];
      for (var i=0; i<business.tables.length; ++i)
      {
        if (i%2 === 0) {
          data.push([]);
        }
        data[data.length-1].push({id:business.tables[i]._id, name:business.tables[i].name});
      }
      return data;
    }
    
    $http.get('/api/business/' + currentId).success(function(business) {
      $scope.business = business;
      $scope.qrData = formatQRData(business);
    });
    
  });
