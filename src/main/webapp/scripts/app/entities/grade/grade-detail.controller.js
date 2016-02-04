'use strict';

angular.module('climbtrackerApp')
    .controller('GradeDetailController', function ($scope, $rootScope, $stateParams, entity, Grade, GradeSystem) {
        $scope.grade = entity;
        $scope.load = function (id) {
            Grade.get({id: id}, function(result) {
                $scope.grade = result;
            });
        };
        var unsubscribe = $rootScope.$on('climbtrackerApp:gradeUpdate', function(event, result) {
            $scope.grade = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
