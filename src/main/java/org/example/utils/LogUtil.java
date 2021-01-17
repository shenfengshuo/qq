package org.example.utils;

import org.apache.log4j.Logger;

public class LogUtil {
    public static void logInfo(Logger loggerme, String log){
        if(loggerme.isInfoEnabled()){
            loggerme.info(log);
        }
    }

//    public static void logWarnning(Logger loggerme, String log){
//        if(loggerme.isWarning){
//            loggerme.isWarnEnabled(log);
//        }
//    }

//    public static void logError(Logger loggerme, String log){
//        if(loggerme.isError){
//            loggerme.isErrorEnabled(log);
//        }
//    }

//    public static void logError(Logger loggerme, String log, Exception e){
//        if(loggerme.isError){
//            loggerme.isErrorEnabled(log,e);
//        }
//    }

    public static void logDebug(Logger loggerme, String log){
        if(loggerme.isDebugEnabled()){
            loggerme.debug(log);
        }
    }

}
