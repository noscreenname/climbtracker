'use strict';

angular.module('climbtrackerApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


