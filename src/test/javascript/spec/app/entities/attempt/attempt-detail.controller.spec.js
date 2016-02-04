'use strict';

describe('Controller Tests', function() {

    describe('Attempt Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockAttempt, MockRoute;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockAttempt = jasmine.createSpy('MockAttempt');
            MockRoute = jasmine.createSpy('MockRoute');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Attempt': MockAttempt,
                'Route': MockRoute
            };
            createController = function() {
                $injector.get('$controller')("AttemptDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'climbtrackerApp:attemptUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
