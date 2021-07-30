// SdkBridgeModule.java

package com.reactlibrary;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import mobile.*;

public class SdkBridgeModule extends ReactContextBaseJavaModule {

    private final ReactApplicationContext reactContext;
    private final Map<String, Object> pointers;
    private final AtomicLong pointer;

    public SdkBridgeModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;

        this.pointers = new HashMap<>();
        this.pointer = new AtomicLong();
    }

    @Override
    public String getName() {
        return "SdkBridge";
    }

    @ReactMethod
    public void newSdk(Promise promise) {
        tryReact(promise, () ->
                this.getPointer(Mobile.newMSdk())
        );
    }

    @ReactMethod
    public void sdkTestConnection(String ptr, Promise promise) {
        tryReact(promise, () -> {
            MSdk msdk = this.getUnretainedObject(ptr);
            msdk.testConnection();
            return null;
        });
    }

    @ReactMethod
    public void sdkSetLogLevel(String ptr, long level, Promise promise) {
        tryReact(promise, () -> {
            MSdk msdk = this.getUnretainedObject(ptr);
            msdk.setLogLevel(level);
            return null;
        });
    }

    @ReactMethod
    public void sdkUserMgr(String ptr, Promise promise) {
        tryReact(promise, () -> {
            MSdk msdk = this.getUnretainedObject(ptr);
            return msdk.userMgr();
        });
    }
    @ReactMethod
    public void sdkRandomMnemonics(String ptr, Promise promise) {
        tryReact(promise, () -> {
            MSdk msdk = this.getUnretainedObject(ptr);
            return msdk.randomMnemonics();
        });
    }

    private String getPointer(Object object) {
        long index = pointer.incrementAndGet();
        String ptr = Long.toHexString(index);
        pointers.put(ptr, object);
        return ptr;
    }

    private <T> T getRetainedObject(String ptr) throws Error, ClassCastException {
        T object = getUnretainedObject(ptr);
        pointers.remove(ptr);
        return object;
    }

    @SuppressWarnings("unchecked")
    private <T> T getUnretainedObject(String ptr) throws Error, ClassCastException {
        Object obj = pointers.get(ptr);
        if (obj == null) {
            throw new Error("Object not found: "+ptr);
        }
        return (T)obj;
    }
    private <T> void tryReact(Promise promise, ExceptionSupplier<T> lambda) {
        try {
            T val = lambda.get();
            promise.resolve(val);
        } catch (Throwable e) {
            promise.reject(e.getClass().getCanonicalName(), e);
        }
    }
}
