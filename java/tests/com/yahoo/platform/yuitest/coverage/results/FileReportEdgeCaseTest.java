/*
 *  YUI Test
 *  Author: Nicholas C. Zakas <nzakas@yahoo-inc.com>
 *  Copyright (c) 2009, Yahoo! Inc. All rights reserved.
 *  Code licensed under the BSD License:
 *      http://developer.yahoo.net/yui/license.txt
 */

package com.yahoo.platform.yuitest.coverage.results;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author nzakas
 */
public class FileReportEdgeCaseTest {

    private FileCoverageReport report;


    public FileReportEdgeCaseTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws IOException, JSONException {
        Reader in = new InputStreamReader(SummaryReportTest.class.getResourceAsStream("coverage4.json"));
        SummaryCoverageReport summaryReport = new SummaryCoverageReport(in);
        report = summaryReport.getFileReport(0);
    }

    @After
    public void tearDown() {
        report = null;
    }

    /**
     * Test of getFunctions method, of class FileCoverageReport.
     */
    @Test
    public void testGetFunctionNames_returns_null() throws Exception {
        String[] result = report.getFunctionNames();
	assertNull(result);
    }

    /**
     * Test of merge() method.
     */
    @Test
    public void testMerge() throws JSONException {

        //get clone of JSONObject
        JSONObject clone = new JSONObject(report.toJSONObject().toString());
        clone.put("calledLines", 110);
        clone.getJSONObject("lines").put("207", 1);

        FileCoverageReport newReport = new FileCoverageReport("cookie.js", clone);
        report.merge(newReport);

	assertEquals(0, report.toJSONObject().getJSONObject("functions").length());
        assertEquals(110, report.getCalledLineCount());
    }
}
