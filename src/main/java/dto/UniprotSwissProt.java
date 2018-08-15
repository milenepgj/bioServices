package dto;

public class UniprotSwissProt {
    String id;
    String proteinName;
    String gene;
    String organism;
    String sequence;

    public UniprotSwissProt (){
        super();
    }

    public UniprotSwissProt (String id, String gene, String organism, String proteinName, String sequence){
        super();
        this.id = id;
        this.proteinName = proteinName;
        this.gene = gene;
        this.organism = organism;
        this.sequence = sequence;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGene() {
        return gene;
    }

    public void setGene(String gene) {
        this.gene = gene;
    }

    public String getOrganism() {
        return organism;
    }

    public void setOrganism(String organism) {
        this.organism = organism;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public String getProteinName() {
        return proteinName;
    }

    public void setProteinName(String proteinName) {
        this.proteinName = proteinName;
    }
}
