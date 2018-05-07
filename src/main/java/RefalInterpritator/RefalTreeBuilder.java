package RefalInterpritator;

import java_cup.runtime.Scanner;
import java_cup.runtime.Symbol;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class RefalTreeBuilder extends parser {


    private RefalNode start;
    private List<RefalNode> functions = new ArrayList<>();

    public RefalTreeBuilder(Scanner scanner) {
    	super(scanner);
	}

    @Override
    public Symbol parse() throws Exception
    {
      /* the current action code */
      int act;
      String tree = "-";
      Map<Symbol, Boolean> usedByParser = new HashMap<>();
      RefalNode curNode = new RefalNode(new LexerToken("START"));
      start = curNode;
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
      tos = 0;
      int j = 0;
      boolean isFunc = false;
      /* continue until we are told to stop */
      for (_done_parsing = false; !_done_parsing; )
	{
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
	      /* shift to the encoded state by pushing it on the stack */
	      cur_token.parse_state = act-1;
          usedByParser.put(cur_token, true);
	      stack.push(cur_token);
	      switch (cur_token.sym) {
              case sym.LBRACE:
                  j++;
                  curNode = curNode.getLastChild();
                  functions.add(curNode);
                  break;
              case sym.RBRACE:
              case sym.RPAREN:
              case sym.RCHEVRON:
              case sym.SEMICOLON:
                  curNode = curNode.getParen();
                  break;
              case sym.LCHEVRON:
                  curNode.addChild(new RefalNode(new LexerToken("FUNC")).setParen(curNode));
                  curNode = curNode.getLastChild();
                  break;
              case sym.NAME:
              case sym.QUOTEDSTRING:
              case sym.INTEGER_LITERAL:
              case sym.VARIABLE:
                  curNode.addChild(new RefalNode((LexerToken)cur_token.value).setParen(curNode));
                  break;
              case sym.EQUAL:
                  curNode.addChild(new RefalNode(new LexerToken("VARS")).setParen(curNode));
                  curNode = curNode.getLastChild();
                  break;
              case sym.LPAREN:
                  curNode.addChild(new RefalNode(new LexerToken("PARENS")).setParen(curNode));
                  curNode = curNode.getLastChild();
                  break;
              case sym.COMMA:
                  curNode.addChild(new RefalNode(new LexerToken("CONDITIONS")).setParen(curNode));
                  curNode = curNode.getLastChild();
                  break;
          }
//	      if(cur_token.sym == 6) {
//              j++;
//              curNode = curNode.getLastChild();
//              functions.add(curNode);
//	      }
//	      if(cur_token.sym == 10 || cur_token.sym == 13 || cur_token.sym == 2 || cur_token.sym == 7) {
//            curNode = curNode.getParen();
//	      }
//	      if(cur_token.sym == 12) {
//            curNode.addChild(new RefalNode(new LexerToken("FUNC")).setParen(curNode));
//            curNode = curNode.getLastChild();
//
//	      }
//	      if(cur_token.sym == 11 || cur_token.sym == 16 || cur_token.sym == 14 || cur_token.sym == 15) {
//                curNode.addChild(new RefalNode((LexerToken)cur_token.value).setParen(curNode));
//	      }
//	      if(cur_token.sym == 8) {
//            curNode.addChild(new RefalNode(new LexerToken("VARS")).setParen(curNode));
//            curNode = curNode.getLastChild();
//	      }
//	      if(cur_token.sym == 9) {
//            curNode.addChild(new RefalNode(new LexerToken("PARENS")).setParen(curNode));
//            curNode = curNode.getLastChild();
//	      }
//	      if(cur_token.sym == 17) {
//            curNode.addChild(new RefalNode(new LexerToken("CONDITIONS")).setParen(curNode));
//            curNode = curNode.getLastChild();
//	      }
	      tree+="-";
	      tos++;

	      /* advance to the next Symbol */
	      cur_token = scan();
          usedByParser.put(cur_token, false);
	    }
	  /* if its less than zero, then it encodes a reduce action */
	  else if (act < 0)
	    {
	        //j--;
	      /* perform the action for the reduce */
	      //System.out.print(tree);
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
	      tree+="-";
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

    public RefalNode getStart() {
        return start;
    }

    public List<RefalNode> getFunctions() {
        return functions;
    }
}

