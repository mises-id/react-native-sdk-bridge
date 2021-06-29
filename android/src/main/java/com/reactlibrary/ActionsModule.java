package com.reactlibrary;

import com.facebook.react.bridge.JavaScriptModule;

public interface  ActionsModule extends JavaScriptModule {
    void callAction(String type, String value);
}
