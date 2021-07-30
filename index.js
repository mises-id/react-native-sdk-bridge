// main index.js

import { NativeModules } from 'react-native';

const { SdkBridge } = NativeModules;


export class MSdk {
  constructor(ptr) {
    this._ptr = ptr;
  }

  static async newSdk() {
    const ptr = await SdkBridge.newSdk();
    return new MSdk(ptr);
  }

  testConnection() {
    return SdkBridge.sdkTestConnection(this._ptr);
  }

  setLogLevel(level) {
    return SdkBridge.sdkSetLogLevel(this._ptr, level);
  }

  userMgr() {
    return SdkBridge.sdkUserMgr(this._ptr);
  }
  randomMnemonics() {
    return SdkBridge.sdkRandomMnemonics(this._ptr);
  }
}

export default SdkBridge;
