'use strict';

angular.module('climbtrackerApp').controller('AttemptDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Attempt', 'Route',
        function($scope, $stateParams, $uibModalInstance, entity, Attempt, Route) {

        $scope.attempt = entity;
        $scope.routes = Route.query();
        $scope.load = function(id) {
            Attempt.get({id : id}, function(result) {
                $scope.attempt = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('climbtrackerApp:attemptUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.attempt.id != null) {
                Attempt.update($scope.attempt, onSaveSuccess, onSaveError);
            } else {
                Attempt.save($scope.attempt, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.datePickerForDate = {};

        $scope.datePickerForDate.status = {
            opened: false
        };

        $scope.datePickerForDateOpen = function($event) {
            $scope.datePickerForDate.status.opened = true;
        };
}]);
