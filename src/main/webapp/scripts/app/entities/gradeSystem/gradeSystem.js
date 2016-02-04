'use strict';

angular.module('climbtrackerApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('gradeSystem', {
                parent: 'entity',
                url: '/gradeSystems',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'GradeSystems'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/gradeSystem/gradeSystems.html',
                        controller: 'GradeSystemController'
                    }
                },
                resolve: {
                }
            })
            .state('gradeSystem.detail', {
                parent: 'entity',
                url: '/gradeSystem/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'GradeSystem'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/gradeSystem/gradeSystem-detail.html',
                        controller: 'GradeSystemDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'GradeSystem', function($stateParams, GradeSystem) {
                        return GradeSystem.get({id : $stateParams.id});
                    }]
                }
            })
            .state('gradeSystem.new', {
                parent: 'gradeSystem',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/gradeSystem/gradeSystem-dialog.html',
                        controller: 'GradeSystemDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('gradeSystem', null, { reload: true });
                    }, function() {
                        $state.go('gradeSystem');
                    })
                }]
            })
            .state('gradeSystem.edit', {
                parent: 'gradeSystem',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/gradeSystem/gradeSystem-dialog.html',
                        controller: 'GradeSystemDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['GradeSystem', function(GradeSystem) {
                                return GradeSystem.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('gradeSystem', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('gradeSystem.delete', {
                parent: 'gradeSystem',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/gradeSystem/gradeSystem-delete-dialog.html',
                        controller: 'GradeSystemDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['GradeSystem', function(GradeSystem) {
                                return GradeSystem.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('gradeSystem', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
