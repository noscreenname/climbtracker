'use strict';

angular.module('climbtrackerApp')
    .controller('RouteController', function ($scope, $state, Route) {

        $scope.routes = [];
        $scope.loadAll = function() {
            Route.query(function(result) {
               $scope.routes = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.route = {
                name: null,
                openDate: null,
                isOpen: null,
                description: null,
                id: null
            };
        };
    });
