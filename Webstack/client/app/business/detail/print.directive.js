
(function (angular) {

  'use strict';

  function printDirective() {

    function link(scope, element, attrs) {

      element.on('click', function () {

        var elemToPrint = document.getElementById(attrs.printElementId);

        if (elemToPrint) {
          printElement();
        }
      });

    }

    function printElement() {
      // Open a popup where the print content is placed
      var printpath = window.location.href + '/print';
      var popupWin = null;
      // Handle Chrome
      if (navigator.userAgent.toLowerCase().indexOf('chrome') > -1) {
        popupWin = window.open(printpath, '_blank', 'width=1000,height=600,scrollbars=no,menubar=no,toolbar=no,location=no,status=no,titlebar=no');
        popupWin.window.focus();
        popupWin.addEventListener('load', function(){
          popupWin.print();
          popupWin.onfocus = function(){
            popupWin.close();
          };
        }, true);
        popupWin.onbeforeunload = function () {
          popupWin.close();
          return '.\n';
        };
        popupWin.onabort = function () {
          popupWin.close();
        };
      } 
      else { // Handle other browsers
        popupWin = window.open(printpath, '_blank', 'width=1000,height=600');
        popupWin.addEventListener('load', function(){
          popupWin.print();
          popupWin.onfocus = function(){
            popupWin.close();
          };
        }, true);
      }
    }

    return {
      link: link,
      restrict: 'A'
    };

  }

  angular.module('atTableApp').directive('ngPrint', [printDirective]);

} (window.angular));

