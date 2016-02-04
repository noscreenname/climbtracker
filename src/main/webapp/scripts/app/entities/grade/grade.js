'use strict';

angular.module('climbtrackerApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('grade', {
                parent: 'entity',
                url: '/grades',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Grades'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/grade/grades.html',
                        controller: 'GradeController'
                    }
                },
                resolve: {
                }
            })
            .state('grade.detail', {
                parent: 'entity',
                url: '/grade/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Grade'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/grade/grade-detail.html',
                        controller: 'GradeDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Grade', function($stateParams, Grade) {
                        return Grade.get({id : $stateParams.id});
                    }]
                }
            })
            .state('grade.new', {
                parent: 'grade',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/grade/grade-dialog.html',
                        controller: 'GradeDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    value: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('grade', null, { reload: true });
                    }, function() {
                        $state.go('grade');
                    })
                }]
            })
            .state('grade.edit', {
                parent: 'grade',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/grade/grade-dialog.html',
                        controller: 'GradeDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Grade', function(Grade) {
                                return Grade.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('grade', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('grade.delete', {
                parent: 'grade',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/grade/grade-delete-dialog.html',
                        controller: 'GradeDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Grade', function(Grade) {
                                return Grade.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('grade', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
