'use strict';

angular.module('climbtrackerApp')
    .controller('PreferenceController', function ($scope, $state, Preference) {

        $scope.preferences = [];
        $scope.loadAll = function() {
            Preference.query(function(result) {
               $scope.preferences = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.preference = {
                id: null
            };
        };
    });
