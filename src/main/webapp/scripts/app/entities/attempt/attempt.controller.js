'use strict';

angular.module('climbtrackerApp')
    .controller('AttemptController', function ($scope, $state, Attempt) {

        $scope.attempts = [];
        $scope.loadAll = function() {
            Attempt.query(function(result) {
               $scope.attempts = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.attempt = {
                date: null,
                description: null,
                nbFail: null,
                nbSuccess: null,
                id: null
            };
        };
    });
