package jotato.quantumflux;


import org.apache.logging.log4j.Level;

import cpw.mods.fml.common.FMLLog;

public class Logger {
	
	private static final String CHANNEL = "QuantumFlux";

    public static void log(Level level, String format, Object... data)
    {
    	FMLLog.log(level, format, data);
    }

    public static void fatal(String format, Object... data)
    {
        log(Level.FATAL, format, data);
    }

    public static void error(String format, Object... data)
    {
        log(Level.ERROR, format, data);
    }

    public static void warning(String format, Object... data)
    {
        log(Level.WARN, format, data);
    }

    public static void info(String format, Object... data)
    {
        log(Level.INFO, format, data);
    }

    public static void debug(String format, Object... data)
    {
        log(Level.DEBUG, format, data);
    }

    public static void trace(String format, Object... data)
    {
        log(Level.TRACE, format, data);
    }
    }