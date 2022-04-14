<?xml version="1.0" encoding="UTF-8"?>
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
    <title><xsl:text>GAP</xsl:text> - <xsl:value-of select="root_page/title"/> - <xsl:value-of select="root_page/subtitle"/>
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
    <div class="main-card mb-3 card card-body" app="app" ng-controller="AppCtrl as vm">
      <div class="card-header">
        <div class="card-header-title font-size-lg text-capitalize font-weight-normal">
          <xsl:value-of select="root_page/subtitle"/>
        </div>
      </div>
      <div class="card-body">
        <div class="row mt-2">
          <div class="col-sm-12 col-md-4">
            <div class="d-flex align-items-center">
              <label class="col-md-4">Paierie:</label>
              <select class="col-md-8 custom-select custom-select-sm form-control form-control-sm" aria-controls="example" ng-model="vm.treasuryId" ng-model-options="{{ debounce: 500 }}" ng-change="vm.search()">
                <option ng-repeat="item in vm.treasuries" value="{{{{item.id}}}}">{{item.name}}</option>
              </select>
            </div>
          </div>
          <div class="col-sm-12 col-md-4">
            <div class="d-flex align-items-center">
              <label class="col-md-4">Année:</label>
              <select class="col-md-8 custom-select custom-select-sm form-control form-control-sm" aria-controls="example" ng-model="vm.yearId" ng-model-options="{{ debounce: 500 }}">
                <option ng-repeat="item in vm.years" value="{{{{item.id}}}}">{{item.id}}</option>
              </select>
            </div>
          </div>
        </div>
        <div class="mt-2">
          <div class="row">
            <div class="col-sm-12 col-md-6">
              <div class="table-responsive">
                <table class="table table-hover table-striped table-bordered table-sm dataTable dtr-inline">
                  <thead>
                    <tr>
                      <th>N°</th>
                      <th>Rapport</th>
                      <th>Actions</th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr>
                      <td>1</td>
                      <td>Compte de gestion</td>
                      <td>
                        <div role="group">
                          <a href="/maccount/management-account/preview?treasury={{{{vm.treasuryId}}}}&amp;year={{{{vm.yearId}}}}&amp;{root_page/full}" class="mb-1 mr-1 btn btn-xs btn-outline-primary">
                            <i class="fa fa-print"/>
                          </a>
                        </div>
                      </td>
                    </tr>
                    <tr>
                      <td>2</td>
                      <td>Détails des dépenses budgétaires</td>
                      <td>
                        <div role="group">
                          <a href="/maccount/budget-expenditure-details/preview?treasury={{{{vm.treasuryId}}}}&amp;year={{{{vm.yearId}}}}&amp;{root_page/full}" class="mb-1 mr-1 btn btn-xs btn-outline-primary">
                            <i class="fa fa-print"/>
                          </a>
                        </div>
                      </td>
                    </tr>
                  </tbody>
                </table>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </xsl:template>
  <xsl:template match="page" mode="custom-script">
    <script type="text/javascript"><![CDATA[
				var app = angular.module("app", ['ui.bootstrap']);

				app.controller("AppCtrl", ["$scope", "$http", "$httpParamSerializerJQLike", function ($scope, $http, $httpParamSerializerJQLike) {
					   var vm = this;

                       vm.treasuries = [
			                    ]]><xsl:for-each select="treasuries/treasury">
                                  {
                                  id: "<xsl:value-of select="id"/>",
                                  name: "<xsl:value-of select="abbreviated"/>"
                                  },
                                </xsl:for-each><![CDATA[
			           ];

					   vm.years = [
			                    ]]><xsl:for-each select="budget_years/budget_year">
                                {
                                id: '<xsl:value-of select="."/>'
                                },
                      </xsl:for-each><![CDATA[
					   ];

					   this.$onInit = function(){
					   	    vm.yearId = vm.years[0].id;
					   	    vm.treasuryId = vm.treasuries[0].id;
					   };
			    }]);

				angular.bootstrap(document, ['app']);
        ]]></script>
  </xsl:template>
</xsl:stylesheet>
