'use strict';

angular.module('climbtrackerApp')
    .controller('GradeController', function ($scope, $state, Grade) {

        $scope.grades = [];
        $scope.loadAll = function() {
            Grade.query(function(result) {
               $scope.grades = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.grade = {
                name: null,
                value: null,
                id: null
            };
        };
    });
