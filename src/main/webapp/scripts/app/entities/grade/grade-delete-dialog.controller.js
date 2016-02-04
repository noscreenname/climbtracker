'use strict';

angular.module('climbtrackerApp')
	.controller('GradeDeleteController', function($scope, $uibModalInstance, entity, Grade) {

        $scope.grade = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Grade.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
