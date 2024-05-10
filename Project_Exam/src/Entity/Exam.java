package Entity;

import java.util.Date;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.List;




public class Exam {
    private String idExam;
    private Date startDate;
    private Date endDate;
    private String description;
    private List<BlockExecution> blocks; 

    public Exam(String idExam, Date startDate, Date endDate, String description) {
        this.idExam = idExam;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.blocks = new ArrayList<>();
    }



	public String getIdExam() {
        return idExam;
    }

    public void setIdExam(String idExam) {
        this.idExam = idExam;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<BlockExecution> getBlocks() {
        return blocks;
    }

    public void setBlocks(List<BlockExecution> blocks) {
        this.blocks = blocks;
    }

    public void addBlock(BlockExecution block) {
        this.blocks.add(block);
    }
    
    @Override
    public String toString() {
    	SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        StringBuilder sb = new StringBuilder();
        sb.append("Exam Code: ").append(idExam).append("\n");
        sb.append("Start Date: ").append(dateFormat.format(startDate)).append("\n");
        sb.append("End Date: ").append(dateFormat.format(endDate)).append("\n");
        sb.append("Description: ").append(description).append("\n");
        sb.append("Blocks:\n");
        for (BlockExecution block : blocks) {
            sb.append("\t").append(block.toString()).append("\n");
        }
        return sb.toString();
    }
}
