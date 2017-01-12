(function() {
    'use strict';

    angular
        .module('testjhipsterApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('solution', {
            parent: 'entity',
            url: '/solution?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Solutions'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/solution/solutions.html',
                    controller: 'SolutionController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }]
            }
        })
        .state('solution-detail', {
            parent: 'entity',
            url: '/solution/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Solution'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/solution/solution-detail.html',
                    controller: 'SolutionDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Solution', function($stateParams, Solution) {
                    return Solution.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'solution',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('solution-detail.edit', {
            parent: 'solution-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/solution/solution-dialog.html',
                    controller: 'SolutionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Solution', function(Solution) {
                            return Solution.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('solution.new', {
            parent: 'solution',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/solution/solution-dialog.html',
                    controller: 'SolutionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                titre: null,
                                description: null,
                                cout: null,
                                publicVise: null,
                                objectif: null,
                                delai: null,
                                budgetMin: null,
                                budgetMax: null,
                                nbrPersonnesVisees: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('solution', null, { reload: 'solution' });
                }, function() {
                    $state.go('solution');
                });
            }]
        })
        .state('solution.edit', {
            parent: 'solution',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/solution/solution-dialog.html',
                    controller: 'SolutionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Solution', function(Solution) {
                            return Solution.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('solution', null, { reload: 'solution' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('solution.delete', {
            parent: 'solution',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/solution/solution-delete-dialog.html',
                    controller: 'SolutionDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Solution', function(Solution) {
                            return Solution.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('solution', null, { reload: 'solution' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
