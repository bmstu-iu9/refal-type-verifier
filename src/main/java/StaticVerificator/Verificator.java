package StaticVerificator;

import VerificatorInterpritator.VerificatorLexer;
import VerificatorInterpritator.VerificatorTreeBuilder;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class Verificator {
    public static void main(String[] args) throws FileNotFoundException {
        VerificatorTreeBuilder verificatorTree = new VerificatorTreeBuilder(new VerificatorLexer(new FileReader(args[0])));
    }
}
