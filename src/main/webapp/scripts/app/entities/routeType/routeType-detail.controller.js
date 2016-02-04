'use strict';

angular.module('climbtrackerApp')
    .controller('RouteTypeDetailController', function ($scope, $rootScope, $stateParams, entity, RouteType) {
        $scope.routeType = entity;
        $scope.load = function (id) {
            RouteType.get({id: id}, function(result) {
                $scope.routeType = result;
            });
        };
        var unsubscribe = $rootScope.$on('climbtrackerApp:routeTypeUpdate', function(event, result) {
            $scope.routeType = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
