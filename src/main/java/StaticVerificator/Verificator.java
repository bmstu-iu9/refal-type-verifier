package StaticVerificator;

import RefalInterpritator.RefalFiveLexer;
import RefalInterpritator.RefalTreeBuilder;
import VerificatorInterpritator.VerificatorLexer;
import VerificatorInterpritator.VerificatorTreeBuilder;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class Verificator {
    public static void main(String[] args) {
        try {
            verify(args[1], args[0]);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    static void verify(String refal_file, String type_file) throws FileNotFoundException {
        VerificatorTreeBuilder verificatorTree = new VerificatorTreeBuilder(new VerificatorLexer(new FileReader(type_file)));
        RefalTreeBuilder refalTree = new RefalTreeBuilder(new RefalFiveLexer(new FileReader(refal_file)));
        try {
            refalTree.parse();
            verificatorTree.parse();
        } catch (Exception e) {
            e.printStackTrace();
        }
        verificatorTree.getTypes().remove(verificatorTree.getTypes().size() - 1);
        refalTree.getDefenition().remove(refalTree.getDefenition().size() - 1);
        System.out.println("Parsing done");
//        System.out.println("!!!!");
//        System.out.println(refalTree.getDefenition());
//        System.out.println("!!!!");
        System.out.println(verificatorTree.getTypes());
//        System.out.println("!!!!");
//        System.out.println(verificatorTree.getFuncs());
//        System.out.println("!!!!");
//        Comparator comparator = new Comparator(verificatorTree);
//        refalTree.getDefenition().forEach(function -> {
//            int i = 0;
//            for (i = 0; i < verificatorTree.getFuncs().size(); i++) {
//                if (verificatorTree.getFuncs().get(i).getName().equals(function.getName())) {
//                    break;
//                }
//            }
//
//            assert(function.getName() != null);
//            if (i == verificatorTree.getFuncs().size()) {
//                System.out.println("No function declaration:" + function.getName());
//            }
//
//            function.setType(verificatorTree.getFuncs().get(i));
//        });
//        System.out.println("Semantic test done");
//        PatternMatching patternMatching = new PatternMatching(verificatorTree.getFuncs(), verificatorTree.getTypes());
//        for (int i = 0; i < refalTree.getDefenition().size(); i++) {
//            int j = 0;
//            for (; j < verificatorTree.getFuncs().size(); j++) {
//                if (verificatorTree.getFuncs().get(j).getName().equals(refalTree.getDefenition().get(i).getName())) {
//                    break;
//                }
//            }
//            if (j == verificatorTree.getFuncs().size()) {
//                System.out.println("Error, cant find description");
//                continue;
//            }
//            if (!patternMatching.match(refalTree.getDefenition().get(i), verificatorTree.getFuncs().get(j))) {
//                System.out.println("Error, not match with description in " + refalTree.getDefenition().get(i));
//            }
//        }
//        System.out.println("Verification finished");
    }
}
