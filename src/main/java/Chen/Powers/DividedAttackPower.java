package Chen.Powers;

import Chen.Abstracts.Power;
import Chen.Actions.GenericActions.HiddenRemoveSpecificPowerAction;
import Chen.ChenMod;
import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.InvisiblePower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.EnemyMoveInfo;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.ArrayList;
import java.util.List;

public class DividedAttackPower extends Power implements InvisiblePower {
    public static final String NAME = "DivideAttack";
    public static final PowerType TYPE = PowerType.DEBUFF;
    public static final boolean TURN_BASED = true;

    public static final String POWER_ID = ChenMod.makeID(NAME);

    private List<DamageInfo> alreadyDone;

    private EnemyMoveInfo ExpectedMoveInfo = null;

    public DividedAttackPower(final AbstractCreature owner)
    {
        super(NAME, TYPE, TURN_BASED, owner, null, 1);

        alreadyDone = new ArrayList<>();
    }


    @Override
    public void onInitialApplication() {
        if (owner instanceof AbstractMonster)
        {
            AbstractMonster targetMonster = (AbstractMonster)owner;
            if (targetMonster.intent == AbstractMonster.Intent.ATTACK ||
                    targetMonster.intent == AbstractMonster.Intent.ATTACK_BUFF ||
                    targetMonster.intent == AbstractMonster.Intent.ATTACK_DEBUFF ||
                    targetMonster.intent == AbstractMonster.Intent.ATTACK_DEFEND)
            {
                EnemyMoveInfo targetMove = (EnemyMoveInfo) ReflectionHacks.getPrivate(targetMonster, AbstractMonster.class, "move");

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

                ExpectedMoveInfo = new EnemyMoveInfo(targetMove.nextMove, targetMove.intent, newDamage, newHits, true);

                ReflectionHacks.setPrivate(targetMonster, AbstractMonster.class, "move", ExpectedMoveInfo);

                targetMonster.createIntent();
                for (AbstractPower p : targetMonster.powers)
                {
                    p.updateDescription();
                }
            }
        }
    }

    @Override
    public void stackPower(int stackAmount) {
        if (owner instanceof AbstractMonster)
        {
            AbstractMonster targetMonster = (AbstractMonster)owner;
            if (targetMonster.intent == AbstractMonster.Intent.ATTACK ||
                    targetMonster.intent == AbstractMonster.Intent.ATTACK_BUFF ||
                    targetMonster.intent == AbstractMonster.Intent.ATTACK_DEBUFF ||
                    targetMonster.intent == AbstractMonster.Intent.ATTACK_DEFEND)
            {
                EnemyMoveInfo targetMove = (EnemyMoveInfo) ReflectionHacks.getPrivate(targetMonster, AbstractMonster.class, "move");

                if (!targetMove.equals(ExpectedMoveInfo))
                {
                    this.amount = 1;
                }
                else
                {
                    this.amount += 1;
                }

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

                ExpectedMoveInfo = new EnemyMoveInfo(targetMove.nextMove, targetMove.intent, newDamage, newHits, true);

                ReflectionHacks.setPrivate(targetMonster, AbstractMonster.class, "move", ExpectedMoveInfo);

                targetMonster.createIntent();
                for (AbstractPower p : targetMonster.powers)
                {
                    p.updateDescription();
                }
            }
        }
    }

    @Override
    public void atStartOfTurn() {
        if (owner instanceof AbstractMonster) {
            EnemyMoveInfo targetMove = (EnemyMoveInfo) ReflectionHacks.getPrivate((AbstractMonster)owner, AbstractMonster.class, "move");

            if (!targetMove.equals(ExpectedMoveInfo))
            {
                this.amount = 0;
                AbstractDungeon.actionManager.addToBottom(new HiddenRemoveSpecificPowerAction(owner, owner, this.ID));
            }
        }
        else
        {
            AbstractDungeon.actionManager.addToBottom(new HiddenRemoveSpecificPowerAction(owner, owner, this.ID));
        }
    }

    //Damage reduction is done through patch, to ensure it applies first.
    //Hit doubling is done here.
    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        super.onAttack(info, damageAmount, target);

        if (this.amount > 0 && !alreadyDone.contains(info) && info.type == DamageInfo.DamageType.NORMAL) {
            DamageInfo copyDamageInfo = new DamageInfo(info.owner, info.output, info.type); //copy the final damage, since applyPowers won't get called
            //ChenMod.logger.info("Incoming damage: " + damageAmount);

            AbstractGameAction.AttackEffect attackEffect = AbstractGameAction.AttackEffect.NONE;

            if (AbstractDungeon.actionManager.currentAction != null && AbstractDungeon.actionManager.currentAction.actionType == AbstractGameAction.ActionType.DAMAGE)
            {
                if (AbstractDungeon.actionManager.currentAction.attackEffect != null)
                    attackEffect = AbstractDungeon.actionManager.currentAction.attackEffect;
            }

            alreadyDone.add(copyDamageInfo);

            int multiplier = (int)Math.pow(2, this.amount) - 1;

            for (int i = 0; i < multiplier; i++)
            {
                AbstractDungeon.actionManager.addToBottom(new DamageAction(target,
                        copyDamageInfo, attackEffect));
            }
        }
    }

    public void atEndOfRound() {
        AbstractDungeon.actionManager.addToBottom(new HiddenRemoveSpecificPowerAction(owner, owner, this.ID));
    }
}
