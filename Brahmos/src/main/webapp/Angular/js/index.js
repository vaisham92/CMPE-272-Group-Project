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
	}).when('/projects/:projectId/builds', {
		templateUrl : 'Angular/projects/Projects/templates/builds.html',
		controller : 'buildController'
	}).when('/projects/:projectId/builds/:buildNumber', {
		templateUrl : 'Angular/projects/Projects/templates/graphs.html',
		controller : 'graphController'
	}).when('/resultAnalyzer', {
		templateUrl : 'Angular/projects/Projects/templates/resultAnalyzer.html',
		controller : 'resultController'
	}).otherwise({
		redirectTo : '/projects'
	});
} ]);