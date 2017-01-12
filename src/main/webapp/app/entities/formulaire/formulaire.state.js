(function() {
    'use strict';

    angular
        .module('testjhipsterApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('formulaire', {
            parent: 'entity',
            url: '/formulaire',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Formulaires'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/formulaire/formulaires.html',
                    controller: 'FormulaireController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('formulaire-detail', {
            parent: 'entity',
            url: '/formulaire/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Formulaire'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/formulaire/formulaire-detail.html',
                    controller: 'FormulaireDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Formulaire', function($stateParams, Formulaire) {
                    return Formulaire.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'formulaire',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('formulaire-detail.edit', {
            parent: 'formulaire-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/formulaire/formulaire-dialog.html',
                    controller: 'FormulaireDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Formulaire', function(Formulaire) {
                            return Formulaire.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('formulaire.new', {
            parent: 'formulaire',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/formulaire/formulaire-dialog.html',
                    controller: 'FormulaireDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('formulaire', null, { reload: 'formulaire' });
                }, function() {
                    $state.go('formulaire');
                });
            }]
        })
        .state('formulaire.edit', {
            parent: 'formulaire',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/formulaire/formulaire-dialog.html',
                    controller: 'FormulaireDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Formulaire', function(Formulaire) {
                            return Formulaire.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('formulaire', null, { reload: 'formulaire' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('formulaire.delete', {
            parent: 'formulaire',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/formulaire/formulaire-delete-dialog.html',
                    controller: 'FormulaireDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Formulaire', function(Formulaire) {
                            return Formulaire.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('formulaire', null, { reload: 'formulaire' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
