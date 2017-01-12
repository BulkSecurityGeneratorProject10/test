(function() {
    'use strict';

    angular
        .module('testjhipsterApp')
        .controller('FormulaireController', FormulaireController);

    FormulaireController.$inject = ['$scope', '$state', 'Formulaire', 'ParseLinks', 'AlertService', 'paginationConstants','Solution'];

    function FormulaireController ($scope, $state, Formulaire, ParseLinks, AlertService, paginationConstants,Solution) {
        var vm = this;

        vm.formulaires = [];
        vm.loadPage = loadPage;
        vm.itemsPerPage = paginationConstants.itemsPerPage;
        vm.page = 0;
        $scope.publicVise = 'etudiants';
        $scope.objectif = 'event';
        
        $scope.searchBudget = function(param) {
            return function(solution) {
            	if(solution.budgetMax>=10000){
            		if(solution.budgetMin <= parseInt(param)){
            			return true;
            		}
            	}
            	if((solution.budgetMin <= parseInt(param))&&(solution.budgetMax > parseInt(param))){
            		return true;
            	}
            	return false;
            }
        }
        $scope.searchDelai = function(param) {
            return function(solution) {
            	if(parseInt(solution.delai) <= parseInt(param)){
            		return true;
            	}
            	return false;
            }
        }
        $scope.searchnbrPersonnesVisees = function(param) {
            return function(solution) {
            	if(solution.nbrPersonnesVisees <= parseInt(param)){
            		return true;
            	}
            	return false;
            }
        }
        $scope.searchPublicVise = function(param) {
            return function(solution) {
            	if(solution.publicVise == param){
            		return true;
            	}
            	return false;
            }
        }
        $scope.searchObjectif = function(param) {
            return function(solution) {
            	if(solution.objectif == param){
            		return true;
            	}
            	return false;
            }
        }
        vm.solutions = Solution.query();
        vm.links = {
            last: 0
        };
        vm.predicate = 'id';
        vm.reset = reset;
        vm.reverse = true;

        loadAll();

        function loadAll () {
            Formulaire.query({
                page: vm.page,
                size: vm.itemsPerPage,
                sort: sort()
            }, onSuccess, onError);
            function sort() {
                var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
                if (vm.predicate !== 'id') {
                    result.push('id');
                }
                return result;
            }

            function onSuccess(data, headers) {
                vm.links = ParseLinks.parse(headers('link'));
                vm.totalItems = headers('X-Total-Count');
                for (var i = 0; i < data.length; i++) {
                    vm.formulaires.push(data[i]);
                }
            }

            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

        function reset () {
            vm.page = 0;
            vm.formulaires = [];
            loadAll();
        }

        function loadPage(page) {
            vm.page = page;
            loadAll();
        }
    }
})();
