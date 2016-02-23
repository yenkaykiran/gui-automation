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
    
    $scope.runTest = function(item, ev) {
        $location.path("/home/tests/" + item.id + "/runner");
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
    
    $scope.restUrl = "steps/byTest/" + $rootScope.temp.testId + '/';
    
    $scope.load = function() {
    	$scope.reorderSteps = {
			stop : function(e, ui) {
				var orders = [];
				for (var index in $scope.items) {
					$scope.items[index].stepOrder = index;
					var obj = {};
					obj[$scope.items[index].id] = index;
					orders.push(obj);
				}
				AjaxService.call($scope.restUrl + 'reorder', 'POST', orders).success(function(data, status, headers, config) {
				    $scope.load();
		        });
			}
		};
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
            item : data,
            testId: $routeSegment.$routeParams['id']
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
    
    $scope.enableDisable = function(item) {
        AjaxService.call($scope.restUrl, 'POST', item).success(function(data, status, headers, config) {
            $scope.item = data;
        });
    };
    
} ]);

sdGuiAutoApp.controller('AddEditTestStepController', [ '$scope', '$rootScope', 'AjaxService', '$controller', function($scope, $rootScope, AjaxService, $controller) {
    'use strict';
    
    $controller('BaseController', {
		$scope : $scope
	});
    
    $scope.restUrl = "steps/byTest/" + $rootScope.temp.testId + '/';
    
    $scope.init = function() {
        $scope.item = $rootScope.temp.item;
        AjaxService.call('pages/all', 'GET').success(function(data, status, headers, config) {
            $scope.pages = data;
            if($scope.pages && $scope.pages[0].elements) {
            	$scope.elements = $scope.pages[0].elements;
            } else {
            	$scope.elements = [];
            }
        });
        AjaxService.call('meta/waitTypes', 'GET').success(function(data, status, headers, config) {
            $scope.waitTypes = data;
        });
        AjaxService.call('meta/actionTypes', 'GET').success(function(data, status, headers, config) {
            $scope.actionTypes = data;
        });
    };
    
    $scope.save = function() {
        AjaxService.call($scope.restUrl, 'POST', $scope.item).success(function(data, status, headers, config) {
        	$scope.item = data;
        });
    };
    
    $scope.populateElements = function(page) {
    	$scope.elements = page.elements;
    };
    
} ]);

/* ******************************************************************************* */

sdGuiAutoApp.controller('TestsRunController', [ '$scope', '$rootScope', 'AjaxService', '$location', '$controller', '$routeSegment', function($scope, $rootScope, AjaxService, $location, $controller, $routeSegment) {
    'use strict';
    
    $controller('BaseController', {
        $scope : $scope
    });
    
    $rootScope.pageTitle = "Tests Runner";
    
    $rootScope.temp.testId = $routeSegment.$routeParams['id'];
    
    $scope.restUrl = "tests/" + $rootScope.temp.testId + '/runner/';
    
    $scope.load = function() {
        AjaxService.call($scope.restUrl, 'GET').success(function(data, status, headers, config) {
            $scope.items = data;
        });
    };
    
    $scope.init = function() {
        AjaxService.call('browsers', 'GET').success(function(data, status, headers, config) {
            $scope.browsers = data;
        });
        $scope.load();
    };
    
    $scope.getBrowserName = function(item) {
        return getObjectFromId($scope.browsers, item.browser);
    };
    
    $scope.start = function(ev) {
        $scope.openAsDialog('tests/newRun.html', ev, function() {
            $scope.load();
        });
    };
    
    $scope.viewStatus = function(item, $event) {
    	if(!item) {
    		item = {};
	    }
	    $rootScope.temp = {
            item : item
        };
    	$scope.openAsDialog('tests/runResult.html', $event, function() {
            $scope.load();
        });
    };
} ]);

sdGuiAutoApp.controller('AddTestRunController', [ '$scope', '$rootScope', 'AjaxService', '$controller', function($scope, $rootScope, AjaxService, $controller) {
    'use strict';
    
    $controller('BaseController', {
        $scope : $scope
    });
    
    $scope.restUrl = "tests/" + $rootScope.temp.testId + '/runner/';
    
    $scope.init = function() {
        $scope.item = $rootScope.temp.item;
        AjaxService.call('urls', 'GET').success(function(data, status, headers, config) {
            $scope.urls = data;
        });
        AjaxService.call('browsers', 'GET').success(function(data, status, headers, config) {
            $scope.browsers = data;
        });
        AjaxService.call('proxies', 'GET').success(function(data, status, headers, config) {
            $scope.proxies = data;
        });
    };
    
    $scope.save = function() {
        AjaxService.call($scope.restUrl, 'POST', $scope.item).success(function(data, status, headers, config) {
            $scope.item = data;
            $scope.cancel();
        });
    };

} ]);

sdGuiAutoApp.controller('TestRunResultController', [ '$scope', '$rootScope', 'AjaxService', '$controller', function($scope, $rootScope, AjaxService, $controller) {
    'use strict';
    
    $controller('BaseController', {
        $scope : $scope
    });
    
    $scope.restUrl = "stepInstances/run/" + $rootScope.temp.item.id + '/';
    
    $scope.init = function() {
        AjaxService.call($scope.restUrl, 'GET').success(function(data, status, headers, config) {
            $scope.items = data;
        });
    };

} ]);