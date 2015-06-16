'use strict';

angular.module('pacpontosApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


