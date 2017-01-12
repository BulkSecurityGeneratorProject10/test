(function() {
    'use strict';

    angular
        .module('testjhipsterApp')
        .controller('FormulaireDialogController', FormulaireDialogController);

    FormulaireDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Formulaire', 'Solution'];

    function FormulaireDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Formulaire, Solution) {
        var vm = this;

        vm.formulaire = entity;
        vm.clear = clear;
        vm.save = save;
        vm.solutions = Solution.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.formulaire.id !== null) {
                Formulaire.update(vm.formulaire, onSaveSuccess, onSaveError);
            } else {
                Formulaire.save(vm.formulaire, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('testjhipsterApp:formulaireUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
