'use strict';

angular.module('pacpontosApp')
    .controller('CidadeDetailController', function ($scope, $stateParams, Cidade) {
        $scope.cidade = {};
        $scope.load = function (id) {
            Cidade.get({id: id}, function(result) {
              $scope.cidade = result;
            });
        };
        $scope.load($stateParams.id);
    });
