package Chen.Actions.GenericActions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class VFXIfAliveAction extends AbstractGameAction {
    private AbstractGameEffect effect;
    private float startingDuration;
    private boolean isTopLevelEffect;

    public VFXIfAliveAction(AbstractCreature target, AbstractGameEffect effect) {
        this(target, (AbstractCreature)null, effect);
    }

    public VFXIfAliveAction(AbstractCreature target, AbstractCreature source, AbstractGameEffect effect) {
        this.isTopLevelEffect = false;
        this.setValues(target, source);
        this.effect = effect;
        this.actionType = ActionType.WAIT;
    }

    public VFXIfAliveAction(AbstractCreature target, AbstractCreature source, AbstractGameEffect effect, boolean topLevel) {
        this.isTopLevelEffect = false;
        this.setValues(target, source);
        this.effect = effect;
        this.actionType = ActionType.WAIT;
        this.isTopLevelEffect = topLevel;
    }

    public void update() {
        if (!target.isDeadOrEscaped()) {
            if (this.isTopLevelEffect) {
                AbstractDungeon.topLevelEffects.add(this.effect);
            } else {
                AbstractDungeon.effectList.add(this.effect);
            }
        }

        this.isDone = true;
    }
}