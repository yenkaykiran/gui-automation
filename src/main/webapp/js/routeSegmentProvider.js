sdGuiAutoApp.config(function($routeSegmentProvider, $routeProvider) {

    $routeSegmentProvider.options.autoLoadTemplates = true;

    $routeSegmentProvider.
    	when('/home', 'home').
    	when('/home/proxies', 'home.proxies').
    	when('/home/browsers', 'home.browsers').
    	when('/home/urls', 'home.urls').
    	when('/home/tests', 'home.tests').
    	when('/home/tests/steps', 'home.tests.steps').
    	segment('home', {
	        templateUrl : 'home/tmpl.html',
	    });

    $routeSegmentProvider.
	    within('home').
		    segment('default', {
		    	'default': true,
		        templateUrl : 'home/default.html'
		    }).
	    	segment('proxies', {
		        templateUrl : 'proxies/tmpl.html'
		    }).
	    	segment('browsers', {
		        templateUrl : 'browsers/tmpl.html'
		    }).
	    	segment('urls', {
		        templateUrl : 'urls/tmpl.html'
		    }).
	    	segment('tests', {
		        templateUrl : 'tests/tmpl.html'
		    });
    
    $routeSegmentProvider.
	    within('home').
		    segment('tests', {
		        templateUrl : 'tests/tmpl.html'
		    }).
		    within().
	            segment('steps', {
	                templateUrl : 'tests/steps.html'
	            });
    
    $routeProvider.otherwise({redirectTo: '/home'}); 
});