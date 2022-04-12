<?xml version="1.0"?>
<!--
Copyright (c) 2022 Surati

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to read
the Software only. Permissions is hereby NOT GRANTED to use, copy, modify,
merge, publish, distribute, sublicense, and/or sell copies of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
-->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:sec="http://www.surati.io/Security/User/Profile" version="2.0">
  <xsl:output method="html" include-content-type="no" doctype-system="about:legacy-compat" encoding="UTF-8" indent="yes"/>
  <xsl:include href="/io/surati/gap/web/base/xsl/layout.xsl"/>
  <xsl:template match="page" mode="head">
    <title>
      <xsl:text>GAP - Aper&#xE7;u du rapport</xsl:text>
    </title>
  </xsl:template>
  <xsl:template match="page" mode="header">
    <div class="app-page-title app-page-title-simple">
      <div class="page-title-wrapper">
        <div class="page-title-heading">
          <div class="page-title-icon">
            <i class="lnr-briefcase icon-gradient bg-night-fade"/>
          </div>
          <div>
            <xsl:value-of select="root_page/title"/>
            <div class="page-title-subheading opacity-10">
              <nav class="" aria-label="breadcrumb">
                <ol class="breadcrumb">
                  <li class="breadcrumb-item">
                    <a href="/home">
                      <i aria-hidden="true" class="fa fa-home"/>
                    </a>
                  </li>
                  <li class="active breadcrumb-item">
                    <xsl:value-of select="root_page/subtitle"/>
                  </li>
                </ol>
              </nav>
            </div>
          </div>
        </div>
      </div>
    </div>
  </xsl:template>
  <xsl:template match="page" mode="body">
    <div class="main-card mb-3 card card-body" app="app" ng-controller="AppCtrl">
      <div class="card-header">
        <div class="card-header-title font-size-lg text-capitalize font-weight-normal">
          <xsl:value-of select="root_page/subtitle"/>
        </div>
        <div class="btn-actions-pane-right">
          <div class="row">
            <div class="form-inline">
              <span class="mr-1">Page: </span>
              <input class="form-control form-control-sm mr-1" style="color: black;" type="number" ng-min="1" ng-max="pageCount" ng-model="pageNum"/>
              <span class="mr-1"> / {{pageCount}}</span>
              <button type="button" class="btn btn-primary btn-sm mr-1" ng-disabled="pageNum == 1" ng-click="goPrevious()">
                <i class="fa fa-arrow-left"/>
              </button>
              <button type="button" class="btn btn-primary btn-sm mr-1" ng-disabled="pageNum == pageCount" ng-click="goNext()">
                <i class="fa fa-arrow-right"/>
              </button>
              <button type="button" class="btn btn-primary btn-sm mr-1" ng-click="zoomIn()">
                <i class="fa fa-search-plus"/>
              </button>
              <button type="button" class="btn btn-primary btn-sm mr-1" ng-click="fit()">100%</button>
              <button type="button" class="btn btn-primary btn-sm" ng-click="zoomOut()">
                <i class="fa fa-search-minus"/>
              </button>
            </div>
          </div>
        </div>
      </div>
      <div class="card-body">
        <div class="row mt-2" ng-if="loadingData">
          <div class="col-sm-12 text-center">
            <h4 class="text-muted">G&#xE9;n&#xE9;ration du rapport... <small>Veuillez patienter, s'il vous pla&#xEE;t.</small></h4>
            <img src="/io/surati/gap/web/base/img/loader.gif" width="250"/>
          </div>
        </div>
        <div class="mt-2" ng-show="!loadingData">
          <h6 class="text-center pb-1 pt-5" ng-if="hasError">
						L'aper&#xE7;u du rapport ne peut &#xEA;tre affich&#xE9;.
					</h6>
          <div class="row" ng-show="!hasError">
            <div class="col-sm-12 col-md-12">
              <div id="viewer">
                <ng-pdf template-url="/io/surati/gap/maccount/module/html/viewer.html" scale="page-fit"/>
              </div>
            </div>
          </div>
          <div class="row mt-3" ng-show="!hasError">
            <div class="pull-left">
              <a role="button" class="btn btn-primary mr-1" href="{root_page/uri}">Retourner</a>
              <button type="button" class="btn btn-primary mr-1" ng-click="refresh()">Actualiser</button>
            </div>
            <div class="pull-right">
              <button type="button" class="btn btn-primary mr-1" ng-click="print()">Imprimer</button>
              <button type="button" class="btn btn-primary mr-1" ng-click="exportPdf()">Exporter PDF</button>
              <button type="button" class="btn btn-primary" ng-click="exportXls()">Exporter EXCEL</button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </xsl:template>
  <xsl:template match="page" mode="custom-script">
    <script src="/io/surati/gap/maccount/module/js/pdf.combined.js"/>
    <script src="/io/surati/gap/maccount/module/js/angular-pdf.min.js"/>
    <script type="text/javascript"><![CDATA[
        var app = angular.module("app", ['pdf']);

		app.controller("AppCtrl", ["$scope", "$rootScope", "$timeout", "$http", function ($scope, $rootScope, $timeout, $http) {

			// Data
	        $scope.fromWebApi = true;

	        // Methods
	        $scope.print = print;
	        $scope.exportPdf = exportPdf;
	        $scope.exportXls = exportXls;
	        $scope.refresh = refresh;

	        function refresh(){
	        	render({ format: 'PDF' }, function (url) {
	                $scope.pdfUrl = url;
	            });
	        }

	        function exportXls() {
	            render({ format: 'XLS' }, function (url) {
	                window.open(url, "_blank");
	            });
	        }

	        function exportPdf() {
	            window.open($scope.pdfUrl, "_blank");
	        }

	        $scope.onError = function (error) {
	            $scope.loadingData = false;
	        }

	        $scope.onLoad = function () {
	            $scope.loadingData = false;
	        }

	        $scope.onProgress = function (progress) {
	            // handle a progress bar
	            // progress% = progress.loaded / progress.total

	            // console.log(progress);
	        }

	        //////////

	        function print() {
	            render({ format: 'PDF' }, function (url) {
	                window.open(url, "_blank");
	            });
	        }

			function buildReport(url, data, success, failure) {

			    var config = {
	                headers : {
	                    'Content-Type': 'application/json;charset=utf-8;'
	                },
	                responseType: "arraybuffer"
	            };
	            return $http.post(url, data, config).
	                          then(function (response) {
	                              if(success)
	                                success(response.data);
	                          }, function (error, status) {
	                              if (error.status == -1) {
	                                  toastr.error('Connexion au serveur momentanément interrompue.');
	                              } else if (error.status == '401') {
	                                  toastr.error(error);
	                              } else if(error.status == '400') {
	                              	toastr.error(error);
	                              } else if (error.status == '500') {
	                                  toastr.error("An error occured during generation of report. Please contact administrator.");
	                              }

	                              if(failure)
	                                  failure(error);
	                          });
	        }

	        function render(config, callback) {
	            $scope.loadingData = true;

	            if ($scope.fromWebApi) {
	                buildReport($scope.url, config,
	                function (arraybuffer) {
	                	var mimeType = null;
	                    switch (config.format) {
	                        case 'XLS':
	                            mimeType = 'application/vnd.ms-excel';
	                            break;
	                        case 'postscript':
	                            mimeType = 'application/postscript';
	                            break;
	                        default:
	                            mimeType = 'application/pdf';
	                            break;
	                    }

	                    var currentBlob = new Blob([arraybuffer], { type: mimeType });
	                    var url = URL.createObjectURL(currentBlob);

	                    if (callback)
	                        callback(url);

						$scope.loadingData = false;
						$scope.hasError = false;
	                }, function (response) {
	                    $scope.loadingData = false;
	                    $scope.hasError = true;
	                    toastr.error("Error during generating report : " + response.statusText);
	                });
	            } else {
	            	$scope.loadingData = false;
	            	$scope.hasError = false;
	                if (callback)
	                    callback($scope.url);
	            }
	        }

	        $rootScope.$on('print-preview', function(event, args){
	            $scope.url = args.url;

	        	render({ format: 'PDF' }, function (url) {
	                $scope.pdfUrl = url;
	            });

	        });

	        this.$onInit = function () {
	   			$rootScope.$broadcast('print-preview', {url: "]]><xsl:value-of select="report_url"/><![CDATA["});
	        }
		}]);

		angular.bootstrap(document, ['app']);
        ]]></script>
  </xsl:template>
</xsl:stylesheet>
