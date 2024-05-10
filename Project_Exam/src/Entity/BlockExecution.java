package Entity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BlockExecution {
    private String idBlock;
    private Date executionDate;

    public BlockExecution(String idBlock, Date executionDate) {
        this.idBlock = idBlock;
        this.executionDate = executionDate;
    }

    public String getIdBlock() {
        return idBlock;
    }

    public void setIdBlock(String idBlock) {
        this.idBlock = idBlock;
    }

    public Date getExecutionDate() {
        return executionDate;
    }

    public void setExecutionDate(Date executionDate) {
        this.executionDate = executionDate;
    }
    
    @Override
    public String toString() {
    	SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        return "Block Code: " + idBlock + ", Date: " + dateFormat.format(executionDate);
    }
}

