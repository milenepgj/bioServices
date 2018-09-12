package com.bioinfo.dto;

import java.util.ArrayList;
import java.util.List;

public class Expasy {

    private String ec;
    private String description;
    private String reaction;
    private String brenda;
    private String ec2pdb;
    private String explorEnz;
    private String priam;
    private String kegg;
    private String iubmb;
    private String intEnz;
    private String medline;
    private String metaCyc;
    private List<UniprotSwissProt> uniprotSwissProtData;

    public String getEc() {
        return ec;
    }

    public void setEc(String ec) {
        this.ec = ec;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReaction() {
        return reaction;
    }

    public void setReaction(String reaction) {
        this.reaction = reaction;
    }

    public String getBrenda() {
        return brenda;
    }

    public void setBrenda(String brenda) {
        this.brenda = brenda;
    }

    public String getEc2pdb() {
        return ec2pdb;
    }

    public void setEc2pdb(String ec2pdb) {
        this.ec2pdb = ec2pdb;
    }

    public String getExplorEnz() {
        return explorEnz;
    }

    public void setExplorEnz(String explorEnz) {
        this.explorEnz = explorEnz;
    }

    public String getPriam() {
        return priam;
    }

    public void setPriam(String priam) {
        this.priam = priam;
    }

    public String getKegg() {
        return kegg;
    }

    public void setKegg(String kegg) {
        this.kegg = kegg;
    }

    public String getIubmb() {
        return iubmb;
    }

    public void setIubmb(String iubmb) {
        this.iubmb = iubmb;
    }

    public String getIntEnz() {
        return intEnz;
    }

    public void setIntEnz(String intEnz) {
        this.intEnz = intEnz;
    }

    public String getMedline() {
        return medline;
    }

    public void setMedline(String medline) {
        this.medline = medline;
    }

    public String getMetaCyc() {
        return metaCyc;
    }

    public void setMetaCyc(String metaCyc) {
        this.metaCyc = metaCyc;
    }

    public List<UniprotSwissProt> getUniprotSwissProtData() {
        if (uniprotSwissProtData == null)
            uniprotSwissProtData = new ArrayList<>();
        return uniprotSwissProtData;
    }

    public void setUniprotSwissProtData(List<UniprotSwissProt> uniprotSwissProtData) {
        this.uniprotSwissProtData = uniprotSwissProtData;
    }
}
