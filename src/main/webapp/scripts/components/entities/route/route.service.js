'use strict';

angular.module('climbtrackerApp')
    .factory('Route', function ($resource, DateUtils) {
        return $resource('api/routes/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.openDate = DateUtils.convertLocaleDateFromServer(data.openDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.openDate = DateUtils.convertLocaleDateToServer(data.openDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.openDate = DateUtils.convertLocaleDateToServer(data.openDate);
                    return angular.toJson(data);
                }
            }
        });
    });
