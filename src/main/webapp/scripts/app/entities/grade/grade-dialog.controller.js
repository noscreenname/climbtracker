'use strict';

angular.module('climbtrackerApp').controller('GradeDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Grade', 'GradeSystem',
        function($scope, $stateParams, $uibModalInstance, entity, Grade, GradeSystem) {

        $scope.grade = entity;
        $scope.gradesystems = GradeSystem.query();
        $scope.load = function(id) {
            Grade.get({id : id}, function(result) {
                $scope.grade = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('climbtrackerApp:gradeUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.grade.id != null) {
                Grade.update($scope.grade, onSaveSuccess, onSaveError);
            } else {
                Grade.save($scope.grade, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
