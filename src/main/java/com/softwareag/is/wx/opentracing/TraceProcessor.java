package com.softwareag.is.wx.opentracing;

import com.wm.app.b2b.server.BaseService;
import com.wm.app.b2b.server.invoke.InvokeChainProcessor;
import com.wm.app.b2b.server.invoke.InvokeManager;
import com.wm.app.b2b.server.invoke.ServiceStatus;
import com.wm.util.FacilityInfo;
import com.wm.util.JournalLogger;
import com.wm.util.ServerException;

import java.util.Iterator;

/*
    https://github.com/SoftwareAG/webmethods-integrationserver-skyprofiler/blob/master/source/ISPackage/SKYProfilerRuntime/src/com/softwareag/skyprofiler/ServiceInvokeProcessor.java
 */

public class TraceProcessor implements InvokeChainProcessor {

    private static final String COMPONENT = "[TraceProcessor]";

    private static final TraceProcessor traceProcessor;

    static {
        traceProcessor = new TraceProcessor();
    }

    private TraceProcessor() {
    }

    public static void startup() {

        InvokeManager invokeManager = InvokeManager.getDefault();

        JournalLogger.log(4, JournalLogger.FAC_FLOW_SVC, JournalLogger.DEBUG, COMPONENT, "startup()");
        invokeManager.registerProcessor(traceProcessor);

    }

    public static void shutdown() {

        InvokeManager invokeManager = InvokeManager.getDefault();

        JournalLogger.log(4, JournalLogger.FAC_FLOW_SVC, JournalLogger.DEBUG, COMPONENT, "shutdown()");
        invokeManager.unregisterProcessor(traceProcessor);

    }

    @Override
    public void process(Iterator processorChain, BaseService baseService, com.wm.data.IData pipeline, ServiceStatus serviceStatus) throws ServerException {

        boolean topLevelService = serviceStatus.isTopService();

        JournalLogger.log(4, JournalLogger.FAC_FLOW_SVC, JournalLogger.DEBUG, COMPONENT, baseService.getNSName().toString() + (topLevelService ? " top" : ""));

        if (topLevelService == true) {

        }

        try {
            if (processorChain.hasNext()) {
                ((InvokeChainProcessor) processorChain.next()).process(processorChain, baseService, pipeline, serviceStatus);
            }
        } finally {

        }
    }
}
