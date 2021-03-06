sdGuiAutoApp.controller('ElementsController', [ '$scope', '$rootScope', 'AjaxService', '$location', '$controller', '$routeSegment', function($scope, $rootScope, AjaxService, $location, $controller, $routeSegment) {
    'use strict';
    
    $controller('BaseController', {
		$scope : $scope
	});
    
    $rootScope.pageTitle = "Elements";

    $rootScope.temp.pageId = $routeSegment.$routeParams['id'];
    
    $scope.restUrl = "elements/byPage/" + $rootScope.temp.pageId + '/';
    
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
            item : data,
            pageId: $routeSegment.$routeParams['id']
        };
	    $scope.openAsDialog('elements/add.html', ev, function() {
	        $scope.load();
	    });
	};
	
	$scope.deleteItem = function(item, $event) {
		$scope.confirmDialog({
			title: 'Are you sure to delete this ?',
			content: 'Element Name: ' + item.name,
			okLabel: 'Delete',
			cancelLabel: 'Cancel'
		}, $event, function() {
			AjaxService.call($scope.restUrl + item.id, 'DELETE').success(function(data, status, headers, config) {
                $scope.load();
            });
		});
    };
    
} ]);

sdGuiAutoApp.controller('AddEditElementController', [ '$scope', '$rootScope', 'AjaxService', '$controller', function($scope, $rootScope, AjaxService, $controller) {
    'use strict';
    
    $controller('BaseController', {
		$scope : $scope
	});
    
    $scope.restUrl = "elements/byPage/" + $rootScope.temp.pageId + '/';
    
    $scope.init = function() {
        $scope.item = $rootScope.temp.item;
        AjaxService.call('meta/identificationTypes', 'GET').success(function(data, status, headers, config) {
            $scope.identificationTypes = data;
        });
    };
    
    $scope.save = function() {
        AjaxService.call($scope.restUrl, 'POST', $scope.item).success(function(data, status, headers, config) {
        	$scope.item = data;
        });
    };

} ]);