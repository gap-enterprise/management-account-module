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
    <title>
      <xsl:text>GAP - Paiements</xsl:text>
    </title>
    <link rel="stylesheet" href="/io/surati/gap/web/base/css/print.min.css"/>
    <link rel="stylesheet" href="/io/surati/gap/web/base/css/toastr-2.1.4.min.css"/>
  </xsl:template>
  <xsl:template match="page" mode="header">
    <div class="app-page-title app-page-title-simple">
      <div class="page-title-wrapper">
        <div class="page-title-heading">
          <div class="page-title-icon">
            <i class="lnr-inbox icon-gradient bg-night-fade"/>
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
                  <li class="breadcrumb-item">
                    <a href="{root_page/uri}">
                      <xsl:value-of select="root_page/subtitle"/>
                    </a>
                  </li>
                  <li class="active breadcrumb-item" aria-current="page">
                    <xsl:text>Visualiser une sous-liasse</xsl:text>
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
    <div class="main-card mb-3 card">
      <div class="card">
        <div class="card-body" ng-controller="printCtrl">
          <div class="form-row">
            <div class="col-md-4">
              <div class="position-relative form-group">
                <h5>
                  <xsl:text>Date de création</xsl:text>
                </h5>
                <p>
                  <xsl:value-of select="item/date_view"/>
                </p>
              </div>
            </div>
            <div class="col-md-4">
              <div class="position-relative form-group">
                <h5>
                  <xsl:text>Année budgétaire</xsl:text>
                </h5>
                <p>
                  <xsl:value-of select="item/year"/>
                </p>
              </div>
            </div>
            <div class="col-md-4">
              <div class="position-relative form-group">
                <h5>
                  <xsl:text>Titre</xsl:text>
                </h5>
                <p>
                  <xsl:value-of select="item/title_fullname"/>
                </p>
              </div>
            </div>
            <div class="col-md-4">
              <div class="position-relative form-group">
                <h5>
                  <xsl:text>Section</xsl:text>
                </h5>
                <p>
                  <xsl:value-of select="item/section_fullname"/>
                </p>
              </div>
            </div>
            <div class="col-md-4">
              <div class="position-relative form-group">
                <h5>
                  <xsl:text>Liasse</xsl:text>
                </h5>
                <p><xsl:value-of select="item/bundle"/> | <xsl:value-of select="item/order"/>
                </p>
              </div>
            </div>
            <div class="col-md-4">
              <div class="position-relative form-group">
                <h5>
                  <xsl:text>Montant total payé</xsl:text>
                </h5>
                <p>
                  <xsl:value-of select="item/total_amount_paid_in_human"/>
                </p>
              </div>
            </div>
            <div class="col-md-4">
              <div class="position-relative form-group">
                <h5>
                  <xsl:text>Nombre de mandats enliassés</xsl:text>
                </h5>
                <p>
                  <xsl:value-of select="item/number_of_warrants"/>
                </p>
              </div>
            </div>
            <div class="col-md-12">
              <div class="card-header">
                <div class="card-header-title font-size-lg text-capitalize font-weight-normal">
                  <xsl:text>Mandats enliassés</xsl:text>
                </div>
              </div>
              <div class="table-responsive">
                <table class="align-middle text-truncate mb-0 table table-borderless table-hover">
                  <thead>
                    <tr>
                      <th class="text-center">N°</th>
                      <th class="text-center">Date</th>
                      <th class="text-center">Référence</th>
                      <th class="text-center">Bénéficiaire</th>
                      <th class="text-center">Montant payé (FCFA)</th>
                    </tr>
                  </thead>
                  <tbody>
                    <xsl:for-each select="annual_warrants/annual_warrant">
                      <tr>
                        <td class="text-center text-muted" style="width: 80px;">
                          <xsl:value-of select="position()"/>
                          <input name="order_id" type="hidden" value="{id}"/>
                        </td>
                        <td class="text-center" style="width: 80px;">
                          <xsl:value-of select="date_view"/>
                        </td>
                        <td class="text-center">
                          <xsl:value-of select="reference"/>
                        </td>
                        <td class="text-center">
                          <xsl:value-of select="beneficiary"/>
                        </td>
                        <td class="text-center">
                          <xsl:value-of select="annual_amount_to_pay_in_human"/>
                        </td>
                      </tr>
                    </xsl:for-each>
                  </tbody>
                </table>
              </div>
            </div>
          </div>
          <div class="divider"/>
          <div class="clearfix">
            <a class="btn-shadow float-right btn-wide btn-pill mr-1 btn btn-outline-secondary" href="{root_page/uri}">
              <xsl:text>Retourner </xsl:text>
              <i class="fa fa-arrow-left"/>
            </a>
          </div>
        </div>
      </div>
    </div>
  </xsl:template>
  <xsl:template match="page" mode="custom-script">
    <script type="text/javascript"><![CDATA[
        var app = angular.module("app", []);
				                 
		app.controller("printCtrl", ["$scope", "$rootScope", "$timeout", "$http", function ($scope, $rootScope, $timeout, $http) {
	        
	        this.$onInit = function () {
   			
	        }
		}]);
		
		angular.bootstrap(document, ['app']);
        ]]></script>
  </xsl:template>
</xsl:stylesheet>
