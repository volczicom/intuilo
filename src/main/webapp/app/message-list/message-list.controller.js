(function () {
    'use strict';

    angular
        .module('app')
        .controller('MessageController', MessageController);

    MessageController.$inject = ['$routeParams', '$scope', '$timeout'];
    /* @ngInject */
    function MessageController($routeParams, $scope, $timeout) {
        var vm = this;
		
		vm.messages = [];
		vm.hasMore = false;
		vm.limit = 10;
		vm.loading = false;
		
		function loadMessages(newer) {
			vm.loading = true;
			$timeout(function() {
				if (vm.loading) {
					vm.showLoading = true;
				}
			}, 200);
			
			var messageLoadParameters = {
				'topic' : $routeParams.topicId, 
				'limit': vm.limit
			};
			if (vm.messages && vm.messages.length > 0) {
				if (newer) {
					messageLoadParameters.minDate = vm.messages[0].createdAt;
				} else {
					messageLoadParameters.maxDate = vm.messages[vm.messages.length - 1].createdAt;
				}
			}
			gapi.client.message.message.getMessages(messageLoadParameters).execute(function(resp) {
				if (!newer || !vm.messages || vm.messages.length == 0) {
					vm.hasMore = resp.items && resp.items.length >= vm.limit;
				}
				
				if (resp.items) {
					if (newer) {
						vm.messages = resp.items.concat(vm.messages);
					} else {
						vm.messages = vm.messages.concat(resp.items);						
					}						
				}											
							
				vm.loading = false;
				vm.showLoading = false;
				$scope.$apply();
			});
		}
		loadMessages(true);
		
		gapi.client.topic.topic.getTopic({'topicId': $routeParams.topicId}).execute(function(resp) {
			vm.topic = resp;
		});
		
		vm.submit = function() {
			if (vm.content) {
				gapi.client.message.message.createMessage({'topic' : $routeParams.topicId, 'content': vm.content}).execute(function(resp) {
					vm.content = '';
					loadMessages(true);
				});
			}
		}
		
		vm.loadMore = function() {
			loadMessages(false);
		}
		
		vm.loadFresh = function() {
			loadMessages(true);
		}
    }
})();
