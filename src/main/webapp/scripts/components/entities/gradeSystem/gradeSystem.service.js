'use strict';

angular.module('climbtrackerApp')
    .factory('GradeSystem', function ($resource, DateUtils) {
        return $resource('api/gradeSystems/:id', {}, {
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
