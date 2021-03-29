package model.dto;

public class ClientDto {
    private String name;
    private String address;
    private int cnp;


    public ClientDto(String name, String address, int cnp) {
        this.name = name;
        this.address = address;
        this.cnp = cnp;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getCnp() {
        return cnp;
    }

    public void setCnp(int cnp) {
        this.cnp = cnp;
    }


}
