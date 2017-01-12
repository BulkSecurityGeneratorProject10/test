(function() {
    'use strict';

    angular
        .module('testjhipsterApp')
        .controller('FormulaireDeleteController',FormulaireDeleteController);

    FormulaireDeleteController.$inject = ['$uibModalInstance', 'entity', 'Formulaire'];

    function FormulaireDeleteController($uibModalInstance, entity, Formulaire) {
        var vm = this;

        vm.formulaire = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Formulaire.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
