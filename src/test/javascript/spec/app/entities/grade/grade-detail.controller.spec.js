'use strict';

describe('Controller Tests', function() {

    describe('Grade Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockGrade, MockGradeSystem;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockGrade = jasmine.createSpy('MockGrade');
            MockGradeSystem = jasmine.createSpy('MockGradeSystem');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Grade': MockGrade,
                'GradeSystem': MockGradeSystem
            };
            createController = function() {
                $injector.get('$controller')("GradeDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'climbtrackerApp:gradeUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
