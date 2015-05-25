'use strict';

// Expects an array of strings as argument, will rewrite the numbers starting from 0 to the rewrite strings
angular.module('atTableFilters', []).filter('rewrite', function() {
  return function(input, rewrites) {
    for (var i=0; i < rewrites.length; ++i){
      if (input == i)
        return rewrites[i];
    }
    return input;
  };
});