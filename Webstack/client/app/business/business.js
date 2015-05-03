'use strict';

angular.module('atTableApp')
  .config(function ($routeProvider) {
    $routeProvider
      .when('/business', {
        templateUrl: 'app/business/business.html',
        controller: 'BusinessCtrl'
      })
      .when('/createbusiness', {
        templateUrl: 'app/business/create/create.html',
        controller: 'BusinessCreateCtrl'
      });
  });
