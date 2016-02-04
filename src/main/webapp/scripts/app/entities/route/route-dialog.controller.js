'use strict';

angular.module('climbtrackerApp').controller('RouteDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Route', 'RouteType', 'Grade', 'Crag',
        function($scope, $stateParams, $uibModalInstance, entity, Route, RouteType, Grade, Crag) {

        $scope.route = entity;
        $scope.routetypes = RouteType.query();
        $scope.grades = Grade.query();
        $scope.crags = Crag.query();
        $scope.load = function(id) {
            Route.get({id : id}, function(result) {
                $scope.route = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('climbtrackerApp:routeUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.route.id != null) {
                Route.update($scope.route, onSaveSuccess, onSaveError);
            } else {
                Route.save($scope.route, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.datePickerForOpenDate = {};

        $scope.datePickerForOpenDate.status = {
            opened: false
        };

        $scope.datePickerForOpenDateOpen = function($event) {
            $scope.datePickerForOpenDate.status.opened = true;
        };
}]);
