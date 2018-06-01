package StaticVerificator;

import RefalInterpritator.RefalFiveLexer;
import RefalInterpritator.RefalTreeBuilder;
import VerificatorInterpritator.VerificatorLexer;
import VerificatorInterpritator.VerificatorNode;
import VerificatorInterpritator.VerificatorTreeBuilder;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class Verificator {
    public static void main(String[] args) throws FileNotFoundException {
        VerificatorTreeBuilder verificatorTree = new VerificatorTreeBuilder(new VerificatorLexer(new FileReader(args[0])));
        RefalTreeBuilder refalTree = new RefalTreeBuilder(new RefalFiveLexer(new FileReader(args[1])));
        try {
            refalTree.parse();
            verificatorTree.parse();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(refalTree.getStart());
        System.out.println(verificatorTree.getStart());
        Comparator comparator = new Comparator(verificatorTree);
//        refalTree.getFunctions().forEach(refalNode -> {
//            int j = 0;
//            //TODO: Посмотреть как сделать более аккуратный поиск через стрим как в тестах
//            for (; j < verificatorTree.getFunctions().size(); j++) {
//                if (refalNode.getName().equals(verificatorTree.getFunctions().get(j).getName())) {
//                    break;
//                }
//            }
//            if (j == verificatorTree.getFunctions().size()) {
//                System.out.println("Error, can't find function");
//                return;
//            } else {
//                System.out.println("It's Ok");
//            }
//            // Поиск функции окончен
//            VerificatorNode verificatorNode = verificatorTree.getFunctions().get(j);
//            for (int m = 0; m < refalNode.getChilds().size(); m++) {
//                int k = m;
//                for (; !refalNode.getChilds().get(k).getName().equals("VARS"); k++) {
//                }
//                if (!comparator.compareNodes(refalNode.getChilds().subList(m, k), verificatorNode.getArguments())) {
//                    System.out.println("Error in" + refalNode.getChilds().subList(m, k));
//                } else {
//                    System.out.println("OK");
//                }
//                m = k;
//                if (comparator.checkResult(refalNode.getChilds().get(m).getChilds(), verificatorNode.getResult())) {
//                    System.out.println("OK");
//                } else {
//                    System.out.println("Error in" + refalNode.getChilds().get(m).getChilds());
//                }
//            }
//        });
    }
}
