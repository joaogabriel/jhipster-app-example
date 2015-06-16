'use strict';

angular.module('pacpontosApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('carimbo', {
                parent: 'entity',
                url: '/carimbo',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'pacpontosApp.carimbo.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/carimbo/carimbos.html',
                        controller: 'CarimboController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('carimbo');
                        return $translate.refresh();
                    }]
                }
            })
            .state('carimboDetail', {
                parent: 'entity',
                url: '/carimbo/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'pacpontosApp.carimbo.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/carimbo/carimbo-detail.html',
                        controller: 'CarimboDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('carimbo');
                        return $translate.refresh();
                    }]
                }
            });
    });
