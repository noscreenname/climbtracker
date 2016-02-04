'use strict';

angular.module('climbtrackerApp')
    .controller('CragTypeDetailController', function ($scope, $rootScope, $stateParams, entity, CragType) {
        $scope.cragType = entity;
        $scope.load = function (id) {
            CragType.get({id: id}, function(result) {
                $scope.cragType = result;
            });
        };
        var unsubscribe = $rootScope.$on('climbtrackerApp:cragTypeUpdate', function(event, result) {
            $scope.cragType = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
