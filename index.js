// main index.js

import { NativeModules } from 'react-native';

const { SdkBridge } = NativeModules;


export class MisesSdk {
  constructor(ptr) {
    this._ptr = ptr;
  }

  static async newsdk() {
    const ptr = await SdkBridge.newSdk();
    return new MisesSdk(ptr);
  }

  testConnection() {
    return SdkBridge.sdkTestConnection(this._ptr);
  }

  setLogLevel(level) {
    return SdkBridge.sdkSetLogLevel(this._ptr, level);
  }

}

export default SdkBridge;
