sdGuiAutoApp.controller('UrlsController', [ '$scope', '$rootScope', 'AjaxService', '$location', '$controller', function($scope, $rootScope, AjaxService, $location, $controller) {
    'use strict';
    
    $controller('BaseController', {
		$scope : $scope
	});
    
    $rootScope.pageTitle = "URLs";
    
    $scope.restUrl = "urls/";
    
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
	    $scope.openAsDialog('urls/add.html', ev, function() {
	        $scope.load();
	    });
	};
	
	$scope.deleteItem = function(item, $event) {
		$scope.confirmDialog({
			title: 'Are you sure to delete this ?',
			content: 'URL Name: ' + item.name,
			okLabel: 'Delete',
			cancelLabel: 'Cancel'
		}, $event, function() {
			AjaxService.call($scope.restUrl + item.id, 'DELETE').success(function(data, status, headers, config) {
                $scope.load();
            });
		});
    };
    
} ]);

sdGuiAutoApp.controller('AddEditUrlController', [ '$scope', '$rootScope', 'AjaxService', '$controller', function($scope, $rootScope, AjaxService, $controller) {
    'use strict';
    
    $controller('BaseController', {
		$scope : $scope
	});
    
    $scope.restUrl = "urls/";
    
    $scope.init = function() {
        $scope.item = $rootScope.temp.item;
    };
    
    $scope.save = function() {
        AjaxService.call($scope.restUrl, 'POST', $scope.item).success(function(data, status, headers, config) {
        	$scope.item = data;
        });
    };

} ]);