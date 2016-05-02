var brahmos = angular.module('brahmos', [ 'ngRoute' ]);
var projectId;
var projectName;
var scenarioId;
var scenarioName;
var buildNumber;

brahmos.config([ '$routeProvider', function($routeProvider) {
	$routeProvider.when('/', {
		templateUrl : 'Angular/projects/Projects/templates/projects.html',
		controller : 'projectsController'
	}).when('/projects', {
		templateUrl : 'Angular/projects/Projects/templates/projects.html',
		controller : 'projectsController'
	}).when('/projects/:projectId/scenarios', {
		templateUrl : 'Angular/projects/Projects/templates/scenarios.html',
		controller : 'scenariosController'
	}).when('/projects/:projectId/new', {
		templateUrl : 'Angular/projects/Projects/templates/newScenario.html',
		controller : 'newScenarioController'
	}).when('/projects/:projectId/scenarios/:scenarioId/builds', {
		templateUrl : 'Angular/projects/Projects/templates/builds.html',
		controller : 'buildController'
	}).when('/projects/:projectId/scenarios/:scenarioId/builds/:buildNumber', {
		templateUrl : 'Angular/projects/Projects/templates/graphs.html',
		controller : 'graphController'
	}).when('/settings', {
		templateUrl : 'Angular/projects/Projects/templates/settings.html',
		controller : 'settingsController'
	}).when('/resultAnalyzer', {
		templateUrl : 'Angular/projects/Projects/templates/resultAnalyzer.html',
		controller : 'resultController'
	}).when('/tutorial', {
		templateUrl : 'Angular/projects/Projects/templates/tutorial.html',
		controller : 'tutorialController'
	}).when('/about', {
		templateUrl : 'Angular/projects/Projects/templates/about.html',
		controller : 'aboutController'
	}).when('/perfSheet', {
		templateUrl : 'Angular/projects/Projects/templates/perfSheet.html',
		controller : 'perfSheetController'
	}).otherwise({
		redirectTo : '/projects'
	});
} ]);

brahmos.controller('newProjectController', function($scope) {

});

brahmos.controller('newScenarioController', function($scope) {

});

brahmos.controller('settingsController', function($scope) {

});

brahmos.controller('aboutController', function($scope) {

});
