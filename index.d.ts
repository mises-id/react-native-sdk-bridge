

export class MisesSdk {

  public static newsdk(): Promise<MisesSdk>;

  public testConnection(): Promise<void>;
  public setLogLevel(level: int): Promise<void>;
}