'use strict';

angular.module('climbtrackerApp')
	.controller('AttemptDeleteController', function($scope, $uibModalInstance, entity, Attempt) {

        $scope.attempt = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Attempt.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
