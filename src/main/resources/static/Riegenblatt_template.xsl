<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:fo="http://www.w3.org/1999/XSL/Format" exclude-result-prefixes="fo">
    <xsl:output method="xml" version="1.0" omit-xml-declaration="no" indent="yes" />
    <xsl:template match="root">
        <fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">
            <fo:layout-master-set>
                <fo:simple-page-master master-name="simpleA4"
                    page-height="21cm" page-width="29.7cm" margin-top="2cm" margin-bottom="2cm"
                    margin-left="1cm" margin-right="1cm">
                    <fo:region-body margin-top="0.5cm" />
                </fo:simple-page-master>
            </fo:layout-master-set>
            <fo:page-sequence master-reference="simpleA4">
                <fo:flow flow-name="xsl-region-body">
                    <fo:block-container absolute-position="fixed" top="2mm" left="10mm">
                        <fo:block>
                            Kantonsschule Im Lee
                        </fo:block>
                        <fo:block>
                            Sporttag <xsl:value-of select="jahr" />
                        </fo:block>
                    </fo:block-container>
                    <fo:block-container absolute-position="fixed" top="5mm" text-align="center">
                        <fo:block font-weight="bold" font-size="20pt">
                            Riegenblatt <xsl:value-of select="riege/name" />
                        </fo:block>
                    </fo:block-container>
                    <fo:block-container absolute-position="fixed" top="5mm" left="262mm">
                        <fo:block font-weight="bold" font-size="20pt">
                            Riege <xsl:value-of select="riege/nr" />
                        </fo:block>
                    </fo:block-container>
                    <fo:block font-size="10pt">
                        <fo:table width="100%" border="0.5px solid">
                            <fo:table-column column-width="6cm" border="0.5px solid" />
                            <xsl:apply-templates select="disziplinen/disziplin" mode="columns" />
                            <fo:table-header>
                                <fo:table-row>
                                    <fo:table-cell border="solid black 0.5px" padding="3px">
                                        <fo:block>Name</fo:block>
                                    </fo:table-cell>
                                    <xsl:apply-templates select="disziplinen/disziplin"
                                        mode="disziplinencolumns" />
                                </fo:table-row>
                            </fo:table-header>
                            <fo:table-body>
                                <xsl:apply-templates
                                    select="students/student" />
                            </fo:table-body>
                        </fo:table>
                    </fo:block>
                </fo:flow>
            </fo:page-sequence>
        </fo:root>
    </xsl:template>
    <xsl:template match="student">
        <fo:table-row border="0.5px solid" height="0.6cm">
            <fo:table-cell number-rows-spanned="2" padding="3px">
                <fo:block font-weight="bold" font-size="12pt">
                    <xsl:value-of select="name" />
                </fo:block>
            </fo:table-cell>
            <xsl:apply-templates select="//root/disziplinen/disziplin" mode="firstrow"/>
        </fo:table-row>
        <fo:table-row border="0.5px solid" height="0.6cm">
            <xsl:apply-templates select="//root/disziplinen/disziplin" mode="secondrow"/>
        </fo:table-row>
    </xsl:template>
    <xsl:template match="disziplin" mode="columns">
        <xsl:choose>
            <xsl:when test="spalten">
                <xsl:apply-templates select="spalten"/>
            </xsl:when>
            <xsl:otherwise>
                <fo:table-column border="0.5px solid" />
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>
    <xsl:template match="disziplin" mode="disziplinencolumns">
        <fo:table-cell border="solid black 0.5px" padding="2px"
            font-weight="bold"
            text-align="center">
            <xsl:if test="spalten">
                <xsl:attribute name="number-columns-spanned">
                    <xsl:value-of
                        select="spalten/anzahl" />
                </xsl:attribute>
            </xsl:if>
            <fo:block font-size="6pt" text-align="center">
                <xsl:value-of select="name" />
            </fo:block>
        </fo:table-cell>
    </xsl:template>
    <xsl:template match="spalten">
        <xsl:for-each select="1 to anzahl">
            <fo:table-column border="0.5px solid" />
        </xsl:for-each>
    </xsl:template>
    <xsl:template match="disziplin" mode="firstrow">
        <xsl:choose>
            <xsl:when test="zweizeilen">
                <xsl:choose>
                    <xsl:when test="spalten">
                        <xsl:for-each select="1 to spalten/anzahl">
                            <fo:table-cell border="0.5px solid" number-rows-spanned="1">
                                <fo:block/>
                            </fo:table-cell>
                        </xsl:for-each>
                    </xsl:when>
                    <xsl:otherwise>
                        <fo:table-cell border="0.5px solid" number-rows-spanned="1">
                            <fo:block/>
                        </fo:table-cell>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:when>
            <xsl:otherwise>
                <xsl:choose>
                    <xsl:when test="spalten">
                        <xsl:for-each select="1 to spalten/anzahl">
                            <fo:table-cell border="0.5px solid" number-rows-spanned="2">
                                <fo:block/>
                            </fo:table-cell>
                        </xsl:for-each>
                    </xsl:when>
                    <xsl:otherwise>
                        <fo:table-cell border="0.5px solid" number-rows-spanned="2">
                            <fo:block/>
                        </fo:table-cell>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>
    <xsl:template match="disziplin" mode="secondrow">
        <xsl:if test="zweizeilen">
            <xsl:choose>
                <xsl:when test="spalten">
                    <xsl:for-each select="1 to spalten/anzahl">
                        <fo:table-cell border="0.5px solid" number-rows-spanned="1">
                            <fo:block/>
                        </fo:table-cell>
                    </xsl:for-each>
                </xsl:when>
                <xsl:otherwise>
                    <fo:table-cell border="0.5px solid" number-rows-spanned="1">
                        <fo:block/>
                    </fo:table-cell>
                </xsl:otherwise>
            </xsl:choose>
        </xsl:if>
    </xsl:template>
</xsl:stylesheet>