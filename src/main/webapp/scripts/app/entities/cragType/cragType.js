'use strict';

angular.module('climbtrackerApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('cragType', {
                parent: 'entity',
                url: '/cragTypes',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'CragTypes'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/cragType/cragTypes.html',
                        controller: 'CragTypeController'
                    }
                },
                resolve: {
                }
            })
            .state('cragType.detail', {
                parent: 'entity',
                url: '/cragType/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'CragType'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/cragType/cragType-detail.html',
                        controller: 'CragTypeDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'CragType', function($stateParams, CragType) {
                        return CragType.get({id : $stateParams.id});
                    }]
                }
            })
            .state('cragType.new', {
                parent: 'cragType',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/cragType/cragType-dialog.html',
                        controller: 'CragTypeDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    indoor: null,
                                    outdoor: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('cragType', null, { reload: true });
                    }, function() {
                        $state.go('cragType');
                    })
                }]
            })
            .state('cragType.edit', {
                parent: 'cragType',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/cragType/cragType-dialog.html',
                        controller: 'CragTypeDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['CragType', function(CragType) {
                                return CragType.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('cragType', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('cragType.delete', {
                parent: 'cragType',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/cragType/cragType-delete-dialog.html',
                        controller: 'CragTypeDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['CragType', function(CragType) {
                                return CragType.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('cragType', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
