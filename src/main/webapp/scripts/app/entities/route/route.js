'use strict';

angular.module('climbtrackerApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('route', {
                parent: 'entity',
                url: '/routes',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Routes'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/route/routes.html',
                        controller: 'RouteController'
                    }
                },
                resolve: {
                }
            })
            .state('route.detail', {
                parent: 'entity',
                url: '/route/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Route'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/route/route-detail.html',
                        controller: 'RouteDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Route', function($stateParams, Route) {
                        return Route.get({id : $stateParams.id});
                    }]
                }
            })
            .state('route.new', {
                parent: 'route',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/route/route-dialog.html',
                        controller: 'RouteDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    openDate: null,
                                    isOpen: null,
                                    description: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('route', null, { reload: true });
                    }, function() {
                        $state.go('route');
                    })
                }]
            })
            .state('route.edit', {
                parent: 'route',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/route/route-dialog.html',
                        controller: 'RouteDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Route', function(Route) {
                                return Route.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('route', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('route.delete', {
                parent: 'route',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/route/route-delete-dialog.html',
                        controller: 'RouteDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Route', function(Route) {
                                return Route.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('route', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
