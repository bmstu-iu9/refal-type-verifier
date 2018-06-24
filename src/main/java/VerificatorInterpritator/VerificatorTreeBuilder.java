package VerificatorInterpritator;

import VerificatorInterpritator.Tokens.*;
import java_cup.runtime.Scanner;
import java_cup.runtime.Symbol;

import java.util.*;


public class VerificatorTreeBuilder extends parser {

    public VerificatorTreeBuilder(Scanner scanner) {
        super(scanner);
    }

    private VerificatorNode start;
    private HelpExpression currentSimple;
    private List<VerificatorNode> functions = new ArrayList<>();
    private List<VerificatorNode> typeDef = new ArrayList<>();
    private List<Function> funcs = new ArrayList<>();
    private List<Type> types = new ArrayList<>();
    private boolean isFunction = false;
    private boolean isStretch = false;
    Stack<Boolean> isStr;
    private List<Type> baseTypes = new ArrayList<>();

    public List<VerificatorNode> getTypeDef() {
        return typeDef;
    }

    @Override
    public Symbol parse() throws Exception
    {
        baseTypes.add(new Type("s.NUMBER"));
        baseTypes.add(new Type("s.COMPOUND"));
        baseTypes.add(new Type("s.CHAR"));
        baseTypes.add(new Type("s.ANY"));
        baseTypes.add(new Type("t.ANY"));
        baseTypes.add(new Type("e.ANY"));
      /* the current action code */
      int act;
        isStr = new Stack<>();
        isStr.push(false);
      String tree = "-";
      Map<Symbol, Boolean> usedByParser = new HashMap<>();
//      VerificatorNode curNode = new VerificatorNode(new LexerToken("START"));
      types.add(new Type());
//      start = curNode;
      /* the Symbol/stack element returned by a reduce */
      Symbol lhs_sym = null;

      /* information about production being reduced with */
      short handle_size, lhs_sym_num;

      /* set up direct reference to tables to drive the parser */

      production_tab = production_table();
      action_tab     = action_table();
      reduce_tab     = reduce_table();

      /* initialize the action encapsulation object */
      init_actions();

      /* do user initialization */
      user_init();

      /* get the first token */
      cur_token = scan();
      usedByParser.put(cur_token, false);
      /* push dummy Symbol with start state to get us underway */
      stack.removeAllElements();
      stack.push(getSymbolFactory().startSymbol("START", 0, start_state()));
      //System.out.print("-");
      tos = 0;
      int j = 0;
      boolean isFunc = false;
      currentSimple = new HelpExpression();
      /* continue until we are told to stop */
      for (_done_parsing = false; !_done_parsing; )
	{
	    //System.out.println("1");
	  /* Check current token for freshness. */
	  if (usedByParser.get(cur_token))
	    throw new Error("Symbol recycling detected (fix your scanner).");

	  /* current state is always on the top of the stack */

	  /* look up action out of the current state with the current input */
	  act = get_action(((Symbol)stack.peek()).parse_state, cur_token.sym);
	  //boolean isOk = true;
	  /* decode the action -- > 0 encodes shift */
	  if (act > 0)
	    {
//            System.out.println(cur_token);
	    //System.out.println(j);
	      /* shift to the encoded state by pushing it on the stack */
	      cur_token.parse_state = act-1;
          usedByParser.put(cur_token, true);
	      stack.push(cur_token);
	      switch (cur_token.sym) {
              case sym.LPAREN:
                  currentSimple = new HelpExpression().setParrent(currentSimple);
                  currentSimple.getCurrentSimples().add(new StretchType());
                  isStr.push(false);
//
//                  curNode.addChild(new VerificatorNode(new LexerToken("PARENS")).setParen(curNode));
//                  curNode = curNode.getLastChild();
                  break;
              case sym.RPAREN:
                  SimpleType bracketsContent = stretchOrFixed((StretchType)currentSimple.getLastSimpleType()).get(0);
                  isStr.pop();
//                  System.out.println(isStr.peek());
                  if (isStr.peek()) {
                      ((StretchType)currentSimple.getParrent().getLastSimpleType()).getRight().add(new Brackets().setContent(bracketsContent));
                  } else {
                      ((StretchType)currentSimple.getParrent().getLastSimpleType()).getLeft().add(new Brackets().setContent(bracketsContent));
                  }
//                  System.out.println(currentSimple.getCurrentSimples());
//                  currentSimple.getCurrentSimples().set(currentSimple.getCurrentSimples().size() - 1, stretchOrFixed((StretchType) currentSimple.getLastSimpleType()).get(0));
                  currentSimple = currentSimple.getParrent();
                  break;
              case sym.NAME:
                  if(isFunc) {
                      funcs.get(funcs.size() - 1).setName(((LexerToken)cur_token.value).name);
                      currentSimple = new HelpExpression();
                      currentSimple.getCurrentSimples().add(new StretchType());

                      isFunc = false;
//                      curNode.addChild(new VerificatorNode((LexerToken)cur_token.value).setParen(curNode));
//                      curNode = curNode.getLastChild();
//                      functions.add(curNode);
//                      curNode.addChild(new VerificatorNode(new LexerToken("ARGS")).setParen(curNode));
//                      curNode = curNode.getLastChild();
                  } else {
                      if (isStr.peek()) {
                          ((StretchType)currentSimple.getLastSimpleType()).getRight().add(new Compound(((LexerToken)cur_token.value).name));
                      } else {
                          ((StretchType)currentSimple.getLastSimpleType()).getLeft().add(new Compound(((LexerToken)cur_token.value).name));
                      }

//                      curNode.addChild(new VerificatorNode((LexerToken)cur_token.value).setParen(curNode));
                  }
                  break;
              case sym.LCHEVRON:
                  funcs.add(new Function());
                  currentSimple = new HelpExpression();
                  isFunc = true;
                  isFunction = true;
                  break;
              case sym.VAREQUAL:
                  j++;
                  currentSimple = new HelpExpression();
                  currentSimple.getCurrentSimples().add(new StretchType());
//                  curNode = curNode.getLastChild();
//                  typeDef.add(curNode);
                  break;
              case sym.VARIABLE:
                  if (types.get(types.size() - 1).getName() == null && !isFunction) {
                      types.get(types.size() - 1).setMode(((LexerToken)cur_token.value).name.charAt(0));
                      types.get(types.size() - 1).setName(((LexerToken)cur_token.value).name.substring(2));
                  } else {
                      if (isStr.peek()) {
                          ((StretchType)currentSimple.getLastSimpleType()).getRight().add(new VarTermType(((LexerToken)cur_token.value).name));
                          if(!checkVarTermDefinition ((VarTermType)((StretchType)currentSimple.getLastSimpleType()).getRight().get(((StretchType)currentSimple.getLastSimpleType()).getRight().size() - 1))) {
                              System.out.println("Undefaind variable " + ((StretchType)currentSimple.getLastSimpleType()).getRight().get(((StretchType)currentSimple.getLastSimpleType()).getRight().size() - 1));
                          }
                      } else {
                          ((StretchType)currentSimple.getLastSimpleType()).getLeft().add(new VarTermType(((LexerToken)cur_token.value).name));
                          if(!checkVarTermDefinition ((VarTermType)((StretchType)currentSimple.getLastSimpleType()).getLeft().get(((StretchType)currentSimple.getLastSimpleType()).getLeft().size() - 1))) {
                              System.out.println("Undefaind variable " + ((StretchType)currentSimple.getLastSimpleType()).getLeft().get(((StretchType)currentSimple.getLastSimpleType()).getLeft().size() - 1));
                          }
                      }
                  }
              case sym.QUOTEDSTRING:
              case sym.INTEGER_LITERAL:
                  if (isStr.peek()) {
                      ((StretchType)currentSimple.getLastSimpleType()).getRight().add(new Compound(((LexerToken)cur_token.value).name));
                  } else {
                      ((StretchType)currentSimple.getLastSimpleType()).getLeft().add(new Compound(((LexerToken)cur_token.value).name));
                  }
                  // ???
//                  curNode.addChild(new VerificatorNode((LexerToken)cur_token.value).setParen(curNode));
//                  curNode.addChild(new VerificatorNode((LexerToken)cur_token.value).setParen(curNode));
                  break;
              case sym.MANY:
//                  System.out.println(currentSimple.getCurrentSimples());
                  ((StretchType)currentSimple.getLastSimpleType()).lastIsStretch();
                  isStr.pop();
                  isStr.push(true);
//                  curNode.addChild(new VerificatorNode(new LexerToken("MANY")).setParen(curNode));
                  break;
              case sym.RCHEVRON:
//                  System.out.println(funcs.get(funcs.size() - 1).getName());
                  funcs.get(funcs.size() - 1).setArgument(stretchOrFixed((StretchType) currentSimple.getLastSimpleType()).get(0));
                  currentSimple = new HelpExpression();
//                  curNode = curNode.getParen();
                  break;
              case sym.SEMICOLON:
                  if (isFunction) {
                      funcs.get(funcs.size() - 1).setResult(stretchOrFixed((StretchType) currentSimple.getLastSimpleType()));
                      currentSimple = new HelpExpression();
                      isFunction= false;
                  } else {
                      currentSimple.getCurrentSimples().set(currentSimple.getCurrentSimples().size() - 1, stretchOrFixed((StretchType) currentSimple.getLastSimpleType()).get(0));
                      types.get(types.size() - 1).setConstructors(currentSimple.getCurrentSimples());
                      currentSimple = new HelpExpression();
                      types.add(new Type());
                  }
                  break;
              case sym.EQUAL:
                  currentSimple.getCurrentSimples().add(new StretchType());
                  break;
              case sym.OR:
                  currentSimple.getCurrentSimples().set(currentSimple.getCurrentSimples().size() - 1, stretchOrFixed((StretchType) currentSimple.getLastSimpleType()).get(0));
                  currentSimple.getCurrentSimples().add(new StretchType());
                  break;
          }
	      tos++;
	      cur_token = scan();
          usedByParser.put(cur_token, false);
	    }
	  /* if its less than zero, then it encodes a reduce action */
	  else if (act < 0)
	    {
	        //j--;
	      /* perform the action for the reduce */
	      //System.out.print(tree);
//	      if(-act-1 == 18 || -act-1 == 17 || -act-1 == 16 || -act-1 == 4 || -act-1 == 24) {
//            curNode = curNode.getParen();
//	      }
	      lhs_sym = do_action((-act)-1, this, stack, tos);
	      //System.out.println("!!!!!!");
	      /* look up information about the production */
	      lhs_sym_num = production_tab[(-act)-1][0];
	      handle_size = production_tab[(-act)-1][1];
	      //System.out.println(handle_size);
	      /* pop the handle off the stack */
	      for (int i = 0; i < handle_size; i++)
		{
          usedByParser.remove(stack.pop());
		  tos--;
		}

	      /* look up the state to go to from the one popped back to */
	      act = get_reduce(((Symbol)stack.peek()).parse_state, lhs_sym_num);
	      /* shift to that state */
	      lhs_sym.parse_state = act;
          usedByParser.put(lhs_sym, true);
	      stack.push(lhs_sym);
	      //tree+="-";
	      tos++;
	    }
	  /* finally if the entry is zero, we have an error */
	  else if (act == 0)
	    {
	      /* call user syntax error reporting routine */
	      syntax_error(cur_token);

	      /* try to error recover */
	      if (!error_recovery(false))
		{
		  /* if that fails give up with a fatal syntax error */
		  unrecovered_syntax_error(cur_token);

		  /* just in case that wasn't fatal enough, end parse */
		  done_parsing();
		} else {
		  lhs_sym = (Symbol)stack.peek();
		}
	    }
	}
      return lhs_sym;
    }

    public VerificatorNode getStart() {
        return start;
    }

    public List<VerificatorNode> getFunctions() {
        return functions;
    }

    public List<Function> getFuncs() {
        return funcs;
    }

    public void setFuncs(List<Function> funcs) {
        this.funcs = funcs;
    }

    public List<Type> getTypes() {
        return types;
    }

    public void setTypes(List<Type> types) {
        this.types = types;
    }

    private List<SimpleType> stretchOrFixed(StretchType stretchType) {
        List<SimpleType> ans = new ArrayList<>();
        if (isStr.peek()) {
            isStr.pop();
            isStr.push(false);
            ans.add(stretchType);
            return ans;
        }
        ans.add(new FixedType().setTerms(stretchType.getLeft()));
//        System.out.println(stretchType);
        return ans;
    }

    public boolean checkVarTermDefinition(VarTermType varTermType) {
        for (int i = 0; i < types.size(); i++) {
            if (types.get(i).getName() != null && types.get(i).getName().equals(varTermType.getName()) && types.get(i).getMode() == varTermType.getMode()) {
                varTermType.setRef(types.get(i));
                return true;
            }
        }
        for (int i = 0; i < baseTypes.size(); i++) {
            if (baseTypes.get(i).getName().equals(varTermType.getName()) && baseTypes.get(i).getMode() == varTermType.getMode()) {
                varTermType.setRef(baseTypes.get(i));
                return true;
            }
        }
        return false;
    }

}


