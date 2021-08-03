// SdkBridgeModule.java

package com.reactlibrary;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;

import java.io.File;
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
        tryReact(promise, () -> {
            MSdk sdk = Mobile.newMSdk();
            File dir = reactContext.getFilesDir();
            sdk.setHomePath(dir.getAbsolutePath());
            return this.getPointer(sdk);
        });
    }

    @ReactMethod()
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
            muserMgr.setActiveUser(did, pass);
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
    public void userInfo(String ptr, Promise promise) {
        tryReact(promise, () -> {
            MUser muser = this.getUnretainedObject(ptr);
            MUserInfo info = muser.info();
            return this.getPointer(info);
        });
    }
    @ReactMethod
    public void userGetFollowing(String ptr, String appDid, Promise promise) {
        tryReact(promise, () -> {
            MUser muser = this.getUnretainedObject(ptr);
            MStringList list = muser.getFollow(appDid);
            return this.getPointer(list);
        });
    }

    @ReactMethod
    public void userSetFollow(String ptr, String did, boolean op, String appDid,Promise promise) {
        tryReact(promise, () -> {
            MUser muser = this.getUnretainedObject(ptr);
            return  muser.setFollow(did, op, appDid);
        });
    }

    @ReactMethod
    public void userIsRegistered(String ptr, Promise promise) {
        tryReact(promise, () -> {
            MUser muser = this.getUnretainedObject(ptr);
            return  muser.isRegistered();
        });
    }
    @ReactMethod
    public void userRegister(String ptr, String appDid,Promise promise) {
        tryReact(promise, () -> {
            MUser muser = this.getUnretainedObject(ptr);
            muser.register(null, appDid);
            return null;
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
}
