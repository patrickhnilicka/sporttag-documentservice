<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:fo="http://www.w3.org/1999/XSL/Format" exclude-result-prefixes="fo">

    <xsl:output method="xml" version="2.0" omit-xml-declaration="no" indent="yes" />
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
                    <fo:block font-size="10pt">
                        <fo:table width="100%" border="0.5px solid">
                            <fo:table-column column-width="6cm" border="0.5px solid" />
                            <xsl:apply-templates select="disziplinen/disziplin" mode="columns" />
                            <fo:table-header>
                                <fo:table-row>
                                    <fo:table-cell border="solid black 0.5px" padding="2px"
                                        font-weight="bold"
                                        text-align="center">
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
        <fo:table-row border="0.5px solid">
            <fo:table-cell>
                <fo:block>
                    <xsl:value-of select="name" />
                </fo:block>
            </fo:table-cell>
        </fo:table-row>
    </xsl:template>

    <xsl:template match="disziplin" mode="columns">
        <xsl:choose>
            <xsl:when test="spalten/spalte">
                <xsl:for-each select="spalten/spalte">
                    <fo:table-column border="0.5px solid" />
                </xsl:for-each>
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
</xsl:stylesheet>