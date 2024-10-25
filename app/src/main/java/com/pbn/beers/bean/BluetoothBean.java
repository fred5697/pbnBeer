package com.pbn.beers.bean;

/**
 * 藍芽清單的 Item
 */
public class BluetoothBean
{
   private String name;
   private String mac;
   private String preParse;
   private String postParse;
   private String rssi;
   
   public String getName() {
      return name;
   }
   
   public void setName(String name) {
      this.name = name;
   }
   
   public String getMac() {
      return mac;
   }
   
   public void setMac(String mac) {
      this.mac = mac;
   }
   
   public String getPreParse() {
      return preParse;
   }
   
   public void setPreParse(String preParse) {
      this.preParse = preParse;
   }
   
   public String getPostParse() {
      return postParse;
   }
   
   public void setPostParse(String postParse) {
      this.postParse = postParse;
   }
   
   public String getRssi() {
      return rssi;
   }
   
   public void setRssi(String rssi) {
      this.rssi = rssi;
   }
   
}
