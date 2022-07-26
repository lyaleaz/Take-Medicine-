package my.takeMedDesign.app.cusPanel;

public class Customer {
    private String FirstName,LastName,Password,ConfirmPassword,EmailId,MobileNo,Location,BDate;

    public Customer(){
    }
    // Press Alt+insert

    public Customer(String location, String firstName, String lastName, String password, String confirmPassword, String emailId, String mobileNo, String bdate) {
        FirstName = firstName;
        LastName = lastName;
        Password = password;
        ConfirmPassword = confirmPassword;
        EmailId = emailId;
        MobileNo = mobileNo;
        BDate = bdate;
        Location = location;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getConfirmPassword() {
        return ConfirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        ConfirmPassword = confirmPassword;
    }

    public String getEmailId() {
        return EmailId;
    }

    public void setEmailId(String emailId) {
        EmailId = emailId;
    }

    public String getMobileNo() {
        return MobileNo;
    }

    public void setMobileNo(String mobileNo) {
        MobileNo = mobileNo;
    }

    public String getBDate() {
        return BDate;
    }

    public void setBDate(String bdate) {
        BDate = bdate;
    }


    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }
}
