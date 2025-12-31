package principale.model;

public enum Region {
    AUVERGNE_RHONE_ALPES("auvergne_rhone_alpes", "Auvergne-Rhône-Alpes"),
    BOURGOGNE_FRANCHE_COMTE("bourgogne_franche_comte", "Bourgogne-Franche-Comté"),
    BRETAGNE("bretagne", "Bretagne"),
    CENTRE_VAL_DE_LOIRE("centre_val_de_loire", "Centre-Val de Loire"),
    CORSE("corse", "Corse"),
    GRAND_EST("grand_est", "Grand Est"),
    HAUTS_DE_FRANCE("hauts_de_france", "Hauts-de-France"),
    ILE_DE_FRANCE("ile_de_france", "Île-de-France"),
    NORMANDIE("normandie", "Normandie"),
    NOUVELLE_AQUITAINE("nouvelle_aquitaine", "Nouvelle-Aquitaine"),
    OCCITANIE("occitanie", "Occitanie"),
    PAYS_DE_LA_LOIRE("pays_de_la_loire", "Pays de la Loire"),
    PROVENCE_ALPES_COTE_D_AZUR("provence_alpes_cote_azur", "Provence-Alpes-Côte d’Azur");

    private final String code;
    private final String label;

    Region(String code, String label) {
        this.code = code;
        this.label = label;
    }

    public String getCode() { return code; }

    @Override
    public String toString() { return label; }
}

