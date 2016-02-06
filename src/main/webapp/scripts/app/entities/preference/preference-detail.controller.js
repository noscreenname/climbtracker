'use strict';

angular.module('climbtrackerApp')
    .controller('PreferenceDetailController', function ($scope, $rootScope, $stateParams, entity, Preference, User, GradeSystem, Crag, Route) {
        $scope.preference = entity;
        $scope.load = function (id) {
            Preference.get({id: id}, function(result) {
                $scope.preference = result;
            });
        };
        var unsubscribe = $rootScope.$on('climbtrackerApp:preferenceUpdate', function(event, result) {
            $scope.preference = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
