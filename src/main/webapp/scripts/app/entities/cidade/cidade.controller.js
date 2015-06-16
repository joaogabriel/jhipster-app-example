'use strict';

angular.module('pacpontosApp')
    .controller('CidadeController', function ($scope, Cidade) {
        $scope.cidades = [];
        $scope.loadAll = function() {
            Cidade.query(function(result) {
               $scope.cidades = result;
            });
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            Cidade.get({id: id}, function(result) {
                $scope.cidade = result;
                $('#saveCidadeModal').modal('show');
            });
        };

        $scope.save = function () {
            if ($scope.cidade.id != null) {
                Cidade.update($scope.cidade,
                    function () {
                        $scope.refresh();
                    });
            } else {
                Cidade.save($scope.cidade,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.delete = function (id) {
            Cidade.get({id: id}, function(result) {
                $scope.cidade = result;
                $('#deleteCidadeConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Cidade.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteCidadeConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $('#saveCidadeModal').modal('hide');
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.cidade = {nome: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
