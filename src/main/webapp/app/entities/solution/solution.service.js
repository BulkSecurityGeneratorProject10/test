(function() {
    'use strict';
    angular
        .module('testjhipsterApp')
        .factory('Solution', Solution);

    Solution.$inject = ['$resource'];

    function Solution ($resource) {
        var resourceUrl =  'api/solutions/:id';

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
