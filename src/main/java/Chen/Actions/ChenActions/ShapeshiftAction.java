package Chen.Actions.ChenActions;

import Chen.Abstracts.ShiftChenCard;
import Chen.Abstracts.TwoFormCharacter;
import Chen.ChenMod;
import Chen.Effects.PoofEffect;
import Chen.Effects.ShiftCardEffect;
import Chen.Interfaces.NoShapeshiftPower;
import Chen.Interfaces.OnShiftSubscriber;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ExhaustCardEffect;
import com.megacrit.cardcrawl.vfx.combat.SmokeBombEffect;

public class ShapeshiftAction extends AbstractGameAction {
    private boolean targetForm;
    private boolean autoForm = false;
    private AbstractCard sourceCard;
    private boolean firstCheck;
    private boolean trigger;

    private static final float DUR = 0.15F;

    public ShapeshiftAction(AbstractCard sourceCard)
    {
        this.autoForm = true;
        this.sourceCard = sourceCard;
        this.duration = DUR;
        this.actionType = ActionType.SPECIAL;
        this.firstCheck = true;
        this.trigger = false;
    }
    public ShapeshiftAction(AbstractCard sourceCard, boolean Form)
    {
        this.sourceCard = sourceCard;
        targetForm = Form;
        this.duration = DUR;
        this.actionType = ActionType.SPECIAL;
        this.firstCheck = true;
        this.trigger = false;
    }

    @Override
    public void update() {
        if (AbstractDungeon.isPlayerInDungeon() && firstCheck) {
            firstCheck = false;
            boolean canTrigger = true;


            if ((AbstractDungeon.player instanceof TwoFormCharacter)) {
                if (autoForm)
                {
                    targetForm = !((TwoFormCharacter) AbstractDungeon.player).Form;
                }

                if (((TwoFormCharacter) AbstractDungeon.player).Form != targetForm)
                {
                    for (AbstractPower p : AbstractDungeon.player.powers)
                    {
                        if (p instanceof NoShapeshiftPower)
                        {
                            ((NoShapeshiftPower) p).onCancelShapeshift();
                            canTrigger = false;
                            this.isDone = true;
                        }
                    }

                    if (canTrigger)
                    {
                        trigger = true;

                        AbstractDungeon.effectList.add(new PoofEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY));

                        for (AbstractCard c : AbstractDungeon.player.hand.group)
                        {
                            if (c instanceof ShiftChenCard)
                            {
                                AbstractDungeon.effectList.add(new ShiftCardEffect((ShiftChenCard)c, targetForm));
                            }
                        }
                        for (AbstractCard c : AbstractDungeon.player.drawPile.group)
                        {
                            if (c instanceof ShiftChenCard)
                            {
                                ((ShiftChenCard)c).Shift(targetForm);
                            }
                        }
                        for (AbstractCard c : AbstractDungeon.player.discardPile.group)
                        {
                            if (c instanceof ShiftChenCard)
                            {
                                ((ShiftChenCard)c).Shift(targetForm);
                            }
                        }
                        for (AbstractCard c : AbstractDungeon.player.exhaustPile.group)
                        {
                            if (c instanceof ShiftChenCard)
                            {
                                ((ShiftChenCard)c).Shift(targetForm);
                            }
                        }
                        if (sourceCard != null && sourceCard instanceof ShiftChenCard)
                        {
                            AbstractDungeon.effectList.add(new ShiftCardEffect((ShiftChenCard)sourceCard, targetForm));
                        }

                        //trigger shift subscribers
                        for (AbstractRelic r : AbstractDungeon.player.relics)
                        {
                            if (r instanceof OnShiftSubscriber)
                            {
                                ((OnShiftSubscriber) r).OnShiftForm();
                            }
                        }

                        for (AbstractPower p : AbstractDungeon.player.powers)
                        {
                            if (p instanceof OnShiftSubscriber)
                            {
                                ((OnShiftSubscriber) p).OnShiftForm();
                            }
                        }
                    }
                }
            }
            else if (AbstractDungeon.player != null)
            {
                for (AbstractPower p : AbstractDungeon.player.powers)
                {
                    if (p instanceof NoShapeshiftPower)
                    {
                        ((NoShapeshiftPower) p).onCancelShapeshift();
                        this.isDone = true;
                    }
                }
            }
        }

        this.tickDuration();

        if (this.isDone && trigger)
        {
            ((TwoFormCharacter) AbstractDungeon.player).Shift(targetForm);
            AbstractDungeon.player.hand.applyPowers();
            AbstractDungeon.player.hand.glowCheck();
            ChenMod.shiftsThisTurn++;
            AbstractDungeon.actionManager.addToTop(new WaitAction(0.2F));
        }
    }
}
