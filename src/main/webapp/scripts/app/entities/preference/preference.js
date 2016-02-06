'use strict';

angular.module('climbtrackerApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('preference', {
                parent: 'entity',
                url: '/preferences',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Preferences'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/preference/preferences.html',
                        controller: 'PreferenceController'
                    }
                },
                resolve: {
                }
            })
            .state('preference.detail', {
                parent: 'entity',
                url: '/preference/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Preference'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/preference/preference-detail.html',
                        controller: 'PreferenceDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Preference', function($stateParams, Preference) {
                        return Preference.get({id : $stateParams.id});
                    }]
                }
            })
            .state('preference.new', {
                parent: 'preference',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/preference/preference-dialog.html',
                        controller: 'PreferenceDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('preference', null, { reload: true });
                    }, function() {
                        $state.go('preference');
                    })
                }]
            })
            .state('preference.edit', {
                parent: 'preference',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/preference/preference-dialog.html',
                        controller: 'PreferenceDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Preference', function(Preference) {
                                return Preference.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('preference', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('preference.delete', {
                parent: 'preference',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/preference/preference-delete-dialog.html',
                        controller: 'PreferenceDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Preference', function(Preference) {
                                return Preference.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('preference', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
