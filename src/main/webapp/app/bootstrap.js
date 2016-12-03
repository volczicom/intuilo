function init() {
	initialize('/_ah/api');
}

function signin(mode) {
	gapi.auth.authorize(
		{
			// TODO replace this with your own client id
			client_id: '1097207723412-2u798llcj8pn0fifd31pnqsqtmvom1tc.apps.googleusercontent.com',
			scope: 'https://www.googleapis.com/auth/userinfo.email',
			immediate: mode
		},
		authorizeCallback);
}

function authorizeCallback(resp) {
	console.log('authorize: ' + angular.toJson(resp));
	if (resp.error) {
		document.getElementById("bootstrap-progress").style.visibility = 'hidden';
		document.getElementById("bootstrap-login").style.visibility = 'visible';
	} else {
		accessToken = resp.access_token;
		//bootstrap manually angularjs after our api are loaded
		angular.bootstrap(document, [ "app" ]);
	}
}

/**
 * Initializes the application. It loads asynchronously all needed libraries
 *
 * @param {string}
 *            apiRoot Root of the API's path.
 */
function initialize(apiRoot) {
	var progress = 0;
	var progressInterval = window.setInterval(function() {
		progress += 10;
		if (progress > 90) {
			window.clearInterval(progressInterval);
		} else {
			document.getElementById("bootstrap-progress-bar").style.width = progress + "%";
		}
	}, 400);

	var apisToLoad;
	var callback = function() {
		if (--apisToLoad == 0) {
			signin(true, authorizeCallback)
		}
	}
	var apisToLoad = 3; // must match number of calls to gapi.client.load()
	gapi.client.load('topic', 'v1', callback, apiRoot);
	gapi.client.load('message', 'v1', callback, apiRoot);
	gapi.client.load('oauth2', 'v2', callback);
};