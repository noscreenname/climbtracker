'use strict';

angular.module('climbtrackerApp')
    .controller('CragTypeController', function ($scope, $state, CragType) {

        $scope.cragTypes = [];
        $scope.loadAll = function() {
            CragType.query(function(result) {
               $scope.cragTypes = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.cragType = {
                indoor: null,
                outdoor: null,
                id: null
            };
        };
    });
