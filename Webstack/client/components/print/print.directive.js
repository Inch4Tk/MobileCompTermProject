
// Code from: Gil Fink http://blogs.microsoft.co.il/gilf/2014/08/09/building-a-simple-angularjs-print-directive/
(function (angular) {

  'use strict';

  function printDirective() {

    function link(scope, element, attrs) {

      element.on('click', function () {

        var elemToPrint = document.getElementById(attrs.printElementId);

        if (elemToPrint) {

          printElement(elemToPrint);

        }

      });

    }

    function printElement(elem) {

      // Open a popup where the print content is placed
      // Handle Chrome
      if (navigator.userAgent.toLowerCase().indexOf('chrome') > -1) {
        var popupWin = window.open(window.location.origin + '/dashboard', '_blank', 'width=600,height=600,scrollbars=no,menubar=no,toolbar=no,location=no,status=no,titlebar=no');
        popupWin.window.focus();
        popupWin.document.write('<!DOCTYPE html><html><head>' + // Potentially insert style here
          '</head><body onload="window.print()"><div class="reward-body">' + elem.innerHTML + '</div></html>');
        popupWin.onbeforeunload = function (event) {
          popupWin.close();
          return '.\n';
        };
        popupWin.onabort = function (event) {
          popupWin.document.close();
          popupWin.close();
        };
      } 
      else { // Handle other browsers
        var popupWin = window.open(window.location.origin + '/print', '_blank', 'width=800,height=600');
        popupWin.addEventListener('load', function(){
          popupWin.document.getElementById('print').innerHTML = elem.innerHTML;
        }, true);
//        popupWin.document.open();
//        popupWin.document.write('<html><head>' + // Potentially insert style here <link rel="stylesheet" type="text/css" href="style.css" />
//          '</head><body onload="window.print()">' + elem.innerHTML + '</html>');
//        popupWin.document.close();
      }
    }

    return {

      link: link,

      restrict: 'A'

    };

  }

  angular.module('atTableApp').directive('ngPrint', [printDirective]);

} (window.angular));

