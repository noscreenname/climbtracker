'use strict';

angular.module('climbtrackerApp')
	.controller('GradeSystemDeleteController', function($scope, $uibModalInstance, entity, GradeSystem) {

        $scope.gradeSystem = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            GradeSystem.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
