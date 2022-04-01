<?xml version="1.0" encoding="utf-8"?>
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
      <xsl:text>GAP - Compte de gestion - Seuils d'enliassement</xsl:text>
    </title>
  </xsl:template>
  <xsl:template match="page" mode="header">
    <div class="app-page-title app-page-title-simple">
      <div class="page-title-wrapper">
        <div class="page-title-heading">
          <div class="page-title-icon">
            <i class="lnr-database icon-gradient bg-night-fade"/>
          </div>
          <div>
            <xsl:text>Seuils d'enliassement</xsl:text>
            <div class="page-title-subheading opacity-10">
              <nav aria-label="breadcrumb">
                <ol class="breadcrumb">
                  <li class="breadcrumb-item">
                    <a href="/home">
                      <i aria-hidden="true" class="fa fa-home"/>
                    </a>
                  </li>
                  <li class="active breadcrumb-item" aria-current="page">
                    <xsl:text>Seuils d'enliassement</xsl:text>
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
        <div class="card-body">
          <div class="row">
            <div class="col-md-6">
              <h5>
                <xsl:text>Seuil réglements partiels</xsl:text>
              </h5>
              <p>
                <xsl:value-of select="item/partial_payment"/>
              </p>
            </div>
            <div class="col-md-6">
              <h5>
                <xsl:text>Seuil réglements totaux</xsl:text>
              </h5>
              <p>
                <xsl:value-of select="item/total_payment"/>
              </p>
            </div>
          </div>
          <div class="divider"/>
          <div class="clearfix">
            <xsl:if test="sec:hasAccess(.,'CONFIGURER_SEUILS_ENLIASSEMENT')">
              <a href="/maccount/bundle-threshold/edit?id={item/id}" class="btn-shadow btn-wide float-right btn-pill mr-1 btn-hover-shine btn btn-primary">
                <xsl:text>Modifier </xsl:text>
                <i class="fa fa-edit"/>
              </a>
            </xsl:if>
          </div>
        </div>
      </div>
    </div>
  </xsl:template>
  <xsl:template match="page" mode="custom-script"/>
</xsl:stylesheet>
