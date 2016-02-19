sdGuiAutoApp.controller('TestsController', [ '$scope', '$rootScope', 'AjaxService', '$location', '$controller', function($scope, $rootScope, AjaxService, $location, $controller) {
    'use strict';
    
    $controller('BaseController', {
		$scope : $scope
	});
    
    $rootScope.pageTitle = "Tests";
    
    $scope.restUrl = "tests/";
    
    $scope.load = function() {
    	AjaxService.call($scope.restUrl, 'GET').success(function(data, status, headers, config) {
            $scope.items = data;
        });
    };
    
    $scope.init = function() {
    	$scope.load();
    };
    
    $scope.add = function(data, ev) {
	    if(!data) {
	    	data = {};
	    }
	    $rootScope.temp = {
            item : data
        };
	    $scope.openAsDialog('tests/add.html', ev, function() {
	        $scope.load();
	    });
	};
	
	$scope.deleteItem = function(item, $event) {
		$scope.confirmDialog({
			title: 'Are you sure to delete this ?',
			content: 'Test Name: ' + item.name,
			okLabel: 'Delete',
			cancelLabel: 'Cancel'
		}, $event, function() {
			AjaxService.call($scope.restUrl + item.id, 'DELETE').success(function(data, status, headers, config) {
                $scope.load();
            });
		});
    };
    
    $scope.launchSteps = function(item) {
        $location.path("/home/tests/" + item.id + "/steps");
    };
    
} ]);

sdGuiAutoApp.controller('AddEditTestController', [ '$scope', '$rootScope', 'AjaxService', '$controller', function($scope, $rootScope, AjaxService, $controller) {
    'use strict';
    
    $controller('BaseController', {
		$scope : $scope
	});
    
    $scope.restUrl = "tests/";
    
    $scope.init = function() {
        $scope.item = $rootScope.temp.item;
    };
    
    $scope.save = function() {
        AjaxService.call($scope.restUrl, 'POST', $scope.item).success(function(data, status, headers, config) {
        	$scope.item = data;
        });
    };

} ]);

sdGuiAutoApp.controller('TestStepsController', [ '$scope', '$rootScope', 'AjaxService', '$routeSegment', '$location', '$controller', function($scope, $rootScope, AjaxService, $routeSegment, $location, $controller) {
    'use strict';
    
    $controller('BaseController', {
        $scope : $scope
    });
    
    $rootScope.pageTitle = "Test Steps";
    
    $rootScope.temp.testId = $routeSegment.$routeParams['id'];
    
    $scope.restUrl = "steps/";
    
    $scope.load = function() {
        AjaxService.call($scope.restUrl, 'GET').success(function(data, status, headers, config) {
            $scope.items = data;
        });
    };
    
    $scope.init = function() {
        $scope.load();
    };
    
    $scope.add = function(data, ev) {
        if(!data) {
            data = {};
        }
        $rootScope.temp = {
            item : data
        };
        $scope.openAsDialog('tests/addStep.html', ev, function() {
            $scope.load();
        });
    };
    
    $scope.deleteItem = function(item, $event) {
        $scope.confirmDialog({
            title: 'Are you sure to delete this ?',
            content: 'Step Name: ' + item.name,
            okLabel: 'Delete',
            cancelLabel: 'Cancel'
        }, $event, function() {
            AjaxService.call($scope.restUrl + item.id, 'DELETE').success(function(data, status, headers, config) {
                $scope.load();
            });
        });
    };
    
} ]);