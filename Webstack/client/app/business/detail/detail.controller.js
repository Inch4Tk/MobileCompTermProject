
'use strict';

angular.module('atTableApp')
  .controller('BusinessDetailCtrl', function ($scope, $http, $routeParams) {

    var currentId = $routeParams.id;
    $scope.business = {};
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
    
    // Picture fetch event handler factory
    function makePictureFetchHandler(i) {
      return function (picture) {
        var raw = String.fromCharCode.apply(null, picture.data.data);
        var b64 = btoa(raw);
        var dataURL = 'data:image/jpeg;base64,' + b64;
        $scope.business.menu[i].pictureData = dataURL;
      };
    }
   
    $http.get('/api/business/' + currentId).success(function(business) {
      $scope.business = business;
      $scope.qrData = formatQRData(business);
      // Fetch images
      for(var i=0; i<$scope.business.menu.length; ++i) {
        $scope.business.menu[i].pictureData = null;
        if ($scope.business.menu[i].picture) {
          $http.get('/api/business/menupic/' + $scope.business.menu[i].picture)
            .success(makePictureFetchHandler(i));
        }
      }
     });
     
    $scope.toggleQR = function(){
      $scope.showQR = !$scope.showQR;
    };
    
  });
