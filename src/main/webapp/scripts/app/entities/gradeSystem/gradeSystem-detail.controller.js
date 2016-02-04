'use strict';

angular.module('climbtrackerApp')
    .controller('GradeSystemDetailController', function ($scope, $rootScope, $stateParams, entity, GradeSystem) {
        $scope.gradeSystem = entity;
        $scope.load = function (id) {
            GradeSystem.get({id: id}, function(result) {
                $scope.gradeSystem = result;
            });
        };
        var unsubscribe = $rootScope.$on('climbtrackerApp:gradeSystemUpdate', function(event, result) {
            $scope.gradeSystem = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
