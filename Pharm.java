package my.takeMedDesign.app.phMedPanel;

public class Pharm {
    private  String passward,emailid,RandomUID;
    public Pharm(String Passward,String Emaiid,String randomUID)
    {
        passward=Passward;
        emailid=Emaiid;
        RandomUID=randomUID;
    }
public Pharm(){

}

    public  String getPassward()
    {
        return passward;
    }
    public  String getEmailid()
    {
        return  emailid;
    }

    public String getRandomUID() {
        return RandomUID;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public void setPassward(String passward) {
        this.passward = passward;
    }

    public void setRandomUID(String randomUID) {
        RandomUID = randomUID;
    }
}
