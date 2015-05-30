
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
        if (i%2==0) {
          data.push([]);
        }
        data[data.length-1].push({id:business.tables[i]._id, name:business.tables[i].name});
      }
      return data;
    };
    
    $http.get('/api/business/' + currentId).success(function(business) {
      $scope.business = business;
      $scope.qrData = formatQRData(business);  
      $http.get('/api/business/menupic/' + $scope.business.menu[4].picture)
        .success(function(picture) {
          console.log(picture);
        });
    
    });
    
    
//      // Code to get a picture from a business and display
//      <img id="image" alt="data url loaded image" />
//      $http.get('/api/business/menupic/' + pictureId)
//        .success(function(picture) {
//          
//        var raw = String.fromCharCode.apply(null, picture.data);
//        var b64=btoa(raw);
//        var dataURL="data:image/jpeg;base64,"+b64;
//        console.log(dataURL);
//        document.getElementById("image").src = dataURL;
//      });
    
    $scope.toggleQR = function(){
      $scope.showQR = !$scope.showQR;
    };
    
  });
