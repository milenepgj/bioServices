package com.bioinfo.dto;

public class KEGGData {

    private String ec;
    private String proteinId;
    private String blastHitId;
    private String entry;
    private String definition;
    private String orthologyKo;
    private String organism;
    private String pathway;

    public String getEc() {
        return ec;
    }

    public void setEc(String ec) {
        this.ec = ec;
    }

    public String getProteinId() {
        return proteinId;
    }

    public void setProteinId(String proteinId) {
        this.proteinId = proteinId;
    }

    public String getBlastHitId() {
        return blastHitId;
    }

    public void setBlastHitId(String blastHitId) {
        this.blastHitId = blastHitId;
    }

    public String getEntry() {
        return entry;
    }

    public void setEntry(String entry) {
        this.entry = entry;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public String getOrthologyKo() {
        return orthologyKo;
    }

    public void setOrthologyKo(String orthologyKo) {
        this.orthologyKo = orthologyKo;
    }

    public String getOrganism() {
        return organism;
    }

    public void setOrganism(String organism) {
        this.organism = organism;
    }

    public String getPathway() {
        return pathway;
    }

    public void setPathway(String pathway) {
        this.pathway = pathway;
    }
}
