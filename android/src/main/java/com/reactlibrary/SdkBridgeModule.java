// SdkBridgeModule.java

package com.reactlibrary;

import android.os.Handler;
import android.util.Base64;
import android.util.Log;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

import mobile.*;

public class SdkBridgeModule extends ReactContextBaseJavaModule {

    static final String TAG = "SdkBridgeModule";

    private final ReactApplicationContext reactContext;
    private final Map<String, Object> pointers;
    private final AtomicLong pointer;
    static final int DEFAULT_THREAD_POOL_SIZE = 4;

    ExecutorService executorService = Executors.newFixedThreadPool(DEFAULT_THREAD_POOL_SIZE);
    private final Map<String, Promise> pendingSessions;
    private Object pendingSessionsMutex = new Object();

    public SdkBridgeModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;

        this.pointers = new HashMap<>();
        this.pointer = new AtomicLong();
        this.pendingSessions = new HashMap<>();
    }

    @Override
    public String getName() {
        return "SdkBridge";
    }

    @ReactMethod
    public void newSdk(Promise promise) {
        tryReact(promise, () -> {
            MSdk sdk = Mobile.newMSdk();
            File dir = reactContext.getFilesDir();
            sdk.setHomePath(dir.getAbsolutePath());
            final String ptr = this.getPointer(sdk);
            startPendingSessionResolver(ptr);
            return ptr;
        });
    }

    @ReactMethod()
    public void sdkTestConnection(String ptr, Promise promise) {
        tryReactWithExecutor(promise, () -> {
            MSdk msdk = SdkBridgeModule.this.getUnretainedObject(ptr);
            msdk.testConnection();
            return null;
        });
    }

    @ReactMethod
    public void sdkSetTestEndpoint(String ptr, String endpoint, Promise promise) {
        tryReact(promise, () -> {
            MSdk msdk = this.getUnretainedObject(ptr);
            msdk.setTestEndpoint(endpoint);
            return null;
        });
    }

    @ReactMethod
    public void sdkLogin(String ptr, String site,String permissionPtr, Promise promise) {
        tryReact(promise, () -> {
            MSdk msdk = this.getUnretainedObject(ptr);
            MStringList list = this.getUnretainedObject(permissionPtr);
            return msdk.login(site, list);
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
            return this.getPointer(msdk.userMgr());
        });
    }
    @ReactMethod
    public void sdkRandomMnemonics(String ptr, Promise promise) {
        tryReact(promise, () -> {
            MSdk msdk = this.getUnretainedObject(ptr);
            return msdk.randomMnemonics();
        });
    }
    @ReactMethod
    public void login(String ptr,String site,MStringList permission, Promise promise) {
        tryReact(promise, () -> {
            MSdk msdk = this.getUnretainedObject(ptr);
            return msdk.login(site,permission);
        });
    }
    @ReactMethod
    public void userMgrActiveUser(String ptr, Promise promise) {
        tryReact(promise, () -> {
            MUserMgr muserMgr = this.getUnretainedObject(ptr);
            MUser user = muserMgr.activeUser();
            return this.getPointer(user);
        });
    }

    @ReactMethod
    public void userMgrCreateUser(String ptr, String mnemonic, String passPhrase, Promise promise) {
        tryReact(promise, () -> {
            MUserMgr muserMgr = this.getUnretainedObject(ptr);

            MUser user = muserMgr.createUser(mnemonic, passPhrase);
            return this.getPointer(user);
        });
    }

    @ReactMethod
    public void userMgrListUser(String ptr, Promise promise) {
        tryReact(promise, () -> {
            MUserMgr muserMgr = this.getUnretainedObject(ptr);
            MUserList userList = muserMgr.listUsers();

            return this.getPointer(userList);
        });
    }

    @ReactMethod
    public void userMgrSetActiveUser(String ptr, String did,String pass,  Promise promise) {
        tryReact(promise, () -> {
            MUserMgr muserMgr = this.getUnretainedObject(ptr);
            muserMgr.setActiveUser(did,pass);
            return null;
        });
    }

    @ReactMethod
    public void userListCount(String ptr,Promise promise) {
        tryReact(promise, () -> {
            MUserList muserList = this.getUnretainedObject(ptr);
            return muserList.count();
        });
    }
    @ReactMethod
    public void userListGet(String ptr, long idx, Promise promise) {
        tryReact(promise, () -> {
            MUserList muserList = this.getUnretainedObject(ptr);
            return this.getPointer(muserList.get(idx));
        });
    }

    @ReactMethod
    public void newStringList(String source, String seperator, Promise promise) {
        tryReact(promise, () -> {
            MStringList list = Mobile.newMStringList(source, seperator);
            return this.getPointer(list);
        });
    }

    @ReactMethod
    public void stringListCount(String ptr,Promise promise) {
        tryReact(promise, () -> {
            MStringList stringList = this.getUnretainedObject(ptr);
            return stringList.count();
        });
    }
    @ReactMethod
    public void stringListGet(String ptr, long idx, Promise promise) {
        tryReact(promise, () -> {
            MStringList stringList = this.getUnretainedObject(ptr);
            return stringList.get(idx);
        });
    }

    @ReactMethod
    public void userMisesID(String ptr, Promise promise) {
        tryReact(promise, () -> {
            MUser muser = this.getUnretainedObject(ptr);
            return muser.misesID();
        });
    }

    @ReactMethod
    public void newUserInfo(String var0, String var1, String var2, String var3, String var4, String emailPtr, String telPtr, String var7, Promise promise) {
        tryReact(promise, () -> {
            byte[] bytes = Base64.decode(var3,  Base64.DEFAULT);
            MStringList listEmail = this.getUnretainedObject(emailPtr);
            MStringList listTel = this.getUnretainedObject(telPtr);
            MUserInfo info = Mobile.newMUserInfo(var0, var1,var2,bytes,var4,listEmail,listTel,var7);
            return this.getPointer(info);
        });
    }

    @ReactMethod
    public void userSetInfo(String ptr, String infoPtr, Promise promise) {
        tryReactWithPendingSession(promise, () -> {
            MUser muser = this.getUnretainedObject(ptr);
            MUserInfo info = this.getUnretainedObject(infoPtr);
            return muser.setInfo(info);
        });
    }

    @ReactMethod
    public void userInfo(String ptr, Promise promise) {
        tryReact(promise, () -> {
            MUser muser = this.getUnretainedObject(ptr);
            MUserInfo info = muser.info();
            return this.getPointer(info);
        });
    }
    @ReactMethod
    public void userGetFollowing(String ptr, String appDid, Promise promise) {
        tryReactWithExecutor(promise, () -> {
            MUser muser = SdkBridgeModule.this.getUnretainedObject(ptr);
            MStringList list = muser.getFollow(appDid);
            return SdkBridgeModule.this.getPointer(list);
        });



    }

    @ReactMethod
    public void userSetFollow(String ptr, String did, boolean op, String appDid,Promise promise) {

        tryReactWithPendingSession(promise, () -> {
            MUser muser = SdkBridgeModule.this.getUnretainedObject(ptr);
            return muser.setFollow(did, op, appDid);
        });
    }

    @ReactMethod
    public void userIsRegistered(String ptr, Promise promise) {

        tryReactWithExecutor(promise, () -> {
            MUser muser = SdkBridgeModule.this.getUnretainedObject(ptr);
            muser.isRegistered();
            return null;
        });

    }
    @ReactMethod
    public void userRegister(String ptr, String appDid,Promise promise) {
        tryReactWithPendingSession(promise, () -> {
            MUser muser = SdkBridgeModule.this.getUnretainedObject(ptr);
            return muser.register(appDid);
        });

    }

    @ReactMethod
    public void userInfoName(String ptr, Promise promise) {
        tryReact(promise, () -> {
            MUserInfo muserinfo = this.getUnretainedObject(ptr);
            return muserinfo.name();
        });
    }
    @ReactMethod
    public void userInfoGender(String ptr, Promise promise) {
        tryReact(promise, () -> {
            MUserInfo muserinfo = this.getUnretainedObject(ptr);
            return muserinfo.gender();
        });
    }

    @ReactMethod
    public void userInfoAvatarDid(String ptr, Promise promise) {
        tryReact(promise, () -> {
            MUserInfo muserinfo = this.getUnretainedObject(ptr);
            return muserinfo.avatarDid();
        });
    }

    @ReactMethod
    public void userInfoAvatarThumb(String ptr, Promise promise) {
        tryReact(promise, () -> {
            MUserInfo muserinfo = this.getUnretainedObject(ptr);
            byte[] bytes =  muserinfo.aavatarThumb();
            return Base64.encode(bytes, Base64.DEFAULT);
        });
    }

    @ReactMethod
    public void userInfoHomePage(String ptr, Promise promise) {
        tryReact(promise, () -> {
            MUserInfo muserinfo = this.getUnretainedObject(ptr);
            return muserinfo.homePage();
        });
    }

    @ReactMethod
    public void userInfoEmails(String ptr, Promise promise) {
        tryReact(promise, () -> {
            MUserInfo muserinfo = this.getUnretainedObject(ptr);
            MStringList list = muserinfo.emails();
            return this.getPointer(list);
        });
    }
    @ReactMethod
    public void userInfoTelphones(String ptr, Promise promise) {
        tryReact(promise, () -> {
            MUserInfo muserinfo = this.getUnretainedObject(ptr);
            MStringList list = muserinfo.telphones();
            return this.getPointer(list);
        });
    }

    @ReactMethod
    public void userInfoIntro(String ptr, Promise promise) {
        tryReact(promise, () -> {
            MUserInfo muserinfo = this.getUnretainedObject(ptr);
            return muserinfo.intro();
        });
    }

    private String getPointer(Object object) {
        if (object == null) {
            return null;
        }
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
    private <T> void tryReactWithExecutor(Promise promise, ExceptionSupplier<T> lambda) {
        executorService.execute(new Runnable(){
            @Override
            public void run(){
                tryReact(promise, lambda);
            }
        });
    }

    private void tryReactWithPendingSession(Promise promise, ExceptionSupplier<String> lambda) {
        executorService.execute(new Runnable(){
            @Override
            public void run(){
                try {
                    final String session = lambda.get();
                    synchronized (pendingSessionsMutex) {
                        pendingSessions.put(session, promise);
                    }
                } catch (Throwable e) {
                    promise.reject(e.getClass().getCanonicalName(), e);
                }
            }
        });

    }
    private void startPendingSessionResolver(final String sdkPtr) {
        executorService.execute(new Runnable(){
            @Override
            public void run(){

                //Log.d(TAG, "running session resolver");
                while (true) {
                    final MSdk msdk = SdkBridgeModule.this.getUnretainedObject(sdkPtr);
                    //Log.d(TAG, "start pollSessionResult");
                    MSessionResult result = msdk.pollSessionResult();
                    if (result != null) {
                        final String session = result.sessionID();
                        Log.d(TAG, "session result " + session);
                        synchronized (pendingSessionsMutex) {
                            if (pendingSessions.containsKey(session)) {
                                final Promise sessPromise = pendingSessions.get(session);
                                if (result.success()) {

                                    sessPromise.resolve(session);
                                } else {
                                    sessPromise.reject(result.sessionID(), result.msg());
                                }
                                pendingSessions.remove(session);
                            }
                        }


                    } else {
                        //Log.d(TAG, "poll time out");
                    }


                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
    }

}
