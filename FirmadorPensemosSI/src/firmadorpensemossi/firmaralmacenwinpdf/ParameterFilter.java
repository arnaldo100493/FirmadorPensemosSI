// Decompiled by DJ v3.12.12.96 Copyright 2011 Atanas Neshkov  Date: 12/12/2017 12:21:44 p. m.
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   ParameterFilter.java

package firmadorpensemossi.firmaralmacenwinpdf;

import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpExchange;
import java.io.*;
import java.net.URI;
import java.net.URLDecoder;
import java.util.*;

public class ParameterFilter extends Filter
{

    public ParameterFilter()
    {
    }

    public String description()
    {
        return "Parses the requested URI for parameters";
    }

    public void doFilter(HttpExchange exchange, com.sun.net.httpserver.Filter.Chain chain)
        throws IOException
    {
        parseGetParameters(exchange);
        parsePostParameters(exchange);
        chain.doFilter(exchange);
    }

    private void parseGetParameters(HttpExchange exchange)
        throws UnsupportedEncodingException
    {
        Map parameters = new HashMap();
        URI requestedUri = exchange.getRequestURI();
        String query = requestedUri.getRawQuery();
        parseQuery(query, parameters);
        exchange.setAttribute("parameters", parameters);
    }

    private void parsePostParameters(HttpExchange exchange)
        throws IOException
    {
        if("post".equalsIgnoreCase(exchange.getRequestMethod()))
        {
            Map parameters = (Map)exchange.getAttribute("parameters");
            InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), "utf-8");
            BufferedReader br = new BufferedReader(isr);
            String query = br.readLine();
            parseQuery(query, parameters);
        }
    }

    private void parseQuery(String query, Map parameters)
        throws UnsupportedEncodingException
    {
        if(query != null)
        {
            String pairs[] = query.split("[&]");
            String as[] = pairs;
            int i = as.length;
            for(int j = 0; j < i; j++)
            {
                String pair = as[j];
                String param[] = pair.split("[=]");
                String key = null;
                String value = null;
                if(param.length > 0)
                    key = URLDecoder.decode(param[0], System.getProperty("file.encoding"));
                if(param.length > 1)
                    value = URLDecoder.decode(param[1], System.getProperty("file.encoding"));
                if(parameters.containsKey(key))
                {
                    Object obj = parameters.get(key);
                    if(obj instanceof List)
                    {
                        List values = (List)obj;
                        values.add(value);
                        continue;
                    }
                    if(obj instanceof String)
                    {
                        List values = new ArrayList();
                        values.add((String)obj);
                        values.add(value);
                        parameters.put(key, values);
                    }
                } else
                {
                    parameters.put(key, value);
                }
            }

        }
    }
}