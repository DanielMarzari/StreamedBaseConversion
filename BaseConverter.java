
import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * * @author Daniel Marzari 11/19/22
 */
public class BaseConverter {
    boolean group;
    byte c;
    int cdigit = 0, base, carry, i, j, k;
    int[] rep, bit_masks = {1<<7, 1<<6, 1<<5, 1<<4, 1<<3, 1<<2, 1<<1, 1};
    long f_bit_len;
    FileInputStream in;
    String path;
    
    /**
     * @param file_path the path to the desired input file
     * @param group_bits whether bytes should be computed as a byte or 8 bits
     * @param set_base optional user defined base, default is sqrt(file_length)
     */
    public BaseConverter (String file_path, boolean group_bits, int... set_base){
        //save init
        path = file_path;
        group = group_bits;

        //needed for f.length
        File f = new File(path); 
        f_bit_len = f.length() * 8;
        //optional param handling
        base = (set_base.length >= 1) ? set_base[0] : (int) Math.sqrt(f_bit_len);

        //# of digits in the out base = (in_len * ln(in_base) / ln(out_base)) + 1
        int depth = (int) Math.floor(f_bit_len * Math.log(2) / Math.log(base));
        rep = new int[depth];
    }
    
    public boolean start(){
        try {
            //read file and process each byte
            in = new FileInputStream(path);
            while ((c = (byte) in.read()) != -1) {
                process(c);
            }
        } catch (Exception e) {
            //unsuccessful file read or conversion
            System.out.println(e);
            return false;
        }
        //successful file read and conversion
        return true;
    }
    
    public void process(byte b){
        //group switch - optimize by bypassing
        if(group){
            process_byte(b);
        }else{
            process_bits(new int[] {
                b & bit_masks[0] >> 7,
                b & bit_masks[1] >> 6,
                b & bit_masks[2] >> 5,
                b & bit_masks[3] >> 4,
                b & bit_masks[4] >> 3,
                b & bit_masks[5] >> 2,
                b & bit_masks[6] >> 1,
                b & bit_masks[7]
            });
        }
    }
    
    public void process_byte(byte b){
        //Horner method val[current]*in_base + val[next]
        for(j = 0; j <= cdigit; j++){
            rep[j] <<= 8;
        }
        rep[0] += b;
        //adjust for overages
        clean_rep();
    }
    
    public void process_bits(int[] bits){
        //update for each bit
        for(i = 0; i < 8; i++){
            //Horner method val[current]*in_base + val[next]
            for(j = 0; j <= cdigit; j++){
                rep[j] <<= 1;
            }
            rep[0] += bits[i];
            //adjust for overages
            clean_rep();
        }
    }
    
    public void clean_rep(){
        //Adjust where rep[j] > base
        carry = 0;
        for(k = 0; k <= cdigit; k++){            
            //update the digit with carry value from the previous itteration
            rep[k] += carry;
            //reset carry counter
            carry = 0;
            //simplify down to a number within the base
            while(rep[k] > base){
                rep[k] -= base;
                carry += 1;
            }
            if((carry > 0) & (k == cdigit)){
                cdigit++;
            }
        }
    }
    
    public void print_rep(){
        //reverse rep (most->least significant digit)
        String reversed = Arrays.toString(IntStream.rangeClosed(1, rep.length).map(i -> rep[rep.length - i]).toArray());
        System.out.printf("File in base %s is %s\n", base, reversed);
    }
}