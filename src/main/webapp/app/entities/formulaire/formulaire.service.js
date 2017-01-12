(function() {
    'use strict';
    angular
        .module('testjhipsterApp')
        .factory('Formulaire', Formulaire);

    Formulaire.$inject = ['$resource'];

    function Formulaire ($resource) {
        var resourceUrl =  'api/formulaires/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
