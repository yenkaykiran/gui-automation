sdGuiAutoApp.controller('PagesController', [ '$scope', '$rootScope', 'AjaxService', '$location', '$controller', function($scope, $rootScope, AjaxService, $location, $controller) {
    'use strict';
    
    $controller('BaseController', {
		$scope : $scope
	});
    
    $rootScope.pageTitle = "Pages";
    
    $scope.restUrl = "pages/";
    
    $scope.load = function() {
    	AjaxService.call($scope.restUrl + 'all', 'GET').success(function(data, status, headers, config) {
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
	    $scope.openAsDialog('pages/add.html', ev, function() {
	        $scope.load();
	    });
	};
	
	$scope.deleteItem = function(item, $event) {
		$scope.confirmDialog({
			title: 'Are you sure to delete this ?',
			content: 'Page Name: ' + item.name,
			okLabel: 'Delete',
			cancelLabel: 'Cancel'
		}, $event, function() {
			AjaxService.call($scope.restUrl + item.id, 'DELETE').success(function(data, status, headers, config) {
                $scope.load();
            });
		});
    };
    
} ]);

sdGuiAutoApp.controller('AddEditPageController', [ '$scope', '$rootScope', 'AjaxService', '$controller', function($scope, $rootScope, AjaxService, $controller) {
    'use strict';
    
    $controller('BaseController', {
		$scope : $scope
	});
    
    $scope.restUrl = "pages/";
    
    $scope.init = function() {
        $scope.item = $rootScope.temp.item;
        AjaxService.call('elements/all', 'GET').success(function(data, status, headers, config) {
        	$scope.elements = data;
        });
    };
    
    $scope.save = function() {
        AjaxService.call($scope.restUrl + 'savePage', 'POST', $scope.item).success(function(data, status, headers, config) {
        	$scope.item = data;
        });
    };
    
    $scope.getElement = function(allottedElement) {
        for(var i in $scope.elements) {
            if($scope.elements[i].id == allottedElement) {
                return $scope.elements[i];
            }
        }
    };
    
    $scope.isAssigned = function(page, elem) {
        for(var allotedElem in page.elements) {
            if(elem.id == page.elements[allotedElem].id) {
                return true;
            }
        }
        return false;
    };
    
    $scope.addElementToPage = function(page, elemId) {
        if(!$scope.isAssigned(page, $scope.getElement(elemId))) {
            if(!page.elements) {
                page.elements = [];
            }
            try {
                elemId = parseInt(elemId);
            } catch (e) { }
            page.elements.push({id: elemId});
            elemId = null;
        }
    };
    
    $scope.getAssignedElement = function(allottedElement) {
        for(var i in $scope.elements) {
            if($scope.item.elements[i].id == allottedElement) {
                return i;
            }
        }
    };
    
    $scope.removeElementFromPage = function(page, elemId) {
        var assigned = $scope.getAssignedElement(elemId);
        if(assigned && assigned >= 0) {
            AjaxService.call('pages/' + $scope.item.id + '/element/' + elemId, 'DELETE').success(function(data, status, headers, config) {
                $scope.item.elements.splice(assigned, 1);
                AjaxService.call('elements/', 'GET').success(function(data, status, headers, config) {
                    $scope.elements = data;
                    AjaxService.call('elements/all', 'GET').success(function(data, status, headers, config) {
                    	$scope.elements = data;
                    });
                });
            });
        }
    };

} ]);