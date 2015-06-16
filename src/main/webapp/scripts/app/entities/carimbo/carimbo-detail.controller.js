'use strict';

angular.module('pacpontosApp')
    .controller('CarimboDetailController', function ($scope, $stateParams, Carimbo) {
        $scope.carimbo = {};
        $scope.load = function (id) {
            Carimbo.get({id: id}, function(result) {
              $scope.carimbo = result;
            });
        };
        $scope.load($stateParams.id);
    });
