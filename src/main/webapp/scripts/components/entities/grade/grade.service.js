'use strict';

angular.module('climbtrackerApp')
    .factory('Grade', function ($resource, DateUtils) {
        return $resource('api/grades/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
