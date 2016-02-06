'use strict';

describe('Controller Tests', function() {

    describe('Preference Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreference, MockUser, MockGradeSystem, MockCrag, MockRoute;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreference = jasmine.createSpy('MockPreference');
            MockUser = jasmine.createSpy('MockUser');
            MockGradeSystem = jasmine.createSpy('MockGradeSystem');
            MockCrag = jasmine.createSpy('MockCrag');
            MockRoute = jasmine.createSpy('MockRoute');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Preference': MockPreference,
                'User': MockUser,
                'GradeSystem': MockGradeSystem,
                'Crag': MockCrag,
                'Route': MockRoute
            };
            createController = function() {
                $injector.get('$controller')("PreferenceDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'climbtrackerApp:preferenceUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
