'use strict';

angular.module('climbtrackerApp')
    .controller('GradeSystemController', function ($scope, $state, GradeSystem) {

        $scope.gradeSystems = [];
        $scope.loadAll = function() {
            GradeSystem.query(function(result) {
               $scope.gradeSystems = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.gradeSystem = {
                name: null,
                id: null
            };
        };
    });
