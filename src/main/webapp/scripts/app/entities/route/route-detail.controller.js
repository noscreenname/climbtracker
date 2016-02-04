'use strict';

angular.module('climbtrackerApp')
    .controller('RouteDetailController', function ($scope, $rootScope, $stateParams, entity, Route, RouteType, Grade, Crag) {
        $scope.route = entity;
        $scope.load = function (id) {
            Route.get({id: id}, function(result) {
                $scope.route = result;
            });
        };
        var unsubscribe = $rootScope.$on('climbtrackerApp:routeUpdate', function(event, result) {
            $scope.route = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
