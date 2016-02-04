'use strict';

angular.module('climbtrackerApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('routeType', {
                parent: 'entity',
                url: '/routeTypes',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'RouteTypes'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/routeType/routeTypes.html',
                        controller: 'RouteTypeController'
                    }
                },
                resolve: {
                }
            })
            .state('routeType.detail', {
                parent: 'entity',
                url: '/routeType/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'RouteType'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/routeType/routeType-detail.html',
                        controller: 'RouteTypeDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'RouteType', function($stateParams, RouteType) {
                        return RouteType.get({id : $stateParams.id});
                    }]
                }
            })
            .state('routeType.new', {
                parent: 'routeType',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/routeType/routeType-dialog.html',
                        controller: 'RouteTypeDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    type: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('routeType', null, { reload: true });
                    }, function() {
                        $state.go('routeType');
                    })
                }]
            })
            .state('routeType.edit', {
                parent: 'routeType',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/routeType/routeType-dialog.html',
                        controller: 'RouteTypeDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['RouteType', function(RouteType) {
                                return RouteType.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('routeType', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('routeType.delete', {
                parent: 'routeType',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/routeType/routeType-delete-dialog.html',
                        controller: 'RouteTypeDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['RouteType', function(RouteType) {
                                return RouteType.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('routeType', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
