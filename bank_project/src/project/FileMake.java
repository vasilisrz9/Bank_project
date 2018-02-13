package project;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileMake {

    private File output;
    private String filename;
    private BankAccount bc;
    private BufferedWriter bw;

    public void setBc(BankAccount bc) {
        this.bc = bc;
    }

    public void setFilename() {
        this.filename = bc.get_Filename();
        this.output = new File(this.filename);
    }

    public void openFile() throws IOException {
        this.bw = new BufferedWriter(new FileWriter(this.output, true));
    }

    private void writeToFile() throws IOException {

        this.bw.write(bc.toString());
        this.bw.newLine();

    }

    public boolean closeFile() throws IOException {
        this.bw.close();
        return true;
    }

    public void print() {
        System.out.println(bc);
        try {
            writeToFile();
        } catch (IOException ex) {
            Logger.getLogger(FileMake.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
