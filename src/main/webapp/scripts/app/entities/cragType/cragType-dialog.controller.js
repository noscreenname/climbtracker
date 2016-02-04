'use strict';

angular.module('climbtrackerApp').controller('CragTypeDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'CragType',
        function($scope, $stateParams, $uibModalInstance, entity, CragType) {

        $scope.cragType = entity;
        $scope.load = function(id) {
            CragType.get({id : id}, function(result) {
                $scope.cragType = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('climbtrackerApp:cragTypeUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.cragType.id != null) {
                CragType.update($scope.cragType, onSaveSuccess, onSaveError);
            } else {
                CragType.save($scope.cragType, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
