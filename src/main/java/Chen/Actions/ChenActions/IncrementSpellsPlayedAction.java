package Chen.Actions.ChenActions;

import Chen.ChenMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;

public class IncrementSpellsPlayedAction extends AbstractGameAction {
    @Override
    public void update() {
        ChenMod.spellsThisCombat += 1;
        isDone = true;
    }
}
