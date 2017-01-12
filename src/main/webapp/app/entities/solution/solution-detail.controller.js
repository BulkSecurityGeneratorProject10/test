(function() {
    'use strict';

    angular
        .module('testjhipsterApp')
        .controller('SolutionDetailController', SolutionDetailController);

    SolutionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Solution', 'Contact'];

    function SolutionDetailController($scope, $rootScope, $stateParams, previousState, entity, Solution, Contact) {
        var vm = this;

        vm.solution = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('testjhipsterApp:solutionUpdate', function(event, result) {
            vm.solution = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
