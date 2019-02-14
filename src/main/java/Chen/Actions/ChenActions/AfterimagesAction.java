package Chen.Actions.ChenActions;

import Chen.Actions.GenericActions.HiddenApplyPowerAction;
import Chen.Powers.DividedAttackPower;
import basemod.ReflectionHacks;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.EnemyMoveInfo;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static Chen.ChenMod.logger;

public class AfterimagesAction extends AbstractGameAction {
    private AbstractMonster targetMonster;

    public AfterimagesAction(AbstractMonster target, AbstractCreature source)
    {
        this.source =  source;
        this.targetMonster = target;
        this.actionType = ActionType.SPECIAL;
    }

    @Override
    public void update() {
        AbstractDungeon.actionManager.addToTop(new HiddenApplyPowerAction(targetMonster, source, new DividedAttackPower(targetMonster), 1));

        this.isDone = true;
    }
}
