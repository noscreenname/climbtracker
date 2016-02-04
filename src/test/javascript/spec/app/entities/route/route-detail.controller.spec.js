'use strict';

describe('Controller Tests', function() {

    describe('Route Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockRoute, MockRouteType, MockGrade, MockCrag;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockRoute = jasmine.createSpy('MockRoute');
            MockRouteType = jasmine.createSpy('MockRouteType');
            MockGrade = jasmine.createSpy('MockGrade');
            MockCrag = jasmine.createSpy('MockCrag');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Route': MockRoute,
                'RouteType': MockRouteType,
                'Grade': MockGrade,
                'Crag': MockCrag
            };
            createController = function() {
                $injector.get('$controller')("RouteDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'climbtrackerApp:routeUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
