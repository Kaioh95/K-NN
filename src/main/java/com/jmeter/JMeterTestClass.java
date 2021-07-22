package com.jmeter;

import com.app.Main;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

import java.io.Serializable;

public class JMeterTestClass extends AbstractJavaSamplerClient implements Serializable {
    private static final long serialVersionUID = 1L;

    @Override
    public SampleResult runTest(JavaSamplerContext javaSamplerContext) {
        SampleResult result = new SampleResult();
        result.sampleStart();
        result.setSampleLabel("Test Main KNN");

        Main knnMain = new Main();
        try{
            knnMain.main(null);

            result.sampleEnd();
            result.setSuccessful(true);
            result.setResponseMessage("Successfully performed action");
            result.setResponseCodeOK();
        } catch (Exception e){
            result.sampleEnd();
            result.setSuccessful(false);
            result.setResponseMessage("Exception: " + e);

            java.io.StringWriter stringWriter = new java.io.StringWriter();
            e.printStackTrace( new java.io.PrintWriter( stringWriter ) );
            result.setResponseData(stringWriter.toString(), "utf-8");
            result.setDataType( org.apache.jmeter.samplers.SampleResult.TEXT );
            result.setResponseCode( "500" );
        }

        return result;
    }
}
