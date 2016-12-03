(function () {
    'use strict';

	var forumApp = angular.module('app', [
	  'ngRoute'
	]);

	forumApp.config(['$routeProvider',
	  function($routeProvider) {
		$routeProvider.
		  when('/', {
			templateUrl: 'app/topic-list/topic-list.html',
			controller: 'TopicController',
			controllerAs: 'vm'
		  }).
		  when('/topic/:topicId', {
			templateUrl: 'app/message-list/message-list.html',
			controller: 'MessageController',
			controllerAs: 'vm'
		  }).
		  otherwise({
			redirectTo: '/'
		  });
	  }]);
})();