package com.bioinfo.dto;

public class Fasta {

    private String proteinId;
    private String blastHit;
    private String entryKeggBlastHit;
    private String eValue;

    public String getProteinId() {
        return proteinId;
    }

    public void setProteinId(String proteinId) {
        this.proteinId = proteinId;
    }

    public String getBlastHit() {
        return blastHit;
    }

    public void setBlastHit(String blastHit) {
        this.blastHit = blastHit;
    }

    public String getEntryKeggBlastHit() {
        return entryKeggBlastHit;
    }

    public void setEntryKeggBlastHit(String entryKeggBlastHit) {
        this.entryKeggBlastHit = entryKeggBlastHit;
    }

    public String geteValue() {
        return eValue;
    }

    public void seteValue(String eValue) {
        this.eValue = eValue;
    }
}
