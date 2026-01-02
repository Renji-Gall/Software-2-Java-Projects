import components.statement.Statement;
import components.statement.StatementKernel.Condition;

public static void simplifyIfElse(Statement s) {
    switch (s.kind()) {
        case BLOCK: {
            for (int i = 0; i < s.lengthOfBlock(); i++) {
                Statement sub = s.removeFromBlock(i);
                simplifyIfElse(sub);
                s.addToBlock(i, sub);
            }
            break;
        }
        case IF: {
            Condition cond = s;
            Statement body = s.newInstance();
            s.disassembleIf(body);
            simplifyIfElse(body);
            s.assembleIf(cond, body);
            break;
        }
        case IF_ELSE: {
            Condition cond = s;
            Statement then = s.newInstance();
            Statement elseValue = s.newInstance();
            s.disassembleIfElse(then, elseValue);

            simplifyIfElse(then);
            simplifyIfElse(elseValue);

            switch (cond) {
                case NEXT_IS_NOT_EMPTY:
                    cond = Condition.NEXT_IS_EMPTY;
                    Statement temp1 = then;
                    then = elseValue;
                    elseValue = temp1;
                    break;
                case NEXT_IS_NOT_ENEMY:
                    cond = Condition.NEXT_IS_ENEMY;
                    Statement temp2 = then;
                    then = elseValue;
                    elseValue = temp2;
                    break;
                case NEXT_IS_NOT_FRIEND:
                    cond = Condition.NEXT_IS_FRIEND;
                    Statement temp3 = then;
                    then = elseValue;
                    elseValue = temp3;
                    break;
                case NEXT_IS_NOT_WALL:

                    cond = Condition.NEXT_IS_WALL;
                    Statement temp4 = then;
                    then = elseValue;
                    elseValue = temp4;

                    break;
                default:
                    break;
            }

            s.assembleIfElse(cond, then, elseValue);
            break;
        }
        case WHILE: {
            Condition cond = s;
            Statement body = s.newInstance();
            s.disassembleWhile(body);
            simplifyIfElse(body);
            s.assembleWhile(cond, body);
            break;
        }
        case CALL: {
            break;
        }
        default: {
            break;
        }
    }
}
