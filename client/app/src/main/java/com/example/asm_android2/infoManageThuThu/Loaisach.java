package com.example.asm_android2.infoManageThuThu;

public class Loaisach {
    private String masach;
    private String loaisach;
    private int idthuthu;

    public Loaisach(String masach, String loaisach, int idthuthu) {
        this.masach = masach;
        this.loaisach = loaisach;
        this.idthuthu = idthuthu;
    }


    public String getMasach() {
        return masach;
    }

    public void setMasach(String masach) {
        this.masach = masach;
    }

    public String getLoaisach() {
        return loaisach;
    }

    public void setLoaisach(String loaisach) {
        this.loaisach = loaisach;
    }
}
