'use strict';

angular.module('climbtrackerApp')
	.controller('RouteTypeDeleteController', function($scope, $uibModalInstance, entity, RouteType) {

        $scope.routeType = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            RouteType.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
