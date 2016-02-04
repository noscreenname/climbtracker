'use strict';

angular.module('climbtrackerApp')
	.controller('CragTypeDeleteController', function($scope, $uibModalInstance, entity, CragType) {

        $scope.cragType = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            CragType.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
