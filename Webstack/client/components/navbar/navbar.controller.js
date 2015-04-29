'use strict';

angular.module('atTableApp')
  .controller('NavbarCtrl', function ($scope, $location, Auth) {
    $scope.menu = [{
      'title': 'Home',
      'link': '/',
      'showLogout': true,
      'showLogin': false,
      'needAdmin': false,
    },
    {
      'title': 'Dashboard',
      'link': '/dashboard',
      'showLogout': false,
      'showLogin': true,
      'needAdmin': false,
    },
    {
      'title': 'Admin',
      'link': '/admin',
      'showLogout': false,
      'showLogin': true,
      'needAdmin': true,
    }];

    $scope.isCollapsed = true;
    $scope.isLoggedIn = Auth.isLoggedIn;
    $scope.isAdmin = Auth.isAdmin;
    $scope.getCurrentUser = Auth.getCurrentUser;

    $scope.logout = function() {
      Auth.logout();
      $location.path('/login');
    };

    $scope.isActive = function(route) {
      return route === $location.path();
    };

    $scope.isShown = function(item) {
      if(item.needAdmin && !$scope.isAdmin())
        return false;
      if($scope.isLoggedIn())
        return item.showLogin;
      else
        return item.showLogout;
    };
  });
