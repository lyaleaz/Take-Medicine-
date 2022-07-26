package my.takeMedDesign.app;

public class UpdateMedModel {

    String Medicine,RandomUID,Description,Quantity,Price,ImageURL;

    public UpdateMedModel(){

    }
    public UpdateMedModel(String medicine, String randomUID, String description, String quantity, String price, String imageURL) {
        Medicine = medicine;
        RandomUID = randomUID;
        Description = description;
        Quantity = quantity;
        Price = price;
        ImageURL = imageURL;
    }

    public void setMedicine(String medicine) {
        Medicine = medicine;
    }
    public String getMedicine() {
        return Medicine;
    }
    public String getRandomUID() {
        return RandomUID;
    }

    public void setRandomUID(String randomUID) {
        RandomUID = randomUID;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getImageURL() {
        return ImageURL;
    }

    public void setImageURL(String imageURL) {
        ImageURL = imageURL;
    }

}