
export class MUserList {
  public count() : Promise<number>;
	public get(idx:number) : Promise<MUser>;
}
export class MStringList {
  public count() : Promise<number>;
	public get(idx:number) : Promise<string>;
}
export class MUserInfo {
  public name() : Promise<string>;
	public gender() : Promise<string>;
	public avatarDid() : Promise<string>;    
	public aavatarThumb(): Promise<number[]>;   
	public homePage() : Promise<string>;
	public emails() : Promise<MStringList>;
	public telphones() : Promise<MStringList>;
	public intro() : Promise<string>;
}
export class MUser {
  public misesID(): Promise<string>;
	public info():  Promise<MUserInfo>;
	public setInfo(info: MUserInfo):  Promise<string>;
	public getFollowing(appDid: string):  Promise<MStringList>;
	public follow(followingId: string, appDid: string):  Promise<string>;
	public unfollow(unfollowingId: string, appDid: string):  Promise<string>;
	public isRegistered():  Promise<boolean>;
	public register(info: MUserInfo, appDid: string):  Promise<void>;
}
export class MUserMgr {
  public activeUser(): Promise<MUser>;

  public createUser(mnemonic: string, passPhrase: string): Promise<MUser>;

  public listUsers(): Promise<MUserList>;

  public setActiveUser(did: string, passPhrase:string): Promise<void>;
}
export class MSdk {

  public static newSdk(): Promise<MSdk>;
	public static instance(): Promise<MSdk>

  public testConnection(): Promise<void>;
  public setLogLevel(level: number): Promise<void>;
  public userMgr(): Promise<MUserMgr>;
  public randomMnemonics(): Promise<string>;
  
}