'use strict';

angular.module('climbtrackerApp')
    .controller('CragController', function ($scope, $state, Crag) {

        $scope.crags = [];
        $scope.loadAll = function() {
            Crag.query(function(result) {
               $scope.crags = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.crag = {
                name: null,
                location: null,
                address: null,
                description: null,
                id: null
            };
        };
    });
