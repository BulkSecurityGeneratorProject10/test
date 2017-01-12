(function() {
    'use strict';

    angular
        .module('testjhipsterApp')
        .controller('FormulaireDetailController', FormulaireDetailController);

    FormulaireDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Formulaire', 'Solution'];

    function FormulaireDetailController($scope, $rootScope, $stateParams, previousState, entity, Formulaire, Solution) {
        var vm = this;

        vm.formulaire = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('testjhipsterApp:formulaireUpdate', function(event, result) {
            vm.formulaire = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
