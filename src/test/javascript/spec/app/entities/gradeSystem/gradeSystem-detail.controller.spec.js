'use strict';

describe('Controller Tests', function() {

    describe('GradeSystem Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockGradeSystem;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockGradeSystem = jasmine.createSpy('MockGradeSystem');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'GradeSystem': MockGradeSystem
            };
            createController = function() {
                $injector.get('$controller')("GradeSystemDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'climbtrackerApp:gradeSystemUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
