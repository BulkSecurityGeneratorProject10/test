(function() {
    'use strict';

    angular
        .module('testjhipsterApp')
        .controller('ContactDetailController', ContactDetailController);

    ContactDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Contact'];

    function ContactDetailController($scope, $rootScope, $stateParams, previousState, entity, Contact) {
        var vm = this;

        vm.contact = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('testjhipsterApp:contactUpdate', function(event, result) {
            vm.contact = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
