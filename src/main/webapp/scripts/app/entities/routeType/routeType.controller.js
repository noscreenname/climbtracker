'use strict';

angular.module('climbtrackerApp')
    .controller('RouteTypeController', function ($scope, $state, RouteType) {

        $scope.routeTypes = [];
        $scope.loadAll = function() {
            RouteType.query(function(result) {
               $scope.routeTypes = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.routeType = {
                type: null,
                id: null
            };
        };
    });
