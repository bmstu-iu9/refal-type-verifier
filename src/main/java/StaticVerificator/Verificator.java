package StaticVerificator;

import RefalInterpritator.RefalFiveLexer;
import RefalInterpritator.RefalTreeBuilder;
import VerificatorInterpritator.VerificatorLexer;
import VerificatorInterpritator.VerificatorTreeBuilder;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class Verificator {
    public static void main(String[] args) throws FileNotFoundException {
        VerificatorTreeBuilder verificatorTree = new VerificatorTreeBuilder(new VerificatorLexer(new FileReader(args[0])));
        RefalTreeBuilder refalTree = new RefalTreeBuilder(new RefalFiveLexer(new FileReader(args[1])));
        try {
            refalTree.parse();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(refalTree.getStart());
    }
}
