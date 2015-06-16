'use strict';

angular.module('pacpontosApp')
    .controller('CarimboController', function ($scope, Carimbo) {
        $scope.carimbos = [];
        $scope.loadAll = function() {
            Carimbo.query(function(result) {
               $scope.carimbos = result;
            });
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            Carimbo.get({id: id}, function(result) {
                $scope.carimbo = result;
                $('#saveCarimboModal').modal('show');
            });
        };

        $scope.save = function () {
            if ($scope.carimbo.id != null) {
                Carimbo.update($scope.carimbo,
                    function () {
                        $scope.refresh();
                    });
            } else {
                Carimbo.save($scope.carimbo,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.delete = function (id) {
            Carimbo.get({id: id}, function(result) {
                $scope.carimbo = result;
                $('#deleteCarimboConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Carimbo.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteCarimboConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $('#saveCarimboModal').modal('hide');
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.carimbo = {id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
