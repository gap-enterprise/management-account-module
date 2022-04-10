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
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0">
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
                  <li class="breadcrumb-item">
                    <a href="/maccount/bundle-threshold/view">Seuils d'enliassement</a>
                  </li>
                  <li class="active breadcrumb-item" aria-current="page">
                    <xsl:text>Modifier seuils</xsl:text>
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
          <form action="/maccount/bundle-threshold/save" method="post">
            <div class="form-row">
              <div class="col-md-6">
                <div class="position-relative form-group">
                  <label for="code" class="">
                    <xsl:text>Seuil règlements partiels</xsl:text>
                    <span style="color: red"> *</span>
                  </label>
                  <input name="partial_payment" id="partial_payment" value="{item/partial_payment}" type="number" min="1" class="form-control" required=""/>
                </div>
              </div>
              <div class="col-md-6">
                <div class="position-relative form-group">
                  <label for="code" class="">
                    <xsl:text>Seuil règlements totaux</xsl:text>
                    <span style="color: red"> *</span>
                  </label>
                  <input name="total_payment" id="total_payment" value="{item/total_payment}" type="number" min="1" class="form-control" required=""/>
                </div>
              </div>
            </div>
            <div class="divider"/>
            <div class="clearfix">
              <button type="submit" class="btn-shadow btn-wide float-right btn-pill btn-hover-shine btn btn-primary">
                <xsl:text>Enregistrer </xsl:text>
                <i class="fa fa-check"/>
              </button>
              <button type="button" onclick="location.href='/maccount/bundle-threshold/view'" class="btn-shadow float-right btn-wide btn-pill mr-1 btn btn-outline-secondary">
                <xsl:text>Annuler </xsl:text>
                <i class="fa fa-undo"/>
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
  </xsl:template>
  <xsl:template match="page" mode="custom-script"/>
</xsl:stylesheet>
