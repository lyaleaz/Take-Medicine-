package my.takeMedDesign.app.phMedPanel;

public class MedDetails {
    public String  Medicine,Quantity,Price,Description,ImageURL,RandomUID,emailid;



    public MedDetails(String medicine, String quantity, String price, String descrption, String imageURL, String randomUID, String phemail) {
        Medicine = medicine;
        Quantity = quantity;
        Price = price;
        Description = descrption;
        ImageURL = imageURL;
        RandomUID = randomUID;
        this.emailid = emailid;
    }
}
