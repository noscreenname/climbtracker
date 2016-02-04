'use strict';

angular.module('climbtrackerApp').controller('GradeSystemDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'GradeSystem',
        function($scope, $stateParams, $uibModalInstance, entity, GradeSystem) {

        $scope.gradeSystem = entity;
        $scope.load = function(id) {
            GradeSystem.get({id : id}, function(result) {
                $scope.gradeSystem = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('climbtrackerApp:gradeSystemUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.gradeSystem.id != null) {
                GradeSystem.update($scope.gradeSystem, onSaveSuccess, onSaveError);
            } else {
                GradeSystem.save($scope.gradeSystem, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
