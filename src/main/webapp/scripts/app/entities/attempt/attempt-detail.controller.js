'use strict';

angular.module('climbtrackerApp')
    .controller('AttemptDetailController', function ($scope, $rootScope, $stateParams, entity, Attempt, Route) {
        $scope.attempt = entity;
        $scope.load = function (id) {
            Attempt.get({id: id}, function(result) {
                $scope.attempt = result;
            });
        };
        var unsubscribe = $rootScope.$on('climbtrackerApp:attemptUpdate', function(event, result) {
            $scope.attempt = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
