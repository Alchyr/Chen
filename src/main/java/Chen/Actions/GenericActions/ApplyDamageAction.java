package Chen.Actions.GenericActions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class ApplyDamageAction extends AbstractGameAction {
    private DamageInfo info;

    public ApplyDamageAction(AbstractCreature target, DamageInfo info)
    {
        this.target = target;
        this.source = info.owner;
        this.actionType = ActionType.DAMAGE;
        this.info = info;
    }

    @Override
    public void update() {
        if (target.currentHealth > 0) {
            target.damageFlash = true;
            target.damageFlashFrames = 4;
            this.info.applyPowers(this.info.owner, target);
            target.damage(this.info);
        }
        this.isDone = true;
    }
}
