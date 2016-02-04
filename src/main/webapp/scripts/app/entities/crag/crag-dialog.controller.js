'use strict';

angular.module('climbtrackerApp').controller('CragDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Crag', 'GradeSystem',
        function($scope, $stateParams, $uibModalInstance, entity, Crag, GradeSystem) {

        $scope.crag = entity;
        $scope.gradesystems = GradeSystem.query();
        $scope.load = function(id) {
            Crag.get({id : id}, function(result) {
                $scope.crag = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('climbtrackerApp:cragUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.crag.id != null) {
                Crag.update($scope.crag, onSaveSuccess, onSaveError);
            } else {
                Crag.save($scope.crag, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
