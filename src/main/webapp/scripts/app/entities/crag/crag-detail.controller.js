'use strict';

angular.module('climbtrackerApp')
    .controller('CragDetailController', function ($scope, $rootScope, $stateParams, entity, Crag, GradeSystem) {
        $scope.crag = entity;
        $scope.load = function (id) {
            Crag.get({id: id}, function(result) {
                $scope.crag = result;
            });
        };
        var unsubscribe = $rootScope.$on('climbtrackerApp:cragUpdate', function(event, result) {
            $scope.crag = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
