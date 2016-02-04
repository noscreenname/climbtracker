'use strict';

angular.module('climbtrackerApp').controller('RouteTypeDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'RouteType',
        function($scope, $stateParams, $uibModalInstance, entity, RouteType) {

        $scope.routeType = entity;
        $scope.load = function(id) {
            RouteType.get({id : id}, function(result) {
                $scope.routeType = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('climbtrackerApp:routeTypeUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.routeType.id != null) {
                RouteType.update($scope.routeType, onSaveSuccess, onSaveError);
            } else {
                RouteType.save($scope.routeType, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
