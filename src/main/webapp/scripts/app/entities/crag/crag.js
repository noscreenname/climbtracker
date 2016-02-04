'use strict';

angular.module('climbtrackerApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('crag', {
                parent: 'entity',
                url: '/crags',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Crags'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/crag/crags.html',
                        controller: 'CragController'
                    }
                },
                resolve: {
                }
            })
            .state('crag.detail', {
                parent: 'entity',
                url: '/crag/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Crag'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/crag/crag-detail.html',
                        controller: 'CragDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Crag', function($stateParams, Crag) {
                        return Crag.get({id : $stateParams.id});
                    }]
                }
            })
            .state('crag.new', {
                parent: 'crag',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/crag/crag-dialog.html',
                        controller: 'CragDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    location: null,
                                    address: null,
                                    description: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('crag', null, { reload: true });
                    }, function() {
                        $state.go('crag');
                    })
                }]
            })
            .state('crag.edit', {
                parent: 'crag',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/crag/crag-dialog.html',
                        controller: 'CragDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Crag', function(Crag) {
                                return Crag.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('crag', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('crag.delete', {
                parent: 'crag',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/crag/crag-delete-dialog.html',
                        controller: 'CragDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Crag', function(Crag) {
                                return Crag.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('crag', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
