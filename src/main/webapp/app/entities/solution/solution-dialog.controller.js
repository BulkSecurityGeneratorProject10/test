(function() {
    'use strict';

    angular
        .module('testjhipsterApp')
        .controller('SolutionDialogController', SolutionDialogController);

    SolutionDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Solution', 'Contact'];

    function SolutionDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Solution, Contact) {
        var vm = this;

        vm.solution = entity;
        vm.clear = clear;
        vm.save = save;
        vm.contacts = Contact.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.solution.id !== null) {
                Solution.update(vm.solution, onSaveSuccess, onSaveError);
            } else {
                Solution.save(vm.solution, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('testjhipsterApp:solutionUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
