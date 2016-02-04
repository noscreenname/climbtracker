 'use strict';

angular.module('climbtrackerApp')
    .factory('notificationInterceptor', function ($q, AlertService) {
        return {
            response: function(response) {
                var alertKey = response.headers('X-climbtrackerApp-alert');
                if (angular.isString(alertKey)) {
                    AlertService.success(alertKey, { param : response.headers('X-climbtrackerApp-params')});
                }
                return response;
            }
        };
    });
