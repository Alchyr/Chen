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
        AbstractDungeon.actionManager.addToTop(new HiddenApplyPowerAction(targetMonster, source, new DividedAttackPower(targetMonster, 1), 1));

        if (targetMonster != null)
        {
            if (targetMonster.intent == AbstractMonster.Intent.ATTACK ||
                    targetMonster.intent == AbstractMonster.Intent.ATTACK_BUFF ||
                    targetMonster.intent == AbstractMonster.Intent.ATTACK_DEBUFF ||
                    targetMonster.intent == AbstractMonster.Intent.ATTACK_DEFEND)
            {
                EnemyMoveInfo targetMove = (EnemyMoveInfo)ReflectionHacks.getPrivate(targetMonster, AbstractMonster.class, "move");

                int newDamage = targetMove.baseDamage;
                //logger.info(targetMonster.name + "'s base damage: " + newDamage);
                if (newDamage > 0)
                {
                    newDamage = Math.max(1, newDamage / 2);
                }
                //logger.info("New base damage: " + newDamage);

                int newHits = 2;
                if (targetMove.isMultiDamage)
                {
                    newHits *= targetMove.multiplier;
                }

                ReflectionHacks.setPrivate(targetMonster, AbstractMonster.class, "move", new EnemyMoveInfo(targetMove.nextMove, targetMove.intent, newDamage, newHits, true));

                targetMonster.createIntent();
                for (AbstractPower p : targetMonster.powers)
                {
                    p.updateDescription();
                }
            }
        }

        this.isDone = true;
    }
}
