'use strict';

angular.module('climbtrackerApp')
	.controller('CragDeleteController', function($scope, $uibModalInstance, entity, Crag) {

        $scope.crag = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Crag.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
