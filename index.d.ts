
export class MUserList {
  public Count() : Promise<number>;
	public Get(idx:number) : Promise<MUser>;
}
export class MStringList {
  public Count() : Promise<number>;
	public Get(idx:number) : Promise<string>;
}
export class MUserInfo {
  public Name() : Promise<string>;
	public Gender() : Promise<string>;
	public AvatarDid() : Promise<string>;    
	public AavatarThumb(): Promise<number[]>;   
	public HomePage() : Promise<string>;
	public Emails() : Promise<MStringList>;
	public Telphones() : Promise<MStringList>;
	public Intro() : Promise<string>;
}
export class MUser {
  public MisesID(): Promise<string>;
	public PubKEY():  Promise<string>;
	public PrivKEY():  Promise<string>;
	public Info():  Promise<MUserInfo>;
	public SetInfo(info: MUserInfo):  Promise<string>;
	public GetFollow(appDid: string):  Promise<MStringList>;
	public SetFollow(followingId: string, op: boolean, appDid: string):  Promise<string>;
	public LoadKeyStore(passPhrase: string):  Promise<void>;
	public IsRegistered():  Promise<boolean>;
	public Register(info: MUserInfo, appDid: string):  Promise<void>;
}
export class MUserMgr {
  public activeUser(): Promise<MUser>;

  public createUser(mnemonic: string, passPhrase: string): Promise<MUser>;

  public listUsers(): Promise<MUserList>;

  public setActiveUser(did: string): Promise<void>;
}
export class MSdk {

  public static newSdk(): Promise<MSdk>;

  public testConnection(): Promise<void>;
  public setLogLevel(level: number): Promise<void>;
  public userMgr(): Promise<MUserMgr>;
  public randomMnemonics(): Promise<string>;
  
}