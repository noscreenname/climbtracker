'use strict';

angular.module('climbtrackerApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('attempt', {
                parent: 'entity',
                url: '/attempts',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Attempts'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/attempt/attempts.html',
                        controller: 'AttemptController'
                    }
                },
                resolve: {
                }
            })
            .state('attempt.detail', {
                parent: 'entity',
                url: '/attempt/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Attempt'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/attempt/attempt-detail.html',
                        controller: 'AttemptDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Attempt', function($stateParams, Attempt) {
                        return Attempt.get({id : $stateParams.id});
                    }]
                }
            })
            .state('attempt.new', {
                parent: 'attempt',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/attempt/attempt-dialog.html',
                        controller: 'AttemptDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    date: null,
                                    description: null,
                                    nbFail: null,
                                    nbSuccess: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('attempt', null, { reload: true });
                    }, function() {
                        $state.go('attempt');
                    })
                }]
            })
            .state('attempt.edit', {
                parent: 'attempt',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/attempt/attempt-dialog.html',
                        controller: 'AttemptDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Attempt', function(Attempt) {
                                return Attempt.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('attempt', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('attempt.delete', {
                parent: 'attempt',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/attempt/attempt-delete-dialog.html',
                        controller: 'AttemptDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Attempt', function(Attempt) {
                                return Attempt.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('attempt', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
