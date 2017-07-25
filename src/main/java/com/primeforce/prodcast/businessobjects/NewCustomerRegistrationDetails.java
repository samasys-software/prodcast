package com.primeforce.prodcast.businessobjects;

/**
 * Created by SamayuSoftcorp on 16-03-2017.
 */
public class NewCustomerRegistrationDetails {
    private String Workphone,Firstname,Lastname,Email,Address1,Address2,Address3;
    private String City,State,Country,Postalcode,Smsallowed,Cellphone;
    private Long AccessId;


    public String getworkPhone(){return Workphone;}
    public void setWorkphone(String Workphone){
         this.Workphone=Workphone;
    };
    public String getFirstname(){return Firstname;}
    public void setFirstname(String Firstname){
         this.Firstname=Firstname;
    };
    public String getLastname(){return Lastname;}
    public void setLastname(String Lastname){
        this.Lastname=Lastname;
    };
    public String getEmail(){return Email;}
    public void setEmail(String Email){
        this.Email=Email;
    };
    public String getAddress1(){return Address1;}
    public void setAddress1(String Address1){
        this.Address1=Address1;
    };
    public String getAddress2(){return Address2;}
    public void setAddress2(String Address2){
        this.Address2=Address2;
    };
    public String getAddress3(){return Address3;}
    public void setAddress3(String Address3){
        this.Address3=Address3;
    };
    public String getCity(){return City;}
    public void setCity(String City){
        this.City=City;
    };
    public String getState(){return State;}
    public void setState(String State){
        this.State=State;
    };
    public String getCountry(){return Country;}
    public void setCountry(String Country){
        this.Country=Country;
    };
    public String getPostalcode(){return Postalcode;}
    public void setPostalcode(String Postalcode){
        this.Postalcode=Postalcode;
    };
    public String getSmsallowed(){return Smsallowed;}
    public void setSmsallowed(String Smsallowed){
        this.Smsallowed=Smsallowed;
    };
   /* public long getAccessId(){return AccessId;}
    public void setAccessId(long AccessId){
        this.AccessId=AccessId;
    };*/
    public String getcellPhone(){return Cellphone;}
    public void setCellphone(String Cellphone){
        this.Cellphone=Cellphone;
    };
}
