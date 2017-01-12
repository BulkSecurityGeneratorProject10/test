(function() {
    'use strict';

    angular
        .module('testjhipsterApp')
        .controller('SolutionDeleteController',SolutionDeleteController);

    SolutionDeleteController.$inject = ['$uibModalInstance', 'entity', 'Solution'];

    function SolutionDeleteController($uibModalInstance, entity, Solution) {
        var vm = this;

        vm.solution = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Solution.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
