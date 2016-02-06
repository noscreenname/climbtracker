'use strict';

angular.module('climbtrackerApp').controller('PreferenceDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Preference', 'User', 'GradeSystem', 'Crag', 'Route',
        function($scope, $stateParams, $uibModalInstance, $q, entity, Preference, User, GradeSystem, Crag, Route) {

        $scope.preference = entity;
        $scope.users = User.query();
        $scope.gradesystems = GradeSystem.query();
        $scope.crags = Crag.query();
        $scope.routes = Route.query();
        $scope.load = function(id) {
            Preference.get({id : id}, function(result) {
                $scope.preference = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('climbtrackerApp:preferenceUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.preference.id != null) {
                Preference.update($scope.preference, onSaveSuccess, onSaveError);
            } else {
                Preference.save($scope.preference, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
