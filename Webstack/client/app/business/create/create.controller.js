
'use strict';

angular.module('atTableApp')
  .controller('BusinessCreateCtrl', function ($scope, $http, $location, Upload, Auth) {

    $scope.newBusinessName = '';
    $scope.tables = [];
    $scope.nTableCount = 1;
    
    // Menu creation variables
    $scope.menu = []; // Added menu items
    $scope.menuIName = '';
    $scope.menuIDesc = '';
    $scope.menuIPic = '';
    $scope.menuIPrice = '';
    
    // Functions
    $scope.reset = function() {
      $scope.newBusinessName = '';
      $scope.tables = [];
      $scope.menu = [];
      $scope.nTableCount = 1;
    };
    
    // Upload percentages
    $scope.uploading = false;
    $scope.totalsizes = [];
    $scope.cursizes = [];
    $scope.pictureIds = [];
    $scope.toUpload = 0;
    $scope.finishedFiles = 0;
    
    // Add x new tables
    $scope.addTables = function() {
      if ($scope.nTableCount < 1)
        return;
      for (var i=0; i < $scope.nTableCount; ++i)
      {
        var tname = 'Table ' + $scope.tables.length.toString();
        $scope.tables.push({name:tname});
      }
    };
    
    // Remove a table
    $scope.trashTable = function(table) {
      var idx = $scope.tables.indexOf(table);
      $scope.tables.splice(idx, 1);
    };
    
    // Add a menu item to the $scope.menu collection
    $scope.addMenuI = function() {
      var p = parseInt($scope.menuIPrice);
      if (!p || $scope.menuIName === '')
        return;
      $scope.menu.push({
        name: $scope.menuIName,
        price: p,
        picture: $scope.menuIPic,
        description: $scope.menuIDesc
      });
      $scope.menuIName = '';
      $scope.menuIDesc = '';
      $scope.menuIPic = '';
      $scope.menuIPrice = '';
    };
    
    // Check if there is a picture to show in upload
    $scope.isMIPicture = function() {
      if ($scope.menuIPic)
        return true;
      return false;
    };
    
    // Remove a menu item from $scope.menu
    $scope.trashMenuI = function(menuI) {
      var idx = $scope.menu.indexOf(menuI);
      $scope.menu.splice(idx, 1);
    };
    
    // Submit the business for creation to server
    // Progress handler factory
    function make_progressHandler(i) {
      return function (evt) {
        $scope.cursizes[i] = evt.loaded;
        $scope.totalsizes[i] = evt.total;
      };
    }
    // Success handler factory
    function make_successHandler(j) {
      return function (data, status, headers, config) {
        ++$scope.finishedFiles;
        $scope.pictureIds[j] = data;
        
        // Check if this is the last file to finish
        if ($scope.finishedFiles == $scope.toUpload) {   
          // Overwrite every menu item with its id
          for (var i = 0; i < $scope.menu.length; i++) {
            if ($scope.pictureIds[i])
              $scope.menu[i].picture = $scope.pictureIds[i];
            else
              $scope.menu[i].picture = null;
          }
          
          // Create the business and upload it  
          var newBusiness = {
            name: $scope.newBusinessName,
            user: Auth.getCurrentUser()._id,
            tables: $scope.tables,
            menu: $scope.menu
          };
          console.log(newBusiness);
          $http.post('/api/business', newBusiness).success(function () {
            $location.path('/dashboard');
          });
        }
      };
    }
    
    $scope.createBusiness = function() {
      if ($scope.newBusinessName === '')
        return;
        
      // First upload all pictures
      // Check for all pictures that have to be uploaded
      for (var i = 0; i < $scope.menu.length; i++) {
        if ($scope.menu[i].picture)
          ++$scope.toUpload;
      }
      // Actually upload something
      for (var i = 0; i < $scope.menu.length; i++) {
        // Check if picture exists
        if (!$scope.menu[i].picture)
          continue;
        // Upload file
        var file = $scope.menu[i].picture[0];
        Upload.upload({
          url: '/api/business/mipic',
          file: file
        })
        .progress(make_progressHandler(i))
        .success(make_successHandler(i));
      }  
    };

  });
