package Chen.Actions.ChenActions;

import Chen.Abstracts.ShiftChenCard;
import Chen.Effects.PoofEffect;
import Chen.Effects.ShiftCardEffect;
import Chen.Interfaces.NoShapeshiftPower;
import Chen.Interfaces.OnShiftSubscriber;
import Chen.Patches.TwoFormFields;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

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


            //if ((AbstractDungeon.player instanceof TwoFormCharacter)) {
            if (autoForm)
            {
                targetForm = !TwoFormFields.getForm();
            }

            if (TwoFormFields.getForm() != targetForm)
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
                            ((ShiftChenCard)c).shift(targetForm);
                        }
                    }
                    for (AbstractCard c : AbstractDungeon.player.discardPile.group)
                    {
                        if (c instanceof ShiftChenCard)
                        {
                            ((ShiftChenCard)c).shift(targetForm);
                        }
                    }
                    for (AbstractCard c : AbstractDungeon.player.exhaustPile.group)
                    {
                        if (c instanceof ShiftChenCard)
                        {
                            ((ShiftChenCard)c).shift(targetForm);
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

        this.tickDuration();

        if (this.isDone && trigger)
        {
            TwoFormFields.Shift(targetForm);
            TwoFormFields.shiftsThisTurn++;
            AbstractDungeon.player.hand.applyPowers();
            AbstractDungeon.player.hand.glowCheck();
            AbstractDungeon.actionManager.addToTop(new WaitAction(0.2F));
        }
    }
}
