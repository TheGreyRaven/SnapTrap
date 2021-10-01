package com.snaptrap.libs;
import java.util.HashMap;

public class SnapchatData {

    // Nested ghetto stuff, should shorten this down
    static public HashMap<String, HashMap> versions = new HashMap<String, HashMap>() {{
        put("11.45.0.38", new HashMap<String, String>() {{
            put("className", "QU7");
            put("methodName", "b");
            put("parameterTypesAndCallback", "PU7");
        }});

        put("11.46.0.33", new HashMap<String, String>() {{
            put("className", "le8");
            put("methodName", "b");
            put("parameterTypesAndCallback", "ke8");
        }});

        put("11.47.0.36", new HashMap<String, String>() {{
            put("className", "qf8");
            put("methodName", "b");
            put("parameterTypesAndCallback", "pf8");
        }});
    }};
}
