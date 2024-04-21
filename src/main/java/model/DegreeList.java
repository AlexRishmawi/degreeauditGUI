package model;
import java.util.ArrayList;
import java.util.UUID;
/**
 * @author Alex Rishmawi
 */
public class DegreeList {
    private static DegreeList degreeList;
    private ArrayList<Degree> degrees;
    private DegreeList() {
        degrees = DataReader.loadDegree();
    }

    /**
     * Gets instance of DegreeList
     * @return degreeList
     */
    public static DegreeList getInstance() {
        return degreeList != null ? degreeList : new DegreeList();
    }

    /**
     * Returns degree from degree list by id
     * @param id
     * @return Degree
     */
    public Degree getDegree(UUID id) {
        for(Degree degree: degrees) {
            if(degree.getID().equals(id)) {
                return degree;
            }
        }
        return null;
    }

    public boolean writeToFile() {
        DataWriter.writeDegree(this.degrees);
        return true;
    }

    public boolean addDegree(Degree degree) {
        this.degrees.add(degree);
        return this.degrees.contains(degree);
    }

    /**
     * Prints DegreeList
     * @return 
     */
    public ArrayList<Degree> getAllDegree() {
        return this.degrees;
    }
}
