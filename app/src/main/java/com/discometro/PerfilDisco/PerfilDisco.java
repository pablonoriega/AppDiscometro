package com.discometro.PerfilDisco;

public class PerfilDisco {

    private String nameDisco;

    private String ib_1;
    private String ib_2;
    private String ib_3;
    private String ib_4;
    private String fab_msg;
    private String fab_items;
    private String fab_favs;
    private String fab_subs;
    private String foto1;
    private String foto2;
    private String foto3;
    private String foto4;

    private String activity_perfil;
    private String logo;

    private String correo;

    public PerfilDisco(String nameDisco,String logo,String activity_perfil, String ib_1, String ib_2, String ib_3, String ib_4, String fab_msg, String fab_items, String fab_favs, String fab_subs,String foto1,String foto2,String foto3,String foto4, String correo) {

        this.nameDisco = nameDisco;
        this.logo = logo;
        this.activity_perfil=activity_perfil;
        this.ib_1 = ib_1;
        this.ib_2 = ib_2;
        this.ib_3 = ib_3;
        this.ib_4=ib_4;
        this.fab_msg = fab_msg;
        this.fab_items = fab_items;
        this.fab_favs = fab_favs;
        this.fab_subs = fab_subs;
        this.foto1=foto1;
        this.foto2=foto2;
        this.foto3=foto3;
        this.foto4=foto4;
        this.correo=correo;
    }


    public String getNameDisco() {
        return nameDisco;
    }

    public String getIb_4() {
        return ib_4;
    }

    public String getCorreo() {
        return correo;
    }

    public String getActivity_perfil() {
        return activity_perfil;
    }

    public String getIb_1() {
        return ib_1;
    }

    public String getIb_2() {
        return ib_2;
    }

    public String getIb_3() {
        return ib_3;
    }

    public String getFab_msg() {
        return fab_msg;
    }

    public String getFab_items() {
        return fab_items;
    }

    public String getFab_favs() {
        return fab_favs;
    }

    public String getFab_subs() {
        return fab_subs;
    }

    public String getLogo() {
        return logo;
    }

    public String getFoto1() {
        return foto1;
    }

    public String getFoto2() {
        return foto2;
    }

    public String getFoto3() {
        return foto3;
    }

    public String getFoto4() {
        return foto4;
    }

    public void setNameDisco(String nameDisco) {
        this.nameDisco = nameDisco;
    }
}
