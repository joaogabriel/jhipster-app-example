'use strict';

angular.module('pacpontosApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('cidade', {
                parent: 'entity',
                url: '/cidade',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'pacpontosApp.cidade.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/cidade/cidades.html',
                        controller: 'CidadeController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('cidade');
                        return $translate.refresh();
                    }]
                }
            })
            .state('cidadeDetail', {
                parent: 'entity',
                url: '/cidade/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'pacpontosApp.cidade.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/cidade/cidade-detail.html',
                        controller: 'CidadeDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('cidade');
                        return $translate.refresh();
                    }]
                }
            });
    });
