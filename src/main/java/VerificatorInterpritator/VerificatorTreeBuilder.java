package VerificatorInterpritator;

import java_cup.parser;
import java_cup.runtime.*;
import jdk.nashorn.internal.parser.Lexer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class VerificatorTreeBuilder extends parser {

    public VerificatorTreeBuilder(Scanner scanner) {
        super(scanner);
    }

    private VerificatorNode start;
    private List<VerificatorNode> functions = new ArrayList<>();
    private List<VerificatorNode> typeDef = new ArrayList<>();

    public List<VerificatorNode> getTypeDef() {
        return typeDef;
    }

    @Override
    public Symbol parse() throws Exception
    {
      /* the current action code */
      int act;
      String tree = "-";
      Map<Symbol, Boolean> usedByParser = new HashMap<>();
      VerificatorNode curNode = new VerificatorNode(new LexerToken("START"));
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
      //System.out.print("-");
      tos = 0;
      int j = 0;
      boolean isFunc = false;
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
	      if(cur_token.sym == 2) {
            curNode.addChild(new VerificatorNode(new LexerToken("PARENS")).setParen(curNode));
              curNode = curNode.getLastChild();
	      }
	      if(cur_token.sym == 4) {
            if(isFunc) {
                isFunc = false;
                curNode.addChild(new VerificatorNode((LexerToken)cur_token.value).setParen(curNode));
                curNode = curNode.getLastChild();
                functions.add(curNode);
                curNode.addChild(new VerificatorNode(new LexerToken("ARGS")).setParen(curNode));
                curNode = curNode.getLastChild();
            } else {
                curNode.addChild(new VerificatorNode((LexerToken)cur_token.value).setParen(curNode));
            }
	      }
	      if(cur_token.sym == 5) {
                isFunc = true;
	      }
	      if(cur_token.sym == 9) {
            j++;
            curNode = curNode.getLastChild();
              typeDef.add(curNode);
	      }
	      if(cur_token.sym == 11 || cur_token.sym == 12 || cur_token.sym == 15 || cur_token.sym == 16) {
              curNode.addChild(new VerificatorNode((LexerToken)cur_token.value).setParen(curNode));
	      }
	      if (cur_token.sym == 13) {
              curNode.addChild(new VerificatorNode(new LexerToken("MANY")).setParen(curNode));
          }
	      tree+="-";
	      tos++;
	      if (cur_token.sym == 6) {
	          curNode = curNode.getParen();
          }
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
	      if(-act-1 == 18 || -act-1 == 17 || -act-1 == 16 || -act-1 == 4 || -act-1 == 24) {
            curNode = curNode.getParen();
	      }
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
}

