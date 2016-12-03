(function () {
    'use strict';

    angular
        .module('app')
        .controller('TopicController', TopicController);

    TopicController.$inject = ['$scope'];
    /* @ngInject */
    function TopicController($scope) {
        var vm = this;
		
		vm.topics =[];
		function getTopics() {
			gapi.client.topic.topic.getTopics().execute(function(resp) {
				vm.topics = resp.items;
				$scope.$apply();
			});
		}
		getTopics();
		
		vm.submit = function() {
			gapi.client.topic.topic.createTopic({'name': vm.topicName}).execute(function(resp) {
				vm.topicName = null;
				getTopics();
			});
		}
    }
})();
