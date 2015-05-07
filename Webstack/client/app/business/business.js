'use strict';

angular.module('atTableApp')
  .config(function ($routeProvider) {
    $routeProvider
      .when('/business', {
        templateUrl: 'app/business/business.html',
        controller: 'BusinessCtrl'
      })
      .when('/business/create', {
        templateUrl: 'app/business/create/create.html',
        controller: 'BusinessCreateCtrl'
      })
      .when('/business/detail/:id/print', {
        templateUrl: 'app/business/detail/print.html',
        controller: 'BusinessDetailPrintCtrl'
      })
      .when('/business/detail/:id', {
        templateUrl: 'app/business/detail/detail.html',
        controller: 'BusinessDetailCtrl'
      });
      
  });
