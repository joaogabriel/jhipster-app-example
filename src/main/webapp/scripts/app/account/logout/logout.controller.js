'use strict';

angular.module('pacpontosApp')
    .controller('LogoutController', function (Auth) {
        Auth.logout();
    });
