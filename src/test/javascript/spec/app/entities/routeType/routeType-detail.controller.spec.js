'use strict';

describe('Controller Tests', function() {

    describe('RouteType Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockRouteType;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockRouteType = jasmine.createSpy('MockRouteType');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'RouteType': MockRouteType
            };
            createController = function() {
                $injector.get('$controller')("RouteTypeDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'climbtrackerApp:routeTypeUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
