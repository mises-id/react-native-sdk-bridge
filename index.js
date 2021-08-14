// main index.js

import { NativeModules } from 'react-native';

const { SdkBridge } = NativeModules;


export class MUserInfo {
  constructor(ptr) {
    this._ptr = ptr;
  }
  static async newUserInfo(
    name,gender,avatarDid,
    avatarThumb,homePage,
    emails,telphones,intro)
  {
    const ptr = await SdkBridge.newUserInfo(
      name,gender,avatarDid,
      avatarThumb,homePage,
      emails._ptr,telphones._ptr,intro
    );
    return new MUserInfo(ptr);
  }
  async name() {
    return SdkBridge.userInfoName(this._ptr);
  }

	async gender() {
    return SdkBridge.userInfoGender(this._ptr);
  }
	async avatarDid() {
    return SdkBridge.userInfoAvatarDid(this._ptr);
  }    
  async avatarThumb() {
    return SdkBridge.userInfoAvatarThumb(this._ptr);
  }  
	async homePage() {
    return SdkBridge.userInfoHomePage(this._ptr);
  }
	async emails() {
    const ptr = await SdkBridge.userInfoEmails(this._ptr);
    if (ptr == null) {
      return null;
    }
    return new MStringList(ptr)
  }
	async telphones() {
    const ptr = await  SdkBridge.userInfoTelphones(this._ptr);
    if (ptr == null) {
      return null;
    }
    return new MStringList(ptr)
  }
	async intro() {
    return SdkBridge.userInfoIntro(this._ptr);
  }
}

export class MUser {
  constructor(ptr) {
    this._ptr = ptr;
  }
  async misesID() {
    return SdkBridge.userMisesID(this._ptr);
  }

  async setInfo(info){
    return SdkBridge.userSetInfo(this._ptr, info._ptr);
  }

	async info(){
    const ptr = await  SdkBridge.userInfo(this._ptr);
    if (ptr == null) {
      return null;
    }
    return new MUserInfo(ptr)
  }
	async getFollowing(appDid){
    const ptr = await  SdkBridge.userGetFollowing(this._ptr, appDid);
    if (ptr == null) {
      return null;
    }
    return new MStringList(ptr)
  }
	async follow(followingId, appDid){
    return SdkBridge.userSetFollow(this._ptr, followingId, true, appDid);
  }
  async unfollow(unfollowingId, appDid){
    return SdkBridge.userSetFollow(this._ptr, unfollowingId, false, appDid);
  }
	async isRegistered(){
    return SdkBridge.userIsRegistered(this._ptr);
  }
	async register(appDid){
    return SdkBridge.userRegister(this._ptr, appDid);
  }

}


export class MStringList {
  constructor(ptr) {
    this._ptr = ptr;
  }
  static async newStringList(source, seperator) {
    const ptr = await SdkBridge.newStringList(source, seperator);
    return new MStringList(ptr);
  }
  async count() {
    return SdkBridge.stringListCount(this._ptr);
  }

  async get(idx) {
    return SdkBridge.stringListGet(this._ptr,idx);
  }

}

export class MUserList {
  constructor(ptr) {
    this._ptr = ptr;
  }
  async count() {
    return SdkBridge.userListCount(this._ptr);
  }

  async get(idx) {
    var userPtr = await SdkBridge.userListGet(this._ptr,idx)
    if (userPtr == null) {
      return null;
    }
    return new MUser(userPtr);
  }

}

export class MUserMgr {
  constructor(ptr) {
    this._ptr = ptr;
  }
  async activeUser() {
    const ptr = await SdkBridge.userMgrActiveUser(this._ptr);
    if (ptr == null) {
      return null;
    }
    return new MUser(ptr);
  }

  async createUser(mnemonic, passPhrase) {
    const ptr = await SdkBridge.userMgrCreateUser(this._ptr,mnemonic, passPhrase);
    if (ptr == null) {
      return null;
    }
    return new MUser(ptr);
  }

  async listUsers() {
    const ptr = await SdkBridge.userMgrListUser(this._ptr);
    if (ptr == null) {
      return null;
    }
    return new MUserList(ptr);
  }

  async setActiveUser(did, passPhrase) {
    return SdkBridge.userMgrSetActiveUser(this._ptr, did, passPhrase);
  }
}

export class MSdk {
  constructor(ptr) {
    this._ptr = ptr;
  }

  static async instance() {
    const ptr = await SdkBridge.instance();
    return new MSdk(ptr);
  }

  async setTestEndpoint(endpoint) {
    return SdkBridge.sdkSetTestEndpoint(this._ptr,endpoint);
  }

  async testConnection() {
    return SdkBridge.sdkTestConnection(this._ptr);
  }

  async setLogLevel(level) {
    return SdkBridge.sdkSetLogLevel(this._ptr, level);
  }

  async userMgr() {
    const ptr = await SdkBridge.sdkUserMgr(this._ptr);
    if (ptr == null) {
      return null;
    }
    return new MUserMgr(ptr);
  }
  async randomMnemonics() {
    return SdkBridge.sdkRandomMnemonics(this._ptr);
  }
  async login(site, permissions) {
    return SdkBridge.sdkLogin(this._ptr,site,permissions._ptr);
  }
}

export default SdkBridge;
